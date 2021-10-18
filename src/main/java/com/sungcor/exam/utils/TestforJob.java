package com.sungcor.exam.utils;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;

public class TestforJob {
    public static void main(String[] args) {
        JobDetail job  = JobBuilder.newJob(MyJob.class).withIdentity("job1","group1").build();

    }
}
