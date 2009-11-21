package com.dimmik.cards.table;

import java.util.List;

public interface GameFactory {

  Game createGame(List<Seat> seats);

}
