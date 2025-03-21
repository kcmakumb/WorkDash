package com.example.workdash.screen.EmployerScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.workdash.models.LocationModel
import com.example.workdash.routes.ScreenRoute
import com.example.workdash.viewModels.LocationViewModel
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChooseLocationEmployerScreen(
    navController: NavController,
    //jobs: List<Job>
) {
    val locationsViewModel= LocationViewModel()
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                title = {
                    Text("Select A Location")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(ScreenRoute.AddLocationEmployer.route)
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(locationsViewModel.getLocationListEmployer(currentUserUid!!)) { location ->
                LocationCard(locationModel = location, navController = navController)
            }
        }
    }
}


@Composable
fun LocationCard(locationModel: LocationModel, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        bottom = 8.dp,
                        top = 16.dp,
                        start = 16.dp,
                    )
                    .weight(10f),
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    model = locationModel.imgUrl,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Row() {
                        Text(
                            text = "Name: ",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            text = locationModel.locationName,
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }
                    Row() {
                        Text(
                            text = "Address: ",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            text = locationModel.address.address,
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }

                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(
                        color = Color.White,
                    )
                    .weight(1f),
                onClick = {
                    navController.navigate(route = ScreenRoute.AddPostEmployer.passLocationId(locationModel.locationId))
                }
            ) {
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = "Arrow"
                )
            }
        }
    }
}

