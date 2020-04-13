package ubicomp.geodiary

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import kotlinx.android.synthetic.main.fragment_second.*
import org.jetbrains.anko.doAsync
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
        ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Todo: Implement text save
        view.findViewById<EditText>(R.id.EditEntry)
        //Todo: implement save_input that saves text + address from latitude & longitude
        view.findViewById<Button>(R.id.button_save_input).setOnClickListener {
            val geocoder: Geocoder
            val latitude = 0.0
            val longitude = 0.0
            val addresses: List<Address>
            geocoder = Geocoder(getContext(), Locale.getDefault())
            // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            addresses = geocoder.getFromLocation(
                latitude, longitude, 1
            )
            // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            val address: String = addresses[0].getAddressLine(0)
            val city: String = addresses[0].getLocality()
            val state: String = addresses[0].getAdminArea()
            val country: String = addresses[0].getCountryName()
            val postalCode: String = addresses[0].getPostalCode()
            val knownName: String = addresses[0].getFeatureName() // Only if available else return NULL

            val EntryText = EditEntry.text
        }

        view.findViewById<Button>(R.id.button_back).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    val entry = EntryEntity
    private fun updateList() {
        doAsync {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "entry-list.db"
            ).build()

            db.entryDao().insert(entry)
            db.close()
        }
    }

}
