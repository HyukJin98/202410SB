package edu.du.sb1015_2.repository;

import edu.du.sb1015_2.entity.MyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyDataRepository extends JpaRepository<MyData, Long> {

}
