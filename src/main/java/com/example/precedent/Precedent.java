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

    @Column
    private String caseTypeName;

    @Column
    private String caseTypeCode;

    @Column
    private String courtName;

    @Column
    private String courtTypeCode;

    @Column
    private String judgeState;

    @Column
    private String judgeDate;

    @Column
    private String judgeType;

    @Lob
    @Column
    private String judgeHolding;

    @Lob
    @Column
    private String judgeReasoning;

    @Lob
    @Column
    private String content;

    @Lob
    @Column
    private String refArticle;

    @Lob
    @Column
    private String refPrecedent;
}