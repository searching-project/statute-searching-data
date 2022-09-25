package com.example.pracrawling.repository;

import com.example.pracrawling.entity.Ministry;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LawMinistryDto {
    private List<Ministry> ministries;
}
