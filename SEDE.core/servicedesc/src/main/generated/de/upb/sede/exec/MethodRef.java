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
 * Immutable implementation of {@link IMethodRef}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code MethodRef.builder()}.
 */
@Generated(from = "IMethodRef", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class MethodRef implements IMethodRef {
  private final IServiceRef serviceRef;
  private final IQualifiable ref;

  private MethodRef(IServiceRef serviceRef, IQualifiable ref) {
    this.serviceRef = serviceRef;
    this.ref = ref;
  }

  /**
   * @return The value of the {@code serviceRef} attribute
   */
  @JsonProperty("serviceRef")
  @Override
  public IServiceRef getServiceRef() {
    return serviceRef;
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
   * Copy the current immutable object by setting a value for the {@link IMethodRef#getServiceRef() serviceRef} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for serviceRef
   * @return A modified copy of the {@code this} object
   */
  public final MethodRef withServiceRef(IServiceRef value) {
    if (this.serviceRef == value) return this;
    IServiceRef newValue = Objects.requireNonNull(value, "serviceRef");
    return new MethodRef(newValue, this.ref);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IMethodRef#getRef() ref} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for ref
   * @return A modified copy of the {@code this} object
   */
  public final MethodRef withRef(IQualifiable value) {
    if (this.ref == value) return this;
    IQualifiable newValue = Objects.requireNonNull(value, "ref");
    return new MethodRef(this.serviceRef, newValue);
  }

  /**
   * This instance is equal to all instances of {@code MethodRef} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof MethodRef
        && equalTo((MethodRef) another);
  }

  private boolean equalTo(MethodRef another) {
    return serviceRef.equals(another.serviceRef)
        && ref.equals(another.ref);
  }

  /**
   * Computes a hash code from attributes: {@code serviceRef}, {@code ref}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + serviceRef.hashCode();
    h += (h << 5) + ref.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code MethodRef} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MethodRef")
        .omitNullValues()
        .add("serviceRef", serviceRef)
        .add("ref", ref)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IMethodRef", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IMethodRef {
    @Nullable IServiceRef serviceRef;
    @Nullable IQualifiable ref;
    @JsonProperty("serviceRef")
    public void setServiceRef(IServiceRef serviceRef) {
      this.serviceRef = serviceRef;
    }
    @JsonProperty("ref")
    public void setRef(IQualifiable ref) {
      this.ref = ref;
    }
    @Override
    public IServiceRef getServiceRef() { throw new UnsupportedOperationException(); }
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
  static MethodRef fromJson(Json json) {
    MethodRef.Builder builder = MethodRef.builder();
    if (json.serviceRef != null) {
      builder.serviceRef(json.serviceRef);
    }
    if (json.ref != null) {
      builder.ref(json.ref);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IMethodRef} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable MethodRef instance
   */
  public static MethodRef copyOf(IMethodRef instance) {
    if (instance instanceof MethodRef) {
      return (MethodRef) instance;
    }
    return MethodRef.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link MethodRef MethodRef}.
   * <pre>
   * MethodRef.builder()
   *    .serviceRef(de.upb.sede.exec.IServiceRef) // required {@link IMethodRef#getServiceRef() serviceRef}
   *    .ref(de.upb.sede.IQualifiable) // required {@link IMethodRef#getRef() ref}
   *    .build();
   * </pre>
   * @return A new MethodRef builder
   */
  public static MethodRef.Builder builder() {
    return new MethodRef.Builder();
  }

  /**
   * Builds instances of type {@link MethodRef MethodRef}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IMethodRef", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_SERVICE_REF = 0x1L;
    private static final long INIT_BIT_REF = 0x2L;
    private long initBits = 0x3L;

    private @Nullable IServiceRef serviceRef;
    private @Nullable IQualifiable ref;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableMethodRef} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableMethodRef instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.serviceRefIsSet()) {
        serviceRef(instance.getServiceRef());
      }
      if (instance.refIsSet()) {
        ref(instance.getRef());
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.exec.IMethodRef} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IMethodRef instance) {
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
      if (object instanceof MutableMethodRef) {
        from((MutableMethodRef) object);
        return;
      }
      if (object instanceof IMethodRef) {
        IMethodRef instance = (IMethodRef) object;
        serviceRef(instance.getServiceRef());
      }
      if (object instanceof ConstructReference) {
        ConstructReference instance = (ConstructReference) object;
        ref(instance.getRef());
      }
    }

    /**
     * Initializes the value for the {@link IMethodRef#getServiceRef() serviceRef} attribute.
     * @param serviceRef The value for serviceRef 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("serviceRef")
    public final Builder serviceRef(IServiceRef serviceRef) {
      this.serviceRef = Objects.requireNonNull(serviceRef, "serviceRef");
      initBits &= ~INIT_BIT_SERVICE_REF;
      return this;
    }

    /**
     * Initializes the value for the {@link IMethodRef#getRef() ref} attribute.
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
     * Builds a new {@link MethodRef MethodRef}.
     * @return An immutable instance of MethodRef
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public MethodRef build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new MethodRef(serviceRef, ref);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_SERVICE_REF) != 0) attributes.add("serviceRef");
      if ((initBits & INIT_BIT_REF) != 0) attributes.add("ref");
      return "Cannot build MethodRef, some of required attributes are not set " + attributes;
    }
  }
}
