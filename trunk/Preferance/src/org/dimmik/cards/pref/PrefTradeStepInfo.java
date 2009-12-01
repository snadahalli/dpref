package org.dimmik.cards.pref;

import java.util.ArrayList;
import java.util.List;

import org.dimmik.cards.pref.trade.Bid;
import org.dimmik.cards.sheets.card.Card;
import org.dimmik.cards.table.ITradeStepInfo;


public class PrefTradeStepInfo implements ITradeStepInfo {
  
  private Card firstThrown;
  private Card secondThrown;
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

  public Card getFirstThrown() {
    return firstThrown;
  }

  public Card getSecondThrown() {
    return secondThrown;
  }

  public Bid getGame() {
    return game;
  }

  public void setFirstThrown(Card firstThrown) {
    this.firstThrown = firstThrown;
  }

  public void setSecondThrown(Card secondThrown) {
    this.secondThrown = secondThrown;
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
}
