package com.randioo.race_server.module.fight.component;

import com.randioo.race_server.entity.bo.Game;

/**
 * 游戏监听器
 * 
 * @author wcy 2017年8月25日
 *
 */
public interface Flow {
    public void execute(Game game, int operateSeat);
}
