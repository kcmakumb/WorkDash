package com.example.workdash.screen



import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.workdash.routes.ID_ARG
import com.example.workdash.routes.ScreenRoute
import com.example.workdash.services.RatingService
//import com.example.workdash.models.EmployerProfileModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Rating(
    navController: NavController,




) {



    val contextForToast = LocalContext.current.applicationContext

    var rating by remember {mutableStateOf(0)}

    val navBackStackEntry = navController.currentBackStackEntry
    val id = navBackStackEntry?.arguments?.getString(ID_ARG) ?: ""






    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        val database: DatabaseReference = FirebaseDatabase.getInstance().reference
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
        val query = database.child("userProfile").orderByChild("uid").equalTo(currentUserUid)

        val satisfiedText = remember { mutableStateOf("") }
        // Read the data from the database
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    // Access the value of isWorker from each matching employer profile
                    val isWorker = snapshot.child("worker").getValue(Boolean::class.java)
                    if (isWorker == true) {

                        satisfiedText.value = "How satisfied were you with this job?"


                    }
                    else{

                        satisfiedText.value = "How satisfied are you with this employee?"
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    contextForToast,
                    "Database Error: $databaseError",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


        Text(


            text = satisfiedText.value,
            style = MaterialTheme.typography.h1,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )



        Row {

            for (i in 0..4) {
                var isHighlighted by remember { mutableStateOf(false) }
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,

                    tint = if (isHighlighted) Color.Yellow else Color.Gray,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            rating = i + 1
                            isHighlighted = !isHighlighted
                            //TODO update rating?
                            if (isHighlighted) {
                                if (currentUserUid != null) {
                                    RatingService.updateRating(currentUserUid, rating.toLong())
                                }
                                if(satisfiedText.value == "How satisfied are you with this employee?") {
                                    navController.navigate(route = ScreenRoute.Payments.route) {

                                        popUpTo(ScreenRoute.Payments.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                                else if(satisfiedText.value == "How satisfied were you with this job?") {
                                    navController.navigate(route = ScreenRoute.ListOfJobs.route) {

                                        popUpTo(ScreenRoute.ListOfJobs.route) {
                                            inclusive = true
                                        }
                                    }
//                                    navController.navigate(route = ScreenRoute.Quiz.route) {
//
//                                        popUpTo(ScreenRoute.Quiz.route) {
//                                            inclusive = true
//                                        }
//                                    }
                                }
                            }
                        }
                        .offset(y = 20.dp)
                )
            }
        }
    }
}



