package com.lms.service;

import com.lms.domain.Course;
import com.lms.domain.Enrollment;
import com.lms.domain.Progress;
import com.lms.domain.User;
import com.lms.dto.CourseDto;
import com.lms.repository.CourseRepository;
import com.lms.repository.EnrollmentRepository;
import com.lms.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("강좌를 찾을 수 없습니다."));
    }

    @Transactional
    public Course createCourse(CourseDto courseDto) {
        Course course = new Course();
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());
        return courseRepository.save(course);
    }

    public boolean isEnrolled(Long courseId, Long userId) {
        return enrollmentRepository.findByCourseIdAndUserId(courseId, userId).isPresent();
    }

    @Transactional
    public void enroll(Long courseId, Long userId) {
        if (isEnrolled(courseId, userId)) {
            throw new IllegalStateException("이미 수강 중인 강좌입니다.");
        }

        Course course = findById(courseId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setUser(user);
        enrollment.setEnrolledAt(LocalDateTime.now());

        enrollmentRepository.save(enrollment);
    }

    public double calculateProgress(Long courseId, Long userId) {
        Enrollment enrollment = enrollmentRepository
                .findByCourseIdAndUserId(courseId, userId)
                .orElseThrow(() -> new EntityNotFoundException("수강 정보를 찾을 수 없습니다."));

        return enrollment.getProgressList().stream()
                .mapToDouble(Progress::getProgressRate)
                .average()
                .orElse(0.0);
    }

    public List<Enrollment> getCurrentEnrollments(Long userId) {
        return enrollmentRepository.findByUserIdAndCompletedFalse(userId);
    }

    public List<Enrollment> getCompletedEnrollments(Long userId) {
        return enrollmentRepository.findByUserIdAndCompletedTrue(userId);
    }
}
