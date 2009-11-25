package com.dimmik.cards.pref;

import java.util.List;

import com.dimmik.cards.pref.score.Score;
import com.dimmik.cards.table.Game;
import com.dimmik.cards.table.GameFactory;
import com.dimmik.cards.table.Seat;

// TODO work with score, not deals...
public class PrefGameFactory implements GameFactory {

  private int deals;
  public PrefGameFactory(int deals) {
    this.deals = deals;
  }
  
  public PrefGame createGame(List<Seat> seats) {
    return new PrefGame(new Score(deals, seats));
  }

}
