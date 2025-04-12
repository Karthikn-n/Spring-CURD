package com.example.helloworld.controller;

import com.example.helloworld.constants.ApiResponse;
import com.example.helloworld.dto.EncryptedRequest;
import com.example.helloworld.model.Customer;
import com.example.helloworld.repository.CustomerRepository;
import com.example.helloworld.util.AESUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerRepository customerRepository;
    //    @Autowired
    //    private UserRepository userRepository;

    // Constructor injection is used here we can do this on using Autowired like above
    // Spring will handle this for us
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Customer>> addCustomer(@RequestBody EncryptedRequest request) {
        try{
            String decryptedJson = AESUtil.decrypt(request.getData());

            // Convert decrypted JSON to Customer object
            ObjectMapper mapper = new ObjectMapper();
            Customer customer = mapper.readValue(decryptedJson, Customer.class);

            customerRepository.save(customer);

//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body(ApiResponse.success("Customer saved", HttpStatus.CREATED.value()));
//            customerRepository.save(customer);
//            ApiResponse<Customer> successResponse = ApiResponse.success(
//                    "Customer added successfully",
//                    HttpStatus.CREATED.value() // Use 201 Created for successful resource creation
//            );
            return ResponseEntity.ok(ApiResponse.success("Customer added successfully", HttpStatus.CREATED.value(), customer));
        } catch (Exception e) {
            throw new RuntimeException("Internal error fetching customer", e);
        }
    }

    @PostMapping("/encrypt")
    public ResponseEntity<Map<String, String>> encryptCustomer(@RequestBody Customer customer) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String customerJson = mapper.writeValueAsString(customer);
            String encrypted = AESUtil.encrypt(customerJson);

            Map<String, String> response = new HashMap<>();
            response.put("data", encrypted);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Encryption failed: " + e.getMessage()));
        }
    }

    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Delete the Customer based on their ID
    @DeleteMapping("/delete")
    public String deleteCustomer(@RequestParam long id) {
        try {
            if (customerRepository.existsById(id)) {
                customerRepository.deleteById(id);
                return "Customer with ID " + id + " has been deleted successfully.";
            } else {
                return "Customer with ID " + id + " does not exist.";
            }
        } catch (Exception e) {
            return "Failed to delete customer with ID " + id + ". Error: " + e.getMessage();
        }
    }

    // Add list of Customers from the Postman
    @PostMapping("/addCustomers")
    public ResponseEntity<ApiResponse<Void>> addCustomers(@RequestBody EncryptedRequest request) {
        try {
            String decryptedJson = AESUtil.decrypt(request.getData());

            ObjectMapper mapper = new ObjectMapper();

            List<Customer> customers = Arrays.asList(mapper.readValue(decryptedJson, Customer[].class));

            customerRepository.saveAll(customers);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Customers added successfully", HttpStatus.CREATED.value()));
        } catch (Exception e) {
            throw new RuntimeException("Internal error saving customers", e);
        }
    }
}
