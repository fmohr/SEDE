package de.upb.sede.composition;

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
 * Immutable implementation of {@link IFieldAccess}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code FieldAccess.builder()}.
 */
@Generated(from = "IFieldAccess", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class FieldAccess implements IFieldAccess {
  private final String field;
  private final IFieldAccess.AccessType accessType;
  private final Long index;

  private FieldAccess(
      String field,
      IFieldAccess.AccessType accessType,
      Long index) {
    this.field = field;
    this.accessType = accessType;
    this.index = index;
  }

  /**
   * @return The value of the {@code field} attribute
   */
  @JsonProperty("field")
  @Override
  public String getField() {
    return field;
  }

  /**
   * @return The value of the {@code accessType} attribute
   */
  @JsonProperty("accessType")
  @Override
  public IFieldAccess.AccessType getAccessType() {
    return accessType;
  }

  /**
   * @return The value of the {@code index} attribute
   */
  @JsonProperty("index")
  @Override
  public Long getIndex() {
    return index;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IFieldAccess#getField() field} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for field
   * @return A modified copy of the {@code this} object
   */
  public final FieldAccess withField(String value) {
    String newValue = Objects.requireNonNull(value, "field");
    if (this.field.equals(newValue)) return this;
    return new FieldAccess(newValue, this.accessType, this.index);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IFieldAccess#getAccessType() accessType} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for accessType
   * @return A modified copy of the {@code this} object
   */
  public final FieldAccess withAccessType(IFieldAccess.AccessType value) {
    if (this.accessType == value) return this;
    IFieldAccess.AccessType newValue = Objects.requireNonNull(value, "accessType");
    if (this.accessType.equals(newValue)) return this;
    return new FieldAccess(this.field, newValue, this.index);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IFieldAccess#getIndex() index} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for index
   * @return A modified copy of the {@code this} object
   */
  public final FieldAccess withIndex(Long value) {
    Long newValue = Objects.requireNonNull(value, "index");
    if (this.index.equals(newValue)) return this;
    return new FieldAccess(this.field, this.accessType, newValue);
  }

  /**
   * This instance is equal to all instances of {@code FieldAccess} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof FieldAccess
        && equalTo((FieldAccess) another);
  }

  private boolean equalTo(FieldAccess another) {
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
    @Var int h = 5381;
    h += (h << 5) + field.hashCode();
    h += (h << 5) + accessType.hashCode();
    h += (h << 5) + index.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code FieldAccess} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("FieldAccess")
        .omitNullValues()
        .add("field", field)
        .add("accessType", accessType)
        .add("index", index)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IFieldAccess", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IFieldAccess {
    @Nullable String field;
    @Nullable IFieldAccess.AccessType accessType;
    @Nullable Long index;
    @JsonProperty("field")
    public void setField(String field) {
      this.field = field;
    }
    @JsonProperty("accessType")
    public void setAccessType(IFieldAccess.AccessType accessType) {
      this.accessType = accessType;
    }
    @JsonProperty("index")
    public void setIndex(Long index) {
      this.index = index;
    }
    @Override
    public String getField() { throw new UnsupportedOperationException(); }
    @Override
    public IFieldAccess.AccessType getAccessType() { throw new UnsupportedOperationException(); }
    @Override
    public Long getIndex() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static FieldAccess fromJson(Json json) {
    FieldAccess.Builder builder = FieldAccess.builder();
    if (json.field != null) {
      builder.field(json.field);
    }
    if (json.accessType != null) {
      builder.accessType(json.accessType);
    }
    if (json.index != null) {
      builder.index(json.index);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IFieldAccess} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable FieldAccess instance
   */
  public static FieldAccess copyOf(IFieldAccess instance) {
    if (instance instanceof FieldAccess) {
      return (FieldAccess) instance;
    }
    return FieldAccess.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link FieldAccess FieldAccess}.
   * <pre>
   * FieldAccess.builder()
   *    .field(String) // required {@link IFieldAccess#getField() field}
   *    .accessType(de.upb.sede.composition.IFieldAccess.AccessType) // required {@link IFieldAccess#getAccessType() accessType}
   *    .index(Long) // required {@link IFieldAccess#getIndex() index}
   *    .build();
   * </pre>
   * @return A new FieldAccess builder
   */
  public static FieldAccess.Builder builder() {
    return new FieldAccess.Builder();
  }

  /**
   * Builds instances of type {@link FieldAccess FieldAccess}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IFieldAccess", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_FIELD = 0x1L;
    private static final long INIT_BIT_ACCESS_TYPE = 0x2L;
    private static final long INIT_BIT_INDEX = 0x4L;
    private long initBits = 0x7L;

    private @Nullable String field;
    private @Nullable IFieldAccess.AccessType accessType;
    private @Nullable Long index;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableFieldAccess} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableFieldAccess instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.fieldIsSet()) {
        field(instance.getField());
      }
      if (instance.accessTypeIsSet()) {
        accessType(instance.getAccessType());
      }
      if (instance.indexIsSet()) {
        index(instance.getIndex());
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IFieldAccess} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IFieldAccess instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableFieldAccess) {
        return from((MutableFieldAccess) instance);
      }
      field(instance.getField());
      accessType(instance.getAccessType());
      index(instance.getIndex());
      return this;
    }

    /**
     * Initializes the value for the {@link IFieldAccess#getField() field} attribute.
     * @param field The value for field 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("field")
    public final Builder field(String field) {
      this.field = Objects.requireNonNull(field, "field");
      initBits &= ~INIT_BIT_FIELD;
      return this;
    }

    /**
     * Initializes the value for the {@link IFieldAccess#getAccessType() accessType} attribute.
     * @param accessType The value for accessType 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("accessType")
    public final Builder accessType(IFieldAccess.AccessType accessType) {
      this.accessType = Objects.requireNonNull(accessType, "accessType");
      initBits &= ~INIT_BIT_ACCESS_TYPE;
      return this;
    }

    /**
     * Initializes the value for the {@link IFieldAccess#getIndex() index} attribute.
     * @param index The value for index 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("index")
    public final Builder index(Long index) {
      this.index = Objects.requireNonNull(index, "index");
      initBits &= ~INIT_BIT_INDEX;
      return this;
    }

    /**
     * Builds a new {@link FieldAccess FieldAccess}.
     * @return An immutable instance of FieldAccess
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public FieldAccess build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new FieldAccess(field, accessType, index);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_FIELD) != 0) attributes.add("field");
      if ((initBits & INIT_BIT_ACCESS_TYPE) != 0) attributes.add("accessType");
      if ((initBits & INIT_BIT_INDEX) != 0) attributes.add("index");
      return "Cannot build FieldAccess, some of required attributes are not set " + attributes;
    }
  }
}
