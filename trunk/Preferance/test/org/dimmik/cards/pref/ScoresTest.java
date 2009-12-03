package org.dimmik.cards.pref;

import java.util.List;

import org.dimmik.cards.pref.PrefDeal;
import org.dimmik.cards.pref.PrefGame;
import org.dimmik.cards.pref.PrefGameFactory;
import org.dimmik.cards.pref.score.DealCountBasedScore;
import org.dimmik.cards.pref.score.Score;
import org.dimmik.cards.table.Deal;
import org.dimmik.cards.table.Move;
import org.dimmik.cards.table.Seat;

import junit.framework.TestCase;

public class ScoresTest extends TestCase {

  public void testAllPass() throws Throwable {
    System.out.println("testAllPass");
    int gamesCnt = 30;
    List<Seat> seats = PrefTestUtility.getAllPassSeats();
    PrefGameFactory factory = new PrefGameFactory(new DealCountBasedScore(
        gamesCnt, seats.get(0), seats.get(1), seats.get(2)));
    PrefGame game = factory.createGame(seats);
    game.process();
    for (Deal dx : game.getDeals()) {
      PrefDeal d = (PrefDeal) dx;
      // System.out.println("contract: " + d.getContract());
      // System.out.println("deal: " + d.getName());
      int tricks = 0;
      for (Seat seat : d.getTricks().keySet()) {
        List<Move> moves = d.getTricks().get(seat);
        // System.out.println(seat + "-> " + moves.size() + " tricks");
        tricks += moves.size();
      }
      assertEquals(10, tricks);
    }
    Score s = game.getScore();
    for (Seat seat : s.getSeats()) {
      System.out.println(seat + " wins: " + s.getWins(seat));
      System.out.println(seat + " fines: " + s.getFines(seat));
    }

    System.out.println("result: " + s.getSeatResults());
  }

  public void testMiserer() throws Throwable {
    System.out.println("\ntestMiserer");
    int gamesCnt = 30;
    List<Seat> seats = PrefTestUtility.getNorthMiserSeats();
    PrefGameFactory factory = new PrefGameFactory(new DealCountBasedScore(
        gamesCnt, seats.get(0), seats.get(1), seats.get(2)));
    PrefGame game = factory.createGame(seats);
    game.process();
    // for (Deal dx : game.getDeals()) {
    // PrefDeal d = (PrefDeal) dx;
    // // System.out.println("contract: " + d.getContract());
    // // System.out.println("deal: " + d.getName());
    // int tricks = 0;
    // for (Seat seat : d.getTricks().keySet()) {
    // List<Move> moves = d.getTricks().get(seat);
    // // System.out.println(seat + "-> " + moves.size() + " tricks");
    // tricks += moves.size();
    // }
    // assertEquals(10, tricks);
    // }
    Score s = game.getScore();
    for (Seat seat : s.getSeats()) {
      System.out.println(seat + " wins: " + s.getWins(seat));
      System.out.println(seat + " fines: " + s.getFines(seat));
    }
    System.out.println("result: " + s.getSeatResults());
  }

  public void testDumbs() throws Throwable {
    System.out.println("\ntestDumbs");
    int gamesCnt = 10;
    List<Seat> seats = PrefTestUtility.getSeats();
    PrefGameFactory factory = new PrefGameFactory(new DealCountBasedScore(
        gamesCnt, seats.get(0), seats.get(1), seats.get(2)));
    PrefGame game = factory.createGame(seats);
    game.process();
    for (Deal dx : game.getDeals()) {
      PrefDeal d = (PrefDeal) dx;
      System.out.println("contract: " + d.getContract());
      // System.out.println("deal: " + d.getName());
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
    Score s = game.getScore();
    for (Seat seat : s.getSeats()) {
      System.out.println(seat + " wins: " + s.getWins(seat));
      System.out.println(seat + " fines: " + s.getFines(seat));
      System.out.println("vists to " + seat + ": " + s.getVists().get(seat));
    }
    System.out.println("result: " + s.getSeatResults());
  }
}
