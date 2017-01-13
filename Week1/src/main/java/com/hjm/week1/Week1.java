package com.hjm.week1;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import java.lang.reflect.Array;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Week1 {
    private Week1() {
    }

    public static void run() {
        example1();
        example2();
        example3();
        example4();
        // example5();
    }

    private static void example1() {
        // Observables を配列から生成する
        Integer[] numbers = {1, 2, 3, 4, 5};
        Observable<Integer> observable = Observable.fromArray(numbers);

        // Observables を subscribe する
        observable.subscribe(new Consumer<Integer>() {
            @Override public void accept(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });
    }

    private static void example2() {
        // 単一アイテムの Observable
        Observable<Integer> observable = Observable.just(1);

        // Observables を subscribe する
        observable.subscribe(new Consumer<Integer>() {
            @Override public void accept(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });
    }

    private static void example3() {
        Integer[] numbers = {1, 2, 3, 4, 5};
        Observable<Integer> observable = Observable.fromArray(numbers);
        // Java 8 のラムダ構文
        observable.subscribe(integer -> {
            System.out.println(integer);
        });
    }

    private static void example4() {
        // 5秒後に 1 を生成する Observable
        Observable<Integer> observable = Observable.fromCallable(() -> {
            // メインスレッドで実行される
            Thread.sleep(5000);
            return 1;
        });
        // 実行完了までブロックする
        observable.subscribe(integer -> {
            System.out.println(integer);
        });
    }

    private static void example5() {
        // 5秒後に 1 を生成する Observable
        Future<Integer> future = Executors.newSingleThreadExecutor().submit(() -> {
            // バックグラウンドスレッドで実行
            Thread.sleep(5000);
            return 1;
        });
        Observable<Integer> observable = Observable.fromFuture(future);
        // 終了しないので注意
        observable.subscribe(System.out::println);
    }

}
