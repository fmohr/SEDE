package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.exec.aux.IJavaParameterizationAux;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link IServiceParameterDesc}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ServiceParameterDesc.builder()}.
 */
@Generated(from = "IServiceParameterDesc", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ServiceParameterDesc implements IServiceParameterDesc {
  private final @Nullable IJavaParameterizationAux javaParameterizationAuxiliaries;

  private ServiceParameterDesc(
      @Nullable IJavaParameterizationAux javaParameterizationAuxiliaries) {
    this.javaParameterizationAuxiliaries = javaParameterizationAuxiliaries;
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
   * Copy the current immutable object by setting a value for the {@link IServiceParameterDesc#getJavaParameterizationAuxiliaries() javaParameterizationAuxiliaries} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for javaParameterizationAuxiliaries (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final ServiceParameterDesc withJavaParameterizationAuxiliaries(@Nullable IJavaParameterizationAux value) {
    if (this.javaParameterizationAuxiliaries == value) return this;
    return new ServiceParameterDesc(value);
  }

  /**
   * This instance is equal to all instances of {@code ServiceParameterDesc} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ServiceParameterDesc
        && equalTo((ServiceParameterDesc) another);
  }

  private boolean equalTo(ServiceParameterDesc another) {
    return Objects.equals(javaParameterizationAuxiliaries, another.javaParameterizationAuxiliaries);
  }

  /**
   * Computes a hash code from attributes: {@code javaParameterizationAuxiliaries}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Objects.hashCode(javaParameterizationAuxiliaries);
    return h;
  }

  /**
   * Prints the immutable value {@code ServiceParameterDesc} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ServiceParameterDesc")
        .omitNullValues()
        .add("javaParameterizationAuxiliaries", javaParameterizationAuxiliaries)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IServiceParameterDesc", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IServiceParameterDesc {
    @Nullable IJavaParameterizationAux javaParameterizationAuxiliaries;
    @JsonProperty("javaParameterizationAuxiliaries")
    public void setJavaParameterizationAuxiliaries(@Nullable IJavaParameterizationAux javaParameterizationAuxiliaries) {
      this.javaParameterizationAuxiliaries = javaParameterizationAuxiliaries;
    }
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
  static ServiceParameterDesc fromJson(Json json) {
    ServiceParameterDesc.Builder builder = ServiceParameterDesc.builder();
    if (json.javaParameterizationAuxiliaries != null) {
      builder.javaParameterizationAuxiliaries(json.javaParameterizationAuxiliaries);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IServiceParameterDesc} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ServiceParameterDesc instance
   */
  public static ServiceParameterDesc copyOf(IServiceParameterDesc instance) {
    if (instance instanceof ServiceParameterDesc) {
      return (ServiceParameterDesc) instance;
    }
    return ServiceParameterDesc.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ServiceParameterDesc ServiceParameterDesc}.
   * <pre>
   * ServiceParameterDesc.builder()
   *    .javaParameterizationAuxiliaries(de.upb.sede.exec.aux.IJavaParameterizationAux | null) // nullable {@link IServiceParameterDesc#getJavaParameterizationAuxiliaries() javaParameterizationAuxiliaries}
   *    .build();
   * </pre>
   * @return A new ServiceParameterDesc builder
   */
  public static ServiceParameterDesc.Builder builder() {
    return new ServiceParameterDesc.Builder();
  }

  /**
   * Builds instances of type {@link ServiceParameterDesc ServiceParameterDesc}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IServiceParameterDesc", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private @Nullable IJavaParameterizationAux javaParameterizationAuxiliaries;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableServiceParameterDesc} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableServiceParameterDesc instance) {
      Objects.requireNonNull(instance, "instance");
      @Nullable IJavaParameterizationAux javaParameterizationAuxiliariesValue = instance.getJavaParameterizationAuxiliaries();
      if (javaParameterizationAuxiliariesValue != null) {
        javaParameterizationAuxiliaries(javaParameterizationAuxiliariesValue);
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IServiceParameterDesc} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IServiceParameterDesc instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableServiceParameterDesc) {
        return from((MutableServiceParameterDesc) instance);
      }
      @Nullable IJavaParameterizationAux javaParameterizationAuxiliariesValue = instance.getJavaParameterizationAuxiliaries();
      if (javaParameterizationAuxiliariesValue != null) {
        javaParameterizationAuxiliaries(javaParameterizationAuxiliariesValue);
      }
      return this;
    }

    /**
     * Initializes the value for the {@link IServiceParameterDesc#getJavaParameterizationAuxiliaries() javaParameterizationAuxiliaries} attribute.
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
     * Builds a new {@link ServiceParameterDesc ServiceParameterDesc}.
     * @return An immutable instance of ServiceParameterDesc
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ServiceParameterDesc build() {
      return new ServiceParameterDesc(javaParameterizationAuxiliaries);
    }
  }
}
