package com.JobMatch.controller;

import com.JobMatch.domain.Application;
import com.JobMatch.domain.User;
import com.JobMatch.service.ApplicationService;
import com.JobMatch.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class ApplicationController {

    private final ApplicationService applicationService;
    private final UserService userService;

    public ApplicationController(ApplicationService applicationService, UserService userService) {
        this.applicationService = applicationService;
        this.userService = userService;
    }

    // Apply for a job
    @PostMapping("/applications/apply")
    public String applyForJob(
            @RequestParam Long jobId,
            @RequestParam(required = false) String message,
            Principal principal,
            Model model
    ) {
        try {
            String email = principal.getName();
            User student = userService.getUserByEmail(email);

            applicationService.applyForJob(jobId, student, message);

            return "redirect:/applications/my-applications?applied=true";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/jobs/browse?error=" + e.getMessage();
        }
    }

    // View student's applications
    @GetMapping("/applications/my-applications")
    public String myApplications(Principal principal, Model model) {
        String email = principal.getName();
        User student = userService.getUserByEmail(email);

        List<Application> applications = applicationService.getStudentApplications(student);
        model.addAttribute("applications", applications);

        return "my-applications";
    }

    // View applications for company's jobs
    @GetMapping("/applications/received")
    public String receivedApplications(Principal principal, Model model) {
        String email = principal.getName();
        User company = userService.getUserByEmail(email);

        List<Application> applications = applicationService.getCompanyApplications(company);
        model.addAttribute("applications", applications);

        return "received-applications";
    }

    // Update application status (accept/reject)
    @PostMapping("/applications/update-status")
    public String updateStatus(
            @RequestParam Long applicationId,
            @RequestParam String status
    ) {
        applicationService.updateStatus(applicationId, status);
        return "redirect:/applications/received?updated=true";
    }
}