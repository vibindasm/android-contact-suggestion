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
            resendNumber.reverse()
            resendNumber.ensureCapacity(5)
            contactList.forEach {
                if (resendNumber.contains(it.number)) {
                    resendContacts.add(it)
                }
            }
            resendContacts.reverse()
            return resendContacts
        }

        fun getFormattedNumber(number: String): String {
            return number.replace(" ","").replace("-","").trim()
        }
    }

    val shortName: String = getShortName(name)
    val formattedNumber: String = getFormattedNumber(number)

}