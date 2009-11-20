package com.dimmik.cards.pref;

import java.util.List;

import com.dimmik.cards.sheets.card.Card;
import com.dimmik.cards.sheets.card.Suit;
import com.dimmik.cards.sheets.deck.CardDeck;
import com.dimmik.cards.sheets.deck.PrefCardInitStrategy;
import com.dimmik.cards.table.Deal;
import com.dimmik.cards.table.Move;
import com.dimmik.cards.table.Seat;

public class PrefDeal extends Deal {
    private final CardDeck deck = new CardDeck(new PrefCardInitStrategy());
    private final List<Seat> seats;
    private final int firstMoveSeatIdx;
    private List<Card> sideCards;
    private List<Card> thrownCards;

    public PrefDeal(List<Seat> seats, int firstMove) {
	this.seats = seats;
	firstMoveSeatIdx = firstMove;
    }

    @Override
    protected Move createMove() {
	// TODO get trump. from trade
	// XXX Pay attention - right now just hardcoded spades
	Suit trump = Suit.SPADES;
	PrefMove move = new PrefMove(seats, firstMoveSeatIdx, trump);
	return move;
    }

    @Override
    protected void serveCards() {
	add10Cards(seats.get(0));
	add10Cards(seats.get(1));
	add10Cards(seats.get(2));
	sideCards.add(deck.getNextCard());
	sideCards.add(deck.getNextCard());
    }

    private void add10Cards(Seat seat) {
	for (int i = 0; i < 10; i++) {
	    seat.addCard(deck.getNextCard());
	}
    }

    @Override
    protected void movePostProcess(Move move) {
	// TODO decide who moves next
	// who's trick and so on.
    }
}
