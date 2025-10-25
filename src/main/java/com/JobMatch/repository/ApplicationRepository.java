package com.JobMatch.repository;

import com.JobMatch.domain.Application;
import com.JobMatch.domain.Job;
import com.JobMatch.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // Find all applications by a student
    List<Application> findByStudent(User student);

    // Find all applications for a specific job
    List<Application> findByJob(Job job);

    // Check if student already applied to this job
    Optional<Application> findByJobAndStudent(Job job, User student);

    // Find all applications for jobs posted by a company
    List<Application> findByJobCompany(User company);
}