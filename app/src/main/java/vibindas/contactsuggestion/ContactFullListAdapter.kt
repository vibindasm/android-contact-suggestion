package vibindas.contactsuggestion

import android.content.ContentUris
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import vibindas.contactsuggestion.databinding.RowFullContactListItemBinding
import java.io.ByteArrayInputStream
import java.io.InputStream


class ContactFullListAdapter(val context: Context, val items: ArrayList<Contact>) : RecyclerView.Adapter<ContactListHolder>(), Filterable {

    var tempItems = ArrayList<Contact>()
    lateinit var listener: OnItemClickListener
//    var highlightPosition: Int = 0

    override fun onBindViewHolder(holder: ContactListHolder, position: Int) {

        val contactItem = items[position]
//        if(position == highlightPosition) {
//            holder.binding.layoutPaynowReg.setBackgroundResource(R.drawable.background_card_border)
//        } else {
//            holder.binding.layoutPaynowReg.setBackgroundColor(android.R.attr.selectableItemBackground)
//        }

        holder.bind(contactItem)
        val thumbNail = openPhoto(contactItem.id.toLong())
        if (thumbNail != null) {
            holder.binding.contactIcon.visibility = View.VISIBLE
            holder.binding.contactShortName.visibility = View.GONE
            Glide.with(context)
                    .load(BitmapFactory.decodeStream(thumbNail))
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.binding.contactIcon)
        } else {
            holder.binding.contactIcon.visibility = View.GONE
            holder.binding.contactShortName.visibility = View.VISIBLE
        }
        holder.binding.contactItem.setOnClickListener {
            listener.onClick(it, contactItem)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListHolder {
        tempItems.addAll(items)
        val binding = DataBindingUtil.inflate<RowFullContactListItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.row_full_contact_list_item,
                parent,
                false
        )


        return ContactListHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                val filterResults = Filter.FilterResults()
                if (constraint != null) {
                    items?.clear()
                    tempItems?.forEach {
                        if (it.name.toLowerCase().contains(constraint.toString().toLowerCase()) ||
                                it.number.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            items?.add(it)
                        }
                    }
                    filterResults.values = items
                    filterResults.count = items!!.size
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

    interface OnItemClickListener {
        fun onClick(view: View, data: Contact)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun openPhoto(contactId: Long): InputStream? {
        val contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId)
        val photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY)
        val cursor = context.contentResolver.query(photoUri,
                arrayOf(ContactsContract.Contacts.Photo.PHOTO), null, null, null) ?: return null
        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val data = cursor.getBlob(0)
                if (data != null) {
                    cursor.close()
                    return ByteArrayInputStream(data)
                }
            }
        }
        return null
    }
}

class ContactListHolder(val binding: RowFullContactListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Contact) {
        binding.contact = data
        binding.executePendingBindings()
    }
}