package com.learning.springboot.cheklistapi.controller;

import com.learning.springboot.cheklistapi.dto.CategoryDTO;
import com.learning.springboot.cheklistapi.dto.ChecklistItemDTO;
import com.learning.springboot.cheklistapi.entity.CategoryEntity;
import com.learning.springboot.cheklistapi.entity.ChecklistItemEntity;
import com.learning.springboot.cheklistapi.service.ChecklistItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChecklistItemController.class)
public class ChecklistItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChecklistItemService checklistItemService;

    private ChecklistItemDTO getChecklistItemDTO(String description, Boolean isCompleted,  LocalDate deadline, String categoryName){
        return ChecklistItemDTO.builder()
                .guid(UUID.randomUUID().toString())
                .description(description)
                .isCompleted(isCompleted)
                .deadline(deadline)
                .category(CategoryDTO.builder()
                        .guid(UUID.randomUUID().toString())
                        .name(categoryName)
                        .build())
                .postedDate(LocalDate.now())
                .build();
    };

    private ChecklistItemEntity getChecklistItemEntity(Long id, String description, Boolean isCompleted, LocalDate deadline, Long categoryId,String categoryName){

        ChecklistItemEntity entity = new ChecklistItemEntity();
        entity.setIsCompleted(isCompleted);
        entity.setGuid(UUID.randomUUID().toString());
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryId(1L);
        categoryEntity.setGuid(UUID.randomUUID().toString());
        categoryEntity.setName(categoryName);
        entity.setCategory(categoryEntity);
        entity.setDeadline(deadline);
        entity.setDescription(description);
        entity.setChecklistItemId(id);
        entity.setPostDate(LocalDate.now());
        return entity;

    }


    @Test
    public void shouldCallGetAllChecklistItemsAndReturn200() throws Exception {


        List<ChecklistItemEntity> findAllData = Arrays.asList(
                getChecklistItemEntity(1L, "Item 1", false, LocalDate.of(2025, 8, 1), 1L, "Cat 1"),
                getChecklistItemEntity(2L, "Item 2", false, LocalDate.of(2025, 8, 2), 2L, "Cat 2")
        );

        when(checklistItemService.findAllChecklistItems()).thenReturn(findAllData);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/checklist-items"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].guid").isNotEmpty())
                .andExpect(jsonPath("$[0].isCompleted").value(false))
                .andExpect(jsonPath("$[0].description").value("Item 1"))
                .andExpect(jsonPath("$[0].deadline").value("2025-08-04"))
                .andExpect(jsonPath("$[0].deadline").isNotEmpty())
                .andExpect(jsonPath("$[0].category").isNotEmpty());


    }

}
