package com.dimmik.cards.table;

import java.util.ArrayList;
import java.util.List;

/**
 * abstract class representing the game
 * @author dkandrievsky
 *
 */
public abstract class Game {
  /**
   * deals in the game
   */
  private final List<Deal> deals = new ArrayList<Deal>();

  
  /**
   * game process
   * @throws GameException
   */
  public void process() throws GameException {
    while (gameMakesSense()) {
      Deal deal = newDeal();
      deal.process();
      dealPostProcess(deal);
      updateGameStatus(deal);
      deals.add(deal);
    }

  }


  /**
   * 
   * @return deals in the game
   */
  public List<Deal> getDeals() {
    return deals;
  }

  /**
   * actions to do after each deal
   * @param deal
   */
  protected abstract void dealPostProcess(Deal deal);

  /**
   * update status of the game - store score and so on
   * @param deal
   */
  protected abstract void updateGameStatus(Deal deal);

  /**
   * 
   * @return true if further game makes sense
   */
  protected abstract boolean gameMakesSense();

  /**
   * 
   * @return new instance of deal. Dependant on game process
   */
  protected abstract Deal newDeal();

}
