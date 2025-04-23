package com.scratch.engine;

import com.scratch.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class RewardCalculator {
    private final Config config;
    private final double betAmount;
    private final String[][] matrix;

    private double finalReward = 0;
    private String bonusApplied = null;
    private final Map<String, List<String>> appliedWinningCombinations = new HashMap<>();

    public RewardCalculator(Config config, double betAmount, String[][] matrix) {
        this.config     = config;
        this.betAmount  = betAmount;
        this.matrix     = matrix;
    }


public void evaluate() {
    Map<String, Integer> symbolCounts = new HashMap<>();
    int rows = matrix.length, cols = matrix[0].length;


    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            String sym = matrix[r][c];
            Symbol s = config.getSymbols().get(sym);
            if (s != null && "standard".equals(s.getType())) {
                symbolCounts.merge(sym, 1, Integer::sum);
            }
        }
    }

    Map<String, WinCombination> winCombos = config.getWinCombinations();


    for (Map.Entry<String, Integer> e : symbolCounts.entrySet()) {
        String symbol = e.getKey();
        int count     = e.getValue();
        Symbol symObj = config.getSymbols().get(symbol);

        double base       = betAmount * symObj.getRewardMultiplier();
        double multiplier = 1.0;
        List<String> matches      = new ArrayList<>();
        Set<String> appliedGroups = new HashSet<>();


        List<Map.Entry<String,WinCombination>> sameList = winCombos.entrySet().stream()
                .filter(wc -> "same_symbols".equals(wc.getValue().getWhen()))
                .filter(wc -> count >= wc.getValue().getCount())
                .collect(Collectors.toList());
        if (!sameList.isEmpty()) {
            var best = sameList.stream()
                    .max(Comparator.comparingInt(x -> x.getValue().getCount()))
                    .get();
            WinCombination combo = best.getValue();
            multiplier   *= combo.getRewardMultiplier();
            matches.add(best.getKey());
            appliedGroups.add(combo.getGroup());
        }


        if (count != config.getColumns()) {
            for (Map.Entry<String,WinCombination> wc : winCombos.entrySet()) {
                WinCombination combo = wc.getValue();
                if (!"linear_symbols".equals(combo.getWhen())) continue;
                if (appliedGroups.contains(combo.getGroup())) continue;

                for (List<String> area : combo.getCoveredAreas()) {
                    boolean valid = true;
                    for (String coord : area) {
                        String[] rc = coord.split(":");
                        int rr = Integer.parseInt(rc[0]), cc = Integer.parseInt(rc[1]);
                        if (!matrix[rr][cc].equals(symbol)) {
                            valid = false;
                            break;
                        }
                    }
                    if (valid) {
                        multiplier *= combo.getRewardMultiplier();
                        matches.add(wc.getKey());
                        appliedGroups.add(combo.getGroup());
                        break;
                    }
                }
            }
        }


        if (!matches.isEmpty()) {
            double reward = base * multiplier;
            finalReward += reward;
            appliedWinningCombinations.put(symbol, matches);
        }
    }


    if (finalReward > 0) {
        applyBonusSymbol();
    }
}

    private void applyBonusSymbol() {
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[0].length; c++) {
                String symbol = matrix[r][c];
                Symbol sym = config.getSymbols().get(symbol);
                if (sym == null || !"bonus".equals(sym.getType())) continue;

                String impact = sym.getImpact();
                if ("multiply_reward".equals(impact)) {
                    finalReward *= sym.getRewardMultiplier();
                } else if ("extra_bonus".equals(impact)) {
                    finalReward += sym.getExtra();
                }
                bonusApplied = symbol;
                return;
            }
        }
    }


    public double getFinalReward() {
        return Math.round(finalReward * 100.0) / 100.0;
    }

    public String getBonusApplied() {
        return bonusApplied;
    }

    public Map<String, List<String>> getAppliedWinningCombinations() {
        return appliedWinningCombinations;
    }
}
