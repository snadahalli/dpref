package com.dimmik.cards.table;

import java.util.ArrayList;
import java.util.List;

public abstract class Deal {
    private final List<Move> moves = new ArrayList<Move>();

    public void process() throws DealException {
	serveCards();
	Move move = createMove();
	move.process();
	movePostProcess(move);
	moves.add(move);
    }

    protected abstract void movePostProcess(Move move);
    
    protected abstract Move createMove();

    protected abstract void serveCards();

    public List<Move> getMoves() {
	return moves;
    }
}
