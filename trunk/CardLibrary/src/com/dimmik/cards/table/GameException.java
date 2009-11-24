package com.dimmik.cards.table;

/**
 * something went wrong in game
 * @author dkandrievsky
 *
 */
public class GameException extends Exception {

  public GameException(String msg) {
    super(msg);
  }
  public GameException(){
    
  }

  /**
     * 
     */
  private static final long serialVersionUID = 8016676107371194335L;

}
