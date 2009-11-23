package com.dimmik.cards.prefplayers;

import java.util.Random;

import com.dimmik.cards.pref.PrefTradeStepResult;
import com.dimmik.cards.pref.PrefDeal;
import com.dimmik.cards.pref.trade.Bid;
import com.dimmik.cards.sheets.card.Card;
import com.dimmik.cards.table.Deal;
import com.dimmik.cards.table.ITradeStepResult;
import com.dimmik.cards.table.IPlayer;
import com.dimmik.cards.table.Move;
import com.dimmik.cards.table.Seat;

/**
 * just bumb pref player.
 * It would be useful to inherit from it to implement
 * real player
 * @author dkandrievsky
 *
 */
public class DumbPlayer implements IPlayer {

  public Card nextCard(Seat seat, Move move) {
    for (Card card : seat.getCards()) {
      if (move.isCardAcceptable(card, seat)) {
        return card;
      }
    }
    // if nothing was chosen
    return seat.getCards().get(0);
  }

  public final void tradeStep(Deal d, ITradeStepResult bc) {
    if (!(d instanceof PrefDeal)) {
      throw new IllegalStateException("deal must be PrefDeal");
    }
    if (!(bc instanceof PrefTradeStepResult)) {
      throw new IllegalStateException("bid container must be PrefBidContainer");
    }
    prefTradeStep((PrefDeal)d, (PrefTradeStepResult)bc);
  }

  private final static Random r = new Random();
  /**
   * just randomly - either pass or min available bid
   * @param d
   * @param bc
   */
  private void prefTradeStep(PrefDeal d, PrefTradeStepResult bc) {
    boolean doPass = r.nextBoolean();
    Bid bid;
    if (doPass) {
      bid = Bid.PASS;
    } else {
      bid = d.getContract().getMinAvailableBid();
    }
    bc.setBid(bid);
  }

}
