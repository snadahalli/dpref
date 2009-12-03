package org.dimmik.cards.pref.score;

import org.dimmik.cards.table.Seat;

//TODO deal with "close others" case.
// When you have max in your wins, you should put it into others wins and get 10x vists from it
public class WinsCountBasedScore extends Score {

  private final int maxWins;
  
  public WinsCountBasedScore(int winsCount, Seat west, Seat north, Seat east) {
    super(west, north, east);
    maxWins = winsCount;
  }

  @Override
  /**
   * minimur wins record >= given
   */
  public boolean isGameFinished() {
    int mWins = Integer.MAX_VALUE;
    for (Seat seat: getSeats()){
      int seatWins = getWins(seat).getValue();
      if (seatWins < mWins) {
        mWins = seatWins;
      }
    }
    return mWins >= maxWins;
  }

}
