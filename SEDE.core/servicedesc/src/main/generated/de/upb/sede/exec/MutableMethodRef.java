package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.ConstructReference;
import de.upb.sede.IQualifiable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IMethodRef IMethodRef} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableMethodRef is not thread-safe</em>
 * @see MethodRef
 */
@Generated(from = "IMethodRef", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IMethodRef"})
@NotThreadSafe
public final class MutableMethodRef implements IMethodRef {
  private static final long INIT_BIT_SERVICE_REF = 0x1L;
  private static final long INIT_BIT_REF = 0x2L;
  private long initBits = 0x3L;

  private IServiceRef serviceRef;
  private IQualifiable ref;

  private MutableMethodRef() {}

  /**
   * Construct a modifiable instance of {@code IMethodRef}.
   * @return A new modifiable instance
   */
  public static MutableMethodRef create() {
    return new MutableMethodRef();
  }

  /**
   * @return value of {@code serviceRef} attribute
   */
  @JsonProperty("serviceRef")
  @Override
  public final IServiceRef getServiceRef() {
    if (!serviceRefIsSet()) {
      checkRequiredAttributes();
    }
    return serviceRef;
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
  public MutableMethodRef clear() {
    initBits = 0x3L;
    serviceRef = null;
    ref = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.exec.IMethodRef} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodRef from(IMethodRef instance) {
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
  public MutableMethodRef from(ConstructReference instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IMethodRef} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableMethodRef from(MutableMethodRef instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableMethodRef) {
      MutableMethodRef instance = (MutableMethodRef) object;
      if (instance.serviceRefIsSet()) {
        setServiceRef(instance.getServiceRef());
      }
      if (instance.refIsSet()) {
        setRef(instance.getRef());
      }
      return;
    }
    if (object instanceof IMethodRef) {
      IMethodRef instance = (IMethodRef) object;
      setServiceRef(instance.getServiceRef());
    }
    if (object instanceof ConstructReference) {
      ConstructReference instance = (ConstructReference) object;
      setRef(instance.getRef());
    }
  }

  /**
   * Assigns a value to the {@link IMethodRef#getServiceRef() serviceRef} attribute.
   * @param serviceRef The value for serviceRef
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodRef setServiceRef(IServiceRef serviceRef) {
    this.serviceRef = Objects.requireNonNull(serviceRef, "serviceRef");
    initBits &= ~INIT_BIT_SERVICE_REF;
    return this;
  }

  /**
   * Assigns a value to the {@link IMethodRef#getRef() ref} attribute.
   * @param ref The value for ref
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodRef setRef(IQualifiable ref) {
    this.ref = Objects.requireNonNull(ref, "ref");
    initBits &= ~INIT_BIT_REF;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IMethodRef#getServiceRef() serviceRef} is set.
   * @return {@code true} if set
   */
  public final boolean serviceRefIsSet() {
    return (initBits & INIT_BIT_SERVICE_REF) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IMethodRef#getRef() ref} is set.
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
  public final MutableMethodRef unsetServiceRef() {
    initBits |= INIT_BIT_SERVICE_REF;
    serviceRef = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableMethodRef unsetRef() {
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
    if (!serviceRefIsSet()) attributes.add("serviceRef");
    if (!refIsSet()) attributes.add("ref");
    return "MethodRef is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link MethodRef MethodRef}.
   * @return An immutable instance of MethodRef
   */
  public final MethodRef toImmutable() {
    checkRequiredAttributes();
    return MethodRef.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableMethodRef} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableMethodRef)) return false;
    MutableMethodRef other = (MutableMethodRef) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableMethodRef another) {
    return serviceRef.equals(another.serviceRef)
        && ref.equals(another.ref);
  }

  /**
   * Computes a hash code from attributes: {@code serviceRef}, {@code ref}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + serviceRef.hashCode();
    h += (h << 5) + ref.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IMethodRef}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableMethodRef")
        .add("serviceRef", serviceRefIsSet() ? getServiceRef() : "?")
        .add("ref", refIsSet() ? getRef() : "?")
        .toString();
  }
}
