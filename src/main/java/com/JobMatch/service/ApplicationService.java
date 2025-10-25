package com.JobMatch.service;

import com.JobMatch.domain.Application;
import com.JobMatch.domain.Job;
import com.JobMatch.domain.User;
import com.JobMatch.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobService jobService;

    public ApplicationService(ApplicationRepository applicationRepository, JobService jobService) {
        this.applicationRepository = applicationRepository;
        this.jobService = jobService;
    }

    // Apply for a job
    public Application applyForJob(Long jobId, User student, String message) {
        // Get the job
        Job job = jobService.getJobById(jobId);

        // Check if student already applied
        if (applicationRepository.findByJobAndStudent(job, student).isPresent()) {
            throw new IllegalArgumentException("You have already applied to this job");
        }

        // Check if applying to own job (if student is somehow also a company)
        if (job.getCompany().getId().equals(student.getId())) {
            throw new IllegalArgumentException("You cannot apply to your own job");
        }

        // Create application
        Application application = new Application();
        application.setJob(job);
        application.setStudent(student);
        application.setMessage(message);
        application.setStatus("PENDING");

        return applicationRepository.save(application);
    }

    // Get all applications by a student
    public List<Application> getStudentApplications(User student) {
        return applicationRepository.findByStudent(student);
    }

    // Get all applications for a job
    public List<Application> getJobApplications(Job job) {
        return applicationRepository.findByJob(job);
    }

    // Get all applications for a company's jobs
    public List<Application> getCompanyApplications(User company) {
        return applicationRepository.findByJobCompany(company);
    }

    // Update application status
    public Application updateStatus(Long applicationId, String status) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        application.setStatus(status);
        return applicationRepository.save(application);
    }
}