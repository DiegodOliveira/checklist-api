package com.learning.springboot.cheklistapi.dto;

import com.learning.springboot.cheklistapi.entity.ChecklistItemEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
@Builder
@Getter
public class ChecklistItemDTO {

    private String guid;

    private String description;

    private Boolean isCompleted;

    private LocalDate deadline;

    private LocalDate postedDate;

    private CategoryDTO category;

    public static ChecklistItemDTO toDTO(ChecklistItemEntity checklistItemEntity) {

         return  ChecklistItemDTO.builder()
                .guid(checklistItemEntity.getGuid())
                .description(checklistItemEntity.getDescription())
                .deadline(checklistItemEntity.getDeadline())
                .postedDate(checklistItemEntity.getPostDate())
                .isCompleted(checklistItemEntity.getCompleted())
                 .category(checklistItemEntity.getCategory() != null ?
                         CategoryDTO.builder()
                                 .guid(checklistItemEntity.getCategory().getGuid())
                                 .name(checklistItemEntity.getCategory().getName())
                                 .build() :
                         null)
                .build();
    }
}
