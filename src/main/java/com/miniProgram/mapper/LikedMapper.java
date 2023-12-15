package com.miniProgram.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miniProgram.entity.LikedRel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikedMapper extends BaseMapper<LikedRel> {
    /**
     * 根据地图id和openid获取点赞数据
     * @param mapId
     * @param openId
     * @return
     */
    default LikedRel getByMapIdAndOpenId(Integer mapId,String openId){
        // 构造查询条件
        LambdaQueryWrapper<LikedRel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikedRel::getMapId,mapId).eq(LikedRel::getOpenId,openId);

        LikedRel likedRel = this.selectOne(wrapper);

        return likedRel;
    }
}
