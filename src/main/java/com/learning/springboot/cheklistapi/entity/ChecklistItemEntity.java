package com.learning.springboot.cheklistapi.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(indexes = { @Index(name = "IDX_GUID_CK_CAT", columnList = "guid") })
public class ChecklistItemEntity extends BaseEntity{

    private Long checklistItemId;

    private String description;

    private Boolean isCompleted;

    private LocalTime deadline;

    private LocalTime postDate;

    private CategoryEntity category;
}
