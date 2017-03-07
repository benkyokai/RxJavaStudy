package com.hjm.week3;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Week3 {
    private Week3() {
    }

    public static void run() {
//        System.out.println("\nsample0");
//        sample0();
//
//        System.out.println("\nsample1");
//        sample1();
//
//        System.out.println("\nsample2");
//        sample2();
//
//        System.out.println("\nsample3");
//        sample3();
//
//        System.out.println("\nsample4");
//        sample4();
//
//        System.out.println("\nsample5");
//        sample5();
//
//        System.out.println("\nsample6");
//        sample6();
//        sample6Fixed();
//
//        System.out.println("\nsample7");
//        sample7();
//        sample7Fixed();
//
//        System.out.println("\nsample8");
//        sample8();
//
//        System.out.println("\nsample9");
//        sample9();
//
//        System.out.println("\nsample10");
//        sample10();
    }

    //
    // スレッド無指定の場合
    //
    private static void sample0() {
        Observable<Integer> observable =
            Observable.create(e -> {
                printCurrentThread("subscribe");
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            });

        observable
            .map(integer -> {
                printCurrentThread("map1-" + integer);
                return integer;
            })
            .map(integer -> {
                printCurrentThread("map2-" + integer);
                return integer;
            })
            .map(integer -> {
                printCurrentThread("map3-" + integer);
                return integer;
            })
            .subscribe(integer -> {
                printCurrentThread("onNext-" + integer);
            });
    }

    //
    // バックグラウンドで実行
    //
    private static void sample1() {
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(Week3::sample0);
        es.shutdown();
    }


    //
    // newThread による割り当て
    //
    private static void sample2() {
        Observable<Integer> observable =
            Observable.create(e -> {
                printCurrentThread("subscribe");
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            });

        observable
            .subscribeOn(Schedulers.newThread()) // newThread
            .observeOn(Schedulers.newThread()) // newThread
            .map(integer -> {
                printCurrentThread("map1-" + integer);
                return integer;
            })
            .observeOn(Schedulers.newThread()) // newThread
            .map(integer -> {
                printCurrentThread("map2-" + integer);
                return integer;
            })
            .observeOn(Schedulers.newThread()) // newThread
            .map(integer -> {
                printCurrentThread("map3-" + integer);
                return integer;
            })
            .observeOn(Schedulers.newThread()) // newThread
            .subscribe(integer -> {
                printCurrentThread("onNext");
            });

        wait(500);
    }

    //
    // newThread による割り当てを一部コメントアウト
    //
    private static void sample3() {
        Observable<Integer> observable =
            Observable.create(e -> {
                printCurrentThread("subscribe");
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            });

        observable
            .subscribeOn(Schedulers.newThread()) // newThread
            .observeOn(Schedulers.newThread()) // newThread
            .map(integer -> {
                printCurrentThread("map1-" + integer);
                return integer;
            })
            //.observeOn(Schedulers.newThread()) // newThread
            .map(integer -> {
                printCurrentThread("map2-" + integer);
                return integer;
            })
            .observeOn(Schedulers.newThread()) // newThread
            .map(integer -> {
                printCurrentThread("map3-" + integer);
                return integer;
            })
            .observeOn(Schedulers.newThread()) // newThread
            .subscribe(integer -> {
                printCurrentThread("onNext");
            });

        wait(500);
    }

    //
    // computation() を利用
    //
    private static void sample4() {
        // initialization
        System.setProperty("rx2.computation-threads", "2");

        Observable<Integer> observable =
            Observable.create(e -> {
                printCurrentThread("subscribe");
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            });

        observable
            .subscribeOn(Schedulers.computation()) // computation
            .observeOn(Schedulers.computation()) // computation
            .map(integer -> {
                printCurrentThread("map1-" + integer);
                return integer;
            })
            .observeOn(Schedulers.computation()) // computation
            .map(integer -> {
                printCurrentThread("map2-" + integer);
                return integer;
            })
            .observeOn(Schedulers.computation()) // computation
            .map(integer -> {
                printCurrentThread("map3-" + integer);
                return integer;
            })
            .observeOn(Schedulers.computation()) // computation
            .subscribe(integer -> {
                printCurrentThread("onNext");
            });

        wait(1000);

        // finalization
        System.clearProperty("rx2.computation-threads");
    }

    //
    // io() を利用
    //
    private static void sample5() {
        // 一つ目の Observable
        Observable<Integer> observable1 =
            Observable.create(e -> {
                printCurrentThread("subscribe");
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            });

        observable1
            .subscribeOn(Schedulers.io()) // io
            .observeOn(Schedulers.io()) // io
            .map(integer -> {
                printCurrentThread("map1-" + integer);
                return integer;
            })
            .observeOn(Schedulers.io()) // io
            .map(integer -> {
                printCurrentThread("map2-" + integer);
                return integer;
            })
            .observeOn(Schedulers.io()) // io
            .map(integer -> {
                printCurrentThread("map3-" + integer);
                return integer;
            })
            .observeOn(Schedulers.io()) // io
            .subscribe(integer -> {
                printCurrentThread("onNext");
            });

        wait(1000);

        // 二つ目の Observable
        Observable<Integer> observable2 =
            Observable.create(e -> {
                printCurrentThread("subscribe");
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            });

        observable2
            .subscribeOn(Schedulers.io()) // io
            .observeOn(Schedulers.io()) // io
            .map(integer -> {
                printCurrentThread("map1-" + integer);
                return integer;
            })
            .observeOn(Schedulers.io()) // io
            .map(integer -> {
                printCurrentThread("map2-" + integer);
                return integer;
            })
            .observeOn(Schedulers.io()) // io
            .map(integer -> {
                printCurrentThread("map3-" + integer);
                return integer;
            })
            .observeOn(Schedulers.io()) // io
            .subscribe(integer -> {
                printCurrentThread("onNext");
            });

        wait(1000);
    }

    //
    // computation() 破綻ケース
    //
    private static void sample6() {
        // computation()
        int times = 50;
        for (int i = 0; i < times; i++) {
            fetchDataFromNetwork(Schedulers.computation());
        }

        wait(30000);
    }

    private static void fetchDataFromNetwork(Scheduler scheduler) {
        Observable<Integer> observable =
            Observable.create(e -> {
                printCurrentThread("subscribe");
                ioBoundedProcess(1000);
                e.onNext(1);
                e.onComplete();
            });

        observable
            .subscribeOn(scheduler)
            .observeOn(scheduler)
            .subscribe(integer -> {
                printCurrentThread("completed: " + integer);
            });
    }

    private static void ioBoundedProcess(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignore) {
        }
    }

    private static void sample6Fixed() {
        int times = 50;
        for (int i = 0; i < times; i++) {
            fetchDataFromNetwork(Schedulers.io());
        }

        wait(1000);
    }

    //
    // io() 破綻ケース
    //
    private static void sample7() {
        int times = 1000;
        for (int i = 0; i < times; i++) {
            mining(Schedulers.io());
        }

        wait(25000);
    }

    private static int cpuBoundedProcess(long times) {
        int sum = 0;
        for (int i = 0; i < times; i++) {
            for (int j = 0; j < times; j++) {
                sum += (i + j);
            }
        }
        return sum;
    }

    private static void mining(Scheduler scheduler) {
        Observable<Integer> observable =
            Observable.create(e -> {
                printCurrentThread("subscribe");
                cpuBoundedProcess(40000);
                e.onNext(1);
                e.onComplete();
            });

        observable
            .subscribeOn(scheduler)
            .observeOn(scheduler)
            .subscribe(integer -> {
                printCurrentThread("completed: " + integer);
            });
    }

    private static void sample7Fixed() {
        int times = 1000;
        for (int i = 0; i < times; i++) {
            mining(Schedulers.computation());
        }

        wait(25000);
    }

    //
    // single() を利用
    //
    private static void sample8() {
        Observable<Integer> observable =
            Observable.create(e -> {
                printCurrentThread("subscribe");
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            });

        observable
            .subscribeOn(Schedulers.single()) // single
            .observeOn(Schedulers.single()) // single
            .map(integer -> {
                printCurrentThread("map1-" + integer);
                return integer;
            })
            .observeOn(Schedulers.single()) // single
            .map(integer -> {
                printCurrentThread("map2-" + integer);
                return integer;
            })
            .observeOn(Schedulers.single()) // single
            .map(integer -> {
                printCurrentThread("map3-" + integer);
                return integer;
            })
            .observeOn(Schedulers.single()) // single
            .subscribe(integer -> {
                printCurrentThread("onNext");
            });

        wait(500);
    }

    //
    // trampoline() を利用
    //
    private static void sample9() {
        Observable<Integer> observable =
            Observable.create(e -> {
                printCurrentThread("subscribe");
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            });

        observable
            .subscribeOn(Schedulers.trampoline()) // single
            .observeOn(Schedulers.trampoline()) // single
            .map(integer -> {
                printCurrentThread("map1-" + integer);
                return integer;
            })
            .observeOn(Schedulers.trampoline()) // single
            .map(integer -> {
                printCurrentThread("map2-" + integer);
                return integer;
            })
            .observeOn(Schedulers.trampoline()) // single
            .map(integer -> {
                printCurrentThread("map3-" + integer);
                return integer;
            })
            .observeOn(Schedulers.trampoline()) // single
            .subscribe(integer -> {
                printCurrentThread("onNext");
            });

        wait(500);
    }

    //
    // from() を利用
    //
    private static void sample10() {
        Observable<Integer> observable =
            Observable.create(e -> {
                printCurrentThread("subscribe");
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            });

        Scheduler scheduler = Schedulers.from(Executors.newFixedThreadPool(4));
        observable
            .subscribeOn(scheduler)
            .observeOn(scheduler)
            .map(integer -> {
                printCurrentThread("map1-" + integer);
                return integer;
            })
            .observeOn(scheduler)
            .map(integer -> {
                printCurrentThread("map2-" + integer);
                return integer;
            })
            .observeOn(scheduler)
            .map(integer -> {
                printCurrentThread("map3-" + integer);
                return integer;
            })
            .observeOn(scheduler)
            .subscribe(integer -> {
                printCurrentThread("onNext");
            });

        wait(500);
    }




    
    private static void wait(int milles) {
        try {
            // Schedulers の実行スレッドがデーモンスレッドなので完了を待たないと終了してしまう
            // 終了を待ついい方法が見つからない。newThread() 内の ExecutorService を見ることができないし。
            Thread.sleep(milles);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void printCurrentThread(String tag) {
        Thread current = Thread.currentThread();
        System.out.println(tag + ": " + current.getName());
    }

}
