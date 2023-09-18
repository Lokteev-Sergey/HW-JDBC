package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class FoodEntity {

    private Integer foodId;
    @JsonProperty("name")
    private String foodName;
    @JsonProperty("type")
    private String foodType;
    @JsonProperty("exotic")
    private BigDecimal foodExotic;

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    @JsonIgnore
    public String getFoodIdColumnName() {
        return "FOOD_ID";
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    @JsonIgnore
    public String getFoodNameColumnName() {
        return "FOOD_NAME";
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    @JsonIgnore
    public String getFoodTypeColumnName() {
        return "FOOD_TYPE";
    }

    public BigDecimal getFoodExotic() {
        return foodExotic;
    }

    public void setFoodExotic(BigDecimal foodExotic) {
        this.foodExotic = foodExotic;
    }

    @JsonIgnore
    public String getFoodExoticColumnName() {
        return "FOOD_EXOTIC";
    }

    @Override
    public String toString() {
        return "FoodEntity{" + "foodId=" + foodId + ", foodName='" + foodName + '\'' + ", foodType='" + foodType + '\'' + ", foodExotic=" + foodExotic + '}';
    }
}
