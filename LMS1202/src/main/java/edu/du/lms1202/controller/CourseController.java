package edu.du.lms1202.controller;

import edu.du.lms1202.domain.Course;
import edu.du.lms1202.domain.Enrollment;
import edu.du.lms1202.domain.User;
import edu.du.lms1202.dto.CourseDto;
import edu.du.lms1202.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/courses")
    public String list(Model model) {
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        return "course/list";
    }

    @GetMapping("/courses/new")
    public String createForm(Model model) {
        model.addAttribute("courseDto", new CourseDto());
        return "course/form";
    }

    @PostMapping("/courses")
    public String create(@ModelAttribute CourseDto courseDto) {
        courseService.createCourse(courseDto);
        return "redirect:/courses";
    }

    @GetMapping("/courses/{courseId}")
    public String detail(@PathVariable Long courseId,
                         @AuthenticationPrincipal UserDetails userDetails,
                         Model model) {
        Course course = courseService.findById(courseId);
        Long userId = ((User) userDetails).getId();  // UserDetails에서 User 객체로 캐스팅하여 ID를 가져옴

        boolean enrolled = courseService.isEnrolled(courseId, userId);
        double progress = enrolled ? courseService.calculateProgress(courseId, userId) : 0;

        model.addAttribute("course", course);
        model.addAttribute("enrolled", enrolled);
        model.addAttribute("progress", progress);
        return "course/detail";
    }

    @PostMapping("/courses/{courseId}/enroll")
    public String enroll(@PathVariable Long courseId,
                         @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();  // UserDetails에서 User 객체로 캐스팅하여 ID를 가져옴
        courseService.enroll(courseId, userId);
        return "redirect:/courses/" + courseId;
    }

    @GetMapping("/courses/my-courses")
    public String myCourses(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Long userId = ((User) userDetails).getId();  // 현재 로그인한 사용자 ID

        // 현재 수강 중인 강좌와 완료된 강좌 조회
        List<Enrollment> currentEnrollments = courseService.getCurrentEnrollments(userId);
        List<Enrollment> completedEnrollments = courseService.getCompletedEnrollments(userId);

        // 진도율 값을 담을 Map 생성
        Map<Long, Double> progressRates = new HashMap<>();

        // 각 수강 중인 강좌에 대해 진도율 계산 후 progressRates에 추가
        for (Enrollment enrollment : currentEnrollments) {
            double progressRate = courseService.calculateProgress(enrollment.getCourse().getId(), userId);
            progressRates.put(enrollment.getCourse().getId(), progressRate);
        }

        model.addAttribute("currentEnrollments", currentEnrollments);
        model.addAttribute("completedEnrollments", completedEnrollments);
        model.addAttribute("progressRates", progressRates);  // 진도율 맵을 모델에 추가

        return "course/my-courses";
    }

}
