package com.example.workdash.screen.WorkerScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workdash.models.JobModel
import com.example.workdash.routes.ScreenRoute
import com.example.workdash.viewModels.JobViewModel
import com.example.workdash.viewModels.LocationViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListOfJobsApplied(
    navController: NavController,
    //jobs: List<Job>
) {
    val locationViewModel = LocationViewModel()
    val jobViewModel = JobViewModel()
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                title = {
                    Text("Job Postings")
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
                        navController.navigate(ScreenRoute.ChooseLocationEmployer.route)
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
            items(jobViewModel.getJobList()) { job ->
                JobCard2(job = job, navController = navController)
            }
        }
    }
}

@Composable
fun JobCard2(job: JobModel, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        elevation = 4.dp,
//        backgroundColor = if (job.isFirstJob) Color.Gray else Color.White
        backgroundColor = if (true) Color.Gray else Color.White
        //backgroundColor = Color.Gray
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Row() {
                        Text(
                            text = "Job Title: ",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            text = job.jobName,
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = if (true) Color.White else Color.Black
                        )
                    }
                    Row() {
                        Text(
                            text = "Employer: ",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            text = job.jobId,
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,

                            color = if (true) Color.White else Color.Black
                            //color = Color.Gray
                        )
                    }
                    Row() {
                        Text(
                            text = "Hourly Wage: ",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            text = job.payPerHour.toString(),
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = if (true) Color.White else Color.Black
                        )
                    }
                }
            }


        }
    }
}