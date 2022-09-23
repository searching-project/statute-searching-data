package com.example.lawComponents.addendum;

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

    @Column
    private String lawId;
}