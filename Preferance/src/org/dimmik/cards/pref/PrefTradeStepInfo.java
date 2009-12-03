package org.dimmik.cards.pref;

import java.util.ArrayList;
import java.util.List;

import org.dimmik.cards.pref.trade.Bid;
import org.dimmik.cards.sheets.card.Card;
import org.dimmik.cards.table.ITradeStepInfo;


public class PrefTradeStepInfo implements ITradeStepInfo {
  

  private PrefDeal.TwoCards thrown;
  private Bid bid;
  private Bid game;
  private Bid vist;
  
  private final List<Bid> availableBids = new ArrayList<Bid>();
  
  private final PrefTradeStep step;
  public PrefTradeStepInfo(PrefTradeStep st){
    step = st;
  }

  public void setBid(Bid bid) {
    this.bid = bid;
  }

  public Bid getBid() {
    return bid;
  }

  public PrefTradeStep getStep() {
    return step;
  }


  public Bid getGame() {
    return game;
  }


  public void setGame(Bid game) {
    this.game = game;
  }

  public Bid getVist() {
    return vist;
  }

  public void setVist(Bid vist) {
    this.vist = vist;
  }

  public void setAvailableVists(Bid ... bids) {
    availableBids.clear();
    for (Bid bid : bids){
      availableBids.add(bid);
    }
  }

  public List<Bid> getAvailableBids() {
    return availableBids;
  } 
  
  public boolean isVistStepOk(Bid bid){
    return availableBids.contains(bid);
  }

  public PrefDeal.TwoCards getThrown() {
    return thrown;
  }

  public void setThrown(PrefDeal.TwoCards thrown) {
    this.thrown = thrown;
  }
}
