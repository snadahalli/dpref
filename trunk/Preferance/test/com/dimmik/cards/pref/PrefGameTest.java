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
    int gamesCnt = 5;
    List<Seat> seats = PrefTestUtility.getSeats();
    GameFactory factory = new PrefGameFactory(gamesCnt);
    Game game = factory.createGame(seats);
    game.process();
    assertEquals(gamesCnt, game.getDeals().size());
    for (Deal dx : game.getDeals()) {
      PrefDeal d = (PrefDeal)dx;
      //System.out.println("deal '" + d.getName() + "': " + d);
      System.out.println("\ndeal: " + d.getName());
      System.out.println("contract: \n" + d.getContract());
      System.out.println();
      int tricks = 0;
      for (Seat seat : d.getTricks().keySet()) {
        List<Move> moves = d.getTricks().get(seat);
        System.out.println(seat + "-> " + moves.size() + " tricks");
        tricks += moves.size();
      }
      assertEquals(10, tricks);
    }
  }
}
