package microsoft.cosmos_db_example.Services;

import com.microsoft.azure.documentdb.ConnectionPolicy;
import com.microsoft.azure.documentdb.ConsistencyLevel;
import com.microsoft.azure.documentdb.Database;
import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.DocumentClientException;
import com.microsoft.azure.documentdb.DocumentCollection;
import com.microsoft.azure.documentdb.RequestOptions;

import java.util.ArrayList;

import microsoft.cosmos_db_example.Constants.DBConstants;
import microsoft.cosmos_db_example.Models.TodoItem;

/**
 * Created by mww121 on 26/10/17.
 */

public class CosmosDBService {
    public ArrayList<TodoItem> Items;

    DocumentClient client;
    //Uri collectionLink;

    public CosmosDBService()
    {
        client = new DocumentClient(DBConstants.EndpointUrl, DBConstants.PrimaryKey, ConnectionPolicy.GetDefault(),
                ConsistencyLevel.Session);
        //collectionLink = UriFactory.CreateDocumentCollectionUri(DBConstants.DatabaseName, DBConstants.CollectionName);
    }

    public void CreateDatabase(String databaseName)
    {
        try
        {
            // Define a new database using the id above.
            Database myDatabase = new Database();
            myDatabase.setId(databaseName);

            // Create a new database.
            myDatabase = client.createDatabase(myDatabase, null)
                    .getResource();
        }
        catch (DocumentClientException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public void CreateDocumentCollection(String databaseName, String collectionName)
    {
        try
        {
            // Define a new collection using the id above.
            DocumentCollection myCollection = new DocumentCollection();
            myCollection.setId(collectionName);

            // Set the provisioned throughput for this collection to be 1000 RUs.
            RequestOptions requestOptions = new RequestOptions();
            //requestOptions.setOfferThroughput(1000);

            // Create a new collection.
            myCollection = client.createCollection(
                    "dbs/" + databaseName, myCollection, requestOptions)
                    .getResource();
        }
        catch (DocumentClientException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    /*public ArrayList<TodoItem> GetTodoItemsAsync()
    {
        Items = new List<TodoItem>();

        try
        {
            var query = client.CreateDocumentQuery<TodoItem>(collectionLink)
                    .AsDocumentQuery();
            while (query.HasMoreResults)
            {
                Items.AddRange(await query.ExecuteNextAsync<TodoItem>());
            }
        }
        catch (DocumentClientException ex)
        {
            Debug.WriteLine("Error: ", ex.Message);
        }

        return Items;
    }

    public async Task SaveTodoItemAsync(TodoItem item, bool isNewItem = false)
    {
        try
        {
            if (isNewItem)
            {
                await client.CreateDocumentAsync(collectionLink, item);
            }
            else
            {
                await client.ReplaceDocumentAsync(UriFactory.CreateDocumentUri(Constants.DatabaseName, Constants.CollectionName, item.Id), item);
            }
        }
        catch (DocumentClientException ex)
        {
            Debug.WriteLine("Error: ", ex.Message);
        }
    }

    public async Task DeleteTodoItemAsync(string id)
    {
        try
        {
            await client.DeleteDocumentAsync(UriFactory.CreateDocumentUri(Constants.DatabaseName, Constants.CollectionName, id));
        }
        catch (DocumentClientException ex)
        {
            Debug.WriteLine("Error: ", ex.Message);
        }
    }

    async Task DeleteDocumentCollection()
    {
        try
        {
            await client.DeleteDocumentCollectionAsync(collectionLink);
        }
        catch (DocumentClientException ex)
        {
            Debug.WriteLine("Error: ", ex.Message);
        }
    }*/

    void DeleteDatabase()
    {
        // Start from a clean state (delete database in case it already exists).
        try {
            client.deleteDatabase("dbs/" + DBConstants.DatabaseName, null);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
