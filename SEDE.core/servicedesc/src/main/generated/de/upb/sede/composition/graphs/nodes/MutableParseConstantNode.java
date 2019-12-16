package de.upb.sede.composition.graphs.nodes;

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
 * A modifiable implementation of the {@link IParseConstantNode IParseConstantNode} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableParseConstantNode is not thread-safe</em>
 * @see ParseConstantNode
 */
@Generated(from = "IParseConstantNode", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IParseConstantNode"})
@NotThreadSafe
public final class MutableParseConstantNode implements IParseConstantNode {
  private static final long INIT_BIT_CONSTANT_VALUE = 0x1L;
  private static final long INIT_BIT_CONSTANT_TYPE = 0x2L;
  private long initBits = 0x3L;

  private String constantValue;
  private IParseConstantNode.ConstantType constantType;

  private MutableParseConstantNode() {}

  /**
   * Construct a modifiable instance of {@code IParseConstantNode}.
   * @return A new modifiable instance
   */
  public static MutableParseConstantNode create() {
    return new MutableParseConstantNode();
  }

  /**
   * @return value of {@code constantValue} attribute
   */
  @JsonProperty("constantValue")
  @Override
  public final String getConstantValue() {
    if (!constantValueIsSet()) {
      checkRequiredAttributes();
    }
    return constantValue;
  }

  /**
   * @return value of {@code constantType} attribute
   */
  @JsonProperty("constantType")
  @Override
  public final IParseConstantNode.ConstantType getConstantType() {
    if (!constantTypeIsSet()) {
      checkRequiredAttributes();
    }
    return constantType;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableParseConstantNode clear() {
    initBits = 0x3L;
    constantValue = null;
    constantType = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IParseConstantNode} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableParseConstantNode from(IParseConstantNode instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableParseConstantNode) {
      from((MutableParseConstantNode) instance);
      return this;
    }
    setConstantValue(instance.getConstantValue());
    setConstantType(instance.getConstantType());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IParseConstantNode} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableParseConstantNode from(MutableParseConstantNode instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.constantValueIsSet()) {
      setConstantValue(instance.getConstantValue());
    }
    if (instance.constantTypeIsSet()) {
      setConstantType(instance.getConstantType());
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IParseConstantNode#getConstantValue() constantValue} attribute.
   * @param constantValue The value for constantValue
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableParseConstantNode setConstantValue(String constantValue) {
    this.constantValue = Objects.requireNonNull(constantValue, "constantValue");
    initBits &= ~INIT_BIT_CONSTANT_VALUE;
    return this;
  }

  /**
   * Assigns a value to the {@link IParseConstantNode#getConstantType() constantType} attribute.
   * @param constantType The value for constantType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableParseConstantNode setConstantType(IParseConstantNode.ConstantType constantType) {
    this.constantType = Objects.requireNonNull(constantType, "constantType");
    initBits &= ~INIT_BIT_CONSTANT_TYPE;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IParseConstantNode#getConstantValue() constantValue} is set.
   * @return {@code true} if set
   */
  public final boolean constantValueIsSet() {
    return (initBits & INIT_BIT_CONSTANT_VALUE) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IParseConstantNode#getConstantType() constantType} is set.
   * @return {@code true} if set
   */
  public final boolean constantTypeIsSet() {
    return (initBits & INIT_BIT_CONSTANT_TYPE) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableParseConstantNode unsetConstantValue() {
    initBits |= INIT_BIT_CONSTANT_VALUE;
    constantValue = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableParseConstantNode unsetConstantType() {
    initBits |= INIT_BIT_CONSTANT_TYPE;
    constantType = null;
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
    if (!constantValueIsSet()) attributes.add("constantValue");
    if (!constantTypeIsSet()) attributes.add("constantType");
    return "ParseConstantNode is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link ParseConstantNode ParseConstantNode}.
   * @return An immutable instance of ParseConstantNode
   */
  public final ParseConstantNode toImmutable() {
    checkRequiredAttributes();
    return ParseConstantNode.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableParseConstantNode} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableParseConstantNode)) return false;
    MutableParseConstantNode other = (MutableParseConstantNode) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableParseConstantNode another) {
    return constantValue.equals(another.constantValue)
        && constantType.equals(another.constantType);
  }

  /**
   * Computes a hash code from attributes: {@code constantValue}, {@code constantType}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + constantValue.hashCode();
    h += (h << 5) + constantType.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IParseConstantNode}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableParseConstantNode")
        .add("constantValue", constantValueIsSet() ? getConstantValue() : "?")
        .add("constantType", constantTypeIsSet() ? getConstantType() : "?")
        .toString();
  }
}
