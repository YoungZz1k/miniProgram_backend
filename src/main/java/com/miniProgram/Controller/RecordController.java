package com.miniProgram.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.miniProgram.Service.RecordService;
import com.miniProgram.entity.Records;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/record")
@Slf4j
public class RecordController {


    @Autowired
    private RecordService recordService;

    /**
     * 查询所有
     * @return list 返回所有记录集合
     */
    @GetMapping()
    public List<Records> getAll(@RequestParam String openId){
        LambdaQueryWrapper<Records> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Records::getId,openId);
        List<Records> list = recordService.list(queryWrapper);
        log.info(list.toString());
        return list;
    }

    @PostMapping("/login/{openId}")
    public String login(@PathVariable String openId){
        System.out.println(openId);
        return "获取openId成功!";
    }

    /**
     * 新增记录
     * @param records 前端发送的记录数据
     */
    @CrossOrigin(origins = "*")
    @PostMapping
    public void save(@RequestBody Records records){
        LambdaQueryWrapper<Records> queryWrapper = new LambdaQueryWrapper<>();
        if(records != null){
            //添加查询条件 查询用户id和关卡id是否已经有记录
            queryWrapper.eq(Records::getId,records.getId());
            queryWrapper.eq(Records::getLevel,records.getLevel());
            Records firstRecord = recordService.getOne(queryWrapper);
            if(firstRecord != null){
                //如果已经保存的记录步数大于新的记录步数
                if(firstRecord.getCount() > records.getCount()){
                    //执行更新操作
                    LambdaQueryWrapper<Records> updateWrapper = new LambdaQueryWrapper();
                    //更新数据
                    firstRecord.setCount(records.getCount());
                    firstRecord.setNowTime(records.getNowTime());
                    //设置更新条件为id 和 关卡数
                    updateWrapper.eq(Records::getId,records.getId());
                    updateWrapper.eq(Records::getLevel,records.getLevel());
                    recordService.update(firstRecord,updateWrapper);
                    log.info("修改记录成功");
                    return;
                }
                //如果查出来的记录步数与新的记录步数相同 比较时间长短
                if(firstRecord.getCount() == records.getCount()){
                    Integer firstSeconds = getSeconds(firstRecord.getNowTime());
                    Integer seconds = getSeconds(records.getNowTime());
                    if(firstSeconds > seconds){
                        //如果已保存的记录时间大于新的记录  更新
                        LambdaQueryWrapper<Records> updateWrapper = new LambdaQueryWrapper<>();
                        //设置更新条件为id 与 关卡数
                        updateWrapper.eq(Records::getId,records.getId());
                        updateWrapper.eq(Records::getLevel,records.getLevel());
                        //更新时间
                        firstRecord.setNowTime(records.getNowTime());
                        recordService.update(firstRecord,updateWrapper);
                        log.info("修改记录成功");
                        return;
                    }
                }
            }
            if(firstRecord == null){
                //此记录未保存
                recordService.saveRecord(records);
                log.info("保存记录成功");
            }
        }
    }

    /**
     * 计算秒数
     * @param nowTime 游戏时间
     * @return 返回秒数进行对比
     */
    public Integer getSeconds(LocalTime nowTime){
        String time = nowTime.toString();
        String[] split = time.split(":");
        List<Integer> list = new ArrayList();
        for (String s : split) {
            Integer value = Integer.valueOf(s);
            list.add(value);
        }
        Integer finalSeconds = list.get(0) * 60 * 60 + list.get(1) * 60 + list.get(2);
        return finalSeconds;
    }
}
