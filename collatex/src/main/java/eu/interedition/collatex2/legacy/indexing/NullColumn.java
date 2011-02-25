package eu.interedition.collatex2.legacy.indexing;

import java.util.List;

import eu.interedition.collatex2.implementation.input.NullToken;
import eu.interedition.collatex2.interfaces.ColumnState;
import eu.interedition.collatex2.interfaces.IInternalColumn;
import eu.interedition.collatex2.interfaces.INormalizedToken;
import eu.interedition.collatex2.interfaces.IWitness;

public class NullColumn implements IInternalColumn {

  public NullColumn() {
  }

  @Override
  public boolean containsWitness(final IWitness witness) {
    return false;
  }

  @Override
  public INormalizedToken getToken(final IWitness witness) {
    return new NullToken();
  }

  @Override
  public ColumnState getState() {
    return null;
  }

   @Override
  public List<IWitness> getWitnesses() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String toString() {
    return "";
  }

  @Override
  public boolean isMatch(IWitness base, IWitness sigil) {
    throw new RuntimeException("DO NOT CALL THIS METHOD!");
  }

}
