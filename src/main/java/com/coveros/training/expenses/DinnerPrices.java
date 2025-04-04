package com.coveros.training.expenses;

public class DinnerPrices {
    private final double subTotal;
    private final double foodTotal;
    private final double tip;
    private final double tax;

    public DinnerPrices(double subTotal, double foodTotal, double tip, double tax) {
        this.subTotal = subTotal;
        this.foodTotal = foodTotal;
        this.tip = tip;
        this.tax = tax;
    }

    public double getSubtotal() {
        return subTotal;
    }

    public double getFoodTotal() {
        return foodTotal;
    }

    public double getTip() {
        return tip;
    }

    public double getTax() {
        return tax;
    }
}
