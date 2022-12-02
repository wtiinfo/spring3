package com.wtiinfo;

import com.wtiinfo.model.Customer;
import com.wtiinfo.repository.CustomerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {

    @Autowired
    private CustomerRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return repository.findAll();
    }

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest newCustomer) {
        Customer customer = new Customer();
        customer.setName(newCustomer.name());
        customer.setEmail(newCustomer.email());
        customer.setAge(newCustomer.age());
        repository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id) {
        repository.deleteById(id);
    }

    record NewCustomerRequest(String name, String email, Integer age) {
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer id,
    @RequestBody NewCustomerRequest customerRequest) {
        Customer c = repository.findById(id).orElseThrow();
        c.setAge(customerRequest.age());
        c.setEmail(customerRequest.email());
        c.setName(customerRequest.name());
        repository.saveAndFlush(c);
    }
}
