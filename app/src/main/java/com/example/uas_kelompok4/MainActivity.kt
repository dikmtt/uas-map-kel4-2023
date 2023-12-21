package com.example.uas_kelompok4

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uas_kelompok4.model.User
import com.example.uas_kelompok4.ui.theme.UAS_Kelompok4Theme
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

lateinit var tUser: User
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

                        }
                        composable("MenuFragment") {

                        }
                        composable("DashboardActivity") {

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

    // Function to goes regular .kt and .xml :D
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
    val goToAddPromoActivity: () -> Unit = {
        val intent = Intent(context, AddPromoActivity::class.java)
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

//    val testDatabase: () -> Unit = {
 //       myRef.setValue("Hello, World!")
  //      Log.d("test", "$myRef")
  //  }

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

                )
            )

            Text(
                text = stringResource(id = R.string.name_area),
                style = TextStyle(
                    fontSize = 18.sp,

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
        )
        {
            Text("Scan QR to start ordering!", modifier = Modifier.padding(8.dp))

          OutlinedButton(
               onClick = { navController.navigate("QrScan") },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.Unspecified)
               Text("Scan QR")
           }

            Text("Already became member?", modifier = Modifier.padding(8.dp))

           OutlinedButton(
                onClick = { navController.navigate("Login") },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {

                Text("Login")
            }

            Text("Register for apply promo!", modifier = Modifier.padding(8.dp))

            OutlinedButton(
                onClick = goToRegisterActivity,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.Unspecified)
                Text("Register")
            }

//            Button(
//                onClick = goToAddMenuActivity // Navigate to AddMenuActivity when button is clicked
//            ) {
//                Text("Go to Add Menu")
//            }
//            Button(
//                onClick = goToMenuFragment // Navigate to MenuFragment when button is clicked
//            ) {
//                Text("Go to Menu Fragment")
//            }
//            Button(
//                onClick = goToDashboardActivity
//
//            ) {
//                Text("Go to Dashboard Activity")
//            }
//            Button(
//                onClick = goToOrderActivity
//            ) {
//                Text("Go to Order Activity")
//            }
//            Button(
//                onClick = goToAddPromoActivity
//            ) {
//                Text("Go to Add Promo Activity")
//            }

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login Page", modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = inputEmail,
            onValueChange = { onEmailValueChange(it) },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputPass,
            onValueChange = { onPassValueChange(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Login")
        }
    }
}


private fun startActivityForMember(context: Context, currUser: User) {
    val intent = Intent(context, DashboardActivity::class.java)
    intent.putExtra("USER", currUser)
    context.startActivity(intent)
}

/*private fun startActivityForMember(navController: NavController) {
    navController.navigate("QrScan")
}*/

private fun startActivityForStaff(context: Context, currUser: User) {
    val intent = Intent(context, DashboardActivity::class.java)
    intent.putExtra("USER", currUser)
    context.startActivity(intent)
}

private fun startActivityForAdmin(context: Context, currUser: User) {
    val intent = Intent(context, DashboardActivity::class.java)
    intent.putExtra("USER", currUser)
    context.startActivity(intent)
}


@Composable
fun LoginHome(navController: NavController) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    val signInWithEmailAndPassword: () -> Unit = {
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            authenticateUserInRealtimeDatabase(context, email, pass, navController)
        } else {
            Toast.makeText(context, "Email and password are required", Toast.LENGTH_SHORT).show()
        }
    }

    Login(
        email,
        pass,
        onEmailValueChange = { email = it },
        onPassValueChange = { pass = it },
        onButtonClick = signInWithEmailAndPassword
    )
}

private fun authenticateUserInRealtimeDatabase(context: Context, email: String, password: String, navController: NavController) {
    val databaseRef = FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users")

    databaseRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                for (userSnapshot in snapshot.children) {
                    val storedPassword = userSnapshot.child("password").getValue(String::class.java)

                    if (password == storedPassword) {
                        val role = userSnapshot.child("role").getValue(String::class.java)
                        if (!role.isNullOrBlank()) {
                            tUser = userSnapshot.getValue(User::class.java)!!
                            navigateBasedOnRole(context, navController, role, tUser)

                            Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "User role not found.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Incorrect credentials.", Toast.LENGTH_SHORT).show()
                    }
                    return
                }
            } else {
                Toast.makeText(context, "User not found.", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context, "Error retrieving user data.", Toast.LENGTH_SHORT).show()
        }
    })
}

private fun navigateBasedOnRole(context: Context, navController: NavController, role: String?
                                ,currUser: User) {
    when (role) {
        "Member" -> startActivityForMember(context, currUser)
        "Staff" -> startActivityForStaff(context, currUser)
        "Admin" -> startActivityForAdmin(context, currUser)
        else -> {
            Toast.makeText(context, "Invalid user role.", Toast.LENGTH_SHORT).show()
        }
    }
}


/*@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    Login("yo", "yo", {}, {}, {} )
}*/

@Composable
fun MyImage() {
    val imagePainter: Painter = painterResource(id = R.mipmap.ic_banner)

    Image(
        painter = imagePainter,
        contentDescription = "Main Page Image",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
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

