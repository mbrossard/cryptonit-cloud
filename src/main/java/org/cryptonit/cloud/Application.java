package org.cryptonit.cloud;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;

/**
 * @author Mathias Brossard
 */
@ApplicationPath("/")
public class Application extends javax.ws.rs.core.Application {
    final static private Set<Class<?>> classSet = new HashSet<>();

    public static void addClass(Class c) {
        synchronized(classSet) {
            classSet.add(c);
        }
    }
    
    @Override
    public Set<Class<?>> getClasses() {
        return classSet;
    }
}
