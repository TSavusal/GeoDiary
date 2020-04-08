package ubicomp.geodiary

//TO DO: fix anko import

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.room.Room
import org.jetbrains.anko.doAsync
//import org.jetbrains.anko.toast

class EntryReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        val uid = intent?.getIntExtra("uid", 0)
        val text = intent?.getStringExtra( "entrytext")

        //MainActivity.showNotification(context,text)
        /*
        doAsync {
            val db = Room.databaseBuilder(context, AppDatabase::class.java, "reminders")
                .build()
            db.EntryDao().delete(uid)
            db.close()
        }*/
    }
}