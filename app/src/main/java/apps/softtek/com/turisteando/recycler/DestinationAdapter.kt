package apps.softtek.com.turisteando.recycler

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import apps.softtek.com.turisteando.DestinationBus
import apps.softtek.com.turisteando.MainActivity
import apps.softtek.com.turisteando.R
import apps.softtek.com.turisteando.fragment.PlaceFragment
import apps.softtek.com.turisteando.models.App
import apps.softtek.com.turisteando.models.Destination
import com.google.android.material.card.MaterialCardView

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

class DestinationAdapter (
        var context: Context,
        var destinations: List<Destination>,
        var destinationClickListener: OnDestinationSelected,
        var destinationBus: DestinationBus) : RecyclerView.Adapter<DestinationAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.destination_item, parent, false)
        return ViewHolder(v)

    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(destinations[position], destinationClickListener, destinationBus)

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return destinations.size
    }

    interface OnDestinationSelected {
        fun onSelected(destination: String)
    }

    //the class is hodling the list view
    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("CheckResult")
        fun bindItems(
                destination: Destination,
                listener: OnDestinationSelected,
                bus: DestinationBus
        ) {
            val destinationName = itemView.findViewById<TextView>(R.id.destination_name)
            val destinationRoot = itemView.findViewById<ConstraintLayout>(R.id.destination_root)
            val destinationCard = itemView.findViewById<MaterialCardView>(R.id.destination_card)
            val selectedDestinationDescription = itemView.findViewById<TextView>(R.id.destination_selected_description)

            destinationName.text = destination.DestinationName

            destinationCard.setOnClickListener(){
                val animator = ObjectAnimator.ofFloat(destinationCard, "cardElevation", 0f, 35f)
                val constraintSet = ConstraintSet()
                constraintSet.clone(destinationRoot)
                val transition = ChangeBounds()
                transition.setInterpolator(AccelerateDecelerateInterpolator())
                transition.setDuration(100)
                TransitionManager.beginDelayedTransition(destinationRoot, transition)
                destinationCard.setCardBackgroundColor(itemView.context!!.getResources().getColor(R.color.primaryColor));
                destinationName.setTextColor(itemView.context!!.getResources().getColor(R.color.whiteColor))
                selectedDestinationDescription.setVisibility(View.VISIBLE)
                selectedDestinationDescription.text = destination.DestinationDescription
                constraintSet.applyTo(destinationRoot)
                animator.start()

                bus.setLatestDestination(destination.DestinationId)

            }

            bus.getChanges()
            .subscribe { id ->
                if (id != destination.DestinationId) {
                    val animator = ObjectAnimator.ofFloat(destinationCard, "cardElevation", 35f, 0f)
                    val constraintSet = ConstraintSet()
                    constraintSet.clone(destinationRoot)
                    val transition = ChangeBounds()
                    //val recolor = Recolor()
                    transition.setInterpolator(AccelerateDecelerateInterpolator())
                    transition.setDuration(100)
                    TransitionManager.beginDelayedTransition(destinationRoot, transition)
                    //com.transitionseverywhere.TransitionManager.beginDelayedTransition(destinationCard, recolor)
                    destinationCard.setCardBackgroundColor(itemView.context!!.getResources().getColor(R.color.whiteColor));
                    destinationName.setTextColor(itemView.context!!.getResources().getColor(R.color.primaryColor))
                    selectedDestinationDescription.setVisibility(View.GONE)
                    constraintSet.applyTo(destinationRoot)
                    animator.start()
                }
            }

        }
    }

}

