package apps.softtek.com.turisteando.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import apps.softtek.com.turisteando.R
import apps.softtek.com.turisteando.models.App
import apps.softtek.com.turisteando.models.Destination
import apps.softtek.com.turisteando.models.Place
import apps.softtek.com.turisteando.recycler.DestinationAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DestinationFragment : Fragment() {
    companion object {
        fun newInstance(): DestinationFragment {
            var fragmentDestination = DestinationFragment()
            var args = Bundle()
            fragmentDestination.arguments = args
            return fragmentDestination
        }

    }

    private val destinations = ArrayList<Destination>()

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
        val recyclerView = view.findViewById<RecyclerView>(R.id.destination_recycler)

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(context!!, RecyclerView.HORIZONTAL, false)

        val bus = (requireContext().applicationContext as App).getDestinationBus()
        val destinationSelectedListener = object : DestinationAdapter.OnDestinationSelected {
            override fun onSelected(destination: String) {

            }
        }

        val adapter = DestinationAdapter(context!!,destinations, destinationSelectedListener, bus)
        recyclerView.adapter = adapter

        var connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected")
        connectedRef.addValueEventListener(object: ValueEventListener {


            override fun onCancelled(e: DatabaseError) {
                Log.d(DestinationFragment::class.java.simpleName, "${e.message} - ${e.toException()}")
            }

            override fun onDataChange(ds: DataSnapshot) {
                var connected : Boolean = ds.getValue() as Boolean
                if (connected) {
                    Toast.makeText(context,"Connected to the Database",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context,"Connecting to Database",Toast.LENGTH_SHORT).show()
                }
            }

        })

        //Instantiation of the Database
        FirebaseDatabase.getInstance().reference.child("Destino").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(e: DatabaseError) {
                Log.d(DestinationFragment::class.java.simpleName, "${e.message} - ${e.toException()}")
            }

            override fun onDataChange(ds: DataSnapshot) {
                destinations.clear()
                ds.children.forEach{ destinoSnapshot->
                    val destino = destinoSnapshot.getValue(Destination::class.java)
                    destino?.let { destinations.add(destino) }
                }
                adapter.notifyDataSetChanged()
            }
        })
    }

}
