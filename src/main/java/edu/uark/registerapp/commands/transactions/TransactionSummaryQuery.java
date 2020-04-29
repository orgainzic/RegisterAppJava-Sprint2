package edu.uark.registerapp.commands.transactions;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.TransactionSummary;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;
import edu.uark.registerapp.models.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionSummaryQuery implements ResultCommandInterface<TransactionSummary> {
    @Transactional
    @Override
    public TransactionSummary execute() {
        this.validateProperties();

        Optional<TransactionEntity> transactionEntity =
                this.transactionRepository.findById(this.transactionId);
        if (!transactionEntity.isPresent()) { // No record in the db
            throw new NotFoundException("Transaction");
        }

        List<TransactionEntryEntity> transactionEntryEntities =
                this.transactionEntryRepository.findByTransactionId(this.transactionId);

        return new TransactionSummary(
                (transactionEntity.get().getTotal() / 100.00),
                transactionEntryEntities.size());

    }

    // Helper methods
    private void validateProperties() {
        if (this.transactionId.equals(null)) {
            throw new UnprocessableEntityException("transactionID");
        }
    }

    // Properties
    private UUID transactionId;
    public UUID getTransactionId() { return this.transactionId; }
    public TransactionSummaryQuery setTransactionId(final UUID transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionEntryRepository transactionEntryRepository;
}
