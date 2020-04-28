package edu.uark.registerapp.commands.transactions;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Transaction;
import edu.uark.registerapp.models.entities.ProductEntity;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.ProductRepository;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;
import edu.uark.registerapp.models.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionSubmission implements ResultCommandInterface<Transaction> {
    @Transactional
    @Override
    public Transaction execute() {
        this.validateProperties();

        final Optional<TransactionEntity> transactionEntity =
                this.transactionRepository.findById(this.transactionId);
        if (!transactionEntity.isPresent()) { // No record with the associated record Id exists in the database
            throw new NotFoundException("Transaction");
        }

        this.createApiTransaction(transactionEntity.get());

        // Synchronize any incoming changes for UPDATE to the database.
        this.apiTransaction = transactionEntity.get().synchronize(this.apiTransaction);

        // Write, via an UPDATE, any changes to database.
        this.transactionRepository.save(transactionEntity.get());

        // Update Product Counts in database
        this.updateProductQuantities();

        return this.apiTransaction;
    }

    // Helper methods
    private void validateProperties() {
        if (this.transactionId.equals(null)) {
            throw new UnprocessableEntityException("transactionId");
        }
    }

    private void updateProductQuantities() {
        for (TransactionEntryEntity transactionEntryEntity :
        this.transactionEntryRepository.findByTransactionId(this.transactionId)) {
            Optional<ProductEntity> productEntity =
                    this.productRepository.findById(transactionEntryEntity.getProductId());
            if (!productEntity.isPresent()) { // No record with the associated record Id exists in the database
                throw new NotFoundException("Product");
            }
            productEntity.get().setCount(productEntity.get().getCount() - (int)(transactionEntryEntity.getQuantity()));

            this.productRepository.save(productEntity.get());
        }
    }

    private void createApiTransaction(final TransactionEntity transactionEntity) {
        this.apiTransaction = new Transaction(transactionEntity);
        long transactionTotal = 0L;
        for (TransactionEntryEntity transactionEntryEntity :
                this.transactionEntryRepository.findByTransactionId(this.transactionId)) {
            transactionTotal += (transactionEntryEntity.getPrice() * transactionEntryEntity.getQuantity());
        }
        this.apiTransaction.setTotal(transactionTotal);
    }

    // Properties
    private UUID transactionId;
    public UUID getTransactionId() { return this.transactionId; }
    public TransactionSubmission setTransactionId(final UUID transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    private Transaction apiTransaction;
    public Transaction getApiTransaction() { return this.apiTransaction; }
    public TransactionSubmission setApiTransaction(final Transaction apiTransaction) {
        this.apiTransaction = apiTransaction;
        return this;
    }

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionEntryRepository transactionEntryRepository;

    @Autowired
    private ProductRepository productRepository;
}