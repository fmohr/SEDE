package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.ConstructReference;
import de.upb.sede.IQualifiable;
import de.upb.sede.IServiceCollectionRef;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IServiceRef IServiceRef} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableServiceRef is not thread-safe</em>
 * @see ServiceRef
 */
@Generated(from = "IServiceRef", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IServiceRef"})
@NotThreadSafe
public final class MutableServiceRef implements IServiceRef {
  private static final long INIT_BIT_REF = 0x1L;
  private long initBits = 0x1L;

  private @Nullable IServiceCollectionRef serviceCollectionRef;
  private IQualifiable ref;

  private MutableServiceRef() {}

  /**
   * Construct a modifiable instance of {@code IServiceRef}.
   * @return A new modifiable instance
   */
  public static MutableServiceRef create() {
    return new MutableServiceRef();
  }

  /**
   * @return value of {@code serviceCollectionRef} attribute, may be {@code null}
   */
  @JsonProperty("serviceCollectionRef")
  @Override
  public final @Nullable IServiceCollectionRef getServiceCollectionRef() {
    return serviceCollectionRef;
  }

  /**
   * @return value of {@code ref} attribute
   */
  @JsonProperty("ref")
  @Override
  public final IQualifiable getRef() {
    if (!refIsSet()) {
      checkRequiredAttributes();
    }
    return ref;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceRef clear() {
    initBits = 0x1L;
    serviceCollectionRef = null;
    ref = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.exec.IServiceRef} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceRef from(IServiceRef instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.ConstructReference} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceRef from(ConstructReference instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IServiceRef} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableServiceRef from(MutableServiceRef instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableServiceRef) {
      MutableServiceRef instance = (MutableServiceRef) object;
      @Nullable IServiceCollectionRef serviceCollectionRefValue = instance.getServiceCollectionRef();
      if (serviceCollectionRefValue != null) {
        setServiceCollectionRef(serviceCollectionRefValue);
      }
      if (instance.refIsSet()) {
        setRef(instance.getRef());
      }
      return;
    }
    if (object instanceof IServiceRef) {
      IServiceRef instance = (IServiceRef) object;
      @Nullable IServiceCollectionRef serviceCollectionRefValue = instance.getServiceCollectionRef();
      if (serviceCollectionRefValue != null) {
        setServiceCollectionRef(serviceCollectionRefValue);
      }
    }
    if (object instanceof ConstructReference) {
      ConstructReference instance = (ConstructReference) object;
      setRef(instance.getRef());
    }
  }

  /**
   * Assigns a value to the {@link IServiceRef#getServiceCollectionRef() serviceCollectionRef} attribute.
   * @param serviceCollectionRef The value for serviceCollectionRef, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceRef setServiceCollectionRef(@Nullable IServiceCollectionRef serviceCollectionRef) {
    this.serviceCollectionRef = serviceCollectionRef;
    return this;
  }

  /**
   * Assigns a value to the {@link IServiceRef#getRef() ref} attribute.
   * @param ref The value for ref
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceRef setRef(IQualifiable ref) {
    this.ref = Objects.requireNonNull(ref, "ref");
    initBits &= ~INIT_BIT_REF;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IServiceRef#getRef() ref} is set.
   * @return {@code true} if set
   */
  public final boolean refIsSet() {
    return (initBits & INIT_BIT_REF) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableServiceRef unsetRef() {
    initBits |= INIT_BIT_REF;
    ref = null;
    return this;
  }

  /**
   * Returns {@code true} if all required attributes are set, indicating that the object is initialized.
   * @return {@code true} if set
   */
  public final boolean isInitialized() {
    return initBits == 0;
  }

  private void checkRequiredAttributes() {
    if (!isInitialized()) {
      throw new IllegalStateException(formatRequiredAttributesMessage());
    }
  }

  private String formatRequiredAttributesMessage() {
    List<String> attributes = new ArrayList<>();
    if (!refIsSet()) attributes.add("ref");
    return "ServiceRef is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link ServiceRef ServiceRef}.
   * @return An immutable instance of ServiceRef
   */
  public final ServiceRef toImmutable() {
    checkRequiredAttributes();
    return ServiceRef.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableServiceRef} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableServiceRef)) return false;
    MutableServiceRef other = (MutableServiceRef) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableServiceRef another) {
    return Objects.equals(serviceCollectionRef, another.serviceCollectionRef)
        && ref.equals(another.ref);
  }

  /**
   * Computes a hash code from attributes: {@code serviceCollectionRef}, {@code ref}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Objects.hashCode(serviceCollectionRef);
    h += (h << 5) + ref.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IServiceRef}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableServiceRef")
        .add("serviceCollectionRef", getServiceCollectionRef())
        .add("ref", refIsSet() ? getRef() : "?")
        .toString();
  }
}
