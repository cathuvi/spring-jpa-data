package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void testMemeber(){
//        Member member = new Member("memeberA");
//        Member saveedMember = memberRepository.save(member);
//        Member findMember = memberRepository.findById(saveedMember.getId()).get();
//
//        assertThat(findMember.getId()).isEqualTo(member.getId());
//        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void findByusernameAndAgeGreaterThen(){
        Member member1 = Member.builder().username("aaa").age(10).build();
        Member member2 = Member.builder().username("aaa").age(20).build();

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("aaa", 15);
        assertThat(result.get(0).getUsername()).isEqualTo("aaa");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void findUser(){
        Member member1 = Member.builder().username("aaa").age(10).build();
        Member member2 = Member.builder().username("aaa").age(20).build();

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> findUser = memberRepository.findUser("aaa", 10);
        assertThat(findUser.get(0)).isEqualTo(member1);
    }

    @Test
    public void findUsernameList(){
        Member member1 = Member.builder().username("aaa").age(10).build();
        Member member2 = Member.builder().username("aab").age(10).build();
        Member member3 = Member.builder().username("aac").age(10).build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<String> usernameList = memberRepository.findUsernameList();
        usernameList.forEach(System.out::println);

    }

    @Test
    public void findMemberDto(){
        Team team =  Team.builder().name("teamA").build();
        teamRepository.save(team);
        
        Member member1 = Member.builder().username("aaa").age(10).build();
        member1.setTeam(team);
        memberRepository.save(member1);

        List<MemberDto> findMemberDto = memberRepository.findMemberDto();
        findMemberDto.forEach(System.out::println);


    }

    @Test
    public void findByNames(){
        Member member1 = Member.builder().username("aaa").age(10).build();
        Member member2 = Member.builder().username("aab").age(10).build();
        Member member3 = Member.builder().username("aac").age(10).build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<Member> findByNames = memberRepository.findByNames(Arrays.asList("aaa", "aab", "aac"));
        for (Member findByName : findByNames) {
            System.out.println("findByName = " + findByName.getUsername());
        }

    }

    @Test
    public void returnType(){
        Member member1 = Member.builder().username("aaa").age(10).build();
        Member member2 = Member.builder().username("aab").age(10).build();

        memberRepository.save(member1);
        memberRepository.save(member2);

        //컬렉션
        //컬렉션은 select 값이 없을때도 null Collection을 준다. 따라서 size가 0이 아니다.
        List<Member> collentionFindMember = memberRepository.findListByUsername("aaa");
        collentionFindMember.forEach(System.out::println);

        //단건
        // select 값이 없을때 null 이다.
        Member findOneMember = memberRepository.findMemberByUsername("aab");
        System.out.println("findOneMember = " + findOneMember);

        //optional
        Optional<Member> optionalfindMember = memberRepository.findOptionalByUsername("aaa");
        System.out.println("optionalfindMember.get() = " + optionalfindMember.get());
    }

    @Test
    public void paging(){
        Member member1 = Member.builder().username("aaa").age(10).build();
        Member member2 = Member.builder().username("aaa").age(10).build();
        Member member3 = Member.builder().username("aaa").age(10).build();
        Member member4 = Member.builder().username("aaa").age(10).build();
        Member member5 = Member.builder().username("aaa").age(10).build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);

        int age =10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        Page<Member> page = memberRepository.findByAge(age, pageRequest);
        List<Member> content = page.getContent();

        Page<MemberDto> toMap = page.map(m -> MemberDto.builder().id(m.getId()).username(m.getUsername()).build());


        long totalElement = page.getTotalElements();

        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    public void slice(){
        Member member1 = Member.builder().username("aaa").age(10).build();
        Member member2 = Member.builder().username("aaa").age(10).build();
        Member member3 = Member.builder().username("aaa").age(10).build();
        Member member4 = Member.builder().username("aaa").age(10).build();
        Member member5 = Member.builder().username("aaa").age(10).build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);

        int age =10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        Slice<Member> page = memberRepository.findSliceByAge(age, pageRequest);
        List<Member> content = page.getContent();


//        long totalElement = page.getTotalElements();

        assertThat(content.size()).isEqualTo(3);
//        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
//        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    public void bulkAgePlus(){
        Member member1 = Member.builder().username("member1").age(10).build();
        Member member2 = Member.builder().username("member2").age(19).build();
        Member member3 = Member.builder().username("member3").age(20).build();
        Member member4 = Member.builder().username("member4").age(21).build();
        Member member5 = Member.builder().username("member5").age(40).build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);

        int resultCnt = memberRepository.bulkAgePlus(20);
        //벌크 연산 뒤에는 영속성 context를 날려야한다.
//        em.flush();
//        em.clear();

        List<Member> findMember = memberRepository.findListByUsername("member5");
        Member member = findMember.get(0);
        System.out.println("member = " + member);


        assertThat(resultCnt).isEqualTo(3);
    }

    @Test
    public void findMemberLazy(){
        //given
        //member1 -> teamA
        //member2 -> teamB
        Team teamA =Team.builder().name("teamA").build();
        Team teamB = Team.builder().name("teamB").build();
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        memberRepository.save(Member.builder().username("member1").team(teamA).age(10).build());
        memberRepository.save(Member.builder().username("member2").team(teamB).age(10).build());

        em.flush();
        em.clear();
        //when
        //N+1 문제  -- entitiyGraph
        List<Member> members = memberRepository.findMemberEntityGraph();

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("member.teamClass = " + member.getTeam().getClass());
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }

    }

    @Test
    public void queryHint(){
        Member member1 = Member.builder().username("member1").age(10).build();
        memberRepository.save(member1);


        em.flush();
        em.clear();

        Member member11 = memberRepository.findReadOnlyByUsername("member1");


        member11.setUsername("member2");

        em.flush();

    }

    @Test
    public void lock(){
        Member member1 = Member.builder().username("member1").age(10).build();
        memberRepository.save(member1);


        em.flush();
        em.clear();

        memberRepository.findLockByusername("member1");


    }
    @Test
    public void JpaEventBaseEntity() throws InterruptedException {
        Member member1 = Member.builder().username("member1").age(10).build();
        memberRepository.save(member1);

        Thread.sleep(100);
        member1.setUsername("member2");

        em.flush();
        em.clear();

        Member findMember = memberRepository.findMemberByUsername("member2");
        System.out.println("findMember.getCreatedDate() = " + findMember.getCreatedDate());
    }

    @Test
    public void findByNativeQuery(){
        Member member1 = Member.builder().username("member1").age(10).build();
        memberRepository.save(member1);
        em.flush();
        em.clear();

        Member member11 = memberRepository.findByNativeQuery("member1");
        System.out.println("member11 = " + member11);


    }

    @Test
    public void findByNativeProjection(){
        Member member1 = Member.builder().username("member1").age(10).build();
        memberRepository.save(member1);
        Team teamA = Team.builder().name("TeamA").build();
        teamRepository.save(teamA);
        member1.setTeam(teamA);

        em.flush();
        em.clear();

        Page<MemberProjection> byNativeProjection = memberRepository.findByNativeProjection(PageRequest.of(0, 10));
        for (MemberProjection meberProjection : byNativeProjection) {
            System.out.println("meberProjection.getUsername() = " + meberProjection.getUsername());
            System.out.println("meberProjection.getTeamName() = " + meberProjection.getTeamName());
        }
    }


}