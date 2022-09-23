package com.example.file_convertor.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
public class Game {
    private String name;
    private Integer price;
    private Boolean available;
    private Double version;
    private List<String> functions;
}
