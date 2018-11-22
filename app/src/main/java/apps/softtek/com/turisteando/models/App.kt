package apps.softtek.com.turisteando.models

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import apps.softtek.com.turisteando.DestinationBus
import apps.softtek.com.turisteando.MainActivity
import apps.softtek.com.turisteando.PlaceBus
import apps.softtek.com.turisteando.R
import apps.softtek.com.turisteando.fragment.DestinationFragment
import apps.softtek.com.turisteando.recycler.PlaceAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class App : Application() {

    private val destinationBus = DestinationBus()
    private val placeBus = PlaceBus()

    fun getDestinationBus(): DestinationBus = destinationBus
    fun getPlaceBus(): PlaceBus = placeBus

}