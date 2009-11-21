package com.dimmik.cards.prefplayers;

import com.dimmik.cards.sheets.card.Card;
import com.dimmik.cards.table.IPlayer;
import com.dimmik.cards.table.Move;
import com.dimmik.cards.table.Seat;

public class DumbPlayer implements IPlayer {

  public Card nextCard(Seat seat, Move move) {
    for (Card card : seat.getCards()) {
      if (move.isCardAcceptable(card, seat)) {
        return card;
      }
    }
    // if nothing was chosen
    return seat.getCards().get(0);
  }

}
