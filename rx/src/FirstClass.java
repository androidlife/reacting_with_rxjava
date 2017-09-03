import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by laaptu on 9/1/17.
 */
public class FirstClass {
    public static void main(String[] args) {
        flatMapAgain();

    }

    private static void combinationTest() {
        Observable
                .just(8, 9, 10)
                .doOnNext(i -> System.out.println("A: " + i))
                .filter(i -> i % 3 > 0)
                .doOnNext(i -> System.out.println("B: " + i))
                .map(i -> "#" + i * 10)
                .doOnNext(s -> System.out.println("C: " + s))
                .filter(s -> s.length() < 4)
                .subscribe(s -> System.out.println("D: " + s));
    }

    private static Observable<Integer> transformInteger(int value) {
        return Observable.just(value * 10);
    }

    private static Observable<Integer> transformInt(int value) {
        return Observable.just(value * 30);
    }

    private static void mapTestAgain() {
        Observable<Integer> observable = Observable.range(1, 10);
//        observable.flatMap(i -> i != 9 ? addTwo(i) : null)
//                .subscribe(i -> System.out.println(i));
        observable.flatMap(i -> i != 9 ? addTwo(i) : null)
                .subscribe(i -> System.out.println(i));


    }

    private static Observable<Integer> addTwo(int value) {
        return Observable.just(value);
    }

    private static void flatMapAgain() {
        Observable<Integer> observable = Observable.range(1, 10);
        observable.flatMap(i -> Observable.just(i * 2),
                e -> Observable.just(0),
                () -> Observable.just(100))
                .subscribe(i -> System.out.println(i));

        System.out.println("----------");
        myIterable().flatMap(i -> Observable.just(i * 2),
                e -> Observable.just(0),
                () -> Observable.just(100))
                .subscribe(i -> System.out.println(i));
        myIterable().subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private static Observable<Integer> myIterable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                for (int i = 1; i <= 10; ++i)
                    observableEmitter.onNext(i);
            }
        });
    }


    private static void mapTest() {

        Observable<Integer> mainObservable = Observable.just(1, 2, 3);
        Observable<Integer> observable = mainObservable.map(i -> i * 10);
        Observable<Integer> mapObservable = mainObservable.flatMap(i -> Observable.just(i * 10));

        Observable<Observable<Integer>> map = mainObservable.map(i -> transformInteger(i));
        Observable<Integer> flatMap = mapObservable.flatMap(i -> transformInteger(i));

        map.subscribe(o -> o.subscribe(i -> System.out.println(i)));
        flatMap.subscribe(i -> System.out.println(i));


        Observable.just(1, 2, 3)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return null;
                    }
                })
                .flatMap(new Function<Integer, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Integer integer) throws Exception {
                        return null;
                    }
                });
    }
}

