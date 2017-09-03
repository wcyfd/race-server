package com.randioo.race_server.module.fight.component.score.round;

/**
 * 游戏总结果
 * 
 * @author wcy 2017年7月11日
 *
 */
public class GameOverResult extends RoundOverResult {
    /** 杠铳 */
    public int gangChongCount;
    /** 胡的次数 */
    public int huCount;
    /** 抢杠 */
    public int qiangGangCount;
    /** 摸胡次数 */
    public int moHuCount;
    /** 抓胡次数 */
    public int zhuaHuCount;
    /** 点冲次数 */
    public int dianChong;
    /** 分数 */
    public int score;
    /** 明杠分数 */
    public int mingGangScore;
    /** 明杠次数 */
    public int mingGangCount;
    /** 暗杠分数 */
    public int darkGangScore;
    /** 暗杠次数 */
    public int darkGangCount;
    /** 补杠分数 */
    public int addGangScore;
    /** 补杠次数 */
    public int addGangCount;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GameOverResult [huCount=").append(huCount).append(", moHuCount=").append(moHuCount)
                .append(", zhuaHuCount=").append(zhuaHuCount).append(", dianChong=").append(dianChong)
                .append(", score=").append(score).append(", mingGangScore=").append(mingGangScore)
                .append(", mingGangCount=").append(mingGangCount).append(", darkGangScore=").append(darkGangScore)
                .append(", darkGangCount=").append(darkGangCount).append(", addGangScore=").append(addGangScore)
                .append(", addGangCount=").append(addGangCount).append("]");
        return builder.toString();
    }

}
