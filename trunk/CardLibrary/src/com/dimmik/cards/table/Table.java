package com.dimmik.cards.table;

import java.util.List;

/**
 * game table... May be not necessary at all.
 * @author dkandrievsky
 *
 */
public class Table {
  private GameFactory gameFactory;
  private List<Seat> seats;

  public Game doGame() throws GameException {
    Game game = gameFactory.createGame(seats);
    game.process();
    return game;
  }
}
