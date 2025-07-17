package com.learning.springboot.cheklistapi.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(indexes = { @Index(name = "IDX_GUID_CK_CAT", columnList = "guid") })
public class ChecklistItemEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checklistItemId;

    private String description;

    private Boolean isCompleted;

    private LocalDate deadline;

    private LocalDate postDate;

    @ManyToOne
    private CategoryEntity category;
}
