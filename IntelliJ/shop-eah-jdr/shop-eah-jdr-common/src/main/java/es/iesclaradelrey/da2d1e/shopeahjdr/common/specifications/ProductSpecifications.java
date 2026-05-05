package es.iesclaradelrey.da2d1e.shopeahjdr.common.specifications;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> nameOrDescriptionContains(String text) {
        return (root, query, cb) -> {
            if (text == null || text.isBlank()) return cb.conjunction();

            String pattern = "%" + text.toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("name")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern)
            );
        };
    }

    public static Specification<Product> priceLessThanOrEqual(Double maxPrice) {
        return (root, query, cb) -> {
            if (maxPrice == null) return cb.conjunction();
            return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }

    public static Specification<Product> hasBrand(Long brandId) {
        return (root, query, cb) -> {
            if (brandId == null) return cb.conjunction();
            return cb.equal(root.get("brand").get("id"), brandId);
        };
    }

    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) return cb.conjunction();

            query.distinct(true);
            return cb.equal(root.join("categories").get("id"), categoryId);
        };
    }

    private ProductSpecifications() {
    }
}