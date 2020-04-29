package edu.uark.registerapp.commands.transactions;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.TransactionEntry;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionEntriesQueriedByTransactionId implements ResultCommandInterface<TransactionEntry[]> {
    @Override
    public TransactionEntry[] execute() {
        this.validateProperties();

        return this.transactionEntryRepository.findByTransactionId(this.transactionId)
                .stream().map(TransactionEntry::new).toArray(TransactionEntry[]::new);

    }

    // Help functions
    private void validateProperties() {
        if (this.transactionId.equals(null)) {
            throw new UnprocessableEntityException("transactionId");
        }
    }

    // Properties
    private UUID transactionId;
    public UUID getTransactionId() {
        return  transactionId;
    }
    public TransactionEntriesQueriedByTransactionId setTransactionId(final UUID transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    @Autowired
    private TransactionEntryRepository transactionEntryRepository;
}
