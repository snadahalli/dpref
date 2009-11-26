package org.dimmik.cards.prefplayers;

import org.dimmik.cards.pref.PrefDeal;
import org.dimmik.cards.pref.trade.Bid;
import org.dimmik.cards.pref.trade.Contract;
import org.dimmik.cards.table.Seat;


public class MiserDumbPlayer extends DumbPlayer {

  @Override
  protected Bid setBid(Seat seat, PrefDeal d) {
    Contract c = d.getContract();
    if (c.isBidCorrect(seat, Bid.MISER)){
      return Bid.MISER;
    } else {
      return Bid.PASS;
    }
  }

}
