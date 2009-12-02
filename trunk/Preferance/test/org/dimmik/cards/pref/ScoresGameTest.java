package org.dimmik.cards.pref;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dimmik.cards.pref.score.Score;
import org.dimmik.cards.pref.trade.Bid;
import org.dimmik.cards.pref.trade.Contract;
import org.dimmik.cards.sheets.card.Rank;
import org.dimmik.cards.sheets.card.Suit;
import org.dimmik.cards.table.DealException;
import org.dimmik.cards.table.Seat;

import junit.framework.TestCase;

public class ScoresGameTest extends TestCase {

  public void test6SGame() throws Throwable {
    Bid sixS = Bid.valueOf(Suit.SPADES, Rank.SIX);
    doTest(sixS, new int[] { 1, 6, 3 }, 5);
    doTest(sixS, new int[] { 2, 5, 3 }, -27);
    doTest(sixS, new int[] { 2, 7, 1 }, 14);
    doTest(sixS, new int[] { 3, 7, 0 }, 14);
    Bid sevenD = Bid.valueOf(Suit.DIAMONDS, Rank.SEVEN);
    doTest(sevenD, new int[] { 1, 7, 2 }, 14);
    doTest(sevenD, new int[] { 0, 8, 2 }, 18);
    doTest(sevenD, new int[] { 2, 6, 2 }, -50);
    doTest(sevenD, new int[] { 3, 5, 2 }, -89);
    Bid tenD = Bid.valueOf(Suit.DIAMONDS, Rank.TEN);
    doTest(tenD, new int[] { 0, 10, 0 }, 66);
    doTest(tenD, new int[] { 0, 7, 3 }, -290);
  }

  private void doTest(Bid bid, int[] tricks, int expected) throws DealException {
    PrefDeal deal;
    Score score;
    List<Seat> seats = PrefTestUtility.getSeats();
    Seat n = seats.get(1);
    Contract c = getContract(seats, bid);
    deal = getDeal(seats, c, tricks);
    score = new Score(1, seats.get(0), seats.get(1), seats.get(2));
    score.update(deal);
    System.out.println("deal: " + deal + " bid: " + bid);
    for (Seat seat : seats) {
      System.out.println(seat + " -> " + score.getResult(seat));
    }
    assertEquals(expected, Float.valueOf(score.getResult(n)).intValue());
  }

  @SuppressWarnings("serial")
  private PrefDeal getDeal(final List<Seat> seats, final Contract c,
      final int[] wneTricks) {
    final Seat w = seats.get(0);
    final Seat n = seats.get(1);
    final Seat e = seats.get(2);
    final PrefDeal deal = new PrefDeal("the test deal", seats, 0) {

      Map<Seat, Integer> tricks = new HashMap<Seat, Integer>() {
        {
          put(w, wneTricks[0]);
          put(n, wneTricks[1]);
          put(e, wneTricks[2]);
        }
      };

      @Override
      public Contract getContract() {
        return c;
      }

      @Override
      public int getTricksCount(Seat seat) {
        return tricks.get(seat);
      }

      @Override
      public boolean isAllPassGame() {
        return false;
      }

      @Override
      public String toString() {
        return wneTricks[0] + " - " + wneTricks[1] + " - " + wneTricks[2];
      }

    };
    return deal;
  }

  private Contract getContract(final List<Seat> seats, final Bid bid) {
    // final Seat w = seats.get(0);
    final Seat n = seats.get(1);
    // final Seat e = seats.get(2);
    final Contract c = new Contract(seats) {

      @Override
      public Bid getGame() {
        return bid;
      }

      @Override
      public Seat getWinnerSeat() {
        return n; // North
      }

    };
    return c;
  }
}
