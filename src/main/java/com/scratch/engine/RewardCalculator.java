////package com.scratch.engine;
////
////import com.scratch.model.*;
////
////import java.util.*;
////
////public class RewardCalculator {
////    private final Config config;
////    private final double betAmount;
////    private final String[][] matrix;
////
////    private double finalReward = 0;
////    private String bonusApplied = null;
////    private final Map<String, List<String>> appliedWinningCombinations = new HashMap<>();
////
////    public RewardCalculator(Config config, double betAmount, String[][] matrix) {
////        this.config = config;
////        this.betAmount = betAmount;
////        this.matrix = matrix;
////    }
////
////    public void evaluate() {
////        Map<String, Integer> symbolCounts = new HashMap<>();
////        int rows = matrix.length;
////        int cols = matrix[0].length;
////
////        // 1. Count symbols
////        for (int r = 0; r < rows; r++) {
////            for (int c = 0; c < cols; c++) {
////                String symbol = matrix[r][c];
////                Symbol s = config.getSymbols().get(symbol);
////                if (s == null || !"standard".equals(s.getType())) continue;
////
////                symbolCounts.put(symbol, symbolCounts.getOrDefault(symbol, 0) + 1);
////            }
////        }
////
////        // 2. Evaluate same-symbol counts
////        Map<String, WinCombination> winCombos = config.getWinCombinations();
////
////        for (Map.Entry<String, Integer> entry : symbolCounts.entrySet()) {
////            String symbol = entry.getKey();
////            int count = entry.getValue();
////            Symbol symbolObj = config.getSymbols().get(symbol);
////            double base = betAmount * symbolObj.getRewardMultiplier();
////            double multiplier = 1.0;
////            List<String> matches = new ArrayList<>();
////
////            for (Map.Entry<String, WinCombination> wc : winCombos.entrySet()) {
////                WinCombination combo = wc.getValue();
////                if (!"same_symbols".equals(combo.getWhen())) continue;
////                if (count >= combo.getCount()) {
////                    multiplier *= combo.getRewardMultiplier();
////                    matches.add(wc.getKey());
////                }
////            }
////
////            // 3. Evaluate linear patterns
////            for (Map.Entry<String, WinCombination> wc : winCombos.entrySet()) {
////                WinCombination combo = wc.getValue();
////                if (!"linear_symbols".equals(combo.getWhen())) continue;
////
////                for (List<String> area : combo.getCoveredAreas()) {
////                    String first = null;
////                    boolean valid = true;
////                    for (String coord : area) {
////                        String[] rc = coord.split(":");
////                        int r = Integer.parseInt(rc[0]);
////                        int c = Integer.parseInt(rc[1]);
////                        if (!matrix[r][c].equals(symbol)) {
////                            valid = false;
////                            break;
////                        }
////                        if (first == null) first = matrix[r][c];
////                    }
////                    if (valid) {
////                        multiplier *= combo.getRewardMultiplier();
////                        matches.add(wc.getKey());
////                        break; // Only apply once per group
////                    }
////                }
////            }
////
////            if (!matches.isEmpty()) {
////                double reward = base * multiplier;
////                finalReward += reward;
////                appliedWinningCombinations.put(symbol, matches);
////            }
////        }
////
////        // 4. Apply bonus if there's a win
////        if (finalReward > 0) {
////            applyBonusSymbol();
////        }
////    }
////
////    private void applyBonusSymbol() {
////        for (int r = 0; r < matrix.length; r++) {
////            for (int c = 0; c < matrix[0].length; c++) {
////                String symbol = matrix[r][c];
////                Symbol symbolObj = config.getSymbols().get(symbol);
////                if (symbolObj == null || !"bonus".equals(symbolObj.getType())) continue;
////
////                String impact = symbolObj.getImpact();
////                if ("multiply_reward".equals(impact)) {
////                    finalReward *= symbolObj.getRewardMultiplier();
////                    bonusApplied = symbol;
////                    return;
////                } else if ("extra_bonus".equals(impact)) {
////                    finalReward += symbolObj.getExtra();
////                    bonusApplied = symbol;
////                    return;
////                } else if ("miss".equals(impact)) {
////                    bonusApplied = symbol;
////                    return;
////                }
////            }
////        }
////    }
////
////    public double getFinalReward() { return Math.round(finalReward); }
////    public String getBonusApplied() { return bonusApplied; }
////    public Map<String, List<String>> getAppliedWinningCombinations() { return appliedWinningCombinations; }
////}
//
//
//
//
//
//
//package com.scratch.engine;
//
//import com.scratch.model.*;
//
//import java.util.*;
//
//public class RewardCalculator {
//    private final Config config;
//    private final double betAmount;
//    private final String[][] matrix;
//
//    private double finalReward = 0;
//    private String bonusApplied = null;
//    private final Map<String, List<String>> appliedWinningCombinations = new HashMap<>();
//
//    public RewardCalculator(Config config, double betAmount, String[][] matrix) {
//        this.config = config;
//        this.betAmount = betAmount;
//        this.matrix = matrix;
//    }
//
////    public void evaluate() {
////        Map<String, Integer> symbolCounts = new HashMap<>();
////        int rows = matrix.length;
////        int cols = matrix[0].length;
////
////        // 1. Count standard symbols
////        for (int r = 0; r < rows; r++) {
////            for (int c = 0; c < cols; c++) {
////                String symbol = matrix[r][c];
////                Symbol s = config.getSymbols().get(symbol);
////                if (s == null || !"standard".equals(s.getType())) continue;
////
////                symbolCounts.put(symbol, symbolCounts.getOrDefault(symbol, 0) + 1);
////            }
////        }
////
////        Map<String, WinCombination> winCombos = config.getWinCombinations();
////
////        // 2. Check each symbol for win combinations
////        for (Map.Entry<String, Integer> entry : symbolCounts.entrySet()) {
////            String symbol = entry.getKey();
////            int count = entry.getValue();
////            Symbol symbolObj = config.getSymbols().get(symbol);
////            double base = betAmount * symbolObj.getRewardMultiplier();
////            double multiplier = 1.0;
////            List<String> matches = new ArrayList<>();
////            Set<String> appliedGroups = new HashSet<>();
////
////            // Check same_symbols rules
////            for (Map.Entry<String, WinCombination> wc : winCombos.entrySet()) {
////                WinCombination combo = wc.getValue();
////                if (!"same_symbols".equals(combo.getWhen())) continue;
////                if (count >= combo.getCount() && !appliedGroups.contains(combo.getGroup())) {
////                    multiplier *= combo.getRewardMultiplier();
////                    matches.add(wc.getKey());
////                    appliedGroups.add(combo.getGroup());
////                }
////            }
////
////            // Check linear_symbols rules
////            for (Map.Entry<String, WinCombination> wc : winCombos.entrySet()) {
////                WinCombination combo = wc.getValue();
////                if (!"linear_symbols".equals(combo.getWhen())) continue;
////
////                for (List<String> area : combo.getCoveredAreas()) {
////                    boolean valid = true;
////                    for (String coord : area) {
////                        String[] rc = coord.split(":");
////                        int r = Integer.parseInt(rc[0]);
////                        int c = Integer.parseInt(rc[1]);
////                        if (!matrix[r][c].equals(symbol)) {
////                            valid = false;
////                            break;
////                        }
////                    }
////                    if (valid && !appliedGroups.contains(combo.getGroup())) {
////                        multiplier *= combo.getRewardMultiplier();
////                        matches.add(wc.getKey());
////                        appliedGroups.add(combo.getGroup());
////                        break;
////                    }
////                }
////            }
////
////            // 3. Accumulate reward
////            if (!matches.isEmpty()) {
////                double reward = base * multiplier;
////                finalReward += reward;
////                appliedWinningCombinations.put(symbol, matches);
////
////                // Optional debug
////                // System.out.printf(">>> Symbol: %s, Base: %.2f, Multiplier: %.2f, Reward: %.2f\n",
////                //         symbol, base, multiplier, reward);
////            }
////        }
////
////        // 4. Apply bonus symbol (once)
////        if (finalReward > 0) {
////            applyBonusSymbol();
////        }
////    }
//
//
//    public void evaluate() {
//        Map<String, Integer> symbolCounts = new HashMap<>();
//        int rows = matrix.length;
//        int cols = matrix[0].length;
//
//        for (int r = 0; r < rows; r++) {
//            for (int c = 0; c < cols; c++) {
//                String symbol = matrix[r][c];
//                Symbol s = config.getSymbols().get(symbol);
//                if (s == null || !"standard".equals(s.getType())) continue;
//
//                symbolCounts.put(symbol, symbolCounts.getOrDefault(symbol, 0) + 1);
//            }
//        }
//
//        Map<String, WinCombination> winCombos = config.getWinCombinations();
//
//        for (Map.Entry<String, Integer> entry : symbolCounts.entrySet()) {
//            String symbol = entry.getKey();
//            int count = entry.getValue();
//            Symbol symbolObj = config.getSymbols().get(symbol);
//            double base = betAmount * symbolObj.getRewardMultiplier();
//            double multiplier = 1.0;
//            List<String> matches = new ArrayList<>();
//            Set<String> appliedGroups = new HashSet<>();  // ❗️ Must be per symbol
//
//            // Match symbol repetition (same_symbols)
//            for (Map.Entry<String, WinCombination> wc : winCombos.entrySet()) {
//                WinCombination combo = wc.getValue();
//                if (!"same_symbols".equals(combo.getWhen())) continue;
//                if (count >= combo.getCount() && !appliedGroups.contains(combo.getGroup())) {
//                    multiplier *= combo.getRewardMultiplier();
//                    matches.add(wc.getKey());
//                    appliedGroups.add(combo.getGroup());
//                }
//            }
//
//            // Match linear patterns
//            for (Map.Entry<String, WinCombination> wc : winCombos.entrySet()) {
//                WinCombination combo = wc.getValue();
//                if (!"linear_symbols".equals(combo.getWhen())) continue;
//
//                for (List<String> area : combo.getCoveredAreas()) {
//                    boolean valid = true;
//                    for (String coord : area) {
//                        String[] rc = coord.split(":");
//                        int r = Integer.parseInt(rc[0]);
//                        int c = Integer.parseInt(rc[1]);
//                        if (!matrix[r][c].equals(symbol)) {
//                            valid = false;
//                            break;
//                        }
//                    }
//                    if (valid && !appliedGroups.contains(combo.getGroup())) {
//                        multiplier *= combo.getRewardMultiplier();
//                        matches.add(wc.getKey());
//                        appliedGroups.add(combo.getGroup());
//                        break;
//                    }
//                }
//            }
//
//            if (!matches.isEmpty()) {
//                double reward = base * multiplier;
//                finalReward += reward;
//                appliedWinningCombinations.put(symbol, matches);
//            }
//        }
//
//        // Apply bonus only if there's a win
//        if (finalReward > 0) {
//            applyBonusSymbol();
//        }
//    }
//
//
////    private void applyBonusSymbol() {
////        for (int r = 0; r < matrix.length; r++) {
////            for (int c = 0; c < matrix[0].length; c++) {
////                String symbol = matrix[r][c];
////                Symbol symbolObj = config.getSymbols().get(symbol);
////                if (symbolObj == null || !"bonus".equals(symbolObj.getType())) continue;
////
////                String impact = symbolObj.getImpact();
////                if ("multiply_reward".equals(impact)) {
////                    finalReward *= symbolObj.getRewardMultiplier();
////                    bonusApplied = symbol;
////                    return;
////                } else if ("extra_bonus".equals(impact)) {
////                    finalReward += symbolObj.getExtra();
////                    bonusApplied = symbol;
////                    return;
////                } else if ("miss".equals(impact)) {
////                    bonusApplied = symbol;
////                    return;
////                }
////            }
////        }
////    }
//private void applyBonusSymbol() {
//    for (int r = 0; r < matrix.length; r++) {
//        for (int c = 0; c < matrix[0].length; c++) {
//            String symbol = matrix[r][c];
//            Symbol symbolObj = config.getSymbols().get(symbol);
//
//            // Debug this!
//            System.out.println(">>> Bonus Check at [" + r + "," + c + "] => " + symbol);
//            if (symbolObj == null) {
//                System.out.println(">>> Symbol not found in config: " + symbol);
//                continue;
//            }
//
//            if (!"bonus".equals(symbolObj.getType())) continue;
//
//            String impact = symbolObj.getImpact();
//            System.out.println(">>> Applying Bonus Symbol: " + symbol + " | Impact: " + impact);
//
//            if ("multiply_reward".equals(impact)) {
//                finalReward *= symbolObj.getRewardMultiplier();
//                bonusApplied = symbol;
//                return;
//            } else if ("extra_bonus".equals(impact)) {
//                finalReward += symbolObj.getExtra();
//                bonusApplied = symbol;
//                return;
//            } else if ("miss".equals(impact)) {
//                bonusApplied = symbol;
//                return;
//            }
//        }
//    }
//}
//
////    public double getFinalReward() {
////        return Math.round(finalReward);
////    }
//    public double getFinalReward() {
//         return Double.parseDouble(String.format("%.2f", finalReward));
//     }
//    public String getBonusApplied() {
//        return bonusApplied;
//    }
//
//    public Map<String, List<String>> getAppliedWinningCombinations() {
//        return appliedWinningCombinations;
//    }
//}


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

//    public void evaluate() {
//        Map<String, Integer> symbolCounts = new HashMap<>();
//        int rows = matrix.length, cols = matrix[0].length;
//
//        // 1) Count standard symbols
//        for (int r = 0; r < rows; r++) {
//            for (int c = 0; c < cols; c++) {
//                String sym = matrix[r][c];
//                Symbol s = config.getSymbols().get(sym);
//                if (s != null && "standard".equals(s.getType())) {
//                    symbolCounts.merge(sym, 1, Integer::sum);
//                }
//            }
//        }
//
//        Map<String, WinCombination> winCombos = config.getWinCombinations();
//
//        // 2) For each symbol that appears, apply combos
//        for (Map.Entry<String, Integer> e : symbolCounts.entrySet()) {
//            String symbol = e.getKey();
//            int count     = e.getValue();
//            Symbol symObj = config.getSymbols().get(symbol);
//
//            double base       = betAmount * symObj.getRewardMultiplier();
//            double multiplier = 1.0;
//            List<String> matches      = new ArrayList<>();
//            Set<String> appliedGroups = new HashSet<>();
//
//            // 2a) SAME_SYMBOLS: pick the highest‑count combo
//            List<Map.Entry<String,WinCombination>> sameList = winCombos.entrySet().stream()
//                    .filter(wc -> "same_symbols".equals(wc.getValue().getWhen()))
//                    .filter(wc -> count >= wc.getValue().getCount())
//                    .collect(Collectors.toList());
//            if (!sameList.isEmpty()) {
//                var best = sameList.stream()
//                        .max(Comparator.comparingInt(x -> x.getValue().getCount()))
//                        .get();
//                WinCombination combo = best.getValue();
//                multiplier   *= combo.getRewardMultiplier();
//                matches.add(best.getKey());
//                appliedGroups.add(combo.getGroup());
//            }
//
//            // 2b) LINEAR_SYMBOLS: *only* if count == pattern length*
//            for (Map.Entry<String,WinCombination> wc : winCombos.entrySet()) {
//                WinCombination combo = wc.getValue();
//                if (!"linear_symbols".equals(combo.getWhen())) continue;
//                if (appliedGroups.contains(combo.getGroup()))  continue;
//
//                for (List<String> area : combo.getCoveredAreas()) {
//                    if (area.size() != count) continue;  // ← only when symbolCount == area length
//                    boolean valid = true;
//                    for (String coord : area) {
//                        var rc = coord.split(":");
//                        int rr = Integer.parseInt(rc[0]), cc = Integer.parseInt(rc[1]);
//                        if (!matrix[rr][cc].equals(symbol)) {
//                            valid = false; break;
//                        }
//                    }
//                    if (valid) {
//                        multiplier *= combo.getRewardMultiplier();
//                        matches.add(wc.getKey());
//                        appliedGroups.add(combo.getGroup());
//                        break;
//                    }
//                }
//            }
//
//            // 3) Accumulate
//            if (!matches.isEmpty()) {
//                double reward = base * multiplier;
//                finalReward += reward;
//                appliedWinningCombinations.put(symbol, matches);
//            }
//        }
//
//        // 4) Bonus
//        if (finalReward > 0) {
//            applyBonusSymbol();
//        }
//    }
public void evaluate() {
    Map<String, Integer> symbolCounts = new HashMap<>();
    int rows = matrix.length, cols = matrix[0].length;

    // 1) Count standard symbols
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

    // 2) For each symbol that appears, apply combos
    for (Map.Entry<String, Integer> e : symbolCounts.entrySet()) {
        String symbol = e.getKey();
        int count     = e.getValue();
        Symbol symObj = config.getSymbols().get(symbol);

        double base       = betAmount * symObj.getRewardMultiplier();
        double multiplier = 1.0;
        List<String> matches      = new ArrayList<>();
        Set<String> appliedGroups = new HashSet<>();

        // 2a) SAME_SYMBOLS: pick the highest‑count combo
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

        // 2b) LINEAR_SYMBOLS: skip entirely if count == number of columns
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

        // 3) Accumulate reward
        if (!matches.isEmpty()) {
            double reward = base * multiplier;
            finalReward += reward;
            appliedWinningCombinations.put(symbol, matches);
        }
    }

    // 4) Bonus
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

    /** Round to 2 decimals after all bonuses. */
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
