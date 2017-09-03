package com.randioo.race_server.entity.po;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CardSort {
	public CardSort(int capacity) {
		for (int i = 0; i < capacity; i++)
			values.add(new TreeSet<Integer>());
	}

	private List<Set<Integer>> values = new ArrayList<>();

	public List<Set<Integer>> getList() {
		return values;
	}

	public void fillCardSort(List<Integer> cards) {
		for (int card : cards) {
			addCard(card);
		}
	}

	public void addCard(int card) {
		for (Set<Integer> set : values) {
			if (set.contains(card))
				continue;
			else {
				set.add(card);
				return;
			}
		}
	}

	public Set<Integer> get(int index) {
		return values.get(index);
	}

	public void remove(int... cards) {
		if (cards.length == 0)
			return;

		for (int card : cards) {
			remove(card);
		}
	}

	public void remove(List<Integer> cards) {
		for (int card : cards) {
			remove(card);
		}
	}

	public void remove(int card) {
		for (int i = values.size() - 1; i >= 0; i--) {
			if (values.get(i).contains(card)) {
				values.get(i).remove(card);
				break;
			}
		}
	}

	/**
	 * 移除某一张牌的所有
	 * 
	 * @param card
	 */
	public int removeAll(int card) {
		int count = 0;
		for (int i = 0; i < values.size(); i++) {
			Set<Integer> set = values.get(i);
			if (set.contains(card)) {
				set.remove(card);
				count++;
			}
		}
		return count;
	}

	/**
	 * 统计某一张牌的个数
	 * 
	 * @param card
	 * @return
	 */
	public int count(int card) {
		int count = 0;
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i).contains(card))
				count++;
		}
		return count;
	}

	/**
	 * 所有牌数
	 * 
	 * @return
	 */
	public int sumCard() {
		int sum = 0;
		for (Set<Integer> set : values)
			sum += set.size();

		return sum;
	}

	public CardSort clone() {
		CardSort cardSort = new CardSort(this.values.size());
		for (int i = 0; i < 4; i++)
			cardSort.values.get(i).addAll(this.values.get(i));

		return cardSort;
	}

	public List<Integer> toArray() {
		List<Integer> list = new ArrayList<>();
		for (Set<Integer> set : values)
			list.addAll(set);

		return list;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Set<Integer> set : this.values) {
			sb.append(set);
		}
		return sb.toString();
	}
}
