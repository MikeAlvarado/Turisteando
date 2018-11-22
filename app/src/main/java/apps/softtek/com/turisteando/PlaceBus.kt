package apps.softtek.com.turisteando

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class PlaceBus {

    private val placeChanges: PublishSubject<String> = PublishSubject.create()

    fun setLatestPlace(place: String) {
        placeChanges.onNext(place)
    }

    fun getChanges(): Observable<String> = placeChanges
}