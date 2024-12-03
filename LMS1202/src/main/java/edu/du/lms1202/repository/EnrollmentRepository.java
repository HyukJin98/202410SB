package edu.du.lms1202.repository;

import edu.du.lms1202.domain.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByCourseIdAndUserId(Long courseId, Long userId);
    List<Enrollment> findByUserIdAndCompletedFalse(Long userId);
    List<Enrollment> findByUserIdAndCompletedTrue(Long userId);
}