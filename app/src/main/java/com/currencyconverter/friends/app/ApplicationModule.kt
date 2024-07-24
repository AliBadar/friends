package com.currencyconverter.friends.app

import com.currencyconverter.friends.domain.RegexCredentialsValidator
import com.currencyconverter.friends.signup.SignUpViewModel
import com.currencyconverter.friends.domain.user.UserRepository
import com.currencyconverter.friends.domain.user.InMemoryUserCatalog
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {

    single {
        InMemoryUserCatalog()
    }

    factory {
        RegexCredentialsValidator()
    }

    factory {
        UserRepository(userCatalog = get())
    }





    viewModel {
        SignUpViewModel(
            credentialsValidator = get(),
            userRepository = get()
        )
    }

//    val userCatalog = InMemoryUserCatalog()
//    val userRepository = UserRepository(userCatalog)
}