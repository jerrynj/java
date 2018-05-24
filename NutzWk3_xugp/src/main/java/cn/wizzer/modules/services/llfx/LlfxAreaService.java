package cn.wizzer.modules.services.llfx;

import cn.wizzer.common.base.Service;
import cn.wizzer.modules.models.llfx.Llfx_area;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
@IocBean(args = {"refer:dao"})
public class LlfxAreaService extends Service<Llfx_area> {
	private static final Log log = Logs.get();

    public LlfxAreaService(Dao dao) {
    	super(dao);
    }
}

