package com.dimmik.cards.table;

import java.util.LinkedList;
import java.util.List;

import com.dimmik.cards.sheets.card.Card;

public class Seat {

    private IPlayer player;
    
    private final List<Card> cards = new LinkedList<Card>();
    public Card nextCard(Move move) {
	Card card = player.nextCard(this, move);
	cards.remove(card);
	return card;
    }
    public void addCard(Card c){
	getCards().add(c);
    }
    public List<Card> getCards() {
	return cards;
    }
    
    public void setPlayer(IPlayer player) {
	this.player = player;
    }
    public IPlayer getPlayer() {
	return player;
    }
    
}
