package com.dimmik.cards.table;

import com.dimmik.cards.sheets.card.Card;

/**
 * interface for player.
 * implementation should decide how to trade and how to move
 * @author dkandrievsky
 *
 */
public interface IPlayer {

  /**
   * nect card in the move
   * @param seat
   * @param move
   * @return
   */
  Card nextCard(Seat seat, Move move);

  /**
   * trade step
   * @param seat player's seat
   * @param d current deal
   * @param bc info about the step. Game dependent.
   */
  void tradeStep(Seat seat, Deal d, ITradeStepInfo bc);

}
