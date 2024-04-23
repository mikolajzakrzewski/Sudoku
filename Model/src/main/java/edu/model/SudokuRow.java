package edu.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class SudokuRow extends SudokuStructure {
    public SudokuRow(List<SudokuField> fieldsConstructor) {
        super(fieldsConstructor);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        SudokuRow rhs = (SudokuRow) obj;
        return new EqualsBuilder()
                .append(this.getFields(), rhs.getFields())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(25,45)
                .append(this.getFields())
                .toHashCode();
    }

    @Override
    public SudokuRow clone() {
        return (SudokuRow) super.clone();
    }
}
