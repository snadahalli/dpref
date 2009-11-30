package org.dimmik.cards.pref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dimmik.cards.pref.trade.Bid;
import org.dimmik.cards.pref.trade.Contract;
import org.dimmik.cards.sheets.card.Card;
import org.dimmik.cards.sheets.card.Suit;
import org.dimmik.cards.sheets.deck.CardDeck;
import org.dimmik.cards.sheets.deck.ICardDeck;
import org.dimmik.cards.sheets.deck.PrefCardInitStrategy;
import org.dimmik.cards.table.Deal;
import org.dimmik.cards.table.DealException;
import org.dimmik.cards.table.Move;
import org.dimmik.cards.table.Seat;


public class PrefDeal extends Deal {

  /**
   * card deck for the deal
   */
  private ICardDeck deck = new CardDeck(new PrefCardInitStrategy());

  /**
   * seats, participating the deal
   */
  private final List<Seat> seats;

  /**
   * who moves first in the deal
   */
  private final int firstMoveSeatIdx;

  /**
   * next seat to move
   */
  private int currentMove;

  /**
   * two side cards. filled during serveCards
   */
  private TwoCards sideCards;

  /**
   * two thrown cards may be filled during trade
   */
  private TwoCards thrownCards;

  /**
   * tricks in the deal
   */
  private final Map<Seat, List<Move>> tricks = new HashMap<Seat, List<Move>>();

  /**
   * contract, firmed up during trade
   */
  private final Contract contract;

  private boolean allPassGame;

  /**
   * Initiate the deal
   * 
   * @param name
   *          - name of the deal. Usually just number
   * @param seats
   *          - seats for deal
   * @param firstMove
   *          - who moves first
   */
  public PrefDeal(String name, List<Seat> seats, int firstMove) {
    super(name);
    this.seats = seats;
    firstMoveSeatIdx = firstMove;
    currentMove = firstMoveSeatIdx;
    for (Seat seat : seats) {
      tricks.put(seat, new ArrayList<Move>());
    }
    contract = newContract(seats);
  }

  /**
   * create move instance for the next move
   * 
   * @throws DealException
   */
  @Override
  protected Move createMove() throws DealException {
    Suit trump = contract.getGame().getSuit();
    if (allPassGame && getMoves().size() < 2) {
      Card f = sideCards.getFirst();
      Card s = sideCards.getSecond();
      PrefPassMove move = new PrefPassMove(seats, currentMove, getMoves()
          .size() == 0 ? f : s);
      return move;
    } else {
      PrefMove move = new PrefMove(seats, currentMove, trump);
      return move;
    }
  }

  /**
   * serve cards to seats. Now - just 10, 10, 10 to seats and 2 to side cards
   */
  @Override
  protected void serveCards() {
    add10Cards(seats.get(0));
    add10Cards(seats.get(1));
    add10Cards(seats.get(2));
    Card fSide = deck.getNextCard();
    Card sSide = deck.getNextCard();
    sideCards = new TwoCards(fSide, sSide);
  }

  /**
   * utility method - ad 10 card to seat
   * 
   * @param seat
   */
  private void add10Cards(Seat seat) {
    for (int i = 0; i < 10; i++) {
      seat.addCard(getDeck().getNextCard());
    }
  }

  /**
   * add tricks to seat, see tricks field changes who moves next see currentMove
   * field
   */
  @Override
  protected void movePostProcess(Move move) {
    Seat winner = move.whoWon();
    addTrick(winner, move);
    // for all-pass-game first 3 moves - one seat
    if (!allPassGame || getMoves().size() >= 3) {
      currentMove = seats.indexOf(winner);
    }
  }

  /**
   * add trick to the winner seat
   * 
   * @param winner
   *          winner seat
   * @param move
   *          the move won
   */
  private void addTrick(Seat winner, Move move) {
    List<Move> seatMoves = getTricks().get(winner);
    if (seatMoves == null) {
      seatMoves = new ArrayList<Move>();
      tricks.put(winner, seatMoves);
    }
    seatMoves.add(move);
  }

  /**
   * set deck for the deal. For debugging purposes
   * 
   * @param deck
   */
  public void setDeck(ICardDeck deck) {
    this.deck = deck;
  }

  /**
   * deck for current deal
   * 
   * @return
   */
  public ICardDeck getDeck() {
    return deck;
  }

  /**
   * seats in the deal
   * 
   * @return
   */
  public List<Seat> getSeats() {
    return seats;
  }

  /**
   * decision whether it makes sense to move further
   */
  @Override
  protected boolean isThereMoreMoves() {
    return (seats.get(0).getCards().size() > 0);
  }

  /**
   * side cards
   * 
   * @return
   */
  public TwoCards getSideCards() {
    return sideCards;
  }

  /**
   * thrown cards
   * 
   * @return
   */
  public TwoCards getThrownCards() {
    return thrownCards;
  }

  /**
   * tricks in the deal
   */
  //@Override
  public Map<Seat, List<Move>> getTricks() {
    return tricks;
  }

  /**
   * returns count of the tricks
   * @param seat
   * @return
   */
  public int getTricksCount(Seat seat) {
    return getTricks().get(seat).size();
  }
  
  /**
   * 
   * @return list where first move seats is the first element
   */
  private List<Seat> getMoveOrderedSeats() {
    List<Seat> ordered;
    ordered = new ArrayList<Seat>();
    for (int i = currentMove; i < seats.size(); i++) {
      ordered.add(seats.get(i));
    }
    for (int i = 0; i < currentMove; i++) {
      ordered.add(seats.get(i));
    }
    return ordered;
  }

  /**
   * May depend on current game status. For example, for 2nd 'all-pass' game at
   * least 8 spades contract available
   * 
   * @return new Contract instance
   */
  private Contract newContract(List<Seat> ss) {
    // TODO add dependency on current game status.
    // TODO May be on score (makes sense store it somewhere in deal).
    return new Contract(ss);
  }

  /**
   * contract for current deal
   * 
   * @return
   */
  public Contract getContract() {
    return contract;
  }

  /**
   * trade process. Determine winner Set
   */
  @Override
  public void performTrade() throws DealException {
    determineContractWinner();
    if (contract.getWinnerSeat() == null) {
      contract.setGame(Bid.PASS);
      setDealAllPass();
    } else { // add real game
      // TODO deal with possible pass/vist of non-winners
      // pass-pass, pass-vist, pass-half-(pass/vist)
      Seat winner = contract.getWinnerSeat();
      giveWinnerSideCards(winner);
      getThrownCardsFromWinner(winner);
      Bid game = setGameForDeal(winner);
      contract.setGame(game);
      // TODO deal with pass/vist for other seats 
    }
  }

  private void setDealAllPass() {
    allPassGame = true;
  }

  /**
   * winner decides which game he wants
   * 
   * @param winner
   * @return
   */
  private Bid setGameForDeal(Seat winner) {
    PrefTradeStepInfo stInfo = new PrefTradeStepInfo(PrefTradeStep.SET_GAME);
    winner.tradeStep(this, stInfo);
    return stInfo.getGame();
  }

  /**
   * get from winner thrown cards
   * 
   * @param winner
   * @throws DealException
   *           if winner did not throw correctly
   */
  private void getThrownCardsFromWinner(Seat winner) throws DealException {
    PrefTradeStepInfo stInfo = new PrefTradeStepInfo(
        PrefTradeStep.GET_THROWN_CARDS);
    winner.tradeStep(this, stInfo);
    checkCardAreCorrectlyThrown(winner, stInfo.getFirstThrown(), stInfo
        .getSecondThrown());
    Card f = stInfo.getFirstThrown();
    Card s = stInfo.getSecondThrown();
    thrownCards = new TwoCards(f, s);
  }

  /**
   * checks if cards are correctly thrown
   * 
   * @param winner
   * @param firstThrown
   * @param secondThrown
   * @throws DealException
   *           if thrown incorrectly
   */
  private void checkCardAreCorrectlyThrown(Seat winner, Card firstThrown,
      Card secondThrown) throws DealException {
    if (firstThrown == null || secondThrown == null) {
      throw new DealException("should be 2 cards thrown");
    }
    // check that cards are really thrown and so on
    if (winner.getCards().size() != 10) {
      throw new DealException("winner should keep 10 cards");
    }
    if (winner.getCards().contains(firstThrown)
        || winner.getCards().contains(secondThrown)) {
      throw new DealException(
          "winner should really throw two cards (not keep them)");
    }
  }

  /**
   * give winner side cards ("prikup")
   * 
   * @param winner
   * @return
   */
  private Seat giveWinnerSideCards(Seat winner) {
    PrefTradeStepInfo stInfo = new PrefTradeStepInfo(
        PrefTradeStep.GIVE_SIDE_CARDS);
    // add 2 cards
    winner.addCard(sideCards.getFirst());
    winner.addCard(sideCards.getSecond());
    // do anything it wants. Analyze, decide, ...
    winner.tradeStep(this, stInfo);

    return winner;
  }

  /**
   * perform trade and decide who wins
   * 
   * @throws DealException
   *           if particular bid is not correct
   */
  private void determineContractWinner() throws DealException {
    List<Seat> ordered = getMoveOrderedSeats();
    int bidderIdx = 0;
    while (!contract.isTradeFinished()) {
      Seat bidder = ordered.get(bidderIdx);
      if (!contract.seatHasBidenPass(bidder)) {
        Bid bid = doBid(bidder);
        contract.addBid(bidder, bid);
      }
      bidderIdx++;
      if (bidderIdx >= ordered.size()) {
        bidderIdx = 0;
      }
    }
  }

  /**
   * bidder seat does the bid
   * 
   * @param bidder
   *          - seat to ask for bid
   * @return bid
   * @throws DealException
   *           if bid is unacceptable
   */
  private Bid doBid(Seat bidder) throws DealException {
    PrefTradeStepInfo stInfo = new PrefTradeStepInfo(PrefTradeStep.SET_BID);
    bidder.tradeStep(this, stInfo);
    Bid bid = stInfo.getBid();
    if (!contract.isBidCorrect(bidder, bid)) {
      throw new DealException("bid " + bid + " is not acceptable");
    }
    return bid;
  }

  public static class TwoCards {
    private final Card first;
    private final Card second;

    public TwoCards(Card f, Card s) {
      first = f;
      second = s;
    }

    public Card getFirst() {
      return first;
    }

    public Card getSecond() {
      return second;
    }
  }

  public boolean isAllPassGame() {
    return allPassGame;
  }

}
