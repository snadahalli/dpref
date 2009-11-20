package com.dimmik.cards.table;

import java.util.ArrayList;
import java.util.List;

public class Deal {
    //private ICardDeck deck;
    private Seat[] seats;
    private final List<Move> moves = new ArrayList<Move>();
    public void process() throws DealException{
	serveCards();
	Seat first = firstMovingSeat();
	Move move = createMove(first);
	move.process();
	updateDealStatus(move);
	moves.add(move);
    }
    
    
    private Seat firstMovingSeat() {
	// TODO Auto-generated method stub
	return null;
    }


    private void updateDealStatus(Move move) {
	// TODO Auto-generated method stub
	
    }


    private Move createMove(Seat first) {
	// TODO Auto-generated method stub
	return null;
    }
    private void serveCards() {
	// TODO Auto-generated method stub
	
    }

}
