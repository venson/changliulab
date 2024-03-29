package com.venson.eduservice.comparator;

import com.venson.eduservice.entity.enums.ReviewStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;

@Configuration
public class ReviewStatusComparator {

    @Bean
    public Comparator<ReviewStatus> reviewComparator() {
        return Comparator.comparingInt(ReviewStatus::getReviewOrder);
    }
    @Bean
    public Comparator<ReviewStatus> viewComparator() {
        return Comparator.comparingInt(ReviewStatus::getViewOrder);
    }
}
