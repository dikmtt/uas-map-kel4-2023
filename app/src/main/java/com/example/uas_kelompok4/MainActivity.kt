package com.example.uas_kelompok4

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uas_kelompok4.ui.theme.UAS_Kelompok4Theme
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.database


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Firebase initialization
        FirebaseApp.initializeApp(this)

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
                        composable("QrScan") {
                            QrScan(navController)
                        }
                        composable("login") {

                            LoginHome()
                        }
                        composable("register") {

                            //Register()
                        }
                        composable("AddMenu") {
                            // Create a composable or call AddMenuActivity here
                        }
                        composable("MenuFragment") {
                            // Your MenuFragment composable or associated logic
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
    val context  = LocalContext.current
    val startAddMenuActivity = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
        }
    }
    val startDashboardActivity = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
        }
    }
    val startOrderActivity = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
        }
    }

    // Function to start AddMenuActivity
    val goToAddMenuActivity: () -> Unit = {
        val intent = Intent(context, AddMenuActivity::class.java)
        startAddMenuActivity.launch(intent)
    }
    val goToDashboardActivity: () -> Unit = {
        val intent = Intent(context, DashboardActivity::class.java)
        startDashboardActivity.launch(intent)
    }
    val goToOrderActivity: () -> Unit = {
        val intent = Intent(context, OrderActivity::class.java)
        startOrderActivity.launch(intent)
    }
    val goToMenuFragment: () -> Unit = {
        navController.navigate("MenuFragment")
    }

    val database = Firebase.database("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val myRef = database.getReference("message")
    val testDatabase: () -> Unit = {
        myRef.setValue("Hello, World!")
        Log.d("test", "$myRef")
    }
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
                onClick = { navController.navigate("QrScan") }
            ) {
                Text("Scan QR")
            }

            Button(
                onClick = { navController.navigate("login") }
            ) {
                Text("Login")
            }

//            Button(
//                onClick = { context.startActivity(Intent(context , DashboardActivity::class.java)) }
//            ) {
//                Text("Register")
//            }

            Button(
                onClick = goToAddMenuActivity // Navigate to AddMenuActivity when button is clicked
            ) {
                Text("Go to Add Menu")
            }
//            Button(
//                onClick = goToMenuFragment // Navigate to MenuFragment when button is clicked
//            ) {
//                Text("Go to Menu Fragment")
//            }
            Button(
                onClick = goToDashboardActivity

            ) {
                Text("Go to Dashboard Activity")
            }
            Button(
                onClick = goToOrderActivity

            ) {
                Text("Go to Order Activity")
            }
//            Button(
//                onClick = testDatabase
//
//            ) {
//                Text("test database")
//            }
        }
    }
}


@Composable
fun Login(
    inputEmail: String,
    inputPass: String,
    onEmailValueChange: (String) -> Unit,
    onPassValueChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.input_email),
                    style = TextStyle(
                        fontSize = 24.sp,
                    )
                )
                TextField(value = inputEmail,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ), onValueChange = onEmailValueChange)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.input_pass),
                    style = TextStyle(
                        fontSize = 24.sp,
                    )
                )
                TextField(value = inputPass,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ), onValueChange = onPassValueChange)
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = onButtonClick) {
                    Text(text = "Submit")
                }
            }
        }
    }
}

@Composable
fun LoginHome() {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    Login(
        email,
        pass,
        onEmailValueChange = { email = it },
        onPassValueChange = { pass = it },
        onButtonClick = {}
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    Login("yo", "yo", {}, {}) {}
}

@Composable
fun Image(painter: Any, contentDescription: Nothing?, modifier: Any) {

}


@Composable
fun QrScan(navController: NavController) {
    val context = LocalContext.current

    val launchQRScan =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val scannedResult = result.data?.getStringExtra("SCAN_RESULT")
                Toast.makeText(context, "Scanned result: $scannedResult", Toast.LENGTH_SHORT).show()
            }
        }

    // Initiate QR scan when the composable is called (equivalent to a direct call)
    LaunchedEffect(Unit) {
        val intent = Intent(context, QrScan::class.java)
        launchQRScan.launch(intent)
    }
}



