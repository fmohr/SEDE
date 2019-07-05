package de.upb.sede.edd.deploy.app;

import de.upb.sede.edd.EDDHome;
import de.upb.sede.edd.deploy.target.JExecutorTarget;
import de.upb.sede.util.LazyAccessCache;

import java.util.ArrayList;
import java.util.List;

public class AppRepository {

    private List<App> apps = new ArrayList<>();

    private  EDDHome home;

    public AppRepository(EDDHome home) {
        this.home = home;
    }

    public LocalJavaExecutorApp getNewJavaExecutor() {
        LocalJavaExecutorApp app = new LocalJavaExecutorApp(home);
        return app;
    }

    public AppDemand getCreator() {
        return new AppDemand();
    }

    public void run(AppDemand demand) {
        if(demand.localJava.isSet()) {
            demand.localJava.get();
        }
    }

    public class AppDemand {

        private LazyAccessCache<LocalJavaExecutorApp> localJava
            = new LazyAccessCache<>(AppRepository.this::getNewJavaExecutor);


        public JExecutorTarget getJavaExecutor() {
            return localJava.access().getJavaExecutor();
        }
    }
}
