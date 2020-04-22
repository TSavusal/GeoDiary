package ubicomp.geodiary

import androidx.room.*

@Entity(tableName = "entry_items")
data class EntryEntity(
    @PrimaryKey(autoGenerate = true) var eid: Int,
    @ColumnInfo(name = "address") var address: String,
    @ColumnInfo(name = "entry_text") var entry_text: String
)