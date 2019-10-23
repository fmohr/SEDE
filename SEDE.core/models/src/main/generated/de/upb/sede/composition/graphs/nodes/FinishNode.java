package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.IFieldContainer;
import de.upb.sede.exec.IExecutorContactInfo;
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
 * Immutable implementation of {@link IFinishNode}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code FinishNode.builder()}.
 */
@Generated(from = "IFinishNode", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class FinishNode implements IFinishNode {
  private final IExecutorContactInfo clientContactInfo;
  private final String nodeType;
  private final String fieldName;

  private FinishNode(FinishNode.Builder builder) {
    this.clientContactInfo = builder.clientContactInfo;
    this.fieldName = builder.fieldName;
    this.nodeType = builder.nodeType != null
        ? builder.nodeType
        : Objects.requireNonNull(IFinishNode.super.getNodeType(), "nodeType");
  }

  private FinishNode(
      IExecutorContactInfo clientContactInfo,
      String nodeType,
      String fieldName) {
    this.clientContactInfo = clientContactInfo;
    this.nodeType = nodeType;
    this.fieldName = fieldName;
  }

  /**
   * @return The value of the {@code clientContactInfo} attribute
   */
  @JsonProperty("clientContactInfo")
  @Override
  public IExecutorContactInfo getClientContactInfo() {
    return clientContactInfo;
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
   * Returns the field name that is being refered at.
   * @return Referenced field name
   */
  @JsonProperty("fieldName")
  @Override
  public String getFieldName() {
    return fieldName;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IFinishNode#getClientContactInfo() clientContactInfo} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for clientContactInfo
   * @return A modified copy of the {@code this} object
   */
  public final FinishNode withClientContactInfo(IExecutorContactInfo value) {
    if (this.clientContactInfo == value) return this;
    IExecutorContactInfo newValue = Objects.requireNonNull(value, "clientContactInfo");
    return new FinishNode(newValue, this.nodeType, this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IFinishNode#getNodeType() nodeType} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for nodeType
   * @return A modified copy of the {@code this} object
   */
  public final FinishNode withNodeType(String value) {
    String newValue = Objects.requireNonNull(value, "nodeType");
    if (this.nodeType.equals(newValue)) return this;
    return new FinishNode(this.clientContactInfo, newValue, this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IFinishNode#getFieldName() fieldName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for fieldName
   * @return A modified copy of the {@code this} object
   */
  public final FinishNode withFieldName(String value) {
    String newValue = Objects.requireNonNull(value, "fieldName");
    if (this.fieldName.equals(newValue)) return this;
    return new FinishNode(this.clientContactInfo, this.nodeType, newValue);
  }

  /**
   * This instance is equal to all instances of {@code FinishNode} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof FinishNode
        && equalTo((FinishNode) another);
  }

  private boolean equalTo(FinishNode another) {
    return clientContactInfo.equals(another.clientContactInfo)
        && nodeType.equals(another.nodeType)
        && fieldName.equals(another.fieldName);
  }

  /**
   * Computes a hash code from attributes: {@code clientContactInfo}, {@code nodeType}, {@code fieldName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + clientContactInfo.hashCode();
    h += (h << 5) + nodeType.hashCode();
    h += (h << 5) + fieldName.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code FinishNode} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("FinishNode")
        .omitNullValues()
        .add("clientContactInfo", clientContactInfo)
        .add("nodeType", nodeType)
        .add("fieldName", fieldName)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IFinishNode", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IFinishNode {
    @Nullable IExecutorContactInfo clientContactInfo;
    @Nullable String nodeType;
    @Nullable String fieldName;
    @JsonProperty("clientContactInfo")
    public void setClientContactInfo(IExecutorContactInfo clientContactInfo) {
      this.clientContactInfo = clientContactInfo;
    }
    @JsonProperty("nodeType")
    public void setNodeType(String nodeType) {
      this.nodeType = nodeType;
    }
    @JsonProperty("fieldName")
    public void setFieldName(String fieldName) {
      this.fieldName = fieldName;
    }
    @Override
    public IExecutorContactInfo getClientContactInfo() { throw new UnsupportedOperationException(); }
    @Override
    public String getNodeType() { throw new UnsupportedOperationException(); }
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
  static FinishNode fromJson(Json json) {
    FinishNode.Builder builder = FinishNode.builder();
    if (json.clientContactInfo != null) {
      builder.clientContactInfo(json.clientContactInfo);
    }
    if (json.nodeType != null) {
      builder.nodeType(json.nodeType);
    }
    if (json.fieldName != null) {
      builder.fieldName(json.fieldName);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IFinishNode} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable FinishNode instance
   */
  public static FinishNode copyOf(IFinishNode instance) {
    if (instance instanceof FinishNode) {
      return (FinishNode) instance;
    }
    return FinishNode.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link FinishNode FinishNode}.
   * <pre>
   * FinishNode.builder()
   *    .clientContactInfo(de.upb.sede.exec.IExecutorContactInfo) // required {@link IFinishNode#getClientContactInfo() clientContactInfo}
   *    .nodeType(String) // optional {@link IFinishNode#getNodeType() nodeType}
   *    .fieldName(String) // required {@link IFinishNode#getFieldName() fieldName}
   *    .build();
   * </pre>
   * @return A new FinishNode builder
   */
  public static FinishNode.Builder builder() {
    return new FinishNode.Builder();
  }

  /**
   * Builds instances of type {@link FinishNode FinishNode}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IFinishNode", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_CLIENT_CONTACT_INFO = 0x1L;
    private static final long INIT_BIT_FIELD_NAME = 0x2L;
    private long initBits = 0x3L;

    private @Nullable IExecutorContactInfo clientContactInfo;
    private @Nullable String nodeType;
    private @Nullable String fieldName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableFinishNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableFinishNode instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.clientContactInfoIsSet()) {
        clientContactInfo(instance.getClientContactInfo());
      }
      nodeType(instance.getNodeType());
      if (instance.fieldNameIsSet()) {
        fieldName(instance.getFieldName());
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.IFieldContainer} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IFieldContainer instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.nodes.IFinishNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IFinishNode instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.nodes.BaseNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(BaseNode instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof MutableFinishNode) {
        from((MutableFinishNode) object);
        return;
      }
      if (object instanceof IFieldContainer) {
        IFieldContainer instance = (IFieldContainer) object;
        fieldName(instance.getFieldName());
      }
      if (object instanceof IFinishNode) {
        IFinishNode instance = (IFinishNode) object;
        clientContactInfo(instance.getClientContactInfo());
      }
      if (object instanceof BaseNode) {
        BaseNode instance = (BaseNode) object;
        nodeType(instance.getNodeType());
      }
    }

    /**
     * Initializes the value for the {@link IFinishNode#getClientContactInfo() clientContactInfo} attribute.
     * @param clientContactInfo The value for clientContactInfo 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("clientContactInfo")
    public final Builder clientContactInfo(IExecutorContactInfo clientContactInfo) {
      this.clientContactInfo = Objects.requireNonNull(clientContactInfo, "clientContactInfo");
      initBits &= ~INIT_BIT_CLIENT_CONTACT_INFO;
      return this;
    }

    /**
     * Initializes the value for the {@link IFinishNode#getNodeType() nodeType} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IFinishNode#getNodeType() nodeType}.</em>
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
     * Initializes the value for the {@link IFinishNode#getFieldName() fieldName} attribute.
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
     * Builds a new {@link FinishNode FinishNode}.
     * @return An immutable instance of FinishNode
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public FinishNode build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new FinishNode(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_CLIENT_CONTACT_INFO) != 0) attributes.add("clientContactInfo");
      if ((initBits & INIT_BIT_FIELD_NAME) != 0) attributes.add("fieldName");
      return "Cannot build FinishNode, some of required attributes are not set " + attributes;
    }
  }
}
