package com.dimmik.cards.table;

import java.util.List;

public class Table {
    private GameFactory gameFactory;
    private List<Seat> seats;
    public Game doGame() throws GameException{
	Game game = gameFactory.createGame(seats);
	game.process();
	return game;
    }
}
