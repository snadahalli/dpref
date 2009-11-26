package org.dimmik.cards.sheets.deck;

import org.dimmik.cards.sheets.card.Card;

public interface ICardInitStrategy {
  Iterable<Card> initialCards();
}
