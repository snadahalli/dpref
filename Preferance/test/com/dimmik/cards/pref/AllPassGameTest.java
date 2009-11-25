package com.dimmik.cards.pref;

import java.util.List;

import junit.framework.TestCase;

import com.dimmik.cards.table.Deal;
import com.dimmik.cards.table.Game;
import com.dimmik.cards.table.GameFactory;
import com.dimmik.cards.table.Move;
import com.dimmik.cards.table.Seat;

public class AllPassGameTest extends TestCase {

  public void testAllPass() throws Throwable {
    System.out.println("testAllPass");
    int gamesCnt = 1;
    List<Seat> seats = PrefTestUtility.getAllPassSeats();
    GameFactory factory = new PrefGameFactory(gamesCnt);
    Game game = factory.createGame(seats);
    game.process();
    for (Deal dx : game.getDeals()) {
      PrefDeal d = (PrefDeal) dx;
      System.out.println("contract: " + d.getContract());
      System.out.println("deal: " + d);
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
