package ubicomp.geodiary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

data class Listitementry(val entryname: String, val entryaddress: String, val entrydate: String, val entrybody: String)

class FirstFragment : Fragment() {

    private val EntriesList = listOf(
        Listitementry("Entry 1", "Hallituskatu 1", "01.02.2020", "This is the first entry in the GeoDiary" ),
        Listitementry("Entry 2", "Ojatie 15", "05.02.2020", "There is a great cafe here" ),
        Listitementry("Entry 3", "Varpusentie 8", "06.02.2020", "The bar at this location serves free chicken wings" ),
        Listitementry("Entry 4", "Haukankatu 3", "12.02.2020", "A dog attacked me here, it hurt" ),
        Listitementry("Entry 5", "Isokatu 4", "13.02.2020", "This is the fifth entry in the GeoDiary" ),
        Listitementry("Entry 6", "Aleksanterinkatu 15", "20.02.2020", "All the shops were closed here when I was here" )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ListAdapter(EntriesList)
        }
        view.findViewById<Button>(R.id.button_add_entry).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    companion object {
        fun newInstance(): FirstFragment = FirstFragment()
    }
}

