package lbeen.game.web;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

/**
 * 游戏
 * @author 李斌
 */
@At("/game/")
@IocBean
public class GameController {
    @At
    @Ok("beetl:game/game2048.html")
    public void game2048(){}
}
