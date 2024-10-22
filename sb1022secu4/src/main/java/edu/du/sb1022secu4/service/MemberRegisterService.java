package edu.du.sb1022secu4.service;

import antlr.BaseAST;
import edu.du.sb1022secu4.entity.Member;
import edu.du.sb1022secu4.repository.MemberRepository;
import javassist.bytecode.DuplicateMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class MemberRegisterService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private final MemberRepository memberRepository;

	public MemberRegisterService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}



	@Transactional
	public Long register(Member me) throws DuplicateMemberException {
		Member member = memberRepository.findByUsername(me.getUsername());
		if (member != null) {
			throw new DuplicateMemberException("dup UserName " + me.getUsername());
		}
		String encodedPassword = passwordEncoder.encode(me.getPassword());
		me.setPassword(encodedPassword);

		Member newMember = Member.builder()
				.email(me.getEmail())
				.username(me.getUsername())
				.password(encodedPassword)
				.build();
		memberRepository.save(newMember);

		return newMember.getId();
	}
}