package org.dimmik.cards.pref;

import java.util.List;

import org.dimmik.cards.pref.PrefDeal;
import org.dimmik.cards.pref.PrefGameFactory;
import org.dimmik.cards.table.Deal;
import org.dimmik.cards.table.Game;
import org.dimmik.cards.table.GameFactory;
import org.dimmik.cards.table.Move;
import org.dimmik.cards.table.Seat;

import junit.framework.TestCase;

public class PrefGameTest extends TestCase {

  public void testGame() throws Throwable {
    System.out.println("testGame");
    int gamesCnt = 15;
    List<Seat> seats = PrefTestUtility.getVistersSeats();
    GameFactory factory = new PrefGameFactory(gamesCnt);
    Game game = factory.createGame(seats);
    game.process();
    assertEquals(gamesCnt, game.getDeals().size());
    for (Deal dx : game.getDeals()) {
      PrefDeal d = (PrefDeal) dx;
      // System.out.println("deal '" + d.getName() + "': " + d);
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

  public void testUsual() throws Throwable {
    System.out.println("testUsual");
    int gamesCnt = 20;
    List<Seat> seats = PrefTestUtility.getSeats();
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
      if (d.shouldGameHaveMoves()) {
        assertEquals(10, tricks);
      }
    }

  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    System.out.println();
    System.out.println(this.getClass());
  }
}
