package org.cryptonit.cloud;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;

/**
 * @author Mathias Brossard
 */
@ApplicationPath("/")
public class Application extends javax.ws.rs.core.Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<>();
        s.add(Index.class);
        return s;
    }	
}
