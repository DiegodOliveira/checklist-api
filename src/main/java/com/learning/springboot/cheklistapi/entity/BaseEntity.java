package com.learning.springboot.cheklistapi.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity {

    private String guid;

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
