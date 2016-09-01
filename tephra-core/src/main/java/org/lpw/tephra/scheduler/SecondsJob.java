package org.lpw.tephra.scheduler;

/**
 * 每秒钟执行定时器任务，每分钟执行一次任务。
 *
 * @author lpw
 */
public interface SecondsJob {
    /**
     * 执行每秒钟任务。
     */
    void executeSecondsJob();
}
