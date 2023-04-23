package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Team;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class TeamRepositoryTest {

    @Autowired TeamRepository teamRepository;

    @Test
    public void repoCRUD(){
        Team team1 = Team.builder().name("TeamA").build();
        Team team2 = Team.builder().name("TeamB").build();
        teamRepository.save(team1);
        teamRepository.save(team2);

        Team findTeam1 = teamRepository.findById(team1.getId()).get();
        Team findTeam2 = teamRepository.findById(team2.getId()).get();

        assertThat(findTeam1).isEqualTo(team1);
        assertThat(findTeam2).isEqualTo(team2);


    }




}