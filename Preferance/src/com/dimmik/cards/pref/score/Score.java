package com.dimmik.cards.pref.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dimmik.cards.pref.PrefDeal;
import com.dimmik.cards.pref.trade.Bid;
import com.dimmik.cards.pref.trade.Contract;
import com.dimmik.cards.prefplayers.DumbPlayer;
import com.dimmik.cards.sheets.card.Rank;
import com.dimmik.cards.table.Deal;
import com.dimmik.cards.table.DealException;
import com.dimmik.cards.table.IPlayer;
import com.dimmik.cards.table.Seat;

// TODO as for now - just max deals, no real score update.
// TODO may be makes sense to move seats into score
public class Score {
  private final Seat west;
  private final Seat north;
  private final Seat east;

  private final List<Deal> deals = new ArrayList<Deal>();

  private final int maxDeals;

  private static final IPlayer dumb = new DumbPlayer();

  private static final Map<Rank, Integer> gameValues = new HashMap<Rank, Integer>();
  static {
    gameValues.put(Rank.SIX, Integer.valueOf(2));
    gameValues.put(Rank.SEVEN, Integer.valueOf(4));
    gameValues.put(Rank.EIGHT, Integer.valueOf(6));
    gameValues.put(Rank.NINE, Integer.valueOf(8));
    gameValues.put(Rank.TEN, Integer.valueOf(10));
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

  public Score(int maxDeals, List<Seat> seats) {
    this(maxDeals, seats.get(0), seats.get(1), seats.get(2));
    if (seats == null || seats.size() != 3) {
      throw new IllegalArgumentException("there should be 3 seats");
    }
  }

  // Vists
  // Game scores (pulya)
  // Fine scores (gora)

  private final Map<Seat, ScoreSeq> fines = new HashMap<Seat, ScoreSeq>();
  private final Map<Seat, ScoreSeq> wins = new HashMap<Seat, ScoreSeq>();
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
    // TODO update scores in real valuable game
    Bid game = c.getGame();
    Rank gameRank = game.getRank();
    int gameValue = gameValues.get(gameRank).intValue();
  }

  private Map<Seat, ScoreSeq> copyScoreSeqMap(Map<Seat, ScoreSeq> source) {
    Map<Seat, ScoreSeq> dest = new HashMap<Seat, ScoreSeq>();
    for (Seat seat : source.keySet()) {
      dest.put(seat, new ScoreSeq(source.get(seat)));
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
        result.put(seat, new Float(sFineResult));
        result.put(vister, new Float(visterR));
      }
    }
  }

  private Map<Seat, Float> getFinesScore(Map<Seat, ScoreSeq> rFines,
      float average) {
    Map<Seat, Float> fineScores = new HashMap<Seat, Float>();
    for (Seat seat : getSeats()) {
      float vists = (average - getScoreSeq(seat, rFines).getValue()) * 10;
      fineScores.put(seat, new Float(vists));
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
    int tricks = getSeatTricksCnt(deal, winner);
    if (tricks == 0) {
      getScoreSeq(winner, wins).addValue(10);
    } else {
      getScoreSeq(winner, fines).addValue(10 * tricks);
    }
  }

  private void updateAllPass(PrefDeal deal) {
    // TODO implement fines amnisty
    // TODO deal with different pass strategies
    int allPassValue = 1;
    for (Seat s : getSeats()) {
      int tricks = getSeatTricksCnt(deal, s);
      if (tricks == 0) {
        getScoreSeq(s, wins).addValue(allPassValue);
      } else {
        getScoreSeq(s, fines).addValue(allPassValue * tricks);
      }
    }
  }

  private int getSeatTricksCnt(PrefDeal deal, Seat s) {
    return deal.getTricks().get(s).size();
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
}
