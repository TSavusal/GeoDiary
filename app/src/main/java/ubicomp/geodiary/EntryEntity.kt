package ubicomp.geodiary

import androidx.room.*

@Entity(tableName = "entry_items")
data class EntryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "entry_title") var entry_title: String,
    @ColumnInfo(name = "address") var address: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "entry_text") var entry_text: String
)