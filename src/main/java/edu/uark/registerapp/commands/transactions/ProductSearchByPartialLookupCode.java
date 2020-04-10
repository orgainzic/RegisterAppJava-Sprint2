package edu.uark.registerapp.commands.transactions;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.repositories.ProductRepository;

@Service
public class ProductSearchByPartialLookupCode implements ResultCommandInterface<Product[]> {
    @Override
    public Product[] execute() {
        this.validateProperties();

        return this.productRepository.findByLookupCodeContainingIgnoreCase(
                this.partialLookupCode
        ).stream().map(Product::new).toArray(Product[]::new);
    }

    // Helper methods
    private void validateProperties() {
        if (StringUtils.isBlank(this.partialLookupCode)) {
            throw new UnprocessableEntityException("partialLookupCode");
        }
    }

    // Properties
    private String partialLookupCode;
    public String getLookupCode() {
        return this.partialLookupCode;
    }
    public ProductSearchByPartialLookupCode setLookupCode(final String partialLookupCode) {
        this.partialLookupCode = partialLookupCode;
        return this;
    }

    @Autowired
    private ProductRepository productRepository;
}
