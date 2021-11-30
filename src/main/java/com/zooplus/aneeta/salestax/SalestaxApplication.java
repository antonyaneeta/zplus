package com.zooplus.aneeta.salestax;

import com.zooplus.aneeta.salestax.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SalestaxApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SalestaxApplication.class, args);
    }

    @Autowired
    private SalesService salesService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hey !!! ");
        salesService.doBilling();

    }
}
