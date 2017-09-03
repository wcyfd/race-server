package com.randioo.race_server.module.fight.component.score.round;

import java.util.ArrayList;
import java.util.List;

import com.randioo.race_server.entity.po.CallCardList;

/**
 * 回合结束需要的参数
 * 
 * @author wcy 2017年8月1日
 *
 */
public class RoundOverParameter {
    /** 是否要检查胡 */
    protected boolean checkHu;
    /** 最低分 */
    protected int minScore;
    /** 苍蝇分数 */
    protected int zhamaScore;
    /** 可以胡的牌 */
    protected List<CallCardList> huCallCardList = new ArrayList<>();
    /** 玩家列表 */
    protected List<String> roleIdList = new ArrayList<>();

    public boolean isCheckHu() {
        return checkHu;
    }

    public void setCheckHu(boolean checkHu) {
        this.checkHu = checkHu;
    }

    public int getMinScore() {
        return minScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    public int getZhamaScore() {
        return zhamaScore;
    }

    public void setZhamaScore(int zhamaScore) {
        this.zhamaScore = zhamaScore;
    }

    public List<CallCardList> getHuCallCardList() {
        return huCallCardList;
    }

    public List<String> getRoleIdList() {
        return roleIdList;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RoundOverParameter [checkHu=").append(checkHu).append(", minScore=").append(minScore)
                .append(", zhamaScore=").append(zhamaScore).append(", huCallCardList=").append(huCallCardList)
                .append(", roleIdList=").append(roleIdList).append("]");
        return builder.toString();
    }

}
