package org.dimmik.cards.table;

import java.util.ArrayList;
import java.util.List;

public abstract class Deal {
  private final String name;
  private final List<Move> moves = new ArrayList<Move>();

  public Deal(String name) {
    this.name = name;
  }

  public void process() throws DealException {
    serveCards();
    performTrade();
    while (isThereMoreMoves()) {
      Move move = createMove();
      move.process();
      moves.add(move);
      movePostProcess(move);
    }
  }

  /**
   * trade process
   * 
   * @throws DealException
   */
  protected abstract void performTrade() throws DealException;

  /**
   * 
   * @return true if there should be at least one more move
   */
  protected abstract boolean isThereMoreMoves();

  /**
   * do something after certain move called AFTER move is added to move list
   * (available through getMoves)
   * 
   * @param move
   */
  protected abstract void movePostProcess(Move move);

  /**
   * create move instance
   * 
   * @return new instance of move
   */
  protected abstract Move createMove() throws DealException;

  /**
   * serves cards to seats
   */
  protected abstract void serveCards();

  public List<Move> getMoves() {
    return moves;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(name);
    if (moves.size() > 0) {
      int i = 1;
      for (Move move : moves) {
        sb.append("\n").append(i).append(". ").append(move);
        i++;
      }
    } else {
      sb.append("\nNO MOVES\n");
    }
    return sb.toString();
  }

  public String getName() {
    return name;
  }

  // /**
  // * returns tricks.
  // * @return
  // */
  // public abstract Map<Seat, List<Move>> getTricks();/* {
  // return new HashMap<Seat, List<Move>>();
  // }*/

}
