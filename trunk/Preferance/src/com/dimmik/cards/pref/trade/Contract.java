package com.dimmik.cards.pref.trade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dimmik.cards.table.Seat;

// TODO add set of all available bids (they are just 25 + miser + pass)
// TODO implement is Bidcorrect and so on
public class Contract {

  private final List<Seat> seats;
  private final Map<Seat, Bid> lastBid = new HashMap<Seat, Bid>(); 
  private final List<SeatBid> bids = new ArrayList<SeatBid>();
  
  private SeatBid lastNonPassBid = null;
  
  public Contract(List<Seat> ss) {
    seats = ss;
  }
  
  public boolean isBidCorrect(Bid bid) {
    if (bid == Bid.PASS) {
      return true;
    }
    // TODO Auto-generated method stub
    return false;
  }

  public void addBid(Seat bidder, Bid bid) {
    SeatBid sb = new SeatBid(bidder, bid); 
    lastBid.put(bidder, bid);
    bids.add(sb);
    if (Bid.PASS != bid) {
      lastNonPassBid = sb;
    }
  }
  
  public Seat getWinnerSeat(){
    checkTradeFinished();
    if (lastNonPassBid == null) {
      return null;
    } else {
      return lastNonPassBid.seat;
    }
  }
  public Bid getWinnerBid(){
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
        return true;
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
    if (countPassed > 2) {
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
}
