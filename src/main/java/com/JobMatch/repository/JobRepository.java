package com.JobMatch.repository;

import com.JobMatch.domain.Job;
import com.JobMatch.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.locationtech.jts.geom.Point;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    // Find all jobs posted by a specific company
    List<Job> findByCompany(User company);

    // Find all active jobs
    List<Job> findByActiveTrue();

    // Spatial query: Find jobs within a distance (in meters) from a point
    @Query(value = "SELECT * FROM jobs j WHERE j.active = true " +
            "AND ST_DWithin(j.location, :point, :distance) " +
            "ORDER BY ST_Distance(j.location, :point)",
            nativeQuery = true)
    List<Job> findNearbyJobs(@Param("point") Point point,
                             @Param("distance") double distance);
}