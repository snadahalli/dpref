package com.dimmik.cards.pref;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import com.dimmik.cards.pref.trade.Contract;
import com.dimmik.cards.sheets.card.Card;
import com.dimmik.cards.table.Move;
import com.dimmik.cards.table.Seat;

public class PrefDealTest extends TestCase {
  public void testDeal() throws Throwable {
    List<Seat> seats = PrefTestUtility.getSeats();
    PrefDeal d = new PrefDeal("test", seats, 0);
    d.process();
    // System.out.println("deal: " + d);
    Set<Card> cardSet = new HashSet<Card>();
    for (Move m : d.getMoves()) {
      for (Card card : m.getCards()) {
        cardSet.add(card);
      }
    }
    assertEquals(30, cardSet.size());
  }

  public void testDealWithTradeCheck() throws Throwable {
    List<Seat> seats = PrefTestUtility.getSeats();
    PrefDeal d = new PrefDeal("test", seats, 0);
    d.process();
    System.out.println("deal: " + d);
    for (Seat seat : d.getTricks().keySet()) {
      List<Move> moves = d.getTricks().get(seat);
      System.out.println(seat + ": " + moves.size() + " tricks");
    }
    Contract c = d.getContract();
    System.out.println("trade: \n" + c);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    System.out.println();
    System.out.println(this.getClass());
  }
}