package com.scratch.model;

import java.util.Map;

public class Config {
    private int columns = 3;
    private int rows = 3;
    private Map<String, Symbol> symbols;
    private ProbabilityConfig probabilities;

    @com.fasterxml.jackson.annotation.JsonProperty("win_combinations")
    private Map<String, WinCombination> winCombinations;

    public int getColumns() { return columns; }
    public void setColumns(int columns) { this.columns = columns; }

    public int getRows() { return rows; }
    public void setRows(int rows) { this.rows = rows; }

    public Map<String, Symbol> getSymbols() { return symbols; }
    public void setSymbols(Map<String, Symbol> symbols) { this.symbols = symbols; }

    public ProbabilityConfig getProbabilities() { return probabilities; }
    public void setProbabilities(ProbabilityConfig probabilities) { this.probabilities = probabilities; }

    public Map<String, WinCombination> getWinCombinations() { return winCombinations; }
    public void setWinCombinations(Map<String, WinCombination> winCombinations) { this.winCombinations = winCombinations; }
}
