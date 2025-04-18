package com.learning.springboot.cheklistapi.entity;


import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;


@Data
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

    public Long getChecklistItemId() {
        return checklistItemId;
    }

    public void setChecklistItemId(Long checklistItemId) {
        this.checklistItemId = checklistItemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }
}
