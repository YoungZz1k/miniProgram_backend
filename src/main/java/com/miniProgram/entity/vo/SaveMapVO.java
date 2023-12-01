package com.miniProgram.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveMapVO {

    private String id;

    private String mapp;

    private LocalDateTime date;

    private String mapname;
}
