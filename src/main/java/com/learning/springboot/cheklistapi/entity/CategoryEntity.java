package com.learning.springboot.cheklistapi.entity;




import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(indexes = { @Index(name = "IDX_GUID_CAT", columnList = "guid") })
public class CategoryEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CategoryId;

    private String name;

    private List<ChecklistItemEntity> checklistItems;
}
