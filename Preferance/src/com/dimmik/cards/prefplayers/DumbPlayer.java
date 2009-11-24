package com.dimmik.cards.prefplayers;

import java.util.List;
import java.util.Random;

import com.dimmik.cards.pref.PrefDeal;
import com.dimmik.cards.pref.PrefTradeStepInfo;
import com.dimmik.cards.pref.trade.Bid;
import com.dimmik.cards.pref.trade.Contract;
import com.dimmik.cards.sheets.card.Card;
import com.dimmik.cards.table.Deal;
import com.dimmik.cards.table.DealException;
import com.dimmik.cards.table.IPlayer;
import com.dimmik.cards.table.ITradeStepInfo;
import com.dimmik.cards.table.Move;
import com.dimmik.cards.table.Seat;

/**
 * just dumb pref player. It would be useful to inherit from it to implement
 * real player activities
 * 
 * @author dkandrievsky
 * 
 */
public class DumbPlayer implements IPlayer {

  private final int passProbability;

  public DumbPlayer(int passProbability) {
    this.passProbability = passProbability;
  }

  public Card nextCard(Seat seat, Move move) {
    for (Card card : seat.getCards()) {
      if (move.isCardAcceptable(card, seat)) {
        return card;
      }
    }
    // if nothing was chosen
    return seat.getCards().get(0);
  }

  public final void tradeStep(Seat seat, Deal d, ITradeStepInfo bc) {
    if (!(d instanceof PrefDeal)) {
      throw new IllegalStateException("deal must be PrefDeal");
    }
    if (!(bc instanceof PrefTradeStepInfo)) {
      throw new IllegalStateException("bid container must be PrefBidContainer");
    }
    prefTradeStep(seat, (PrefDeal) d, (PrefTradeStepInfo) bc);

  }

  private final static Random r = new Random();

  /**
   * just randomly - either pass or min available bid
   * 
   * @param d
   * @param bc
   */
  private final void prefTradeStep(Seat seat, PrefDeal d, PrefTradeStepInfo bc) {
    switch (bc.getStep()) {
    case SET_BID:
      setBid(seat, d, bc);
      break;
    case GIVE_SIDE_CARDS:
      giveSideCards(seat, d, bc);
      break;
    case GET_THROWN_CARDS:
      getThrownCards(seat, d, bc);
      break;
    case SET_GAME:
      setGame(seat, d, bc);
      break;
    }
  }

  protected void setGame(Seat seat, PrefDeal d, PrefTradeStepInfo bc) {
    Contract c = d.getContract();
    Bid game = Bid.PASS;
    game = c.getFirstAvailableGame();
    // think how to avoid this possibility
    // c.setGame(any game);
    bc.setGame(game);

  }

  protected void getThrownCards(Seat seat, PrefDeal d, PrefTradeStepInfo bc) {
    // dumb - first 2 cards
    List<Card> cards = seat.getCards();
    bc.setFirstThrown(cards.remove(0));
    bc.setSecondThrown(cards.remove(1));
  }

  protected void giveSideCards(Seat seat, PrefDeal d, PrefTradeStepInfo bc) {
    // just nothing
  }

  protected void setBid(Seat seat, PrefDeal d, PrefTradeStepInfo bc) {
    int prob = r.nextInt(100);
    boolean doPass = prob < passProbability;
    Bid bid;
    if (doPass) {
      bid = Bid.PASS;
    } else {
      bid = d.getContract().getMinAvailableBid(seat);
    }
    bc.setBid(bid);
  }

}
