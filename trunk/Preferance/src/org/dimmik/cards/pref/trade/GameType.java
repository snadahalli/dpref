package org.dimmik.cards.pref.trade;

public enum GameType {
  /**
   * all-pass game
   */
  ALL_PASS(true, false),
  /**
   * miser game
   */
  MISER(true, false),
  /**
   * ranked game (such as 7 spades), visted (should be played)
   */
  RANKED_VISTED(true, true),
  /**
   * ranked, but not visted (two passes or pass-half-pass)
   */
  RANKED_NOT_VISTED(false, true);

  private boolean playable;
  private boolean ranked;

  private GameType(boolean playable, boolean ranked) {
    this.playable = playable;
    this.ranked = ranked;
  }

  public boolean isPlayable() {
    return playable;
  }

  public boolean isRanked() {
    return ranked;
  }

}
