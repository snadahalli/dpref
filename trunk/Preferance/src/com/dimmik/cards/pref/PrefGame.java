package com.dimmik.cards.pref;

import java.util.List;

import com.dimmik.cards.pref.score.Score;
import com.dimmik.cards.table.Deal;
import com.dimmik.cards.table.Game;
import com.dimmik.cards.table.Seat;


public class PrefGame extends Game {

  private final List<Seat> seats;

  private int firstDealSeatNumber = 0;

  private final Score score;
  
  public PrefGame(List<Seat> s, Score sc) {
    seats = s;
    if (seats.size() != 3) {
      throw new IllegalArgumentException("seat size should be 3");
    }
    score = sc;
  }

  @Override
  protected boolean gameMakesSense() {
    return !score.isGameFinished();
  }

  @Override
  protected Deal newDeal() {
    return new PrefDeal("" + getDeals().size(), seats, firstDealSeatNumber);
  }

  @Override
  protected void updateGameStatus(Deal deal) {
    if (!(deal instanceof PrefDeal)) {
      throw new IllegalStateException("deal must be PrefDeal");
    }
    score.update((PrefDeal)deal);
  }

  @Override
  protected void dealPostProcess(Deal deal) {
    // choose next player
    firstDealSeatNumber++;
    if (firstDealSeatNumber >= seats.size()) {
      firstDealSeatNumber = 0;
    }
  }

}
