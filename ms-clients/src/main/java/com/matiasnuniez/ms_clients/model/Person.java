package com.matiasnuniez.msclients.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public class Person {

    private String name;
    private String gender;
    private Integer age;

    @Column(unique = true, nullable = false)
    private String identification;

    private String address;
    private String phone;
}
