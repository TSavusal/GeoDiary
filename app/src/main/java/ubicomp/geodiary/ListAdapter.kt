package ubicomp.geodiary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private val list: List<Listitementry>)
    : RecyclerView.Adapter<EntryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EntryViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry: Listitementry = list[position]
        holder.bind(entry)
    }

    override fun getItemCount(): Int = list.size

}