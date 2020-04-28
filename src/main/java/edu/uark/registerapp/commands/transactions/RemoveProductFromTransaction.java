package edu.uark.registerapp.commands.transactions;

import java.util.Optional;
import java.util.UUID;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoveProductFromTransaction implements VoidCommandInterface {
    @Transactional
    @Override
    public void execute() {
        final Optional<TransactionEntryEntity> transactionEntryEntity =
                this.transactionEntryRepository.findByTransactionIdAndProductId(
                        this.transactionId,
                        this.productId
                );
        if (!transactionEntryEntity.isPresent()) { // No record with the associated transaction ID and product ID exists in the database
            throw new NotFoundException("TransactionEntry");
        }

        this.transactionEntryRepository.delete(transactionEntryEntity.get());
    }

    // Properties
    private UUID transactionId;
    public UUID getTransactionID() { return this.transactionId; }
    public RemoveProductFromTransaction setTransactionId(final UUID transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    private UUID productId;
    public UUID getProductId() { return this.productId; }
    public RemoveProductFromTransaction setProductId(final UUID productId) {
        this.productId = productId;
        return this;
    }

    @Autowired
    private TransactionEntryRepository transactionEntryRepository;
}