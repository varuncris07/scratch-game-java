//package com.scratch;
//
//import com.scratch.engine.RewardCalculator;
//import com.scratch.model.*;
//
//public class TestGameEngine {
//    private final Config config;
//    private final double betAmount;
//    private final String[][] matrix;
//
//    public TestGameEngine(Config config, double betAmount, String[][] matrix) {
//        this.config = config;
//        this.betAmount = betAmount;
//        this.matrix = matrix;
//    }
//
//    public GameResult play() {
//        RewardCalculator calculator = new RewardCalculator(config, betAmount, matrix);
//        calculator.evaluate();
//
//        GameResult result = new GameResult();
//        result.setMatrix(matrix);
//        result.setReward(calculator.getFinalReward());
//        result.setAppliedWinningCombinations(calculator.getAppliedWinningCombinations());
//        result.setAppliedBonusSymbol(calculator.getBonusApplied());
//
//        return result;
//    }
//}
//
//package com.scratch;
//
//import com.scratch.engine.RewardCalculator;
//import com.scratch.model.*;
//
//public class TestGameEngine {
//    private final Config config;
//    private final double betAmount;
//    private final String[][] matrix;
//
//    public TestGameEngine(Config config, double betAmount, String[][] matrix) {
//        this.config = config;
//        this.betAmount = betAmount;
//        this.matrix = matrix;
//    }
//
//    public GameResult play() {
//        RewardCalculator calculator = new RewardCalculator(config, betAmount, matrix);
//        calculator.evaluate();  // This updates reward
//
//        GameResult result = new GameResult();
//        result.setMatrix(matrix);
//        result.setReward(calculator.getFinalReward());  // ✅ Use finalReward from the correct instance
//        result.setAppliedWinningCombinations(calculator.getAppliedWinningCombinations());
//        result.setAppliedBonusSymbol(calculator.getBonusApplied());
//
//        return result;
//    }
//}
package com.scratch;

import com.scratch.engine.RewardCalculator;
import com.scratch.model.*;

public class TestGameEngine {
    private final Config config;
    private final double betAmount;
    private final String[][] matrix;

    public TestGameEngine(Config config, double betAmount, String[][] matrix) {
        this.config = config;
        this.betAmount = betAmount;
        this.matrix = matrix;
    }

    public GameResult play() {
        RewardCalculator calculator = new RewardCalculator(config, betAmount, matrix);
        calculator.evaluate();  // This updates reward

        GameResult result = new GameResult();
        result.setMatrix(matrix);
        result.setReward(calculator.getFinalReward());  // ✅ Use finalReward from the correct instance
        result.setAppliedWinningCombinations(calculator.getAppliedWinningCombinations());
        result.setAppliedBonusSymbol(calculator.getBonusApplied());

        return result;
    }
}
