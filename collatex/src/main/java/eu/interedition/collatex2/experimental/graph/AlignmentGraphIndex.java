package eu.interedition.collatex2.experimental.graph;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import eu.interedition.collatex2.interfaces.INormalizedToken;

public class AlignmentGraphIndex implements IAlignmentGraphIndex {

  private Multimap<String, INormalizedToken> normalizedToTokens;
  private LinkedHashMap<INormalizedToken, IAlignmentNode> tokenToNode;

  public static IAlignmentGraphIndex create(IAlignmentGraph graph, List<String> findRepeatingTokens) {
    final AlignmentGraphIndex index = new AlignmentGraphIndex();
    //TODO: change this for three or more witnesses!
    for (IAlignmentNode node : graph.getNodes().subList(1, graph.getNodes().size())) {
      makeTokenUniqueIfNeeded(index, findRepeatingTokens, node);
    }
    //    for (final String sigil : table.getSigli()) {
//      findUniquePhrasesForRow(sigil, table, index, repeatingTokens);
//    }
    return index;
  }

  @Override
  public boolean containsNormalizedPhrase(String normalized) {
    return normalizedToTokens.containsKey(normalized);
  }

  @Override
  public IAlignmentNode getAlignmentNode(INormalizedToken token) {
    return tokenToNode.get(token);
  }

  @Override
  public Collection<INormalizedToken> getTokens(String normalized) {
    return normalizedToTokens.get(normalized);
  }

  private AlignmentGraphIndex() {
    normalizedToTokens = Multimaps.newArrayListMultimap();
    tokenToNode = Maps.newLinkedHashMap();
  }

//  private static void findUniquePhrasesForRow(final String row, final IAlignmentTable table, final AlignmentTableIndex index, final List<String> findRepeatingTokens) {
//    // filteren would be nicer.. maar we doen het maar even alles in een!
//    for (final IColumn column : table.getColumns()) {
//      if (column.containsWitness(row)) {
//        makeTokenUnique(row, table, index, findRepeatingTokens, column);
//      } else {
//        logger.debug("Column " + column.getPosition() + " is empty!");
//      }
//    }
//  }

  

  private static void makeTokenUniqueIfNeeded(final AlignmentGraphIndex index, final List<String> findRepeatingTokens,
      final IAlignmentNode node) {
    INormalizedToken token = node.getToken();
    // kijken of ie unique is
    final boolean unique = !findRepeatingTokens.contains(token.getNormalized());
    if (unique) {
      List<IAlignmentNode> nodes = Lists.newArrayList(node);
      index.add(nodes);
    } 
//    else {
//      //System.out.println("We have to combine stuff here!");
//      final ColumnPhrase leftPhrase = findUniqueColumnPhraseToTheLeft(table, findRepeatingTokens, row, column, token);
//      final ColumnPhrase rightPhrase = findUniqueColumnPhraseToTheRight(table, findRepeatingTokens, row, column, token);
//      index.add(leftPhrase);
//      index.add(rightPhrase);
//    }
  }

  private void add(List<IAlignmentNode> nodes) {
    String normalized = "";
    String splitter = "";
    for (IAlignmentNode node : nodes) {
      normalized += splitter+node.getNormalized();
      splitter = " ";
    }
    for (IAlignmentNode node : nodes) {
      normalizedToTokens.put(normalized, node.getToken());
      tokenToNode.put(node.getToken(), node);
    }
  }

  @Override
  public String toString() {
    String result = "AlignmentGraphIndex: (";
    String delimiter = "";
    for (final String normalizedPhrase : normalizedToTokens.keySet()) {
      result += delimiter + normalizedPhrase;
      delimiter = ", ";
    }
    result += ")";
    return result;
  }

}
