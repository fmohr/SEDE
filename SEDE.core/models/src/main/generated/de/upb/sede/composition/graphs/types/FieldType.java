package de.upb.sede.composition.graphs.types;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
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
 * Immutable implementation of {@link IFieldType}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code FieldType.builder()}.
 */
@Generated(from = "IFieldType", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class FieldType implements IFieldType {
  private final TypeClass fieldType;
  private final String fieldName;

  private FieldType(TypeClass fieldType, String fieldName) {
    this.fieldType = fieldType;
    this.fieldName = fieldName;
  }

  /**
   * @return The value of the {@code fieldType} attribute
   */
  @JsonProperty("fieldType")
  @Override
  public TypeClass getFieldType() {
    return fieldType;
  }

  /**
   * @return The value of the {@code fieldName} attribute
   */
  @JsonProperty("fieldName")
  @Override
  public String getFieldName() {
    return fieldName;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IFieldType#getFieldType() fieldType} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for fieldType
   * @return A modified copy of the {@code this} object
   */
  public final FieldType withFieldType(TypeClass value) {
    if (this.fieldType == value) return this;
    TypeClass newValue = Objects.requireNonNull(value, "fieldType");
    return new FieldType(newValue, this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IFieldType#getFieldName() fieldName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for fieldName
   * @return A modified copy of the {@code this} object
   */
  public final FieldType withFieldName(String value) {
    String newValue = Objects.requireNonNull(value, "fieldName");
    if (this.fieldName.equals(newValue)) return this;
    return new FieldType(this.fieldType, newValue);
  }

  /**
   * This instance is equal to all instances of {@code FieldType} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof FieldType
        && equalTo((FieldType) another);
  }

  private boolean equalTo(FieldType another) {
    return fieldType.equals(another.fieldType)
        && fieldName.equals(another.fieldName);
  }

  /**
   * Computes a hash code from attributes: {@code fieldType}, {@code fieldName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + fieldType.hashCode();
    h += (h << 5) + fieldName.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code FieldType} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("FieldType")
        .omitNullValues()
        .add("fieldType", fieldType)
        .add("fieldName", fieldName)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IFieldType", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IFieldType {
    @Nullable TypeClass fieldType;
    @Nullable String fieldName;
    @JsonProperty("fieldType")
    public void setFieldType(TypeClass fieldType) {
      this.fieldType = fieldType;
    }
    @JsonProperty("fieldName")
    public void setFieldName(String fieldName) {
      this.fieldName = fieldName;
    }
    @Override
    public TypeClass getFieldType() { throw new UnsupportedOperationException(); }
    @Override
    public String getFieldName() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static FieldType fromJson(Json json) {
    FieldType.Builder builder = FieldType.builder();
    if (json.fieldType != null) {
      builder.fieldType(json.fieldType);
    }
    if (json.fieldName != null) {
      builder.fieldName(json.fieldName);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IFieldType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable FieldType instance
   */
  public static FieldType copyOf(IFieldType instance) {
    if (instance instanceof FieldType) {
      return (FieldType) instance;
    }
    return FieldType.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link FieldType FieldType}.
   * <pre>
   * FieldType.builder()
   *    .fieldType(de.upb.sede.composition.graphs.types.TypeClass) // required {@link IFieldType#getFieldType() fieldType}
   *    .fieldName(String) // required {@link IFieldType#getFieldName() fieldName}
   *    .build();
   * </pre>
   * @return A new FieldType builder
   */
  public static FieldType.Builder builder() {
    return new FieldType.Builder();
  }

  /**
   * Builds instances of type {@link FieldType FieldType}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IFieldType", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_FIELD_TYPE = 0x1L;
    private static final long INIT_BIT_FIELD_NAME = 0x2L;
    private long initBits = 0x3L;

    private @Nullable TypeClass fieldType;
    private @Nullable String fieldName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableFieldType} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableFieldType instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.fieldTypeIsSet()) {
        fieldType(instance.getFieldType());
      }
      if (instance.fieldNameIsSet()) {
        fieldName(instance.getFieldName());
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IFieldType} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IFieldType instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableFieldType) {
        return from((MutableFieldType) instance);
      }
      fieldType(instance.getFieldType());
      fieldName(instance.getFieldName());
      return this;
    }

    /**
     * Initializes the value for the {@link IFieldType#getFieldType() fieldType} attribute.
     * @param fieldType The value for fieldType 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("fieldType")
    public final Builder fieldType(TypeClass fieldType) {
      this.fieldType = Objects.requireNonNull(fieldType, "fieldType");
      initBits &= ~INIT_BIT_FIELD_TYPE;
      return this;
    }

    /**
     * Initializes the value for the {@link IFieldType#getFieldName() fieldName} attribute.
     * @param fieldName The value for fieldName 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("fieldName")
    public final Builder fieldName(String fieldName) {
      this.fieldName = Objects.requireNonNull(fieldName, "fieldName");
      initBits &= ~INIT_BIT_FIELD_NAME;
      return this;
    }

    /**
     * Builds a new {@link FieldType FieldType}.
     * @return An immutable instance of FieldType
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public FieldType build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new FieldType(fieldType, fieldName);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_FIELD_TYPE) != 0) attributes.add("fieldType");
      if ((initBits & INIT_BIT_FIELD_NAME) != 0) attributes.add("fieldName");
      return "Cannot build FieldType, some of required attributes are not set " + attributes;
    }
  }
}
