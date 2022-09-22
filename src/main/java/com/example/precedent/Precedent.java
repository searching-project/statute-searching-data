package com.example.precedent;

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
public class Precedent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 6, nullable = false)
    private String precSN;

    @Column(length = 1000, nullable = false)
    private String caseName;

    @Lob
    @Column(nullable = false)
    private String caseNumber;

    @Column(length = 10, nullable = false)
    private String caseTypeName;

    @Column(length = 6, nullable = false)
    private String caseTypeCode;

    @Column(length = 255, nullable = false)
    private String courtName;

    @Column(length = 6, nullable = false)
    private String courtTypeCode;

    @Column(length = 10, nullable = false)
    private String judgeState;

    @Column(length = 8, nullable = false)
    private String judgeDate;

    @Column(length = 10, nullable = false)
    private String judgeType;

    @Lob
    @Column(nullable = false)
    private String judgeHolding;

    @Lob
    @Column(nullable = false)
    private String judgeReasoning;

    @Lob
    @Column(nullable = false)
    private String content;

    @Lob
    @Column(nullable = false)
    private String refArticle;

    @Lob
    @Column(nullable = false)
    private String refPrecedent;
}