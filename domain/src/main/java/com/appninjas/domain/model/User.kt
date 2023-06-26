package com.appninjas.domain.model

import com.appninjas.domain.enums.UserStatus

data class User(
    val id: Int,
    val authorId: String,
    val name: String,
    val role: UserStatus,
    val login: String,
    val createdAtDate: String,
    val password: String,
    val vacanciesIds: List<String>?
)
