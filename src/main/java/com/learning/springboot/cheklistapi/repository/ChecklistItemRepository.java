package com.learning.springboot.cheklistapi.repository;

import com.learning.springboot.cheklistapi.entity.ChecklistItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ChecklistItemRepository extends PagingAndSortingRepository<ChecklistItemEntity, Long> {
}
