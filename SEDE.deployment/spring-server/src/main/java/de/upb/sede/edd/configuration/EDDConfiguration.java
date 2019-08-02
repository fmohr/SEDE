package de.upb.sede.edd.configuration;

import de.upb.sede.edd.EDD;
import de.upb.sede.edd.EDDHome;
import de.upb.sede.edd.EDDVersion;
import de.upb.sede.edd.LocalEDDInfo;
import de.upb.sede.edd.ctrl.ServiceRequirementCtrl;
import de.upb.sede.edd.deploy.deplengine.DeplEngineRegistry;
import de.upb.sede.edd.deploy.specsrc.SpecSourceRegistry;
import de.upb.sede.edd.runtime.LocalRuntimeRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EDDConfiguration {

    @Bean
    public EDD edd() {
        return EDD.getInstance();
    }

    @Bean
    EDDHome eddHome() {
        return edd().getHome();
    }

    @Bean
    EDDVersion eddVersion() {
        return edd().getVersion();
    }

    @Bean
    DeplEngineRegistry deplEngineRegistry() {
        return edd().getDeploymentEngine();
    }

    @Bean
    LocalEDDInfo localEDDInfo() {
        return edd().getInfo();
    }

    @Bean
    LocalRuntimeRegistry localRuntimeRegistry() {
        return edd().getRuntimeRegistry();
    }

    @Bean
    SpecSourceRegistry specSourceRegistry() {
        return edd().getSpecSrc();
    }

    @Bean
    ServiceRequirementCtrl serviceRequirementCtrl() {
        return new ServiceRequirementCtrl();
    }

}
