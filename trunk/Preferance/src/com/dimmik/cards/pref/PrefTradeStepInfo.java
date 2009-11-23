package com.dimmik.cards.pref;

import com.dimmik.cards.pref.trade.Bid;
import com.dimmik.cards.sheets.card.Card;
import com.dimmik.cards.table.ITradeStepInfo;

public class PrefTradeStepInfo implements ITradeStepInfo {
  
  private Card firstThrown;
  private Card secondThrown;
  private Bid game;
  
  private final PrefTradeStep step;
  public PrefTradeStepInfo(PrefTradeStep st){
    step = st;
  }
  private Bid bid;

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
}
