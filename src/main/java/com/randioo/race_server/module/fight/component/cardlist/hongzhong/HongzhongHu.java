package com.randioo.race_server.module.fight.component.cardlist.hongzhong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.randioo.mahjong_public_server.protocol.Entity.GameConfigData;
import com.randioo.race_server.entity.bo.Game;
import com.randioo.race_server.entity.po.CardSort;
import com.randioo.race_server.module.fight.component.cardlist.CardList;
import com.randioo.race_server.module.fight.component.cardlist.Hu;
import com.randioo.race_server.module.fight.component.cardlist.ZLPBaiDaHu;
import com.randioo.race_server.util.Lists;
import com.randioo.race_server.util.Sets;
import com.randioo.randioo_server_base.template.Ref;

public class HongzhongHu extends Hu {
    private Logger logger = LoggerFactory.getLogger(ZLPBaiDaHu.class.getSimpleName());

    private boolean ifHuDirectHu = true;

    @Override
    public void check(Game game, List<CardList> cardLists, CardSort cardSort, int card, List<CardList> showCardList,
            boolean isMine) {
        GameConfigData gameConfigData = game.getGameConfig();

        // 如果手上有四个红中直接胡
        int baidaCard = game.getRule().getBaidaCard(game);

        if (baidaCard == card && !isMine) {
            return;
        }

        if (!cardSort.get(3).contains(baidaCard)) {
            boolean hasHu = this.checkHu(gameConfigData, cardSort);
            if (!hasHu) {
                return;
            }
        }

        // 这个胡是zlp想出来的，我只是负责实现，如果出现问题请找他改进算法
        HongzhongHu hu = new HongzhongHu();
        List<Integer> list = cardSort.toArray();
        hu.card = card;
        hu.isMine = isMine;
        Lists.removeElementByList(list, Arrays.asList(card));
        Collections.sort(list);
        hu.handCards.addAll(list);
        hu.showCardList.addAll(showCardList);

        cardLists.add(hu);

    }

    private boolean checkHu(GameConfigData gameConfigData, CardSort cardSort) {
        // 1.克隆牌组
        CardSort cardSort1 = cardSort.clone();

        List<Integer> l = cardSort1.toArray();
        Collections.sort(l);
        System.out.println(l);

        // 2.去除所有的白搭
        int baiDaCount = cardSort1.removeAll(801);

        // 只剩下百搭牌,肯定可以胡
        if (cardSort1.sumCard() == 0) {
            return true;
        }

        // 3.三个一样的先拿走
        List<Integer> kezi_arr = new ArrayList<>(cardSort1.get(2));
        for (int kezi : kezi_arr)
            cardSort1.remove(kezi, kezi, kezi);

        // 4.以每个数字为基准,分别从头到尾吃一遍
        Set<Integer> indexSet = new HashSet<>();
        {
            List<Integer> cards = cardSort1.toArray();
            Collections.sort(cards);

            for (int startIndex = 0; startIndex < cards.size(); startIndex++) {
                logger.debug(startIndex + "");
                int step4chiCount = kezi_arr.size();
                Ref<Integer> baiDaCountRef = new Ref<>();
                baiDaCountRef.set(baiDaCount);
                step4chiCount += getLoopChiCount(cards, baiDaCountRef, startIndex, indexSet);
                logger.debug("step4chiCount=" + step4chiCount);
                List<Integer> cloneCards = new ArrayList<>(cards);
                Lists.removeAllIndex(cloneCards, new ArrayList<>(indexSet));
                logger.debug("remain=" + cloneCards);
                if (checkOnlyJiangCards(baiDaCountRef, cloneCards)) {
                    // 可以胡
                    logger.debug("hu");
                    if (ifHuDirectHu) {
                        return true;
                    }
                }
            }

        }

        System.out.println("//////////////////////////////");

        // 5.刻子拿回来以每个数字为基准,分别从头到尾吃一遍,但有四个相同的先拿走三个
        {
            for (int kezi : kezi_arr) {
                cardSort1.addCard(kezi);
                cardSort1.addCard(kezi);
                cardSort1.addCard(kezi);
            }

            List<Integer> gangzi_arr = new ArrayList<>(cardSort1.get(3));
            for (int gangzi : gangzi_arr)
                cardSort1.remove(gangzi, gangzi, gangzi);

            List<Integer> cards = cardSort1.toArray();
            Collections.sort(cards);

            for (int startIndex = 0; startIndex < cards.size(); startIndex++) {
                int step5chiCount = kezi_arr.size();
                Ref<Integer> baiDaCountRef = new Ref<>();
                baiDaCountRef.set(baiDaCount);
                step5chiCount += getLoopChiCount(cards, baiDaCountRef, startIndex, indexSet);
                logger.debug("step5chiCount=" + step5chiCount);
                List<Integer> cloneCards = new ArrayList<>(cards);
                Lists.removeAllIndex(cloneCards, new ArrayList<>(indexSet));

                step5chiCount += check3(cloneCards);
                logger.debug("remain=" + cloneCards);
                if (checkOnlyJiangCards(baiDaCountRef, cloneCards)) {
                    // 可以胡
                    logger.debug("hu");
                    if (ifHuDirectHu) {
                        return true;
                    }
                }
            }
        }

        // 6.如果都没有胡,则先选择碰,再选择吃,三个一样的先拿走
        for (int kezi : kezi_arr)
            cardSort1.remove(kezi, kezi, kezi);

        {
            List<Integer> cards = cardSort1.toArray();
            Collections.sort(cards);
            for (int startIndex = 0; startIndex < cards.size(); startIndex++) {
                int step6pengChiCount = kezi_arr.size();
                Ref<Integer> baiDaCountRef = new Ref<>();
                baiDaCountRef.set(baiDaCount);
                step6pengChiCount += getLoopPengChiCount(cards, baiDaCountRef, startIndex, indexSet);
                logger.debug("step6pengChiCount=" + step6pengChiCount);
                List<Integer> cloneCards = new ArrayList<>(cards);
                Lists.removeAllIndex(cloneCards, new ArrayList<>(indexSet));
                logger.debug("remain=" + cloneCards);
                if (checkOnlyJiangCards(baiDaCountRef, cloneCards)) {
                    logger.debug("hu");
                    if (ifHuDirectHu) {
                        return true;
                    }
                }
            }
        }

        // 7.刻子放回去,先选择碰,再选择吃,但有四个相同的先拿走三个
        {
            List<Integer> gangzi_arr = new ArrayList<>(cardSort1.get(3));
            for (int gangzi : gangzi_arr)
                cardSort1.remove(gangzi, gangzi, gangzi);

            List<Integer> cards = cardSort1.toArray();
            Collections.sort(cards);
            for (int startIndex = 0; startIndex < cards.size(); startIndex++) {
                int step7pengChiCount = kezi_arr.size();
                Ref<Integer> baiDaCountRef = new Ref<>();
                baiDaCountRef.set(baiDaCount);
                step7pengChiCount += getLoopPengChiCount(cards, baiDaCountRef, startIndex, indexSet);
                logger.debug("step6pengChiCount=" + step7pengChiCount);
                List<Integer> cloneCards = new ArrayList<>(cards);
                Lists.removeAllIndex(cloneCards, new ArrayList<>(indexSet));
                logger.debug("remain=" + cloneCards);
                step7pengChiCount += check3(cloneCards);

                if (checkOnlyJiangCards(baiDaCountRef, cloneCards)) {
                    logger.debug("hu");
                    if (ifHuDirectHu) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 检查是否只剩下将牌
     * 
     * @param baiDaCountRef
     * @param remainCards
     * @return
     * @author wcy 2017年7月24日
     */
    private boolean checkOnlyJiangCards2(Ref<Integer> baiDaCountRef, List<Integer> remainCards) {
        // 百搭牌数量
        int baiDaCount = baiDaCountRef.get();
        if ((baiDaCount + remainCards.size()) != 2)
            return false;

        // 如果剩余两张牌，比较一下之间的大小
        if (remainCards.size() == 2) {
            int card1 = remainCards.get(0);
            int card2 = remainCards.get(1);
            if (card1 == card2) {
                return true;
            }
        }

        // 其中一张是红中,那无论如何都能胡
        boolean hasBaiDa = baiDaCount == 1;

        return hasBaiDa;
    }

    /**
     * 检查是否只剩下将牌,凯恩改进查将牌算法，原来写错的在上面
     * 
     * @param baiDaCountRef
     * @param remainCards
     * @return
     * @author wcy 2017年7月24日
     */
    private boolean checkOnlyJiangCards3(Ref<Integer> baiDaCountRef, List<Integer> remainCards) {
        int baiDaCount = baiDaCountRef.get();
        int totalCount = baiDaCount + remainCards.size();
        if ((totalCount - 2) % 3 != 0) {
            return false;
        }

        // 如果剩余两张牌，比较一下之间的大小
        if (totalCount == 2) {
            CardSort cardSort = new CardSort(4);
            cardSort.fillCardSort(remainCards);
            Set<Integer> set0 = cardSort.get(0);
            Set<Integer> set1 = cardSort.get(1);
            if (set1.size() == 1) {
                return true;
            }
            if (set0.size() == 1 && set1.size() == 0) {
                return true;
            }
            if (set0.size() == 0) {
                return true;
            }
        } else {
            if (this.dealRemainCards(baiDaCount, totalCount, remainCards)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 检查是否只剩下将牌,凯恩改进查将牌算法，原来写错的在上面
     * 
     * @param baiDaCountRef
     * @param remainCards
     * @return
     * @author wcy 2017年7月24日
     */
    private boolean checkOnlyJiangCards(Ref<Integer> baiDaCountRef, List<Integer> remainCards) {
        int baiDaCount = baiDaCountRef.get();
        int totalCount = baiDaCount + remainCards.size();
        if ((totalCount - 2) % 3 != 0) {
            return false;
        }
        // 如果只剩下两张牌
        if (baiDaCount + remainCards.size() == 2) {
            return checkOnlyTwoCards(baiDaCountRef, remainCards);
        }
        // 多于两张牌,先将对子能生成的三全部移除，然后重新检查最后两张将牌
        int startIndex = 0;
        int endIndex = remainCards.size();
        Set<Integer> indexSet = new HashSet<>();
        for (int i = startIndex; i < endIndex; i++) {
            // 如果该索引已经使用过了,则直接继续
            if (indexSet.contains(i))
                continue;
            int c1 = remainCards.get(i);

            if (i + 1 < endIndex) {
                // 找对子
                int c2 = remainCards.get(i + 1);
                if (c1 == c2) {
                    if (baiDaCountRef.get() > 0) {
                        baiDaCountRef.set(baiDaCountRef.get() - 1);
                        Sets.add(indexSet, i, i + 1);
                        continue;
                    }
                }

            }

            // 超出边界则直接跳过
            if ((c1 + 2) % 100 >= 10)
                continue;
        }

        List<Integer> cloneCards = new ArrayList<>(remainCards);
        Lists.removeAllIndex(cloneCards, new ArrayList<>(indexSet));

        return checkOnlyTwoCards(baiDaCountRef, cloneCards);

    }

    /**
     * 检查是否只有两张牌
     * 
     * @param baidaCardRef
     * @param remainCards
     * @return
     * @author wcy 2017年9月1日
     */
    private boolean checkOnlyTwoCards(Ref<Integer> baidaCardRef, List<Integer> remainCards) {
        int baiDaCount = baidaCardRef.get();
        if (baiDaCount + remainCards.size() == 2) {
            if (remainCards.size() == 1 && baiDaCount == 1) {
                return true;
            } else {
                int card0 = remainCards.get(0);
                int card1 = remainCards.get(1);
                if (card0 == card1) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * 只可能存在2 5 8 11 14张总数
     * 
     * @param totalCount
     * @param cards
     * @return
     * @author wcy 2017年8月30日
     */
    private boolean dealRemainCards(int baidaCount, int totalCount, List<Integer> cards) {
        CardSort cardSort = new CardSort(4);
        cardSort.fillCardSort(cards);
        Set<Integer> set0 = cardSort.get(0);
        Set<Integer> set1 = cardSort.get(1);

        int needBaidaCount = needBaiDaCard(totalCount);
        if (needBaidaCount <= baidaCount || needBaidaCount == -1) {
            return false;
        }

        if (set1.size() == set0.size()) {
            int count = needBaiDaCard(set1.size());
            if (count == baidaCount) {
                return true;
            }
        }
        return false;
    }

    /**
     * 剩余牌总数所需要的百搭牌
     * 
     * @param totalCount
     * @return
     * @author wcy 2017年8月30日
     */
    private int needBaiDaCard(int totalCount) {
        switch (totalCount) {
        case 2:
            return 1;
        case 5:
            return 1;
        case 8:
            return 2;
        case 11:
            return 3;
        case 14:
            return 4;
        default:
            return -1;
        }
    }

    private int check3(List<Integer> cloneCards) {
        int count = 0;
        for (int v = cloneCards.size() - 1; v >= 0; v--) {
            int remainCard = cloneCards.get(v);
            int value = Lists.containsCount(cloneCards, remainCard);
            if (value == 3) {
                count++;
                Lists.removeElementByList(cloneCards, Arrays.asList(remainCard, remainCard, remainCard));
                // 复位
                v = cloneCards.size();
            }
        }
        return count;
    }

    public int getLoopChiCount(List<Integer> cards, Ref<Integer> baiDaCountRef, int startIndex, Set<Integer> indexSet) {
        indexSet.clear();
        int count1 = getChiCountAndRecordUseIndex(cards, baiDaCountRef, startIndex, cards.size(), indexSet);
        int count2 = getChiCountAndRecordUseIndex(cards, baiDaCountRef, 0, cards.size(), indexSet);
        return count1 + count2;
    }

    /**
     * 获得吃的数量并记录使用过的位置
     * 
     * @param cards
     * @param baiDaCountRef
     * @param startIndex
     * @param endIndex
     * @param indexSet
     * @return
     * @author wcy 2017年7月21日
     */
    public int getChiCountAndRecordUseIndex(List<Integer> cards, Ref<Integer> baiDaCountRef, int startIndex,
            int endIndex, Set<Integer> indexSet) {
        int count = 0;
        for (int i = startIndex; i < endIndex; i++) {
            // 如果该索引已经使用过了,则直接继续
            if (indexSet.contains(i))
                continue;
            int c1 = cards.get(i);

            // // 超出边界则直接跳过
            // if ((c1 + 1) % 100 >= 10)
            // continue;
            //
            // if ((c1 - 1) % 100 >= 10)
            // continue;

            int c2Index = findUnuseCardIndex(cards, indexSet, i, endIndex, c1 + 1);
            int c3Index = findUnuseCardIndex(cards, indexSet, i, endIndex, c1 + 2);

            // 检查有没有这个吃
            if (c2Index >= 0 && c3Index >= 0) {
                // 加入index
                Sets.add(indexSet, i, c2Index, c3Index);
                count++;
                continue;
            } else if (c2Index == -1 && c3Index != -1) { // 如果有红中,则使用红中
                if (baiDaCountRef.get() >= 1) {
                    baiDaCountRef.set(baiDaCountRef.get() - 1);
                    Sets.add(indexSet, i, c3Index);
                    count++;
                    continue;
                }
            } else if (c2Index != -1 && c3Index == -1) {
                if (baiDaCountRef.get() >= 1) {
                    baiDaCountRef.set(baiDaCountRef.get() - 1);
                    count++;
                    Sets.add(indexSet, i, c2Index);
                    continue;
                }
            } else if (c2Index == -1 && c3Index == -1) {
                if (baiDaCountRef.get() >= 2) {
                    baiDaCountRef.set(baiDaCountRef.get() - 2);
                    count++;
                    Sets.add(indexSet, i);
                    continue;
                }
            }

        }

        return count;
    }

    /**
     * 找到没有使用过的指定对象索引
     * 
     * @param cards
     * @param indexSet 使用过的对象索引
     * @param startIndex
     * @param card
     * @return
     * @author wcy 2017年7月21日
     */
    private int findUnuseCardIndex(List<Integer> cards, Set<Integer> indexSet, int startIndex, int endIndex, int card) {
        int c2Index = Lists.indexOf(cards, startIndex, endIndex, card);
        if (c2Index == -1)
            return -1;
        while (indexSet.contains(c2Index)) {
            c2Index++;
            if (c2Index > endIndex)
                return -1;
            c2Index = Lists.indexOf(cards, c2Index, endIndex, card);
        }

        return c2Index;
    }

    public int getLoopPengChiCount(List<Integer> cards, Ref<Integer> baiDaCountRef, int startIndex,
            Set<Integer> indexSet) {
        indexSet.clear();
        int count1 = getPengAndChiCountAndRecordUnuseIndex(cards, baiDaCountRef, startIndex, cards.size(), indexSet);
        int count2 = getPengAndChiCountAndRecordUnuseIndex(cards, baiDaCountRef, 0, cards.size(), indexSet);
        return count1 + count2;
    }

    public int getPengAndChiCountAndRecordUnuseIndex(List<Integer> cards, Ref<Integer> baiDaCountRef, int startIndex,
            int endIndex, Set<Integer> indexSet) {
        int count = 0;
        for (int i = startIndex; i < endIndex; i++) {
            // 如果该索引已经使用过了,则直接继续
            if (indexSet.contains(i))
                continue;
            int c1 = cards.get(i);

            if (i + 1 < endIndex) {
                // 找对子
                int c2 = cards.get(i + 1);
                if (c1 == c2) {
                    if (baiDaCountRef.get() > 0) {
                        baiDaCountRef.set(baiDaCountRef.get() - 1);
                        Sets.add(indexSet, i, i + 1);
                        count++;
                        continue;
                    }
                }

            }

            // 超出边界则直接跳过
            if ((c1 + 2) % 100 >= 10)
                continue;
            // 找吃
            int c2Index = findUnuseCardIndex(cards, indexSet, i, endIndex, c1 + 1);
            int c3Index = findUnuseCardIndex(cards, indexSet, i, endIndex, c1 + 2);

            // 检查有没有这个吃
            if (c2Index >= 0 && c3Index >= 0) {
                // 加入index
                Sets.add(indexSet, i, c2Index, c3Index);
                count++;
                continue;
            } else if (c2Index == -1 && c3Index != -1) { // 如果有红中,则使用红中
                if (baiDaCountRef.get() >= 1) {
                    baiDaCountRef.set(baiDaCountRef.get() - 1);
                    Sets.add(indexSet, i, c3Index);
                    count++;
                    continue;
                }
            } else if (c2Index != -1 && c3Index == -1) {
                if (baiDaCountRef.get() >= 1) {
                    baiDaCountRef.set(baiDaCountRef.get() - 1);
                    Sets.add(indexSet, i, c2Index);
                    count++;
                    continue;
                }
            } else if (c2Index == -1 && c3Index == -1) {
                if (baiDaCountRef.get() >= 2) {
                    baiDaCountRef.set(baiDaCountRef.get() - 2);
                    Sets.add(indexSet, i);
                    count++;
                    continue;
                }
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return "cardList:hu=>gangkai=" + gangKai + ",isMine=" + isMine + ",card=" + card + "," + super.toString();
    }

    @Override
    public List<Integer> getCards() {
        return null;
    }

    @Override
    public boolean checkTing(Game game, CardSort cardSort, List<Integer> waitCards) {

        return false;
    }

}
