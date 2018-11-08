package apps.softtek.com.turisteando

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import apps.softtek.com.turisteando.fragment.AgendaFragment
import apps.softtek.com.turisteando.fragment.DestinationFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Add / replace fragment in container
    private fun addDestinationFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.destination_container, fragment, fragment.javaClass.getSimpleName())
                .addToBackStack(fragment.javaClass.getSimpleName())
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val destinationFragment = DestinationFragment.newInstance()
        addDestinationFragment(destinationFragment)
        showAgendaOnClick()

    }

    fun showAgendaOnClick() {
        // Fragment OnClick Listener
        agenda_button.setOnClickListener{
            // Load BottomsheetFragment
            val agendaFragment = AgendaFragment()
            agendaFragment.show(supportFragmentManager, agendaFragment.tag)
        }
    }

}
