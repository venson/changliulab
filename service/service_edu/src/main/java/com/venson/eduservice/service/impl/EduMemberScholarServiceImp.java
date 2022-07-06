package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.eduservice.entity.EduMemberScholar;
import com.venson.eduservice.mapper.EduMemberScholarMapper;
import com.venson.eduservice.service.EduMemberScholarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-06-20
 */
@Service
public class EduMemberScholarServiceImp extends ServiceImpl<EduMemberScholarMapper, EduMemberScholar> implements EduMemberScholarService {



    @Override
    public void updateMemberScholar(String scholarId, List<EduMemberScholar> memberList) {

        // get the currentMemberList from the database before update
        List<EduMemberScholar> currentMemberList = getCurrentMemberByScholarId(scholarId);
        List<String> memberIdListDb = currentMemberList.parallelStream()
                .map(EduMemberScholar::getId).collect(Collectors.toList());
        List<String> newMemberIdList = memberList.parallelStream()
                .map(EduMemberScholar::getId).collect(Collectors.toList());
        List<EduMemberScholar> newBatch;
        List<String> removeBatch;
        if(ObjectUtils.isEmpty(currentMemberList)){
            newBatch = memberList;
            removeBatch = null;
        }else{
            removeBatch = currentMemberList.stream().map(EduMemberScholar::getId)
                    .filter(id -> !newMemberIdList.contains(id)).collect(Collectors.toList());

            newBatch = memberList.stream().map(o -> new EduMemberScholar(o.getId(),scholarId))
                            .filter(o -> !memberIdListDb.contains(o.getId())).collect(Collectors.toList());

        }

        // the IDs in the currentMemberList that are not in newMemberIdList should be deleted from the database


        newBatch.forEach(baseMapper::insert);
        assert removeBatch != null;
        removeBatch.forEach(baseMapper::deleteById);

    }

    @Override
    public List<EduMemberScholar> getCurrentMemberByScholarId(String scholarId) {
        return baseMapper.selectList(new QueryWrapper<EduMemberScholar>().eq("scholar_id", scholarId));
    }

    @Override
    public void saveMemberScholar(String scholarId, List<EduMemberScholar> memberList) {
        memberList.forEach(o-> o.setScholarId(scholarId));

    }
}
