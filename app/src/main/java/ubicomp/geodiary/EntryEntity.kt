package ubicomp.geodiary

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry_table")
data class EntryEntity (
    @NonNull
    @field:PrimaryKey(autoGenerate = true) protected var id: Int,
    @field:ColumnInfo(name = "address") val address: String,
    @field:ColumnInfo(name = "date") val date: String,
    @field:ColumnInfo(name = "entry_title") val entry_title: String,
    @field:ColumnInfo(name = "entry_text") val entry_text: String
) {
    var title: String? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is EntryEntity) return false
        val title: Any? = o
        if (id != this.id) return false
        return if (title != null) title == this.title else {
            this.title == null
        }
    }

    override fun hashCode(): Int {
        var result: Int = id
        result = 31 * result + if (title != null) title.hashCode() else 0
        return result
    }

    override fun toString(): String {
        return "Entry{" +
                "id=" + id +
                ", entry_text='" + entry_text + '\'' +
                ", entry_title='" + entry_title + '\'' +
        '}'
    }
}