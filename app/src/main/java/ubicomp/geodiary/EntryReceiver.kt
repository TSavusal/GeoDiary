package ubicomp.geodiary

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.room.Room

class EntryReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        val uid = intent?.getIntExtra("uid", 0)
        val text = intent?.getStringExtra( "entrytext")

        //MainActivity.showNotification(context,text)

        val db = Room.databaseBuilder(context, AppDatabase::class.java, "reminders")
            .build()
        if (uid != null) {
            db.EntryDao().delete(uid)
        }
        db.close()

    }
}