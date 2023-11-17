package com.miniProgram.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miniProgram.entity.Records;

import java.util.List;

public interface RecordService extends IService<Records> {

    public List<Records> test();

    public void saveRecord(Records records);
}
