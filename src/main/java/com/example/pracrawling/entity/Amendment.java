package com.example.pracrawling.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Amendment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String type;

    @Lob
    @Column
    private String content;

    @Lob
    @Column
    private String reasonContent;

    @JoinColumn(name = "LAW_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Law law;
}
