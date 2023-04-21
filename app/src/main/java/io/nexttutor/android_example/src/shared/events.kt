package io.nexttutor.android_example.src.shared

import io.reactivex.rxjava3.subjects.PublishSubject

object EventBus {
    val appLoadedEvent = PublishSubject.create<String>()
    val openCardEntryEvent = PublishSubject.create<String>()
}