package de.upb.sede.composition.graphs.types;

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
 * A modifiable implementation of the {@link IRefType IRefType} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableRefType is not thread-safe</em>
 * @see RefType
 */
@Generated(from = "IRefType", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IRefType"})
@NotThreadSafe
public final class MutableRefType implements IRefType {
  private static final long INIT_BIT_TYPE_OF_REF = 0x1L;
  private long initBits = 0x1L;

  private ValueTypeClass typeOfRef;

  private MutableRefType() {}

  /**
   * Construct a modifiable instance of {@code IRefType}.
   * @return A new modifiable instance
   */
  public static MutableRefType create() {
    return new MutableRefType();
  }

  /**
   * @return value of {@code typeOfRef} attribute
   */
  @JsonProperty("typeOfRef")
  @Override
  public final ValueTypeClass getTypeOfRef() {
    if (!typeOfRefIsSet()) {
      checkRequiredAttributes();
    }
    return typeOfRef;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableRefType clear() {
    initBits = 0x1L;
    typeOfRef = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IRefType} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableRefType from(IRefType instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableRefType) {
      from((MutableRefType) instance);
      return this;
    }
    setTypeOfRef(instance.getTypeOfRef());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IRefType} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableRefType from(MutableRefType instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.typeOfRefIsSet()) {
      setTypeOfRef(instance.getTypeOfRef());
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IRefType#getTypeOfRef() typeOfRef} attribute.
   * @param typeOfRef The value for typeOfRef
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableRefType setTypeOfRef(ValueTypeClass typeOfRef) {
    this.typeOfRef = Objects.requireNonNull(typeOfRef, "typeOfRef");
    initBits &= ~INIT_BIT_TYPE_OF_REF;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IRefType#getTypeOfRef() typeOfRef} is set.
   * @return {@code true} if set
   */
  public final boolean typeOfRefIsSet() {
    return (initBits & INIT_BIT_TYPE_OF_REF) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableRefType unsetTypeOfRef() {
    initBits |= INIT_BIT_TYPE_OF_REF;
    typeOfRef = null;
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
    if (!typeOfRefIsSet()) attributes.add("typeOfRef");
    return "RefType is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link RefType RefType}.
   * @return An immutable instance of RefType
   */
  public final RefType toImmutable() {
    checkRequiredAttributes();
    return RefType.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableRefType} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableRefType)) return false;
    MutableRefType other = (MutableRefType) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableRefType another) {
    return typeOfRef.equals(another.typeOfRef);
  }

  /**
   * Computes a hash code from attributes: {@code typeOfRef}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + typeOfRef.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IRefType}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableRefType")
        .add("typeOfRef", typeOfRefIsSet() ? getTypeOfRef() : "?")
        .toString();
  }
}
