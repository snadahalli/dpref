package org.dimmik.cards.pref;

import java.util.List;

import org.dimmik.cards.sheets.card.Card;
import org.dimmik.cards.sheets.card.Suit;
import org.dimmik.cards.table.Seat;


/**
 * all-pass move - one of two when side cards are opened
 * 
 * @author dkandrievsky
 * 
 */
public class PrefPassMove extends PrefMove {

  private final Card sideCard;
  
  public PrefPassMove(List<Seat> seats, int first, Card sideCard) {
    super(seats, first, Suit.NO_SUIT);
    getCards().add(sideCard);
    this.sideCard = sideCard;
  }

  @Override
  public String toString(){
    StringBuffer sb = new StringBuffer();
    sb.append("\n").append("side: " + sideCard).append("\n");
    sb.append(super.toString());
    return sb.toString();
  }
}
