package org.dimmik.cards.sheets.deck;

import java.util.Iterator;
import java.util.LinkedList;

import org.dimmik.cards.sheets.card.Card;

public class CardDeck implements ICardDeck {

  private final LinkedList<Card> cards = new LinkedList<Card>();

  public CardDeck(ICardInitStrategy cs) {
    for (Card c : cs.initialCards()) {
      cards.add(c);
    }
  }

  public Card getNextCard() {
    return cards.removeFirst();
  }

  public boolean hasMoreCards() {
    return getCapacity() > 0;
  }

  public Iterator<Card> iterator() {
    return cards.iterator();
  }

  public int getCapacity() {
    return cards.size();
  }

}
