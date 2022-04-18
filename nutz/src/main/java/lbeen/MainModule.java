package lbeen;

import org.beetl.ext.nutz.BeetlViewMaker;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

/**
 * @author 李斌
 */
@IocBy(type = ComboIocProvider.class, args = {"*js", "config/ioc.js",
        "*anno", "lbeen","*jedis",
        "*tx", "*async"})
@Views(BeetlViewMaker.class)
public class MainModule {
}
