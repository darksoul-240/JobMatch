package com.JobMatch.controller;

import com.JobMatch.domain.Job;
import com.JobMatch.domain.User;
import com.JobMatch.service.JobService;
import com.JobMatch.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class JobController {

    private final JobService jobService;
    private final UserService userService;

    public JobController(JobService jobService, UserService userService) {
        this.jobService = jobService;
        this.userService = userService;
    }

    // Show job posting form (companies only)
    @GetMapping("/jobs/post")
    public String showPostJobForm() {
        return "post-job";
    }

    // Handle job posting
    @PostMapping("/jobs/post")
    public String postJob(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String skills,
            @RequestParam Double pay,
            @RequestParam Integer durationHours,
            @RequestParam String address,
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            Principal principal,
            Model model
    ) {
        try {
            // Get the logged-in company
            String email = principal.getName();
            User company = userService.getUserByEmail(email);

            // Post the job
            jobService.postJob(title, description, skills, pay, durationHours,
                    address, latitude, longitude, company);

            return "redirect:/jobs/my-jobs?posted=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "post-job";
        }
    }

    // Show company's jobs
    @GetMapping("/jobs/my-jobs")
    public String myJobs(Principal principal, Model model) {
        String email = principal.getName();
        User company = userService.getUserByEmail(email);

        List<Job> jobs = jobService.getJobsByCompany(company);
        model.addAttribute("jobs", jobs);

        return "my-jobs";
    }

    // Browse all jobs (students)
    @GetMapping("/jobs/browse")
    public String browseJobs(Model model) {
        List<Job> jobs = jobService.getAllActiveJobs();
        model.addAttribute("jobs", jobs);

        return "browse-jobs";
    }
}