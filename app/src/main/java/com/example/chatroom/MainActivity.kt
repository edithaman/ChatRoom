package com.example.chatroom

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatroom.screen.ChatRoomScreen
import com.example.chatroom.screen.ChatScreen
import com.example.chatroom.screen.LoginScreen
import com.example.chatroom.screen.SignUpScreen
import com.example.chatroom.ui.theme.ChatRoomTheme
import com.example.chatroom.viewmodel.AuthViewModel
import com.example.chatroom.viewmodel.RoomViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatRoomTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainApp() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    NavHost(navController = navController , startDestination =Screen.LoginScreen.route ){
        composable(Screen.LoginScreen.route){
            LoginScreen (
                authViewModel = AuthViewModel(),
                onLoginSuccess = { navController.navigate(Screen.ChatRoomScreen.route)},
                onSignUpClick = {navController.navigate(Screen.SignupScreen.route)}
            )
        }
        composable(Screen.SignupScreen.route){
            SignUpScreen(
                authViewModel =  AuthViewModel(),
                onSignUpSuccess = { navController.navigate(Screen.LoginScreen.route) },
                onLoginClick = {navController.navigate(Screen.LoginScreen.route)}
            )
        }
        composable(Screen.ChatRoomScreen.route){
            ChatRoomScreen (
                roomViewModel = RoomViewModel(),
                onJoinClicked = {navController.navigate("${Screen.ChatScreen.route}/ ${it.id}")}
            )
        }
        composable("${ Screen.ChatScreen.route }/ {roomId}"){
                val roomId: String = it
            .arguments?.getString("roomId") ?: ""
            ChatScreen(roomId = roomId)
        }
    }
}


