package org.dimmik.cards.pref.score;

import java.util.HashMap;
import java.util.Map;

import org.dimmik.cards.pref.trade.Bid;
import org.dimmik.cards.sheets.card.Card;
import org.dimmik.cards.sheets.card.Rank;

public class GameRules {
  private final Map<Rank, Integer> gameValues = new HashMap<Rank, Integer>();
  private final Map<Rank, Integer> visterRequiredTricks = new HashMap<Rank, Integer>();

  public GameRules() {
    gameValues.put(Rank.SIX, Integer.valueOf(2));
    gameValues.put(Rank.SEVEN, Integer.valueOf(4));
    gameValues.put(Rank.EIGHT, Integer.valueOf(6));
    gameValues.put(Rank.NINE, Integer.valueOf(8));
    gameValues.put(Rank.TEN, Integer.valueOf(10));

    visterRequiredTricks.put(Rank.SIX, Integer.valueOf(4));
    visterRequiredTricks.put(Rank.SEVEN, Integer.valueOf(2));
    visterRequiredTricks.put(Rank.EIGHT, Integer.valueOf(1));
    visterRequiredTricks.put(Rank.NINE, Integer.valueOf(1));
    visterRequiredTricks.put(Rank.TEN, Integer.valueOf(0));
  }

  public int getVistersRequiredTricks(Rank r) {
    return visterRequiredTricks.get(r).intValue();
  }

  public int getVistersRequiredTricks(Bid bid) {
    return getVistersRequiredTricks(bid.getRank());
  }
  

  public int getGameValue(Bid bid) {
    return getGameValue(bid.getRank());
  }

  public int getGameValue(Rank rank) {
    return gameValues.get(rank).intValue();
  }
  
  public int getGameTricksRequired(Bid game){
    return game.getRank().getValue();
  }
  // public int getHalfOfVistersRequiredTricks(Rank r){
  //    
  // }

  public int getEachVisterRequired(Bid game) {
    int eachRequired = 2;
    if (getVistersRequiredTricks(game) <4) {
      eachRequired = 1;
    }
    return eachRequired;
  }
}
