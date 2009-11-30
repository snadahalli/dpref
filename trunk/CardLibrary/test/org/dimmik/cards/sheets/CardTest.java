package org.dimmik.cards.sheets;

import java.util.List;

import org.dimmik.cards.sheets.card.Card;
import org.dimmik.cards.sheets.card.Rank;
import org.dimmik.cards.sheets.card.Suit;

import junit.framework.TestCase;

public class CardTest extends TestCase {

  public void testCradFromString(){
    Card c = Card.fromString("6S");
    assertEquals(Card.getInstance(Suit.SPADES, Rank.SIX), c);
    String cardsS = "6S, QD, AC";
    List<Card> cards = Card.fromDelimitedList(cardsS, ", ");
    System.out.println("cards: " + cards);
    assertEquals(Card.getInstance(Suit.SPADES, Rank.SIX), cards.get(0));
    assertEquals(Card.getInstance(Suit.DIAMONDS, Rank.QUEEN), cards.get(1));
    assertEquals(Card.getInstance(Suit.CLUBS, Rank.ACE), cards.get(2));
  }
  
}
