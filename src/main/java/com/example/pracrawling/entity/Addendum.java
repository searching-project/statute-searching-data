package com.example.pracrawling.entity;

import com.example.pracrawling.entity.Law;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Addendum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String publishNumber;

    @Column
    private String publishDate;

    @Lob
    @Column
    private String content;

    @JoinColumn(name = "law_id")
    @ManyToOne(targetEntity = Law.class, fetch = FetchType.LAZY)
    private Law law;
}