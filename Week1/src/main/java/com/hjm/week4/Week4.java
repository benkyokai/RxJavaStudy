package com.hjm.week4;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Week4 {
    private Week4() {
    }

    public static void run() {
        sample0();
        sample1();
    }

    private static void sample0() {
        // Observable を作成
        Observable<Integer> observable = Observable.create(e -> {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
            e.onComplete();
        });

        observable.subscribe(
            System.out::println,
            System.out::println,
            System.out::println,
            System.out::println
        );
    }

    private static void sample1() {
        // Observable を作成
        Observable<Integer> observable = Observable.create((ObservableEmitter<Integer> e) -> {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
            e.onComplete();
        });

        observable.subscribe(new Observer<Integer>() {
            @Override public void onSubscribe(Disposable d) {
                System.out.println(d);
            }
            @Override public void onNext(Integer integer) {
                System.out.println(integer);
            }
            @Override public void onError(Throwable e) {
                System.out.println(e);
            }
            @Override public void onComplete() {
                // 実際には何もしない出力しない
                System.out.println();
            }
        });
    }

}
