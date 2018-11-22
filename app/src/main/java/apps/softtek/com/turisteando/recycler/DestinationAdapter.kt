package apps.softtek.com.turisteando.recycler

import android.animation.ObjectAnimator
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
import apps.softtek.com.turisteando.MainActivity
import apps.softtek.com.turisteando.R
import apps.softtek.com.turisteando.fragment.PlaceFragment
import apps.softtek.com.turisteando.models.App
import apps.softtek.com.turisteando.models.Destination
import com.google.android.material.card.MaterialCardView

class DestinationAdapter (var context: Context,
                  var destinations: List<Destination>) : RecyclerView.Adapter<DestinationAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.destination_item, parent, false)
        return ViewHolder(v)

    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(destinations[position])

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return destinations.size
    }

    //the class is hodling the list view
    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(destination: Destination) {
            val destinationName = itemView.findViewById<TextView>(R.id.destination_name)
            val destinationRoot = itemView.findViewById<ConstraintLayout>(R.id.destination_root)
            val destinationCard = itemView.findViewById<MaterialCardView>(R.id.destination_card)
            val selectedDestinationDescription = itemView.findViewById<TextView>(R.id.destination_selected_description)
            val destinationMinimize = itemView.findViewById<CardView>(R.id.destination_minimize)

            destinationName.text = destination.DestinationName

            destinationCard.setOnClickListener(){
                val animator = ObjectAnimator.ofFloat(destinationCard, "cardElevation", 0f, 35f)
                val constraintSet = ConstraintSet()
                constraintSet.clone(destinationRoot)
                val transition = ChangeBounds()
                //val recolor = Recolor()
                transition.setInterpolator(AccelerateDecelerateInterpolator())
                transition.setDuration(100)
                TransitionManager.beginDelayedTransition(destinationRoot, transition)
                //com.transitionseverywhere.TransitionManager.beginDelayedTransition(destinationCard, recolor)
                destinationCard.setCardBackgroundColor(itemView.context!!.getResources().getColor(R.color.primaryColor));
                destinationName.setTextColor(itemView.context!!.getResources().getColor(R.color.whiteColor))
                selectedDestinationDescription.setVisibility(View.VISIBLE)
                selectedDestinationDescription.text = destination.DestinationDescription
                destinationMinimize.setVisibility(View.VISIBLE)
                constraintSet.applyTo(destinationRoot)
                animator.start()

                var selection = destinationName.text.toString()
                App.currentSelectionn = selection

                App.updatePlaces()

            }

            destinationMinimize.setOnClickListener(){
                /*
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
                destinationMinimize.setVisibility(View.GONE)
                constraintSet.applyTo(destinationRoot)
                animator.start()
                */
            }

        }
    }

}

