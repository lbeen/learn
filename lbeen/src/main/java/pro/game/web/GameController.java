package pro.game.web;

import lb.ioc.annotation.bean.Controller;
import lb.mvc.annotation.At;
import lb.mvc.annotation.Login;
import lb.mvc.annotation.Ok;

/**
 * 游戏
 * @author 李斌
 */
@Controller
@At("/game/")
public class GameController {
    @At
    @Ok("game/game2048.html")
    @Login
    public void game2048(){}
}
