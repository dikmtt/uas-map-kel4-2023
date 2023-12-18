@file:OptIn(ExperimentalComposeUiApi::class)

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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

                            LoginHome(navController)
                        }
                        composable("register") {

                            //Register(navController)
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

    val startRegisterActivity = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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

    val goToRegisterActivity: () -> Unit = {
        val intent = Intent(context, RegisterActivity::class.java)
        startRegisterActivity.launch(intent)
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
           OutlinedButton(
                onClick = { navController.navigate("QrScan") },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.Unspecified)
                Text("Scan QR")
            }

            OutlinedButton(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Unspecified)
                Text("Login")
            }

            OutlinedButton(
                onClick = goToRegisterActivity,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.Unspecified)
                Text("Register")
            }

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
    onButtonClick: () -> Unit,
    navController: NavController
){
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
                TextField(
                    value = inputEmail,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = onEmailValueChange
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.input_pass),
                    style = TextStyle(
                        fontSize = 24.sp,
                    )
                )
                TextField(
                    value = inputPass,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = onPassValueChange
                )
                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    onClick = {
                        authenticateUser(inputEmail, inputPass, navController)
                        onButtonClick()
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Submit")
                }
            }
        }
    }
}

private fun authenticateUser(email: String, password: String, navController: NavController) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = task.result?.user
                getUserRole(navController, user?.uid)
            } else {
                val exception = task.exception
            }
        }
}

private fun getUserRole(navController: NavController, userId: String?) {
    val database = FirebaseDatabase.getInstance()
    val rolesRef = database.getReference("roles")

    userId?.let {
        rolesRef.child(it).get()
            .addOnSuccessListener { dataSnapshot ->
                val role = dataSnapshot.getValue(String::class.java)
                // Redirect based on user role
                navigateBasedOnRole(navController, role)
            }
            .addOnFailureListener {
                // Handle failure to retrieve user role
            }
    }
}

private fun navigateBasedOnRole(navController: NavController, role: String?) {
    when (role) {
        "member" -> {
            startActivityForMember(navController)
        }
        "staff" -> {
            // Add code for staff navigation
        }
        "admin" -> {
            // Add code for admin navigation
        }
        else -> {
            Toast.makeText(navController.context, "Must be a member...", Toast.LENGTH_SHORT).show()
        }
    }
}

private fun startActivityForMember(navController: NavController) {
    navController.navigate("MainPage")
}

private fun startActivityForStaff() {
  //  val intent = Intent(context, StaffActivity::class.java)
  //  startActivity(intent)
}

private fun startActivityForAdmin() {
 //   val intent = Intent(context, AdminActivity::class.java)
 //  startActivity(intent)
}

@Composable
fun LoginHome(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    Login(
        email,
        pass,
        onEmailValueChange = { email = it },
        onPassValueChange = { pass = it },
        onButtonClick = { authenticateUser(email, pass, navController) },
        navController = navController
    )
}


/*@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    Login("yo", "yo", {}, {}, {} )
}*/

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

