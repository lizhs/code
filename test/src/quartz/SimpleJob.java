package quartz;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class SimpleJob implements Job {
    public static Long time;
    public static Integer count = 0;
    public static Integer jyjob = 0;

    public void execute(JobExecutionContext jobCtx) throws JobExecutionException
    {
        System.out.println(jobCtx.getFireInstanceId() + "Job...." + jobCtx.getTrigger().getClass().getName() + "triggered time is:" + new Date());
        count++;
        try {
            if (count < 5)
                Thread.sleep(10 * 1000);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(jobCtx.getFireInstanceId() + "Job end.....");
        if (time != null) {
            System.out.println("本次与上次的时间差：" + (System.currentTimeMillis() - time));
            if ((System.currentTimeMillis() - time) < 100) {
                jyjob++;
            }
        }

        
        System.out.println("挤压任务：" + jyjob);
        time = System.currentTimeMillis();
    }

}