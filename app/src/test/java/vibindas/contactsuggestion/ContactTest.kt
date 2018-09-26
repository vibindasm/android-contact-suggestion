package vibindas.contactsuggestion

import org.junit.Assert
import org.junit.Test
import vibindas.contactsuggestion.Contact

class ContactTest {
    @Test
    fun getShortNameTest() {
        Assert.assertEquals("VM", Contact.getShortName("Vibindas M"))
        Assert.assertEquals("Vi", Contact.getShortName("Vibindas"))
        Assert.assertEquals("V", Contact.getShortName("V"))
        Assert.assertEquals("", Contact.getShortName(""))
        Assert.assertEquals("Vd", Contact.getShortName("Vibin das m"))
    }
}