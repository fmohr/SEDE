package de.upb.sede.composition;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.composition.graphs.types.TypeClass;
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
  private final String fieldname;
  private final TypeClass type;

  private FieldType(String fieldname, TypeClass type) {
    this.fieldname = fieldname;
    this.type = type;
  }

  /**
   * @return The value of the {@code fieldname} attribute
   */
  @JsonProperty("fieldname")
  @Override
  public String getFieldname() {
    return fieldname;
  }

  /**
   * @return The value of the {@code type} attribute
   */
  @JsonProperty("type")
  @Override
  public TypeClass getType() {
    return type;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IFieldType#getFieldname() fieldname} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for fieldname
   * @return A modified copy of the {@code this} object
   */
  public final FieldType withFieldname(String value) {
    String newValue = Objects.requireNonNull(value, "fieldname");
    if (this.fieldname.equals(newValue)) return this;
    return new FieldType(newValue, this.type);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IFieldType#getType() type} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for type
   * @return A modified copy of the {@code this} object
   */
  public final FieldType withType(TypeClass value) {
    if (this.type == value) return this;
    TypeClass newValue = Objects.requireNonNull(value, "type");
    return new FieldType(this.fieldname, newValue);
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
    return fieldname.equals(another.fieldname)
        && type.equals(another.type);
  }

  /**
   * Computes a hash code from attributes: {@code fieldname}, {@code type}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + fieldname.hashCode();
    h += (h << 5) + type.hashCode();
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
        .add("fieldname", fieldname)
        .add("type", type)
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
    @Nullable String fieldname;
    @Nullable TypeClass type;
    @JsonProperty("fieldname")
    public void setFieldname(String fieldname) {
      this.fieldname = fieldname;
    }
    @JsonProperty("type")
    public void setType(TypeClass type) {
      this.type = type;
    }
    @Override
    public String getFieldname() { throw new UnsupportedOperationException(); }
    @Override
    public TypeClass getType() { throw new UnsupportedOperationException(); }
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
    if (json.fieldname != null) {
      builder.fieldname(json.fieldname);
    }
    if (json.type != null) {
      builder.type(json.type);
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
   *    .fieldname(String) // required {@link IFieldType#getFieldname() fieldname}
   *    .type(de.upb.sede.composition.graphs.types.TypeClass) // required {@link IFieldType#getType() type}
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
    private static final long INIT_BIT_FIELDNAME = 0x1L;
    private static final long INIT_BIT_TYPE = 0x2L;
    private long initBits = 0x3L;

    private @Nullable String fieldname;
    private @Nullable TypeClass type;

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
      if (instance.fieldnameIsSet()) {
        fieldname(instance.getFieldname());
      }
      if (instance.typeIsSet()) {
        type(instance.getType());
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
      fieldname(instance.getFieldname());
      type(instance.getType());
      return this;
    }

    /**
     * Initializes the value for the {@link IFieldType#getFieldname() fieldname} attribute.
     * @param fieldname The value for fieldname 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("fieldname")
    public final Builder fieldname(String fieldname) {
      this.fieldname = Objects.requireNonNull(fieldname, "fieldname");
      initBits &= ~INIT_BIT_FIELDNAME;
      return this;
    }

    /**
     * Initializes the value for the {@link IFieldType#getType() type} attribute.
     * @param type The value for type 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("type")
    public final Builder type(TypeClass type) {
      this.type = Objects.requireNonNull(type, "type");
      initBits &= ~INIT_BIT_TYPE;
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
      return new FieldType(fieldname, type);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_FIELDNAME) != 0) attributes.add("fieldname");
      if ((initBits & INIT_BIT_TYPE) != 0) attributes.add("type");
      return "Cannot build FieldType, some of required attributes are not set " + attributes;
    }
  }
}
