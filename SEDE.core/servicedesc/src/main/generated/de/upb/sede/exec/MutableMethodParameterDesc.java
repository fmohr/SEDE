package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IMethodParameterDesc IMethodParameterDesc} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableMethodParameterDesc is not thread-safe</em>
 * @see MethodParameterDesc
 */
@Generated(from = "IMethodParameterDesc", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IMethodParameterDesc"})
@NotThreadSafe
public final class MutableMethodParameterDesc implements IMethodParameterDesc {
  private static final long INIT_BIT_TYPE = 0x1L;
  private static final long OPT_BIT_IS_MUTABLE = 0x1L;
  private long initBits = 0x1L;
  private long optBits;

  private String type;
  private @Nullable String name;
  private @Nullable String fixedValue;
  private boolean isMutable;

  private MutableMethodParameterDesc() {}

  /**
   * Construct a modifiable instance of {@code IMethodParameterDesc}.
   * @return A new modifiable instance
   */
  public static MutableMethodParameterDesc create() {
    return new MutableMethodParameterDesc();
  }

  /**
   * @return value of {@code type} attribute
   */
  @JsonProperty("type")
  @Override
  public final String getType() {
    if (!typeIsSet()) {
      checkRequiredAttributes();
    }
    return type;
  }

  /**
   * @return value of {@code name} attribute, may be {@code null}
   */
  @JsonProperty("name")
  @Override
  public final @Nullable String getName() {
    return name;
  }

  /**
   * @return value of {@code fixedValue} attribute, may be {@code null}
   */
  @JsonProperty("fixedValue")
  @Override
  public final @Nullable String getFixedValue() {
    return fixedValue;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code isMutable} attribute
   */
  @JsonProperty("isMutable")
  @Override
  public final boolean isMutable() {
    return isMutableIsSet()
        ? isMutable
        : IMethodParameterDesc.super.isMutable();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodParameterDesc clear() {
    initBits = 0x1L;
    optBits = 0;
    type = null;
    name = null;
    fixedValue = null;
    isMutable = false;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IMethodParameterDesc} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableMethodParameterDesc from(IMethodParameterDesc instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableMethodParameterDesc) {
      from((MutableMethodParameterDesc) instance);
      return this;
    }
    setType(instance.getType());
    @Nullable String nameValue = instance.getName();
    if (nameValue != null) {
      setName(nameValue);
    }
    @Nullable String fixedValueValue = instance.getFixedValue();
    if (fixedValueValue != null) {
      setFixedValue(fixedValueValue);
    }
    setIsMutable(instance.isMutable());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IMethodParameterDesc} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableMethodParameterDesc from(MutableMethodParameterDesc instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.typeIsSet()) {
      setType(instance.getType());
    }
    @Nullable String nameValue = instance.getName();
    if (nameValue != null) {
      setName(nameValue);
    }
    @Nullable String fixedValueValue = instance.getFixedValue();
    if (fixedValueValue != null) {
      setFixedValue(fixedValueValue);
    }
    setIsMutable(instance.isMutable());
    return this;
  }

  /**
   * Assigns a value to the {@link IMethodParameterDesc#getType() type} attribute.
   * @param type The value for type
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodParameterDesc setType(String type) {
    this.type = Objects.requireNonNull(type, "type");
    initBits &= ~INIT_BIT_TYPE;
    return this;
  }

  /**
   * Assigns a value to the {@link IMethodParameterDesc#getName() name} attribute.
   * @param name The value for name, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodParameterDesc setName(@Nullable String name) {
    this.name = name;
    return this;
  }

  /**
   * Assigns a value to the {@link IMethodParameterDesc#getFixedValue() fixedValue} attribute.
   * @param fixedValue The value for fixedValue, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodParameterDesc setFixedValue(@Nullable String fixedValue) {
    this.fixedValue = fixedValue;
    return this;
  }

  /**
   * Assigns a value to the {@link IMethodParameterDesc#isMutable() isMutable} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IMethodParameterDesc#isMutable() isMutable}.</em>
   * @param isMutable The value for isMutable
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodParameterDesc setIsMutable(boolean isMutable) {
    this.isMutable = isMutable;
    optBits |= OPT_BIT_IS_MUTABLE;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IMethodParameterDesc#getType() type} is set.
   * @return {@code true} if set
   */
  public final boolean typeIsSet() {
    return (initBits & INIT_BIT_TYPE) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IMethodParameterDesc#isMutable() isMutable} is set.
   * @return {@code true} if set
   */
  public final boolean isMutableIsSet() {
    return (optBits & OPT_BIT_IS_MUTABLE) != 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableMethodParameterDesc unsetType() {
    initBits |= INIT_BIT_TYPE;
    type = null;
    return this;
  }
  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableMethodParameterDesc unsetIsMutable() {
    optBits |= 0;
    isMutable = false;
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
    if (!typeIsSet()) attributes.add("type");
    return "MethodParameterDesc is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link MethodParameterDesc MethodParameterDesc}.
   * @return An immutable instance of MethodParameterDesc
   */
  public final MethodParameterDesc toImmutable() {
    checkRequiredAttributes();
    return MethodParameterDesc.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableMethodParameterDesc} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableMethodParameterDesc)) return false;
    MutableMethodParameterDesc other = (MutableMethodParameterDesc) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableMethodParameterDesc another) {
    boolean isMutable = isMutable();
    return type.equals(another.type)
        && Objects.equals(name, another.name)
        && Objects.equals(fixedValue, another.fixedValue)
        && isMutable == another.isMutable();
  }

  /**
   * Computes a hash code from attributes: {@code type}, {@code name}, {@code fixedValue}, {@code isMutable}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + type.hashCode();
    h += (h << 5) + Objects.hashCode(name);
    h += (h << 5) + Objects.hashCode(fixedValue);
    boolean isMutable = isMutable();
    h += (h << 5) + Booleans.hashCode(isMutable);
    return h;
  }

  /**
   * Generates a string representation of this {@code IMethodParameterDesc}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableMethodParameterDesc")
        .add("type", typeIsSet() ? getType() : "?")
        .add("name", getName())
        .add("fixedValue", getFixedValue())
        .add("isMutable", isMutable())
        .toString();
  }
}
