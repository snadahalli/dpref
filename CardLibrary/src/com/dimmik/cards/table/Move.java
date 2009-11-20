package com.dimmik.cards.table;

import java.util.ArrayList;
import java.util.List;

import com.dimmik.cards.sheets.card.Card;

public abstract class Move {
    private final List<Seat> seats;
    private final List<Card> cards = new ArrayList<Card>();

    protected Move(List<Seat> s, int first) {
	if (s == null || first >= s.size()) {
	    throw new IllegalArgumentException("first should be inside seats");
	}
	List<Seat> correctlyArranged = rearrange(s, first);
	seats = correctlyArranged;
    }

    private List<Seat> rearrange(List<Seat> s, int first) {
	List<Seat> correctlyArranged = new ArrayList<Seat>();
	for (int i = first; i < s.size(); i ++){
	    correctlyArranged.add(s.get(i));
	}
	for (int i = 0; i < first; i ++) {
	    correctlyArranged.add(s.get(i));	    
	}
	return correctlyArranged;
    }
    
    public void process() throws CardIsNotAcceptableException {
	for (Seat seat : seats) {
	    Card card = seat.nextCard(this);
	    if (!isCardAcceptable(card, seat)) {
		throw new CardIsNotAcceptableException(card, seat);
	    }
	    cards.add(card);
	}
    }

    public abstract boolean isCardAcceptable(Card card, Seat seat);

    public List<Card> getCards() {
	return cards;
    }

}
