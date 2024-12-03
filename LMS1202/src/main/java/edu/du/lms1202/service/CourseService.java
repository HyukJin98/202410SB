package edu.du.lms1202.service;

import edu.du.lms1202.domain.Course;
import edu.du.lms1202.domain.Enrollment;
import edu.du.lms1202.domain.Progress;
import edu.du.lms1202.domain.User;
import edu.du.lms1202.dto.CourseDto;
import edu.du.lms1202.repository.CourseRepository;
import edu.du.lms1202.repository.EnrollmentRepository;
import edu.du.lms1202.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
