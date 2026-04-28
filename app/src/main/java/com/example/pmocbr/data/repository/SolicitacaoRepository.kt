package com.example.pmocbr.data.repository

import com.example.pmocbr.data.model.Solicitacao
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SolicitacaoRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val collection = firestore.collection("solicitacoes")

    suspend fun salvarSolicitacao(solicitacao: Solicitacao): Result<Unit> {
        return try {
            collection.document(solicitacao.id).set(solicitacao).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun buscarSolicitacoesPorUsuario(userId: String): Result<List<Solicitacao>> {
        return try {
            val snapshot = collection
                .whereEqualTo("userId", userId)
                .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .await()
            val lista = snapshot.toObjects(Solicitacao::class.java)
            Result.success(lista)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
