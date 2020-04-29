package edu.uark.registerapp.commands.transactions;

import java.util.Optional;
import java.util.UUID;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
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
        this.validateProperties();

        final Optional<TransactionEntryEntity> transactionEntryEntity =
                this.transactionEntryRepository.findById(this.transactionEntryId);
        if (!transactionEntryEntity.isPresent()) { // No record with the associated transaction ID and product ID exists in the database
            throw new NotFoundException("TransactionEntry");
        }

        this.transactionEntryRepository.delete(transactionEntryEntity.get());
    }

    // Helper Methods
    private void validateProperties() {
        if (this.transactionEntryId.equals(null)) {
            throw new UnprocessableEntityException("transactionEntryId");
        }
    }

    // Properties
    private UUID transactionEntryId;
    public UUID getTransactionEntryId() { return transactionEntryId; }
    public RemoveProductFromTransaction setTransactionEntryId(final UUID transactionEntryId) {
        this.transactionEntryId = transactionEntryId;
        return this;
    }

    @Autowired
    private TransactionEntryRepository transactionEntryRepository;
}