package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.IFieldContainer;
import de.upb.sede.exec.IExecutorContactInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IFinishNode IFinishNode} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableFinishNode is not thread-safe</em>
 * @see FinishNode
 */
@Generated(from = "IFinishNode", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IFinishNode"})
@NotThreadSafe
public final class MutableFinishNode implements IFinishNode {
  private static final long INIT_BIT_CLIENT_CONTACT_INFO = 0x1L;
  private static final long INIT_BIT_FIELD_NAME = 0x2L;
  private long initBits = 0x3L;

  private IExecutorContactInfo clientContactInfo;
  private String nodeType;
  private String fieldName;

  private MutableFinishNode() {}

  /**
   * Construct a modifiable instance of {@code IFinishNode}.
   * @return A new modifiable instance
   */
  public static MutableFinishNode create() {
    return new MutableFinishNode();
  }

  /**
   * @return value of {@code clientContactInfo} attribute
   */
  @JsonProperty("clientContactInfo")
  @Override
  public final IExecutorContactInfo getClientContactInfo() {
    if (!clientContactInfoIsSet()) {
      checkRequiredAttributes();
    }
    return clientContactInfo;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code nodeType} attribute
   */
  @JsonProperty("nodeType")
  @Override
  public final String getNodeType() {
    return nodeTypeIsSet()
        ? nodeType
        : IFinishNode.super.getNodeType();
  }

  /**
   * Returns the field name that is being refered at.
   * @return Referenced field name
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
  public MutableFinishNode clear() {
    initBits = 0x3L;
    clientContactInfo = null;
    nodeType = null;
    fieldName = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.IFieldContainer} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFinishNode from(IFieldContainer instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.nodes.IFinishNode} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFinishNode from(IFinishNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.nodes.BaseNode} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFinishNode from(BaseNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IFinishNode} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableFinishNode from(MutableFinishNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableFinishNode) {
      MutableFinishNode instance = (MutableFinishNode) object;
      if (instance.clientContactInfoIsSet()) {
        setClientContactInfo(instance.getClientContactInfo());
      }
      setNodeType(instance.getNodeType());
      if (instance.fieldNameIsSet()) {
        setFieldName(instance.getFieldName());
      }
      return;
    }
    if (object instanceof IFieldContainer) {
      IFieldContainer instance = (IFieldContainer) object;
      setFieldName(instance.getFieldName());
    }
    if (object instanceof IFinishNode) {
      IFinishNode instance = (IFinishNode) object;
      setClientContactInfo(instance.getClientContactInfo());
    }
    if (object instanceof BaseNode) {
      BaseNode instance = (BaseNode) object;
      setNodeType(instance.getNodeType());
    }
  }

  /**
   * Assigns a value to the {@link IFinishNode#getClientContactInfo() clientContactInfo} attribute.
   * @param clientContactInfo The value for clientContactInfo
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFinishNode setClientContactInfo(IExecutorContactInfo clientContactInfo) {
    this.clientContactInfo = Objects.requireNonNull(clientContactInfo, "clientContactInfo");
    initBits &= ~INIT_BIT_CLIENT_CONTACT_INFO;
    return this;
  }

  /**
   * Assigns a value to the {@link IFinishNode#getNodeType() nodeType} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IFinishNode#getNodeType() nodeType}.</em>
   * @param nodeType The value for nodeType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFinishNode setNodeType(String nodeType) {
    this.nodeType = Objects.requireNonNull(nodeType, "nodeType");
    return this;
  }

  /**
   * Assigns a value to the {@link IFinishNode#getFieldName() fieldName} attribute.
   * @param fieldName The value for fieldName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFinishNode setFieldName(String fieldName) {
    this.fieldName = Objects.requireNonNull(fieldName, "fieldName");
    initBits &= ~INIT_BIT_FIELD_NAME;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IFinishNode#getClientContactInfo() clientContactInfo} is set.
   * @return {@code true} if set
   */
  public final boolean clientContactInfoIsSet() {
    return (initBits & INIT_BIT_CLIENT_CONTACT_INFO) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IFinishNode#getFieldName() fieldName} is set.
   * @return {@code true} if set
   */
  public final boolean fieldNameIsSet() {
    return (initBits & INIT_BIT_FIELD_NAME) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IFinishNode#getNodeType() nodeType} is set.
   * @return {@code true} if set
   */
  public final boolean nodeTypeIsSet() {
    return nodeType != null;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableFinishNode unsetClientContactInfo() {
    initBits |= INIT_BIT_CLIENT_CONTACT_INFO;
    clientContactInfo = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableFinishNode unsetFieldName() {
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
    if (!clientContactInfoIsSet()) attributes.add("clientContactInfo");
    if (!fieldNameIsSet()) attributes.add("fieldName");
    return "FinishNode is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link FinishNode FinishNode}.
   * @return An immutable instance of FinishNode
   */
  public final FinishNode toImmutable() {
    checkRequiredAttributes();
    return FinishNode.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableFinishNode} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableFinishNode)) return false;
    MutableFinishNode other = (MutableFinishNode) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableFinishNode another) {
    String nodeType = getNodeType();
    return clientContactInfo.equals(another.clientContactInfo)
        && nodeType.equals(another.getNodeType())
        && fieldName.equals(another.fieldName);
  }

  /**
   * Computes a hash code from attributes: {@code clientContactInfo}, {@code nodeType}, {@code fieldName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + clientContactInfo.hashCode();
    String nodeType = getNodeType();
    h += (h << 5) + nodeType.hashCode();
    h += (h << 5) + fieldName.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IFinishNode}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableFinishNode")
        .add("clientContactInfo", clientContactInfoIsSet() ? getClientContactInfo() : "?")
        .add("nodeType", getNodeType())
        .add("fieldName", fieldNameIsSet() ? getFieldName() : "?")
        .toString();
  }
}
