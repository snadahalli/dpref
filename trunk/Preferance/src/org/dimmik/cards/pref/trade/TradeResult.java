package org.dimmik.cards.pref.trade;

public class TradeResult {
  public final GameType gameType;
  public final SeatGameStatus seatGameStatus;
  
  public TradeResult(GameType gameType, SeatGameStatus seatGameStatus) {
    this.gameType = gameType;
    this.seatGameStatus = seatGameStatus;
  }

  public GameType getGameType() {
    return gameType;
  }

  public SeatGameStatus getSeatGameStatus() {
    return seatGameStatus;
  }
  
  public boolean isGameRanked(){
    return gameType.isRanked();
  }

  
}
