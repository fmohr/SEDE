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
 * A modifiable implementation of the {@link ICollectErrorsNode ICollectErrorsNode} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableCollectErrorsNode is not thread-safe</em>
 * @see CollectErrorsNode
 */
@Generated(from = "ICollectErrorsNode", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "ICollectErrorsNode"})
@NotThreadSafe
public final class MutableCollectErrorsNode implements ICollectErrorsNode {
  private static final long INIT_BIT_FIELD_NAME = 0x1L;
  private long initBits = 0x1L;

  private final ArrayList<String> errorFields = new ArrayList<String>();
  private String nodeType;
  private String fieldName;

  private MutableCollectErrorsNode() {}

  /**
   * Construct a modifiable instance of {@code ICollectErrorsNode}.
   * @return A new modifiable instance
   */
  public static MutableCollectErrorsNode create() {
    return new MutableCollectErrorsNode();
  }

  /**
   * @return modifiable list {@code errorFields}
   */
  @JsonProperty("errorFields")
  @Override
  public final List<String> getErrorFields() {
    return errorFields;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code nodeType} attribute
   */
  @JsonProperty("nodeType")
  @Override
  public final String getNodeType() {
    return nodeTypeIsSet()
        ? nodeType
        : ICollectErrorsNode.super.getNodeType();
  }

  /**
   * Returns the field name that this node is referencing.
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
  public MutableCollectErrorsNode clear() {
    initBits = 0x1L;
    errorFields.clear();
    nodeType = null;
    fieldName = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.nodes.IFieldNameAware} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCollectErrorsNode from(IFieldNameAware instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.nodes.IBaseNode} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCollectErrorsNode from(IBaseNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.nodes.ICollectErrorsNode} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCollectErrorsNode from(ICollectErrorsNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link ICollectErrorsNode} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableCollectErrorsNode from(MutableCollectErrorsNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableCollectErrorsNode) {
      MutableCollectErrorsNode instance = (MutableCollectErrorsNode) object;
      addAllErrorFields(instance.getErrorFields());
      setNodeType(instance.getNodeType());
      if (instance.fieldNameIsSet()) {
        setFieldName(instance.getFieldName());
      }
      return;
    }
    if (object instanceof IFieldNameAware) {
      IFieldNameAware instance = (IFieldNameAware) object;
      setFieldName(instance.getFieldName());
    }
    if (object instanceof IBaseNode) {
      IBaseNode instance = (IBaseNode) object;
      setNodeType(instance.getNodeType());
    }
    if (object instanceof ICollectErrorsNode) {
      ICollectErrorsNode instance = (ICollectErrorsNode) object;
      addAllErrorFields(instance.getErrorFields());
    }
  }

  /**
   * Adds one element to {@link ICollectErrorsNode#getErrorFields() errorFields} list.
   * @param element The errorFields element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCollectErrorsNode addErrorFields(String element) {
    Objects.requireNonNull(element, "errorFields element");
    this.errorFields.add(element);
    return this;
  }

  /**
   * Adds elements to {@link ICollectErrorsNode#getErrorFields() errorFields} list.
   * @param elements An array of errorFields elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableCollectErrorsNode addErrorFields(String... elements) {
    for (String e : elements) {
      addErrorFields(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link ICollectErrorsNode#getErrorFields() errorFields} list.
   * @param elements An iterable of errorFields elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCollectErrorsNode setErrorFields(Iterable<String> elements) {
    this.errorFields.clear();
    addAllErrorFields(elements);
    return this;
  }

  /**
   * Adds elements to {@link ICollectErrorsNode#getErrorFields() errorFields} list.
   * @param elements An iterable of errorFields elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCollectErrorsNode addAllErrorFields(Iterable<String> elements) {
    for (String e : elements) {
      addErrorFields(e);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link ICollectErrorsNode#getNodeType() nodeType} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link ICollectErrorsNode#getNodeType() nodeType}.</em>
   * @param nodeType The value for nodeType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCollectErrorsNode setNodeType(String nodeType) {
    this.nodeType = Objects.requireNonNull(nodeType, "nodeType");
    return this;
  }

  /**
   * Assigns a value to the {@link ICollectErrorsNode#getFieldName() fieldName} attribute.
   * @param fieldName The value for fieldName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCollectErrorsNode setFieldName(String fieldName) {
    this.fieldName = Objects.requireNonNull(fieldName, "fieldName");
    initBits &= ~INIT_BIT_FIELD_NAME;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link ICollectErrorsNode#getFieldName() fieldName} is set.
   * @return {@code true} if set
   */
  public final boolean fieldNameIsSet() {
    return (initBits & INIT_BIT_FIELD_NAME) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link ICollectErrorsNode#getNodeType() nodeType} is set.
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
  public final MutableCollectErrorsNode unsetFieldName() {
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
    if (!fieldNameIsSet()) attributes.add("fieldName");
    return "CollectErrorsNode is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link CollectErrorsNode CollectErrorsNode}.
   * @return An immutable instance of CollectErrorsNode
   */
  public final CollectErrorsNode toImmutable() {
    checkRequiredAttributes();
    return CollectErrorsNode.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableCollectErrorsNode} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableCollectErrorsNode)) return false;
    MutableCollectErrorsNode other = (MutableCollectErrorsNode) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableCollectErrorsNode another) {
    String nodeType = getNodeType();
    return errorFields.equals(another.errorFields)
        && nodeType.equals(another.getNodeType())
        && fieldName.equals(another.fieldName);
  }

  /**
   * Computes a hash code from attributes: {@code errorFields}, {@code nodeType}, {@code fieldName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + errorFields.hashCode();
    String nodeType = getNodeType();
    h += (h << 5) + nodeType.hashCode();
    h += (h << 5) + fieldName.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code ICollectErrorsNode}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableCollectErrorsNode")
        .add("errorFields", getErrorFields())
        .add("nodeType", getNodeType())
        .add("fieldName", fieldNameIsSet() ? getFieldName() : "?")
        .toString();
  }
}
