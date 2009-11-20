package com.dimmik.cards.table;

import java.util.LinkedList;
import java.util.List;

import com.dimmik.cards.sheets.card.Card;

public abstract class Seat {

    private final LinkedList<Card> cards = new LinkedList<Card>();
    public Card nextCard(Move move) {
	// TODO Auto-generated method stub
	return null;
    }
    public void addCard(Card c){
	getCards().add(c);
    }
    public List<Card> getCards() {
	return cards;
    }
    
}
