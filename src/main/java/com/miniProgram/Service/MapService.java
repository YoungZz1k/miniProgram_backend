package com.miniProgram.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miniProgram.entity.Map;
import com.miniProgram.entity.Records;
import com.miniProgram.entity.vo.SaveMapVO;

import java.util.List;

public interface MapService extends IService<Map> {

    String saveMap(SaveMapVO map);

    List<Map> getMapById(String id);
}
