package edu.du.sb1023;

import edu.du.sb1023.entity.Member;
import edu.du.sb1023.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.List;

@SpringBootTest
class Sb1023ApplicationTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    void test_save(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // em.persist() 사용하여 데이터를 입력해 보세요.
        Team team1 = new Team("team1","팀1");
        em.persist(team1);

        Member member1 = new Member("member1", "멤버1");
        member1.setTeam(team1);
        em.persist(member1);

        tx.commit();
    }

    @Test
    void test_find(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        String jpql = "select m from Member m join m.team t where t.name=:teamName ";
        List<Member> resultList = em.createQuery(jpql,Member.class)
                .setParameter("teamName","팀1")
                .getResultList();
        for (Member member : resultList) {
            System.out.println(member);
        }

        tx.commit();
    }

    @Test
    void test_find2(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member member = em.find(Member.class,"member1");
        System.out.println(member);
        System.out.println("팀이름: "+ member.getTeam().getName());

        tx.commit();
    }

    @Test
    void test_update(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Team team2 = new Team("team2","팀2");
        em.persist(team2);

        em.find(Member.class,"member1").setTeam(team2);
        tx.commit();
    }

    @Test
    void test_양방향_탐색(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Team team = em.find(Team.class,"team1");
        for (Member member : team.getMembers()) {
            System.out.println(member.getTeam().getName());
        }
//        for (Member member : team.getMembers()) {
//            System.out.println(member.getUsername());
//        }
//        team.getMembers().forEach(t-> System.out.println("member.username="+t.getUsername()));
        tx.commit();
    }

    @Test
    void test_연관된_엔티티_삭제(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

//        Team team = em.find(Team.class,"team2");
//        em.remove(team);

        Member member = em.find(Member.class,"member1");
        member.setTeam(null);
        Team team2 = em.find(Team.class,"team1");
        em.remove(team2);

        tx.commit();
    }

    @Test
    void test_순수객체() {
        Member member1 = new Member("member1","회원1");
        Member member2 = new Member("member2","회원2");
        Team team = new Team("team1","팀1");

        member1.setTeam(team);
        member2.setTeam(team);
        System.out.println(member1);
        System.out.println(member2);
    }

}
