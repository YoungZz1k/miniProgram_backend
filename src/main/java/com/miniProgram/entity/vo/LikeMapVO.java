package com.miniProgram.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeMapVO {

    // 地图id
    private Integer id;

    // 用户id
    private String openId;

}
