package vibindas.contactsuggestion

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import vibindas.contactsuggestion.Contact

class ContactTest {

    var resendNumber = ArrayList<String>()
    var contactList = ArrayList<Contact>()
    @Before
    fun initialSetup() {
        resendNumber.add("98798709")
        contactList.add(Contact("1","abc","98798709"))
        contactList.add(Contact("2","xyz","98769877"))
        contactList.add(Contact("3","qwe","87654983"))
    }

    @Test
    fun getShortNameTest() {
        Assert.assertEquals("VM", Contact.getShortName("Vibindas M"))
        Assert.assertEquals("Vi", Contact.getShortName("Vibindas"))
        Assert.assertEquals("V", Contact.getShortName("V"))
        Assert.assertEquals("", Contact.getShortName(""))
        Assert.assertEquals("Vd", Contact.getShortName("Vibin das m"))
    }

    @Test
    fun getResendContactsTest() {
        Assert.assertEquals(contactList[0], Contact.getResendContacts(contactList, resendNumber)[0])
    }
}