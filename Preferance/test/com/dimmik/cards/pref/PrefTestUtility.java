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
        add(new Seat("West"));
        add(new Seat("North"));
        add(new Seat("East"));
      }
    };
    for (Seat s : seats) {
      s.setPlayer(new DumbPlayer());
    }
    return seats;
  }
}
