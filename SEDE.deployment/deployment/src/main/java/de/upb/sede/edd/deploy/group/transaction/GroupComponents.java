package de.upb.sede.edd.deploy.group.transaction;

import de.upb.sede.edd.deploy.target.JExecutorTarget;

public interface GroupComponents {

    JExecutorTarget getJavaExecutor(String spec);

}
