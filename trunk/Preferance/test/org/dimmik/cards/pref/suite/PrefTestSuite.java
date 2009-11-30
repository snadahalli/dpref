package org.dimmik.cards.pref.suite;

import org.dimmik.cards.pref.AllPassGameTest;
import org.dimmik.cards.pref.ContractTest;
import org.dimmik.cards.pref.PrefDealTest;
import org.dimmik.cards.pref.PrefGameTest;
import org.dimmik.cards.pref.ScoresGameTest;
import org.dimmik.cards.pref.ScoresTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PrefTestSuite extends TestSuite {

  public static Test suite(){
    TestSuite suite = new TestSuite();
    suite.addTestSuite(ContractTest.class);
    suite.addTestSuite(PrefDealTest.class);
    suite.addTestSuite(PrefGameTest.class);
    suite.addTestSuite(AllPassGameTest.class);
    suite.addTestSuite(ScoresTest.class);
    suite.addTestSuite(ScoresGameTest.class);
    return suite;
  }
}
