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
import apps.softtek.com.turisteando.recycler.AgendaAdapter
import apps.softtek.com.turisteando.recycler.PromoAdapter
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


class AgendaFragment: BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_agenda, container, false)
    }

    val agendated = ArrayList<Place>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //getting recyclerview from xml
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.agenda_recycler)

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager?

        val agendaListener = object : AgendaAdapter.OnAgendaSelected {
            override fun onSelected(placeName: String, placeDescription: String) {
                // HERE UPDATE

            }

        }

        val adapter = AgendaAdapter(context!!,agendated, agendaListener)
        recyclerView.adapter = adapter

        //Instantiation of the Database
        FirebaseDatabase.getInstance().reference.child("Lugares").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(e: DatabaseError) {
                Log.d(AgendaFragment::class.java.simpleName, "${e.message} - ${e.toException()}")
            }

            override fun onDataChange(ds: DataSnapshot) {
                agendated.clear()
                ds.children.forEach{ promosSnapshot->
                    val agenda = promosSnapshot.getValue(Place::class.java)
                    agenda?.let {
                        if (agenda.PlaceAgenda == "True") {
                            agendated.add(agenda)
                        }

                    }
                }
                adapter.notifyDataSetChanged()
            }
        })

    }
}