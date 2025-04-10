package com.learning.springboot.cheklistapi.repository;

import com.learning.springboot.cheklistapi.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByName(String name);

    Optional<CategoryEntity> findByGuid(String guid);

}
