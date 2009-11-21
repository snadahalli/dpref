package com.dimmik.cards.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Deal {
  private final String name;
  private final List<Move> moves = new ArrayList<Move>();

  public Deal(String name) {
    this.name = name;
  }

  public void process() throws DealException {
    serveCards();
    while (isThereMoreMoves()) {
      Move move = createMove();
      move.process();
      movePostProcess(move);
      moves.add(move);
    }
  }

  protected abstract boolean isThereMoreMoves();

  protected abstract void movePostProcess(Move move);

  protected abstract Move createMove();

  protected abstract void serveCards();

  public List<Move> getMoves() {
    return moves;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    int i = 1;
    for (Move move : moves) {
      sb.append("\n").append(i).append(". ").append(move);
      i++;
    }
    return sb.toString();
  }

  public String getName() {
    return name;
  }

  public Map<Seat, List<Move>> getTricks() {
    return new HashMap<Seat, List<Move>>();
  }

}
