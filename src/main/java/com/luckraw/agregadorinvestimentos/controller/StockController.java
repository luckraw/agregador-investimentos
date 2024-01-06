package com.luckraw.agregadorinvestimentos.controller;

import com.luckraw.agregadorinvestimentos.controller.dto.CreateStockDTO;
import com.luckraw.agregadorinvestimentos.controller.dto.CreateUserDTO;
import com.luckraw.agregadorinvestimentos.entity.User;
import com.luckraw.agregadorinvestimentos.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/stocks")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody CreateStockDTO createStockDTO) {

        stockService.createStock(createStockDTO);

        return ResponseEntity.ok().build();
    }
}
