package org.dimmik.cards.sheets;

import org.dimmik.cards.sheets.card.Card;
import org.dimmik.cards.sheets.deck.CardDeck;
import org.dimmik.cards.sheets.deck.ICardDeck;
import org.dimmik.cards.sheets.deck.PrefCardInitStrategy;

public class Test {

  /**
   * @param args
   */
  public static void main(String[] args) {
    ICardDeck deck = new CardDeck(new PrefCardInitStrategy());
    int i = 0;
    for (Card c : deck) {
      System.out.println((++i) + " : " + c);
    }
    while (deck.hasMoreCards()) {
      System.out.println("capacity: " + deck.getCapacity() + " next: "
          + deck.getNextCard());
    }
    for (Card c : deck) {
      System.out.println((++i) + " : " + c);
    }
  }

}
