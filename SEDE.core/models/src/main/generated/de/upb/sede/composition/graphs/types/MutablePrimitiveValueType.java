package de.upb.sede.composition.graphs.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.core.PrimitiveType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IPrimitiveValueType IPrimitiveValueType} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutablePrimitiveValueType is not thread-safe</em>
 * @see PrimitiveValueType
 */
@Generated(from = "IPrimitiveValueType", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IPrimitiveValueType"})
@NotThreadSafe
public final class MutablePrimitiveValueType
    implements IPrimitiveValueType {
  private static final long INIT_BIT_PRIMITIVE_TYPE = 0x1L;
  private long initBits = 0x1L;

  private PrimitiveType primitiveType;

  private MutablePrimitiveValueType() {}

  /**
   * Construct a modifiable instance of {@code IPrimitiveValueType}.
   * @return A new modifiable instance
   */
  public static MutablePrimitiveValueType create() {
    return new MutablePrimitiveValueType();
  }

  /**
   * @return value of {@code primitiveType} attribute
   */
  @JsonProperty("primitiveType")
  @Override
  public final PrimitiveType getPrimitiveType() {
    if (!primitiveTypeIsSet()) {
      checkRequiredAttributes();
    }
    return primitiveType;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutablePrimitiveValueType clear() {
    initBits = 0x1L;
    primitiveType = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IPrimitiveValueType} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutablePrimitiveValueType from(IPrimitiveValueType instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutablePrimitiveValueType) {
      from((MutablePrimitiveValueType) instance);
      return this;
    }
    setPrimitiveType(instance.getPrimitiveType());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IPrimitiveValueType} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutablePrimitiveValueType from(MutablePrimitiveValueType instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.primitiveTypeIsSet()) {
      setPrimitiveType(instance.getPrimitiveType());
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IPrimitiveValueType#getPrimitiveType() primitiveType} attribute.
   * @param primitiveType The value for primitiveType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutablePrimitiveValueType setPrimitiveType(PrimitiveType primitiveType) {
    this.primitiveType = Objects.requireNonNull(primitiveType, "primitiveType");
    initBits &= ~INIT_BIT_PRIMITIVE_TYPE;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IPrimitiveValueType#getPrimitiveType() primitiveType} is set.
   * @return {@code true} if set
   */
  public final boolean primitiveTypeIsSet() {
    return (initBits & INIT_BIT_PRIMITIVE_TYPE) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutablePrimitiveValueType unsetPrimitiveType() {
    initBits |= INIT_BIT_PRIMITIVE_TYPE;
    primitiveType = null;
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
    if (!primitiveTypeIsSet()) attributes.add("primitiveType");
    return "PrimitiveValueType is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link PrimitiveValueType PrimitiveValueType}.
   * @return An immutable instance of PrimitiveValueType
   */
  public final PrimitiveValueType toImmutable() {
    checkRequiredAttributes();
    return PrimitiveValueType.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutablePrimitiveValueType} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutablePrimitiveValueType)) return false;
    MutablePrimitiveValueType other = (MutablePrimitiveValueType) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutablePrimitiveValueType another) {
    return primitiveType.equals(another.primitiveType);
  }

  /**
   * Computes a hash code from attributes: {@code primitiveType}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + primitiveType.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IPrimitiveValueType}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutablePrimitiveValueType")
        .add("primitiveType", primitiveTypeIsSet() ? getPrimitiveType() : "?")
        .toString();
  }
}
