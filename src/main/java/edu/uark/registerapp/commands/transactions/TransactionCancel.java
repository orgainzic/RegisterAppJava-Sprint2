package edu.uark.registerapp.commands.transactions;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
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
public class TransactionCancel implements VoidCommandInterface {
    @Transactional
    @Override
    public void execute() {
        // Query TransactionEntries associated with Transaction
        final List<TransactionEntryEntity> transactionEntryEntities =
                this.transactionEntryRepository.findByTransactionId(this.transactionId);

        // Delete associated TransactionEntries from database
        for (TransactionEntryEntity transactionEntryEntity: transactionEntryEntities) {
            this.transactionEntryRepository.delete(transactionEntryEntity);
        }

        // Find Transaction in database
        final Optional<TransactionEntity> transactionEntity =
                this.transactionRepository.findById(this.transactionId);
        if (!transactionEntity.isPresent()) { // The transaction doesn't exist in the database
            throw new NotFoundException("Transaction");
        }

        // Delete Transaction record from database
        this.transactionRepository.delete(transactionEntity.get());
    }

    // Properties
    private UUID transactionId;
    public UUID getTransactionId() { return this.transactionId; }
    public TransactionCancel setTransactionId(final UUID transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionEntryRepository transactionEntryRepository;
}