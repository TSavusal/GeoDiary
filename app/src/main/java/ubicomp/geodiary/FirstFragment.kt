package ubicomp.geodiary

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_first.*
import java.util.concurrent.TimeUnit

/**
 * A [Fragment] subclass as the main view and navigation point.
 */

class FirstFragment : Fragment() {

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

    //Populate the views after the layout has been inflated
    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_get_location).setOnClickListener {
            //Toast.makeText(activity, "Fetching location!", Toast.LENGTH_SHORT).show()
            (activity as MainActivity).getLastLocation()
            //Toast.makeText(activity, "Location fetched!", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<Button>(R.id.button_add_entry).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        view.findViewById<Button>(R.id.button_entrylist).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment)
        }
        view.findViewById<Button>(R.id.button_exit).setOnClickListener {
            System.exit(0);
        }
    }
}
