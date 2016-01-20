package com.obsidian.octopus.resolver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex Chou
 */
public class QuartzResolver {

    private String groupName;
    private final List<Job> jobs;
    private final List<Trigger> triggers;

    public QuartzResolver() {
        this.jobs = new ArrayList<>();
        this.triggers = new ArrayList<>();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void addJob(Job job) {
        this.jobs.add(job);
    }

    public List<Trigger> getTriggers() {
        return triggers;
    }

    public void addTrigger(Trigger trigger) {
        this.triggers.add(trigger);
    }

    public static class Job {

        private String name;
        private Class clazz;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Class getClazz() {
            return clazz;
        }

        public void setClazz(Class clazz) {
            this.clazz = clazz;
        }

    }

    public static class Trigger {

        private String name;
        private String cron;
        private Integer delay;
        private Integer repeated;
        private String[] jobs;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCron() {
            return cron;
        }

        public void setCron(String cron) {
            this.cron = cron;
        }

        public Integer getDelay() {
            return delay;
        }

        public void setDelay(Integer delay) {
            this.delay = delay;
        }

        public Integer getRepeated() {
            return repeated;
        }

        public void setRepeated(Integer repeated) {
            this.repeated = repeated;
        }

        public String[] getJobs() {
            return jobs;
        }

        public void setJobs(String[] jobs) {
            this.jobs = jobs;
        }

    }

}
