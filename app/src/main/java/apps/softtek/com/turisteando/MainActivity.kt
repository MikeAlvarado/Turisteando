package apps.softtek.com.turisteando

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import apps.softtek.com.turisteando.fragment.AgendaFragment
import apps.softtek.com.turisteando.fragment.DestinationFragment
import apps.softtek.com.turisteando.fragment.PlaceFragment
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Add / replace fragment in container
    private fun addDestinationsFragment(fragment: Fragment){
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.destination_container, fragment, fragment.javaClass.simpleName)
                .commit()
    }

    private fun addPlacesFragment(fragment: Fragment){
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.place_container, fragment, fragment.javaClass.simpleName)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        val destinationFragment = DestinationFragment.newInstance()
        addDestinationsFragment(destinationFragment)

        val placeFragment = PlaceFragment.newInstance()
        addPlacesFragment(placeFragment)

        val layout = findViewById(R.id.root) as ViewGroup
        val layoutTransition = layout.layoutTransition
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        showAgendaOnClick()

    }

    private fun showAgendaOnClick() {
        // Fragment OnClick Listener
        agenda_button.setOnClickListener{
            // Load BottomsheetFragment
            val agendaFragment = AgendaFragment()
            agendaFragment.show(supportFragmentManager, agendaFragment.tag)
        }
    }


}
