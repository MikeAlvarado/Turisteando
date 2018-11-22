package apps.softtek.com.turisteando.models

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import apps.softtek.com.turisteando.MainActivity
import apps.softtek.com.turisteando.R
import apps.softtek.com.turisteando.fragment.DestinationFragment
import apps.softtek.com.turisteando.recycler.PlaceAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class App : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: App? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }

        var currentSelectionn = "NLE"
        val firebaseDatabase = FirebaseDatabase.getInstance()

        val places = ArrayList<Place>()
        var adapter = PlaceAdapter(applicationContext(),places)

        fun updatePlaces(){
            if (currentSelectionn == ""){
                FirebaseDatabase.getInstance().reference.child("Lugares").addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(e: DatabaseError) {
                        Log.d(DestinationFragment::class.java.simpleName, "${e.message} - ${e.toException()}")
                    }

                    override fun onDataChange(ds: DataSnapshot) {
                        places.clear()
                        ds.children.forEach{ destinoSnapshot->
                            val lugar = destinoSnapshot.getValue(Place::class.java)
                            lugar?.let {
                                if (lugar.PlaceParent == App.currentSelectionn) {
                                    places.add(lugar)
                                }

                            }
                        }
                        adapter.notifyDataSetChanged()
                    }
                })

            }
        }

    }


    override fun onCreate() {
        super.onCreate()
        val context: Context = App.applicationContext()

    }

}