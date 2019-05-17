package de.upb.sede.util;

import java.util.List;

public interface Kneadable {

    <T> T knead(Class<T> form);

}
