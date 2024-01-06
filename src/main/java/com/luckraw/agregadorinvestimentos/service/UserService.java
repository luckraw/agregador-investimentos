package com.luckraw.agregadorinvestimentos.service;

import com.luckraw.agregadorinvestimentos.controller.dto.AccountResponseDTO;
import com.luckraw.agregadorinvestimentos.controller.dto.CreateAccountDTO;
import com.luckraw.agregadorinvestimentos.controller.dto.CreateUserDTO;
import com.luckraw.agregadorinvestimentos.controller.dto.UpdateUserDTO;
import com.luckraw.agregadorinvestimentos.entity.Account;
import com.luckraw.agregadorinvestimentos.entity.BillingAddress;
import com.luckraw.agregadorinvestimentos.entity.User;
import com.luckraw.agregadorinvestimentos.repository.AccountRepository;
import com.luckraw.agregadorinvestimentos.repository.BillingAddressRepository;
import com.luckraw.agregadorinvestimentos.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    private AccountRepository accountRepository;

    private BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository,
                       BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
    }

    public UUID createUser(CreateUserDTO createUserDTO) {
        var entity = new User(UUID.randomUUID(),
                createUserDTO.username(),
                createUserDTO.email(),
                createUserDTO.password(),
                Instant.now(),
                null);
        var userSaved = userRepository.save(entity);

        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void updateUserById(String userId, UpdateUserDTO updateUserDTO) {
        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDTO.username() != null) {
                user.setUserName(updateUserDTO.username());
            }

            if (updateUserDTO.password() != null) {
                user.setPassword(updateUserDTO.password());
            }

            userRepository.save(user);
        }


    }

    public void deleteById(String userId) {
        var id = UUID.fromString(userId);

        var userExist = userRepository.existsById(id);

        if (userExist) {
            userRepository.deleteById(id);
        }
    }

    public void createAccount(String userId, CreateAccountDTO createAccountDTO) {

        var user =userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var account = new Account(
                UUID.randomUUID(),
                createAccountDTO.description(),
                user,
                null,
                new ArrayList<>()
        );

        var accountCreated = accountRepository.save(account);

        var billingAddress = new BillingAddress(
                accountCreated.getAccountId(),
                account,
                createAccountDTO.street(),
                createAccountDTO.number()
        );

        billingAddressRepository.save(billingAddress);
    }

    public List<AccountResponseDTO> listAccounts(String userId) {
        var user =userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccounts()
                .stream()
                .map(account -> new AccountResponseDTO(
                        account.getAccountId().toString(),
                        account.getDescription()
                )).toList();
    }
}
