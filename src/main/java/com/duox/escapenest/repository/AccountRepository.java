package com.duox.escapenest.repository;

import com.duox.escapenest.entity.Account;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    boolean existsAccountByEmail(String email);
    Optional<Account> findAccountsByEmail(String email);
}
