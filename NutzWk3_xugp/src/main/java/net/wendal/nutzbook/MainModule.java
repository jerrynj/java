package net.wendal.nutzbook;

/**
 * Created by Administrator on 2018/4/5.
 */

import org.nutz.mvc.annotation.*;
import org.nutz.mvc.ioc.provider.ComboIocProvider;


@SetupBy(value=MainSetup.class)
@IocBy(type=ComboIocProvider.class, args={"*js", "ioc/",
        "*anno", "net.wendal.nutzbook",
        "*tx", // 事务拦截 aop
        "*async"}) // 异步执行aop

@Modules(scanPackage=true)
public class MainModule {

    @At("/h")
    @Ok("jsp:jsp.hello")
    public String doHello() {
        return "Hi Nutz";
    }

}