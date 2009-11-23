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
        add(new Seat("North 30%pass"));
        add(new Seat("East 70%pass"));
      }
    };
    int[] passProbs = new int[]{50, 30, 70};
   for (int i = 0; i < 3; i ++){
      seats.get(i).setPlayer(new DumbPlayer(passProbs[i]));
    }
    return seats;
  }
}
