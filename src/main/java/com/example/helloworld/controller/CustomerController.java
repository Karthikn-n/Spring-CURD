package com.example.helloworld.controller;

import com.example.helloworld.constants.ApiResponse;
import com.example.helloworld.dto.EncryptedRequest;
import com.example.helloworld.model.Customer;
import com.example.helloworld.repository.CustomerRepository;
import com.example.helloworld.util.AESUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    ///  Instance of the Customer Repository that extends the JPA Repository
    private final CustomerRepository customerRepository;

    private final ObjectMapper mapper;

    /// Constructor injection is used here we can do this on using Autowired like above
    /// Spring will handle this for us
    public CustomerController(CustomerRepository customerRepository, ObjectMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addCustomer(@RequestBody EncryptedRequest request) {
        try{
            String decryptedJson = AESUtil.decrypt(request.getData());

            // Convert decrypted JSON to Customer object
            ObjectMapper mapper = new ObjectMapper();
            Customer customer = mapper.readValue(decryptedJson, Customer.class);

            customerRepository.save(customer);

            Map<String, Object> result= Map.of(
                "status", "ok",
                "data", customer
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new RuntimeException("Internal error fetching customer", e);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllCustomers() {
        try{
            List<Customer> customers = customerRepository.findAll();
            /// It will help as to read or write the JSON type from POJO class
            ObjectMapper mapper = new ObjectMapper();
            // Convert the JSON objects into String
            String jsonData = mapper.writeValueAsString(customers);
            // Encrypt the JSON string
            String encryptedData = AESUtil.encrypt(jsonData);
            // Put encrypted string into a Map
            Map<String, Object> responseMap = Map.of(
                "data", encryptedData,
                "code", HttpStatus.OK.value(),
                "status", "ok"
            );
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("error", "Failed to fetch and encrypt customers.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
        }
    }

    // Delete the Customer based on their ID
    @DeleteMapping("/delete")
    public String deleteCustomer(@RequestParam String data) {
        try {
            String decryptedJson = AESUtil.decrypt(data);

            JsonNode jsonNode = mapper.readTree(decryptedJson);
            Long id = jsonNode.get("id").asLong();
            if (customerRepository.existsById(id)) {
                customerRepository.deleteById(id);
                return "Customer with ID " + id + " has been deleted successfully.";
            } else {
                return "Customer with ID " + id + " does not exist.";
            }
        } catch (Exception e) {
            return "Failed to delete customer" + ". Error: " + e.getMessage();
        }
    }

    /// Add list of Customers from the Postman
    @PostMapping("/addCustomers")
    public ResponseEntity<String> addCustomers(@RequestBody EncryptedRequest request) {
        try {
            String decryptedJson = AESUtil.decrypt(request.getData());
            List<Customer> customers = mapper.readValue(decryptedJson, new TypeReference<List<Customer>>() {
            });
            customerRepository.saveAll(customers);
            // save customers to database if needed
            return ResponseEntity.status(HttpStatus.CREATED).body("Customers added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Decryption error: " + e.getMessage());
        }
    }
    @PostMapping("/encrypt")
    public ResponseEntity<String> encryptData(@RequestBody List<Customer> customers) {
        try {
            String json = mapper.writeValueAsString(customers);
            String encrypted = AESUtil.encrypt(json);
            return ResponseEntity.ok(encrypted);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Encryption error: " + e.getMessage());
        }
    }

    @PostMapping(value = "/decrypt")
    public ResponseEntity<String> decryptData(@RequestBody String encryptedData) {
        try {
            String decryptedData = AESUtil.decrypt(encryptedData);
            return ResponseEntity.ok(decryptedData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Decryption Error: " + e.getMessage());
        }
    }
}
