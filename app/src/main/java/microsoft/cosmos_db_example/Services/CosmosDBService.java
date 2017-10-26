package microsoft.cosmos_db_example.Services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.azure.documentdb.ConnectionPolicy;
import com.microsoft.azure.documentdb.ConsistencyLevel;
import com.microsoft.azure.documentdb.Database;
import com.microsoft.azure.documentdb.Document;
import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.DocumentClientException;
import com.microsoft.azure.documentdb.DocumentCollection;

import java.util.ArrayList;
import java.util.List;

import microsoft.cosmos_db_example.Constants.DBConstants;
import microsoft.cosmos_db_example.Models.TodoItem;

/**
 * Created by mww121 on 26/10/17.
 */

public class CosmosDBService {
    // The Azure Cosmos DB Client
    private static DocumentClient documentClient;

    // Cache for the database object, so we don't have to query for it to
    // retrieve self links.
    private static Database databaseCache;

    // Cache for the collection object, so we don't have to query for it to
    // retrieve self links.
    private static DocumentCollection collectionCache;

    Gson gson;

    public CosmosDBService() {
        try {
            documentClient = new DocumentClient(DBConstants.EndpointUrl, DBConstants.PrimaryKey,
                    ConnectionPolicy.GetDefault(),
                    ConsistencyLevel.Session);

            gson = new GsonBuilder().create();
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Database getTodoDatabase() {
        if (databaseCache == null) {
            // Get the database if it exists
            List<Database> databaseList = documentClient
                    .queryDatabases(
                            "SELECT * FROM root r WHERE r.id='" + DBConstants.DatabaseId
                                    + "'", null).getQueryIterable().toList();

            if (databaseList.size() > 0) {
                // Cache the database object so we won't have to query for it
                // later to retrieve the selfLink.
                databaseCache = databaseList.get(0);
            } else {
                // Create the database if it doesn't exist.
                try {
                    Database databaseDefinition = new Database();
                    databaseDefinition.setId(DBConstants.DatabaseId);

                    databaseCache = documentClient.createDatabase(
                            databaseDefinition, null).getResource();
                } catch (DocumentClientException e) {
                    // TODO: Something has gone terribly wrong - the app wasn't
                    // able to query or create the collection.
                    // Verify your connection, endpoint, and key.
                    e.printStackTrace();
                }
            }
        }

        return databaseCache;
    }

    private DocumentCollection getTodoCollection() {
        if (collectionCache == null) {
            // Get the collection if it exists.
            List<DocumentCollection> collectionList = documentClient
                    .queryCollections(
                            getTodoDatabase().getSelfLink(),
                            "SELECT * FROM root r WHERE r.id='" + DBConstants.CollectionId
                                    + "'", null).getQueryIterable().toList();

            if (collectionList.size() > 0) {
                // Cache the collection object so we won't have to query for it
                // later to retrieve the selfLink.
                collectionCache = collectionList.get(0);
            } else {
                // Create the collection if it doesn't exist.
                try {
                    DocumentCollection collectionDefinition = new DocumentCollection();
                    collectionDefinition.setId(DBConstants.CollectionId);

                    collectionCache = documentClient.createCollection(
                            getTodoDatabase().getSelfLink(),
                            collectionDefinition, null).getResource();
                } catch (DocumentClientException e) {
                    // TODO: Something has gone terribly wrong - the app wasn't
                    // able to query or create the collection.
                    // Verify your connection, endpoint, and key.
                    e.printStackTrace();
                }
            }
        }

        return collectionCache;
    }

    public List<TodoItem> getTodoItems() {

        ArrayList<TodoItem> items = new ArrayList<TodoItem>();

        try
        {
            // Retrieve the TodoItem documents
            List<Document> documentList = documentClient.queryDocuments(getTodoCollection().getSelfLink(),
                            "SELECT * FROM root r WHERE r.entityType = 'todoItem'",
                            null).getQueryIterable().toList();

            // De-serialize the documents in to TodoItems.
            for (Document todoItemDocument : documentList) {
                items.add(gson.fromJson(todoItemDocument.toString(),
                        TodoItem.class));
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        return items;
    }


    public TodoItem createTodoItem(TodoItem todoItem) {
        // Serialize the TodoItem as a JSON Document.
        Document todoItemDocument = new Document(gson.toJson(todoItem));

        // Annotate the document as a TodoItem for retrieval (so that we can
        // store multiple entity types in the collection).
        todoItemDocument.set("entityType", "todoItem");

        try {
            // Persist the document using the DocumentClient.
            todoItemDocument = documentClient.createDocument(getTodoCollection().getSelfLink(), todoItemDocument, null,
                    false).getResource();
        } catch (DocumentClientException e) {
            e.printStackTrace();
            return null;
        }

        return gson.fromJson(todoItemDocument.toString(), TodoItem.class);
    }

    public TodoItem updateTodoItem(String id, boolean isComplete) {
        // Retrieve the document from the database
        Document todoItemDocument = getDocumentById(id);

        // You can update the document as a JSON document directly.
        // For more complex operations - you could de-serialize the document in
        // to a POJO, update the POJO, and then re-serialize the POJO back in to
        // a document.
        todoItemDocument.set("complete", isComplete);

        try {
            // Persist/replace the updated document.
            todoItemDocument = documentClient.replaceDocument(todoItemDocument,
                    null).getResource();
        } catch (DocumentClientException e) {
            e.printStackTrace();
            return null;
        }

        return gson.fromJson(todoItemDocument.toString(), TodoItem.class);
    }

    private Document getDocumentById(String id) {
        // Retrieve the document using the DocumentClient.
        List<Document> documentList = documentClient.queryDocuments(getTodoCollection().getSelfLink(),
                        "SELECT * FROM root r WHERE r.id='" + id + "'", null)
                .getQueryIterable().toList();

        if (documentList.size() > 0) {
            return documentList.get(0);
        } else {
            return null;
        }
    }

    public TodoItem readTodoItem(String id) {
        // Retrieve the document by id using our helper method.
        Document todoItemDocument = getDocumentById(id);

        if (todoItemDocument != null) {
            // De-serialize the document in to a TodoItem.
            return gson.fromJson(todoItemDocument.toString(), TodoItem.class);
        } else {
            return null;
        }
    }

    public ArrayList<TodoItem> readTodoItems() {
        ArrayList<TodoItem> todoItems = new ArrayList<TodoItem>();

        // Retrieve the TodoItem documents
        List<Document> documentList = documentClient
                .queryDocuments(getTodoCollection().getSelfLink(),
                        "SELECT * FROM root r WHERE r.entityType = 'todoItem'",
                        null).getQueryIterable().toList();

        // De-serialize the documents in to TodoItems.
        for (Document todoItemDocument : documentList) {
            todoItems.add(gson.fromJson(todoItemDocument.toString(),
                    TodoItem.class));
        }

        return todoItems;
    }

    public boolean deleteTodoItem(String id) {
        // Azure Cosmos DB refers to documents by self link rather than id.

        // Query for the document to retrieve the self link.
        Document todoItemDocument = getDocumentById(id);

        try {
            // Delete the document by self link.
            documentClient.deleteDocument(todoItemDocument.getSelfLink(), null);
        } catch (DocumentClientException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
