package ubicomp.geodiary

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import kotlinx.android.synthetic.main.fragment_second.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.toast
import java.util.*

/**
 *SecondFragment is a [Fragment] subclass for the text input
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
        //intent to show SpeechtoText dialog
        val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak your entry")

        try {
            //if there is no error show SpeechToText dialog
            startActivityForResult(mIntent, REQUEST_CODE_SPEECH_INPUT)
        }
        catch (e: Exception) {
            //if there is any error get error message and show in toast
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

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

        //button click to:
        //1. record voice to text entry
        //2. get geographical location
        voiceBtn.setOnClickListener{
            speak()
            (activity as MainActivity).getLastLocation()
        }

        button_save_input.setOnClickListener{
            //Toast.makeText(this, "Entry saved!", Toast.LENGTH_LONG).show()
            Toast.makeText((activity as MainActivity), "Entry Saved!", Toast.LENGTH_LONG).show()
        }

        view.findViewById<Button>(R.id.button_back).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)

        //val EntryText = view.findViewById<EditText>(R.id.EditEntry) as EditText
        //Toast.makeText(activity, EntryText.toString(), Toast.LENGTH_SHORT).show()

        //Todo: (optional) implement save_input that saves text + address from latitude & longitude
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
        }
        }*/
        }
    }
    
    companion object {
        private var instance: MainActivity? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    val entry = EntryEntity(address = "address", eid = 123, entry_text = "entry_text")
    private fun updateList() {
        doAsync {
            val db = Room.databaseBuilder(applicationContext(), AppDatabase::class.java, "entry-items").build()

            db.entryDao().insert(entry)
            db.close()
        }
    }
}
/** @Database(entities = arrayOf(UserEntity::class), version = 1)
abstract class UserDb : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: UserDb? = null

        fun getInstance(context: Context): UserDb? {
            if (INSTANCE == null) {
                synchronized(UserDb::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, UserDb::class.java, "user.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
 */
