package com.example.pmocbr.util

import java.text.SimpleDateFormat
import java.util.*

object IdGenerator {
    fun generateId(): String {
        val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val datePart = sdf.format(Date())
        val randomPart = UUID.randomUUID().toString().substring(0, 4).uppercase()
        return "PMOC-$datePart-$randomPart"
    }
}
