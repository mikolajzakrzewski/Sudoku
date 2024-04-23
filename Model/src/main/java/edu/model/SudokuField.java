package edu.model;

import edu.model.exceptions.CloneException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ResourceBundle;

public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {
    private int fieldValue;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    private static final Logger logger = LogManager.getLogger(SudokuField.class);

    public SudokuField() {

    }

   public SudokuField(int fieldValue) {
        this.fieldValue = fieldValue;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public int getFieldValue() {
        return this.fieldValue;
    }

    public void setFieldValue(int fieldValue) {
        int oldValue = this.fieldValue;
        this.fieldValue = fieldValue;
        support.firePropertyChange("value", oldValue, fieldValue);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("value", this.fieldValue)
                .toString();
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
        SudokuField rhs = (SudokuField) obj;
        return new EqualsBuilder()
                .append(this.fieldValue, rhs.fieldValue)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(21,41)
                .append(this.fieldValue)
                .toHashCode();
    }

    public SudokuField clone() {
        try {
            SudokuField clone = (SudokuField) super.clone();
            clone.support = new PropertyChangeSupport(clone);
            return clone;
        } catch (CloneNotSupportedException e) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle");
            logger.error(resourceBundle.getString("CloneException"));
            throw new CloneException(resourceBundle.getString("CloneException"), e);
        }
    }

    @Override
    public int compareTo(SudokuField o) {
        return Integer.compare(this.fieldValue, o.fieldValue);
    }
}
