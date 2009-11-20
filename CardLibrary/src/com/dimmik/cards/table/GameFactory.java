package com.dimmik.cards.table;

import java.util.List;

public abstract class GameFactory {

    public abstract Game createGame(List<Seat> seats);

}
