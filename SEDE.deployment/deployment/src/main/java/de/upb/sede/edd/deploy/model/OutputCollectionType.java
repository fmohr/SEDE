package de.upb.sede.edd.deploy.model;


import de.upb.sede.util.OptionalField;

import java.util.Optional;

public class OutputCollectionType {


    OptionalField<String> type = OptionalField.empty();

    public Optional<String> getType() {
        return type.opt();
    }

    public void setType(String type) {
        this.type = OptionalField.of(type);
    }

}
