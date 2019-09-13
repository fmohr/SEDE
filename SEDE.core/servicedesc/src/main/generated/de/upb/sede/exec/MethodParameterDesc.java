package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link IMethodParameterDesc}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code MethodParameterDesc.builder()}.
 */
@Generated(from = "IMethodParameterDesc", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class MethodParameterDesc implements IMethodParameterDesc {
  private final boolean isMutable;
  private final @Nullable String name;
  private final String type;
  private final @Nullable String fixedValue;

  private MethodParameterDesc(MethodParameterDesc.Builder builder) {
    this.name = builder.name;
    this.type = builder.type;
    this.fixedValue = builder.fixedValue;
    this.isMutable = builder.isMutableIsSet()
        ? builder.isMutable
        : IMethodParameterDesc.super.isMutable();
  }

  private MethodParameterDesc(
      boolean isMutable,
      @Nullable String name,
      String type,
      @Nullable String fixedValue) {
    this.isMutable = isMutable;
    this.name = name;
    this.type = type;
    this.fixedValue = fixedValue;
  }

  /**
   * @return The value of the {@code isMutable} attribute
   */
  @JsonProperty("isMutable")
  @Override
  public boolean isMutable() {
    return isMutable;
  }

  /**
   * @return The value of the {@code name} attribute
   */
  @JsonProperty("name")
  @Override
  public Optional<String> getName() {
    return Optional.ofNullable(name);
  }

  /**
   * @return The value of the {@code type} attribute
   */
  @JsonProperty("type")
  @Override
  public String getType() {
    return type;
  }

  /**
   * @return The value of the {@code fixedValue} attribute
   */
  @JsonProperty("fixedValue")
  @Override
  public @Nullable String getFixedValue() {
    return fixedValue;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IMethodParameterDesc#isMutable() isMutable} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isMutable
   * @return A modified copy of the {@code this} object
   */
  public final MethodParameterDesc withIsMutable(boolean value) {
    if (this.isMutable == value) return this;
    return new MethodParameterDesc(value, this.name, this.type, this.fixedValue);
  }

  /**
   * Copy the current immutable object by setting a <i>present</i> value for the optional {@link IMethodParameterDesc#getName() name} attribute.
   * @param value The value for name
   * @return A modified copy of {@code this} object
   */
  public final MethodParameterDesc withName(String value) {
    @Nullable String newValue = Objects.requireNonNull(value, "name");
    if (Objects.equals(this.name, newValue)) return this;
    return new MethodParameterDesc(this.isMutable, newValue, this.type, this.fixedValue);
  }

  /**
   * Copy the current immutable object by setting an optional value for the {@link IMethodParameterDesc#getName() name} attribute.
   * An equality check is used on inner nullable value to prevent copying of the same value by returning {@code this}.
   * @param optional A value for name
   * @return A modified copy of {@code this} object
   */
  public final MethodParameterDesc withName(Optional<String> optional) {
    @Nullable String value = optional.orElse(null);
    if (Objects.equals(this.name, value)) return this;
    return new MethodParameterDesc(this.isMutable, value, this.type, this.fixedValue);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IMethodParameterDesc#getType() type} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for type
   * @return A modified copy of the {@code this} object
   */
  public final MethodParameterDesc withType(String value) {
    String newValue = Objects.requireNonNull(value, "type");
    if (this.type.equals(newValue)) return this;
    return new MethodParameterDesc(this.isMutable, this.name, newValue, this.fixedValue);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IMethodParameterDesc#getFixedValue() fixedValue} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for fixedValue (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final MethodParameterDesc withFixedValue(@Nullable String value) {
    if (Objects.equals(this.fixedValue, value)) return this;
    return new MethodParameterDesc(this.isMutable, this.name, this.type, value);
  }

  /**
   * This instance is equal to all instances of {@code MethodParameterDesc} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof MethodParameterDesc
        && equalTo((MethodParameterDesc) another);
  }

  private boolean equalTo(MethodParameterDesc another) {
    return isMutable == another.isMutable
        && Objects.equals(name, another.name)
        && type.equals(another.type)
        && Objects.equals(fixedValue, another.fixedValue);
  }

  /**
   * Computes a hash code from attributes: {@code isMutable}, {@code name}, {@code type}, {@code fixedValue}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Booleans.hashCode(isMutable);
    h += (h << 5) + Objects.hashCode(name);
    h += (h << 5) + type.hashCode();
    h += (h << 5) + Objects.hashCode(fixedValue);
    return h;
  }

  /**
   * Prints the immutable value {@code MethodParameterDesc} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MethodParameterDesc")
        .omitNullValues()
        .add("isMutable", isMutable)
        .add("name", name)
        .add("type", type)
        .add("fixedValue", fixedValue)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IMethodParameterDesc", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IMethodParameterDesc {
    boolean isMutable;
    boolean isMutableIsSet;
    @Nullable Optional<String> name = Optional.empty();
    @Nullable String type;
    @Nullable String fixedValue;
    @JsonProperty("isMutable")
    public void setIsMutable(boolean isMutable) {
      this.isMutable = isMutable;
      this.isMutableIsSet = true;
    }
    @JsonProperty("name")
    public void setName(Optional<String> name) {
      this.name = name;
    }
    @JsonProperty("type")
    public void setType(String type) {
      this.type = type;
    }
    @JsonProperty("fixedValue")
    public void setFixedValue(@Nullable String fixedValue) {
      this.fixedValue = fixedValue;
    }
    @Override
    public boolean isMutable() { throw new UnsupportedOperationException(); }
    @Override
    public Optional<String> getName() { throw new UnsupportedOperationException(); }
    @Override
    public String getType() { throw new UnsupportedOperationException(); }
    @Override
    public String getFixedValue() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static MethodParameterDesc fromJson(Json json) {
    MethodParameterDesc.Builder builder = MethodParameterDesc.builder();
    if (json.isMutableIsSet) {
      builder.isMutable(json.isMutable);
    }
    if (json.name != null) {
      builder.name(json.name);
    }
    if (json.type != null) {
      builder.type(json.type);
    }
    if (json.fixedValue != null) {
      builder.fixedValue(json.fixedValue);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IMethodParameterDesc} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable MethodParameterDesc instance
   */
  public static MethodParameterDesc copyOf(IMethodParameterDesc instance) {
    if (instance instanceof MethodParameterDesc) {
      return (MethodParameterDesc) instance;
    }
    return MethodParameterDesc.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link MethodParameterDesc MethodParameterDesc}.
   * <pre>
   * MethodParameterDesc.builder()
   *    .isMutable(boolean) // optional {@link IMethodParameterDesc#isMutable() isMutable}
   *    .name(String) // optional {@link IMethodParameterDesc#getName() name}
   *    .type(String) // required {@link IMethodParameterDesc#getType() type}
   *    .fixedValue(String | null) // nullable {@link IMethodParameterDesc#getFixedValue() fixedValue}
   *    .build();
   * </pre>
   * @return A new MethodParameterDesc builder
   */
  public static MethodParameterDesc.Builder builder() {
    return new MethodParameterDesc.Builder();
  }

  /**
   * Builds instances of type {@link MethodParameterDesc MethodParameterDesc}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IMethodParameterDesc", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_TYPE = 0x1L;
    private static final long OPT_BIT_IS_MUTABLE = 0x1L;
    private long initBits = 0x1L;
    private long optBits;

    private boolean isMutable;
    private @Nullable String name;
    private @Nullable String type;
    private @Nullable String fixedValue;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableMethodParameterDesc} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableMethodParameterDesc instance) {
      Objects.requireNonNull(instance, "instance");
      isMutable(instance.isMutable());
      Optional<String> nameOptional = instance.getName();
      if (nameOptional.isPresent()) {
        name(nameOptional);
      }
      if (instance.typeIsSet()) {
        type(instance.getType());
      }
      @Nullable String fixedValueValue = instance.getFixedValue();
      if (fixedValueValue != null) {
        fixedValue(fixedValueValue);
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IMethodParameterDesc} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IMethodParameterDesc instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableMethodParameterDesc) {
        return from((MutableMethodParameterDesc) instance);
      }
      isMutable(instance.isMutable());
      Optional<String> nameOptional = instance.getName();
      if (nameOptional.isPresent()) {
        name(nameOptional);
      }
      type(instance.getType());
      @Nullable String fixedValueValue = instance.getFixedValue();
      if (fixedValueValue != null) {
        fixedValue(fixedValueValue);
      }
      return this;
    }

    /**
     * Initializes the value for the {@link IMethodParameterDesc#isMutable() isMutable} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IMethodParameterDesc#isMutable() isMutable}.</em>
     * @param isMutable The value for isMutable 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("isMutable")
    public final Builder isMutable(boolean isMutable) {
      this.isMutable = isMutable;
      optBits |= OPT_BIT_IS_MUTABLE;
      return this;
    }

    /**
     * Initializes the optional value {@link IMethodParameterDesc#getName() name} to name.
     * @param name The value for name
     * @return {@code this} builder for chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder name(String name) {
      this.name = Objects.requireNonNull(name, "name");
      return this;
    }

    /**
     * Initializes the optional value {@link IMethodParameterDesc#getName() name} to name.
     * @param name The value for name
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("name")
    public final Builder name(Optional<String> name) {
      this.name = name.orElse(null);
      return this;
    }

    /**
     * Initializes the value for the {@link IMethodParameterDesc#getType() type} attribute.
     * @param type The value for type 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("type")
    public final Builder type(String type) {
      this.type = Objects.requireNonNull(type, "type");
      initBits &= ~INIT_BIT_TYPE;
      return this;
    }

    /**
     * Initializes the value for the {@link IMethodParameterDesc#getFixedValue() fixedValue} attribute.
     * @param fixedValue The value for fixedValue (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("fixedValue")
    public final Builder fixedValue(@Nullable String fixedValue) {
      this.fixedValue = fixedValue;
      return this;
    }

    /**
     * Builds a new {@link MethodParameterDesc MethodParameterDesc}.
     * @return An immutable instance of MethodParameterDesc
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public MethodParameterDesc build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new MethodParameterDesc(this);
    }

    private boolean isMutableIsSet() {
      return (optBits & OPT_BIT_IS_MUTABLE) != 0;
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_TYPE) != 0) attributes.add("type");
      return "Cannot build MethodParameterDesc, some of required attributes are not set " + attributes;
    }
  }
}
