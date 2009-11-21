package com.dimmik.cards.pref;

import java.util.List;

import com.dimmik.cards.table.Game;
import com.dimmik.cards.table.GameFactory;
import com.dimmik.cards.table.Seat;

public class PrefGameFactory implements GameFactory {

  public Game createGame(List<Seat> seats) {
    return new PrefGame(seats);
  }

}
