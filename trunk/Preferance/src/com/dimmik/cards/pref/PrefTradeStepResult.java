package com.dimmik.cards.pref;

import com.dimmik.cards.pref.trade.Bid;
import com.dimmik.cards.table.ITradeStepResult;

public class PrefTradeStepResult implements ITradeStepResult {
  private Bid bid;

  public void setBid(Bid bid) {
    this.bid = bid;
  }

  public Bid getBid() {
    return bid;
  } 
}
