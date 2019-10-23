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
  private final String type;
  private final @Nullable String name;
  private final @Nullable String fixedValue;
  private final boolean callByValue;

  private MethodParameterDesc(MethodParameterDesc.Builder builder) {
    this.type = builder.type;
    this.name = builder.name;
    this.fixedValue = builder.fixedValue;
    this.callByValue = builder.callByValueIsSet()
        ? builder.callByValue
        : IMethodParameterDesc.super.callByValue();
  }

  private MethodParameterDesc(
      String type,
      @Nullable String name,
      @Nullable String fixedValue,
      boolean callByValue) {
    this.type = type;
    this.name = name;
    this.fixedValue = fixedValue;
    this.callByValue = callByValue;
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
   * @return The value of the {@code name} attribute
   */
  @JsonProperty("name")
  @Override
  public @Nullable String getName() {
    return name;
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
   * @return The value of the {@code callByValue} attribute
   */
  @JsonProperty("callByValue")
  @Override
  public boolean callByValue() {
    return callByValue;
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
    return new MethodParameterDesc(newValue, this.name, this.fixedValue, this.callByValue);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IMethodParameterDesc#getName() name} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for name (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final MethodParameterDesc withName(@Nullable String value) {
    if (Objects.equals(this.name, value)) return this;
    return new MethodParameterDesc(this.type, value, this.fixedValue, this.callByValue);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IMethodParameterDesc#getFixedValue() fixedValue} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for fixedValue (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final MethodParameterDesc withFixedValue(@Nullable String value) {
    if (Objects.equals(this.fixedValue, value)) return this;
    return new MethodParameterDesc(this.type, this.name, value, this.callByValue);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IMethodParameterDesc#callByValue() callByValue} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for callByValue
   * @return A modified copy of the {@code this} object
   */
  public final MethodParameterDesc withCallByValue(boolean value) {
    if (this.callByValue == value) return this;
    return new MethodParameterDesc(this.type, this.name, this.fixedValue, value);
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
    return type.equals(another.type)
        && Objects.equals(name, another.name)
        && Objects.equals(fixedValue, another.fixedValue)
        && callByValue == another.callByValue;
  }

  /**
   * Computes a hash code from attributes: {@code type}, {@code name}, {@code fixedValue}, {@code callByValue}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + type.hashCode();
    h += (h << 5) + Objects.hashCode(name);
    h += (h << 5) + Objects.hashCode(fixedValue);
    h += (h << 5) + Booleans.hashCode(callByValue);
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
        .add("type", type)
        .add("name", name)
        .add("fixedValue", fixedValue)
        .add("callByValue", callByValue)
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
    @Nullable String type;
    @Nullable String name;
    @Nullable String fixedValue;
    boolean callByValue;
    boolean callByValueIsSet;
    @JsonProperty("type")
    public void setType(String type) {
      this.type = type;
    }
    @JsonProperty("name")
    public void setName(@Nullable String name) {
      this.name = name;
    }
    @JsonProperty("fixedValue")
    public void setFixedValue(@Nullable String fixedValue) {
      this.fixedValue = fixedValue;
    }
    @JsonProperty("callByValue")
    public void setCallByValue(boolean callByValue) {
      this.callByValue = callByValue;
      this.callByValueIsSet = true;
    }
    @Override
    public String getType() { throw new UnsupportedOperationException(); }
    @Override
    public String getName() { throw new UnsupportedOperationException(); }
    @Override
    public String getFixedValue() { throw new UnsupportedOperationException(); }
    @Override
    public boolean callByValue() { throw new UnsupportedOperationException(); }
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
    if (json.type != null) {
      builder.type(json.type);
    }
    if (json.name != null) {
      builder.name(json.name);
    }
    if (json.fixedValue != null) {
      builder.fixedValue(json.fixedValue);
    }
    if (json.callByValueIsSet) {
      builder.callByValue(json.callByValue);
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
   *    .type(String) // required {@link IMethodParameterDesc#getType() type}
   *    .name(String | null) // nullable {@link IMethodParameterDesc#getName() name}
   *    .fixedValue(String | null) // nullable {@link IMethodParameterDesc#getFixedValue() fixedValue}
   *    .callByValue(boolean) // optional {@link IMethodParameterDesc#callByValue() callByValue}
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
    private static final long OPT_BIT_CALL_BY_VALUE = 0x1L;
    private long initBits = 0x1L;
    private long optBits;

    private @Nullable String type;
    private @Nullable String name;
    private @Nullable String fixedValue;
    private boolean callByValue;

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
      if (instance.typeIsSet()) {
        type(instance.getType());
      }
      @Nullable String nameValue = instance.getName();
      if (nameValue != null) {
        name(nameValue);
      }
      @Nullable String fixedValueValue = instance.getFixedValue();
      if (fixedValueValue != null) {
        fixedValue(fixedValueValue);
      }
      callByValue(instance.callByValue());
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
      type(instance.getType());
      @Nullable String nameValue = instance.getName();
      if (nameValue != null) {
        name(nameValue);
      }
      @Nullable String fixedValueValue = instance.getFixedValue();
      if (fixedValueValue != null) {
        fixedValue(fixedValueValue);
      }
      callByValue(instance.callByValue());
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
     * Initializes the value for the {@link IMethodParameterDesc#getName() name} attribute.
     * @param name The value for name (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("name")
    public final Builder name(@Nullable String name) {
      this.name = name;
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
     * Initializes the value for the {@link IMethodParameterDesc#callByValue() callByValue} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IMethodParameterDesc#callByValue() callByValue}.</em>
     * @param callByValue The value for callByValue 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("callByValue")
    public final Builder callByValue(boolean callByValue) {
      this.callByValue = callByValue;
      optBits |= OPT_BIT_CALL_BY_VALUE;
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

    private boolean callByValueIsSet() {
      return (optBits & OPT_BIT_CALL_BY_VALUE) != 0;
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_TYPE) != 0) attributes.add("type");
      return "Cannot build MethodParameterDesc, some of required attributes are not set " + attributes;
    }
  }
}
