package de.upb.sede.edd.deploy.target.components;

import de.upb.sede.edd.SEDECodeBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;

public class SEDECodeBaseComponent extends TargetComponent {

    private final static Logger logger = LoggerFactory.getLogger(SEDECodeBaseComponent.class);

    private SEDECodeBase codeBase;

    public SEDECodeBaseComponent(String displayName, SEDECodeBase codeBase) {
        this.codeBase = codeBase;
        setDisplayName(displayName);
    }

    public void deploy(boolean update, Executor executor) {
        logger.info("{}: fetching sede code base.", getDisplayName());
        boolean updated = this.codeBase.retrieve(update);
        if(updated && update) {
            logger.info("{}: new updates in sede code base. building sede", getDisplayName());
            this.codeBase.buildSEDE(executor);
        }
    }

    public SEDECodeBase getCodeBase() {
        return codeBase;
    }
}
