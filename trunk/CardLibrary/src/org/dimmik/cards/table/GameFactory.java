package org.dimmik.cards.table;

import java.util.List;

public interface GameFactory {

  /**
   * return new instance of game
   * @param seats
   * @return
   */
  Game createGame(List<Seat> seats);

}
