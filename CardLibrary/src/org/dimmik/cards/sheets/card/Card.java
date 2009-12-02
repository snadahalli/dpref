package org.dimmik.cards.sheets.card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Card implements Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 4608280795416738732L;
  private final Suit suit;
  private final Rank rank;

  private Card(Suit s, Rank r) {
    if (s == null || s == Suit.NO_SUIT) {
      throw new IllegalArgumentException("suit should not be " + s);
    }
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

  public static Card fromString(String s) {
    String suit = s.substring(s.length() - 1);
    String rank = s.substring(0, s.length() - 1);
    Card c = getInstance(Suit.fromString(suit), Rank.fromString(rank));
    return c;
  }

  public static List<Card> fromDelimitedList(String list, String delim) {
    String[] cardStrings = list.split(delim);
    List<Card> cards = new ArrayList<Card>();
    for (String cs : cardStrings) {
      cards.add(fromString(cs));
    }
    return cards;
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
