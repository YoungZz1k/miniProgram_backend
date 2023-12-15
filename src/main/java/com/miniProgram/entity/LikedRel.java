package com.miniProgram.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("map_liked_rel")
public class LikedRel {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("open_id")
    private String openId;

    @TableField("map_id")
    private Integer mapId;

    @TableField("status")
    private Integer status;
}
