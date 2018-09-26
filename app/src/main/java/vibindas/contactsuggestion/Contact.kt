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
    }

    val shortName: String = getShortName(name)
}