/*
 * Copyright (c) 2015 The Interedition Development Group.
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

package eu.interedition.collatex.lab;

/**
 * @author Ronald Haentjens Dekker
 */
public class MatchTableCell {

    private final MatchMatrixCellStatus status;
    private final String text;

    public MatchTableCell(MatchMatrixCellStatus status, String text) {
        this.status = status;
        this.text = text;
    }

    public MatchMatrixCellStatus getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }
}
