package com.scratch.engine;

import com.scratch.model.*;

import java.util.*;

public class MatrixGenerator {
    private final Config config;
    private final Random random = new Random();

    public MatrixGenerator(Config config) {
        this.config = config;
    }

    public String[][] generateMatrix() {
        int rows = config.getRows();
        int columns = config.getColumns();
        String[][] matrix = new String[rows][columns];

        Map<String, Integer> bonusProbMap = config.getProbabilities().getBonusSymbols().getSymbols();
        List<String> bonusPool = weightedSymbolPool(bonusProbMap);

        Map<String, Symbol> allSymbols = config.getSymbols();
        Map<String, Symbol> standardSymbols = new HashMap<>();
        for (Map.Entry<String, Symbol> entry : allSymbols.entrySet()) {
            if ("standard".equals(entry.getValue().getType())) {
                standardSymbols.put(entry.getKey(), entry.getValue());
            }
        }

        List<StandardSymbolProbability> defaultProbs = config.getProbabilities().getStandardSymbols();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Map<String, Integer> cellProb = findCellProbability(defaultProbs, row, col);

                if (random.nextDouble() < 0.2) { // 20% chance to drop a bonus
                    matrix[row][col] = bonusPool.get(random.nextInt(bonusPool.size()));
                } else {
                    List<String> pool = weightedSymbolPool(cellProb);
                    matrix[row][col] = pool.get(random.nextInt(pool.size()));
                }
            }
        }

        return matrix;
    }

    private Map<String, Integer> findCellProbability(List<StandardSymbolProbability> probs, int row, int col) {
        return probs.stream()
                .filter(p -> p.getRow() == row && p.getColumn() == col)
                .findFirst()
                .map(StandardSymbolProbability::getSymbols)
                .orElse(probs.get(0).getSymbols()); // fallback to default
    }

    private List<String> weightedSymbolPool(Map<String, Integer> symbolMap) {
        List<String> pool = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : symbolMap.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                pool.add(entry.getKey());
            }
        }
        return pool;
    }
}
