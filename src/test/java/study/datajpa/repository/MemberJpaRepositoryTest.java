package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    public void testMemeber(){
//        Member member = new Member("memeberA");
//        Member saveedMember = memberJpaRepository.save(member);
//        Member findMember = memberJpaRepository.find(saveedMember.getId());
//
//        assertThat(findMember.getId()).isEqualTo(member.getId());
//        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD(){
        Member member1 = Member.builder().username("member1").build();
        Member member2 = Member.builder().username("member2").build();
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);


        //단건 조회
        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

//        findMember1.setUsername("meme12111111111");

        //리스트 조회
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

//        //카운트 검증
        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);



        //삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        long deletedCount = memberJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);

    }

    @Test
    public void findByusernameAndAgeGreaterThen(){
        Member member1 = Member.builder().username("aaa").age(10).build();
        Member member2 = Member.builder().username("aaa").age(20).build();

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        List<Member> result = memberJpaRepository.findByusernameAndAgeGreaterThan("aaa", 15);
        assertThat(result.get(0).getUsername()).isEqualTo("aaa");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }


    @Test
    public void paging(){
        Member member1 = Member.builder().username("aaa").age(10).build();
        Member member2 = Member.builder().username("aaa").age(10).build();
        Member member3 = Member.builder().username("aaa").age(10).build();
        Member member4 = Member.builder().username("aaa").age(10).build();
        Member member5 = Member.builder().username("aaa").age(10).build();

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        memberJpaRepository.save(member3);
        memberJpaRepository.save(member4);
        memberJpaRepository.save(member5);

        List<Member> members = memberJpaRepository.findByPage(10, 1, 3);
        long totalCount = memberJpaRepository.totalCount(10);

        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(5);


    }


    @Test
    public void bulkAgePlus(){
        Member member1 = Member.builder().username("aaa").age(10).build();
        Member member2 = Member.builder().username("aaa").age(19).build();
        Member member3 = Member.builder().username("aaa").age(20).build();
        Member member4 = Member.builder().username("aaa").age(21).build();
        Member member5 = Member.builder().username("aaa").age(40).build();

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        memberJpaRepository.save(member3);
        memberJpaRepository.save(member4);
        memberJpaRepository.save(member5);

        int resultCnt = memberJpaRepository.bulkAgePlus(20);



        assertThat(resultCnt).isEqualTo(3);


    }



}