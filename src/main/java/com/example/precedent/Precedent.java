package com.example.precedent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Precedent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String precSN;

    @Column(nullable = false)
    private String caseName;

    @Column(nullable = false)
    private String caseNumber;

    @Column(nullable = false)
    private String judgeDate;

    @Column(nullable = false)
    private String courtName;

    @Column(nullable = false)
    private String courtTypeCode;

    @Column(nullable = false)
    private String caseTypeName;

    @Column(nullable = false)
    private String caseTypeCode;

    @Column(nullable = false)
    private String judgeType;

    @Column(nullable = false)
    private String judgeState;

    @Column(nullable = false)
    private String judgeHolding;

    @Column(nullable = false)
    private String judgeReasoning;

    @Column(nullable = false)
    private String refArticle;

    @Column(nullable = false)
    private String refPrecedent;

    @Column(nullable = false)
    private String content;
}