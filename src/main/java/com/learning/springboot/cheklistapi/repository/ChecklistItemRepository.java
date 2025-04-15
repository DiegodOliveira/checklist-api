package com.learning.springboot.cheklistapi.repository;

import com.learning.springboot.cheklistapi.entity.ChecklistItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  ChecklistItemRepository extends JpaRepository<ChecklistItemEntity, Long> {

    Optional<ChecklistItemEntity> findByGuid(String guid);

    List<ChecklistItemEntity> findByDescriptionAndIsCOmpleted(String description, Boolean isCompleted);

    List<ChecklistItemEntity> findByCategoryGuid(String guid);

}
