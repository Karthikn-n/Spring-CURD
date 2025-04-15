package com.example.helloworld.repository;

import com.example.helloworld.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**  Used to interact with database by providing some methods instead of writing
 * boilerplate code
 * You get built-in methods like:
 * save(Customer customer) – Save or update a customer,
 * findById(Long id) – Find a customer by ID,
 * findAll() – Get all customers,
 * deleteById(Long id) – Delete a customer by ID,
 * count() – Count total number of customers,
 * existsById(Long id) – Check if a customer exists,
 * We can add more custom queries to fetch the data from the Data JPA will automatically
 * converts it into SQL while using this Keywords
 * <a href="https://www.notion.so/Spring-1d07421e44ee808b927fc53fbee027c5?pvs=4#1d67421e44ee803ab1e6c379ad32ea1e">Keywords for Custom Queries</a>
*/
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
