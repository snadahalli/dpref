package org.dimmik.cards.sheets.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dimmik.cards.sheets.card.Card;
import org.dimmik.cards.sheets.card.Rank;
import org.dimmik.cards.sheets.card.Suit;

public class PokerInitStrategy implements ICardInitStrategy {

  public Iterable<Card> initialCards() {
    List<Card> cards = new ArrayList<Card>(32);
    for (Suit s : Suit.playableSuits()) {
      for (Rank r : Rank.values()) {
        cards.add(Card.getInstance(s, r));
      }
    }
    Collections.shuffle(cards);
    return cards;
  }

}
