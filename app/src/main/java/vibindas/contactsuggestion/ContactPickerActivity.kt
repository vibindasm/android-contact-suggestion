package vibindas.contactsuggestion

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment


class ContactPickerActivity : AppCompatActivity() {

    var contactList: ArrayList<Contact> = ArrayList()
    var resendNumbers: ArrayList<String> = ArrayList()
    private val PERMISSIONS_REQUEST_READ_CONTACTS = 101
    var selectedContact: Contact? = null

    private lateinit var mNavHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_picker_activity)
        loadContacts()
        mNavHostFragment = supportFragmentManager.findFragmentById(R.id.contactFragment) as NavHostFragment
    }

    private fun loadContacts() {

        if (checkSelfPermission(
                        Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),
                    PERMISSIONS_REQUEST_READ_CONTACTS)

        } else {
            contactList = getContacts()
            resendNumbers = getResend()
        }
    }

   private fun getResend(): ArrayList<String> {
        var numbers = ArrayList<String>()
        numbers.add("84217691")
        return numbers
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts()
            } else {
                //  toast("Permission must be granted in order to display contacts information")
            }
        }
    }

    override fun onSupportNavigateUp() =
            mNavHostFragment.navController.navigateUp()

    private fun getContacts(): ArrayList<Contact> {

        val contactList: ArrayList<Contact> = ArrayList()
        val resolver: ContentResolver = contentResolver
        val cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
                null)

        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))


                val phoneNumber = (cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()

                if (phoneNumber > 0) {
                    val cursorPhone = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                    if (cursorPhone != null && cursorPhone.count > 0) {
                        while (cursorPhone.moveToNext()) {
                            val phoneNumValue = cursorPhone.getString(
                                    cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                            if(validateContacts(phoneNumValue))
                                contactList.add(Contact(id, name, phoneNumValue))
                        }
                    }
                    cursorPhone?.close()
                }
            }
        } else {
            //   toast("No contacts available!")
        }
        cursor?.close()
        return contactList
    }

    private fun validateContacts(phoneNumValue: String): Boolean {
        return phoneNumValue.length >= 8
    }
}
