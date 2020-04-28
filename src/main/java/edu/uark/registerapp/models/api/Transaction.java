package edu.uark.registerapp.models.api;

import edu.uark.registerapp.models.entities.TransactionEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction extends ApiResponse{
    private UUID id;
    public UUID getId() { return  this.id; }
    public Transaction setId(final UUID id) {
        this.id = id;
        return this;
    }

    private UUID cashierId;

    public UUID getCashierId() {
        return this.cashierId;
    }

    public Transaction setCashierId(final UUID cashierId) {
        this.cashierId = cashierId;
        return this;
    }

    private long total;

    public long getTotal() {
        return this.total;
    }

    public Transaction setTotal(final long total) {
        this.total = total;
        return this;
    }

    private int type; // TODO: The idea is to map this to different types of transactions: Sale=1, Return, etc.

    public int getType() {
        return this.type;
    }

    public Transaction setType(final int type) {
        this.type = type;
        return this;
    }

    private UUID referenceId;

    public UUID getReferenceId() {
        return this.referenceId;
    }

    public Transaction setReferenceId(final UUID referenceId) {
        this.referenceId = referenceId;
        return this;
    }

    private String createdOn;

    public String getCreatedOn() {
        return this.createdOn;
    }

    public Transaction setCreatedOn(final String createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Transaction setCreatedOn(final LocalDateTime createdOn) {
        this.createdOn =
                createdOn.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        return this;
    }

    public Transaction() {
        super();

        this.id = new UUID(0,0);
        this.cashierId = new UUID(0,0);
        this.total = 0;
        this.type = -1;
        this.referenceId = new UUID(0,0);

        this.setCreatedOn(LocalDateTime.now());
    }

    public Transaction(final TransactionEntity transactionEntity) {
        super(false);

        this.id = transactionEntity.getId();
        this.cashierId = transactionEntity.getCashierId();
        this.total = transactionEntity.getTotal();
        this.type = transactionEntity.getType();
        this.referenceId = transactionEntity.getReferenceId();

        this.setCreatedOn(transactionEntity.getCreatedOn());
    }
}
