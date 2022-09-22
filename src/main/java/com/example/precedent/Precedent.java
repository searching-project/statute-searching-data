package com.example.precedent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Precedent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max = 6)
    private String precSN;

    @Column(nullable = false)
    @Size(max = 255)
    private String caseName;

    @Column(nullable = false)
    @Size(max = 10000)
    private String caseNumber;

    @Column(nullable = false)
    @Size(max = 10)
    private String caseTypeName;

    @Column(nullable = false)
    @Size(max = 6)
    private String caseTypeCode;

    @Column(nullable = false)
    @Size(max = 255)
    private String courtName;

    @Column(nullable = false)
    @Size(max = 6)
    private String courtTypeCode;

    @Column(nullable = false)
    @Size(max = 10)
    private String judgeState;

    @Column(nullable = false)
    @Size(max = 8)
    private String judgeDate;

    @Column(nullable = false)
    @Size(max = 10)
    private String judgeType;

    @Column(nullable = false)
    @Size(max = 10000)
    private String judgeHolding;

    @Column(nullable = false)
    @Size(max = 10000)
    private String judgeReasoning;

    @Column(nullable = false)
    @Size(max = 100000)
    private String content;

    @Column(nullable = false)
    @Size(max = 10000)
    private String refArticle;

    @Column(length = 10000, nullable = false)
    @Size(max = 10000)
    private String refPrecedent;
}