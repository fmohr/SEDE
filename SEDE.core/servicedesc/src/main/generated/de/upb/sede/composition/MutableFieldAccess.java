package de.upb.sede.composition;

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
 * A modifiable implementation of the {@link IFieldAccess IFieldAccess} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableFieldAccess is not thread-safe</em>
 * @see FieldAccess
 */
@Generated(from = "IFieldAccess", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IFieldAccess"})
@NotThreadSafe
public final class MutableFieldAccess implements IFieldAccess {
  private static final long INIT_BIT_FIELD = 0x1L;
  private static final long INIT_BIT_ACCESS_TYPE = 0x2L;
  private static final long INIT_BIT_INDEX = 0x4L;
  private long initBits = 0x7L;

  private String field;
  private IFieldAccess.AccessType accessType;
  private Long index;

  private MutableFieldAccess() {}

  /**
   * Construct a modifiable instance of {@code IFieldAccess}.
   * @return A new modifiable instance
   */
  public static MutableFieldAccess create() {
    return new MutableFieldAccess();
  }

  /**
   * @return value of {@code field} attribute
   */
  @JsonProperty("field")
  @Override
  public final String getField() {
    if (!fieldIsSet()) {
      checkRequiredAttributes();
    }
    return field;
  }

  /**
   * @return value of {@code accessType} attribute
   */
  @JsonProperty("accessType")
  @Override
  public final IFieldAccess.AccessType getAccessType() {
    if (!accessTypeIsSet()) {
      checkRequiredAttributes();
    }
    return accessType;
  }

  /**
   * @return value of {@code index} attribute
   */
  @JsonProperty("index")
  @Override
  public final Long getIndex() {
    if (!indexIsSet()) {
      checkRequiredAttributes();
    }
    return index;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFieldAccess clear() {
    initBits = 0x7L;
    field = null;
    accessType = null;
    index = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IFieldAccess} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableFieldAccess from(IFieldAccess instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableFieldAccess) {
      from((MutableFieldAccess) instance);
      return this;
    }
    setField(instance.getField());
    setAccessType(instance.getAccessType());
    setIndex(instance.getIndex());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IFieldAccess} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableFieldAccess from(MutableFieldAccess instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.fieldIsSet()) {
      setField(instance.getField());
    }
    if (instance.accessTypeIsSet()) {
      setAccessType(instance.getAccessType());
    }
    if (instance.indexIsSet()) {
      setIndex(instance.getIndex());
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IFieldAccess#getField() field} attribute.
   * @param field The value for field
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFieldAccess setField(String field) {
    this.field = Objects.requireNonNull(field, "field");
    initBits &= ~INIT_BIT_FIELD;
    return this;
  }

  /**
   * Assigns a value to the {@link IFieldAccess#getAccessType() accessType} attribute.
   * @param accessType The value for accessType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFieldAccess setAccessType(IFieldAccess.AccessType accessType) {
    this.accessType = Objects.requireNonNull(accessType, "accessType");
    initBits &= ~INIT_BIT_ACCESS_TYPE;
    return this;
  }

  /**
   * Assigns a value to the {@link IFieldAccess#getIndex() index} attribute.
   * @param index The value for index
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFieldAccess setIndex(Long index) {
    this.index = Objects.requireNonNull(index, "index");
    initBits &= ~INIT_BIT_INDEX;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IFieldAccess#getField() field} is set.
   * @return {@code true} if set
   */
  public final boolean fieldIsSet() {
    return (initBits & INIT_BIT_FIELD) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IFieldAccess#getAccessType() accessType} is set.
   * @return {@code true} if set
   */
  public final boolean accessTypeIsSet() {
    return (initBits & INIT_BIT_ACCESS_TYPE) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IFieldAccess#getIndex() index} is set.
   * @return {@code true} if set
   */
  public final boolean indexIsSet() {
    return (initBits & INIT_BIT_INDEX) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableFieldAccess unsetField() {
    initBits |= INIT_BIT_FIELD;
    field = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableFieldAccess unsetAccessType() {
    initBits |= INIT_BIT_ACCESS_TYPE;
    accessType = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableFieldAccess unsetIndex() {
    initBits |= INIT_BIT_INDEX;
    index = null;
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
    if (!fieldIsSet()) attributes.add("field");
    if (!accessTypeIsSet()) attributes.add("accessType");
    if (!indexIsSet()) attributes.add("index");
    return "FieldAccess is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link FieldAccess FieldAccess}.
   * @return An immutable instance of FieldAccess
   */
  public final FieldAccess toImmutable() {
    checkRequiredAttributes();
    return FieldAccess.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableFieldAccess} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableFieldAccess)) return false;
    MutableFieldAccess other = (MutableFieldAccess) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableFieldAccess another) {
    return field.equals(another.field)
        && accessType.equals(another.accessType)
        && index.equals(another.index);
  }

  /**
   * Computes a hash code from attributes: {@code field}, {@code accessType}, {@code index}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + field.hashCode();
    h += (h << 5) + accessType.hashCode();
    h += (h << 5) + index.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IFieldAccess}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableFieldAccess")
        .add("field", fieldIsSet() ? getField() : "?")
        .add("accessType", accessTypeIsSet() ? getAccessType() : "?")
        .add("index", indexIsSet() ? getIndex() : "?")
        .toString();
  }
}
