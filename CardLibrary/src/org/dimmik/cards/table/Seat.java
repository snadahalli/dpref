package org.dimmik.cards.table;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.dimmik.cards.sheets.card.Card;

public class Seat implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -1362539417438893576L;
  private final String name;
  private transient IPlayer player;

  private final List<Card> cards = new LinkedList<Card>();

  public Seat(String name) {
    this.name = name;
  }

  public Seat(String name, IPlayer player) {
    this(name);
    setPlayer(player);
  }

  public void addCard(Card c) {
    getCards().add(c);
  }

  public List<Card> getCards() {
    return cards;
  }

  public void setPlayer(IPlayer player) {
    this.player = player;
  }

  public IPlayer getPlayer() {
    return player;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }

  // -------------------
  // delegate to player
  // -------------------
  public Card nextCard(Deal d, Move move) {
    Card card = player.nextCard(this, d, move);
    cards.remove(card);
    return card;
  }

  public void tradeStep(Deal d, ITradeStepInfo stInfo) {
    player.tradeStep(this, d, stInfo);
  }

}
