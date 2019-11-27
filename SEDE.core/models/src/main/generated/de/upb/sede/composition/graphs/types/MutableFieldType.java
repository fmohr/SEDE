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
 * A modifiable implementation of the {@link IFieldType IFieldType} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableFieldType is not thread-safe</em>
 * @see FieldType
 */
@Generated(from = "IFieldType", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IFieldType"})
@NotThreadSafe
public final class MutableFieldType implements IFieldType {
  private static final long INIT_BIT_FIELD_TYPE = 0x1L;
  private static final long INIT_BIT_FIELD_NAME = 0x2L;
  private long initBits = 0x3L;

  private TypeClass fieldType;
  private String fieldName;

  private MutableFieldType() {}

  /**
   * Construct a modifiable instance of {@code IFieldType}.
   * @return A new modifiable instance
   */
  public static MutableFieldType create() {
    return new MutableFieldType();
  }

  /**
   * @return value of {@code fieldType} attribute
   */
  @JsonProperty("fieldType")
  @Override
  public final TypeClass getFieldType() {
    if (!fieldTypeIsSet()) {
      checkRequiredAttributes();
    }
    return fieldType;
  }

  /**
   * @return value of {@code fieldName} attribute
   */
  @JsonProperty("fieldName")
  @Override
  public final String getFieldName() {
    if (!fieldNameIsSet()) {
      checkRequiredAttributes();
    }
    return fieldName;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFieldType clear() {
    initBits = 0x3L;
    fieldType = null;
    fieldName = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IFieldType} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableFieldType from(IFieldType instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableFieldType) {
      from((MutableFieldType) instance);
      return this;
    }
    setFieldType(instance.getFieldType());
    setFieldName(instance.getFieldName());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IFieldType} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableFieldType from(MutableFieldType instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.fieldTypeIsSet()) {
      setFieldType(instance.getFieldType());
    }
    if (instance.fieldNameIsSet()) {
      setFieldName(instance.getFieldName());
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IFieldType#getFieldType() fieldType} attribute.
   * @param fieldType The value for fieldType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFieldType setFieldType(TypeClass fieldType) {
    this.fieldType = Objects.requireNonNull(fieldType, "fieldType");
    initBits &= ~INIT_BIT_FIELD_TYPE;
    return this;
  }

  /**
   * Assigns a value to the {@link IFieldType#getFieldName() fieldName} attribute.
   * @param fieldName The value for fieldName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFieldType setFieldName(String fieldName) {
    this.fieldName = Objects.requireNonNull(fieldName, "fieldName");
    initBits &= ~INIT_BIT_FIELD_NAME;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IFieldType#getFieldType() fieldType} is set.
   * @return {@code true} if set
   */
  public final boolean fieldTypeIsSet() {
    return (initBits & INIT_BIT_FIELD_TYPE) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IFieldType#getFieldName() fieldName} is set.
   * @return {@code true} if set
   */
  public final boolean fieldNameIsSet() {
    return (initBits & INIT_BIT_FIELD_NAME) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableFieldType unsetFieldType() {
    initBits |= INIT_BIT_FIELD_TYPE;
    fieldType = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableFieldType unsetFieldName() {
    initBits |= INIT_BIT_FIELD_NAME;
    fieldName = null;
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
    if (!fieldTypeIsSet()) attributes.add("fieldType");
    if (!fieldNameIsSet()) attributes.add("fieldName");
    return "FieldType is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link FieldType FieldType}.
   * @return An immutable instance of FieldType
   */
  public final FieldType toImmutable() {
    checkRequiredAttributes();
    return FieldType.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableFieldType} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableFieldType)) return false;
    MutableFieldType other = (MutableFieldType) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableFieldType another) {
    return fieldType.equals(another.fieldType)
        && fieldName.equals(another.fieldName);
  }

  /**
   * Computes a hash code from attributes: {@code fieldType}, {@code fieldName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + fieldType.hashCode();
    h += (h << 5) + fieldName.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IFieldType}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableFieldType")
        .add("fieldType", fieldTypeIsSet() ? getFieldType() : "?")
        .add("fieldName", fieldNameIsSet() ? getFieldName() : "?")
        .toString();
  }
}
