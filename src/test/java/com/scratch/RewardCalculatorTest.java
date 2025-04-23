package com.scratch;

import com.scratch.engine.RewardCalculator;
import com.scratch.model.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RewardCalculatorTest {

    private Config buildBaseConfig() {
        Config config = new Config();

        Map<String, Symbol> symbols = new HashMap<>();
        symbols.put("A", createSymbol("standard", 5));
        symbols.put("B", createSymbol("standard", 3));
        symbols.put("F", createSymbol("standard", 1));
        symbols.put("+1000", createBonus("extra_bonus", 0, 1000));
        symbols.put("10x", createBonus("multiply_reward", 10, 0));
        symbols.put("MISS", createBonus("miss", 0, 0));
        config.setSymbols(symbols);

        Map<String, WinCombination> winCombos = new HashMap<>();
        winCombos.put("same_symbol_3_times", createCombo("same_symbols", 3, 1, "same_symbols"));
        winCombos.put("same_symbol_4_times", createCombo("same_symbols", 4, 1.5, "same_symbols"));
        winCombos.put("same_symbols_vertically", createLinearCombo("vertically_linear_symbols", 2,
                Arrays.asList(List.of("0:0", "1:0", "2:0"))));
        winCombos.put("same_symbols_horizontally", createLinearCombo("horizontally_linear_symbols", 1.13,
                Arrays.asList(
                        List.of("0:0", "0:1", "0:2"),
                        List.of("1:0", "1:1", "1:2"),
                        List.of("2:0", "2:1", "2:2")
                )));
        config.setWinCombinations(winCombos);

        config.setColumns(3);
        return config;
    }

    private Symbol createSymbol(String type, double multiplier) {
        Symbol s = new Symbol();
        s.setType(type);
        s.setRewardMultiplier(multiplier);
        return s;
    }

    private Symbol createBonus(String impact, double multiplier, double extra) {
        Symbol b = new Symbol();
        b.setType("bonus");
        b.setImpact(impact);
        if ("multiply_reward".equals(impact)) b.setRewardMultiplier(multiplier);
        if ("extra_bonus".equals(impact)) b.setExtra((int) extra);
        return b;
    }

    private WinCombination createCombo(String when, int count, double mult, String group) {
        WinCombination w = new WinCombination();
        w.setWhen(when);
        w.setCount(count);
        w.setRewardMultiplier(mult);
        w.setGroup(group);
        return w;
    }

    private WinCombination createLinearCombo(String group, double mult, List<List<String>> areas) {
        WinCombination w = new WinCombination();
        w.setWhen("linear_symbols");
        w.setGroup(group);
        w.setRewardMultiplier(mult);
        w.setCoveredAreas(areas);
        return w;
    }
    @Test
    void testSimple3SymbolMatch() {
        String[][] matrix = {
                {"A", "A", "A"},
                {"B", "B", "F"},
                {"F", "F", "F"}
        };

        RewardCalculator rc = new RewardCalculator(buildBaseConfig(), 100, matrix);
        rc.evaluate();

        assertEquals(669.5, rc.getFinalReward());
        assertTrue(rc.getAppliedWinningCombinations().containsKey("A"));
    }

    @Test
    void testLinearAndCountMatchTogether() {
        String[][] matrix = {
                {"F", "B", "C"},
                {"F", "B", "C"},
                {"F", "B", "C"}
        };

        RewardCalculator rc = new RewardCalculator(buildBaseConfig(), 100, matrix);
        rc.evaluate();

        assertEquals(400, rc.getFinalReward());
    }


    @Test
    void testBonusExtraAppliesAfterWin() {
        String[][] matrix = {
                {"A", "+1000", "A"},
                {"A", "F", "B"},
                {"F", "F", "F"}
        };

        RewardCalculator rc = new RewardCalculator(buildBaseConfig(), 100, matrix);
        rc.evaluate();

        assertEquals(1669.5, rc.getFinalReward());
        assertEquals("+1000", rc.getBonusApplied());
    }

    @Test
    void testMultiplyBonusAppliesCorrectly() {
        String[][] matrix = {
                {"F", "F", "10x"},
                {"F", "B", "B"},
                {"A", "A", "A"}
        };

        RewardCalculator rc = new RewardCalculator(buildBaseConfig(), 100, matrix);
        rc.evaluate();

        assertEquals(6000, rc.getFinalReward());
        assertEquals("10x", rc.getBonusApplied());
    }

    @Test
    void testOnlyFirstBonusApplies() {
        String[][] matrix = {
                {"F", "+1000", "10x"},
                {"F", "B", "B"},
                {"A", "A", "A"}
        };

        RewardCalculator rc = new RewardCalculator(buildBaseConfig(), 100, matrix);
        rc.evaluate();

        assertEquals(1500, rc.getFinalReward());
        assertEquals("+1000", rc.getBonusApplied());
    }

    @Test
    void testBonusNotAppliedWithoutWin() {
        String[][] matrix = {
                {"MISS", "+1000", "MISS"},
                {"F", "B", "C"},
                {"B", "C", "D"}
        };

        RewardCalculator rc = new RewardCalculator(buildBaseConfig(), 100, matrix);
        rc.evaluate();

        assertEquals(0, rc.getFinalReward());
        assertNull(rc.getBonusApplied());
    }
}

