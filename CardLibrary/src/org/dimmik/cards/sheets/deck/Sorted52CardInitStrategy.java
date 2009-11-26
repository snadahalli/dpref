package org.dimmik.cards.sheets.deck;

import java.util.ArrayList;
import java.util.List;

import org.dimmik.cards.sheets.card.Card;
import org.dimmik.cards.sheets.card.Rank;
import org.dimmik.cards.sheets.card.Suit;


public class Sorted52CardInitStrategy implements ICardInitStrategy {

  @Override
  public Iterable<Card> initialCards() {
    List<Card> cards = new ArrayList<Card>(32);
    for (Suit s : Suit.playableSuits()) {
      for (Rank r : Rank.values()) {
        if (r.getValue() > 6) {
          cards.add(Card.getInstance(s, r));
        }
      }
    }
    return cards;
  }

}
