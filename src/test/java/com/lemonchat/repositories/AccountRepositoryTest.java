package com.lemonchat.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lemonchat.TestDataSourceConfig;
import com.lemonchat.entities.Account;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestDataSourceConfig.class)
public class AccountRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testFindByName() {
    	Account account = new Account(null, "testusername", "test@test.com");
        Account savedEntity = entityManager.persistFlushFind(account);
        Account foundEntity = accountRepository.findByUsername("testusername").get();
        
        assertThat(foundEntity.getUsername()).isEqualTo(savedEntity.getUsername());
    }
}
