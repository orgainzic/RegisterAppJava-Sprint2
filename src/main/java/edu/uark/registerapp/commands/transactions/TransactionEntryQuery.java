package edu.uark.registerapp.commands.transactions;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.api.TransactionEntry;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionEntryQuery implements ResultCommandInterface<TransactionEntry> {
    @Override
    public TransactionEntry execute() {
        final Optional<TransactionEntryEntity> transactionEntryEntity =
                this.transactionEntryRepository.findById(this.transactionEntryId);
        if (transactionEntryEntity.isPresent()) {
            return new TransactionEntry((transactionEntryEntity.get()));
        } else {
            throw new NotFoundException("TransactionEntry");
        }
    }

    // Properties
    private UUID transactionEntryId;
    public UUID getTransactionEntryId() { return this.transactionEntryId; }
    public TransactionEntryQuery setTransactionEntryId(final UUID transactionEntryId) {
        this.transactionEntryId = transactionEntryId;
        return this;
    }

    @Autowired
    private TransactionEntryRepository transactionEntryRepository;
}
