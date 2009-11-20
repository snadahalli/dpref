package com.dimmik.cards.table;

import java.util.ArrayList;
import java.util.List;

import com.dimmik.cards.sheets.deck.CardDeck;

public abstract class Game {
    private final List<Deal> deals = new ArrayList<Deal>();
    private List<Seat> seats;
    public void process() throws GameException {
	while (gameMakesSense()) {
	    Deal deal = newDeal();
	    deal.process();
	    updateGameStatus(deal);
	    getDeals().add(deal);
	}

    }


    protected List<Seat> getSeats(){
	return seats;
    }

    protected abstract CardDeck getDeck();
    
    protected abstract void updateGameStatus(Deal deal);

    protected abstract boolean gameMakesSense();

    protected abstract Deal newDeal();

    public List<Deal> getDeals() {
	return deals;
    }

}
