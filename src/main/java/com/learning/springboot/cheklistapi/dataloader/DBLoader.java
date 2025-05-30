package com.learning.springboot.cheklistapi.dataloader;

import com.learning.springboot.cheklistapi.entity.CategoryEntity;
import com.learning.springboot.cheklistapi.repository.CategoryRepository;
import com.learning.springboot.cheklistapi.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Profile("data-load")
@Slf4j
@Component
public class DBLoader implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void run(String... args) throws Exception{

        log.info("Populating db with categories");

        List<String> categoryNames = Arrays.asList(
                "Trabalho","Saúde","Educação","Pessoal","Outros"
        );

        for(String categoryName : categoryNames){
            Optional<CategoryEntity> catOpt = this.categoryRepository.findByName(categoryName);

            if(!catOpt.isPresent()){
                categoryService.addNewCategory(categoryName);
            }
        }

    }
}
