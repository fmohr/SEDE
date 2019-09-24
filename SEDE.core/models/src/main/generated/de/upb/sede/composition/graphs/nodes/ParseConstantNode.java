package de.upb.sede.composition.graphs.nodes;

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
 * Immutable implementation of {@link IParseConstantNode}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ParseConstantNode.builder()}.
 */
@Generated(from = "IParseConstantNode", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ParseConstantNode implements IParseConstantNode {
  private final String constantValue;
  private final IParseConstantNode.ConstantType constantType;
  private final String nodeType;

  private ParseConstantNode(ParseConstantNode.Builder builder) {
    this.constantValue = builder.constantValue;
    this.constantType = builder.constantType;
    this.nodeType = builder.nodeType != null
        ? builder.nodeType
        : Objects.requireNonNull(IParseConstantNode.super.getNodeType(), "nodeType");
  }

  private ParseConstantNode(
      String constantValue,
      IParseConstantNode.ConstantType constantType,
      String nodeType) {
    this.constantValue = constantValue;
    this.constantType = constantType;
    this.nodeType = nodeType;
  }

  /**
   * @return The value of the {@code constantValue} attribute
   */
  @JsonProperty("constantValue")
  @Override
  public String getConstantValue() {
    return constantValue;
  }

  /**
   * @return The value of the {@code constantType} attribute
   */
  @JsonProperty("constantType")
  @Override
  public IParseConstantNode.ConstantType getConstantType() {
    return constantType;
  }

  /**
   * @return The value of the {@code nodeType} attribute
   */
  @JsonProperty("nodeType")
  @Override
  public String getNodeType() {
    return nodeType;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IParseConstantNode#getConstantValue() constantValue} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for constantValue
   * @return A modified copy of the {@code this} object
   */
  public final ParseConstantNode withConstantValue(String value) {
    String newValue = Objects.requireNonNull(value, "constantValue");
    if (this.constantValue.equals(newValue)) return this;
    return new ParseConstantNode(newValue, this.constantType, this.nodeType);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IParseConstantNode#getConstantType() constantType} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for constantType
   * @return A modified copy of the {@code this} object
   */
  public final ParseConstantNode withConstantType(IParseConstantNode.ConstantType value) {
    if (this.constantType == value) return this;
    IParseConstantNode.ConstantType newValue = Objects.requireNonNull(value, "constantType");
    if (this.constantType.equals(newValue)) return this;
    return new ParseConstantNode(this.constantValue, newValue, this.nodeType);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IParseConstantNode#getNodeType() nodeType} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for nodeType
   * @return A modified copy of the {@code this} object
   */
  public final ParseConstantNode withNodeType(String value) {
    String newValue = Objects.requireNonNull(value, "nodeType");
    if (this.nodeType.equals(newValue)) return this;
    return new ParseConstantNode(this.constantValue, this.constantType, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ParseConstantNode} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ParseConstantNode
        && equalTo((ParseConstantNode) another);
  }

  private boolean equalTo(ParseConstantNode another) {
    return constantValue.equals(another.constantValue)
        && constantType.equals(another.constantType)
        && nodeType.equals(another.nodeType);
  }

  /**
   * Computes a hash code from attributes: {@code constantValue}, {@code constantType}, {@code nodeType}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + constantValue.hashCode();
    h += (h << 5) + constantType.hashCode();
    h += (h << 5) + nodeType.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ParseConstantNode} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ParseConstantNode")
        .omitNullValues()
        .add("constantValue", constantValue)
        .add("constantType", constantType)
        .add("nodeType", nodeType)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IParseConstantNode", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IParseConstantNode {
    @Nullable String constantValue;
    @Nullable IParseConstantNode.ConstantType constantType;
    @Nullable String nodeType;
    @JsonProperty("constantValue")
    public void setConstantValue(String constantValue) {
      this.constantValue = constantValue;
    }
    @JsonProperty("constantType")
    public void setConstantType(IParseConstantNode.ConstantType constantType) {
      this.constantType = constantType;
    }
    @JsonProperty("nodeType")
    public void setNodeType(String nodeType) {
      this.nodeType = nodeType;
    }
    @Override
    public String getConstantValue() { throw new UnsupportedOperationException(); }
    @Override
    public IParseConstantNode.ConstantType getConstantType() { throw new UnsupportedOperationException(); }
    @Override
    public String getNodeType() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static ParseConstantNode fromJson(Json json) {
    ParseConstantNode.Builder builder = ParseConstantNode.builder();
    if (json.constantValue != null) {
      builder.constantValue(json.constantValue);
    }
    if (json.constantType != null) {
      builder.constantType(json.constantType);
    }
    if (json.nodeType != null) {
      builder.nodeType(json.nodeType);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IParseConstantNode} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ParseConstantNode instance
   */
  public static ParseConstantNode copyOf(IParseConstantNode instance) {
    if (instance instanceof ParseConstantNode) {
      return (ParseConstantNode) instance;
    }
    return ParseConstantNode.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ParseConstantNode ParseConstantNode}.
   * <pre>
   * ParseConstantNode.builder()
   *    .constantValue(String) // required {@link IParseConstantNode#getConstantValue() constantValue}
   *    .constantType(de.upb.sede.composition.graphs.nodes.IParseConstantNode.ConstantType) // required {@link IParseConstantNode#getConstantType() constantType}
   *    .nodeType(String) // optional {@link IParseConstantNode#getNodeType() nodeType}
   *    .build();
   * </pre>
   * @return A new ParseConstantNode builder
   */
  public static ParseConstantNode.Builder builder() {
    return new ParseConstantNode.Builder();
  }

  /**
   * Builds instances of type {@link ParseConstantNode ParseConstantNode}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IParseConstantNode", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_CONSTANT_VALUE = 0x1L;
    private static final long INIT_BIT_CONSTANT_TYPE = 0x2L;
    private long initBits = 0x3L;

    private @Nullable String constantValue;
    private @Nullable IParseConstantNode.ConstantType constantType;
    private @Nullable String nodeType;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableParseConstantNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableParseConstantNode instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.constantValueIsSet()) {
        constantValue(instance.getConstantValue());
      }
      if (instance.constantTypeIsSet()) {
        constantType(instance.getConstantType());
      }
      nodeType(instance.getNodeType());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.nodes.IBaseNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IBaseNode instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.nodes.IParseConstantNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IParseConstantNode instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof MutableParseConstantNode) {
        from((MutableParseConstantNode) object);
        return;
      }
      if (object instanceof IBaseNode) {
        IBaseNode instance = (IBaseNode) object;
        nodeType(instance.getNodeType());
      }
      if (object instanceof IParseConstantNode) {
        IParseConstantNode instance = (IParseConstantNode) object;
        constantType(instance.getConstantType());
        constantValue(instance.getConstantValue());
      }
    }

    /**
     * Initializes the value for the {@link IParseConstantNode#getConstantValue() constantValue} attribute.
     * @param constantValue The value for constantValue 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("constantValue")
    public final Builder constantValue(String constantValue) {
      this.constantValue = Objects.requireNonNull(constantValue, "constantValue");
      initBits &= ~INIT_BIT_CONSTANT_VALUE;
      return this;
    }

    /**
     * Initializes the value for the {@link IParseConstantNode#getConstantType() constantType} attribute.
     * @param constantType The value for constantType 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("constantType")
    public final Builder constantType(IParseConstantNode.ConstantType constantType) {
      this.constantType = Objects.requireNonNull(constantType, "constantType");
      initBits &= ~INIT_BIT_CONSTANT_TYPE;
      return this;
    }

    /**
     * Initializes the value for the {@link IParseConstantNode#getNodeType() nodeType} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IParseConstantNode#getNodeType() nodeType}.</em>
     * @param nodeType The value for nodeType 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("nodeType")
    public final Builder nodeType(String nodeType) {
      this.nodeType = Objects.requireNonNull(nodeType, "nodeType");
      return this;
    }

    /**
     * Builds a new {@link ParseConstantNode ParseConstantNode}.
     * @return An immutable instance of ParseConstantNode
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ParseConstantNode build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ParseConstantNode(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_CONSTANT_VALUE) != 0) attributes.add("constantValue");
      if ((initBits & INIT_BIT_CONSTANT_TYPE) != 0) attributes.add("constantType");
      return "Cannot build ParseConstantNode, some of required attributes are not set " + attributes;
    }
  }
}
