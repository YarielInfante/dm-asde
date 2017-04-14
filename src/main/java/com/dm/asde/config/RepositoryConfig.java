package com.dm.asde.config;

import com.dm.asde.model.CaseType;
import com.dm.asde.model.DmCase;
import com.dm.asde.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

/**
 * Created by yarielinfante on 4/1/17.
 */
@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(DmCase.class);
        config.exposeIdsFor(CaseType.class);
        config.exposeIdsFor(User.class);
    }
}
