package org.dimmik.cards.prefplayers;

import java.util.List;

import org.dimmik.cards.pref.PrefDeal;
import org.dimmik.cards.pref.PrefTradeStepInfo;
import org.dimmik.cards.pref.PrefDeal.TwoCards;
import org.dimmik.cards.pref.trade.Bid;
import org.dimmik.cards.sheets.card.Card;
import org.dimmik.cards.table.Deal;
import org.dimmik.cards.table.IPlayer;
import org.dimmik.cards.table.ITradeStepInfo;
import org.dimmik.cards.table.Move;
import org.dimmik.cards.table.Seat;

public abstract class AbstractPrefPlayer implements IPlayer {

  public final Card nextCard(Seat seat, Deal d, Move move) {
    return nextCard(seat, (PrefDeal) d, move);
  }

  public abstract Card nextCard(Seat seat, PrefDeal d, Move move);

  public void tradeStep(Seat seat, Deal d, ITradeStepInfo bc) {
    if (!(d instanceof PrefDeal)) {
      throw new IllegalStateException("deal must be PrefDeal");
    }
    if (!(bc instanceof PrefTradeStepInfo)) {
      throw new IllegalStateException("bid container must be PrefBidContainer");
    }
    prefTradeStep(seat, (PrefDeal) d, (PrefTradeStepInfo) bc);
  }

  private void prefTradeStep(Seat seat, PrefDeal d, PrefTradeStepInfo bc) {
    switch (bc.getStep()) {
    case SET_BID:
      Bid bid = setBid(seat, d);
      bc.setBid(bid);
      break;
    case GIVE_SIDE_CARDS:
      giveSideCards(seat, d, bc);
      break;
    case GET_THROWN_CARDS:
      PrefDeal.TwoCards thrown = getThrownCards(seat, d);
      bc.setFirstThrown(thrown.getFirst());
      bc.setSecondThrown(thrown.getSecond());
      break;
    case SET_GAME:
      Bid game = setGame(seat, d);
      bc.setGame(game);
      break;
    case SET_VIST:
      Bid vist = setVist(seat, d, bc.getAvailableBids());
      bc.setVist(vist);
      break;
    default:
      throw new IllegalStateException("unknownm trade step");
    }

  }

  protected abstract Bid setVist(Seat seat, PrefDeal d, List<Bid> availableBids);

  protected abstract Bid setGame(Seat seat, PrefDeal d);

  protected void giveSideCards(Seat seat, PrefDeal d, PrefTradeStepInfo bc) {
  }

  protected abstract TwoCards getThrownCards(Seat seat, PrefDeal d);

  protected abstract Bid setBid(Seat seat, PrefDeal d);

}
