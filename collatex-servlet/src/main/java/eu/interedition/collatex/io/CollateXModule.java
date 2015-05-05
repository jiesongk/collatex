/*
 * Copyright (c) 2013 The Interedition Development Group.
 *
 * This file is part of CollateX.
 *
 * CollateX is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CollateX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CollateX.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.interedition.collatex.io;

import eu.interedition.collatex.VariantGraph;
import eu.interedition.collatex.simple.SimpleCollation;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;

/**
* @author <a href="http://gregor.middell.net/" title="Homepage">Gregor Middell</a>
*/
public class CollateXModule extends SimpleModule {

  public CollateXModule() {
    super(CollateXModule.class.getPackage().getName(), Version.unknownVersion());
    addDeserializer(SimpleCollation.class, new SimpleCollationDeserializer());
  }
}
