package com.currencyconverter.friends.app

import com.currencyconverter.friends.domain.RegexCredentialsValidator
import com.currencyconverter.friends.signup.SignUpViewModel
import com.currencyconverter.friends.domain.user.UserRepository
import com.currencyconverter.friends.domain.user.InMemoryUserCatalog
import com.currencyconverter.friends.domain.user.UserCatalog
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {

    single<CoroutinesDispatchers> {
        DefaultDispatchers()
    }
    single<UserCatalog> {
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
            userRepository = get(),
            dispatchers = get()
        )
    }

//    val userCatalog = InMemoryUserCatalog()
//    val userRepository = UserRepository(userCatalog)
}