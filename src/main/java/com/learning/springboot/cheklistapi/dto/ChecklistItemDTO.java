package com.learning.springboot.cheklistapi.dto;

import com.learning.springboot.cheklistapi.entity.ChecklistItemEntity;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public class ChecklistItemDTO {

    private String guid;

    private String description;

    private Boolean isCompleted;

    private LocalDate deadline;

    private LocalDate postDate;

    private String categoryGuid;

    public static ChecklistItemDTO toDTO(ChecklistItemEntity checklistItemEntity) {

        ChecklistItemDTO.builder()
                .guid(checklistItemEntity.getGuid())
                .description(checklistItemEntity.getDescription())
                .deadline(checklistItemEntity.getDeadline())
                .postedDate(checklistItemEntity.getPostDate())
                .isCompleted(checklistItemEntity.getCompleted())
                .categoryGuid(checklistItemEntity.getCategory().getGuid())
                .build();

        return null;
    }
}
