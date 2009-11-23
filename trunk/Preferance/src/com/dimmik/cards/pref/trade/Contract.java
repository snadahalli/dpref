package com.dimmik.cards.pref.trade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dimmik.cards.sheets.card.Rank;
import com.dimmik.cards.sheets.card.Suit;
import com.dimmik.cards.table.Seat;

// TODO implement setting actual game
public class Contract {

  private final List<Seat> seats;
  private final Map<Seat, Bid> lastBid = new HashMap<Seat, Bid>();
  private final List<SeatBid> seatBids = new ArrayList<SeatBid>();

  private SeatBid lastNonPassBid = null;

  public Contract(List<Seat> ss) {
    seats = ss;
    initAllBids();
  }

  private final static Suit[] suitArray = new Suit[] { Suit.SPADES, Suit.CLUBS,
      Suit.DIAMONDS, Suit.HEARTS, Suit.noSuit() };
  private static Rank[] ranksBeforeMiser = new Rank[] { Rank.SIX,
      Rank.SEVEN, Rank.EIGHT };
  private static Rank[] ranksAfterMiser = new Rank[] { Rank.NINE,
      Rank.TEN };

  private final List<Bid> allBids = new ArrayList<Bid>(); // 5 suits, 5 ranks and miser

  private final Map<Bid, Integer> bidsValue = new HashMap<Bid, Integer>();
  
  private final Set<Seat> seatsPassed = new HashSet<Seat>();
  
  private void initAllBids(){
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
    for (Bid b : allBids){
      bidsValue.put(b, Integer.valueOf(val));
      val ++;
    }
  }

  public boolean isBidCorrect(Bid bid) {
    if (bid == Bid.PASS) {
      // pass always acceptable
      return true;
    }
    if (!bidsValue.containsKey(bid)){
      // strange bid, out of all available
      return false;
    }
    if (lastNonPassBid == null){
      // first valuable bid
      return true;
    }
    int lastNonPassValue = bidsValue.get(lastNonPassBid.bid);
    int bidValue = bidsValue.get(bid);
    if (bidValue > lastNonPassValue){
      return true;
    }
    return false;
  }

  public void addBid(Seat bidder, Bid bid) {
    if (isTradeFinished()){
      throw new IllegalStateException("trade is finished - can not add bids");
    }
    if (seatsPassed.contains(bidder)) {
      throw new IllegalStateException("seat " + bidder + " has already passed");
    }
    SeatBid sb = new SeatBid(bidder, bid);
    lastBid.put(bidder, bid);
    seatBids.add(sb);
    if (bid != Bid.PASS) {
      lastNonPassBid = sb;
    } else {
      seatsPassed.add(bidder);
    }
  }

  public Seat getWinnerSeat() {
    checkTradeFinished();
    if (lastNonPassBid == null) {
      return null;
    } else {
      return lastNonPassBid.seat;
    }
  }

  public Bid getWinnerBid() {
    checkTradeFinished();
    if (lastNonPassBid == null) {
      return null;
    } else {
      return lastNonPassBid.bid;
    }
  }

  private void checkTradeFinished() {
    if (!isTradeFinished()) {
      throw new IllegalStateException("trade is not finished");
    }
  }

  public boolean isTradeFinished() {
    // check if all seats have bidden
    int countPassed = 0;
    for (Seat seat : seats) {
      if (lastBid.get(seat) == null) {
        // at least one has not bidden
        return false;
      }
    }
    // everyone has bidden
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

  private final static class SeatBid {
    private final Seat seat;
    private final Bid bid;

    private SeatBid(Seat s, Bid b) {
      seat = s;
      bid = b;
    }
  }

  public Bid getMinAvailableBid() {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean seatHasBiddenPass(Seat bidder) {
    return Bid.PASS.equals(lastBid.get(bidder));
  }

  public List<Bid> getAllBids() {
    return allBids;
  }
}
