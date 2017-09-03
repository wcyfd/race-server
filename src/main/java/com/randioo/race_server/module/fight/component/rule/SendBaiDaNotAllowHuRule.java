package com.randioo.race_server.module.fight.component.rule;

import org.springframework.stereotype.Component;

import com.randioo.mahjong_public_server.protocol.Entity.GameConfigData;
import com.randioo.race_server.entity.bo.Game;

@Component
public class SendBaiDaNotAllowHuRule extends AbstractRule {

    /**
     * 
     * @param gameConfigData
     * @return
     * @author wcy 2017年8月11日
     */
    public boolean sendBaiDaNotAllowHu(Game game, int card) {
        if (!this.readConfig(game)) {
            return false;
        }
        return false;
    }

    @Override
    protected boolean readConfig(Game game) {
        GameConfigData gameConfigData = game.getGameConfig();
        return gameConfigData.getBaidaZhuaHu();
    }

}
