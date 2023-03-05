package ru.practicum.ewm.category.model;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class CategoryDto {

    Long id;

    @NotBlank
    String name;
}
