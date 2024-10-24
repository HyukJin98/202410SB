package edu.du.sb1022secu5.repository;

import edu.du.sb1022secu5.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}
