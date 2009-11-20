package com.dimmik.cards.pref;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dimmik.cards.prefplayers.DumbPlayer;
import com.dimmik.cards.sheets.card.Card;
import com.dimmik.cards.table.Deal;
import com.dimmik.cards.table.Move;
import com.dimmik.cards.table.Seat;

import junit.framework.TestCase;

public class PrefDealTest extends TestCase {
    @SuppressWarnings("serial")
    public void testDeal() throws Throwable{
	List<Seat> seats = new ArrayList<Seat>(){{
		   add(new Seat("West")); 
		   add(new Seat("North")); 
		   add(new Seat("East")); 
	}};
	for (Seat s : seats) {
	    s.setPlayer(new DumbPlayer());
	}
	Deal d = new PrefDeal(seats, 0);
	d.process();
	System.out.println("deal: " + d);
	Set<Card> cardSet = new HashSet<Card>();
	for (Move m : d.getMoves()){
	    for (Card card: m.getCards()) {
		cardSet.add(card);
	    }
	}
	assertEquals(30, cardSet.size());
	for (Seat s: seats) {
	    System.out.println(s + " -- " + s.getTricks().size());
	}
    }
}
