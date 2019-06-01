package de.upb.sede.edd.deploy.specification;

import de.upb.sede.util.Kneadable;
import de.upb.sede.util.KneadableField;

import java.util.ArrayList;
import java.util.List;

public class DeploymentMethod {

    private String method = "";

    private List<KneadableField> sources    =   new ArrayList<>();

    private List<KneadableField> builds     =   new ArrayList<>();

    private List<KneadableField> deployments = new ArrayList<>();

    private List<KneadableField> actions = new ArrayList<>();


}
