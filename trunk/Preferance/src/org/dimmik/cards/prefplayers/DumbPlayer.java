package org.dimmik.cards.prefplayers;

import java.util.List;
import java.util.Random;

import org.dimmik.cards.pref.PrefDeal;
import org.dimmik.cards.pref.PrefTradeStepInfo;
import org.dimmik.cards.pref.PrefDeal.TwoCards;
import org.dimmik.cards.pref.trade.Bid;
import org.dimmik.cards.pref.trade.Contract;
import org.dimmik.cards.sheets.card.Card;
import org.dimmik.cards.table.Deal;
import org.dimmik.cards.table.Move;
import org.dimmik.cards.table.Seat;

/**
 * just dumb pref player. It would be useful to inherit from it to implement
 * real player activities
 * 
 * @author dkandrievsky
 * 
 */
public class DumbPlayer extends AbstractPrefPlayer {

  private final static Random r = new Random();
  private final int passProbability;
  private final int vistProbability;

  public DumbPlayer(int passProbability) {
    this.passProbability = passProbability;
    this.vistProbability = 0;
  }

  public DumbPlayer(int passProbability, int vistP) {
    this.passProbability = passProbability;
    this.vistProbability = vistP;
  }

  public DumbPlayer() {
    this(50);
  }

  @Override
  public Card nextCard(Seat seat, PrefDeal d, Move move) {
    for (Card card : seat.getCards()) {
      if (move.isCardAcceptable(card, seat)) {
        return card;
      }
    }
    // if nothing was chosen
    return seat.getCards().get(0);
  }


  protected void giveSideCards(Seat seat, PrefDeal d, PrefTradeStepInfo bc) {
    // just nothing
  }

  @Override
  protected TwoCards getThrownCards(Seat seat, PrefDeal d) {
    Card f = seat.getCards().remove(0);
    Card s = seat.getCards().remove(0);
    return new TwoCards(f, s);
  }

  @Override
  protected Bid setBid(Seat seat, PrefDeal d) {
    int prob = r.nextInt(100);
    boolean doPass = prob < passProbability;
    Bid bid;
    if (doPass) {
      bid = Bid.PASS;
    } else {
      bid = d.getContract().getMinAvailableBid(seat);
    }
    return bid;
  }

  @Override
  protected Bid setGame(Seat seat, PrefDeal d) {
    Contract c = d.getContract();
    Bid game = c.getFirstAvailableGame();
    // TODO think how to avoid this possibility
    // c.setGame(any game);
    return game;
  }

  @Override
  protected Bid setVist(Seat seat, PrefDeal d, List<Bid> availableBids) {
    int prob = r.nextInt(100);
    if (prob < vistProbability) {
      return Bid.VIST;
    }
    return availableBids.get(0);
  }

}
