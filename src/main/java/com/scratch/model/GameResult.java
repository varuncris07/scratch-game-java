package com.scratch.model;

import java.util.List;
import java.util.Map;

public class GameResult {
    private String[][] matrix;
    private double reward;
    private Map<String, List<String>> appliedWinningCombinations;
    private String appliedBonusSymbol;

    public String[][] getMatrix() { return matrix; }
    public void setMatrix(String[][] matrix) { this.matrix = matrix; }

    public double getReward() { return reward; }
    public void setReward(double reward) { this.reward = reward; }

    public Map<String, List<String>> getAppliedWinningCombinations() { return appliedWinningCombinations; }
    public void setAppliedWinningCombinations(Map<String, List<String>> appliedWinningCombinations) { this.appliedWinningCombinations = appliedWinningCombinations; }

    public String getAppliedBonusSymbol() { return appliedBonusSymbol; }
    public void setAppliedBonusSymbol(String appliedBonusSymbol) { this.appliedBonusSymbol = appliedBonusSymbol; }
}
