package com.dimmik.cards.sheets.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dimmik.cards.sheets.card.Card;
import com.dimmik.cards.sheets.card.Rank;
import com.dimmik.cards.sheets.card.Suit;

public class Sorted52CardInitStrategy implements ICardInitStrategy {

	@Override
	public Iterable<Card> initialCards() {
		List<Card> cards = new ArrayList<Card>(32);
		for (Suit s : Suit.values()) {
			for (Rank r : Rank.values()) {
				if (r.getValue() > 6) {
					cards.add(Card.getInstance(s, r));
				}
			}
		}
		return cards;
	}

}
