package com.luckraw.agregadorinvestimentos.controller;

import com.luckraw.agregadorinvestimentos.controller.dto.AccountStockResponseDTO;
import com.luckraw.agregadorinvestimentos.controller.dto.AssociateAccountStockDTO;
import com.luckraw.agregadorinvestimentos.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId, @RequestBody AssociateAccountStockDTO associateAccountStockDTO) {
        accountService.associateStock(accountId, associateAccountStockDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDTO>> associateStock(@PathVariable("accountId") String accountId) {

        var stock = accountService.listStocks(accountId);

        return ResponseEntity.ok(stock);
    }
}
