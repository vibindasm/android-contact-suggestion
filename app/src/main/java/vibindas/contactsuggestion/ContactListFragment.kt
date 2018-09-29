package vibindas.contactsuggestion

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.contact_list_fragment.*


class ContactListFragment : Fragment() {

    companion object {
        fun newInstance() = ContactListFragment()
    }

    private lateinit var viewModel: ContactListViewModel
    private lateinit var mActivity: ContactPickerActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.contact_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as ContactPickerActivity
        viewModel = ViewModelProviders.of(this).get(ContactListViewModel::class.java)
        setRecyclerAccountList(recyclerAllContact, mActivity.contactList, getString(R.string.all_contacts))
        setRecyclerAccountList(recyclerRecentContact, Contact.getResendContacts(mActivity.contactList, mActivity.resendNumbers),
                getString(R.string.recent_contacts))
    }


    private fun setRecyclerAccountList(recyclerContact: RecyclerView, contactList: ArrayList<Contact>, header: String) {
        recyclerContact.layoutManager = LinearLayoutManager(mActivity)

        val adapter = ContactFullListAdapter(mActivity, contactList, header)
        adapter.setOnItemClickListener(object : ContactFullListAdapter.OnItemClickListener {
            override fun onClick(view: View, data: Contact) {
                viewModel.selectedContact(data)
                mActivity.onSupportNavigateUp()
            }
        })

        val itemDecor = ContactDividerItemDecoration(mActivity, VERTICAL, 72)
        recyclerContact.addItemDecoration(itemDecor)
        recyclerContact.adapter = adapter


        //set filter
        searchEt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                adapter.filter.filter(s)
            }
        })
    }
}
