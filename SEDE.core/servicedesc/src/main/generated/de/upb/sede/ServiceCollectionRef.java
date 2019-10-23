package de.upb.sede;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link IServiceCollectionRef}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ServiceCollectionRef.builder()}.
 */
@Generated(from = "IServiceCollectionRef", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ServiceCollectionRef implements IServiceCollectionRef {
  private final IQualifiable ref;

  private ServiceCollectionRef(IQualifiable ref) {
    this.ref = ref;
  }

  /**
   * @return The value of the {@code ref} attribute
   */
  @JsonProperty("ref")
  @Override
  public IQualifiable getRef() {
    return ref;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IServiceCollectionRef#getRef() ref} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for ref
   * @return A modified copy of the {@code this} object
   */
  public final ServiceCollectionRef withRef(IQualifiable value) {
    if (this.ref == value) return this;
    IQualifiable newValue = Objects.requireNonNull(value, "ref");
    return new ServiceCollectionRef(newValue);
  }

  /**
   * This instance is equal to all instances of {@code ServiceCollectionRef} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ServiceCollectionRef
        && equalTo((ServiceCollectionRef) another);
  }

  private boolean equalTo(ServiceCollectionRef another) {
    return ref.equals(another.ref);
  }

  /**
   * Computes a hash code from attributes: {@code ref}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + ref.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ServiceCollectionRef} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ServiceCollectionRef")
        .omitNullValues()
        .add("ref", ref)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IServiceCollectionRef", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IServiceCollectionRef {
    @Nullable IQualifiable ref;
    @JsonProperty("ref")
    public void setRef(IQualifiable ref) {
      this.ref = ref;
    }
    @Override
    public IQualifiable getRef() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static ServiceCollectionRef fromJson(Json json) {
    ServiceCollectionRef.Builder builder = ServiceCollectionRef.builder();
    if (json.ref != null) {
      builder.ref(json.ref);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IServiceCollectionRef} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ServiceCollectionRef instance
   */
  public static ServiceCollectionRef copyOf(IServiceCollectionRef instance) {
    if (instance instanceof ServiceCollectionRef) {
      return (ServiceCollectionRef) instance;
    }
    return ServiceCollectionRef.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ServiceCollectionRef ServiceCollectionRef}.
   * <pre>
   * ServiceCollectionRef.builder()
   *    .ref(de.upb.sede.IQualifiable) // required {@link IServiceCollectionRef#getRef() ref}
   *    .build();
   * </pre>
   * @return A new ServiceCollectionRef builder
   */
  public static ServiceCollectionRef.Builder builder() {
    return new ServiceCollectionRef.Builder();
  }

  /**
   * Builds instances of type {@link ServiceCollectionRef ServiceCollectionRef}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IServiceCollectionRef", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_REF = 0x1L;
    private long initBits = 0x1L;

    private @Nullable IQualifiable ref;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableServiceCollectionRef} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableServiceCollectionRef instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.refIsSet()) {
        ref(instance.getRef());
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.ConstructReference} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(ConstructReference instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.IServiceCollectionRef} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IServiceCollectionRef instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof MutableServiceCollectionRef) {
        from((MutableServiceCollectionRef) object);
        return;
      }
      if (object instanceof ConstructReference) {
        ConstructReference instance = (ConstructReference) object;
        ref(instance.getRef());
      }
    }

    /**
     * Initializes the value for the {@link IServiceCollectionRef#getRef() ref} attribute.
     * @param ref The value for ref 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("ref")
    public final Builder ref(IQualifiable ref) {
      this.ref = Objects.requireNonNull(ref, "ref");
      initBits &= ~INIT_BIT_REF;
      return this;
    }

    /**
     * Builds a new {@link ServiceCollectionRef ServiceCollectionRef}.
     * @return An immutable instance of ServiceCollectionRef
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ServiceCollectionRef build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ServiceCollectionRef(ref);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_REF) != 0) attributes.add("ref");
      return "Cannot build ServiceCollectionRef, some of required attributes are not set " + attributes;
    }
  }
}
