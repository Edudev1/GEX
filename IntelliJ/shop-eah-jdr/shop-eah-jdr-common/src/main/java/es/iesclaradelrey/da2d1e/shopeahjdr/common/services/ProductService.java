package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.web.NewProductsDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.xml.stream.XMLStreamException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Product save(Product product);
    Optional<Product> findById(Long id);
    Product createNew(NewProductsDto newProductsDto);
    Product update(Long productId, NewProductsDto newProductsDto);

    List<Product> findByCategory(Long categoryId);

    String exportAllStax() throws XMLStreamException;

    void importProductsStax(InputStream productsStream) throws XMLStreamException;
    List<Product> findByCategoryId(Long categoryId);
    //void deleteById(Long id);
    boolean existsByProductName(String productName);

    Page<Product> searchProducts(String text, Double maxPrice, Long brandId, Long categoryId, Pageable pageable);
}
