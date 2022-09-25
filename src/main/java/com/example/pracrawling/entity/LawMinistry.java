package com.example.pracrawling.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@IdClass(LawMinistryId.class)
@Table(name = "law_ministry")
@NoArgsConstructor
@Getter
public class LawMinistry {

    @Id
    @ManyToOne
    @JoinColumn(name = "law_id")
    private Law law;

    @Id
    @ManyToOne
    @JoinColumn(name = "ministry_id")
    private Ministry ministry;

    public LawMinistry(Law law, Ministry ministry) {
        this.law = law;
        this.ministry = ministry;
    }
}