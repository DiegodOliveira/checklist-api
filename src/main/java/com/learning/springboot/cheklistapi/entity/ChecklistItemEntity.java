package com.learning.springboot.cheklistapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChecklistItemEntity extends BaseEntity{

    private Long checklistItemId;

    private String description;

    private Boolean isCompleted;

    private LocalTime deadline;

    private LocalTime postDate;

    private CategoryEntity category;
}
