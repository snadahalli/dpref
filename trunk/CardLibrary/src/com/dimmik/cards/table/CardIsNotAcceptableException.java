package com.dimmik.cards.table;

import com.dimmik.cards.sheets.card.Card;

public class CardIsNotAcceptableException extends DealException {

  /**
     * 
     */
  private static final long serialVersionUID = 7170230638490016821L;
  private final Card card;
  private final Seat seat;

  public CardIsNotAcceptableException(Card card, Seat seat) {
    super();
    this.card = card;
    this.seat = seat;
  }

  public Card getCard() {
    return card;
  }

  public Seat getSeat() {
    return seat;
  }

}
