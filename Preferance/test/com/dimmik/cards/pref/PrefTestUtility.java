package com.dimmik.cards.pref;

import java.util.ArrayList;
import java.util.List;

import com.dimmik.cards.prefplayers.DumbPlayer;
import com.dimmik.cards.table.Seat;

public class PrefTestUtility {
  @SuppressWarnings("serial")
  public static List<Seat> getSeats() {
    List<Seat> seats = new ArrayList<Seat>() {
      {
        add(new Seat("West 50%pass"));
        add(new Seat("North 10%pass"));
        add(new Seat("East 90%pass"));
      }
    };
    int[] passProbs = new int[]{50, 10, 90};
   for (int i = 0; i < 3; i ++){
      seats.get(i).setPlayer(new DumbPlayer(passProbs[i]));
    }
    return seats;
  }
}
