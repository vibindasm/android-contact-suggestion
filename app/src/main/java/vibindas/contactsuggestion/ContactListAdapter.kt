package vibindas.contactsuggestion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_contact_list_item.view.*


class ContactListAdapter(val items: List<Contact>?, val context: Context) : RecyclerView.Adapter<ViewHolder>(), Filterable {

    var suggestions: ArrayList<Contact>? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = items?.get(position)?.name
        holder.tvNumber.text = items?.get(position)?.number
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_contact_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items!!.size
    }


    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                val filterResults = Filter.FilterResults()
                if (constraint != null) {
                    suggestions?.clear()
                    items?.forEach {
                        if (it.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            suggestions?.add(it)
                        }
                    }
                    filterResults.values = suggestions
                    filterResults.count = suggestions!!.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?,
                                        results: Filter.FilterResults?) = when {
                results?.count ?: -1 > 0 -> notifyDataSetChanged()
                else -> notifyDataSetChanged()
            }
        }
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvName = view.name!!
    val tvNumber = view.number!!
}