package de.upb.sede.requests;

import java.util.Map;

public abstract class Body {
       public abstract String getComposition();
       public abstract Map<String, Object> getVariables();
}