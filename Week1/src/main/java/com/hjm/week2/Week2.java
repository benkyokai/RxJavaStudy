package com.hjm.week2;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Week2 {
    private Week2() {
    }

    public static void run() {
        System.out.println("\nsample1");
        sample1();

        System.out.println("\nsample2");
        sample2();

        System.out.println("\nsample3");
        sample3();

        System.out.println("\nexample4");
        example4();

        System.out.println("\nexample5");
        example5();

        System.out.println("\nexample6");
        example6();
    }

    private static void sample1() {
        // 1.
        // Observer の作成と購読
        Integer[] numbers = {1, 2, 3, 4, 5};
        Observable<Integer> observable = Observable.fromArray(numbers);

        Observer<Integer> observer = new Observer<Integer>() {
            @Override public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe(): " + d);
            }
            @Override public void onNext(Integer integer) {
                System.out.println("onNext(): " + integer);
            }
            @Override public void onError(Throwable e) {
                System.out.println("onError(): " + e);
            }
            @Override public void onComplete() {
                System.out.println("onComplete()");
            }
        };
        observable.subscribe(observer);
    }

    private static void sample2() {
        // 2.
        // onNext, onError, onComplete, onSubscribe を個別に与える
        Integer[] numbers = {1, 2, 3, 4, 5};
        Observable<Integer> observable = Observable.fromArray(numbers);

        Consumer<Integer> onNext = new Consumer<Integer>() {
            @Override public void accept(Integer integer) throws Exception {
                System.out.println("onNext(): " + integer);
            }
        };
        Consumer<Throwable> onError = new Consumer<Throwable>() {
            @Override public void accept(Throwable e) throws Exception {
                System.out.println("onError(): " + e);
            }
        };
        Action onComplete = new Action() {
            @Override public void run() throws Exception {
                System.out.println("onComplete()");
            }
        };
        Consumer<Disposable> onSubscribe = new Consumer<Disposable>() {
            @Override public void accept(Disposable disposable) throws Exception {
                System.out.println("onSubscribe(): " + disposable);
            }
        };

        observable.subscribe(onNext, onError, onComplete, onSubscribe);

    }

    private static void sample3() {
        // 3.
        // onNext, onError, onComplete, onSubscribe を個別に与える
        Integer[] numbers = {1, 2, 3, 4, 5};
        Observable<Integer> observable = Observable.fromArray(numbers);

        observable.subscribe(
            integer -> {
                System.out.println("onNext(): " + integer);
            },
            e -> {
                System.out.println("onError(): " + e);
            },
            () -> {
                System.out.println("onComplete()");
            },
            disposable ->  {
                System.out.println("onSubscribe(): " + disposable);
            });
    }

    private static void example4() {
        // 4.
        // 3 の時に失敗させる
        Integer[] numbers = {1, 2, 3, 4, 5};
        Observable<Integer> observable = Observable.fromArray(numbers);

        Consumer<Integer> onNext = new Consumer<Integer>() {
            @Override public void accept(Integer integer) throws Exception {
                if (integer != null && integer == 3) {
                    throw new RuntimeException("A number is three.");
                }
                System.out.println("onNext(): " + integer);
            }
        };
        Consumer<Throwable> onError = new Consumer<Throwable>() {
            @Override public void accept(Throwable e) throws Exception {
                System.out.println("onError(): " + e);
            }
        };
        Action onComplete = new Action() {
            @Override public void run() throws Exception {
                System.out.println("onComplete()");
            }
        };

        observable.subscribe(onNext, onError, onComplete);
    }

    private static void example5() {
        // 5.
        // Observable の作成
        Observable<Integer> observable =
            Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                    e.onNext(1);
                    e.onNext(2);
                    e.onNext(3);
                    e.onComplete();
                }
            });

        Observer<Integer> observer = new Observer<Integer>() {
            @Override public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe(): " + d);
            }
            @Override public void onNext(Integer n) {
                System.out.println("onNext(): " + n);
            }
            @Override public void onError(Throwable e) {
                System.out.println("onError(): " + e);
            }
            @Override public void onComplete() {
                System.out.println("onComplete()");
            }
        };
        observable.subscribe(observer);
    }

    private static void example6() {
        // 6.
        // subscribeOn() と observeOn() の指定
        Observable<Integer> observable =
            Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                    e.onNext(1);
                    e.onNext(2);
                    e.onNext(3);
                    e.onComplete();
                    System.out.println("subscribe() end");
                }
            });

        Observer<Integer> observer = new Observer<Integer>() {
            @Override public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe(): " + d);
            }
            @Override public void onNext(Integer n) {
                System.out.println("onNext(): " + n);
            }
            @Override public void onError(Throwable e) {
                System.out.println("onError(): " + e);
            }
            @Override public void onComplete() {
                System.out.println("onComplete()");
            }
        };

        observable
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .subscribe(observer);
    }
}
