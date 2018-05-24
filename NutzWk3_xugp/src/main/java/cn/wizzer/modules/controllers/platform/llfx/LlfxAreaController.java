package cn.wizzer.modules.controllers.platform.llfx;

import cn.wizzer.common.annotation.SLog;
import cn.wizzer.common.base.Result;
import cn.wizzer.common.filter.PrivateFilter;
import cn.wizzer.common.page.DataTableColumn;
import cn.wizzer.common.page.DataTableOrder;
import cn.wizzer.modules.models.llfx.Llfx_area;
import cn.wizzer.modules.services.llfx.LlfxAreaService;
import net.wendal.nutzbook.bean.User;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@IocBean
@At("/platform/llfx/area1")
@Filters({@By(type = PrivateFilter.class)})
public class LlfxAreaController {
    private static final Log log = Logs.get();
    @Inject
    private LlfxAreaService llfxArea1Service;

    @Inject // 注入同名的一个ioc对象
    protected Dao dao;

    @At("")
    @Ok("beetl:/platform/llfx/area1/index.html")
    @RequiresAuthentication
    public void index() {

    }

    @At
    @Ok("json:full")
    @RequiresAuthentication
    public Object data(@Param("length") int length, @Param("start") int start, @Param("draw") int draw, @Param("::order") List<DataTableOrder> order, @Param("::columns") List<DataTableColumn> columns) {
        Cnd cnd = Cnd.NEW();
        return llfxArea1Service.data(length, start, draw, order, columns, cnd, null);
    }

    @At
    @Ok("beetl:/platform/llfx/area1/add.html")
    @RequiresAuthentication
    public void add() {

    }

    @At
    @Ok("json")
    @SLog(tag = "新建Llfx_area", msg = "")
    public Object addDo1(@Param("..") Llfx_area llfxArea1, HttpServletRequest req) {
        try {
            llfxArea1Service.insert(llfxArea1);
            return Result.success("system.success");
        } catch (Exception e) {
            return Result.error("system.error");
        }
    }

    @At
    @Ok("json")
    @SLog(tag = "新建Llfx_area", msg = "")
    public Object addDo(@Param("..")Llfx_area llfxArea1) { // 两个点号是按对象属性一一设置
        NutMap re = new NutMap();
        String msg = check(llfxArea1, true);
        if (msg != null){
            return re.setv("ok", false).setv("msg", msg);
        }

        llfxArea1 = dao.insert(llfxArea1);
        return Result.success("system.success");
    }

    @At("/edit/?")
    @Ok("beetl:/platform/llfx/area1/edit.html")
    @RequiresAuthentication
    public Object edit(String id) {
        return llfxArea1Service.fetch(id);
    }

    @At
    @Ok("json")
    @SLog(tag = "修改Llfx_area", msg = "ID:${args[0].id}")
    public Object editDo(@Param("..") Llfx_area llfxArea1, HttpServletRequest req) {
        try {

            llfxArea1.setOpAt((int) (System.currentTimeMillis() / 1000));
            llfxArea1Service.updateIgnoreNull(llfxArea1);
            return Result.success("system.success");
        } catch (Exception e) {
            return Result.error("system.error");
        }
    }


    @At({"/delete","/delete/?"})
    @Ok("json")
    @SLog(tag = "删除Llfx_area", msg = "ID:${args[2].getAttribute('id')}")
    public Object delete(String id, @Param("ids") String[] ids ,HttpServletRequest req) {
        try {
            if(ids!=null&&ids.length>0){
                llfxArea1Service.delete(ids);
                req.setAttribute("id", org.apache.shiro.util.StringUtils.toString(ids));
            }else{
                llfxArea1Service.delete(id);
                req.setAttribute("id", id);
            }
            return Result.success("system.success");
        } catch (Exception e) {
            return Result.error("system.error");
        }
    }


    @At("/detail/?")
    @Ok("beetl:/platform/llfx/area1/detail.html")
    @RequiresAuthentication
    public Object detail(String id) {
        if (!Strings.isBlank(id)) {
            return llfxArea1Service.fetch(id);

        }
        return null;
    }


    @At
    @Ok("json")
    public int count() { // 统计用户数的方法,算是个测试点
        int cnt =  dao.count(Llfx_area.class);
        log.debug("count :" + cnt);
        return cnt;
    }

    @At
    @Ok(">>:/") // 跟其他方法不同,这个方法完成后就跳转首页了
    public void logout(HttpSession session) {
        session.invalidate();
    }

    protected String check(Llfx_area user, boolean create) {
        if (user == null) {
            return "空对象";
        }
        if (create) {
            if (Strings.isBlank(user.getTitle()) || Strings.isBlank(user.getShopid()))
                return "title/shopid不能为空";
        } else {
            if (Strings.isBlank(user.getContent()))
                return "content不能为空";
        }

        return null;
    }

    @At
    public Object query(@Param("name")String name, @Param("..")Pager pager) {
        Cnd cnd = Strings.isBlank(name)? null : Cnd.where("name", "like", "%"+name+"%");
        QueryResult qr = new QueryResult();
        qr.setList(dao.query(User.class, cnd, pager));
        pager.setRecordCount(dao.count(User.class, cnd));
        qr.setPager(pager);
        return qr; //默认分页是第1页,每页20条
    }


}
