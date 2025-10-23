package com.JobMatch.service;

import com.JobMatch.domain.Job;
import com.JobMatch.domain.User;
import com.JobMatch.repository.JobRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final GeometryFactory geometryFactory;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
        // 4326 = SRID for WGS84 (standard lat/long coordinate system)
        this.geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    }

    // Post a new job
    public Job postJob(String title, String description, String skills,
                       Double pay, Integer durationHours, String address,
                       Double latitude, Double longitude, User company) {

        // Validate company is actually a company
        if (!company.isCompany()) {
            throw new IllegalArgumentException("Only companies can post jobs");
        }

        // Create location Point (IMPORTANT: longitude first, then latitude!)
        Point location = geometryFactory.createPoint(new Coordinate(longitude, latitude));

        // Create and save job
        Job job = new Job();
        job.setTitle(title);
        job.setDescription(description);
        job.setSkills(skills);
        job.setPay(pay);
        job.setDurationHours(durationHours);
        job.setAddress(address);
        job.setLocation(location);
        job.setCompany(company);

        return jobRepository.save(job);
    }

    // Get all jobs posted by a company
    public List<Job> getJobsByCompany(User company) {
        return jobRepository.findByCompany(company);
    }

    // Get all active jobs
    public List<Job> getAllActiveJobs() {
        return jobRepository.findByActiveTrue();
    }

    // Find jobs near a location
    public List<Job> findJobsNearLocation(Double latitude, Double longitude, Double radiusKm) {
        Point location = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        double radiusMeters = radiusKm * 1000; // Convert km to meters

        return jobRepository.findNearbyJobs(location, radiusMeters);
    }

    // Get a single job by ID
    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job not found"));
    }
}
