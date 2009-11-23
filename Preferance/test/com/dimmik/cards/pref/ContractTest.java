package com.dimmik.cards.pref;

import java.util.List;

import junit.framework.TestCase;

import com.dimmik.cards.pref.trade.Bid;
import com.dimmik.cards.pref.trade.Contract;
import com.dimmik.cards.sheets.card.Rank;
import com.dimmik.cards.sheets.card.Suit;
import com.dimmik.cards.table.Seat;

public class ContractTest extends TestCase {
  
  public void testPrimitiveContract() {
    System.out.println("testPrimitiveContract");
    List<Seat> seats = PrefTestUtility.getSeats();
    Seat w = seats.get(0);

    Contract c = new Contract(seats);
    assertEquals(26, c.getAllBids().size());

    boolean bidOk;
    bidOk = c.isBidCorrect(w, Bid.valueOf(Suit.HEARTS, Rank.ACE));
    assertFalse(bidOk);
    bidOk = c.isBidCorrect(w, Bid.valueOf(Suit.DIAMONDS, Rank.SEVEN));
    assertTrue(bidOk);
    c.addBid(w, Bid.valueOf(Suit.DIAMONDS, Rank.SEVEN));
    bidOk = c.isBidCorrect(w, Bid.valueOf(Suit.CLUBS, Rank.SEVEN));
    assertFalse(bidOk);
    bidOk = c.isBidCorrect(w, Bid.MISER);
    assertTrue(bidOk);
  }

  public void testErrorneousTrade() {
    System.out.println("testErrorneousTrade");
    List<Seat> seats = PrefTestUtility.getSeats();
    Seat w = seats.get(0);
    Seat n = seats.get(1);
    Seat e = seats.get(2);

    Contract c;
    boolean bidOk;
    c = new Contract(seats);
    c.addBid(w, Bid.MISER);
    bidOk = c.isBidCorrect(w, Bid.valueOf(Suit.SPADES, Rank.EIGHT));
    assertFalse(bidOk);
    bidOk = c.isBidCorrect(w, Bid.valueOf(Suit.SPADES, Rank.NINE));
    assertTrue(bidOk);
    c.addBid(n, Bid.valueOf(Suit.SPADES, Rank.NINE));
    c.addBid(e, Bid.PASS);
    boolean bidExcepted = false;
    try {
      c.addBid(e, Bid.valueOf(Suit.SPADES, Rank.SIX));
    } catch (IllegalStateException ex) {
      System.out.println("already pas ex: " + ex);
      bidExcepted = true;
    }
    assertTrue(bidExcepted);
    c.addBid(w, Bid.PASS);

    bidExcepted = false;
    try {
      c.addBid(n, Bid.valueOf(Suit.SPADES, Rank.SIX));
    } catch (IllegalStateException ex) {
      System.out.println("add to finished ex: " + ex);
      bidExcepted = true;
    }
    assertTrue(bidExcepted);
    
    
    System.out.println("winner: " + c.getWinnerSeat() + " bid: "
        + c.getWinnerBid());
    
    c = new Contract(seats);
    boolean excepted = false;
    c.addBid(w, Bid.valueOf(Suit.SPADES, Rank.NINE));
    try {
    c.addBid(n, Bid.valueOf(Suit.SPADES, Rank.SIX));
    } catch (IllegalStateException ex) {
      System.out.println("test addBid exception ex: " + ex);
      excepted = true;
    }
    assertTrue(excepted);
    
  }

  public void testSampleTrade() {
    System.out.println("testSampleTrade");
    List<Seat> seats = PrefTestUtility.getSeats();
    Seat w = seats.get(0);
    Seat n = seats.get(1);
    Seat e = seats.get(2);
    
    Contract c;
    c = new Contract(seats);
    c.addBid(w, Bid.valueOf(Suit.SPADES, Rank.SIX));
    c.addBid(n, Bid.valueOf(Suit.DIAMONDS, Rank.SIX));
    c.addBid(e, Bid.valueOf(Suit.HEARTS, Rank.SIX));
    c.addBid(w, Bid.valueOf(Suit.SPADES, Rank.SEVEN));
    c.addBid(n, Bid.PASS);
    c.addBid(e, Bid.valueOf(Suit.DIAMONDS, Rank.SEVEN));
    c.addBid(w, Bid.valueOf(Suit.noSuit(), Rank.SEVEN));
    c.addBid(e, Bid.valueOf(Suit.HEARTS, Rank.EIGHT));
    c.addBid(w, Bid.valueOf(Suit.noSuit(), Rank.EIGHT));
    c.addBid(e, Bid.PASS);
    assertEquals(w, c.getWinnerSeat());
    assertEquals(Bid.valueOf(Suit.noSuit(), Rank.EIGHT), c.getWinnerBid());
    System.out.println("winner: " + c.getWinnerSeat() + " bid: "
        + c.getWinnerBid());
  }
  
  public void testAllPass(){
    System.out.println("testAllPass");
    List<Seat> seats = PrefTestUtility.getSeats();
    Seat w = seats.get(0);
    Seat n = seats.get(1);
    Seat e = seats.get(2);

    Contract c;
    c = new Contract(seats);

    c.addBid(w, Bid.PASS);
    c.addBid(n, Bid.PASS);
    c.addBid(e, Bid.PASS);

    assertNull(c.getWinnerBid());
    assertNull(c.getWinnerSeat());
    
  }

  public void testMiser(){
    System.out.println("testMiser");
    List<Seat> seats = PrefTestUtility.getSeats();
    Seat w = seats.get(0);
    Seat n = seats.get(1);
    Seat e = seats.get(2);

    Contract c;
    c = new Contract(seats);
    c.addBid(w, Bid.MISER);
    c.addBid(n, Bid.PASS);
    c.addBid(e, Bid.PASS);
    
    assertEquals(w, c.getWinnerSeat());
    assertEquals(Bid.MISER, c.getWinnerBid());
    System.out.println("winner: " + c.getWinnerSeat() + " bid: "
        + c.getWinnerBid());
  }
}
