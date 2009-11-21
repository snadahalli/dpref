package com.dimmik.cards.sheets.card;

public class Card {
  private final Suit suit;
  private final Rank rank;

  private Card(Suit s, Rank r) {
    suit = s;
    rank = r;
  }

  public Suit getSuit() {
    return suit;
  }

  public Rank getRank() {
    return rank;
  }

  public String toString() {
    return "'" + rank + " " + suit + "'";
  }

  private static Card[] allCards = new Card[Suit.values().length
      * Rank.values().length];

  public static Card getInstance(Suit s, Rank r) {
    int cardNum = s.ordinal() * Rank.values().length + r.ordinal();
    Card c = allCards[cardNum];
    if (c == null) {
      c = new Card(s, r);
      allCards[cardNum] = c;
    }
    return c;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((rank == null) ? 0 : rank.hashCode());
    result = prime * result + ((suit == null) ? 0 : suit.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Card other = (Card) obj;
    if (rank == null) {
      if (other.rank != null)
        return false;
    } else if (!rank.equals(other.rank))
      return false;
    if (suit == null) {
      if (other.suit != null)
        return false;
    } else if (!suit.equals(other.suit))
      return false;
    return true;
  }

}
