package ubicomp.geodiary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_speech_recognition.*
import java.util.*

//Todo: Implement "voice input , where the user can speak the items in the diary and then the voice will be converted to text."
//todo: implement speechrecognition to make journal entries

class SpeechRecognition : AppCompatActivity() {

    val RESULT_ID = 12345

    //todo: Fix xml fetch problem (may not be necessary if xml is not used)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_speech_recognition)

        val listen = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        listen.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        listen.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US)
        listen.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        listen.putExtra(RecognizerIntent.EXTRA_PROMPT, "I'm listening...")
        startActivityForResult(listen, RESULT_ID)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_ID) {
            if (resultCode == Activity.RESULT_OK) {
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                    output.text = results?.get(0)
                }
            }
        }
    }
}