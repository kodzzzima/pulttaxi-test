package com.example.testapp.data.model

data class User(
    val active_order: String?,
    val birth_day: String?,
    val city: String,
    val email: String,
    val id: Int,
    val name: String,
    val need_registration: Boolean,
    val organization_id: Int?,
    val phone_number: String,
    val rating: String?,
    val sex: String?,
    val status: String,
)