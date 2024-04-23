package edu.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class SudokuColumn extends SudokuStructure {
    public SudokuColumn(List<SudokuField> fieldsConstructor) {
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
        SudokuColumn rhs = (SudokuColumn) obj;
        return new EqualsBuilder()
                .append(this.getFields(), rhs.getFields())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(27,47)
                .append(this.getFields())
                .toHashCode();
    }

    @Override
    public SudokuColumn clone() {
        return (SudokuColumn) super.clone();
    }
}
