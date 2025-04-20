package com.learning.springboot.cheklistapi.controller;

import com.learning.springboot.cheklistapi.dto.ChecklistItemDTO;
import com.learning.springboot.cheklistapi.service.ChecklistItemService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController("/api/v1/checklist-items")
public class ChecklistItemController {

    private ChecklistItemService checklistItemService;

    public ChecklistItemController(ChecklistItemService checklistItemService){
        this.checklistItemService = checklistItemService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ChecklistItemDTO>> getAllChecklistItems(){

        List<ChecklistItemDTO> resp =  StreamSupport.stream(this.checklistItemService.findAllChecklistItems().spliterator(), false).
                map(checklistItemEntity -> ChecklistItemDTO.toDTO(checklistItemEntity)).
                collect(Collectors.toList());



        return null;
    }

}
