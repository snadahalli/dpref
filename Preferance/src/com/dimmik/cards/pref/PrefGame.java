package com.dimmik.cards.pref;

import java.util.List;

import com.dimmik.cards.sheets.deck.CardDeck;
import com.dimmik.cards.sheets.deck.PrefCardInitStrategy;
import com.dimmik.cards.table.Deal;
import com.dimmik.cards.table.Game;
import com.dimmik.cards.table.Seat;

public class PrefGame extends Game {

    private final int maxDeals = 1;

    
    private final List<Seat> seats;
    private final CardDeck deck = new CardDeck(new PrefCardInitStrategy());
    
    private int firstDealSeatNumber = 0;
    
    public PrefGame(List<Seat> s){
	seats = s;
	if (seats.size() != 3) {
	    throw new IllegalArgumentException("seat size should be 3");
	}
    }
    
    @Override
    protected CardDeck getDeck() {
	return deck;
    }

    @Override
    protected boolean gameMakesSense() {
	// TODO change to real decision if the game makes sense.
	return getDeals().size() < maxDeals; 
    }


    @Override
    protected Deal newDeal() {
	return new PrefDeal(deck, seats, firstDealSeatNumber);
    }

    @Override
    protected void updateGameStatus(Deal deal) {
	// TODO Auto-generated method stub
	// TODO may be makes sense to move it into dealPostProcess
    }

    @Override
    protected void dealPostProcess(Deal deal) {
	firstDealSeatNumber ++;
	if (firstDealSeatNumber > seats.size()){
	    firstDealSeatNumber = 0;
	}
    }

}
