package com.example.pmocbr.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class Solicitacao(
    val id: String = "",
    val userId: String = "",
    val userEmail: String? = null,
    @ServerTimestamp
    val createdAt: Timestamp? = null,
    val status: String = "Recebida",
    val formData: Map<String, Any> = emptyMap()
)
