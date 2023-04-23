package study.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Team {
    @Id
    @GeneratedValue
    @Column(name="team_id")
    private Long id;
    private String name;

    //forien key가 없는쪽을 mappedBy
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }

    @Builder
    public Team(Long id, String name, List<Member> members) {
        this.id = id;
        this.name = name;
        this.members = members;
    }
}
