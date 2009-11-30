package org.dimmik.cards.sheets.card;

public enum Suit {
  SPADES, CLUBS, DIAMONDS, HEARTS, NO_SUIT;
  public static Suit[] playableSuits(){
    return new Suit[] {SPADES, CLUBS, DIAMONDS, HEARTS};
  }
  public static Suit fromString(String s) {
    if ("S".equalsIgnoreCase(s)){
      return SPADES;
    }
    if ("C".equalsIgnoreCase(s)){
      return CLUBS;
    }
    if ("D".equalsIgnoreCase(s)){
      return DIAMONDS;
    }
    if ("H".equalsIgnoreCase(s)){
      return HEARTS;
    }
    return NO_SUIT;
  }
}
