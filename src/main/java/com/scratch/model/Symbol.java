package com.scratch.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Symbol {
    private String type;

    @JsonProperty("reward_multiplier")
    private Double rewardMultiplier;

    private String impact;
    private Integer extra;



    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Double getRewardMultiplier() { return rewardMultiplier; }
    public void setRewardMultiplier(Double rewardMultiplier) { this.rewardMultiplier = rewardMultiplier; }

    public String getImpact() { return impact; }
    public void setImpact(String impact) { this.impact = impact; }

    public Integer getExtra() { return extra; }
    public void setExtra(Integer extra) { this.extra = extra; }
}
