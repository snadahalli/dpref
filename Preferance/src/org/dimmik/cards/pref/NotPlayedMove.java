package org.dimmik.cards.pref;

import java.util.List;

import org.dimmik.cards.sheets.card.Card;
import org.dimmik.cards.table.Deal;
import org.dimmik.cards.table.Move;
import org.dimmik.cards.table.Seat;

public class NotPlayedMove extends Move {

  private final Seat winner;
  
  protected NotPlayedMove(Deal d, List<Seat> s, int first, Seat winner) {
    super(d, s, first);
   this.winner = winner;
  }

  @Override
  public boolean isCardAcceptable(Card card, Seat seat) {
    throw new IllegalStateException("this move is not intended as played move");
  }

  @Override
  public Seat whoWon() {
    return winner;
  }

}
