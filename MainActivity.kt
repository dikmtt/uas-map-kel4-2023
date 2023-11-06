package com.example.uas_kelompok4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uas_kelompok4.ui.theme.UAS_Kelompok4Theme

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UAS_Kelompok4Theme {

                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                   // color = Color.White
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "MainPage"
                    ) {
                        composable("MainPage") {
                            MainPage(navController)
                        }
                        composable("scanqr") {

                            //scanqr()
                        }
                        composable("login") {

                            //Login
                        }
                        composable("register") {

                            //Register()
                        }
                    }
                }
            }
        }
    }
}


//ini utamanya
//@Preview(showBackground = true)
@Composable
fun MainPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.Logo_area),
                style = TextStyle(
                    fontSize = 24.sp,
                    // color = Color.Black
                )
            )

            Text(
                text = stringResource(id = R.string.name_area),
                style = TextStyle(
                    fontSize = 18.sp,
                    // color = Color.Gray
                )
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        // Buttons Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate("scanqr") }
            ) {
                Text("Scan QR")
            }

            Button(
                onClick = { navController.navigate("login") }
            ) {
                Text("Login")
            }

            Button(
                onClick = { navController.navigate("register") }
            ) {
                Text("Register")
            }
        }
    }
}



@Composable
fun Image(painter: Any, contentDescription: Nothing?, modifier: Any) {

}
