package com.venson.eduservice.controller;

import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduVideo;
import com.venson.eduservice.service.EduVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-05-11
 */
@RestController
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
@Slf4j
public class EduVideoController {

    private final EduVideoService eduVideoService;

    public EduVideoController(EduVideoService eduVideoService) {
        this.eduVideoService = eduVideoService;
    }

    @PostMapping("addVideo")
    public RMessage addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return RMessage.ok();
    }


    @DeleteMapping("{id}")
    public RMessage deleteVideo(@PathVariable String id){
        eduVideoService.removeById(id);
        return RMessage.ok();
    }

    @PutMapping("{id}")
    public RMessage updateVideo(@RequestBody EduVideo eduVideo){
        log.info("starting put");
        eduVideoService.updateById(eduVideo);
        return RMessage.ok();
    }

    @GetMapping("{id}")
    public RMessage getVideo(@PathVariable String id){
        EduVideo video = eduVideoService.getById(id);
        return RMessage.ok().data("item",video);
    }

}
