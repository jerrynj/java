package cn.wizzer.modules.services.llfx;

import cn.wizzer.common.base.Service;
import cn.wizzer.modules.models.llfx.Llfx_user;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
@IocBean(args = {"refer:dao"})
public class LlfxUserService extends Service<Llfx_user> {
	private static final Log log = Logs.get();

    public LlfxUserService(Dao dao) {
    	super(dao);
    }
}

