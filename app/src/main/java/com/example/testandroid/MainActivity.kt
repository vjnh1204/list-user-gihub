package com.example.testandroid

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.testandroid.ui.detail.UserDetailScreen
import com.example.testandroid.ui.home.HomeScreen
import com.example.testandroid.ui.theme.TestAndroidTheme
import com.example.testandroid.utils.Constants
import com.example.testandroid.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val painter = painterResource(id = R.drawable.close)
            TestAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    NavigationController(painter)

                }
            }
        }

    }
}

@Composable
fun NavigationController(painter: Painter){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Home.route){
        composable(route = Routes.Home.route){
            HomeScreen(clickUserCard = {
                navController.navigate(Routes.Detail.withArgs(it))
            })
        }
        composable(
            route = Routes.Detail.route +"/{${Constants.DETAIL_AGR_USER_LOGIN}}" ,
            arguments = listOf(navArgument(Constants.DETAIL_AGR_USER_LOGIN){
                    type = NavType.StringType
            })
                ){ navBackStackEntry ->
                val loginId = navBackStackEntry.arguments?.getString(Constants.DETAIL_AGR_USER_LOGIN)
                requireNotNull(loginId)
                UserDetailScreen(navController,loginId,painter)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TestAndroidTheme {

    }
}