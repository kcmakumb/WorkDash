package com.example.workdash.services

import com.example.workdash.Constants
import com.example.workdash.models.JobApplicationModel

object JobApplicationService {
    fun applyToJob(jobId: String) {
        var jobApplicationId: String
        val employeeId = UserService.getCurrentUserId()
        val lmd = { retrievedId : Long ->
            jobApplicationId = retrievedId.toString()
            val jobApplicationModel = JobApplicationModel(jobApplicationId, jobId, employeeId)
            saveJobApplication(jobApplicationModel)
        }
        IdGeneratorService.generateJobApplicationId(lmd)
        CheckInService.updateJobState(jobId, "In Progress")
    }

    private fun saveJobApplication(jobApplication: JobApplicationModel) {
        DatabaseService.writeToDbTable(Constants.TableNames.JOB_APPLICATION_TABLE_NAME, jobApplication.jobApplicationId, jobApplication)
    }

    fun acceptApplication(jobApplication: JobApplicationModel) {
        JobService.incrementTotalFilledPositions(jobApplication.jobId)
        jobApplication.applicationStatus = "Accepted"
        saveJobApplication(jobApplication)
        CheckInService.createMatchedJobModel(jobApplication)
    }

    fun rejectApplication(jobApplication: JobApplicationModel) {
        jobApplication.applicationStatus = "Rejected"
        saveJobApplication(jobApplication)
    }

    fun getJobApplicationFromId(jobApplicationId: String, callback: (jobApplication: JobApplicationModel?) -> Unit) {
        val jobApplicationModel = JobApplicationModel()
        DatabaseService.readSingleObjectFromDbTableWithId(
            Constants.TableNames.JOB_APPLICATION_TABLE_NAME,
            Constants.IdNames.JOB_APPLICATION_ID_NAME, jobApplicationId, jobApplicationModel, callback)
    }
}