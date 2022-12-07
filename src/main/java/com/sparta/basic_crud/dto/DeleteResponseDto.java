package com.sparta.basic_crud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteResponseDto {
    private int statusCode;
    private String msg;
}