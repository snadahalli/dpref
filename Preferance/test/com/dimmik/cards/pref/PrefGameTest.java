package com.dimmik.cards.pref;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.dimmik.cards.prefplayers.DumbPlayer;
import com.dimmik.cards.table.Deal;
import com.dimmik.cards.table.Game;
import com.dimmik.cards.table.GameFactory;
import com.dimmik.cards.table.Seat;

public class PrefGameTest extends TestCase {
    @SuppressWarnings("serial")
    public void testGame() throws Throwable {
	List<Seat> seats = new ArrayList<Seat>() {
	    {
		add(new Seat("West"));
		add(new Seat("North"));
		add(new Seat("East"));
	    }
	};
	for (Seat s : seats) {
	    s.setPlayer(new DumbPlayer());
	}
	GameFactory factory = new PrefGameFactory();
	Game game = factory.createGame(seats);
	game.process();
	for (Deal d : game.getDeals()) {
	    System.out.println("deal '" + d.getName() + "': " + d);
	}
    }
}
