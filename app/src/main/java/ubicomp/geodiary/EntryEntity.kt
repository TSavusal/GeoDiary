package ubicomp.geodiary

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry_table")
data class EntryEntity(
    @NonNull
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "entry_title") val entry_title: String,
    @ColumnInfo(name = "entry_text") val entry_text: String
)