package com.liu.jim.jobgo.event;


import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * RxBus supporting sticky event
 *
 * normal event is sent by {@link RxBus#mBus}, while sticky event is sent by {@link RxBus#mStickyEvents}
 *
 * @author Hongzhi.Liu
 * @date 2018/12/6
 */
public class RxBus {
    private static volatile RxBus rxBusInstance;

    /**
     * 用于各类事件封装发送
     */
    private final Subject<Object> mBus;

    /**
     * 用于实现sticky事件发送(被订阅前)的缓存
     * sticky event : 距离当前最近的一次已发射的事件
     */
    private final ConcurrentHashMap<Object, Object> mStickyEvents;


    private RxBus() {
        mBus = PublishSubject.create().toSerialized();
        mStickyEvents = new ConcurrentHashMap<>();
    }

    public RxBus get() {
        if (null == rxBusInstance) {
            synchronized (RxBus.class) {
                rxBusInstance = new RxBus();
            }
        }
        return rxBusInstance;
    }


    /**
     * 发射event(调用下游onNext)
     *
     * @param event
     */
    public void post(Object event) {
        mBus.onNext(event);
    }

    /**
     * 返回指定eventType的Observable对象 (用于给观察者监听event)
     * 注意：返回的Observable数据源不包含sticky事件
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }


    /**
     * 发射sticky event
     * 若event 在Observable 被订阅前{@link RxBus#postSticky(Object)}，会立即被发射(尽管对应的observer接受不到)，而且订阅前最近一次event会被缓存，
     * 被缓存的event会在被订阅之后被合并到Observable 数据源
     *
     * 若event 在Observable 被订阅后{@link RxBus#postSticky(Object)}，也会被缓存，而且立即发射
     *
     * @param event
     */
    public void postSticky(Object event) {
        synchronized (mStickyEvents) {
            mStickyEvents.put(event.getClass(), event);
        }
        mBus.onNext(event);
    }

    /**
     * 返回指定eventType的Observable对象 (用于给观察者监听event)
     * 注意：生成的Observable的数据源包括 sticky event 和当前正发射的event
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toStickyObservable(Class<T> eventType) {
        synchronized (mStickyEvents){
            Observable<T> observable = mBus.ofType(eventType);
            Object event = mStickyEvents.get(eventType);
            if(null != event){
                return observable.startWith(eventType.cast(event));
            }else {
                return observable;
            }
        }
    }

    public <T> T getStickyEvent(Class<T> eventType){
        synchronized (mStickyEvents) {
            return eventType.cast(mStickyEvents.get(eventType));
        }
    }


    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (mStickyEvents) {
            return eventType.cast(mStickyEvents.remove(eventType));
        }
    }

    public void removeAllStickyEvents(){
        synchronized (mStickyEvents) {
            mStickyEvents.clear();
        }
    }
}
