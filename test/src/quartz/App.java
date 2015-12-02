package quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Hello world!
 * 
 */
public class App
{
    public static void main(String[] args)
    {
        System.out.println("Hello World!");
        try
        {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();

            for (int i = 0; i < 1; i++) {
                JobDetail jobDetail = JobBuilder.newJob()
                        .withIdentity("job1_1" + i, "jgroup1")
                        .ofType(SimpleJob.class)
                        .withDescription("xxxxxxxxxxx")
                        .build();
                //                JobDetail jobdetail = new JobDetail("job1_1"+i, "jgroup1", SimpleJob.class);
                Trigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity("job1_1" + i, "jgroup1")
                        .forJob(jobDetail)
                        .withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?"))
                        .build();
                //                
                //                TriggerBuilder.newTrigger().
                //                SimpleTrigger simpleTrigger = new SimpleTrigger("trigger1"+i, "tgroup1");
                //                simpleTrigger.setStartTime(new Date());
                ////                simpleTrigger.setRepeatInterval(1000);
                ////                simpleTrigger.setRepeatCount(10);
                scheduler.scheduleJob(jobDetail, trigger);
                //                Thread.sleep(1 * 1000);
            }
            while (true) {
                System.in.read();
                System.out.println(scheduler.getCurrentlyExecutingJobs().size());
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
}