package org.example;

import java.math.BigDecimal;

public class FoodEntity {
    private Integer foodId;
    private String foodName;
    private String foodType;
    private BigDecimal foodExotic;

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getFoodIdColumnName() {
        return "FOOD_ID";
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodNameColumnName() {
        return "FOOD_NAME";
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getFoodTypeColumnName() {
        return "FOOD_TYPE";
    }

    public BigDecimal getFoodExotic() {
        return foodExotic;
    }

    public void setFoodExotic(BigDecimal foodExotic) {
        this.foodExotic = foodExotic;
    }

    public String getFoodExoticColumnName() {
        return "FOOD_EXOTIC";
    }

    @Override
    public String toString() {
        return "FoodEntity{" + "foodId=" + foodId + ", foodName='" + foodName + '\'' + ", foodType='" + foodType + '\'' + ", foodExotic=" + foodExotic + '}';
    }
}
