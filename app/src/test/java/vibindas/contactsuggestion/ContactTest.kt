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
        resendNumber.add("98769877")
        resendNumber.add("87654981")
        resendNumber.add("87654982")
        resendNumber.add("87654983")
        resendNumber.add("87654984")
        contactList.add(Contact("1", "abc", "98798709"))
        contactList.add(Contact("2", "xyz", "98769877"))
        contactList.add(Contact("3", "qw1", "87654981"))
        contactList.add(Contact("4", "qw2", "87654982"))
        contactList.add(Contact("5", "qw3", "87654983"))
        contactList.add(Contact("6", "qw4", "87654984"))
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
        //Test the filter
        Assert.assertEquals(contactList[5], Contact.getResendContacts(contactList, resendNumber)[0])
        //Test the order
        Assert.assertEquals(resendNumber[0], Contact.getResendContacts(contactList, resendNumber)[0].number)

    }

    @Test
    fun getFormattedNumberTest() {
        Assert.assertEquals("+6598798798", Contact.getFormattedNumber("+65 987 987 98"))
        Assert.assertEquals("90123565", Contact.getFormattedNumber("90-123-565"))
        Assert.assertEquals("9012356", Contact.getFormattedNumber("9012356"))
    }
}