package com.dimmik.cards.table;

import java.util.ArrayList;
import java.util.List;

public abstract class Game {
  private final List<Deal> deals = new ArrayList<Deal>();
  private List<Seat> seats;

  public void process() throws GameException {
    while (gameMakesSense()) {
      Deal deal = newDeal();
      deal.process();
      dealPostProcess(deal);
      updateGameStatus(deal);
      deals.add(deal);
    }

  }

  protected List<Seat> getSeats() {
    return seats;
  }

  public List<Deal> getDeals() {
    return deals;
  }

  protected abstract void dealPostProcess(Deal deal);

  protected abstract void updateGameStatus(Deal deal);

  protected abstract boolean gameMakesSense();

  protected abstract Deal newDeal();

}
