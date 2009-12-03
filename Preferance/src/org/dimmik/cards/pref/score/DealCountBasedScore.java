package org.dimmik.cards.pref.score;

import org.dimmik.cards.table.Seat;

public class DealCountBasedScore extends Score {

  private final int maxDeals;
  
  public DealCountBasedScore(int maxDeals, Seat west, Seat north, Seat east) {
    super(west, north, east);
    this.maxDeals = maxDeals;
  }

  @Override
  public boolean isGameFinished() {
    // TODO Auto-generated method stub
    return getDeals().size() >= maxDeals;
  }

}
