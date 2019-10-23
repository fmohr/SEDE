package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.IFieldContainer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link ICastTypeNode ICastTypeNode} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableCastTypeNode is not thread-safe</em>
 * @see CastTypeNode
 */
@Generated(from = "ICastTypeNode", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "ICastTypeNode"})
@NotThreadSafe
public final class MutableCastTypeNode implements ICastTypeNode {
  private static final long INIT_BIT_SOURCE_TYPE = 0x1L;
  private static final long INIT_BIT_TARGET_TYPE = 0x2L;
  private static final long INIT_BIT_CAST_TO_SEMANTIC = 0x4L;
  private static final long INIT_BIT_CASTER_CLASSPATH = 0x8L;
  private static final long INIT_BIT_FIELD_NAME = 0x10L;
  private long initBits = 0x1fL;

  private String sourceType;
  private String targetType;
  private boolean castToSemantic;
  private String casterClasspath;
  private String nodeType;
  private String fieldName;

  private MutableCastTypeNode() {}

  /**
   * Construct a modifiable instance of {@code ICastTypeNode}.
   * @return A new modifiable instance
   */
  public static MutableCastTypeNode create() {
    return new MutableCastTypeNode();
  }

  /**
   * @return value of {@code sourceType} attribute
   */
  @JsonProperty("sourceType")
  @Override
  public final String getSourceType() {
    if (!sourceTypeIsSet()) {
      checkRequiredAttributes();
    }
    return sourceType;
  }

  /**
   * @return value of {@code targetType} attribute
   */
  @JsonProperty("targetType")
  @Override
  public final String getTargetType() {
    if (!targetTypeIsSet()) {
      checkRequiredAttributes();
    }
    return targetType;
  }

  /**
   * @return value of {@code castToSemantic} attribute
   */
  @JsonProperty("castToSemantic")
  @Override
  public final boolean castToSemantic() {
    if (!castToSemanticIsSet()) {
      checkRequiredAttributes();
    }
    return castToSemantic;
  }

  /**
   * @return value of {@code casterClasspath} attribute
   */
  @JsonProperty("casterClasspath")
  @Override
  public final String getCasterClasspath() {
    if (!casterClasspathIsSet()) {
      checkRequiredAttributes();
    }
    return casterClasspath;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code nodeType} attribute
   */
  @JsonProperty("nodeType")
  @Override
  public final String getNodeType() {
    return nodeTypeIsSet()
        ? nodeType
        : ICastTypeNode.super.getNodeType();
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
  public MutableCastTypeNode clear() {
    initBits = 0x1fL;
    sourceType = null;
    targetType = null;
    castToSemantic = false;
    casterClasspath = null;
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
  public MutableCastTypeNode from(IFieldContainer instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.nodes.ICastTypeNode} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCastTypeNode from(ICastTypeNode instance) {
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
  public MutableCastTypeNode from(BaseNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link ICastTypeNode} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableCastTypeNode from(MutableCastTypeNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableCastTypeNode) {
      MutableCastTypeNode instance = (MutableCastTypeNode) object;
      if (instance.sourceTypeIsSet()) {
        setSourceType(instance.getSourceType());
      }
      if (instance.targetTypeIsSet()) {
        setTargetType(instance.getTargetType());
      }
      if (instance.castToSemanticIsSet()) {
        setCastToSemantic(instance.castToSemantic());
      }
      if (instance.casterClasspathIsSet()) {
        setCasterClasspath(instance.getCasterClasspath());
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
    if (object instanceof ICastTypeNode) {
      ICastTypeNode instance = (ICastTypeNode) object;
      setTargetType(instance.getTargetType());
      setSourceType(instance.getSourceType());
      setCasterClasspath(instance.getCasterClasspath());
      setCastToSemantic(instance.castToSemantic());
    }
    if (object instanceof BaseNode) {
      BaseNode instance = (BaseNode) object;
      setNodeType(instance.getNodeType());
    }
  }

  /**
   * Assigns a value to the {@link ICastTypeNode#getSourceType() sourceType} attribute.
   * @param sourceType The value for sourceType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCastTypeNode setSourceType(String sourceType) {
    this.sourceType = Objects.requireNonNull(sourceType, "sourceType");
    initBits &= ~INIT_BIT_SOURCE_TYPE;
    return this;
  }

  /**
   * Assigns a value to the {@link ICastTypeNode#getTargetType() targetType} attribute.
   * @param targetType The value for targetType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCastTypeNode setTargetType(String targetType) {
    this.targetType = Objects.requireNonNull(targetType, "targetType");
    initBits &= ~INIT_BIT_TARGET_TYPE;
    return this;
  }

  /**
   * Assigns a value to the {@link ICastTypeNode#castToSemantic() castToSemantic} attribute.
   * @param castToSemantic The value for castToSemantic
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCastTypeNode setCastToSemantic(boolean castToSemantic) {
    this.castToSemantic = castToSemantic;
    initBits &= ~INIT_BIT_CAST_TO_SEMANTIC;
    return this;
  }

  /**
   * Assigns a value to the {@link ICastTypeNode#getCasterClasspath() casterClasspath} attribute.
   * @param casterClasspath The value for casterClasspath
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCastTypeNode setCasterClasspath(String casterClasspath) {
    this.casterClasspath = Objects.requireNonNull(casterClasspath, "casterClasspath");
    initBits &= ~INIT_BIT_CASTER_CLASSPATH;
    return this;
  }

  /**
   * Assigns a value to the {@link ICastTypeNode#getNodeType() nodeType} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link ICastTypeNode#getNodeType() nodeType}.</em>
   * @param nodeType The value for nodeType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCastTypeNode setNodeType(String nodeType) {
    this.nodeType = Objects.requireNonNull(nodeType, "nodeType");
    return this;
  }

  /**
   * Assigns a value to the {@link ICastTypeNode#getFieldName() fieldName} attribute.
   * @param fieldName The value for fieldName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCastTypeNode setFieldName(String fieldName) {
    this.fieldName = Objects.requireNonNull(fieldName, "fieldName");
    initBits &= ~INIT_BIT_FIELD_NAME;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link ICastTypeNode#getSourceType() sourceType} is set.
   * @return {@code true} if set
   */
  public final boolean sourceTypeIsSet() {
    return (initBits & INIT_BIT_SOURCE_TYPE) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link ICastTypeNode#getTargetType() targetType} is set.
   * @return {@code true} if set
   */
  public final boolean targetTypeIsSet() {
    return (initBits & INIT_BIT_TARGET_TYPE) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link ICastTypeNode#castToSemantic() castToSemantic} is set.
   * @return {@code true} if set
   */
  public final boolean castToSemanticIsSet() {
    return (initBits & INIT_BIT_CAST_TO_SEMANTIC) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link ICastTypeNode#getCasterClasspath() casterClasspath} is set.
   * @return {@code true} if set
   */
  public final boolean casterClasspathIsSet() {
    return (initBits & INIT_BIT_CASTER_CLASSPATH) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link ICastTypeNode#getFieldName() fieldName} is set.
   * @return {@code true} if set
   */
  public final boolean fieldNameIsSet() {
    return (initBits & INIT_BIT_FIELD_NAME) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link ICastTypeNode#getNodeType() nodeType} is set.
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
  public final MutableCastTypeNode unsetSourceType() {
    initBits |= INIT_BIT_SOURCE_TYPE;
    sourceType = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableCastTypeNode unsetTargetType() {
    initBits |= INIT_BIT_TARGET_TYPE;
    targetType = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableCastTypeNode unsetCastToSemantic() {
    initBits |= INIT_BIT_CAST_TO_SEMANTIC;
    castToSemantic = false;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableCastTypeNode unsetCasterClasspath() {
    initBits |= INIT_BIT_CASTER_CLASSPATH;
    casterClasspath = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableCastTypeNode unsetFieldName() {
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
    if (!sourceTypeIsSet()) attributes.add("sourceType");
    if (!targetTypeIsSet()) attributes.add("targetType");
    if (!castToSemanticIsSet()) attributes.add("castToSemantic");
    if (!casterClasspathIsSet()) attributes.add("casterClasspath");
    if (!fieldNameIsSet()) attributes.add("fieldName");
    return "CastTypeNode is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link CastTypeNode CastTypeNode}.
   * @return An immutable instance of CastTypeNode
   */
  public final CastTypeNode toImmutable() {
    checkRequiredAttributes();
    return CastTypeNode.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableCastTypeNode} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableCastTypeNode)) return false;
    MutableCastTypeNode other = (MutableCastTypeNode) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableCastTypeNode another) {
    String nodeType = getNodeType();
    return sourceType.equals(another.sourceType)
        && targetType.equals(another.targetType)
        && castToSemantic == another.castToSemantic
        && casterClasspath.equals(another.casterClasspath)
        && nodeType.equals(another.getNodeType())
        && fieldName.equals(another.fieldName);
  }

  /**
   * Computes a hash code from attributes: {@code sourceType}, {@code targetType}, {@code castToSemantic}, {@code casterClasspath}, {@code nodeType}, {@code fieldName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + sourceType.hashCode();
    h += (h << 5) + targetType.hashCode();
    h += (h << 5) + Booleans.hashCode(castToSemantic);
    h += (h << 5) + casterClasspath.hashCode();
    String nodeType = getNodeType();
    h += (h << 5) + nodeType.hashCode();
    h += (h << 5) + fieldName.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code ICastTypeNode}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableCastTypeNode")
        .add("sourceType", sourceTypeIsSet() ? getSourceType() : "?")
        .add("targetType", targetTypeIsSet() ? getTargetType() : "?")
        .add("castToSemantic", castToSemanticIsSet() ? castToSemantic() : "?")
        .add("casterClasspath", casterClasspathIsSet() ? getCasterClasspath() : "?")
        .add("nodeType", getNodeType())
        .add("fieldName", fieldNameIsSet() ? getFieldName() : "?")
        .toString();
  }
}
