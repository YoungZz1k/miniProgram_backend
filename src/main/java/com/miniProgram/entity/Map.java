package com.miniProgram.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("map")
public class Map {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("open_id")
    private String openId;

    @TableField("map_name")
    private String mapName;

    @TableField("map_data")
    private String mapData;

    @TableField("create_time")
    private LocalDateTime createTime;

}
