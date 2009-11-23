package com.dimmik.cards.table;

import com.dimmik.cards.sheets.card.Card;

public interface IPlayer {

  Card nextCard(Seat seat, Move move);

  void tradeStep(Deal d, ITradeStepResult bc);

}
