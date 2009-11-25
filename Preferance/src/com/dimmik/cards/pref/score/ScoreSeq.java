package com.dimmik.cards.pref.score;

import java.util.LinkedList;
import java.util.List;

public class ScoreSeq {

  private final LinkedList<Integer> scores = new LinkedList<Integer>();

  public ScoreSeq() {

  }

  public ScoreSeq(ScoreSeq copy) {
    if (copy != null) {
      for (Integer s : copy.getScores()) {
        scores.add(s);
      }
    }
  }

  /**
   * adds valueToAdd to last element in scores and adds resulting value to
   * scores. For example, if we have scores [1,2,3] and add 4 resulting scores
   * will be [1,2,3,7]
   * 
   * @param valueToAdd
   */
  public void addValue(int valueToAdd) {
    if (valueToAdd != 0) {
      scores.add(new Integer(getValue() + valueToAdd));
    }
  }

  public int getValue() {
    if (scores.size() == 0) {
      return 0;
    } else {
      return scores.getLast().intValue();
    }
  }

  public List<Integer> getLastValues(int valuesCnt) {
    if (scores.size() < valuesCnt) {
      return scores;
    } else {
      List<Integer> sc = new LinkedList<Integer>();
      for (int i = scores.size() - valuesCnt; i < scores.size(); i++) {
        sc.add(scores.get(i));
      }
      return sc;
    }
  }

  public LinkedList<Integer> getScores() {
    return scores;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    for (Integer v : getLastValues(4)) {
      sb.append(" " + v);
    }
    return sb.toString();
  }

}
