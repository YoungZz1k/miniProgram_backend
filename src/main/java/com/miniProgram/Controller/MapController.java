package com.miniProgram.Controller;

import com.miniProgram.Service.MapService;
import com.miniProgram.entity.Map;
import com.miniProgram.entity.vo.SaveMapVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/map")
public class MapController {

    @Autowired
    private MapService mapService;

    @PostMapping("/saveMap")
    public String saveMap(@RequestBody SaveMapVO map){
        return mapService.saveMap(map);
    }

    @PostMapping("/map")
    public List<Map> getMapById(@RequestParam String id){
        return mapService.getMapById(id);
    }
}
