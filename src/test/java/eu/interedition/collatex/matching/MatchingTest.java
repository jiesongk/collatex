package eu.interedition.collatex.matching;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.interedition.collatex.alignment.Alignment;
import eu.interedition.collatex.alignment.UnfixedAlignment;
import eu.interedition.collatex.alignment.Match;
import eu.interedition.collatex.alignment.functions.Matcher;
import eu.interedition.collatex.collation.CollateCore;
import eu.interedition.collatex.input.Witness;
import eu.interedition.collatex.input.builders.WitnessBuilder;

public class MatchingTest {

  private WitnessBuilder builder;

  @Before
  public void setUp() {
    builder = new WitnessBuilder();
  }

  @Test
  public void testExactMatches() {
    Witness a = builder.build("zijn hond liep aan zijn hand");
    Witness b = builder.build("op zijn pad liep zijn hond aan zijn hand");
    UnfixedAlignment alignment = Matcher.align(a, b);
    UnfixedAlignment firstAlignment = alignment.getPrevious().getPrevious().getPrevious().getPrevious();
    Set<Match> exactMatches = firstAlignment.getFixedMatches();
    String expected = "[(3->4), (4->7)]";
    Assert.assertEquals(expected, exactMatches.toString());
  }

  @Test
  // TODO: assert near matches separate?
  public void testNearMatch() {
    Witness a = builder.build("a near match");
    Witness b = builder.build("a nar match");
    UnfixedAlignment matches = Matcher.align(a, b);
    Set<Match> fixedMatches = matches.getFixedMatches();
    Assert.assertEquals("[(1->1), (2->2), (3->3)]", fixedMatches.toString());
  }

  @Test
  public void testNoPermutationsOnlyExactMatches() {
    Witness a = builder.build("deze zinnen zijn hetzelfde");
    Witness b = builder.build("deze zinnen zijn hetzelfde met een aanvulling");
    Alignment collation = CollateCore.collate(a, b);
    Set<Match> matches = collation.getMatches();
    String expected = "[(1->1), (2->2), (3->3), (4->4)]";
    Assert.assertEquals(expected, matches.toString());
  }

  @Test
  public void testSelectBestMatchFromPossibleMatches() {
    Witness a = builder.build("zijn hond liep aan zijn hand");
    Witness b = builder.build("op zijn pad liep zijn hond aan zijn hand");
    Alignment collation = CollateCore.collate(a, b);
    Set<Match> matches = collation.getMatches();
    String expected = "[(3->4), (4->7), (2->6), (6->9), (1->5), (5->8)]";
    Assert.assertEquals(expected, matches.toString());
    Assert.assertEquals(1, collation.getGaps().size());
    Assert.assertEquals(3, collation.getMatchSequences().size());
  }

  @Test
  public void testTreeTimesZijnAlsoWorks() {
    Witness a = builder.build("zijn hond liep aan zijn hand op zijn dag");
    Witness b = builder.build("op zijn pad liep zijn hond aan zijn hand op zijn dag");
    Alignment collation = CollateCore.collate(a, b);
    Set<Match> matches = collation.getMatches();
    String expected = "[(3->4), (4->7), (9->12), (2->6), (6->9), (1->5), (5->8), (7->10), (8->11)]";
    Assert.assertEquals(expected, matches.toString());
    Assert.assertEquals(1, collation.getGaps().size());
    Assert.assertEquals(3, collation.getMatchSequences().size());
  }

  @Test
  public void testMatchingFromBtoA() {
    Witness a = builder.build("op zijn pad liep zijn hond aan zijn hand");
    Witness b = builder.build("zijn hond liep aan zijn hand");
    Alignment collation = CollateCore.collate(a, b);
    Set<Match> matches = collation.getMatches();
    String expected = "[(4->3), (7->4), (6->2), (9->6), (5->1), (8->5)]";
    Assert.assertEquals(expected, matches.toString());
    Assert.assertEquals(1, collation.getGaps().size());
    Assert.assertEquals(3, collation.getMatchSequences().size());
  }

  // Note: in test there are no exact matches!
  // so there are multiple alignments that
  // are both equally valid!
  @Test
  public void testMatchingFromAtoBandBtoAMixed() {
    Witness a = builder.build("a a b");
    Witness b = builder.build("a b b");
    Alignment collation = CollateCore.collate(a, b);
    Set<Match> matches = collation.getMatches();
    String expected = "[(1->1), (3->3)]";
    Assert.assertEquals(expected, matches.toString());
  }

  @Test
  public void testMatchingFromAtoBandBtoAMixedCFixed() {
    Witness a = builder.build("a a c b");
    Witness b = builder.build("a b c b");
    Alignment collation = CollateCore.collate(a, b);
    Set<Match> matches = collation.getMatches();
    String expected = "[(3->3), (1->1), (4->4)]";
    Assert.assertEquals(expected, matches.toString());
  }

  @Test
  public void testAlignmentTreadExactMatchesAndNearMatchesEqually() {
    Witness a = builder.build("I bought this glass, because it matches those dinner plates.");
    Witness b = builder.build("I bought those glasses.");
    CollateCore collateCore = new CollateCore();
    Alignment collation = collateCore.doCompareWitnesses(a, b);
    Assert.assertEquals("[(1->1), (2->2), (3->3), (4->4)]", collation.getMatches().toString());
  }

}
