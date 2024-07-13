package com.currencyconverter.friends.domain

sealed class CredentialsValidationResult {

  object InvalidEmail : CredentialsValidationResult()

  object InvalidPassword : CredentialsValidationResult()

  object Valid : CredentialsValidationResult()
}