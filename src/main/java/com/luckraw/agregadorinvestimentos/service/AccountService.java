package com.luckraw.agregadorinvestimentos.service;

import com.luckraw.agregadorinvestimentos.controller.dto.AccountStockResponseDTO;
import com.luckraw.agregadorinvestimentos.controller.dto.AssociateAccountStockDTO;
import com.luckraw.agregadorinvestimentos.entity.AccountStock;
import com.luckraw.agregadorinvestimentos.entity.AccountStockId;
import com.luckraw.agregadorinvestimentos.repository.AccountRepository;
import com.luckraw.agregadorinvestimentos.repository.AccountStockRepository;
import com.luckraw.agregadorinvestimentos.repository.StockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private StockRepository stockRepository;
    private AccountStockRepository accountStockRepository;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
    }

    public void associateStock(String accountId, AssociateAccountStockDTO associateAccountStockDTO) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(associateAccountStockDTO.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new AccountStockId(account.getAccountId(), stock.getStockId());
        var entity = new AccountStock(id,
                account,
                stock,
                associateAccountStockDTO.quantity());

        accountStockRepository.save(entity);

    }

    public List<AccountStockResponseDTO> listStocks(String accountId) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStocks()
                .stream()
                .map(accountStock -> new AccountStockResponseDTO(accountStock.getStock().getStockId(),
                        accountStock.getQuantity(),
                        0.0))
                .toList();
    }
}
