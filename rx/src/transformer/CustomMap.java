package transformer;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import org.reactivestreams.Subscriber;

/**
 * Created by laaptu on 9/3/17.
 */
public class CustomMap<T, R> extends Observable<R> {

    private Function<T, R> function;
    private Observable<T> source;

    public CustomMap(Observable<T> source, Function<T, R> function) {
        this.source = source;
        this.function = function;
    }

    @Override
    protected void subscribeActual(Observer<? super R> observer) {
        source.subscribe(new MapObserver<T, R>(observer, function));
    }

    static class MapObserver<T, R> implements Observer<T> {

        private Observer<? super R> observer;
        private Function<T, R> function;

        public MapObserver(Observer<? super R> observer, Function<T, R> function) {
            this.observer = observer;
            this.function = function;
        }

        @Override
        public void onSubscribe(Disposable disposable) {

        }

        @Override
        public void onNext(T t) {
            try {
                observer.onNext(function.apply(t));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {

        }
    }
}
