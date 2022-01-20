package com.codewithabdel.batch;

import com.codewithabdel.batch.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ProductItemProcessor implements ItemProcessor<Product, Product> {
    private static final Logger log = LoggerFactory.getLogger(ProductItemProcessor.class);

    @Override
    public Product process(Product product) throws Exception {
        final String name = product.getName().toUpperCase();
        final double price = product.getPrice();
        final int qty = product.getQty();
        final String description = product.getDescription().toLowerCase();
        final double total = price * qty;
        final Product productProcessed = new Product(name, qty, price, description, total);
        log.info("Calcul total of: "+ product+" result --> "+productProcessed);
        return productProcessed;
    }
}
