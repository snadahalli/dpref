package org.dimmik.cards.pref;

import java.util.List;

import org.dimmik.cards.pref.score.Score;
import org.dimmik.cards.table.Game;
import org.dimmik.cards.table.GameFactory;
import org.dimmik.cards.table.Seat;


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
