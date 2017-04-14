package com.dm.asde.repository;

import com.dm.asde.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by yarielinfante on 3/26/17.
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByEmail(String email);
}
