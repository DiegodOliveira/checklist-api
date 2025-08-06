package com.learning.springboot.cheklistapi.controller;

import com.learning.springboot.cheklistapi.dto.ChecklistItemDTO;
import com.learning.springboot.cheklistapi.dto.NewResourceDTO;
import com.learning.springboot.cheklistapi.dto.UpdateStatusDTO;
import com.learning.springboot.cheklistapi.entity.ChecklistItemEntity;
import com.learning.springboot.cheklistapi.service.ChecklistItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/checklist-items")
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



        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

    // POST
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewResourceDTO> createNewChecklistItem(@RequestBody ChecklistItemDTO checklistItemDTO){

        if(checklistItemDTO.getCategory() == null){
            throw new ValidationException("Category cannot be null");
        }

        ChecklistItemEntity newChecklistItem =  this.checklistItemService.addNewChecklistitem(
                checklistItemDTO.getDescription(), checklistItemDTO.getIsCompleted(), checklistItemDTO.getDeadline(),
                checklistItemDTO.getCategory().getGuid()
        );

        return new ResponseEntity<>(new NewResourceDTO(newChecklistItem.getGuid()), HttpStatus.CREATED);

    }

    // PUT
    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateChecklistItem(@RequestBody ChecklistItemDTO checklistItemDTO){


        if(!StringUtils.hasLength(checklistItemDTO.getGuid())){
            throw new ValidationException("Checklist item guid is mandatory");
        }
        this.checklistItemService.updateChecklistItem(checklistItemDTO.getGuid(), checklistItemDTO.getDescription(),
                checklistItemDTO.getIsCompleted(), checklistItemDTO.getDeadline(), checklistItemDTO.getCategory() != null ? checklistItemDTO.getCategory().getGuid() : null);


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // DELETE
    @DeleteMapping(value = "{guid}")
    public ResponseEntity<Void> deleteChecklistItem(@PathVariable String guid){
        this.checklistItemService.deleteChecklistItem(guid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "{guid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCompletedStatus(@PathVariable String guid, @RequestBody UpdateStatusDTO status){
        this.checklistItemService.updateChecklistItem(guid, status.isComplete);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
