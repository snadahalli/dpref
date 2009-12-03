package org.dimmik.cards.pref;

import java.util.List;

import org.dimmik.cards.pref.PrefDeal;
import org.dimmik.cards.pref.PrefGameFactory;
import org.dimmik.cards.pref.score.DealCountBasedScore;
import org.dimmik.cards.table.Deal;
import org.dimmik.cards.table.Game;
import org.dimmik.cards.table.GameException;
import org.dimmik.cards.table.GameFactory;
import org.dimmik.cards.table.Move;
import org.dimmik.cards.table.Seat;

import junit.framework.TestCase;

public class PrefGameTest extends TestCase {

  public void testGame() throws Throwable {
    System.out.println("testGame");
    int gamesCnt = 15;
    List<Seat> seats = PrefTestUtility.getVistersSeats();
    doTest(gamesCnt, seats);
  }

  public void testUsual() throws Throwable {
    System.out.println("testUsual");
    int gamesCnt = 20;
    List<Seat> seats = PrefTestUtility.getSeats();
    doTest(gamesCnt, seats);
  }

  private void doTest(int gamesCnt, List<Seat> seats) throws GameException {
    Game game = getGame(gamesCnt, seats);
    assertEquals(gamesCnt, game.getDeals().size());
    check(game);
  }

  public void testSemiVisters() throws Throwable {
    System.out.println("testGame");
    int gamesCnt = 15;
    List<Seat> seats = PrefTestUtility.getSemiVistersSeats();
    doTest(gamesCnt, seats);
  }

  private Game getGame(int gamesCnt, List<Seat> seats) throws GameException {
    GameFactory factory = new PrefGameFactory(new DealCountBasedScore(gamesCnt,
        seats.get(0), seats.get(1), seats.get(2)));
    Game game = factory.createGame(seats);
    game.process();
    return game;
  }

  private void check(Game game) {
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
