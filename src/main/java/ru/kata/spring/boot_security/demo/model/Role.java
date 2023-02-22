package ru.kata.spring.boot_security.demo.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
