package com.dm.asde.repository;

import com.dm.asde.model.DmCase;
import com.dm.asde.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by yarielinfante on 3/28/17.
 */
public interface CaseRepository extends PagingAndSortingRepository<DmCase, Long> {

    Page<DmCase> findByUser(User user, Pageable pageable);
}
