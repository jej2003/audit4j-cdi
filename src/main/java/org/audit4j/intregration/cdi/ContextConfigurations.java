package org.audit4j.intregration.cdi;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@ManagedBean
public class ContextConfigurations {

    @InjectedProperty(key = "audit.handlers", mandatory = true)
    String handlers;

    @PostConstruct
    public void init() {

    }

    @PreDestroy
    public void stop() {

    }
}
