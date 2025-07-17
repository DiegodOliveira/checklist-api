package com.learning.springboot.cheklistapi.dto;

import com.learning.springboot.cheklistapi.entity.ChecklistItemEntity;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Builder
@Getter
public class ChecklistItemDTO {

    private String guid;

    @NotBlank(message = "Checklist item description cannot be either null or empty")
    private String description;

    @NotNull(message = "Is completed is mandatory")
    private Boolean isCompleted;

    @NotNull(message = "Deadline date is mandatory")
    private LocalDate deadline;

    @NotNull(message = "Posted date is mandatory")
    private LocalDate postedDate;

    @NotNull(message = "Category name is mandatory")
    private CategoryDTO category;

    public static ChecklistItemDTO toDTO(ChecklistItemEntity checklistItemEntity) {

         return  ChecklistItemDTO.builder()
                .guid(checklistItemEntity.getGuid())
                .description(checklistItemEntity.getDescription())
                .deadline(checklistItemEntity.getDeadline())
                .postedDate(checklistItemEntity.getPostDate())
                .isCompleted(checklistItemEntity.getIsCompleted())
                 .category(checklistItemEntity.getCategory() != null ?
                         CategoryDTO.builder()
                                 .guid(checklistItemEntity.getCategory().getGuid())
                                 .name(checklistItemEntity.getCategory().getName())
                                 .build() :
                         null)
                .build();
    }
}
