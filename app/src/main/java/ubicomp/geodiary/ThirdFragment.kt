package ubicomp.geodiary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_third.*

data class Listitementry(val entryname: String, val entryaddress: String, val entrydate: String, val entrybody: String)

class ThirdFragment : Fragment() {

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
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_third, container, false)

    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView node initialized here
        list_recycler_view.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            this.layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            this.adapter = ListAdapter(EntriesList)
        }
        view.findViewById<Button>(R.id.button_back2).setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
        }
    }

    companion object {
        fun newInstance(): ThirdFragment = ThirdFragment()
    }
}
