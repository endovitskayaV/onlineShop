package ru.reksoft.onlineShop.controller.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NotEmpty
public class Error {
    private String field;
    private String message;
}
