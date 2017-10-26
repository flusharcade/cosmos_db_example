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
import android.util.Log;
import android.view.View;
import android.widget.Button;

import microsoft.cosmos_db_example.Adapter.CardAdapter;
import microsoft.cosmos_db_example.Controllers.App;
import microsoft.cosmos_db_example.Controllers.CosmosController;
import microsoft.cosmos_db_example.Controllers.CosmosRxController;
import microsoft.cosmos_db_example.Delegates.CosmosDelegate;
import microsoft.cosmos_db_example.R;

public class MainActivity extends Activity implements CosmosDelegate {
    private static final String TAG = "MainActivity";

    //private final CompositeDisposable disposables = new CompositeDisposable();

    private CosmosController controller;

    private CosmosRxController _rxController;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        _rxController = CosmosRxController.getInstance(this);

        /**
         * Set up Android CardView/RecycleView
         */
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final CardAdapter mCardAdapter = new CardAdapter();
        mRecyclerView.setAdapter(mCardAdapter);

        /**
         * START: button set up
         */
        Button bClear = (Button) findViewById(R.id.button_clear);
        Button bFetch = (Button) findViewById(R.id.button_fetch);
        bClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardAdapter.clear();
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

                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("document", document.toString());

                    controller.createDocument("testDb", "example", "wiggum", params);*/

                    _rxController.getDatabaseById("example1")
                            .subscribe(x -> {
                                Log.e("onError", "WIGGUM");
                            });

                    //controller.createAttachment("testDb", "example", "wiggum", "imageId1", "image/jpg", "www.bing.com");

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