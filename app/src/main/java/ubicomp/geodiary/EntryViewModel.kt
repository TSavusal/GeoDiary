package ubicomp.geodiary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class EntryViewModel(application: Application?) : AndroidViewModel(application!!) {
    private val mRepository: EntryRepository = EntryRepository(application)
    private val mAllEntries: MutableLiveData<List<EntryEntity>>?
    val allEntries: MutableLiveData<List<EntryEntity>>?
        get() = mAllEntries

    fun insert(entry: EntryEntity?) {
        mRepository.insert(entry)
    }

    init {
        mAllEntries = mRepository.getAllEntries()
    }
}