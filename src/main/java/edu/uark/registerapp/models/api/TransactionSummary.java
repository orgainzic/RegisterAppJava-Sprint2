package edu.uark.registerapp.models.api;

public class TransactionSummary extends ApiResponse{
    private double price;
    public double getPrice() { return this.price; }
    public TransactionSummary setPrice(final double price) {
        this.price = price;
        return this;
    }

    private int count;
    public int getCount() {return this.count; }
    public TransactionSummary setCount(final int count) {
        this.count = count;
        return this;
    }

    public TransactionSummary(double price, int count) {
        super(false);

        this.price = price;
        this.count = count;

    }

    public TransactionSummary() {
        super();

        this.price = 0.00;
        this.count = 0;
    }
}
