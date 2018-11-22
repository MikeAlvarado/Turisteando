package apps.softtek.com.turisteando

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class DestinationBus {

    private val destinationChanges: PublishSubject<String> = PublishSubject.create()

    fun setLatestDestination(destination: String) {
        destinationChanges.onNext(destination)
    }

    fun getChanges(): Observable<String> = destinationChanges
}