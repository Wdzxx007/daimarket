package com.jishi.daichao.rx;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by laulee on 17/3/31.
 */

public class RxBus {

    private final Subject<Object> bus;

    public RxBus() {
        // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
        this.bus = PublishSubject.create( ).toSerialized();
    }

    public static RxBus getDefault() {
        return RxBusHolder.sInstance;
    }

    // 提供了一个新的事件
    public void post(Object o) {
        bus.onNext(o);
    }

    private static class RxBusHolder {
        private static final RxBus sInstance = new RxBus();
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
