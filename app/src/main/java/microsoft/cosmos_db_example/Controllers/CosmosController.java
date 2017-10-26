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

    public void createDatabase(String databaseId) {
        _cosmosService.createDatabase(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId);
    }

    public void getDatabase(String databaseId) {
        _cosmosService.getDatabase(new IAsyncResponse() {
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

    public void getCollection(String databaseId, String collectionId) {
        _cosmosService.getCollection(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId);
    }

    // docs
    public void getDocuments(String databaseId, String collectionId) {
        _cosmosService.getDocumentsInCollection(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId);
    }

    public void createDocument(String databaseId, String collectionId, String documentId, HashMap<String, String> documentParams) {
        _cosmosService.createDocumentInCollection(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId, documentId, documentParams);
    }

    public void getDocument(String databaseId, String collectionId, String documentId) {
        _cosmosService.getDocumentInCollection(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId, documentId);
    }

    // attachments
    public void getAttachments(String databaseId, String collectionId, String attachmentId) {
        _cosmosService.getAttachmentsInDocument(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId, attachmentId);
    }

    public void createAttachment(String databaseId, String collectionId, String documentId, String attachmentId,
                                 String attachmentContentType, String media) {
        _cosmosService.createAttachmentInDocument(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId, documentId, attachmentId, attachmentContentType, media);
    }

    public void getAttachment(String databaseId, String collectionId, String documentId, String attachmentId) {
        _cosmosService.getAttachmentInDocument(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId, documentId, attachmentId);
    }
}