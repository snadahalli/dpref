package com.dimmik.cards.sheets.deck;

import com.dimmik.cards.sheets.card.Card;

public interface ICardInitStrategy {
	Iterable<Card> initialCards();
}
