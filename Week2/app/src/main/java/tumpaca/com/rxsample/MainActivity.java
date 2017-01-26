package tumpaca.com.rxsample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private TextView reactiveText;
    private TextView asyncTaskText;
    private int reactiveTappedCount;
    private int asyncTaskTappedCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reactiveText = (TextView) findViewById(R.id.reactiveText);
        asyncTaskText = (TextView) findViewById(R.id.asyncTaskText);

        reactiveTappedCount = 0;
        asyncTaskTappedCount = 0;

        Button reactiveButton = (Button) findViewById(R.id.button);
        Button asyncTaskButton = (Button) findViewById(R.id.button2);
        Object[] obj = new Void[]{};

        reactiveButton.setOnClickListener((View view) -> {
            Observable
                    .create(new ObservableOnSubscribe<Integer>() {
                        @Override
                        public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                            Thread.sleep(3000);
                            reactiveTappedCount += 1;
                            e.onNext(reactiveTappedCount);
                            e.onComplete();
                        }
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Integer>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            System.out.println("reactiveTask: onSubscribe, thread = " + Thread.currentThread().getId());
                        }

                        @Override
                        public void onNext(Integer integer) {
                            System.out.println("reactiveTask: onNext, thread = " + Thread.currentThread().getId());
                            System.out.println("reactiveTask: reactiveTappedCount = " + integer);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            System.out.println("reactiveTask: completed, thread = " + Thread.currentThread().getId());
                            reactiveText.setText(String.valueOf(reactiveTappedCount));
                        }
                    });
        });

        asyncTaskButton.setOnClickListener(view -> {
            AsyncTask task = new AsyncTask<Void, Integer, Integer>() {
                @Override
                protected Integer doInBackground(Void... voids) {
                    try {
                        System.out.println("asyncTask: doInBackground, thread = " + Thread.currentThread().getId());
                        Thread.sleep(3000);
                        asyncTaskTappedCount += 1;
                        System.out.println("asyncTask: asyncTappedCount = " + asyncTaskTappedCount);
                    } catch (InterruptedException ignored) {
                    }

                    return asyncTaskTappedCount;
                }

                @Override
                protected void onPostExecute(Integer integer) {
                    System.out.println("asyncTask: completed, thread = " + Thread.currentThread().getId());
                    asyncTaskText.setText(String.valueOf(integer));
                }
            };
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, obj);
        });
    }
}