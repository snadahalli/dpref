package com.dimmik.cards.pref.score;

import java.util.ArrayList;
import java.util.List;

import com.dimmik.cards.pref.PrefDeal;
import com.dimmik.cards.table.Deal;

// TODO as for now - just max deals.
public class Score {
  private final  List<Deal> deals = new ArrayList<Deal>();
  
  private final int maxDeals;
  
  public Score(int deals){
    maxDeals = deals;
  }
  
  // Vists
  // Game scores (pulya)
  // Fine scores (gora)



  public void update(PrefDeal deal) {
    deals.add(deal);
    // TODO update scores
  }


  public int getMaxDeals() {
    return maxDeals;
  }

  public boolean isGameFinished() {
    return deals.size() >= maxDeals;
  }
}
