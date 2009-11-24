package com.dimmik.cards.pref;

import java.util.List;

import com.dimmik.cards.pref.score.Score;
import com.dimmik.cards.table.Deal;
import com.dimmik.cards.table.Game;
import com.dimmik.cards.table.Seat;

/*
 * TODO deal with "all pass" - minimum bid in score
 * TODO score update
 * TODO document everything
 */

/**
 * Pref game - class incapsulates game preferences. Score, rules, etc.
 * @author dkandrievsky
 *
 */
public class PrefGame extends Game {

  /**
   * seats in the gemae
   */
  private final List<Seat> seats;

  /**
   * number of player to move first
   */
  private int firstDealSeatNumber = 0;

  /**
   * Score object - stores current game score and score status
   * such as level of all-pass game (3-e raspasy, for example, min bid - 8 spades)
   */
  private final Score score;
  
  /**
   * Init the instance
   * @param s seats
   * @param sc initial score
   */
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
