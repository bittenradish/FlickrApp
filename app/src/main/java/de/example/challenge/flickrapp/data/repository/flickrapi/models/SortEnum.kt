package de.example.challenge.flickrapp.data.repository.flickrapi.models

enum class SortEnum(val sort: String, val ASC: Boolean = false) {
    DATE_POSTED_DESC("date-posted", false){
        override fun getFullString(): String {
            return "$sort-desc"
        }
    },
    DATE_POSTED_ASC("date-posted", true) {
        override fun getFullString(): String {
            return "$sort-asc"
        }
    },
    DATE_TAKEN_DESC("date-taken", false) {
        override fun getFullString(): String {
            return "$sort-desc"
        }
    },
    DATE_TAKEN_ASC("date-taken", true) {
        override fun getFullString(): String {
            return "$sort-asc"
        }
    },
    INTERESTINGNESS_DESC("interestingness", false) {
        override fun getFullString(): String {
            return "$sort-desc"
        }
    },
    INTERESTINGNESS_ASC("interestingness", true) {
        override fun getFullString(): String {
            return "$sort-asc"
        }
    },
    RELEVANCE("relevance", false) {
        override fun getFullString(): String {
            return sort
        }
    };

    abstract fun getFullString(): String
}