package apps.softtek.com.turisteando.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import apps.softtek.com.turisteando.R
import apps.softtek.com.turisteando.models.Promo


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