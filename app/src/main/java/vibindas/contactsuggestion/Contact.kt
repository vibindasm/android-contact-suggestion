package vibindas.contactsuggestion

data class Contact(var id: String, var name: String, var number: String) {
    companion object {
        fun getShortName(name: String): String {
            var shortName = ""
            val splitName = name.split(" ")

            if(splitName.size == 1 && splitName[0].length < 2) return splitName[0]

            if(splitName.size == 1 && splitName[0].length > 1) return splitName[0].substring(0,2)

            splitName.forEach {
                shortName += it[0]
                if(shortName.length > 1)return shortName
            }
            return shortName
        }
        fun getResendContacts(contactList: ArrayList<Contact>, resendNumber: ArrayList<String>):ArrayList<Contact> {
            val resendContacts = ArrayList<Contact>()
            resendContacts.clear()
            contactList.forEach {
                if (resendNumber.contains(it.number)) {
                    resendContacts.add(it)
                }
            }
            return resendContacts
        }
    }

    val shortName: String = getShortName(name)

}