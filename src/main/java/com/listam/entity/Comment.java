package com.listam.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "comment")
@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String comment;

    @ManyToOne
    private Item item;
}
