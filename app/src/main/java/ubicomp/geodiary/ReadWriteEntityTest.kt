package ubicomp.geodiary

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(JUnit4::class)
class ReadWriteEntityTest {
    private lateinit var EntryDao: EntryDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        EntryDao = db.entryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun ReadWriteEntityTest() {
        val entry = EntryEntity(id = 1337,address = "address",date = "date",entry_title = "entry_title",entry_text ="entry_text")
        EntryDao.insertAll(entry)
        //val entryItem = EntryDao.findByTitle(entry.entry_title)
        val entryItem = EntryDao.getAllEntries()
        assertThat(entryItem, equalTo(entry))
    }
}
