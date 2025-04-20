package com.learning.springboot.cheklistapi.service;

import com.learning.springboot.cheklistapi.entity.CategoryEntity;
import com.learning.springboot.cheklistapi.entity.ChecklistItemEntity;
import com.learning.springboot.cheklistapi.exceptions.ResourceNotFoundException;
import com.learning.springboot.cheklistapi.repository.CategoryRepository;
import com.learning.springboot.cheklistapi.repository.ChecklistItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.UUID;


@Slf4j
@Service
public class ChecklistItemService {

    // private static final Logger log = LogManager.getLogger(ChecklistItemService.class);
    @Autowired
    private ChecklistItemRepository checklistItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private void validateChecklistItemData(String description, Boolean isCompleted, LocalDate deadline, String categoryGuid){
        if(!StringUtils.hasText(description)){
            throw new IllegalArgumentException("Checklist item must have a description");
        }

        if(!StringUtils.hasText(categoryGuid)){
            throw new IllegalArgumentException("Checklist item must have a description");
        }

        if(isCompleted == null){
            throw new IllegalArgumentException("Checklist item must have a flag indicating if it is completed or not");
        }

        if(deadline == null){
            throw new IllegalArgumentException("Checklist item must have a flag indicating if it is completed or not");
        }
    }

    public ChecklistItemEntity updateChecklistItem(String guid ,String description, Boolean isCompleted, LocalDate deadline, String categoryGuid){

        if(!StringUtils.hasText(guid)){
            throw new IllegalArgumentException("Guid cannot be null or empty");
        }

         ChecklistItemEntity retrievedItem = this.checklistItemRepository.findByGuid(guid).orElseThrow(() -> new ResourceNotFoundException("Checklist item not found"));

        if(!StringUtils.hasText(description)){
            retrievedItem.setDescription(description);
        }

        if(isCompleted != null){
            retrievedItem.setCompleted(isCompleted);
        }

        if(deadline != null){
            retrievedItem.setDeadline(deadline);
        }

        if(!StringUtils.hasText(categoryGuid)){
            CategoryEntity retrievedCategory = this.categoryRepository.findByGuid(categoryGuid).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            retrievedItem.setCategory(retrievedCategory);
        }

        log.debug("Updating checklist item [ checklistItem = {} ]", retrievedItem);

        return this.checklistItemRepository.save(retrievedItem);

    }

    public ChecklistItemEntity findChecklistItemByGuid(String guid){
        if(!StringUtils.hasText(guid)){
            throw new IllegalArgumentException("Guid cannot be empty or null");
        }
        return this.checklistItemRepository.findByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("ChecklistItem not found")
        );
    }


    public ChecklistItemEntity addNewChecklistitem(String description, Boolean isCompleted, LocalDate deadline, String categoryGuid){

        this.validateChecklistItemData(description, isCompleted, deadline,categoryGuid);

       CategoryEntity retrievedCategory = this.categoryRepository.findByGuid(categoryGuid).orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        ChecklistItemEntity checklistItemEntity = new ChecklistItemEntity();
        checklistItemEntity.setGuid(UUID.randomUUID().toString());
        checklistItemEntity.setDescription(description);
        checklistItemEntity.setDeadline(deadline);
        checklistItemEntity.setPostDate(LocalDate.now());
        checklistItemEntity.setCategory(retrievedCategory);

        log.debug("Adding new checklist item [ checklistItem = {} ]", checklistItemEntity);

        return checklistItemRepository.save(checklistItemEntity);
    }

    public Iterable<ChecklistItemEntity> findAllChecklistItems(){
        return this.checklistItemRepository.findAll();
    }

    public void deleteChecklistItem(String guid){
        if(!StringUtils.hasText(guid)){
            throw new IllegalArgumentException("Guid cannot be null or empty");
        }
        ChecklistItemEntity retrivedItem = this.checklistItemRepository.findByGuid(guid).orElseThrow(() -> new ResourceNotFoundException("Checklist item not found"));

        log.debug("Deleting checklist item [ guid ={} ]", guid);

        this.checklistItemRepository.delete(retrivedItem);
    }


}
