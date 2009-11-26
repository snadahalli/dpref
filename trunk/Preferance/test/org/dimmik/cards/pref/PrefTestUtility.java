package org.dimmik.cards.pref;

import java.util.ArrayList;
import java.util.List;

import org.dimmik.cards.prefplayers.DumbPlayer;
import org.dimmik.cards.prefplayers.MiserDumbPlayer;
import org.dimmik.cards.table.IPlayer;
import org.dimmik.cards.table.Seat;


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
    int[] passProbs = new int[] { 50, 10, 90 };
    for (int i = 0; i < 3; i++) {
      seats.get(i).setPlayer(new DumbPlayer(passProbs[i]));
    }
    return seats;
  }

  @SuppressWarnings("serial")
  public static List<Seat> getAllPassSeats() {
    List<Seat> seats = new ArrayList<Seat>() {
      {
        add(new Seat("West pass"));
        add(new Seat("North pass"));
        add(new Seat("East pass"));
      }
    };
    int[] passProbs = new int[] { 100, 100, 100 };
    for (int i = 0; i < 3; i++) {
      seats.get(i).setPlayer(new DumbPlayer(passProbs[i]));
    }
    return seats;
  }

  @SuppressWarnings("serial")
  public static List<Seat> getNorthMiserSeats() {
    List<Seat> seats = new ArrayList<Seat>() {
      {
        add(new Seat("West pass"));
        add(new Seat("North miser"));
        add(new Seat("East pass"));
      }
    };
    int[] passProbs = new int[] { 100, 100, 100 };
    for (int i = 0; i < 3; i++) {
      seats.get(i).setPlayer(new DumbPlayer(passProbs[i]));
    }
    IPlayer miserer = new MiserDumbPlayer();
    seats.get(1).setPlayer(miserer);
    return seats;
  }

}
