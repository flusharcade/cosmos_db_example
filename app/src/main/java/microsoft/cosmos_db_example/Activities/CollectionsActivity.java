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
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

import microsoft.cosmos_db_example.Adapter.Callback;
import microsoft.cosmos_db_example.Adapter.CardAdapter;
import microsoft.cosmos_db_example.Adapter.DatabaseViewHolder;
import microsoft.cosmos_db_example.Controllers.App;
import microsoft.cosmos_db_example.Controllers.CosmosController;
import microsoft.cosmos_db_example.Controllers.CosmosRxController;
import microsoft.cosmos_db_example.Delegates.CosmosDelegate;
import microsoft.cosmos_db_example.Models.Database;
import microsoft.cosmos_db_example.R;

public class CollectionsActivity extends Activity implements CosmosDelegate {
    private static final String TAG = "MainActivity";

    private CardAdapter _adapter;

    //private final CompositeDisposable disposables = new CompositeDisposable();

    private CosmosController controller;

    private CosmosRxController _rxController;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        _rxController = CosmosRxController.getInstance(this);
        controller = CosmosController.getInstance(this);

        _adapter = new CardAdapter(R.layout.database_view, new Callback<Object>() {
            @Override
            public Void call() {
                Database db = (Database)this._result;
                DatabaseViewHolder vHolder = (DatabaseViewHolder)this._viewHolder;

                vHolder.idTextView.setText(db.getId());
                vHolder.descriptionTextView.setText("repos: " + db.getEtag());
                vHolder.ownerTextView.setText("blog: " + db.getSelf());

                return null;
            }
        }, DatabaseViewHolder.class, this);

        /**
         * Set up Android CardView/RecycleView
         */
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(_adapter);

        /**
         * START: button set up
         */
        Button bClear = (Button) findViewById(R.id.button_clear);
        Button bFetch = (Button) findViewById(R.id.button_fetch);
        bClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _adapter.clear();
            }
        });

        bFetch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //controller.getDatabases();

                try
                {
                    /*JSONObject document = new JSONObject();
                    document.put("filename", "new_document");
                    document.put("user", "michael");

                    HashMap<String, Object> params = new HashMap<String, Object>();
                    params.put("document", document.toString());*/

                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("name", "@author");
                    params.put("author", "Leo Tolstoy");

                    //controller.createDocument("testDb", "example", "wiggum", params);

                    /*_rxController.executeQuery("testDb", "example", "SELECT * FROM root WHERE (root.Author.id = @author)",
                            params)
                            .subscribe(x -> {
                                Log.e("onError", "WIGGUM");
                            });*/

                    controller.executeQuery("testDb", "example", "SELECT * FROM root WHERE (root.Author.id = @author)",
                            params);

                    //controller.getAttachment("testDb", "example", "wiggum", "imageId1");
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

    @Override protected void onDestroy() {
        super.onDestroy();
        //disposables.clear();
    }

    void onRunSchedulerExampleButtonClicked() {
        /*disposables.add(sampleObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override public void onComplete() {
                        Log.d(TAG, "onComplete()");
                    }

                    @Override public void onError(Throwable e) {
                        Log.e(TAG, "onError()", e);
                    }

                    @Override public void onNext(String string) {
                        Log.d(TAG, "onNext(" + string + ")");
                    }
                }));*/
    }

    /*static Observable<String> sampleObservable() {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override public ObservableSource<? extends String> call() throws Exception {
                // Do some long running operation
                SystemClock.sleep(5000);
                return Observable.just("one", "two", "three", "four", "five");
            }
        });
    }*/

    public void didError() {

    }
}