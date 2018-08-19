package com.braintri.github.util.extension

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.OnErrorNotImplementedException
import io.reactivex.schedulers.Schedulers

private val onNextStub: (Any) -> Unit = {}
private val onErrorStub: (Throwable) -> Unit = { throw OnErrorNotImplementedException(it) }
private val onCompleteStub: () -> Unit = {}

fun <T : Any> Observable<T>.retrySubscribe(onNext: (T) -> Unit = onNextStub,
                                           onError: (Throwable) -> Unit = onErrorStub,
                                           onComplete: () -> Unit = onCompleteStub) =
        this.doOnNext(onNext)
                .doOnComplete(onComplete)
                .doOnError(onError)
                .retry()
                .subscribe()!!

fun <T : Any> Maybe<T>.retrySubscribe(onSuccess: (T) -> Unit = onNextStub,
                                      onError: (Throwable) -> Unit = onErrorStub,
                                      onComplete: () -> Unit = onCompleteStub) =
        this.doOnSuccess(onSuccess)
                .doOnComplete(onComplete)
                .doOnError(onError)
                .subscribe()!!

fun Completable.retrySubscribe(onError: (Throwable) -> Unit = onErrorStub,
                               onComplete: () -> Unit = onCompleteStub) =
        this.doOnComplete(onComplete)
                .doOnError(onError)
                .retry()
                .subscribe()!!

fun <T> Observable<T>.observeOnMain(): Observable<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.observeOnIo(): Observable<T> {
    return this.observeOn(Schedulers.io())
}

fun <T> Observable<T>.observeOnComputation(): Observable<T> {
    return this.observeOn(Schedulers.computation())
}

fun <T> Observable<T>.subscribeOnIo(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
}

fun <T> Single<T>.subscribeOnIo(): Single<T> {
    return this.subscribeOn(Schedulers.io())
}

fun Completable.observeOnMain(): Completable {
    return this.observeOn(AndroidSchedulers.mainThread())
}
