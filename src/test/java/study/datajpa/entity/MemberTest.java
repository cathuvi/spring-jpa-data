package study.datajpa.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void testEntity(){
        Team teamA = Team.builder()
                .name("teamA")
                .build();
        Team teamB = Team.builder()
                .name("teamB")
                .build();

        em.persist(teamA);
        em.persist(teamB);

        Member memeber1 = Member.builder().username("memeber1").age(10).team(teamA).build();
        Member memeber2 = Member.builder().username("memeber2").age(20).team(teamA).build();
        Member memeber3 = Member.builder().username("memeber3").age(30).team(teamB).build();
        Member memeber4 = Member.builder().username("memeber4").age(40).team(teamB).build();

        em.persist(memeber1);
        em.persist(memeber2);
        em.persist(memeber3);
        em.persist(memeber4);

        em.flush();
        em.clear();

        //확인
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

        for(Member member : members){
            System.out.println("memberName = "+member.getUsername());
            System.out.println("team = "+member.getTeam());
        }

    }
}