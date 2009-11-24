package com.dimmik.cards.pref.trade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dimmik.cards.sheets.card.Rank;
import com.dimmik.cards.sheets.card.Suit;
import com.dimmik.cards.table.DealException;
import com.dimmik.cards.table.Seat;

public class Contract {

  private final List<Seat> seats;
  private final Map<Seat, Bid> lastBid = new HashMap<Seat, Bid>();
  private final List<SeatBid> seatBids = new ArrayList<SeatBid>();

  private SeatBid lastNonPassBid = null;

  private Bid game;

  public Contract(List<Seat> ss) {
    seats = ss;
    initAllBids();
  }

  private final static Suit[] suitArray = new Suit[] { Suit.SPADES, Suit.CLUBS,
      Suit.DIAMONDS, Suit.HEARTS, Suit.NO_SUIT };
  private static Rank[] ranksBeforeMiser = new Rank[] { Rank.SIX, Rank.SEVEN,
      Rank.EIGHT };
  private static Rank[] ranksAfterMiser = new Rank[] { Rank.NINE, Rank.TEN };

  private final List<Bid> allBids = new ArrayList<Bid>(); // 5 suits, 5 ranks
  // and miser

  private final Map<Bid, Integer> bidsValue = new HashMap<Bid, Integer>();

  private final Set<Seat> seatsPassed = new HashSet<Seat>();
  private Seat miserBiden = null;

  private void initAllBids() {
    for (Rank r : ranksBeforeMiser) {
      for (Suit s : suitArray) {
        allBids.add(Bid.valueOf(s, r));
      }
    }
    allBids.add(Bid.MISER);
    for (Rank r : ranksAfterMiser) {
      for (Suit s : suitArray) {
        allBids.add(Bid.valueOf(s, r));
      }
    }
    int val = 0;
    for (Bid b : allBids) {
      bidsValue.put(b, Integer.valueOf(val));
      val++;
    }
  }

  public boolean isBidCorrect(Seat seat, Bid bid) {
    if (bid == Bid.PASS) {
      // pass always acceptable
      return true;
    }
    if (!bidsValue.containsKey(bid)) {
      // strange bid, out of all available
      return false;
    }
    if (lastNonPassBid == null) {
      // first valuable bid
      return true;
    }
    if (miserBiden != null && miserBiden.equals(seat)) {
      // if said miser - only pass then
      if (bid != Bid.PASS) {
        return false;
      }
    }
    int lastNonPassValue = bidsValue.get(lastNonPassBid.bid).intValue();
    int bidValue = bidsValue.get(bid).intValue();
    if (bidValue > lastNonPassValue) {
      return true;
    }
    return false;
  }

  public void addBid(Seat bidder, Bid bid) throws DealException {
    checkBidIsPossibleAndThrowIfNot(bidder, bid);
    storeBid(bidder, bid);
  }

  private void storeBid(Seat bidder, Bid bid) {
    SeatBid sb = new SeatBid(bidder, bid);
    lastBid.put(bidder, bid);
    seatBids.add(sb);
    if (bid != Bid.PASS) {
      lastNonPassBid = sb;
    } else {
      seatsPassed.add(bidder);
    }
    if (bid == Bid.MISER) {
      miserBiden = bidder;
    }
  }

  private void checkBidIsPossibleAndThrowIfNot(Seat bidder, Bid bid) throws DealException {
    if (isTradeFinished()) {
      throw new DealException("trade is finished - can not add bids");
    }
    if (seatsPassed.contains(bidder)) {
      throw new DealException("seat " + bidder + " has already passed");
    }
    if (!isBidCorrect(bidder, bid)) {
      throw new DealException("bid " + bid + " is not correct");
    }
    if (miserBiden != null) {
      if (miserBiden.equals(bidder)) {
        if (bid != Bid.PASS) {
          throw new DealException("bidder " + bidder
              + " has already bidden MISER");
        }
      }
    }
  }

  public Seat getWinnerSeat() throws DealException {
    checkTradeFinished();
    if (lastNonPassBid == null) {
      return null;
    } else {
      return lastNonPassBid.seat;
    }
  }

  public Bid getWinnerBid() throws DealException {
    checkTradeFinished();
    if (lastNonPassBid == null) {
      return null;
    } else {
      return lastNonPassBid.bid;
    }
  }

  private void checkTradeFinished() throws DealException {
    if (!isTradeFinished()) {
      throw new DealException("trade is not finished");
    }
  }

  public boolean isTradeFinished() {
    // check if all seats have bidden
    for (Seat seat : seats) {
      if (lastBid.get(seat) == null) {
        // at least one has not bidden
        return false;
      }
    }

    // everyone has biden
    int countPassed = 0;
    for (Seat seat : seats) {
      Bid b = lastBid.get(seat);
      if (b.equals(Bid.PASS)) {
        countPassed++;
      }
    }
    // at least two passed -
    if (countPassed >= 2) {
      return true;
    }
    return false;
  }

  public Bid getMinAvailableBid(Seat seat) {
    for (Bid bid : allBids) {
      if (isBidCorrect(seat, bid)) {
        return bid;
      }
    }
    return Bid.PASS;
  }

  public boolean seatHasBidenPass(Seat bidder) {
    return Bid.PASS.equals(lastBid.get(bidder));
  }

  public List<Bid> getAllBids() {
    return allBids;
  }

  public void setGame(Bid bid) throws DealException {
    if (!isGameAcceptable(bid)) {
      throw new IllegalStateException("you can not order " + bid);
    }
    game = bid;
  }

  public boolean isGameAcceptable(Bid gameBid) throws DealException {
    if (!isTradeFinished()) {
      return false;
    }
    if (getWinnerBid() == null) { // all pass - only pass acceptable
      return (gameBid == Bid.PASS);
    }
    if (getWinnerBid() == Bid.MISER) { // miser - only miser game
      return (gameBid == Bid.MISER);
    }
    if (!bidsValue.containsKey(gameBid)) {
      return false;
    }

    Bid winnerBid = getWinnerBid();
    int winnerBidValue = bidsValue.get(winnerBid);
    int gameBidValue = bidsValue.get(gameBid);
    // anything higher but miser
    return ((gameBidValue >= winnerBidValue) && (gameBid != Bid.MISER));
  }

  public Bid getGame() {
    return game;
  }

  public Bid getFirstAvailableGame() throws DealException {
    for (Bid bid : allBids) {
      if (isGameAcceptable(bid)) {
        return bid;
      }
    }
    return Bid.PASS;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    int i = 0;
    for (SeatBid seatBid : seatBids) {
      sb.append(i + 1).append(".").append(seatBid.seat);
      sb.append("->").append(seatBid.bid);
      sb.append("\n");
      i++;
    }
    if (isTradeFinished()) {
      sb.append("trade is finished\n");
      try {
        sb.append("winner: ").append(getWinnerSeat()).append(" -> ").append(
            getWinnerBid());
      } catch (DealException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return sb.toString();
  }

  /**
   * stores seat and bid.
   * 
   * @author dkandrievsky
   * 
   */
  private final static class SeatBid {
    private final Seat seat;
    private final Bid bid;

    private SeatBid(Seat s, Bid b) {
      seat = s;
      bid = b;
    }
  }

}
