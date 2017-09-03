package com.randioo.race_server.module.fight.component.cardlist;

import java.util.List;

import com.randioo.race_server.entity.bo.Game;
import com.randioo.race_server.entity.po.CardSort;

public interface CardList {
    public void check(Game game, List<CardList> cardLists, CardSort cardSort, int card, List<CardList> showCardList,
            boolean isMine);

    public List<Integer> getCards();

    public int getTargetSeat();

    public void setTargetSeat(int targetSeat);
}
