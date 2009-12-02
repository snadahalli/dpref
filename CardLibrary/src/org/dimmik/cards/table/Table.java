package org.dimmik.cards.table;

import java.util.List;

/**
 * game table... May be not necessary at all.
 * @author dkandrievsky
 *
 */
// TODO do we really need this class at all?.. 
public class Table {
  private final GameFactory gameFactory;
  private final List<Seat> seats;

  public Table(GameFactory gf, List<Seat> seats){
    gameFactory = gf;
    this.seats = seats; 
  }
  
  public Game doGame() throws GameException {
    Game game = gameFactory.createGame(seats);
    game.process();
    return game;
  }

  public GameFactory getGameFactory() {
    return gameFactory;
  }


  public List<Seat> getSeats() {
    return seats;
  }

}
