package edu.du.sb1029.repository;

import edu.du.sb1029.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    public Notice findAllByOrderByIdDesc();
}
