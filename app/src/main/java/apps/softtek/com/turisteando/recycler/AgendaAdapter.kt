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
