package de.upb.sede.requests;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ExecEnvironment {
    
    private final String composition;
    
    private final Map<String, Object> variables;

    public ExecEnvironment(String composition){
        this.composition = composition;
        this.variables = new HashMap<>();
    }


    public String getComposition(){
        return composition;
    }
    
    public Map<String, Object> getVariables(){
        return variables;
    }
}