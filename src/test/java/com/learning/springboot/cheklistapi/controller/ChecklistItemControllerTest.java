package com.learning.springboot.cheklistapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.springboot.cheklistapi.dto.CategoryDTO;
import com.learning.springboot.cheklistapi.dto.ChecklistItemDTO;
import com.learning.springboot.cheklistapi.entity.CategoryEntity;
import com.learning.springboot.cheklistapi.entity.ChecklistItemEntity;
import com.learning.springboot.cheklistapi.service.ChecklistItemService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
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

    @Autowired
    private ObjectMapper objectMapper;

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
                .andExpect(jsonPath("$[0].description").value("Item 1"));



    }

    @Test
    public void shouldCallEndpointAndAddNewChecklistItemAndReturn201() throws Exception {

        when(this.checklistItemService.addNewChecklistitem(anyString(),
                anyBoolean(), ArgumentMatchers.any(LocalDate.class), anyString())).thenReturn(
                getChecklistItemEntity(1L, "Item 1", false, LocalDate.of(2025, 8, 1), 1L, "Cat 1")
        );

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/checklist-items")
                .content(objectMapper.writeValueAsString(
                        getChecklistItemDTO("Teste", true, LocalDate.now(), "Test cat")
                        ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.guid").isNotEmpty());


    }

    @Test
    public void shouldCallEndpointAndUpdateChecklistItemAndReturn204() throws Exception {

        when(this.checklistItemService.addNewChecklistitem(anyString(),
                anyBoolean(), ArgumentMatchers.any(LocalDate.class), anyString())).thenReturn(
                getChecklistItemEntity(1L, "Item 1", false, LocalDate.of(2025, 8, 1), 1L, "Cat 1")
        );

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/checklist-items")
                        .content(objectMapper.writeValueAsString(
                                getChecklistItemDTO("Teste", true, LocalDate.now(), "Test cat")
                        ))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isNoContent());


    }

    @Test
    public void shouldCallEndpointAndDeleteChecklistItemAndReturn204() throws Exception {

        String checklistItemGuid = UUID.randomUUID().toString();

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/checklist-items/{guid}", checklistItemGuid)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isNoContent());


    }

}
