package org.dimmik.cards.pref.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dimmik.cards.pref.PrefDeal;
import org.dimmik.cards.pref.trade.Bid;
import org.dimmik.cards.pref.trade.Contract;
import org.dimmik.cards.prefplayers.DumbPlayer;
import org.dimmik.cards.sheets.card.Rank;
import org.dimmik.cards.table.Deal;
import org.dimmik.cards.table.DealException;
import org.dimmik.cards.table.IPlayer;
import org.dimmik.cards.table.Seat;

public class Score {
  private final Seat west;
  private final Seat north;
  private final Seat east;

  private final List<Deal> deals = new ArrayList<Deal>();

  private final int maxDeals;

  private static final IPlayer dumb = new DumbPlayer();

  private static final GameRules gr = new GameRules();

  public static boolean isHalfPossible(Bid game) {
    return (gr.getVistersRequiredTricks(game) > 1);
  }

  public static int getHalfTricsCnt(Bid game) {
    int half = 2;
    if (gr.getVistersRequiredTricks(game) < 4) {
      half = 1;
    }
    return half;

  }

  public static int getWinnerTricksRequired(Bid game) {
    return gr.getGameTricksRequired(game);
  }

  public Score(int deals) {
    this(deals, new Seat("West", dumb), new Seat("North", dumb), new Seat(
        "East", dumb));
  }

  public Score(int maxDeals, Seat west, Seat north, Seat east) {
    this.maxDeals = maxDeals;
    this.west = west;
    this.north = north;
    this.east = east;
    vists.put(west, new HashMap<Seat, ScoreSeq>());
    vists.put(north, new HashMap<Seat, ScoreSeq>());
    vists.put(east, new HashMap<Seat, ScoreSeq>());
  }

  // Vists
  // Game scores (pulya)
  // Fines scores (gora)

  /**
   * fines "gora" for seat
   */
  private final Map<Seat, ScoreSeq> fines = new HashMap<Seat, ScoreSeq>();
  /**
   * wins "pulya" for seats
   */
  private final Map<Seat, ScoreSeq> wins = new HashMap<Seat, ScoreSeq>();
  /**
   * vists for seats (how many vists has others to the seat denoted by key
   * entry)
   */
  private final Map<Seat, Map<Seat, ScoreSeq>> vists = new HashMap<Seat, Map<Seat, ScoreSeq>>();

  private ScoreSeq getScoreSeq(Seat seat, Map<Seat, ScoreSeq> scoreMap) {
    ScoreSeq s = scoreMap.get(seat);
    if (s == null) {
      s = new ScoreSeq();
      scoreMap.put(seat, s);
    }
    return s;
  }

  public void update(PrefDeal deal) throws DealException {
    deals.add(deal);
    if (deal.isAllPassGame()) {
      updateForAllPass(deal);
      return;
    }
    Contract c = deal.getContract();
    if (c.getGame() == Bid.MISER) {
      updateForMiser(deal);
      return;
    }
    updateForValuableGame(deal);
  }

  private void updateForValuableGame(PrefDeal deal) throws DealException {

    Map<Seat, Integer> sTricks = getScorableTricks(deal);
    // in sTricks - Seat -> tricks, to take into account
    // including extra tricks if game is not won

    for (Seat seat : getSeats()) {
      if (seat == deal.getContract().getWinnerSeat()) { // wins or fines
        updateWinnerScore(deal, sTricks);
      } else {
        updateVisterScore(deal, sTricks, seat);
      }
    }
    //
    // int wTricks = deal.getTricksCount(winner);
    // // check if winner performed the game well
    // // performer
    // updateWinnerScores(winner, game, wTricks);
    // // TODO deal with pass visters - no fines update, not vists if winner has
    // // won the game
    // // visters - vists
    // int allVistersTricks = updateVistersVists(deal, winner, game, wTricks);
    // // visters - fines
    // updateVistersFines(deal, winner, game, allVistersTricks);
  }

  private void updateVisterScore(PrefDeal deal, Map<Seat, Integer> sTricks,
      Seat seat) throws DealException {
    Contract c = deal.getContract();
    Bid game = c.getGame();
    Seat winner = c.getWinnerSeat();
    int trickCount = sTricks.get(winner);
    int tricksRequired = gr.getGameTricksRequired(game);
    boolean gameWon = trickCount >= tricksRequired;
    int gameValue = gr.getGameValue(game);
    int allVistersTricksCount = 10 - trickCount;
    // vists
    int tricks = sTricks.get(seat);
    getScoreSeq(seat, vists.get(winner)).addValue(tricks * gameValue);
    // fines
    if (gameWon) {
      if (c.getVisters().contains(seat)
          && allVistersTricksCount < gr.getVistersRequiredTricks(game)) {
        int tDiff = gr.getVistersRequiredTricks(game) - allVistersTricksCount;
        // seat is vister and it should be fined - not enough tricks
        int finesCnt;
        if (c.getVisters().size() == 2) { // if two visters - each has its own
                                          // fines
          int upToRequired = tricks >= gr.getEachVisterRequired(game) ? 0 : gr
              .getEachVisterRequired(game)
              - tricks;
          finesCnt = Math.min(tDiff, upToRequired);
        } else {
          finesCnt = tDiff;
        }
        getFines(seat).addValue(gameValue * finesCnt);
      }
    }
  }

  private void updateWinnerScore(PrefDeal deal, Map<Seat, Integer> sTricks)
      throws DealException {
    Contract c = deal.getContract();
    Bid game = c.getGame();
    Seat winner = c.getWinnerSeat();
    int trickCount = sTricks.get(winner);
    int tricksRequired = gr.getGameTricksRequired(game);
    boolean gameWon = trickCount >= tricksRequired;
    int gameValue = gr.getGameValue(game);
    if (gameWon) {
      getWins(winner).addValue(gameValue);
    } else {
      getFines(winner).addValue(gameValue * (tricksRequired - trickCount));
    }
  }

  private Map<Seat, Integer> getScorableTricks(PrefDeal deal)
      throws DealException {
    Contract c = deal.getContract();
    Bid game = c.getGame();
    Seat winner = c.getWinnerSeat();
    int gameTricksRequired = gr.getGameTricksRequired(game);
    int winnerTricks = deal.getTricksCount(winner);
    boolean gameWon = winnerTricks >= gameTricksRequired;
    int diff = 0;
    // if game is not won - add diff to non-player's tricks
    if (!gameWon) {
      diff = gameTricksRequired - winnerTricks;
    }

    // determine tricks that should be taken into account
    Map<Seat, Integer> sTricks = new HashMap<Seat, Integer>();
    sTricks.put(winner, deal.getTricksCount(winner));
    if (c.getVisters().isEmpty() || c.getVisters().size() == 2) {
      // all vist or no visters - as is
      for (Seat s : getSeats()) {
        if (s != winner) {
          sTricks.put(s, deal.getTricksCount(s) + diff);
        }
      }
    } else if (c.getVisters().size() == 1) {
      // if one vister - add passer's tricks to vister's
      Seat vister = c.getVisters().get(0);
      int passTricks = 0;
      for (Seat s : getSeats()) {
        if (s != winner && s != vister) {
          passTricks = deal.getTricksCount(s);
          sTricks.put(s, diff);
        }
      }
      int vistTricks = deal.getTricksCount(vister);
      sTricks.put(vister, vistTricks + passTricks + diff);
    }
    return sTricks;
  }


  private Map<Seat, ScoreSeq> copyScoreSeqMap(Map<Seat, ScoreSeq> source) {
    Map<Seat, ScoreSeq> dest = new HashMap<Seat, ScoreSeq>();
    for (Map.Entry<Seat, ScoreSeq> seatSc : source.entrySet()) {
      dest.put(seatSc.getKey(), new ScoreSeq(seatSc.getValue()));
    }
    return dest;
  }

  public float getResult(Seat s) {
    Map<Seat, Float> fineScores = getSeatResults();
    return fineScores.get(s).floatValue();
  }

  public Map<Seat, Float> getSeatResults() {
    Map<Seat, ScoreSeq> rFines = copyScoreSeqMap(fines);
    Map<Seat, ScoreSeq> rWins = copyScoreSeqMap(wins);
    // subtract wins from fines
    int minFine = subtractWinsFromFines(rFines, rWins);
    // normalize fines (min fine = 0)
    normalizeFines(rFines, minFine);
    // get fines average
    float average = calculateFinesAverage(rFines);
    // subtract fine from average
    Map<Seat, Float> result = getFinesScore(rFines, average);

    calculateVistBalance(result);

    return result;
  }

  private void calculateVistBalance(Map<Seat, Float> result) {
    for (Seat seat : vists.keySet()) {
      Map<Seat, ScoreSeq> vistsToThisSeat = vists.get(seat);
      for (Seat vister : vistsToThisSeat.keySet()) {
        int vists = getScoreSeq(vister, vistsToThisSeat).getValue();
        float sFineResult = result.get(seat).floatValue() - vists;
        float visterR = result.get(vister).floatValue() + vists;
        result.put(seat, Float.valueOf(sFineResult));
        result.put(vister, Float.valueOf(visterR));
      }
    }
  }

  private Map<Seat, Float> getFinesScore(Map<Seat, ScoreSeq> rFines,
      float average) {
    Map<Seat, Float> fineScores = new HashMap<Seat, Float>();
    for (Seat seat : getSeats()) {
      float vists = (average - getScoreSeq(seat, rFines).getValue()) * 10;
      fineScores.put(seat, Float.valueOf(vists));
    }
    return fineScores;
  }

  private float calculateFinesAverage(Map<Seat, ScoreSeq> rFines) {
    float average = 0;
    for (Seat seat : getSeats()) {
      average += getScoreSeq(seat, rFines).getValue();
    }
    average = average / getSeats().size();
    return average;
  }

  private void normalizeFines(Map<Seat, ScoreSeq> rFines, int minFine) {
    for (Seat seat : getSeats()) {
      getScoreSeq(seat, rFines).addValue(-minFine);
    }
  }

  private int subtractWinsFromFines(Map<Seat, ScoreSeq> rFines,
      Map<Seat, ScoreSeq> rWins) {
    int minFine = Integer.MAX_VALUE;
    for (Seat seat : getSeats()) {
      getScoreSeq(seat, rFines)
          .addValue(-(getScoreSeq(seat, rWins)).getValue());
      int fine = getScoreSeq(seat, rFines).getValue();
      if (fine < minFine) {
        minFine = fine;
      }
    }
    return minFine;
  }

  private void updateForMiser(PrefDeal deal) throws DealException {
    Contract c = deal.getContract();
    Seat winner = c.getWinnerSeat();
    int tricks = deal.getTricksCount(winner);
    if (tricks == 0) {
      getScoreSeq(winner, wins).addValue(10);
    } else {
      getScoreSeq(winner, fines).addValue(10 * tricks);
    }
  }

  private void updateForAllPass(PrefDeal deal) {
    // XXX deal with different pass strategies ??? What does it mean?!
    // Butthead, be more concrete!!!
    int allPassValue = 1;
    int minTricks = Integer.MAX_VALUE;
    for (Seat s : getSeats()) {
      int tricks = deal.getTricksCount(s);
      if (tricks < minTricks) {
        minTricks = tricks;
      }
    }
    for (Seat s : getSeats()) {
      int tricks = deal.getTricksCount(s);
      if (tricks == 0) {
        getScoreSeq(s, wins).addValue(allPassValue);
      } else {
        getScoreSeq(s, fines).addValue(allPassValue * (tricks - minTricks));
      }
    }
  }

  public ScoreSeq getFines(Seat seat) {
    return getScoreSeq(seat, fines);
  }

  public ScoreSeq getWins(Seat seat) {
    return getScoreSeq(seat, wins);
  }

  public int getMaxDeals() {
    return maxDeals;
  }

  public boolean isGameFinished() {
    return deals.size() >= maxDeals;
  }

  public List<Seat> getSeats() {
    List<Seat> seats = new ArrayList<Seat>();
    seats.add(west);
    seats.add(north);
    seats.add(east);
    return seats;
  }

  public Seat getWest() {
    return west;
  }

  public Seat getNorth() {
    return north;
  }

  public Seat getEast() {
    return east;
  }

  public List<Deal> getDeals() {
    return deals;
  }

  public Map<Seat, Map<Seat, ScoreSeq>> getVists() {
    return vists;
  }

}
