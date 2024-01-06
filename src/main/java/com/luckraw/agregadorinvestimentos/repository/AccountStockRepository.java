package com.luckraw.agregadorinvestimentos.repository;

import com.luckraw.agregadorinvestimentos.entity.AccountStock;
import com.luckraw.agregadorinvestimentos.entity.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
