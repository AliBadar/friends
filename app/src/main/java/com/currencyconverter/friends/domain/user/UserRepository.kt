package com.currencyconverter.friends.domain.user

import com.currencyconverter.friends.domain.exeptions.BackEndException
import com.currencyconverter.friends.domain.exeptions.ConnectionUnavailableException
import com.currencyconverter.friends.domain.exeptions.DuplicateAccountException
import com.currencyconverter.friends.signup.states.SignUpState

class UserRepository(private val userCatalog: UserCatalog) {

    suspend fun signUp(
        email: String,
        about: String,
        passowd: String
    ) = try {
        val user = userCatalog.createUser(email, passowd, about)
        SignUpState.SignedUp(user)
    } catch (_: DuplicateAccountException) {
        SignUpState.DuplicateAccount
    } catch (backendExceptio: BackEndException){
        SignUpState.BackEndError
    } catch (unavailableException: ConnectionUnavailableException){
        SignUpState.Offline
    }
}