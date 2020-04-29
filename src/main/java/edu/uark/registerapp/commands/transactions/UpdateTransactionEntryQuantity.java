package edu.uark.registerapp.commands.transactions;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.TransactionEntry;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateTransactionEntryQuantity implements ResultCommandInterface<TransactionEntry> {
    @Transactional
    @Override
    public TransactionEntry execute() {

        this.validateProperties();

        Optional<TransactionEntryEntity> transactionEntryEntity =
                this.transactionEntryRepository.findById(apiTransactionEntry.getId());
        if (!transactionEntryEntity.isPresent()) { // No record with the associated record Id was found in the database
            throw new NotFoundException("TransactionEntry");
        }

        // Synchronize any incoming changes for UPDATE to the database
        this.apiTransactionEntry = transactionEntryEntity.get().synchronize(this.apiTransactionEntry);

        // Write, via an UPDATE, any changes to the database
        this.transactionEntryRepository.save(transactionEntryEntity.get());

        return this.apiTransactionEntry;
    }

    // Helper methods
    private void validateProperties() {
        if (this.apiTransactionEntry.equals(null)) {
            throw new UnprocessableEntityException("apiTransaction");
        }
    }

    // Properties
    private TransactionEntry apiTransactionEntry;
    public TransactionEntry getApiTransactionEntry() { return this.apiTransactionEntry; }
    public UpdateTransactionEntryQuantity setApiTransactionEntry(final TransactionEntry apiTransactionEntry) {
        this.apiTransactionEntry = apiTransactionEntry;
        return this;
    }

    @Autowired
    private TransactionEntryRepository transactionEntryRepository;
}