package com.example.uas_kelompok4

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uas_kelompok4.model.User
import com.example.uas_kelompok4.ui.theme.Purple80
import com.example.uas_kelompok4.ui.theme.UAS_Kelompok4Theme
import com.google.firebase.database.*

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UserList : ComponentActivity() {
    private val database: FirebaseDatabase =
        FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val userRef: DatabaseReference = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UAS_Kelompok4Theme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "ListPage"
                    ) {
                        composable("ListPage") {
                            ListPage()
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun ListPage() {
        // Use MutableState to hold the listData
        val listDataState = remember { mutableStateListOf<User>() }

        // Fetch data from Firebase
        LaunchedEffect(Unit) {
            val database = FirebaseDatabase.getInstance("https://uas-kelompok-4-5e25b-default-rtdb.asia-southeast1.firebasedatabase.app/")
            val userRef = database.getReference("users")

            val eventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val users = mutableListOf<User>()
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        user?.let { users.add(it) }
                    }
                    listDataState.clear()
                    listDataState.addAll(users)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            }
            userRef.addValueEventListener(eventListener)
        }

        ListContent(listData = listDataState)
    }

    @Composable
    fun ListContent(listData: SnapshotStateList<User>) {
        LazyColumn {
            items(listData) { item ->
                Row(
                    modifier =
                    Modifier.padding(vertical = 5.dp)
                        .padding(horizontal = 5.dp)
                        .background(color = Purple80)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier =
                        Modifier.padding(horizontal = 3.dp)
                            .padding(vertical = 3.dp)
                    ) {
                        Text(item.name, style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(5.dp))
                        Row {
                            Text(item.email, style = MaterialTheme.typography.bodySmall)
                            Spacer(modifier = Modifier.width(2.dp))
                            Text("-", style = MaterialTheme.typography.bodySmall)
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(item.role, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ListDetail(listItem: User) {
        Column(
            modifier = Modifier.padding(5.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(listItem.name, style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(3.dp))
                Text(listItem.email, style = MaterialTheme.typography.bodySmall)
                Text(listItem.role, style = MaterialTheme.typography.bodySmall)
                Text("Joined since:", style = MaterialTheme.typography.bodySmall)

            }
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Column(
                    modifier = Modifier.background(color = Purple80)
                        .size(100.dp)
                        .padding(5.dp)
                ) {
                    Text("Active Promos", style = MaterialTheme.typography.bodySmall)
                }
                Text("View History", style = MaterialTheme.typography.bodySmall)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewList() {
        ListPage()
    }

    /*@Preview(showBackground = true)
    @Composable
    fun PreviewDetail() {
        ListDetail(User("1", "Joe", "joe@mail.com", "abcdef", "Member"))
    }*/
}

