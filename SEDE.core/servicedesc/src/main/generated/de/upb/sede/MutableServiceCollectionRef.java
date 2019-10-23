package de.upb.sede;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IServiceCollectionRef IServiceCollectionRef} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableServiceCollectionRef is not thread-safe</em>
 * @see ServiceCollectionRef
 */
@Generated(from = "IServiceCollectionRef", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IServiceCollectionRef"})
@NotThreadSafe
public final class MutableServiceCollectionRef implements IServiceCollectionRef {
  private static final long INIT_BIT_REF = 0x1L;
  private long initBits = 0x1L;

  private IQualifiable ref;

  private MutableServiceCollectionRef() {}

  /**
   * Construct a modifiable instance of {@code IServiceCollectionRef}.
   * @return A new modifiable instance
   */
  public static MutableServiceCollectionRef create() {
    return new MutableServiceCollectionRef();
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
  public MutableServiceCollectionRef clear() {
    initBits = 0x1L;
    ref = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.ConstructReference} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceCollectionRef from(ConstructReference instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.IServiceCollectionRef} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceCollectionRef from(IServiceCollectionRef instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IServiceCollectionRef} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableServiceCollectionRef from(MutableServiceCollectionRef instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableServiceCollectionRef) {
      MutableServiceCollectionRef instance = (MutableServiceCollectionRef) object;
      if (instance.refIsSet()) {
        setRef(instance.getRef());
      }
      return;
    }
    if (object instanceof ConstructReference) {
      ConstructReference instance = (ConstructReference) object;
      setRef(instance.getRef());
    }
  }

  /**
   * Assigns a value to the {@link IServiceCollectionRef#getRef() ref} attribute.
   * @param ref The value for ref
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceCollectionRef setRef(IQualifiable ref) {
    this.ref = Objects.requireNonNull(ref, "ref");
    initBits &= ~INIT_BIT_REF;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IServiceCollectionRef#getRef() ref} is set.
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
  public final MutableServiceCollectionRef unsetRef() {
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
    return "ServiceCollectionRef is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link ServiceCollectionRef ServiceCollectionRef}.
   * @return An immutable instance of ServiceCollectionRef
   */
  public final ServiceCollectionRef toImmutable() {
    checkRequiredAttributes();
    return ServiceCollectionRef.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableServiceCollectionRef} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableServiceCollectionRef)) return false;
    MutableServiceCollectionRef other = (MutableServiceCollectionRef) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableServiceCollectionRef another) {
    return ref.equals(another.ref);
  }

  /**
   * Computes a hash code from attributes: {@code ref}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + ref.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IServiceCollectionRef}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableServiceCollectionRef")
        .add("ref", refIsSet() ? getRef() : "?")
        .toString();
  }
}
