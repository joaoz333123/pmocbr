package com.example.pmocbr.auth

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID

class AuthManager(private val context: Context) {
    private val auth = FirebaseAuth.getInstance()
    private val credentialManager = CredentialManager.create(context)

    val currentUser get() = auth.currentUser

    suspend fun signInWithGoogle(): Result<Unit> {
        return try {
            val rawNonce = UUID.randomUUID().toString()
            val bytes = rawNonce.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

            // Web Client ID is required here. Usually found in google-services.json 
            // under oauth_client -> client_type 3
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId("SEU_WEB_CLIENT_ID_AQUI") // Instruções abaixo para obter
                .setNonce(hashedNonce)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(context, request)
            handleSignIn(result)
        } catch (e: GetCredentialException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun handleSignIn(result: GetCredentialResponse): Result<Unit> {
        val credential = result.credential
        return if (credential is GoogleIdTokenCredential) {
            try {
                val firebaseCredential = GoogleAuthProvider.getCredential(credential.idToken, null)
                auth.signInWithCredential(firebaseCredential).await()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(Exception("Tipo de credencial inválido"))
        }
    }

    suspend fun signOut() {
        auth.signOut()
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
    }
}
