package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.exec.aux.IJavaParameterizationAux;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link IServiceParameterizationDesc}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ServiceParameterizationDesc.builder()}.
 */
@Generated(from = "IServiceParameterizationDesc", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ServiceParameterizationDesc implements IServiceParameterizationDesc {
  private final ImmutableList<IServiceParameterDesc> serviceParameterDesc;
  private final @Nullable IJavaParameterizationAux javaParameterizationAuxiliaries;

  private ServiceParameterizationDesc(
      ImmutableList<IServiceParameterDesc> serviceParameterDesc,
      @Nullable IJavaParameterizationAux javaParameterizationAuxiliaries) {
    this.serviceParameterDesc = serviceParameterDesc;
    this.javaParameterizationAuxiliaries = javaParameterizationAuxiliaries;
  }

  /**
   * @return The value of the {@code serviceParameterDesc} attribute
   */
  @JsonProperty("serviceParameterDesc")
  @Override
  public ImmutableList<IServiceParameterDesc> getServiceParameterDesc() {
    return serviceParameterDesc;
  }

  /**
   * @return The value of the {@code javaParameterizationAuxiliaries} attribute
   */
  @JsonProperty("javaParameterizationAuxiliaries")
  @Override
  public @Nullable IJavaParameterizationAux getJavaParameterizationAuxiliaries() {
    return javaParameterizationAuxiliaries;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IServiceParameterizationDesc#getServiceParameterDesc() serviceParameterDesc}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceParameterizationDesc withServiceParameterDesc(IServiceParameterDesc... elements) {
    ImmutableList<IServiceParameterDesc> newValue = ImmutableList.copyOf(elements);
    return new ServiceParameterizationDesc(newValue, this.javaParameterizationAuxiliaries);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IServiceParameterizationDesc#getServiceParameterDesc() serviceParameterDesc}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of serviceParameterDesc elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceParameterizationDesc withServiceParameterDesc(Iterable<? extends IServiceParameterDesc> elements) {
    if (this.serviceParameterDesc == elements) return this;
    ImmutableList<IServiceParameterDesc> newValue = ImmutableList.copyOf(elements);
    return new ServiceParameterizationDesc(newValue, this.javaParameterizationAuxiliaries);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IServiceParameterizationDesc#getJavaParameterizationAuxiliaries() javaParameterizationAuxiliaries} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for javaParameterizationAuxiliaries (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final ServiceParameterizationDesc withJavaParameterizationAuxiliaries(@Nullable IJavaParameterizationAux value) {
    if (this.javaParameterizationAuxiliaries == value) return this;
    return new ServiceParameterizationDesc(this.serviceParameterDesc, value);
  }

  /**
   * This instance is equal to all instances of {@code ServiceParameterizationDesc} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ServiceParameterizationDesc
        && equalTo((ServiceParameterizationDesc) another);
  }

  private boolean equalTo(ServiceParameterizationDesc another) {
    return serviceParameterDesc.equals(another.serviceParameterDesc)
        && Objects.equals(javaParameterizationAuxiliaries, another.javaParameterizationAuxiliaries);
  }

  /**
   * Computes a hash code from attributes: {@code serviceParameterDesc}, {@code javaParameterizationAuxiliaries}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + serviceParameterDesc.hashCode();
    h += (h << 5) + Objects.hashCode(javaParameterizationAuxiliaries);
    return h;
  }

  /**
   * Prints the immutable value {@code ServiceParameterizationDesc} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ServiceParameterizationDesc")
        .omitNullValues()
        .add("serviceParameterDesc", serviceParameterDesc)
        .add("javaParameterizationAuxiliaries", javaParameterizationAuxiliaries)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IServiceParameterizationDesc", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IServiceParameterizationDesc {
    @Nullable List<IServiceParameterDesc> serviceParameterDesc = ImmutableList.of();
    @Nullable IJavaParameterizationAux javaParameterizationAuxiliaries;
    @JsonProperty("serviceParameterDesc")
    public void setServiceParameterDesc(List<IServiceParameterDesc> serviceParameterDesc) {
      this.serviceParameterDesc = serviceParameterDesc;
    }
    @JsonProperty("javaParameterizationAuxiliaries")
    public void setJavaParameterizationAuxiliaries(@Nullable IJavaParameterizationAux javaParameterizationAuxiliaries) {
      this.javaParameterizationAuxiliaries = javaParameterizationAuxiliaries;
    }
    @Override
    public List<IServiceParameterDesc> getServiceParameterDesc() { throw new UnsupportedOperationException(); }
    @Override
    public IJavaParameterizationAux getJavaParameterizationAuxiliaries() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static ServiceParameterizationDesc fromJson(Json json) {
    ServiceParameterizationDesc.Builder builder = ServiceParameterizationDesc.builder();
    if (json.serviceParameterDesc != null) {
      builder.addAllServiceParameterDesc(json.serviceParameterDesc);
    }
    if (json.javaParameterizationAuxiliaries != null) {
      builder.javaParameterizationAuxiliaries(json.javaParameterizationAuxiliaries);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IServiceParameterizationDesc} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ServiceParameterizationDesc instance
   */
  public static ServiceParameterizationDesc copyOf(IServiceParameterizationDesc instance) {
    if (instance instanceof ServiceParameterizationDesc) {
      return (ServiceParameterizationDesc) instance;
    }
    return ServiceParameterizationDesc.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ServiceParameterizationDesc ServiceParameterizationDesc}.
   * <pre>
   * ServiceParameterizationDesc.builder()
   *    .addServiceParameterDesc|addAllServiceParameterDesc(de.upb.sede.exec.IServiceParameterDesc) // {@link IServiceParameterizationDesc#getServiceParameterDesc() serviceParameterDesc} elements
   *    .javaParameterizationAuxiliaries(de.upb.sede.exec.aux.IJavaParameterizationAux | null) // nullable {@link IServiceParameterizationDesc#getJavaParameterizationAuxiliaries() javaParameterizationAuxiliaries}
   *    .build();
   * </pre>
   * @return A new ServiceParameterizationDesc builder
   */
  public static ServiceParameterizationDesc.Builder builder() {
    return new ServiceParameterizationDesc.Builder();
  }

  /**
   * Builds instances of type {@link ServiceParameterizationDesc ServiceParameterizationDesc}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IServiceParameterizationDesc", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private ImmutableList.Builder<IServiceParameterDesc> serviceParameterDesc = ImmutableList.builder();
    private @Nullable IJavaParameterizationAux javaParameterizationAuxiliaries;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableServiceParameterizationDesc} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableServiceParameterizationDesc instance) {
      Objects.requireNonNull(instance, "instance");
      addAllServiceParameterDesc(instance.getServiceParameterDesc());
      @Nullable IJavaParameterizationAux javaParameterizationAuxiliariesValue = instance.getJavaParameterizationAuxiliaries();
      if (javaParameterizationAuxiliariesValue != null) {
        javaParameterizationAuxiliaries(javaParameterizationAuxiliariesValue);
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IServiceParameterizationDesc} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IServiceParameterizationDesc instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableServiceParameterizationDesc) {
        return from((MutableServiceParameterizationDesc) instance);
      }
      addAllServiceParameterDesc(instance.getServiceParameterDesc());
      @Nullable IJavaParameterizationAux javaParameterizationAuxiliariesValue = instance.getJavaParameterizationAuxiliaries();
      if (javaParameterizationAuxiliariesValue != null) {
        javaParameterizationAuxiliaries(javaParameterizationAuxiliariesValue);
      }
      return this;
    }

    /**
     * Adds one element to {@link IServiceParameterizationDesc#getServiceParameterDesc() serviceParameterDesc} list.
     * @param element A serviceParameterDesc element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addServiceParameterDesc(IServiceParameterDesc element) {
      this.serviceParameterDesc.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IServiceParameterizationDesc#getServiceParameterDesc() serviceParameterDesc} list.
     * @param elements An array of serviceParameterDesc elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addServiceParameterDesc(IServiceParameterDesc... elements) {
      this.serviceParameterDesc.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IServiceParameterizationDesc#getServiceParameterDesc() serviceParameterDesc} list.
     * @param elements An iterable of serviceParameterDesc elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("serviceParameterDesc")
    public final Builder serviceParameterDesc(Iterable<? extends IServiceParameterDesc> elements) {
      this.serviceParameterDesc = ImmutableList.builder();
      return addAllServiceParameterDesc(elements);
    }

    /**
     * Adds elements to {@link IServiceParameterizationDesc#getServiceParameterDesc() serviceParameterDesc} list.
     * @param elements An iterable of serviceParameterDesc elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllServiceParameterDesc(Iterable<? extends IServiceParameterDesc> elements) {
      this.serviceParameterDesc.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link IServiceParameterizationDesc#getJavaParameterizationAuxiliaries() javaParameterizationAuxiliaries} attribute.
     * @param javaParameterizationAuxiliaries The value for javaParameterizationAuxiliaries (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("javaParameterizationAuxiliaries")
    public final Builder javaParameterizationAuxiliaries(@Nullable IJavaParameterizationAux javaParameterizationAuxiliaries) {
      this.javaParameterizationAuxiliaries = javaParameterizationAuxiliaries;
      return this;
    }

    /**
     * Builds a new {@link ServiceParameterizationDesc ServiceParameterizationDesc}.
     * @return An immutable instance of ServiceParameterizationDesc
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ServiceParameterizationDesc build() {
      return new ServiceParameterizationDesc(serviceParameterDesc.build(), javaParameterizationAuxiliaries);
    }
  }
}
