package com.dimmik.cards.sheets;

import java.util.HashSet;
import java.util.Set;

import com.dimmik.cards.sheets.card.Card;
import com.dimmik.cards.sheets.deck.CardDeck;
import com.dimmik.cards.sheets.deck.ICardDeck;
import com.dimmik.cards.sheets.deck.ICardInitStrategy;
import com.dimmik.cards.sheets.deck.PokerInitStrategy;
import com.dimmik.cards.sheets.deck.PrefCardInitStrategy;

import junit.framework.TestCase;

public class CardDeckTest extends TestCase {
	public void testCardDeck(){
		ICardInitStrategy pref = new PrefCardInitStrategy();
		ICardInitStrategy poker = new PokerInitStrategy();
		ICardDeck prefd = new CardDeck(pref);
		ICardDeck pokerd = new CardDeck(poker);
		assertEquals(prefd.getCapacity(), 32);
		assertEquals(pokerd.getCapacity(), 52);
		Set<Card> cs = new HashSet<Card>();
		for (Card c: prefd) {
			cs.add(c);
		}
		assertEquals(prefd.getCapacity(), cs.size());
		
		cs = new HashSet<Card>();
		for (Card c: pokerd) {
			cs.add(c);
		}
		assertEquals(pokerd.getCapacity(), cs.size());
	}
}
