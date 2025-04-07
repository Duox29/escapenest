package com.duox.escapenest.mapper;

import com.duox.escapenest.dto.response.AccountResponse;
import com.duox.escapenest.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResponse toAccountResponse(Account account);
}
