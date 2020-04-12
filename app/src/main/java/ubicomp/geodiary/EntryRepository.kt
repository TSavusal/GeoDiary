package ubicomp.geodiary

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class EntryRepository(application: Application?) {
    private var mEntryDao: EntryDao? = null
    private var mAllEntries: MutableLiveData<List<EntryEntity>>? = null

    fun EntryRepository(application: Application?) {
        val db: AppDatabase = AppDatabase.getDatabase(application)
        mEntryDao = db.EntryDao()
        mAllEntries = mEntryDao!!.getAllEntries()
    }

    fun getAllEntries(): MutableLiveData<List<EntryEntity>>? {
        return mAllEntries
    }

    fun insert(word: EntryEntity?) {
        insertAsyncTask(mEntryDao).execute(word)
    }

    private class insertAsyncTask internal constructor(dao: EntryDao?) :
        AsyncTask<EntryEntity?, Void?, Void?>() {
        private val mAsyncTaskDao: EntryDao?
        override fun doInBackground(vararg params: EntryEntity?): Void? {
            mAsyncTaskDao?.insert(params[0])
            return null
        }

        init {
            mAsyncTaskDao = dao
        }
    }
}