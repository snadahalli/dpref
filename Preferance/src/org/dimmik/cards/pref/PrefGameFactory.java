package org.dimmik.cards.pref;

import java.util.List;

import org.dimmik.cards.pref.score.Score;
import org.dimmik.cards.table.GameFactory;
import org.dimmik.cards.table.Seat;


// TODO work with score, not deals...
public class PrefGameFactory implements GameFactory {

  private Score score;
  public PrefGameFactory(Score score) {
    this.score = score;
  }
  
  public PrefGame createGame(List<Seat> seats) {
    return new PrefGame(score);
  }

}
