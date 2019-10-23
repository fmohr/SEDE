package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.ConstructReference;
import de.upb.sede.IQualifiable;
import de.upb.sede.IServiceCollectionRef;
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
 * Immutable implementation of {@link IServiceRef}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ServiceRef.builder()}.
 */
@Generated(from = "IServiceRef", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ServiceRef implements IServiceRef {
  private final @Nullable IServiceCollectionRef serviceCollectionRef;
  private final IQualifiable ref;

  private ServiceRef(
      @Nullable IServiceCollectionRef serviceCollectionRef,
      IQualifiable ref) {
    this.serviceCollectionRef = serviceCollectionRef;
    this.ref = ref;
  }

  /**
   * @return The value of the {@code serviceCollectionRef} attribute
   */
  @JsonProperty("serviceCollectionRef")
  @Override
  public @Nullable IServiceCollectionRef getServiceCollectionRef() {
    return serviceCollectionRef;
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
   * Copy the current immutable object by setting a value for the {@link IServiceRef#getServiceCollectionRef() serviceCollectionRef} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for serviceCollectionRef (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final ServiceRef withServiceCollectionRef(@Nullable IServiceCollectionRef value) {
    if (this.serviceCollectionRef == value) return this;
    return new ServiceRef(value, this.ref);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IServiceRef#getRef() ref} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for ref
   * @return A modified copy of the {@code this} object
   */
  public final ServiceRef withRef(IQualifiable value) {
    if (this.ref == value) return this;
    IQualifiable newValue = Objects.requireNonNull(value, "ref");
    return new ServiceRef(this.serviceCollectionRef, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ServiceRef} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ServiceRef
        && equalTo((ServiceRef) another);
  }

  private boolean equalTo(ServiceRef another) {
    return Objects.equals(serviceCollectionRef, another.serviceCollectionRef)
        && ref.equals(another.ref);
  }

  /**
   * Computes a hash code from attributes: {@code serviceCollectionRef}, {@code ref}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Objects.hashCode(serviceCollectionRef);
    h += (h << 5) + ref.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ServiceRef} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ServiceRef")
        .omitNullValues()
        .add("serviceCollectionRef", serviceCollectionRef)
        .add("ref", ref)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IServiceRef", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IServiceRef {
    @Nullable IServiceCollectionRef serviceCollectionRef;
    @Nullable IQualifiable ref;
    @JsonProperty("serviceCollectionRef")
    public void setServiceCollectionRef(@Nullable IServiceCollectionRef serviceCollectionRef) {
      this.serviceCollectionRef = serviceCollectionRef;
    }
    @JsonProperty("ref")
    public void setRef(IQualifiable ref) {
      this.ref = ref;
    }
    @Override
    public IServiceCollectionRef getServiceCollectionRef() { throw new UnsupportedOperationException(); }
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
  static ServiceRef fromJson(Json json) {
    ServiceRef.Builder builder = ServiceRef.builder();
    if (json.serviceCollectionRef != null) {
      builder.serviceCollectionRef(json.serviceCollectionRef);
    }
    if (json.ref != null) {
      builder.ref(json.ref);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IServiceRef} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ServiceRef instance
   */
  public static ServiceRef copyOf(IServiceRef instance) {
    if (instance instanceof ServiceRef) {
      return (ServiceRef) instance;
    }
    return ServiceRef.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ServiceRef ServiceRef}.
   * <pre>
   * ServiceRef.builder()
   *    .serviceCollectionRef(de.upb.sede.IServiceCollectionRef | null) // nullable {@link IServiceRef#getServiceCollectionRef() serviceCollectionRef}
   *    .ref(de.upb.sede.IQualifiable) // required {@link IServiceRef#getRef() ref}
   *    .build();
   * </pre>
   * @return A new ServiceRef builder
   */
  public static ServiceRef.Builder builder() {
    return new ServiceRef.Builder();
  }

  /**
   * Builds instances of type {@link ServiceRef ServiceRef}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IServiceRef", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_REF = 0x1L;
    private long initBits = 0x1L;

    private @Nullable IServiceCollectionRef serviceCollectionRef;
    private @Nullable IQualifiable ref;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableServiceRef} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableServiceRef instance) {
      Objects.requireNonNull(instance, "instance");
      @Nullable IServiceCollectionRef serviceCollectionRefValue = instance.getServiceCollectionRef();
      if (serviceCollectionRefValue != null) {
        serviceCollectionRef(serviceCollectionRefValue);
      }
      if (instance.refIsSet()) {
        ref(instance.getRef());
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.exec.IServiceRef} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IServiceRef instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
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

    private void from(Object object) {
      if (object instanceof MutableServiceRef) {
        from((MutableServiceRef) object);
        return;
      }
      if (object instanceof IServiceRef) {
        IServiceRef instance = (IServiceRef) object;
        @Nullable IServiceCollectionRef serviceCollectionRefValue = instance.getServiceCollectionRef();
        if (serviceCollectionRefValue != null) {
          serviceCollectionRef(serviceCollectionRefValue);
        }
      }
      if (object instanceof ConstructReference) {
        ConstructReference instance = (ConstructReference) object;
        ref(instance.getRef());
      }
    }

    /**
     * Initializes the value for the {@link IServiceRef#getServiceCollectionRef() serviceCollectionRef} attribute.
     * @param serviceCollectionRef The value for serviceCollectionRef (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("serviceCollectionRef")
    public final Builder serviceCollectionRef(@Nullable IServiceCollectionRef serviceCollectionRef) {
      this.serviceCollectionRef = serviceCollectionRef;
      return this;
    }

    /**
     * Initializes the value for the {@link IServiceRef#getRef() ref} attribute.
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
     * Builds a new {@link ServiceRef ServiceRef}.
     * @return An immutable instance of ServiceRef
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ServiceRef build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ServiceRef(serviceCollectionRef, ref);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_REF) != 0) attributes.add("ref");
      return "Cannot build ServiceRef, some of required attributes are not set " + attributes;
    }
  }
}
