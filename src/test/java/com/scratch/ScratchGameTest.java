//package com.scratch;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.scratch.engine.GameEngine;
//import com.scratch.model.Config;
//import com.scratch.model.GameResult;
//import org.junit.jupiter.api.Test;
//
//import java.io.File;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ScratchGameTest {
//
//    @Test
//    public void testWinningGameWithBonus() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        Config config = mapper.readValue(new File("src/main/resources/config.json"), Config.class);
//
//        double bet = 100;
//        String[][] matrix = {
//                {"A", "A", "B"},
//                {"A", "+1000", "B"},
//                {"A", "A", "B"}
//        };
//
//        TestGameEngine engine = new TestGameEngine(config, bet, matrix);
//        GameResult result = engine.play();
//
//        assertEquals(3600, result.getReward(), 0.01);
//        assertEquals("+1000", result.getAppliedBonusSymbol());
//        assertTrue(result.getAppliedWinningCombinations().containsKey("A"));
//        assertTrue(result.getAppliedWinningCombinations().containsKey("B"));
//    }
//
//    @Test
//    public void testLostGameNoBonusApplied() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        Config config = mapper.readValue(new File("src/main/resources/config.json"), Config.class);
//
//        double bet = 100;
//        String[][] matrix = {
//                {"A", "B", "C"},
//                {"E", "B", "5x"},
//                {"F", "D", "C"}
//        };
//
//        TestGameEngine engine = new TestGameEngine(config, bet, matrix);
//        GameResult result = engine.play();
//
//        assertEquals(0, result.getReward(), 0.01);
//        assertNull(result.getAppliedBonusSymbol());
//        assertTrue(result.getAppliedWinningCombinations().isEmpty());
//    }
//}


//
//package com.scratch;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.scratch.model.Config;
//import com.scratch.model.GameResult;
//import org.junit.jupiter.api.Test;
//
//import java.io.File;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ScratchGameTest {
//
//    // existing tests …
//
//    @Test
//    public void testHorizontalWinWith5xBonus() throws Exception {
//        Config config = new ObjectMapper()
//                .readValue(new File("src/main/resources/config.json"), Config.class);
//
//        double bet = 100;
//        String[][] matrix = {
//                {"C", "C", "C"},
//                {"A", "5x", "D"},
//                {"E", "F", "B"}
//        };
//
//        TestGameEngine engine = new TestGameEngine(config, bet, matrix);
//        GameResult result = engine.play();
//
//        // 100×2.5=250; ×same_symbol_3_times(1)×same_symbols_horizontally(2)=500; bonus 5× → 2500
//        assertEquals(2500.0, result.getReward(), 0.01);
//        assertEquals("5x", result.getAppliedBonusSymbol());
//        assertTrue(result.getAppliedWinningCombinations().containsKey("C"));
//        assertIterableEquals(
//                List.of("same_symbol_3_times", "same_symbols_horizontally"),
//                result.getAppliedWinningCombinations().get("C")
//        );
//    }
//
//    @Test
//    public void testDiagonalWinWith10xBonus() throws Exception {
//        Config config = new ObjectMapper()
//                .readValue(new File("src/main/resources/config.json"), Config.class);
//
//        double bet = 100;
//        String[][] matrix = {
//                {"D", "10x", "A"},
//                {"B", "D",  "C"},
//                {"A", "B",  "D"}
//        };
//
//        TestGameEngine engine = new TestGameEngine(config, bet, matrix);
//        GameResult result = engine.play();
//
//        // D: 100×2=200; ×same_symbol_3_times(1)×same_symbols_diagonally_left_to_right(5)=1000; bonus×10=10000
//        assertEquals(10000.0, result.getReward(), 0.01);
//        assertEquals("10x", result.getAppliedBonusSymbol());
//        assertIterableEquals(
//                List.of("same_symbol_3_times", "same_symbols_diagonally_left_to_right"),
//                result.getAppliedWinningCombinations().get("D")
//        );
//    }
//
//    @Test
//    public void testNoWinBonusIgnored() throws Exception {
//        Config config = new ObjectMapper()
//                .readValue(new File("src/main/resources/config.json"), Config.class);
//
//        double bet = 100;
//        String[][] matrix = {
//                {"A", "B",  "C"},
//                {"D", "10x","E"},
//                {"F", "B",  "C"}
//        };
//
//        TestGameEngine engine = new TestGameEngine(config, bet, matrix);
//        GameResult result = engine.play();
//
//        assertEquals(0.0, result.getReward(), 0.01);
//        assertNull(result.getAppliedBonusSymbol());
//        assertTrue(result.getAppliedWinningCombinations().isEmpty());
//    }
//
//    @Test
//    public void testWinWithMissBonus() throws Exception {
//        Config config = new ObjectMapper()
//                .readValue(new File("src/main/resources/config.json"), Config.class);
//
//        double bet = 100;
//        String[][] matrix = {
//                {"A", "A",  "A"},
//                {"D", "MISS","F"},
//                {"B", "C",  "E"}
//        };
//
//        TestGameEngine engine = new TestGameEngine(config, bet, matrix);
//        GameResult result = engine.play();
//
//        // A: 100×5=500; ×same_symbol_3_times(1)×same_symbols_horizontally(2)=1000; bonus MISS => no change
//        assertEquals(1000.0, result.getReward(), 0.01);
//        assertEquals("MISS", result.getAppliedBonusSymbol());
//        assertIterableEquals(
//                List.of("same_symbol_3_times", "same_symbols_horizontally"),
//                result.getAppliedWinningCombinations().get("A")
//        );
//    }
//
//    @Test
//    public void testFourSymbolWinNoBonus() throws Exception {
//        Config config = new ObjectMapper()
//                .readValue(new File("src/main/resources/config.json"), Config.class);
//
//        double bet = 100;
//        String[][] matrix = {
//                {"D", "D", "D"},
//                {"D", "A", "B"},
//                {"C", "E", "F"}
//        };
//
//        TestGameEngine engine = new TestGameEngine(config, bet, matrix);
//        GameResult result = engine.play();
//
//        // D: 100×2=200; same_symbol_4_times multiplier 1.5 => 300; no linear combo, no bonus
//        assertEquals(300.0, result.getReward(), 0.01);
//        assertNull(result.getAppliedBonusSymbol());
//        assertIterableEquals(
//                List.of("same_symbol_4_times"),
//                result.getAppliedWinningCombinations().get("D")
//        );
//    }
//}





package com.scratch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scratch.model.Config;
import com.scratch.model.GameResult;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScratchGameTest {

    // ——————————————————————————————————————————————————————————————————————————
    @Test
    public void testWinningGameWithBonus() throws Exception {
        Config config = new ObjectMapper()
                .readValue(new File("src/main/resources/config.json"), Config.class);

        double bet = 100;
        String[][] matrix = {
                {"A", "A", "B"},
                {"A", "+1000", "B"},
                {"A", "A", "B"}
        };

        TestGameEngine engine = new TestGameEngine(config, bet, matrix);
        GameResult result = engine.play();

        assertEquals(3600.0, result.getReward(), 0.01);
        assertEquals("+1000", result.getAppliedBonusSymbol());
        assertTrue(result.getAppliedWinningCombinations().containsKey("A"));
        assertTrue(result.getAppliedWinningCombinations().containsKey("B"));
    }

    // ——————————————————————————————————————————————————————————————————————————
    @Test
    public void testLostGameNoBonusApplied() throws Exception {
        Config config = new ObjectMapper()
                .readValue(new File("src/main/resources/config.json"), Config.class);

        double bet = 100;
        String[][] matrix = {
                {"A", "B", "C"},
                {"E", "B", "5x"},
                {"F", "D", "C"}
        };

        TestGameEngine engine = new TestGameEngine(config, bet, matrix);
        GameResult result = engine.play();

        assertEquals(0.0, result.getReward(), 0.01);
        assertNull(result.getAppliedBonusSymbol());
        assertTrue(result.getAppliedWinningCombinations().isEmpty());
    }

    // ——————————————————————————————————————————————————————————————————————————
    @Test
    public void testHorizontalWinWith5xBonus() throws Exception {
        Config config = new ObjectMapper()
                .readValue(new File("src/main/resources/config.json"), Config.class);

        double bet = 100;
        String[][] matrix = {
                {"C", "C", "C"},
                {"A", "5x", "D"},
                {"E", "F", "B"}
        };

        TestGameEngine engine = new TestGameEngine(config, bet, matrix);
        GameResult result = engine.play();

        // 100×2.5=250; ×same_symbol_3_times(1)×same_symbols_horizontally(2)=500; bonus×5=2500
        assertEquals(2500.0, result.getReward(), 0.01);
        assertEquals("5x", result.getAppliedBonusSymbol());
        assertIterableEquals(
                List.of("same_symbol_3_times", "same_symbols_horizontally"),
                result.getAppliedWinningCombinations().get("C")
        );
    }

    // ——————————————————————————————————————————————————————————————————————————
    @Test
    public void testDiagonalWinWith10xBonus() throws Exception {
        Config config = new ObjectMapper()
                .readValue(new File("src/main/resources/config.json"), Config.class);

        double bet = 100;
        String[][] matrix = {
                {"D", "10x", "A"},
                {"B", "D",  "C"},
                {"A", "B",  "D"}
        };

        TestGameEngine engine = new TestGameEngine(config, bet, matrix);
        GameResult result = engine.play();

        // D: 100×2=200; ×same_symbol_3_times(1)×same_symbols_diagonally_left_to_right(5)=1000; bonus×10=10000
        assertEquals(10000.0, result.getReward(), 0.01);
        assertEquals("10x", result.getAppliedBonusSymbol());
        assertIterableEquals(
                List.of("same_symbol_3_times", "same_symbols_diagonally_left_to_right"),
                result.getAppliedWinningCombinations().get("D")
        );
    }

    // ——————————————————————————————————————————————————————————————————————————
    @Test
    public void testNoWinBonusIgnored() throws Exception {
        Config config = new ObjectMapper()
                .readValue(new File("src/main/resources/config.json"), Config.class);

        double bet = 100;
        String[][] matrix = {
                {"A", "B",  "C"},
                {"D", "10x","E"},
                {"F", "B",  "C"}
        };

        TestGameEngine engine = new TestGameEngine(config, bet, matrix);
        GameResult result = engine.play();

        assertEquals(0.0, result.getReward(), 0.01);
        assertNull(result.getAppliedBonusSymbol());
        assertTrue(result.getAppliedWinningCombinations().isEmpty());
    }

    // ——————————————————————————————————————————————————————————————————————————
    @Test
    public void testWinWithMissBonus() throws Exception {
        Config config = new ObjectMapper()
                .readValue(new File("src/main/resources/config.json"), Config.class);

        double bet = 100;
        String[][] matrix = {
                {"A", "A",  "A"},
                {"D", "MISS","F"},
                {"B", "C",  "E"}
        };

        TestGameEngine engine = new TestGameEngine(config, bet, matrix);
        GameResult result = engine.play();

        // A: 100×5=500; ×same_symbol_3_times(1)×same_symbols_horizontally(2)=1000; bonus MISS => 1000
        assertEquals(1000.0, result.getReward(), 0.01);
        assertEquals("MISS", result.getAppliedBonusSymbol());
        assertIterableEquals(
                List.of("same_symbol_3_times", "same_symbols_horizontally"),
                result.getAppliedWinningCombinations().get("A")
        );
    }

    // ——————————————————————————————————————————————————————————————————————————
    @Test
    public void testFourSymbolWinNoBonus() throws Exception {
        Config config = new ObjectMapper()
                .readValue(new File("src/main/resources/config.json"), Config.class);

        double bet = 100;
        String[][] matrix = {
                {"D", "D", "D"},
                {"D", "A", "B"},
                {"C", "E", "F"}
        };

        TestGameEngine engine = new TestGameEngine(config, bet, matrix);
        GameResult result = engine.play();

        // D: 100×2=200; ×same_symbol_4_times(1.5)=300; no linear combo, no bonus
        assertEquals(300.0, result.getReward(), 0.01);
        assertNull(result.getAppliedBonusSymbol());
        assertIterableEquals(
                List.of("same_symbol_4_times"),
                result.getAppliedWinningCombinations().get("D")
        );
    }
}

