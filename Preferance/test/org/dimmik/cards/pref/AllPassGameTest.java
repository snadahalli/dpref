package org.dimmik.cards.pref;

import java.util.List;

import org.dimmik.cards.pref.PrefDeal;
import org.dimmik.cards.pref.PrefGameFactory;
import org.dimmik.cards.pref.score.DealCountBasedScore;
import org.dimmik.cards.table.Deal;
import org.dimmik.cards.table.Game;
import org.dimmik.cards.table.GameFactory;
import org.dimmik.cards.table.Move;
import org.dimmik.cards.table.Seat;

import junit.framework.TestCase;

public class AllPassGameTest extends TestCase {

  public void testAllPass() throws Throwable {
    System.out.println("testAllPass");
    int gamesCnt = 1;
    List<Seat> seats = PrefTestUtility.getAllPassSeats();
    GameFactory factory = new PrefGameFactory(new DealCountBasedScore(gamesCnt,
        seats.get(0), seats.get(1), seats.get(2)));
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
