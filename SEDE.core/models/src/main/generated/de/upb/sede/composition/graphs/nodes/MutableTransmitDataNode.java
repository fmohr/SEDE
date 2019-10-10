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
 * A modifiable implementation of the {@link ITransmitDataNode ITransmitDataNode} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableTransmitDataNode is not thread-safe</em>
 * @see TransmitDataNode
 */
@Generated(from = "ITransmitDataNode", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "ITransmitDataNode"})
@NotThreadSafe
public final class MutableTransmitDataNode implements ITransmitDataNode {
  private static final long INIT_BIT_CONTACT_INFO = 0x1L;
  private static final long INIT_BIT_FIELD_NAME = 0x2L;
  private long initBits = 0x3L;

  private IExecutorContactInfo contactInfo;
  private @Nullable String caster;
  private @Nullable String semantiveType;
  private String nodeType;
  private String fieldName;

  private MutableTransmitDataNode() {}

  /**
   * Construct a modifiable instance of {@code ITransmitDataNode}.
   * @return A new modifiable instance
   */
  public static MutableTransmitDataNode create() {
    return new MutableTransmitDataNode();
  }

  /**
   * @return value of {@code contactInfo} attribute
   */
  @JsonProperty("contactInfo")
  @Override
  public final IExecutorContactInfo getContactInfo() {
    if (!contactInfoIsSet()) {
      checkRequiredAttributes();
    }
    return contactInfo;
  }

  /**
   * @return value of {@code caster} attribute, may be {@code null}
   */
  @JsonProperty("caster")
  @Override
  public final @Nullable String getCaster() {
    return caster;
  }

  /**
   * @return value of {@code semantiveType} attribute, may be {@code null}
   */
  @JsonProperty("semantiveType")
  @Override
  public final @Nullable String getSemantiveType() {
    return semantiveType;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code nodeType} attribute
   */
  @JsonProperty("nodeType")
  @Override
  public final String getNodeType() {
    return nodeTypeIsSet()
        ? nodeType
        : ITransmitDataNode.super.getNodeType();
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
  public MutableTransmitDataNode clear() {
    initBits = 0x3L;
    contactInfo = null;
    caster = null;
    semantiveType = null;
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
  public MutableTransmitDataNode from(IFieldContainer instance) {
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
  public MutableTransmitDataNode from(BaseNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.nodes.ITransmitDataNode} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableTransmitDataNode from(ITransmitDataNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link ITransmitDataNode} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableTransmitDataNode from(MutableTransmitDataNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableTransmitDataNode) {
      MutableTransmitDataNode instance = (MutableTransmitDataNode) object;
      if (instance.contactInfoIsSet()) {
        setContactInfo(instance.getContactInfo());
      }
      @Nullable String casterValue = instance.getCaster();
      if (casterValue != null) {
        setCaster(casterValue);
      }
      @Nullable String semantiveTypeValue = instance.getSemantiveType();
      if (semantiveTypeValue != null) {
        setSemantiveType(semantiveTypeValue);
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
    if (object instanceof BaseNode) {
      BaseNode instance = (BaseNode) object;
      setNodeType(instance.getNodeType());
    }
    if (object instanceof ITransmitDataNode) {
      ITransmitDataNode instance = (ITransmitDataNode) object;
      @Nullable String casterValue = instance.getCaster();
      if (casterValue != null) {
        setCaster(casterValue);
      }
      setContactInfo(instance.getContactInfo());
      @Nullable String semantiveTypeValue = instance.getSemantiveType();
      if (semantiveTypeValue != null) {
        setSemantiveType(semantiveTypeValue);
      }
    }
  }

  /**
   * Assigns a value to the {@link ITransmitDataNode#getContactInfo() contactInfo} attribute.
   * @param contactInfo The value for contactInfo
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableTransmitDataNode setContactInfo(IExecutorContactInfo contactInfo) {
    this.contactInfo = Objects.requireNonNull(contactInfo, "contactInfo");
    initBits &= ~INIT_BIT_CONTACT_INFO;
    return this;
  }

  /**
   * Assigns a value to the {@link ITransmitDataNode#getCaster() caster} attribute.
   * @param caster The value for caster, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableTransmitDataNode setCaster(@Nullable String caster) {
    this.caster = caster;
    return this;
  }

  /**
   * Assigns a value to the {@link ITransmitDataNode#getSemantiveType() semantiveType} attribute.
   * @param semantiveType The value for semantiveType, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableTransmitDataNode setSemantiveType(@Nullable String semantiveType) {
    this.semantiveType = semantiveType;
    return this;
  }

  /**
   * Assigns a value to the {@link ITransmitDataNode#getNodeType() nodeType} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link ITransmitDataNode#getNodeType() nodeType}.</em>
   * @param nodeType The value for nodeType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableTransmitDataNode setNodeType(String nodeType) {
    this.nodeType = Objects.requireNonNull(nodeType, "nodeType");
    return this;
  }

  /**
   * Assigns a value to the {@link ITransmitDataNode#getFieldName() fieldName} attribute.
   * @param fieldName The value for fieldName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableTransmitDataNode setFieldName(String fieldName) {
    this.fieldName = Objects.requireNonNull(fieldName, "fieldName");
    initBits &= ~INIT_BIT_FIELD_NAME;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link ITransmitDataNode#getContactInfo() contactInfo} is set.
   * @return {@code true} if set
   */
  public final boolean contactInfoIsSet() {
    return (initBits & INIT_BIT_CONTACT_INFO) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link ITransmitDataNode#getFieldName() fieldName} is set.
   * @return {@code true} if set
   */
  public final boolean fieldNameIsSet() {
    return (initBits & INIT_BIT_FIELD_NAME) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link ITransmitDataNode#getNodeType() nodeType} is set.
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
  public final MutableTransmitDataNode unsetContactInfo() {
    initBits |= INIT_BIT_CONTACT_INFO;
    contactInfo = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableTransmitDataNode unsetFieldName() {
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
    if (!contactInfoIsSet()) attributes.add("contactInfo");
    if (!fieldNameIsSet()) attributes.add("fieldName");
    return "TransmitDataNode is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link TransmitDataNode TransmitDataNode}.
   * @return An immutable instance of TransmitDataNode
   */
  public final TransmitDataNode toImmutable() {
    checkRequiredAttributes();
    return TransmitDataNode.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableTransmitDataNode} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableTransmitDataNode)) return false;
    MutableTransmitDataNode other = (MutableTransmitDataNode) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableTransmitDataNode another) {
    String nodeType = getNodeType();
    return contactInfo.equals(another.contactInfo)
        && Objects.equals(caster, another.caster)
        && Objects.equals(semantiveType, another.semantiveType)
        && nodeType.equals(another.getNodeType())
        && fieldName.equals(another.fieldName);
  }

  /**
   * Computes a hash code from attributes: {@code contactInfo}, {@code caster}, {@code semantiveType}, {@code nodeType}, {@code fieldName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + contactInfo.hashCode();
    h += (h << 5) + Objects.hashCode(caster);
    h += (h << 5) + Objects.hashCode(semantiveType);
    String nodeType = getNodeType();
    h += (h << 5) + nodeType.hashCode();
    h += (h << 5) + fieldName.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code ITransmitDataNode}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableTransmitDataNode")
        .add("contactInfo", contactInfoIsSet() ? getContactInfo() : "?")
        .add("caster", getCaster())
        .add("semantiveType", getSemantiveType())
        .add("nodeType", getNodeType())
        .add("fieldName", fieldNameIsSet() ? getFieldName() : "?")
        .toString();
  }
}
