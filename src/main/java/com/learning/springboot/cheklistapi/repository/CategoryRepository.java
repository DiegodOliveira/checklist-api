package com.learning.springboot.cheklistapi.repository;

import com.learning.springboot.cheklistapi.entity.CategoryEntity;
import com.learning.springboot.cheklistapi.entity.ChecklistItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CategoryRepository extends PagingAndSortingRepository<ChecklistItemEntity, Long> {

    Optional<CategoryEntity> findByName(String name);

    Optional<CategoryEntity> findByGuid(String name);

}
