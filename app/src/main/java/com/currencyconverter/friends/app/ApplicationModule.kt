package com.currencyconverter.friends.app

import com.currencyconverter.friends.domain.RegexCredentialsValidator
import com.currencyconverter.friends.domain.post.InMemoryPostCatalog
import com.currencyconverter.friends.domain.post.PostCatalog
import com.currencyconverter.friends.domain.timeline.TimeLineRepository
import com.currencyconverter.friends.domain.user.Following
import com.currencyconverter.friends.signup.SignUpViewModel
import com.currencyconverter.friends.domain.user.UserRepository
import com.currencyconverter.friends.domain.user.InMemoryUserCatalog
import com.currencyconverter.friends.domain.user.UserCatalog
import com.currencyconverter.friends.timeline.TimeLineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.single

val applicationModule = module {

    single<CoroutinesDispatchers> {
        DefaultDispatchers()
    }
    single<UserCatalog> {
        InMemoryUserCatalog(
            followings = listOf(
                Following("saraId", "lucyId"),
                Following("annaId", "lucyId")
            )
        )
    }

    single<PostCatalog>() {
        InMemoryPostCatalog()
    }


    factory {
        RegexCredentialsValidator()
    }

    factory {
        UserRepository(userCatalog = get())
    }

    factory {
        TimeLineRepository(userCatalog = get(), postCatalog = get())
    }

    viewModel {
        SignUpViewModel(
            credentialsValidator = get(),
            userRepository = get(),
            dispatchers = get()
        )
    }

    viewModel {
        TimeLineViewModel(timeLineRepository = get())
    }

//    val userCatalog = InMemoryUserCatalog()
//    val userRepository = UserRepository(userCatalog)
}