package com.btbatux.account.repository;

import com.btbatux.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Accountepository extends JpaRepository<Account, String> {


}
