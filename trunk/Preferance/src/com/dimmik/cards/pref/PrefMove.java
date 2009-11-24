package com.dimmik.cards.pref;

import java.util.List;

import com.dimmik.cards.sheets.card.Card;
import com.dimmik.cards.sheets.card.Suit;
import com.dimmik.cards.table.Move;
import com.dimmik.cards.table.Seat;

public class PrefMove extends Move {

  /**
   * Not null
   */
  private final Suit trump;

  public PrefMove(List<Seat> seats, int first, Suit trump) {
    super(seats, first);
    if (trump == null){
      throw new IllegalArgumentException("trump can not be null");
    }
    this.trump = trump;
  }

  @Override
  public Seat whoWon() {
    List<Move.Turn> turns = getTurns();
    // 1. Decide what is turn Suit
    Suit turnSuit = turns.get(0).card.getSuit();
    // find higher trump
    Turn higherTrumpTurn = findHigherTrump(turns);
    if (higherTrumpTurn != null) {
      return higherTrumpTurn.seat;
    }
    // otherwise find highest of move suit
    Turn higherTurn = findHigherTurn(turns, turnSuit);
    return higherTurn.seat;
  }

  private Turn findHigherTurn(List<Move.Turn> turns, Suit turnSuit) {
    Turn higherTurn = turns.get(0);
    for (Turn turn : turns) {
      if (turn.card.getSuit() == turnSuit) {
        if (rankHigher(turn, higherTurn)) {
          higherTurn = turn;
        }
      }
    }
    return higherTurn;
  }

  private Turn findHigherTrump(List<Move.Turn> turns) {
    Turn higherTrumpTurn = null;
    // just find higher trump in move
    for (Turn turn : turns) {
      if (turn.card.getSuit() == trump) {
        if (higherTrumpTurn == null) {
          higherTrumpTurn = turn;
        } else {
          if (rankHigher(turn, higherTrumpTurn)) {
            higherTrumpTurn = turn;
          }
        }
      }
    }
    return higherTrumpTurn;
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
    Suit moveSuit = firstCard.getSuit();
    Suit cardSuit = card.getSuit();

    if (cardSuit == moveSuit) {
      return true;
    }
    // Suits don't match
    // ensure seat has no suit
    for (Card seatCard : seat.getCards()) {
      if (seatCard.getSuit() == moveSuit) {
        // he has suit - should have placed it
        return false;
      }
    }
    // seat has no suit
    // trump acceptable
    if (cardSuit == trump) {
      return true;
    }

    // check if seat has trump
    for (Card seatCard : seat.getCards()) {
      if (seatCard.getSuit() == trump) {
        // he has trump - should have placed it
        return false;
      }
    }
    // Seat has no trumps, no suit - any card acceptable
    return true;
  }

  @Override
  public String toString() {
    return super.toString() + " (tr: " + trump + ")";
  }

}
