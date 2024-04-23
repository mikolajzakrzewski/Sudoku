package edu.model;

import edu.model.exceptions.CloneException;
import edu.model.exceptions.SudokuStructureSizeException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public abstract class SudokuStructure implements Cloneable {

    private List<SudokuField> fields;

    private static final Logger logger = LogManager.getLogger(SudokuStructure.class);

    public SudokuStructure(List<SudokuField> fieldsConstructor) {
        if (fieldsConstructor == null || fieldsConstructor.size() != 9) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle");
            logger.error(resourceBundle.getString("SudokuStructureConstructor"));
            throw new SudokuStructureSizeException(resourceBundle.getString("SudokuStructureConstructor"));
        }
        this.fields = fieldsConstructor;
    }

    public boolean verify() {
        ArrayList<Integer> fieldValues = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            if (this.fields.get(i).getFieldValue() != 0) {
                if (fieldValues.contains(this.fields.get(i).getFieldValue())) {
                    return false;
                } else if (this.fields.get(i).getFieldValue() < 0) {
                    return false;
                } else if (this.fields.get(i).getFieldValue() > fields.size()) {
                    return false;
                } else {
                    fieldValues.add(this.fields.get(i).getFieldValue());
                }
            }
        }
        return true;
    }

    public List<SudokuField> getFields() {
        List<SudokuField> fields = Arrays.asList(new SudokuField[this.fields.size()]);
        for (int i = 0; i < this.fields.size(); i++) {
            fields.set(i, new SudokuField(this.fields.get(i).getFieldValue()));
        }
        return fields;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("fields", this.fields)
                .toString();
    }

    @Override
    public SudokuStructure clone() {
        try {
            SudokuStructure sudokuStructureClone = (SudokuStructure) super.clone();
            List<SudokuField> list = Arrays.asList(new SudokuField[9]);
            for (int i = 0; i < list.size(); i++) {
                list.set(i, getFields().get(i).clone());
            }
            sudokuStructureClone.fields = list;
            return sudokuStructureClone;
        } catch (CloneNotSupportedException e) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle");
            logger.error(resourceBundle.getString("CloneException"));
            throw new CloneException(resourceBundle.getString("CloneException"), e);
        }
    }
}
