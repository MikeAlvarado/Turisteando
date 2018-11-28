package apps.softtek.com.turisteando.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import apps.softtek.com.turisteando.R
import apps.softtek.com.turisteando.models.Place
import apps.softtek.com.turisteando.models.Promo
import apps.softtek.com.turisteando.recycler.PromoAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class PlaceDetailFragment: BottomSheetDialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var placeName = getArguments()!!.getInt("placeName")

        return inflater.inflate(R.layout.fragment_place_detail, container, false)
    }

    val promos = ArrayList<Promo>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //getting recyclerview from xml
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.promos_recycler)

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager?

        val adapter = PromoAdapter(context!!,promos)
        recyclerView.adapter = adapter

        //Instantiation of the Database
        FirebaseDatabase.getInstance().reference.child("Promociones").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(e: DatabaseError) {
                Log.d(PlaceDetailFragment::class.java.simpleName, "${e.message} - ${e.toException()}")
            }

            override fun onDataChange(ds: DataSnapshot) {
                promos.clear()
                ds.children.forEach{ promosSnapshot->
                    val promo = promosSnapshot.getValue(Promo::class.java)
                    promo?.let {
                        var placeName = getArguments()!!.getString("placeName")
                        if (promo.PromoParent == placeName) {
                            promos.add(promo)
                        }

                    }
                }
                adapter.notifyDataSetChanged()
            }
        })

    }
}