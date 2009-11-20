package com.dimmik.cards.pref;

import java.util.List;

import com.dimmik.cards.sheets.card.Card;
import com.dimmik.cards.sheets.card.Suit;
import com.dimmik.cards.table.Move;
import com.dimmik.cards.table.Seat;

public class PrefMove extends Move {

    private final Suit trump;

    public PrefMove(List<Seat> seats, int first, Suit trump) {
	super(seats, first);
	this.trump = trump;
    }

    @Override
    public boolean isCardAcceptable(Card card, Seat seat) {
	if (getCards().isEmpty()) {
	    // first move - anything
	    return true;
	}
	Card firstCard = getCards().get(0);
	Suit firstCardSuit = firstCard.getSuit();
	Suit cardSuit = card.getSuit();

	if (suitsMatch(firstCardSuit, cardSuit)) {
	    return true;
	}
	// Suits don't match

	// trump acceptable
	if (suitIsTrump(cardSuit)) {
	    return true;
	}

	// nor trump neither suit - check seat's cards
	for (Card seatCard : seat.getCards()) {
	    // if seat has at least one trump or matching card - not acceptable
	    if (suitIsTrump(seatCard.getSuit())
		    || suitsMatch(firstCardSuit, seatCard.getSuit())) {
		return false;
	    }
	}
	// Seat has no trumps - any card acceptable
	return true;
    }

    private boolean suitIsTrump(Suit cardSuit) {
	return cardSuit.equals(trump);
    }

    private boolean suitsMatch(Suit firstCardSuit, Suit cardSuit) {
	return cardSuit == firstCardSuit;
    }

}
