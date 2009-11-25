package com.dimmik.cards.prefplayers;

import com.dimmik.cards.pref.PrefDeal;
import com.dimmik.cards.pref.trade.Bid;
import com.dimmik.cards.pref.trade.Contract;
import com.dimmik.cards.table.Seat;

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
