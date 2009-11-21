package com.dimmik.cards.pref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dimmik.cards.sheets.card.Card;
import com.dimmik.cards.sheets.card.Suit;
import com.dimmik.cards.sheets.deck.CardDeck;
import com.dimmik.cards.sheets.deck.ICardDeck;
import com.dimmik.cards.sheets.deck.PrefCardInitStrategy;
import com.dimmik.cards.table.Deal;
import com.dimmik.cards.table.Move;
import com.dimmik.cards.table.Seat;

public class PrefDeal extends Deal {
  private ICardDeck deck = new CardDeck(new PrefCardInitStrategy());
  private final List<Seat> seats;
  private final int firstMoveSeatIdx;
  private int currentMove;
  private List<Card> sideCards = new ArrayList<Card>();
  private List<Card> thrownCards = new ArrayList<Card>();

  private final Map<Seat, List<Move>> tricks = new HashMap<Seat, List<Move>>();

  public PrefDeal(String name, List<Seat> seats, int firstMove) {
    super(name);
    this.seats = seats;
    firstMoveSeatIdx = firstMove;
    currentMove = firstMoveSeatIdx;
    for (Seat seat : seats){
      tricks.put(seat, new ArrayList<Move>());
    }
  }

  @Override
  protected Move createMove() {
    // TODO get trump. from trade
    // XXX Pay attention - right now no trump
    Suit trump = Suit.SPADES;
    trump = null;
    if (currentMove > 2) {
      System.out.println("cm: " + currentMove);
    }
    PrefMove move = new PrefMove(seats, currentMove, trump);
    return move;
  }

  @Override
  protected void serveCards() {
    add10Cards(seats.get(0));
    add10Cards(seats.get(1));
    add10Cards(seats.get(2));
    sideCards.add(getDeck().getNextCard());
    sideCards.add(getDeck().getNextCard());
  }

  private void add10Cards(Seat seat) {
    for (int i = 0; i < 10; i++) {
      seat.addCard(getDeck().getNextCard());
    }
  }

  @Override
  protected void movePostProcess(Move move) {
    Seat winner = move.whoWon();
    addTrick(winner, move);
    currentMove = seats.indexOf(winner);
    if (currentMove > 2) {
      System.out.println("move: " + currentMove);
    }
  }

  private void addTrick(Seat winner, Move move) {
    List<Move> seatMoves = getTricks().get(winner);
    if (seatMoves == null) {
      seatMoves = new ArrayList<Move>();
      getTricks().put(winner, seatMoves);
    }
    seatMoves.add(move);
  }

  public void setDeck(ICardDeck deck) {
    this.deck = deck;
  }

  public ICardDeck getDeck() {
    return deck;
  }

  public List<Seat> getSeats() {
    return seats;
  }

  @Override
  protected boolean isThereMoreMoves() {
    return (seats.get(0).getCards().size() > 0);
  }

  public List<Card> getSideCards() {
    return sideCards;
  }

  public List<Card> getThrownCards() {
    return thrownCards;
  }

  @Override
  public Map<Seat, List<Move>> getTricks() {
    return tricks;
  }

  @Override
  protected void performTrade() {
    // TODO perform trade, yeah
    // Decide what will be trump
    // and so on
    
  }
}
