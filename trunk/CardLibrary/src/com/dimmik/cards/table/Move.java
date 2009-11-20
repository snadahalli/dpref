package com.dimmik.cards.table;

import java.util.ArrayList;
import java.util.List;

import com.dimmik.cards.sheets.card.Card;

public class Move {
    private Seat[] seats;
    private final List<Card> cards = new ArrayList<Card>();

    public void process() throws CardIsNotAcceptableException {
	for (Seat seat : seats) {
	    Card card = seat.nextCard(this);
	    if (!isCardAcceptable(card, seat)) {
		throw new CardIsNotAcceptableException(card, seat);
	    }
	    cards.add(card);
	}
    }

    public boolean isCardAcceptable(Card card, Seat seat) {
	// TODO Auto-generated method stub
	return false;
    }

}
