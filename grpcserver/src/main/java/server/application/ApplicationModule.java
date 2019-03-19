package server.application;

import com.google.inject.AbstractModule;
import service.AccountService;
import service.impl.AccountServiceImpl;

public class ApplicationModule extends AbstractModule {

    @Override
    public void configure() {
        bind(AccountService.class).to(AccountServiceImpl.class);
    }
}
