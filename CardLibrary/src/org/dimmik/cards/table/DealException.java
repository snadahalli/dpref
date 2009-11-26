package org.dimmik.cards.table;

/**
 * something went wrong during deal
 * @author dkandrievsky
 *
 */
public class DealException extends GameException {

  /**
     * 
     */
  private static final long serialVersionUID = 6230412591341094867L;

  public DealException(){
    
  }
  public DealException(String msg) {
    super(msg);
  }
  
}
