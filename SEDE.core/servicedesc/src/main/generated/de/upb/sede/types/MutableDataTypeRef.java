package de.upb.sede.types;

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
 * A modifiable implementation of the {@link IDataTypeRef IDataTypeRef} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableDataTypeRef is not thread-safe</em>
 * @see DataTypeRef
 */
@Generated(from = "IDataTypeRef", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IDataTypeRef"})
@NotThreadSafe
public final class MutableDataTypeRef implements IDataTypeRef {
  private static final long INIT_BIT_REF = 0x1L;
  private long initBits = 0x1L;

  private @Nullable IServiceCollectionRef serviceCollectionRef;
  private IQualifiable ref;

  private MutableDataTypeRef() {}

  /**
   * Construct a modifiable instance of {@code IDataTypeRef}.
   * @return A new modifiable instance
   */
  public static MutableDataTypeRef create() {
    return new MutableDataTypeRef();
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
  public MutableDataTypeRef clear() {
    initBits = 0x1L;
    serviceCollectionRef = null;
    ref = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.types.IDataTypeRef} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataTypeRef from(IDataTypeRef instance) {
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
  public MutableDataTypeRef from(ConstructReference instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IDataTypeRef} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableDataTypeRef from(MutableDataTypeRef instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableDataTypeRef) {
      MutableDataTypeRef instance = (MutableDataTypeRef) object;
      @Nullable IServiceCollectionRef serviceCollectionRefValue = instance.getServiceCollectionRef();
      if (serviceCollectionRefValue != null) {
        setServiceCollectionRef(serviceCollectionRefValue);
      }
      if (instance.refIsSet()) {
        setRef(instance.getRef());
      }
      return;
    }
    if (object instanceof IDataTypeRef) {
      IDataTypeRef instance = (IDataTypeRef) object;
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
   * Assigns a value to the {@link IDataTypeRef#getServiceCollectionRef() serviceCollectionRef} attribute.
   * @param serviceCollectionRef The value for serviceCollectionRef, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataTypeRef setServiceCollectionRef(@Nullable IServiceCollectionRef serviceCollectionRef) {
    this.serviceCollectionRef = serviceCollectionRef;
    return this;
  }

  /**
   * Assigns a value to the {@link IDataTypeRef#getRef() ref} attribute.
   * @param ref The value for ref
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataTypeRef setRef(IQualifiable ref) {
    this.ref = Objects.requireNonNull(ref, "ref");
    initBits &= ~INIT_BIT_REF;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IDataTypeRef#getRef() ref} is set.
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
  public final MutableDataTypeRef unsetRef() {
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
    return "DataTypeRef is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link DataTypeRef DataTypeRef}.
   * @return An immutable instance of DataTypeRef
   */
  public final DataTypeRef toImmutable() {
    checkRequiredAttributes();
    return DataTypeRef.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableDataTypeRef} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableDataTypeRef)) return false;
    MutableDataTypeRef other = (MutableDataTypeRef) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableDataTypeRef another) {
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
   * Generates a string representation of this {@code IDataTypeRef}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableDataTypeRef")
        .add("serviceCollectionRef", getServiceCollectionRef())
        .add("ref", refIsSet() ? getRef() : "?")
        .toString();
  }
}
