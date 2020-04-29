package edu.uark.registerapp.commands.transactions;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Transaction;
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
public class UpdateTransactionPrice implements ResultCommandInterface<Transaction> {
    @Transactional
    @Override
    public Transaction execute() {
        this.validateProperties();

        List<TransactionEntryEntity> transactionEntryEntities =
                this.transactionEntryRepository.findByTransactionId(this.transactionID);

        long total = 0L;
        for(TransactionEntryEntity transactionEntryEntity : transactionEntryEntities) {
            total += transactionEntryEntity.getPrice() * transactionEntryEntity.getQuantity();
        }

        Optional<TransactionEntity> transactionEntity =
                this.transactionRepository.findById(this.transactionID);
        if (!transactionEntity.isPresent()) { // Transaction not found in db
            throw new NotFoundException("Transaction");
        }

        // Write, via an UPDATE, the new price total
        this.transactionRepository.save(transactionEntity.get().setTotal(total));

        return new Transaction(transactionEntity.get());
    }

    // Helper methods
    private void validateProperties() {
        if (this.transactionID.equals(null)) {
            throw new UnprocessableEntityException("transactionId");
        }
    }

    // Properties
    private UUID transactionID;
    public UUID getTransactionID() {return this.transactionID; }
    public UpdateTransactionPrice setTransactionId(final UUID transactionId) {
        this.transactionID = transactionId;
        return this;
    }

    @Autowired
    private TransactionEntryRepository transactionEntryRepository;

    @Autowired
    private TransactionRepository transactionRepository;
}
