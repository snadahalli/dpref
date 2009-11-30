package org.dimmik.cards.sheets.card;

import java.util.HashMap;
import java.util.Map;

public enum Rank {
  TWO("2", 2), THREE("3", 3), FOUR("4", 4), FIVE("5", 5), SIX("6", 6), SEVEN(
      "7", 7), EIGHT("8", 8), NINE("9", 9), TEN("10", 10), JACK("J", 11), QUEEN(
      "Q", 12), KING("K", 13), ACE("A", 14);

  private String name;
  private int value;

  
  private Rank(String r, int v) {
    name = r;
    value = v;
    StrToRank.strToRank.put(name, this);
  }

  public String getName() {
    return name;
  }

  public int getValue() {
    return value;
  }
  
  public String toString(){
    return name;
  }
  public static Rank fromString(String r){
    return StrToRank.strToRank.get(r);
  }
}
class StrToRank {
  public static Map<String, Rank> strToRank = new HashMap<String, Rank>();
}
