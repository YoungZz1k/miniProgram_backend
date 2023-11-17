package com.miniProgram.Service.ServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miniProgram.Service.RecordService;
import com.miniProgram.entity.Records;
import com.miniProgram.mapper.RecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Records> implements RecordService {
    @Override
    public List<Records> test() {
        List<Records> list = super.list();
        return list;
    }

    @Override
    public void saveRecord(Records records) {
        super.save(records);
    }
}
