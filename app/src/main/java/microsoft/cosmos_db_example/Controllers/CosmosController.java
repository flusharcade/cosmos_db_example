package microsoft.cosmos_db_example.Controllers;

import java.util.HashMap;

import microsoft.cosmos_db_example.Delegates.CosmosDelegate;
import microsoft.cosmos_db_example.Services.CosmosService;
import microsoft.cosmos_db_example.Services.IAsyncResponse;

/**
 * Created by mww121 on 26/10/17.
 */

public class CosmosController {
    private CosmosService _cosmosService;
    private CosmosDelegate _delegate;

    private static CosmosController instance = null;

    public static CosmosController getInstance(CosmosDelegate delegate) {
        if(instance == null) {
            instance = new CosmosController(delegate);
        }
        else {
            instance.updateDelegate(delegate);
        }

        return instance;
    }

    protected CosmosController(CosmosDelegate delegate) {
        _delegate = delegate;
        _cosmosService = CosmosService.getInstance();
    }

    protected void updateDelegate(CosmosDelegate delegate) {
        _delegate = delegate;
    }

    // dbs
    public void getDatabases() {
        _cosmosService.getDatabases(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        });
    }

    public void getDatabaseById(String databaseId) {
        _cosmosService.getDatabaseById(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId);
    }

    public void createDatabase(String databaseId) {
        _cosmosService.createDatabase(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId);
    }

    // colls
    public void getCollections(String databaseId) {
        _cosmosService.getCollections(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId);
    }

    public void createCollection(String databaseId, String collectionId) {
        _cosmosService.createCollection(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId);
    }

    public void getCollectionById(String databaseId, String collectionId) {
        _cosmosService.getCollectionById(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId);
    }

    // docs
    public void getDocuments(String databaseId, String collectionId) {
        _cosmosService.getDocuments(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId);
    }

    public void getDocumentById(String databaseId, String collectionId, String documentId) {
        _cosmosService.getDocumentById(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId, documentId);
    }

    public void createDocument(String databaseId, String collectionId, String documentId, HashMap<String, String> documentParams) {
        _cosmosService.createDocument(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId, documentId, documentParams);
    }

    // attachments
    public void getAttachments(String databaseId, String collectionId, String attachmentId) {
        _cosmosService.getAttachments(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId, attachmentId);
    }

    public void getAttachmentById(String databaseId, String collectionId, String documentId, String attachmentId) {
        _cosmosService.getAttachmentById(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId, documentId, attachmentId);
    }

    public void createAttachment(String databaseId, String collectionId, String documentId, String attachmentId,
                                 String attachmentContentType, String media) {
        _cosmosService.createAttachment(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId, documentId, attachmentId, attachmentContentType, media);
    }
}