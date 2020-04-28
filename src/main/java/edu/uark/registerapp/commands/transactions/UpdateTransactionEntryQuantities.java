package edu.uark.registerapp.commands.transactions;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.TransactionEntry;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UpdateTransactionEntryQuantities implements ResultCommandInterface<TransactionEntry[]> {
    @Transactional
    @Override
    public TransactionEntry[] execute() {

        this.validateProperties();

        for (TransactionEntry transactionEntry : this.transactionEntries) {
            Optional<TransactionEntryEntity> transactionEntryEntity =
                    this.transactionEntryRepository.findByTransactionIdAndProductId(
                            transactionEntry.getTransactionId(),
                            transactionEntry.getProductId());
            if (!transactionEntryEntity.isPresent()) { // No record with the associated record Id was in database
                throw new NotFoundException("TransactionEntry");
            }

            // Update quantity
            transactionEntryEntity.get().setQuantity(transactionEntry.getQuantity());

            // Write, via an UPDATE, any changes to the database
            this.transactionEntryRepository.save(transactionEntryEntity.get());
        }

        return this.transactionEntries;
    }

    // Helper Methods
    private void validateProperties() {
        if (this.transactionEntries.equals(null)) {
            throw new UnprocessableEntityException("transactionEntries");
        }
    }

    // Properties

    // TODO: transactionEntries may need to be of List<> type...
    private TransactionEntry[] transactionEntries;
    public TransactionEntry[] getTransactionEntries() { return this.transactionEntries; }
    public UpdateTransactionEntryQuantities setTransactionEntries(final TransactionEntry[] transactionEntries) {
        this.transactionEntries = transactionEntries;
        return this;
    }


    @Autowired
    private TransactionEntryRepository transactionEntryRepository;
}
