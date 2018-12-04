package apps.softtek.com.turisteando.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import apps.softtek.com.turisteando.R
import apps.softtek.com.turisteando.models.Promo

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

class PromoAdapter (
        var context: Context,
        var promos: List<Promo>) : RecyclerView.Adapter<PromoAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: PromoAdapter.ViewHolder, position: Int) {
        holder.bindItems(promos[position])
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.promo_item, parent, false)
        return PromoAdapter.ViewHolder(v)

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return promos.size
    }

    //the class is hodling the list view
    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(
                promo: Promo
        ) {
            val promoTitle = itemView.findViewById<TextView>(R.id.promo_title)
            val promoDescription = itemView.findViewById<TextView>(R.id.promo_description)

            promoTitle.text = promo.PromoTitulo
            promoDescription.text = promo.PromoDescripcion

        }
    }
}