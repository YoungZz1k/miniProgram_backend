package com.miniProgram.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miniProgram.Service.MapService;
import com.miniProgram.Service.RecordService;
import com.miniProgram.entity.Map;
import com.miniProgram.entity.Records;
import com.miniProgram.entity.vo.SaveMapVO;
import com.miniProgram.mapper.MapMapper;
import com.miniProgram.mapper.RecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MapServiceImpl extends ServiceImpl<MapMapper, Map> implements MapService {

    @Autowired
    private MapMapper mapMapper;

    @Override
    public String saveMap(SaveMapVO map) {
        if (map == null){
            log.info("===> map为空");
            return "添加失败";
        }
        if (StringUtils.isBlank(map.getId()) || "Undefined".equals(map.getId()) || "undefined".equals(map.getId())){
            log.info("===> openId获取错误");
            return "请先登录";
        }
        // VO转DO
        Map mapDO = Map.builder()
                .openId(map.getId())
                .mapData(map.getMapp())
                .createTime(map.getDate())
                .mapName(map.getMapname())
                .build();

        boolean save = save(mapDO);

        log.info("===> 添加地图成功！ ===>  {}",mapDO.toString());

        return save ? "添加地图成功！":"添加地图失败!";
    }

    @Override
    public List<Map> getMapById(String id) {
        if (StringUtils.isBlank(id) || "undefined".equals(id)){
            log.info("===> openId获取错误");
            return null;
        }

        // 进行查询
        List<Map> maps = query().eq("open_id", id).list();

        if (maps.size() == 0 || maps == null){
            log.info("===> 数据库中无地图数据");
            return null;
        }

        log.info("===> mapData ===> {}",maps.toString());

        return maps;
    }
}
