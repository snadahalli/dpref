package com.dimmik.cards.pref;

import java.util.List;

import com.dimmik.cards.sheets.card.Card;
import com.dimmik.cards.sheets.card.Suit;
import com.dimmik.cards.table.Move;
import com.dimmik.cards.table.Seat;

public class PrefMove extends Move {

  /**
   * may be null.
   */
  private final Suit trump;

  public PrefMove(List<Seat> seats, int first, Suit trump) {
    super(seats, first);
    this.trump = trump;
  }

  @Override
  public Seat whoWon() {
    List<Move.Turn> turns = getTurns();
    // 1. Decide what is turn Suit
    Suit turnSuit = turns.get(0).card.getSuit();
    // find higher trump
    Turn higherTrumpTurn = null;
    // just find higher trump in move
    for (Turn turn : turns) {
      if (suitIsTrump(turn.card.getSuit())) {
        if (higherTrumpTurn == null) {
          higherTrumpTurn = turn;
        } else {
          if (rankHigher(turn, higherTrumpTurn)) {
            higherTrumpTurn = turn;
          }
        }
      }
    }
    if (higherTrumpTurn != null) {
      return higherTrumpTurn.seat;
    }
    // otherwise find highest of move suit
    Turn higherTurn = turns.get(0);
    for (Turn turn : turns) {
      if (suitsMatch(turnSuit, turn.card.getSuit())) {
        if (rankHigher(turn, higherTurn)) {
          higherTurn = turn;
        }
      }
    }
    return higherTurn.seat;
  }

  private boolean rankHigher(Turn turn, Turn otherTurn) {
    return turn.card.getRank().getValue() > otherTurn.card.getRank().getValue();
  }

  @Override
  public boolean isCardAcceptable(Card card, Seat seat) {
    if (getCards().isEmpty()) {
      // first move - anything
      return true;
    }
    Card firstCard = getCards().get(0);
    Suit firstCardSuit = firstCard.getSuit();
    Suit cardSuit = card.getSuit();

    if (suitsMatch(firstCardSuit, cardSuit)) {
      return true;
    }
    // Suits don't match
    // ensure seat has no suit
    for (Card seatCard : seat.getCards()) {
      if (suitsMatch(firstCardSuit, seatCard.getSuit())) {
        // he has suit
        return false;
      }
    }
    // seat has no suit
    // trump acceptable
    if (suitIsTrump(cardSuit)) {
      return true;
    }

    // check id seat has trump
    for (Card seatCard : seat.getCards()) {
      if (suitIsTrump(seatCard.getSuit())) {
        // he has trump
        return false;
      }
    }
    // Seat has no trumps, no suit - any card acceptable
    return true;
  }

  private boolean suitIsTrump(Suit cardSuit) {
    if (trump == null) {
      return false;
    }
    return cardSuit.equals(trump);
  }

  private boolean suitsMatch(Suit firstCardSuit, Suit cardSuit) {
    return cardSuit == firstCardSuit;
  }

  @Override
  public String toString() {
    return super.toString() + " (tr: " + trump + ")";
  }

}
