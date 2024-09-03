package com.currencyconverter.friends

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.currencyconverter.friends.signup.SignUpScreen
import com.currencyconverter.friends.signup.SignUpViewModel
import com.currencyconverter.friends.timeline.TimeLineScreen
import com.currencyconverter.friends.timeline.TimeLineViewModel
import com.currencyconverter.friends.ui.theme.FriendsTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val signUpViewModel: SignUpViewModel by viewModel()
    private val timeLineViewModel: TimeLineViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            FriendsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    NavHost(navController = navController, startDestination = SIGN_UP) {
                        composable(SIGN_UP) {
                            SignUpScreen(signUpViewModel) { signeUpUserId ->
                                navController.navigate("$TIME_LINE/$signeUpUserId") {
                                    popUpTo(SIGN_UP) {
                                        inclusive = true
                                    }
                                }
                            }
                        }

                        composable(route = "$TIME_LINE/{userId}",
                            arguments = listOf(navArgument("userId") {})
                        ) { backStackEntry ->
                            TimeLineScreen(
                                backStackEntry.arguments?.getString("userId") ?: "",
                                timeLineViewModel
                            )
                        }

                    }


//                    Box(modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center){
//                        Image(
//                            painter = painterResource(id = R.drawable.ic_launcher),
//                            contentDescription = null
//                        )
//                    }


                    //Greeting("Android")
                }
            }
        }
    }

    companion object {
        private const val SIGN_UP = "signUp"
        private const val TIME_LINE = "timeline"
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FriendsTheme {
        Greeting("Android")
    }
}