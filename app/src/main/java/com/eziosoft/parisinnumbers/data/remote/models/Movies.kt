package com.eziosoft.parisinnumbers.data.remote.models

data class Movies(
    val links: List<Link>,
    val records: List<Record>,
    val total_count: Int
)
