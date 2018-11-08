package apps.softtek.com.turisteando.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import apps.softtek.com.turisteando.R
import apps.softtek.com.turisteando.models.Destination
import apps.softtek.com.turisteando.recycler.DestinationAdapter
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import android.R.attr.order
import android.animation.ObjectAnimator
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.destination_item.*
import kotlinx.android.synthetic.main.fragment_destination.*


class DestinationFragment : Fragment() {
    companion object {
        fun newInstance(): DestinationFragment {
            var fragmentHome = DestinationFragment()
            var args = Bundle()
            fragmentHome.arguments = args
            return fragmentHome
        }

    }

    val destinations = ArrayList<Destination>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    interface ClickListener {
        fun onClick(view: View, position: Int)

        fun onLongClick(view: View?, position: Int)
    }

    internal class RecyclerTouchListener(context: Context?, recyclerView: RecyclerView, private val clickListener: ClickListener?) : RecyclerView.OnItemTouchListener {

        private val gestureDetector: GestureDetector

        init {
            gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val child = recyclerView.findChildViewUnder(e.x, e.y)
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child))
                    }
                }
            })
        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {

            val child = rv.findChildViewUnder(e.x, e.y)
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child))

            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.fragment_destination, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //getting recyclerview from xml
        val recyclerView = view?.findViewById<RecyclerView>(R.id.destination_recycler)

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        //adding some dummy data to the list
        destinations.add(Destination("Nuevo León","Lorem Ipsum", "www.photo.com", 3,"NL"))
        destinations.add(Destination("Guadalajara","Lorem Ipsum", "www.photo.com", 3,"NL"))
        destinations.add(Destination("CDMX","Lorem Ipsum", "www.photo.com", 3,"NL"))
        destinations.add(Destination("Puebla","Lorem Ipsum", "www.photo.com", 3,"NL"))

        val adapter = DestinationAdapter(context!!,destinations)

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        //added touchListener to Recycler
        recyclerView.addOnItemTouchListener(RecyclerTouchListener(this!!.context, recyclerView, object : ClickListener {
            override fun onClick(view: View, position: Int) {

            }

            override fun onLongClick(view: View?, position: Int) {

            }
        }))

    }

}
