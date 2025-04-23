package com.scratch.engine;

import com.scratch.model.*;

public class GameEngine {
    private final Config config;
    private final double betAmount;

    public GameEngine(Config config, double betAmount) {
        this.config = config;
        this.betAmount = betAmount;
    }

    public GameResult play() {
        MatrixGenerator generator = new MatrixGenerator(config);
        String[][] matrix = generator.generateMatrix();

        RewardCalculator calculator = new RewardCalculator(config, betAmount, matrix);
        calculator.evaluate();

        GameResult result = new GameResult();
        result.setMatrix(matrix);
        result.setReward(calculator.getFinalReward());
        result.setAppliedWinningCombinations(calculator.getAppliedWinningCombinations());
        result.setAppliedBonusSymbol(calculator.getBonusApplied());

        return result;
    }
}
