package ubicomp.geodiary

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.location.Location
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import kotlinx.android.synthetic.main.fragment_second.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.intentFor
import java.util.*

/**
 *SecondFragment is the main [Fragment] subclass for the diary entry input through manual text entry and voice
 */

class SecondFragment : Fragment() {

    //private code for reference
    private val REQUEST_CODE_SPEECH_INPUT = 100

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
        ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    private fun speak() {
        //Intent to show SpeechtoText dialog
        val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak your entry")

        try {
            //If there is no error show SpeechToText dialog
            startActivityForResult(mIntent, REQUEST_CODE_SPEECH_INPUT)
        }
        catch (e: Exception) {
            //If there is any error get error message and show in toast
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            REQUEST_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && null != data){
                    //get text from speech
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    //set the text to textview
                    speech_target.text = result[0]
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var spoken: Int = 1
        val lattxt = "65.0137"
        val longtxt = "25.4717"

        //button click to:
        //1. record voice to text entry
        //2. get geographical location
        voiceBtn.setOnClickListener{
            if (spoken == 0 ) {
                speak()
            }
            else {
                spoken = 0
            }
        }

        button_save_input.setOnClickListener{
            //Fetch intent lat long, date and save EditEntry text
            val EditContent: String = EditEntry.text.toString()
            Toast.makeText((activity as MainActivity), "Entry Saved!", Toast.LENGTH_SHORT).show()
            Toast.makeText((activity as MainActivity), EditContent, Toast.LENGTH_SHORT).show()
            Toast.makeText((activity as MainActivity), "Coordinates:", Toast.LENGTH_SHORT).show()
            Toast.makeText((activity as MainActivity), longtxt + " / " + lattxt, Toast.LENGTH_SHORT).show()
            Toast.makeText((activity as MainActivity), "Date: 26.04.2020", Toast.LENGTH_SHORT).show()
            doAsync {
                val db = Room.databaseBuilder(applicationContext(), AppDatabase::class.java, "entry-items").build()
                val entry = EntryEntity(eid = 123, address = longtxt + lattxt, entry_text = EditContent)
                db.entryDao().insert(entry)
                db.close()
            }
        }

        view.findViewById<Button>(R.id.button_back).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        //Todo: (optional) implement automatic address fetch from latitude & longitude
        /**
        view.findViewById<Button>(R.id.button_save_input).setOnClickListener {
            val geocoder: Geocoder
            val latitude = 0.0
            val longitude = 0.0
            val addresses: List<Address>
            geocoder = Geocoder(getContext(), Locale.getDefault())

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
        }*/
        }

    companion object {
        private var instance: MainActivity? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}


    /*
    val entry = this.EditContent
    private fun updateList(EditContent: String) {
        doAsync {
            val db = Room.databaseBuilder(applicationContext(), AppDatabase::class.java, "entry-items").build()

            db.entryDao().insert(EditContent)
            db.close()
        }
    }*/
