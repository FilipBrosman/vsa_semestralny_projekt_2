package sk.stuba.fei.uim.vsa.pr2;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import sk.stuba.fei.uim.vsa.pr2.web.CarParkResource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class Project2Application extends Application {
    static final Set<Class<?>> appClasses = new HashSet<>();

    static{
        appClasses.add(CarParkResource.class);
    }

    @Override
    public Set<Class<?>> getClasses(){ return appClasses;}

}
