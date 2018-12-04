package apps.softtek.com.turisteando.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import apps.softtek.com.turisteando.R
import apps.softtek.com.turisteando.models.Place
import apps.softtek.com.turisteando.models.Promo
import apps.softtek.com.turisteando.recycler.PromoAdapter
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_place_detail.*

/*
Turisteando

Objetivo
El objetivo del proyecto es implementar los conocimientos aprendidos en el curso de Proyecto de Aplicaciones Móviles en Kotlin, brindando al cliente un producto de valor.

Descripción
El cliente, tiene como objetivo el realizar una aplicación que te permita visualizar destinos que contengan lugares con promociones para así mismo "vender una experiencia" al momento de viajar/utilizar sus servicios.
Copyright (C) 2018 - ITESM

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

class PlaceDetailFragment: BottomSheetDialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_place_detail, container, false)
    }

    val promos = ArrayList<Promo>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var placeName = getArguments()!!.getString("placeName")
        var placeDescription = getArguments()!!.getString("placeDescription")
        var placePhoto = getArguments()!!.getString("placePhoto")
        val placeImageView = view!!.findViewById<ImageView>(R.id.place_image)


        place_name.text = placeName
        place_description.text = placeDescription
        Glide.with(context!!).load(placePhoto).into(placeImageView)


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

        promo_call_button.setOnClickListener{
            Toast.makeText(context, "Disabled", Toast.LENGTH_SHORT).show()
        }

        promo_agenda_button.setOnClickListener{
            Toast.makeText(context, "Disabled", Toast.LENGTH_SHORT).show()
        }

    }
}