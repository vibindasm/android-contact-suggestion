package vibindas.contactsuggestion

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.contact_picker_fragment.*

class ContactPickerFragment : Fragment() {

    companion object {
        fun newInstance() = ContactPickerFragment()
    }

    private lateinit var viewModel: ContactPickerViewModel
    private lateinit var mActivity: ContactPickerActivity
    private lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.contact_picker_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as ContactPickerActivity
        viewModel = ViewModelProviders.of(this).get(ContactPickerViewModel::class.java)
        bindUiToBehavior()
    }

    private fun bindUiToBehavior() {

        val adapter = ContactAdapter(mActivity, R.layout.activity_main, R.id.autoCompleteText, mActivity.contactList)
        mobileNumberEt.setAdapter(adapter)

        mobileNumberEt.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, pos, id ->
            //this is the way to find selected object/item
            mActivity.selectedContact = adapterView.getItemAtPosition(pos) as Contact
            Log.d("Contact", "Selected Item $mActivity.contactList[$pos]")
        }


        contactBt.setOnClickListener {
            it.findNavController().navigate(R.id.action_contactPickerFragment_to_contactListFragment)
        }
    }

}
