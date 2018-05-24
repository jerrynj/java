package net.wendal.nutzbook.net.wendal.nutzbook.quartz.job;

import cn.wizzer.modules.models.llfx.Llfx_area;
import net.wendal.nutzbook.bean.User;
import oracle.jdbc.driver.DatabaseError;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/30.
 */
public class CleanNonActiveUserJob  implements Job {
    private static final Log log = Logs.get();

    @Inject
    protected Dao dao;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.debug("clean Non-Active User , start");
        Date deadtime = new Date(System.currentTimeMillis() - 24*60*60*1000L);
        Cnd cnd = Cnd.where("shopid", ">", 11).and("createTime", "<", deadtime).and(Cnd.exps("emailChecked", "=", false).or("email", "IS", null));

        int deleted = dao.clear(Llfx_area.class, cnd);
        log.debugf("delete %d user_table", deleted);

//        Sql sql = Sqls.create("delete from user_table where id > 10  and ct < @deadtime");
//        sql.vars().set("user_table", dao.getEntity(User.class).getTableName());
//        sql.params().set("deadtime", deadtime);
//        dao.execute(sql);
//        log.debugf("delete %d User", sql.getUpdateCount());
//
//        log.debug("clean Non-Active User , Done");



    }
}
