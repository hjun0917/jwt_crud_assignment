package com.sparta.basic_crud.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class StatusDto {
    private String status;
    private String msg;

    public StatusDto(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
