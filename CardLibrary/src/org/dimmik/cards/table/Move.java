package org.dimmik.cards.table;

import java.util.ArrayList;
import java.util.List;

import org.dimmik.cards.sheets.card.Card;


/**
 * abstract move
 * @author dkandrievsky
 *
 */
public abstract class Move {
  private final List<Seat> seats;
  private final List<Card> cards = new ArrayList<Card>();
  private final List<Turn> turns = new ArrayList<Turn>();
  private final Deal deal;
  
  
  protected Move(Deal d, List<Seat> s, int first) {
    if (s == null || first >= s.size()) {
      throw new IllegalArgumentException("first should be inside seats, not "
          + first);
    }
    List<Seat> correctlyArranged = rearrange(s, first);
    seats = correctlyArranged;
    deal = d;
  }

  private List<Seat> rearrange(List<Seat> s, int first) {
    List<Seat> correctlyArranged = new ArrayList<Seat>();
    for (int i = first; i < s.size(); i++) {
      correctlyArranged.add(s.get(i));
    }
    for (int i = 0; i < first; i++) {
      correctlyArranged.add(s.get(i));
    }
    return correctlyArranged;
  }

  public void process() throws CardIsNotAcceptableException {
    for (Seat seat : seats) {
      Card card = seat.nextCard(deal, this);
      if (!isCardAcceptable(card, seat)) {
        throw new CardIsNotAcceptableException(card, seat);
      }
      cards.add(card);
      turns.add(new Turn(card, seat));
    }
  }

  public abstract boolean isCardAcceptable(Card card, Seat seat);

  public List<Card> getCards() {
    return cards;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    for (Turn t : getTurns()) {
      sb.append(t.seat).append(" -> ").append(t.card).append("; ");
    }
    sb.append("winner: ").append(whoWon());
    return sb.toString();
  }

  public static class Turn {
    public Turn(Card c, Seat s) {
      seat = s;
      card = c;
    }

    public final Card card;
    public final Seat seat;
  }

  public abstract Seat whoWon();

  public List<Turn> getTurns() {
    return turns;
  }
}
