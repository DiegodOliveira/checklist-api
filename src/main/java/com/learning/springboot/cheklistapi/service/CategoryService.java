package com.learning.springboot.cheklistapi.service;

import com.learning.springboot.cheklistapi.entity.CategoryEntity;
import com.learning.springboot.cheklistapi.exceptions.ResourceNotFoundException;
import com.learning.springboot.cheklistapi.repository.CategoryRepository;
import com.learning.springboot.cheklistapi.repository.ChecklistItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;

import java.util.UUID;

@Slf4j
@Service
public class CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    private ChecklistItemRepository checklistItemRepository;
    private CategoryRepository categoryRepository;

    public CategoryService(ChecklistItemRepository checklistItemRepository, CategoryRepository categoryRepository){
        this.checklistItemRepository = checklistItemRepository;
        this.categoryRepository = categoryRepository;
    }

    public CategoryEntity addNewCategory(String name){

        if(!StringUtils.hasText(name)){
            throw new IllegalArgumentException("Category name cannot be empty or null");
        }

        CategoryEntity newCategory = new CategoryEntity();
        newCategory.setGuid(UUID.randomUUID().toString());
        newCategory.setName(name);

        log.debug("Adding a new category with name [ name = {} ] ", name);

        return this.categoryRepository.save(newCategory);
    }

    public CategoryEntity updateCategory(String guid, String name){
        if(guid == null || !StringUtils.hasText(name)){
            throw new IllegalArgumentException("Invalid parameters provided to update a category");
        }

        CategoryEntity retrievedCategory = this.categoryRepository.findByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Catgeory not found."));

        retrievedCategory.setName(name);
        log.debug("Updating category [guid = {}, newName = {} ]", guid, name);

        return this.categoryRepository.save(retrievedCategory);
    }

}
