package com.currencyconverter.friends.domain.post

class InMemoryPostCatalog(private val availablePosts: List<Post> = emptyList()) : PostCatalog {
    override fun postsFor(userIds: List<String>): List<Post> {
        val availablePosts = availablePosts
        return availablePosts.filter { userIds.contains(it.userId) }
    }
}