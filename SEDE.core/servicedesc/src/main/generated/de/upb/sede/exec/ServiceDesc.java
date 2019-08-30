package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.upb.sede.IQualifiable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link IServiceDesc}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ServiceDesc.builder()}.
 */
@Generated(from = "IServiceDesc", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
public final class ServiceDesc implements IServiceDesc {
  private final List<IMethodDesc> methods;
  private final String qualifier;

  private ServiceDesc(List<IMethodDesc> methods, String qualifier) {
    this.methods = methods;
    this.qualifier = qualifier;
  }

  /**
   * @return The value of the {@code methods} attribute
   */
  @JsonProperty("methods")
  @Override
  public List<IMethodDesc> getMethods() {
    return methods;
  }

  /**
   * @return The value of the {@code qualifier} attribute
   */
  @JsonProperty("qualifier")
  @Override
  public String getQualifier() {
    return qualifier;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ServiceDesc#getMethods() methods}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceDesc withMethods(IMethodDesc... elements) {
    List<IMethodDesc> newValue = createUnmodifiableList(false, createSafeList(Arrays.asList(elements), true, false));
    return new ServiceDesc(newValue, this.qualifier);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ServiceDesc#getMethods() methods}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of methods elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceDesc withMethods(Iterable<? extends IMethodDesc> elements) {
    if (this.methods == elements) return this;
    List<IMethodDesc> newValue = createUnmodifiableList(false, createSafeList(elements, true, false));
    return new ServiceDesc(newValue, this.qualifier);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ServiceDesc#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final ServiceDesc withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new ServiceDesc(this.methods, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ServiceDesc} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ServiceDesc
        && equalTo((ServiceDesc) another);
  }

  private boolean equalTo(ServiceDesc another) {
    return methods.equals(another.methods)
        && qualifier.equals(another.qualifier);
  }

  /**
   * Computes a hash code from attributes: {@code methods}, {@code qualifier}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + methods.hashCode();
    h += (h << 5) + qualifier.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ServiceDesc} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "ServiceDesc{"
        + "methods=" + methods
        + ", qualifier=" + qualifier
        + "}";
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IServiceDesc", generator = "Immutables")
  @Deprecated
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IServiceDesc {
    List<IMethodDesc> methods = Collections.emptyList();
    String qualifier;
    @JsonProperty("methods")
    public void setMethods(List<IMethodDesc> methods) {
      this.methods = methods;
    }
    @JsonProperty("qualifier")
    public void setQualifier(String qualifier) {
      this.qualifier = qualifier;
    }
    @Override
    public List<IMethodDesc> getMethods() { throw new UnsupportedOperationException(); }
    @Override
    public String getQualifier() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static ServiceDesc fromJson(Json json) {
    ServiceDesc.Builder builder = ServiceDesc.builder();
    if (json.methods != null) {
      builder.addAllMethods(json.methods);
    }
    if (json.qualifier != null) {
      builder.qualifier(json.qualifier);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IServiceDesc} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ServiceDesc instance
   */
  static ServiceDesc copyOf(IServiceDesc instance) {
    if (instance instanceof ServiceDesc) {
      return (ServiceDesc) instance;
    }
    return ServiceDesc.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ServiceDesc ServiceDesc}.
   * <pre>
   * ServiceDesc.builder()
   *    .addMethods|addAllMethods(de.upb.sede.exec.IMethodDesc) // {@link ServiceDesc#getMethods() methods} elements
   *    .qualifier(String) // required {@link ServiceDesc#getQualifier() qualifier}
   *    .build();
   * </pre>
   * @return A new ServiceDesc builder
   */
  public static ServiceDesc.Builder builder() {
    return new ServiceDesc.Builder();
  }

  /**
   * Builds instances of type {@link ServiceDesc ServiceDesc}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IServiceDesc", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_QUALIFIER = 0x1L;
    private long initBits = 0x1L;

    private List<IMethodDesc> methods = new ArrayList<IMethodDesc>();
    private String qualifier;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code ServiceDesc} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(ServiceDesc instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Copy abstract value type {@code IServiceDesc} instance into builder.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(IServiceDesc instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.IQualifiable} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(IQualifiable instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof IServiceDesc) {
        IServiceDesc instance = (IServiceDesc) object;
        addAllMethods(instance.getMethods());
      }
      if (object instanceof IQualifiable) {
        IQualifiable instance = (IQualifiable) object;
        qualifier(instance.getQualifier());
      }
    }

    /**
     * Adds one element to {@link ServiceDesc#getMethods() methods} list.
     * @param element A methods element
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addMethods(IMethodDesc element) {
      this.methods.add(Objects.requireNonNull(element, "methods element"));
      return this;
    }

    /**
     * Adds elements to {@link ServiceDesc#getMethods() methods} list.
     * @param elements An array of methods elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addMethods(IMethodDesc... elements) {
      for (IMethodDesc element : elements) {
        this.methods.add(Objects.requireNonNull(element, "methods element"));
      }
      return this;
    }


    /**
     * Sets or replaces all elements for {@link ServiceDesc#getMethods() methods} list.
     * @param elements An iterable of methods elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder methods(Iterable<? extends IMethodDesc> elements) {
      this.methods.clear();
      return addAllMethods(elements);
    }

    /**
     * Adds elements to {@link ServiceDesc#getMethods() methods} list.
     * @param elements An iterable of methods elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addAllMethods(Iterable<? extends IMethodDesc> elements) {
      for (IMethodDesc element : elements) {
        this.methods.add(Objects.requireNonNull(element, "methods element"));
      }
      return this;
    }

    /**
     * Initializes the value for the {@link ServiceDesc#getQualifier() qualifier} attribute.
     * @param qualifier The value for qualifier 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder qualifier(String qualifier) {
      this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
      initBits &= ~INIT_BIT_QUALIFIER;
      return this;
    }

    /**
     * Builds a new {@link ServiceDesc ServiceDesc}.
     * @return An immutable instance of ServiceDesc
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ServiceDesc build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ServiceDesc(createUnmodifiableList(true, methods), qualifier);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      return "Cannot build ServiceDesc, some of required attributes are not set " + attributes;
    }
  }

  private static <T> List<T> createSafeList(Iterable<? extends T> iterable, boolean checkNulls, boolean skipNulls) {
    ArrayList<T> list;
    if (iterable instanceof Collection<?>) {
      int size = ((Collection<?>) iterable).size();
      if (size == 0) return Collections.emptyList();
      list = new ArrayList<>();
    } else {
      list = new ArrayList<>();
    }
    for (T element : iterable) {
      if (skipNulls && element == null) continue;
      if (checkNulls) Objects.requireNonNull(element, "element");
      list.add(element);
    }
    return list;
  }

  private static <T> List<T> createUnmodifiableList(boolean clone, List<T> list) {
    switch(list.size()) {
    case 0: return Collections.emptyList();
    case 1: return Collections.singletonList(list.get(0));
    default:
      if (clone) {
        return Collections.unmodifiableList(new ArrayList<>(list));
      } else {
        if (list instanceof ArrayList<?>) {
          ((ArrayList<?>) list).trimToSize();
        }
        return Collections.unmodifiableList(list);
      }
    }
  }
}
