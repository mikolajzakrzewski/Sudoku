package edu.model;

public enum Difficulty {
    EASY(10),
    MEDIUM(20),
    HARD(30);

    public final int numberOfCellsToRemove;

    Difficulty(int numberOfCellsToRemove) {
        this.numberOfCellsToRemove = numberOfCellsToRemove;
    }

    public int getNumberOfCellsToRemove() {
        return numberOfCellsToRemove;
    }
}
