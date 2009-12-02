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

// TODO as for now - just max deals, no real score update.
// TODO may be makes sense to move seats into score
public class Score {
  private final Seat west;
  private final Seat north;
  private final Seat east;

  private final List<Deal> deals = new ArrayList<Deal>();

  private final int maxDeals;

  private static final IPlayer dumb = new DumbPlayer();

  private static final GameRules gr = new GameRules();

  public static boolean isHalfPossible(Bid game) {
    Rank r = game.getRank();
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
    Rank r = game.getRank();
    return r.getValue();
  }

  public Score(int deals) {
    this(deals, new Seat("West", dumb), new Seat("North, dumb"), new Seat(
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
   * vists for seats (how many vists has others to this)
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
      updateAllPass(deal);
      return;
    }
    Contract c = deal.getContract();
    if (c.getGame() == Bid.MISER) {
      updateMiser(deal, c);
      return;
    }
    updateValuableGame(deal, c);
  }

  // TODO now only all-vist game is implemented
  private void updateValuableGame(PrefDeal deal, Contract c)
      throws DealException {
    // TODO update scores in real valuable game
    Bid game = c.getGame();
    Rank gameRank = game.getRank();
    Seat winner = c.getWinnerSeat();
    int gameValue = gr.getGameValue(game);
    int trickCount = deal.getTricksCount(winner);
    int tricksRequired = gameRank.getValue();
    boolean gameWon = (trickCount >= tricksRequired);
    // check if winner performed the game well
    // performer
    updateWinnerScores(winner, game, trickCount);
    // visters - vists
    int allVistersTricks = updateVistersVists(deal, winner, game, trickCount);
    // visters - fines
    updateVistersFines(deal, winner, game, allVistersTricks);
  }

  private void updateVistersFines(PrefDeal deal, Seat winner, Bid game,
      int allVistersTricks) {
    // TODO - think about complicated rules. Such as "non-gentlemen vist",
    // "only last vist gets fines" and so on
    int gameValue = gr.getGameValue(game);
    int visterTricksRequired = gr.getVistersRequiredTricks(game);
    int upToRequired;
    if (allVistersTricks < visterTricksRequired) {
      upToRequired = visterTricksRequired - allVistersTricks;
      int eachRequired = 2;
      if (visterTricksRequired < 4) {
        eachRequired = 1;
      }
      for (Seat seat : getSeats()) {
        if (seat != winner) {
          int tricks = deal.getTricksCount(seat);
          if (tricks < eachRequired) {
            getFines(seat).addValue(
                gameValue * Math.min((eachRequired - tricks), upToRequired));
          }
        }
      }
    }
  }

  private int updateVistersVists(PrefDeal deal, Seat winner, Bid game,
      int trickCount) {
    int tricksRequired = gr.getGameTricksRequired(game);
    int gameValue = gr.getGameValue(game);
    boolean gameWon = (trickCount > tricksRequired);

    int allVistersTricks = 0;
    for (Seat seat : getSeats()) {
      if (seat != winner) {
        int tricks = deal.getTricksCount(seat);
        allVistersTricks += tricks;
        if (!gameWon) {
          // if game is not won - add appropriate tricks to every vister
          tricks += (tricksRequired - trickCount);
        }
        getScoreSeq(seat, vists.get(winner)).addValue(tricks * gameValue);
      }
    }
    return allVistersTricks;
  }

  private void updateWinnerScores(Seat winner, Bid game, int trickCount) {
    int gameValue = gr.getGameValue(game);
    int tricksRequired = gr.getGameTricksRequired(game);
    boolean gameWon = trickCount >= tricksRequired;
    if (gameWon) {
      getWins(winner).addValue(gameValue);
    } else {
      getFines(winner).addValue(gameValue * (tricksRequired - trickCount));
    }
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
    // TODO deal with vists
    // TODO may be it'd be better to work with Map<Seat, Integer>
    // instead of Map<Seat, ScoreSeq>
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

  private void updateMiser(PrefDeal deal, Contract c) throws DealException {
    Seat winner = c.getWinnerSeat();
    int tricks = deal.getTricksCount(winner);
    if (tricks == 0) {
      getScoreSeq(winner, wins).addValue(10);
    } else {
      getScoreSeq(winner, fines).addValue(10 * tricks);
    }
  }

  private void updateAllPass(PrefDeal deal) {
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
