package edu.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class SudokuBox extends SudokuStructure {
    public SudokuBox(List<SudokuField> fieldsConstructor) {
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
        SudokuBox rhs = (SudokuBox) obj;
        return new EqualsBuilder()
                .append(this.getFields(), rhs.getFields())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(29,49)
                .append(this.getFields())
                .toHashCode();
    }

    @Override
    public SudokuBox clone() {
        return (SudokuBox) super.clone();
    }
}
