package com.example.rewardscodingexercise.model


data class Items(
    val id: Int,
    val listId: Int,
    val name: String
): Comparable<Items> {
    override fun compareTo(other: Items): Int {
        // Compare first by listId, then by name
        return if (listId == other.listId) {
            name.compareTo(other.name)
        } else {
            listId.compareTo(other.listId)
        }
    }
}