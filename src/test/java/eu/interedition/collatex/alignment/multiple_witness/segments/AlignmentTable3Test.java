package eu.interedition.collatex.alignment.multiple_witness.segments;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.interedition.collatex.input.Segment;
import eu.interedition.collatex.input.WitnessSet;
import eu.interedition.collatex.input.builders.WitnessBuilder;

// Note: this test tests the new alignment table based on <Witness, SegmentColumn>
public class AlignmentTable3Test {
  private static WitnessBuilder builder;

  @BeforeClass
  public static void setUp() {
    builder = new WitnessBuilder();
  }

  @Test
  public void testStringOutputOneWitness() {
    Segment w1 = builder.build("A", "the black cat");
    AlignmentTable3 table = new AlignmentTable3();
    table.addWitness(w1);
    String expected = "A: the black cat\n";
    assertEquals(expected, table.toString());
  }

  @Test
  public void testEverythingMatches() {
    Segment w1 = builder.build("A", "the black cat");
    Segment w2 = builder.build("B", "the black cat");
    Segment w3 = builder.build("C", "the black cat");
    WitnessSet set = new WitnessSet(w1, w2, w3);
    AlignmentTable3 table = AlignmentTable3.create(set);
    String expected = "A: the black cat\n";
    expected += "B: the black cat\n";
    expected += "C: the black cat\n";
    assertEquals(expected, table.toString());
  }

  //  @Test
  //  public void testOmission() {
  //    Witness w1 = builder.build("A", "the black cat");
  //    Witness w2 = builder.build("B", "the cat");
  //    Witness w3 = builder.build("C", "the black cat");
  //    WitnessSet magic = new WitnessSet(w1, w2, w3);
  //    AlignmentTable2 table = magic.createAlignmentTable();
  //    String expected = "A: the|black|cat\n";
  //    expected += "B: the| |cat\n";
  //    expected += "C: the|black|cat\n";
  //    assertEquals(expected, table.toString());
  //  }
  //
  //  @Test
  //  public void testVariant() {
  //    Witness w1 = builder.build("A", "the black cat");
  //    Witness w2 = builder.build("B", "the white cat");
  //    Witness w3 = builder.build("C", "the green cat");
  //    Witness w4 = builder.build("D", "the red cat");
  //    Witness w5 = builder.build("E", "the yellow cat");
  //    WitnessSet magic = new WitnessSet(w1, w2, w3, w4, w5);
  //    AlignmentTable2 table = magic.createAlignmentTable();
  //    String expected = "A: the|black|cat\n";
  //    expected += "B: the|white|cat\n";
  //    expected += "C: the|green|cat\n";
  //    expected += "D: the|red|cat\n";
  //    expected += "E: the|yellow|cat\n";
  //    assertEquals(expected, table.toString());
  //  }
  //
  //  @Test
  //  public void testAddition() {
  //    Witness w1 = builder.build("A", "the cat");
  //    Witness w2 = builder.build("B", "before the cat");
  //    Witness w3 = builder.build("C", "the black cat");
  //    Witness w4 = builder.build("D", "the cat walks");
  //    WitnessSet magic = new WitnessSet(w1, w2, w3, w4);
  //    AlignmentTable2 table = magic.createAlignmentTable();
  //    String expected = "A:  |the| |cat| \n";
  //    expected += "B: before|the| |cat| \n";
  //    expected += "C:  |the|black|cat| \n";
  //    expected += "D:  |the| |cat|walks\n";
  //
  //    assertEquals(expected, table.toString());
  //  }
  //
  //  // TODO: make the tostring on the alignmenttable
  //  // TODO: work with multiple spaces for an empty cell
  //  // TODO: fix the gap bug for the last gap
  //
  //  @Test
  //  public void testGenSuperbase() {
  //    Witness w1 = builder.build("A", "the cat");
  //    Witness w2 = builder.build("B", "before the cat");
  //    Witness w3 = builder.build("C", "the black cat");
  //    Witness w4 = builder.build("D", "just before midnight the cat walks");
  //    WitnessSet magic = new WitnessSet(w1, w2, w3, w4);
  //    AlignmentTable2 table = magic.createAlignmentTable();
  //    String expected = "A:  | | |the| |cat| \n";
  //    expected += "B:  |before| |the| |cat| \n";
  //    expected += "C:  | | |the|black|cat| \n";
  //    expected += "D: just|before|midnight|the| |cat|walks\n";
  //
  //    assertEquals(expected, table.toString());
  //  }
  //
  //  @Test
  //  public void testVariation() {
  //    Witness w1 = builder.build("A", "the black cat");
  //    Witness w2 = builder.build("B", "the black and white cat");
  //    Witness w3 = builder.build("C", "the black very special cat");
  //    Witness w4 = builder.build("D", "the black not very special cat");
  //    WitnessSet magic = new WitnessSet(w1, w2, w3, w4);
  //    AlignmentTable2 table = magic.createAlignmentTable();
  //    String expected = "A: the|black| | | |cat\n";
  //    expected += "B: the|black| |and|white|cat\n";
  //    expected += "C: the|black| |very|special|cat\n";
  //    expected += "D: the|black|not|very|special|cat\n";
  //
  //    assertEquals(expected, table.toString());
  //  }
  //
  //  @Test
  //  public void testWitnessReorder() {
  //    Witness w1 = builder.build("A", "the black cat");
  //    Witness w2 = builder.build("B", "the black and white cat");
  //    Witness w3 = builder.build("C", "the black not very special cat");
  //    Witness w4 = builder.build("D", "the black very special cat");
  //    WitnessSet magic = new WitnessSet(w1, w2, w3, w4);
  //    AlignmentTable2 table = magic.createAlignmentTable();
  //    String expected = "A: the|black| | | |cat\n";
  //    expected += "B: the|black|and|white| |cat\n";
  //    expected += "C: the|black|not|very|special|cat\n";
  //    expected += "D: the|black| |very|special|cat\n";
  //
  //    assertEquals(expected, table.toString());
  //  }

  //
  //  @Test
  //  public void testCreateSuperBaseWithVariation() {
  //    WitnessBuilder builder = new WitnessBuilder();
  //    Witness a = builder.build("A", "the first witness");
  //    Witness b = builder.build("B", "the second witness");
  //    AlignmentTable2 alignmentTable = new AlignmentTable2();
  //    alignmentTable.addWitness(a);
  //    alignmentTable.addWitness(b);
  //    Witness superbase = alignmentTable.createSuperbase();
  //    assertEquals("the first second witness", superbase.toString());
  //  }
  //
  //
  //  @Test
  //  public void testStringOutputTwoWitnesses() {
  //    WitnessBuilder builder = new WitnessBuilder();
  //    Witness w1 = builder.build("A", "the black cat");
  //    Witness w2 = builder.build("B", "the black cat");
  //    AlignmentTable2 table = new AlignmentTable2();
  //    table.addWitness(w1);
  //    table.addWitness(w2);
  //    // TODO: add match test can be moved to a column test class? 
  //    //    // TODO: word contains id also, which refers to Witness
  //    //    Column c1 = table.getColumns().get(0);
  //    //    Column c2 = table.getColumns().get(1);
  //    //    Column c3 = table.getColumns().get(2);
  //    //    table.addMatch(w2, w2.getWordOnPosition(1), c1);
  //    //    table.addMatch(w2, w2.getWordOnPosition(2), c2);
  //    //    table.addMatch(w2, w2.getWordOnPosition(3), c3);
  //    String expected = "A: the|black|cat\n";
  //    expected += "B: the|black|cat\n";
  //    assertEquals(expected, table.toString());
  //  }
  //
  //  @Test
  //  public void testStringOutputEmptyCells() {
  //    WitnessBuilder builder = new WitnessBuilder();
  //    Witness w1 = builder.build("A", "the black cat");
  //    Witness w2 = builder.build("B", "the");
  //    AlignmentTable2 table = new AlignmentTable2();
  //    table.addWitness(w1);
  //    table.addWitness(w2);
  //    // TODO: add match test can be moved to column class?
  //    //    Column column = table.getColumns().get(0);
  //    // TODO: word contains id also, which refers to Witness
  //    //table.addMatch(w2, w2.getWordOnPosition(1), column);
  //    String expected = "A: the|black|cat\n";
  //    expected += "B: the| | \n";
  //    assertEquals(expected, table.toString());
  //  }
  //
  //  @Test
  //  public void testTranspositionsAreNotStoredInAlignmentTable() {
  //    WitnessBuilder builder = new WitnessBuilder();
  //    Witness w1 = builder.build("A", "the black and white cat");
  //    Witness w2 = builder.build("B", "the white and black cat");
  //    AlignmentTable2 table = new AlignmentTable2();
  //    table.addWitness(w1);
  //    table.addWitness(w2);
  //    String expected = "A: the|black|and|white|cat\n";
  //    expected += "B: the|black|and|white|cat\n";
  //    assertEquals(expected, table.toString());
  //  }
}
