package edu.uark.registerapp.models.api;

import edu.uark.registerapp.models.entities.TransactionEntryEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class TransactionEntry extends ApiResponse {
    private UUID id;
    public UUID getId() { return this.id; }
    public TransactionEntry setId(final UUID id) {
        this.id = id;
        return this;
    }

    private UUID transactionId;
    public UUID getTransactionId() { return this.transactionId; }
    public TransactionEntry setTransactionId(final UUID transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    private UUID productId;
    public UUID getProductId() { return this.productId; }
    public TransactionEntry setProductId(final UUID productId) {
        this.productId = productId;
        return this;
    }

    private double quantity;
    public double getQuantity() { return this.quantity; }
    public TransactionEntry setQuantity(final double quantity) {
        this.quantity = quantity;
        return this;
    }

    private long price;
    public long getPrice() { return this.price; }
    public TransactionEntry setPrice(final long price) {
        this.price = price;
        return this;
    }

    private String createdOn;
    public String getCreatedOn() { return this.createdOn; }
    public TransactionEntry setCreatedOn(final LocalDateTime createdOn) {
        this.createdOn =
                createdOn.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return this;
    }

    public TransactionEntry() {
        super();

        this.id = new UUID(0,0);
        this.transactionId = new UUID(0,0);
        this.productId = new UUID(0,0);
        this.quantity = -1;
        this.price = 0;

        this.setCreatedOn(LocalDateTime.now());
    }

    public TransactionEntry(final TransactionEntryEntity transactionEntryEntity) {
        super(false);

        this.id = transactionEntryEntity.getId();
        this.transactionId = transactionEntryEntity.getTransactionId();
        this.productId = transactionEntryEntity.getProductId();
        this.quantity = transactionEntryEntity.getQuantity();
        this.price = transactionEntryEntity.getPrice();

        this.setCreatedOn(transactionEntryEntity.getCreatedOn());
    }
}
