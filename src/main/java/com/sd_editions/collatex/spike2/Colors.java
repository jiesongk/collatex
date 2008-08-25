package com.sd_editions.collatex.spike2;

import java.util.List;

import com.google.common.collect.Lists;

public class Colors {

  private final Index index;
  private final List<WitnessIndex> witnessIndexes;

  public Colors(String... witnesses) {
    index = new Index(witnesses);
    this.witnessIndexes = Lists.newArrayList();
    for (String witness : witnesses) {
      WitnessIndex witnessIndex = new WitnessIndex(witness, index);
      witnessIndexes.add(witnessIndex);
    }
  }

  public int numberOfUniqueWords() {
    return index.numberOfEntries();
  }

  public WitnessIndex getWitnessIndex(int i) {
    return witnessIndexes.get(i - 1);
  }

  public Comparison compareWitness(int i, int j) {
    return new Comparison(getMatches(i, j));
  }

  public Matches getMatches(int i, int j) {
    return new Matches(getWitnessIndex(i), getWitnessIndex(j));
  }

  public TranspositionDetection detectTranspositions(int i, int j) {
    return new TranspositionDetection(getWitnessIndex(i), getWitnessIndex(j));
  }

  public int numberOfWitnesses() {
    return witnessIndexes.size();
  }
}
