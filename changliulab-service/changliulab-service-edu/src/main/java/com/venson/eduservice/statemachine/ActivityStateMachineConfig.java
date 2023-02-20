package com.venson.eduservice.statemachine;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.venson.eduservice.entity.EduActivity;
import com.venson.eduservice.entity.EduActivityMarkdown;
import com.venson.eduservice.entity.EduActivityPublished;
import com.venson.eduservice.entity.EduActivityPublishedMd;
import com.venson.eduservice.entity.dto.ReviewApplyVo;
import com.venson.eduservice.entity.enums.ReviewAction;
import com.venson.eduservice.entity.enums.ReviewStatus;
import com.venson.eduservice.entity.statemachine.StateMachineConstant;
import com.venson.eduservice.mapper.EduActivityMapper;
import com.venson.eduservice.mapper.EduActivityMarkdownMapper;
import com.venson.eduservice.mapper.EduActivityPublishedMapper;
import com.venson.eduservice.mapper.EduActivityPublishedMdMapper;
import com.venson.eduservice.service.StateMachineService;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.ObjectUtils;


@Configuration
public class ActivityStateMachineConfig {

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private StateMachineService stateMachineService;

    @Autowired
    private EduActivityMapper activityMapper;
    @Autowired
    private EduActivityMarkdownMapper activityMarkdownMapper;
    @Autowired
    private EduActivityPublishedMapper activityPublishedMapper;
    @Autowired
    private EduActivityPublishedMdMapper activityPublishedMdMapper;

    @Bean
    public StateMachine<ReviewStatus, ReviewAction, ReviewApplyVo> activityStateMachine() {
        StateMachineBuilder<ReviewStatus, ReviewAction, ReviewApplyVo> builder = StateMachineBuilderFactory.create();

        // request activity review for new review
        builder.externalTransitions().fromAmong(ReviewStatus.NONE, ReviewStatus.FINISHED).to(ReviewStatus.APPLIED)
                .on(ReviewAction.REQUEST).when(requestNoneActivityCon())
                .perform(doNoneActivityRequest());

        // request activity review for rejected review
        builder.externalTransition().from(ReviewStatus.REJECTED).to(ReviewStatus.APPLIED)
                .on(ReviewAction.REQUEST).when(requestRejectActivityCon())
                .perform(doRequestRejectActivity());

        // pass activity review
        builder.externalTransition().from(ReviewStatus.APPLIED).to(ReviewStatus.FINISHED)
                .on(ReviewAction.PASS).when(reviewActivityCon())
                .perform(doPassActivity());

        builder.externalTransition().from(ReviewStatus.APPLIED).to(ReviewStatus.REJECTED)
                .on(ReviewAction.REJECT).when(reviewActivityCon())
                .perform(doRejectActivity());

        return builder.build(StateMachineConstant.ACTIVITY_STATE_MACHINE_ID);
    }
    @PreAuthorize("hasAuthority('activity.review.apply')")
    private Condition<ReviewApplyVo> requestNoneActivityCon() {
        return (ctx) -> {
            EduActivity activity = activityMapper.selectById(ctx.getId());
            return !ObjectUtils.isEmpty(activity) && activity.getReview() == ctx.getFrom();
        };
    }

    private Action<ReviewStatus, ReviewAction, ReviewApplyVo> doNoneActivityRequest() {
        return (from, to, action, ctx) -> transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(@NonNull TransactionStatus status) {
                stateMachineService.requestNoneReview(ctx);

                LambdaUpdateWrapper<EduActivity> wrapper = Wrappers.lambdaUpdate(EduActivity.class);
                wrapper.eq(EduActivity::getId,ctx.getId())
                        .set(EduActivity::getReview, ReviewStatus.APPLIED);
                activityMapper.update(null, wrapper);

            }
        });
    }


    @PreAuthorize("hasAuthority('activity.review.reject')")
    private Condition<ReviewApplyVo> requestRejectActivityCon() {
        return (ctx) -> {
            EduActivity activity = activityMapper.selectById(ctx.getId());
            return !ObjectUtils.isEmpty(activity) && activity.getReview() == ctx.getFrom();
        };
    }

    private Action<ReviewStatus, ReviewAction, ReviewApplyVo> doRequestRejectActivity() {
        return (from, to, action, ctx) ->
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(@NonNull TransactionStatus status) {
                        stateMachineService.requestRejectedReview(ctx);
                        LambdaUpdateWrapper<EduActivity> wrapper = Wrappers.lambdaUpdate(EduActivity.class);
                        wrapper.eq(EduActivity::getId, ctx.getId()).eq(EduActivity::getReview,ctx.getFrom())
                                .set(EduActivity::getReview, ctx.getTo());
                        activityMapper.update(null, wrapper);

                    }
                });
//                activityService.requestRejectedActivityReview(ctx);
    }

    private Condition<ReviewApplyVo> reviewActivityCon() {
        return (ctx) -> {
            EduActivity activity = activityMapper.selectById(ctx.getId());
            return !ObjectUtils.isEmpty(activity) && activity.getReview() == ctx.getFrom();
        };
    }

    private Action<ReviewStatus, ReviewAction, ReviewApplyVo> doPassActivity() {
        return (from, to, action, ctx) -> {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(@NonNull TransactionStatus status) {

                    stateMachineService.doAlterReviewByCtx(ctx);
                    Long activityId =ctx.getId();
                    // remove activity if marked isRemoveAfterReview
                    EduActivity activity = activityMapper.selectById(activityId);
                    if (activity.getIsRemoveAfterReview()) {
                        activityMapper.deleteById(activityId);
                        activityMarkdownMapper.deleteById(activityId);
                        activityPublishedMapper.deleteById(activityId);
                        activityPublishedMdMapper.deleteById(activityId);
                    } else {
                        activity.setReview(ctx.getTo());
                        activityMapper.updateById(activity);
                        // save or update published activity.
                        EduActivityMarkdown activityMarkdown = activityMarkdownMapper.selectById(activityId);
                        EduActivityPublished published = activityPublishedMapper.selectById(activityId);
                        EduActivityPublishedMd publishedMd = activityPublishedMdMapper.selectById(activityId);
                        if (ObjectUtils.isEmpty(published)) {
                            published = new EduActivityPublished();
                            publishedMd = new EduActivityPublishedMd();
                        }
                        BeanUtils.copyProperties(activity, published);
//                        published.setViewCount(null);
                        BeanUtils.copyProperties(activityMarkdown, publishedMd);

                        if(published.getId()==null){
                            activityPublishedMapper.insert(published);
                            activityPublishedMdMapper.insert(publishedMd);
                        }else {
                            activityPublishedMapper.updateById(published);
                            activityPublishedMdMapper.updateById(publishedMd);

                        }
                    }
                }
            });

//            activityService.passActivity(ctx);
        };
    }

    private Action<ReviewStatus, ReviewAction, ReviewApplyVo> doRejectActivity() {
        return (from, to, action, ctx) -> transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(@NonNull TransactionStatus status) {

                stateMachineService.doAlterReviewByCtx(ctx);
                // set review status to rejected and  isRemoveAfterReview to false,
                LambdaUpdateWrapper<EduActivity> wrapper = Wrappers.lambdaUpdate(EduActivity.class);
                wrapper.eq(EduActivity::getId,ctx.getId())
                        .eq(EduActivity::getReview, ReviewStatus.APPLIED)
                        .set(EduActivity::getIsRemoveAfterReview, false)
                        .set(EduActivity::getReview, ReviewStatus.REJECTED);
                activityMapper.update(null, wrapper);

            }
        });
//        activityService.rejectActivity(ctx);
    }
}
