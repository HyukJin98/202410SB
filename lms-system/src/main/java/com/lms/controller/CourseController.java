package com.lms.controller;

import com.lms.domain.Course;
import com.lms.domain.Enrollment;
import com.lms.dto.CourseDto;
import com.lms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public String list(Model model) {
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        return "/course/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("courseDto", new CourseDto());
        return "/course/form";
    }

    @PostMapping
    public String create(@ModelAttribute CourseDto courseDto) {
        courseService.createCourse(courseDto);
        return "redirect:/courses";
    }

    @GetMapping("/{courseId}")
    public String detail(@PathVariable Long courseId,
                         @AuthenticationPrincipal UserDetails userDetails,
                         Model model) {
        Course course = courseService.findById(courseId);
        boolean enrolled = courseService.isEnrolled(courseId,
                Long.parseLong(userDetails.getUsername()));
        double progress = enrolled ?
                courseService.calculateProgress(courseId, Long.parseLong(userDetails.getUsername())) : 0;

        model.addAttribute("course", course);
        model.addAttribute("enrolled", enrolled);
        model.addAttribute("progress", progress);
        return "/course/detail";
    }

    @PostMapping("/{courseId}/enroll")
    public String enroll(@PathVariable Long courseId,
                         @AuthenticationPrincipal UserDetails userDetails) {
        courseService.enroll(courseId, Long.parseLong(userDetails.getUsername()));
        return "redirect:/courses/" + courseId;
    }

    @GetMapping("/my-courses")
    public String myCourses(@AuthenticationPrincipal UserDetails userDetails,
                            Model model) {
        Long userId = Long.parseLong(userDetails.getUsername());
        List<Enrollment> currentEnrollments = courseService.getCurrentEnrollments(userId);
        List<Enrollment> completedEnrollments = courseService.getCompletedEnrollments(userId);

        model.addAttribute("currentEnrollments", currentEnrollments);
        model.addAttribute("completedEnrollments", completedEnrollments);
        return "/course/my-courses";
    }
}