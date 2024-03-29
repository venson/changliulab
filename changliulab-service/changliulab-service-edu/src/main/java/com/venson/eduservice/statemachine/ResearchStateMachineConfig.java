package com.venson.eduservice.statemachine;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.venson.eduservice.entity.*;
import com.venson.eduservice.entity.dto.ReviewApplyVo;
import com.venson.eduservice.entity.enums.ReviewAction;
import com.venson.eduservice.entity.enums.ReviewStatus;
import com.venson.eduservice.entity.statemachine.StateMachineConstant;
import com.venson.eduservice.mapper.EduResearchMapper;
import com.venson.eduservice.service.StateMachineService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.ObjectUtils;


@Configuration
public class ResearchStateMachineConfig {

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private StateMachineService stateMachineService;

    @Autowired
    private EduResearchMapper researchMapper;

    @Bean
    public StateMachine<ReviewStatus, ReviewAction, ReviewApplyVo> researchStateMachine() {
        StateMachineBuilder<ReviewStatus, ReviewAction, ReviewApplyVo> builder = StateMachineBuilderFactory.create();

        // request research review for new review
        builder.externalTransitions().fromAmong(ReviewStatus.NONE, ReviewStatus.FINISHED).to(ReviewStatus.APPLIED)
                .on(ReviewAction.REQUEST).when(requestNoneResearchCon())
                .perform(doNoneResearchRequest());

        // request research review for rejected review
        builder.externalTransition().from(ReviewStatus.REJECTED).to(ReviewStatus.APPLIED)
                .on(ReviewAction.REQUEST).when(requestRejectResearchCon())
                .perform(doRequestRejectResearch());

        // pass research review
        builder.externalTransition().from(ReviewStatus.APPLIED).to(ReviewStatus.FINISHED)
                .on(ReviewAction.PASS).when(reviewResearchCon())
                .perform(doPassResearch());

        builder.externalTransition().from(ReviewStatus.APPLIED).to(ReviewStatus.REJECTED)
                .on(ReviewAction.REJECT).when(reviewResearchCon())
                .perform(doRejectResearch());

        return builder.build(StateMachineConstant.RESEARCH_STATE_MACHINE_ID);
    }
    @PreAuthorize("hasAuthority('research.review.apply')")
    private Condition<ReviewApplyVo> requestNoneResearchCon() {
        return (ctx) -> {
            EduResearch research = researchMapper.selectById(ctx.getId());
            return !ObjectUtils.isEmpty(research) && research.getReview() == ctx.getFrom();
        };
    }

    private Action<ReviewStatus, ReviewAction, ReviewApplyVo> doNoneResearchRequest() {
        return (from, to, action, ctx) -> transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(@NonNull TransactionStatus status) {
                stateMachineService.requestNoneReview(ctx);

                LambdaUpdateWrapper<EduResearch> wrapper = Wrappers.lambdaUpdate(EduResearch.class);
                wrapper.eq(EduResearch::getId,ctx.getId())
                        .set(EduResearch::getReview, ReviewStatus.APPLIED);
                researchMapper.update(null, wrapper);

            }
        });
    }


    @PreAuthorize("hasAuthority('research.review.reject')")
    private Condition<ReviewApplyVo> requestRejectResearchCon() {
        return (ctx) -> {
            EduResearch research = researchMapper.selectById(ctx.getId());
            return !ObjectUtils.isEmpty(research) && research.getReview() == ctx.getFrom();
        };
    }

    private Action<ReviewStatus, ReviewAction, ReviewApplyVo> doRequestRejectResearch() {
        return (from, to, action, ctx) ->
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(@NonNull TransactionStatus status) {
                        stateMachineService.requestRejectedReview(ctx);
                        LambdaUpdateWrapper<EduResearch> wrapper = Wrappers.lambdaUpdate(EduResearch.class);
                        wrapper.eq(EduResearch::getId, ctx.getId()).eq(EduResearch::getReview,ctx.getFrom())
                                .set(EduResearch::getReview, ctx.getTo());
                        researchMapper.update(null, wrapper);

                    }
                });
//                researchService.requestRejectedResearchReview(ctx);
    }

    private Condition<ReviewApplyVo> reviewResearchCon() {
        return (ctx) -> {
            EduResearch research = researchMapper.selectById(ctx.getId());
            return !ObjectUtils.isEmpty(research) && research.getReview() == ctx.getFrom();
        };
    }

    private Action<ReviewStatus, ReviewAction, ReviewApplyVo> doPassResearch() {
        return (from, to, action, ctx) -> {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(@NonNull TransactionStatus status) {

                    stateMachineService.doAlterReviewByCtx(ctx);
                    Long researchId =ctx.getId();
                    // remove research if marked isRemoveAfterReview
                    EduResearch research = researchMapper.selectById(researchId);
                    if (research.getIsRemoveAfterReview()) {
                        researchMapper.deleteById(researchId);
                    } else {
                        // save or update published research.
                        research.setPublishedMd(research.getMarkdown());
                        research.setPublishedHtmlBrBase64(research.getHtmlBrBase64());
                        research.setReview(ReviewStatus.FINISHED);
                        researchMapper.updateById(research);
                    }
                }
            });

//            researchService.passResearch(ctx);
        };
    }

    private Action<ReviewStatus, ReviewAction, ReviewApplyVo> doRejectResearch() {
        return (from, to, action, ctx) -> transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(@NonNull TransactionStatus status) {

                stateMachineService.doAlterReviewByCtx(ctx);
                // set review status to rejected and  isRemoveAfterReview to false,
                LambdaUpdateWrapper<EduResearch> wrapper = Wrappers.lambdaUpdate(EduResearch.class);
                wrapper.eq(EduResearch::getId,ctx.getId())
                        .eq(EduResearch::getReview, ReviewStatus.APPLIED)
                        .set(EduResearch::getIsRemoveAfterReview, false)
                        .set(EduResearch::getReview, ReviewStatus.REJECTED);
                researchMapper.update(null, wrapper);

            }
        });
//        researchService.rejectResearch(ctx);
    }
}
