package com.dimmik.cards.pref;

import java.util.List;

import junit.framework.TestCase;

import com.dimmik.cards.table.Deal;
import com.dimmik.cards.table.Game;
import com.dimmik.cards.table.GameFactory;
import com.dimmik.cards.table.Move;
import com.dimmik.cards.table.Seat;

public class PrefGameTest extends TestCase {
  
  public void testGame() throws Throwable {
    List<Seat> seats = PrefTestUtility.getSeats();
    GameFactory factory = new PrefGameFactory();
    Game game = factory.createGame(seats);
    game.process();
    for (Deal d : game.getDeals()) {
      //System.out.println("deal '" + d.getName() + "': " + d);
      System.out.println("\ndeal: " + d.getName());
      int tricks = 0;
      for (Seat seat : d.getTricks().keySet()) {
        List<Move> moves = d.getTricks().get(seat);
        System.out.println(seat + ": " + moves.size() + " tricks");
        tricks += moves.size();
      }
      assertEquals(10, tricks);
    }
  }
}
