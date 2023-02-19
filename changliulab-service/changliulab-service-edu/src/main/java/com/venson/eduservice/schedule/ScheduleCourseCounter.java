package com.venson.eduservice.schedule;

import com.venson.eduservice.entity.EduCourse;
import com.venson.eduservice.entity.EduCoursePublished;
import com.venson.eduservice.service.EduCoursePublishedService;
import com.venson.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@EnableScheduling
@Configuration
public class ScheduleCourseCounter {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduCoursePublishedService coursePublishedService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Scheduled(cron = "@hourly")
    public void saveViewCounters(){
        Set<String> keys = stringRedisTemplate.keys("course:counter");
        assert keys != null;
        List<String> keyList = keys.stream().toList();
        List<String> counters = stringRedisTemplate.opsForValue().multiGet(keyList);
        stringRedisTemplate.delete(keys);
        assert counters != null;
        LinkedList<EduCourse> courseList = new LinkedList<>();
        LinkedList<EduCoursePublished> coursePublishedList = new LinkedList<>();
        for( int i = 0; i< keys.size(); i++){
            EduCourse courseTemp = new EduCourse();
            courseTemp.setId(Long.valueOf(keyList.get(i)));
            courseTemp.setViewCount(Long.valueOf(counters.get(i)));
            courseList.addLast(courseTemp);
            EduCoursePublished coursePublishedTemp = new EduCoursePublished();
            coursePublishedTemp.setId(Long.valueOf(keyList.get(i)));
            coursePublishedTemp.setViewCount(Long.valueOf(counters.get(i)));
            coursePublishedList.addLast(coursePublishedTemp);
        }
        courseService.updateBatchById(courseList);
        coursePublishedService.updateBatchById(coursePublishedList);


    }
}
