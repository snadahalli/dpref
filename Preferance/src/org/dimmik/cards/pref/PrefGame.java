package org.dimmik.cards.pref;

import java.util.List;

import org.dimmik.cards.pref.score.Score;
import org.dimmik.cards.table.Deal;
import org.dimmik.cards.table.DealException;
import org.dimmik.cards.table.Game;
import org.dimmik.cards.table.Seat;


/*
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
   * seats in the game
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
  public PrefGame(Score sc) {
    seats = sc.getSeats();
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
  protected void updateGameStatus(Deal deal) throws DealException {
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


  public Score getScore() {
    return score;
  }
  
}
