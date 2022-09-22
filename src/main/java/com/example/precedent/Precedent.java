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

    @Column(length = 6, nullable = false)
//    @Column(nullable = false)
//    @Size(max = 6)
    private String precSN;

    @Column(length = 255, nullable = false)
//    @Column(nullable = false)
//    @Size(max = 255)
    private String caseName;

    @Lob
    @Column(nullable = false)
//    @Column(nullable = false)
//    @Size(max = 10000)
    private String caseNumber;

    @Column(length = 10, nullable = false)
//    @Column(nullable = false)
//    @Size(max = 10)
    private String caseTypeName;

    @Column(length = 6, nullable = false)
//    @Column(nullable = false)
//    @Size(max = 6)
    private String caseTypeCode;

    @Column(length = 255, nullable = false)
//    @Column(nullable = false)
//    @Size(max = 255)
    private String courtName;

    @Column(length = 6, nullable = false)
//    @Column(nullable = false)
//    @Size(max = 6)
    private String courtTypeCode;

    @Column(length = 10, nullable = false)
//    @Column(nullable = false)
//    @Size(max = 10)
    private String judgeState;

    @Column(length = 8, nullable = false)
//    @Column(nullable = false)
//    @Size(max = 8)
    private String judgeDate;

    @Column(length = 10, nullable = false)
//    @Column(nullable = false)
//    @Size(max = 10)
    private String judgeType;

    @Lob
    @Column(nullable = false)
//    @Column(nullable = false)
//    @Size(max = 10000)
    private String judgeHolding;

    @Lob
    @Column(nullable = false)
//    @Column(nullable = false)
//    @Size(max = 10000)
    private String judgeReasoning;

    @Lob
    @Column(nullable = false)
//    @Column(nullable = false)
//    @Size(max = 100000)
    private String content;

    @Lob
    @Column(nullable = false)
//    @Column(nullable = false)
//    @Size(max = 10000)
    private String refArticle;

    @Lob
    @Column(nullable = false)
//    @Column(nullable = false)
//    @Size(max = 10000)
    private String refPrecedent;
}