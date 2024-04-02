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
import com.lemonchat.entities.Post;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestDataSourceConfig.class)
public class PostRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void testFindByName() {
    	Post post = new Post();
    	Account account = new Account(null,"testusername","test@test.com");
    	account = entityManager.persistFlushFind(account);
    	post.setAccount(account);
    	post.setTopic("test topic");
        Post savedEntity = entityManager.persistFlushFind(post);
        Post foundEntity = postRepository.findById(1L).get();
        
        assertThat(foundEntity.getUsername()).isEqualTo(savedEntity.getUsername());
    }
}
