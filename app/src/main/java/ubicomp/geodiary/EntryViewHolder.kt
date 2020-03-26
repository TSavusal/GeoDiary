package ubicomp.geodiary

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntryViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {
    private var mEntryNameView: TextView? = null
    private var mEntryDateView: TextView? = null
    private var mEntryAddressView: TextView? = null
    private var mEntryBodyView: TextView? = null


    init {
        mEntryNameView = itemView.findViewById(R.id.list_title)
        mEntryDateView = itemView.findViewById(R.id.list_date)
        mEntryAddressView = itemView.findViewById(R.id.list_address)
        mEntryBodyView = itemView.findViewById(R.id.list_description)
    }

    fun bind(Listitementry: Listitementry) {
        mEntryNameView?.text = Listitementry.entryname.toString()
        mEntryDateView?.text = Listitementry.entrydate.toString()
        mEntryAddressView?.text = Listitementry.entryaddress.toString()
        mEntryBodyView?.text = Listitementry.entrybody.toString()
    }

}