package org.dimmik.cards.sheets.card;

public enum Suit {
  SPADES, CLUBS, DIAMONDS, HEARTS, NO_SUIT;
  public static Suit[] playableSuits(){
    return new Suit[] {SPADES, CLUBS, DIAMONDS, HEARTS};
  }
}
