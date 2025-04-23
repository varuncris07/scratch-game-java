package com.scratch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ProbabilityConfig {

    @JsonProperty("standard_symbols")
    private List<StandardSymbolProbability> standardSymbols;

    @JsonProperty("bonus_symbols")
    private BonusSymbolProbability bonusSymbols;

    public List<StandardSymbolProbability> getStandardSymbols() {
        return standardSymbols;
    }
    public void setStandardSymbols(List<StandardSymbolProbability> standardSymbols) {
        this.standardSymbols = standardSymbols;
    }

    public BonusSymbolProbability getBonusSymbols() {
        return bonusSymbols;
    }
    public void setBonusSymbols(BonusSymbolProbability bonusSymbols) {
        this.bonusSymbols = bonusSymbols;
    }
}
