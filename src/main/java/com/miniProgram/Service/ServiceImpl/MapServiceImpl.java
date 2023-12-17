package com.miniProgram.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miniProgram.Service.MapService;
import com.miniProgram.Service.RecordService;
import com.miniProgram.common.ApiOperationLog;
import com.miniProgram.entity.LikedRel;
import com.miniProgram.entity.Map;
import com.miniProgram.entity.Records;
import com.miniProgram.entity.vo.LikeMapVO;
import com.miniProgram.entity.vo.SaveMapVO;
import com.miniProgram.mapper.LikedMapper;
import com.miniProgram.mapper.MapMapper;
import com.miniProgram.mapper.RecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class MapServiceImpl extends ServiceImpl<MapMapper, Map> implements MapService {

    @Autowired
    private MapMapper mapMapper;

    @Autowired
    private LikedMapper likedMapper;

    @Override
    @ApiOperationLog(description = "保存新地图")
    public String saveMap(SaveMapVO map) {
        if (map == null) {
            log.info("===> map为空 当前值===> {}", map);
            return "添加失败";
        }
        if (StringUtils.isBlank(map.getId()) || "Undefined".equals(map.getId()) || "undefined".equals(map.getId())) {
            log.info("===> openId获取错误  当前值===> {}", map.getId());
            return "请先登录";
        }
        // VO转DO
        Map mapDO = Map.builder()
                .openId(map.getId())
                .mapData(map.getMapp())
                .createTime(map.getDate())
                .mapName(map.getMapname())
                .likes(0)
                .build();

        boolean save = save(mapDO);

        return save ? "添加地图成功！" : "添加地图失败!";
    }

    @Override
    @ApiOperationLog(description = "根据用户openId获取所创作的地图信息")
    public List<Map> getMapById(String id) {
        if (StringUtils.isBlank(id) || "undefined".equals(id)) {
            log.info("===> openId获取错误  当前值===> {}", id);
            return null;
        }

        // 进行查询
        LambdaQueryWrapper<Map> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Map::getOpenId,id).orderByDesc(Map::getLikes);
        List<Map> maps = mapMapper.selectList(wrapper);

        if (maps.size() == 0 || maps == null) {
            log.info("===> 数据库中无地图数据");
            return null;
        }

        return maps;
    }


    @Override
    @ApiOperationLog(description = "根据地图id删除地图")
    public String deleteMapById(String id) {
        // 根据id删除地图
        int i = mapMapper.deleteById(id);

        if (i == 1) {
            log.info("===> 删除地图成功 ===> {}", id);
        } else {
            log.info("===> 删除地图失败 ===> {}", id);
        }

        return i == 1 ? "删除成功" : "删除失败";
    }

    /**
     * 点赞
     *
     * @param map
     * @return
     */
    @Override
    @ApiOperationLog(description = "对地图进行点赞/取消点赞")
    @Transactional
    public String isLiked(LikeMapVO map) {
        if (map == null) {
            log.info("===> 参数为空 ===> ");
            return "参数错误";
        }
        Integer mapId = map.getId();
        String openId = map.getOpenId();

        // 通过mapid 和 openid查询是否点赞 未点赞执行插入一条数据
        LikedRel likedData = likedMapper.getByMapIdAndOpenId(mapId, openId);

        // 为空 执行插入
        if (likedData == null) {
            // 构造bean
            LikedRel likedRel = LikedRel.builder()
                    .status(1)
                    .mapId(mapId)
                    .openId(openId)
                    .build();

            int insert = likedMapper.insert(likedRel);

            if (insert == 0) {
                log.info("===> 插入点赞数据失败 ===> {}", likedRel.toString());
                return "未知错误";
            } else {
                // 插入成功
                log.info("===>  插入点赞数据成功 ===> {}", likedRel.toString());

                // 更新map里的点赞数量
                Map mapData = mapMapper.selectById(mapId);
                mapData.setLikes(mapData.getLikes() + 1);

                // 执行更新操作
                int count = mapMapper.updateById(mapData);
                if (count == 1) {
                    log.info("===> 更新地图点赞数据成功 ===> {}", map.toString());
                    return "点赞成功";
                } else {
                    log.info("===> 更新地图点赞数据失败 ===> {}", map.toString());
                    return "未知错误";
                }
            }
        }

        // 不为空 查看是否点赞
        Integer status = likedData.getStatus();
        // 取反
        likedData.setStatus(status == 0 ? 1 : 0);
        // 更新关联表
        likedMapper.updateById(likedData);
        log.info("===> 更新点赞关联表数据 ===>{}",likedData.toString());

        Map mapData = mapMapper.selectById(mapId);
        if (status == 0) {
            // 需要点赞
            // 更新map里的点赞数量
            mapData.setLikes(mapData.getLikes() + 1);

            // 执行更新操作
            int count = mapMapper.updateById(mapData);
            if (count == 1) {
                log.info("===> 更新地图点赞数据成功 ===> {}", map.toString());
                return "点赞成功";
            } else {
                log.info("===> 更新地图点赞数据失败 ===> {}", map.toString());
                return "未知错误";
            }
        } else {
            // 取消点赞
            // 更新map里的点赞数量
            mapData.setLikes(mapData.getLikes() - 1);

            // 执行更新操作
            int count = mapMapper.updateById(mapData);
            if (count == 1) {
                log.info("===> 更新地图点赞数据成功 ===> {}", map.toString());
                return "取消点赞成功";
            } else {
                log.info("===> 更新地图点赞数据失败 ===> {}", map.toString());
                return "未知错误";
            }
        }

    }
}
