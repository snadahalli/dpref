package com.dimmik.cards.pref;

import java.util.List;

import junit.framework.TestCase;

import com.dimmik.cards.pref.score.Score;
import com.dimmik.cards.table.Deal;
import com.dimmik.cards.table.Move;
import com.dimmik.cards.table.Seat;

public class ScoresTest extends TestCase {

  public void testAllPass() throws Throwable {
    System.out.println("testAllPass");
    int gamesCnt = 30;
    List<Seat> seats = PrefTestUtility.getAllPassSeats();
    PrefGameFactory factory = new PrefGameFactory(gamesCnt);
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
    int gamesCnt = 1;
    List<Seat> seats = PrefTestUtility.getNorthMiserSeats();
    PrefGameFactory factory = new PrefGameFactory(gamesCnt);
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
}
