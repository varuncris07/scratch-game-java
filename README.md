# Scratch Game - Java CLI Project

This is a **Java-based Scratch Card Game** that simulates a reward-based matrix game using a configurable symbol probability system. The game determines the user's reward based on matching symbol patterns, bonus symbols, and defined rules.

---

## Features

- Random matrix generation based on symbol probabilities
- Win combinations: match counts, horizontal, vertical, diagonal lines
- Bonus logic: `10x`, `+500`, `MISS`, etc. only applied on wins
- JSON-configurable symbol definitions and rules
- Fully tested with JUnit 5
- CLI support for `--config` and `--betting-amount` arguments

---

## Technologies Used

- Java 17
- Maven (Build tool)
- Jackson (JSON serialization)
- JUnit 5 (Testing)
- JaCoCo (Test coverage reporting)

---

## How to Run

### 1. Compile and Package:

```bash
mvn clean package

java -jar target/scratch-game-1.0-SNAPSHOT-shaded.jar --config src/main/resources/config.json --betting-amount 100

--config <path_to_config_file>      # Example: src/main/resources/config.json
--betting-amount <amount>           # Example: 100
