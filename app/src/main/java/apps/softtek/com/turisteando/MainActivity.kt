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
