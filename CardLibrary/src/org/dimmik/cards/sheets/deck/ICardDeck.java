package org.dimmik.cards.sheets.deck;

import org.dimmik.cards.sheets.card.Card;

/**
 * pack of cards.
 * 
 * @author dimmik
 * 
 */
public interface ICardDeck extends Iterable<Card> {
  /**
   * returns next card
   * 
   * @return
   */
  Card getNextCard();

  boolean hasMoreCards();

  int getCapacity();
}
