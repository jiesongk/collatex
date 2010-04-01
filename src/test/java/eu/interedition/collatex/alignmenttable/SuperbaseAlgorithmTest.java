package eu.interedition.collatex.alignmenttable;

import org.junit.BeforeClass;

import eu.interedition.collatex.input.builders.WitnessBuilder;

// Note: this test are very similar to the alignment table 2 tests!
// Note: since the superbase algorithm class becomes more like a container, and does not contain any 
// Note: responsibility the tests should just move to there!
public class SuperbaseAlgorithmTest {
  private static WitnessBuilder builder;

  @BeforeClass
  public static void setUp() {
    builder = new WitnessBuilder();
  }

  // TODO: make the tostring on the alignmenttable
  // TODO: work with multiple spaces for an empty cell
  // TODO: fix the gap bug for the last gap

  // TODO: add this for replacements in comb. with transposit.
  //  // Note: this is with an unequal transposition sequence size!
  //  @Test
  //  public void testAdditionInCombinationWithTransposition() {
  //    Witness w1 = builder.build("A", "the cat is black");
  //    Witness w2 = builder.build("B", "black is the cat");
  //    Witness w3 = builder.build("C", "black and white is the cat");
  //    SuperbaseAlgorithm magic = new SuperbaseAlgorithm(w1, w2, w3);
  //    AlignmentTable2 table = magic.createAlignmentTable();
  //    String expected;
  //    expected = "A: the|cat| |is|black| |\n";
  //    expected += "B: black| | |is||the|cat\n";
  //    expected += "C: black|and|white|is|the|cat\n";
  //
  //    assertEquals(expected, table.toString());
  //  }

  // TODO: test a variant with multiple words
  // TODO: hint: more words than the original string it replaces

  // TODO: test a variant where one words turns out
  // todo: to be a match later
  // this is an addition.. not the easiest test
  //  @Test
  //  public void testTwoWitnesses() {
  //    Witness w1 = builder.build("A", "the black cat");
  //    Witness w2 = builder.build("B", "the white and black cat");
  //    MagicClass2 magic = new MagicClass2(w1, w2);
  //    AlignmentTable2 table = magic.createAlignmentTable();
  //    String expected = "A: the| | |black|cat";
  //    expected += "B: the|white|and|black|cat";
  //    System.out.println(table.toString());
  //  }

}
