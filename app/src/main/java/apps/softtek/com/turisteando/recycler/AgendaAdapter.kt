package apps.softtek.com.turisteando.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import apps.softtek.com.turisteando.R
import apps.softtek.com.turisteando.models.Place

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

class AgendaAdapter (var context: Context,
                     var places: List<Place>, var listener: OnAgendaSelected) : RecyclerView.Adapter<AgendaAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.agenda_item, parent, false)
        return ViewHolder(v)

    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(places[position], listener)

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return places.size
    }

    interface OnAgendaSelected {
        fun onSelected(placeName: String, placeDescription: String)
    }

    //the class is hodling the list view
    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(place: Place, listener: OnAgendaSelected) {
            val placeName = itemView.findViewById<TextView>(R.id.place_name)
            val placeDescription = itemView.findViewById<TextView>(R.id.place_description)
            val agendaButton = itemView.findViewById<ImageView>(R.id.fav_button)

            placeName.text = place.PlaceName
            placeDescription.text = place.PlaceDescription

            agendaButton.setOnClickListener {
                listener.onSelected(place.PlaceName, place.PlaceDescription)

            }

        }
    }
}
