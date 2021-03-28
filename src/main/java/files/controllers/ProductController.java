package files.controllers;

import files.entity.Product;
import files.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping
    public String index(Model model) {
        List<Product> productList = productRepository.findAll();
        model.addAttribute("products", productList);
        model.addAttribute("string", "Products");
        return "index";
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id).get();
    }

    @DeleteMapping("/delete")
    public String deleteProduct(@RequestParam(name = "id") Long id){
        productRepository.deleteById(id);
        return "redirect:/products";
    }

    @PostMapping
    public Product save(@RequestBody Product product) {
        return productRepository.save(product);
    }

    //    http://localhost:8080/app/products/search_by_min_price_and_max_price?minPrice=300&maxPrice=600
    @GetMapping("/search_by_min_price_and_max_price")
    public List<Product> searchByMinPriceAndMaxPrice(@RequestParam Integer minPrice, @RequestParam Integer maxPrice) {
        return productRepository.findAllByPriceIsBetween(minPrice, maxPrice);
    }

    //    http://localhost:8080/app/products/search_by_min_price?minPrice=300
    @GetMapping("/search_by_min_price")
    public List<Product> searchByMinPrice(@RequestParam Integer minPrice) {
        return productRepository.findAllByPriceGreaterThanEqual(minPrice);
    }

    //    http://localhost:8080/app/products/search_by_max_price?maxPrice=600
    @GetMapping("/search_by_max_price")
    public List<Product> searchByMaxPrice(@RequestParam Integer maxPrice) {
        return productRepository.findAllByPriceIsLessThanEqual(maxPrice);
    }

    @GetMapping("/search_by_title")
    public Product searchByTitle(@RequestParam String title) {
        return productRepository.findByTitle(title).get();
    }
}
