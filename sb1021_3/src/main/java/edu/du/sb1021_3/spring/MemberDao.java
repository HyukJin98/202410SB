package edu.du.sb1021_3.spring;

import java.util.List;
import java.util.Optional;

import edu.du.sb1021_3.entity.Member;
import edu.du.sb1021_3.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDao {

	private final MemberRepository memberRepository;


	public Optional<Member> selectByEmail(String email) {
		return memberRepository.findByEmail(email);
	}

	public void insert(Member member) {
		memberRepository.save(member);
	}

	public void update(Member member) {
		memberRepository.save(member);
	}

	public List<Member> selectAll() {
		return memberRepository.findAll();
	}

}
