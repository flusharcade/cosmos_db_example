/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package microsoft.cosmos_db_example.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import microsoft.cosmos_db_example.Adapter.Callback;
import microsoft.cosmos_db_example.Adapter.CardAdapter;
import microsoft.cosmos_db_example.Adapter.DocumentViewHolder;
import microsoft.cosmos_db_example.Controllers.App;
import microsoft.cosmos_db_example.Controllers.CosmosRxController;
import microsoft.cosmos_db_example.Delegates.CosmosDelegate;
import microsoft.cosmos_db_example.Models.Document;
import microsoft.cosmos_db_example.R;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DocumentsActivity extends Activity implements CosmosDelegate {
    private static final String TAG = "CollectionsActivity";

    private CardAdapter _adapter;

    private CosmosRxController _rxController;

    private String _databaseId;

    private String _collectionId;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.documents_activity);

        _rxController = CosmosRxController.getInstance(this);

        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        _adapter = new CardAdapter(R.layout.document_view, new Callback<Object>() {
            @Override
            public Void call() {
                Document coll = (Document)this._result;
                DocumentViewHolder vHolder = (DocumentViewHolder)this._viewHolder;

                vHolder.idTextView.setText(coll.getId());
                vHolder.ridTextView.setText(coll.getRid());
                vHolder.selfTextView.setText(coll.getSelf());
                vHolder.eTagTextView.setText(coll.getEtag());

                vHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });

                return null;
            }
        }, DocumentViewHolder.class, this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(_adapter);

        TextView collectionIdTextView = (TextView) findViewById(R.id.collectionId);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _databaseId = extras.getString("db_id");
            _collectionId = extras.getString("coll_id");

            collectionIdTextView.setText(_collectionId);
        }

        Button clearButton = (Button) findViewById(R.id.button_clear);
        Button fetchButton = (Button) findViewById(R.id.button_fetch);
        Button deleteButton = (Button) findViewById(R.id.button_delete);

        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _adapter.clear();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final ProgressDialog dialog = ProgressDialog.show(DocumentsActivity.this, "", "Deleting. Please wait...", true);

                _rxController.deleteCollection(_databaseId, _collectionId)
                        // Run on a background thread
                        .subscribeOn(Schedulers.io())
                        // Be notified on the main thread
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(x -> {
                            Log.e(TAG, "_rxController.deleteDatabases(_databaseId) - finished.");

                            dialog.cancel();

                            finish();
                        });

                _adapter.clear();
            }
        });

        fetchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try
                {
                    final ProgressDialog dialog = ProgressDialog.show(DocumentsActivity.this, "", "Loading. Please wait...", true);

                    _rxController.getDocuments(_databaseId, _collectionId)
                            // Run on a background thread
                            .subscribeOn(Schedulers.io())
                            // Be notified on the main thread
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(x -> {
                                // clear current list
                                _adapter.clear();

                                Log.e(TAG, "_rxController.getDocuments(_databaseId, _collectionId) finished.");

//                                for (DocumentCollectionContract contract: x.getDocumentCollections()) {
//                                    String partitionKey = contract.getPartitionKey() == null ? "" : contract.getPartitionKey().getKind();
//
//                                    _adapter.addData(new DocumentCollection(contract.getId(), contract.getRid(), contract.getSelf(),
//                                            contract.getEtag(), partitionKey));
//                                }

                                dialog.cancel();
                            });
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        App.activityPaused();
    }

    public void didError() {

    }
}