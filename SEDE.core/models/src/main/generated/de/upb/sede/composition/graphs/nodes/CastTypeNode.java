package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.IFieldContainer;
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
 * Immutable implementation of {@link ICastTypeNode}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code CastTypeNode.builder()}.
 */
@Generated(from = "ICastTypeNode", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class CastTypeNode implements ICastTypeNode {
  private final String sourceType;
  private final String targetType;
  private final boolean castToSemantic;
  private final String casterClasspath;
  private final String nodeType;
  private final String fieldName;

  private CastTypeNode(CastTypeNode.Builder builder) {
    this.sourceType = builder.sourceType;
    this.targetType = builder.targetType;
    this.castToSemantic = builder.castToSemantic;
    this.casterClasspath = builder.casterClasspath;
    this.fieldName = builder.fieldName;
    this.nodeType = builder.nodeType != null
        ? builder.nodeType
        : Objects.requireNonNull(ICastTypeNode.super.getNodeType(), "nodeType");
  }

  private CastTypeNode(
      String sourceType,
      String targetType,
      boolean castToSemantic,
      String casterClasspath,
      String nodeType,
      String fieldName) {
    this.sourceType = sourceType;
    this.targetType = targetType;
    this.castToSemantic = castToSemantic;
    this.casterClasspath = casterClasspath;
    this.nodeType = nodeType;
    this.fieldName = fieldName;
  }

  /**
   * @return The value of the {@code sourceType} attribute
   */
  @JsonProperty("sourceType")
  @Override
  public String getSourceType() {
    return sourceType;
  }

  /**
   * @return The value of the {@code targetType} attribute
   */
  @JsonProperty("targetType")
  @Override
  public String getTargetType() {
    return targetType;
  }

  /**
   * @return The value of the {@code castToSemantic} attribute
   */
  @JsonProperty("castToSemantic")
  @Override
  public boolean castToSemantic() {
    return castToSemantic;
  }

  /**
   * @return The value of the {@code casterClasspath} attribute
   */
  @JsonProperty("casterClasspath")
  @Override
  public String getCasterClasspath() {
    return casterClasspath;
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
   * Copy the current immutable object by setting a value for the {@link ICastTypeNode#getSourceType() sourceType} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for sourceType
   * @return A modified copy of the {@code this} object
   */
  public final CastTypeNode withSourceType(String value) {
    String newValue = Objects.requireNonNull(value, "sourceType");
    if (this.sourceType.equals(newValue)) return this;
    return new CastTypeNode(
        newValue,
        this.targetType,
        this.castToSemantic,
        this.casterClasspath,
        this.nodeType,
        this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ICastTypeNode#getTargetType() targetType} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for targetType
   * @return A modified copy of the {@code this} object
   */
  public final CastTypeNode withTargetType(String value) {
    String newValue = Objects.requireNonNull(value, "targetType");
    if (this.targetType.equals(newValue)) return this;
    return new CastTypeNode(
        this.sourceType,
        newValue,
        this.castToSemantic,
        this.casterClasspath,
        this.nodeType,
        this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ICastTypeNode#castToSemantic() castToSemantic} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for castToSemantic
   * @return A modified copy of the {@code this} object
   */
  public final CastTypeNode withCastToSemantic(boolean value) {
    if (this.castToSemantic == value) return this;
    return new CastTypeNode(this.sourceType, this.targetType, value, this.casterClasspath, this.nodeType, this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ICastTypeNode#getCasterClasspath() casterClasspath} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for casterClasspath
   * @return A modified copy of the {@code this} object
   */
  public final CastTypeNode withCasterClasspath(String value) {
    String newValue = Objects.requireNonNull(value, "casterClasspath");
    if (this.casterClasspath.equals(newValue)) return this;
    return new CastTypeNode(this.sourceType, this.targetType, this.castToSemantic, newValue, this.nodeType, this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ICastTypeNode#getNodeType() nodeType} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for nodeType
   * @return A modified copy of the {@code this} object
   */
  public final CastTypeNode withNodeType(String value) {
    String newValue = Objects.requireNonNull(value, "nodeType");
    if (this.nodeType.equals(newValue)) return this;
    return new CastTypeNode(
        this.sourceType,
        this.targetType,
        this.castToSemantic,
        this.casterClasspath,
        newValue,
        this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ICastTypeNode#getFieldName() fieldName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for fieldName
   * @return A modified copy of the {@code this} object
   */
  public final CastTypeNode withFieldName(String value) {
    String newValue = Objects.requireNonNull(value, "fieldName");
    if (this.fieldName.equals(newValue)) return this;
    return new CastTypeNode(
        this.sourceType,
        this.targetType,
        this.castToSemantic,
        this.casterClasspath,
        this.nodeType,
        newValue);
  }

  /**
   * This instance is equal to all instances of {@code CastTypeNode} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof CastTypeNode
        && equalTo((CastTypeNode) another);
  }

  private boolean equalTo(CastTypeNode another) {
    return sourceType.equals(another.sourceType)
        && targetType.equals(another.targetType)
        && castToSemantic == another.castToSemantic
        && casterClasspath.equals(another.casterClasspath)
        && nodeType.equals(another.nodeType)
        && fieldName.equals(another.fieldName);
  }

  /**
   * Computes a hash code from attributes: {@code sourceType}, {@code targetType}, {@code castToSemantic}, {@code casterClasspath}, {@code nodeType}, {@code fieldName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + sourceType.hashCode();
    h += (h << 5) + targetType.hashCode();
    h += (h << 5) + Booleans.hashCode(castToSemantic);
    h += (h << 5) + casterClasspath.hashCode();
    h += (h << 5) + nodeType.hashCode();
    h += (h << 5) + fieldName.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code CastTypeNode} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("CastTypeNode")
        .omitNullValues()
        .add("sourceType", sourceType)
        .add("targetType", targetType)
        .add("castToSemantic", castToSemantic)
        .add("casterClasspath", casterClasspath)
        .add("nodeType", nodeType)
        .add("fieldName", fieldName)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "ICastTypeNode", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements ICastTypeNode {
    @Nullable String sourceType;
    @Nullable String targetType;
    boolean castToSemantic;
    boolean castToSemanticIsSet;
    @Nullable String casterClasspath;
    @Nullable String nodeType;
    @Nullable String fieldName;
    @JsonProperty("sourceType")
    public void setSourceType(String sourceType) {
      this.sourceType = sourceType;
    }
    @JsonProperty("targetType")
    public void setTargetType(String targetType) {
      this.targetType = targetType;
    }
    @JsonProperty("castToSemantic")
    public void setCastToSemantic(boolean castToSemantic) {
      this.castToSemantic = castToSemantic;
      this.castToSemanticIsSet = true;
    }
    @JsonProperty("casterClasspath")
    public void setCasterClasspath(String casterClasspath) {
      this.casterClasspath = casterClasspath;
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
    public String getSourceType() { throw new UnsupportedOperationException(); }
    @Override
    public String getTargetType() { throw new UnsupportedOperationException(); }
    @Override
    public boolean castToSemantic() { throw new UnsupportedOperationException(); }
    @Override
    public String getCasterClasspath() { throw new UnsupportedOperationException(); }
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
  static CastTypeNode fromJson(Json json) {
    CastTypeNode.Builder builder = CastTypeNode.builder();
    if (json.sourceType != null) {
      builder.sourceType(json.sourceType);
    }
    if (json.targetType != null) {
      builder.targetType(json.targetType);
    }
    if (json.castToSemanticIsSet) {
      builder.castToSemantic(json.castToSemantic);
    }
    if (json.casterClasspath != null) {
      builder.casterClasspath(json.casterClasspath);
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
   * Creates an immutable copy of a {@link ICastTypeNode} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable CastTypeNode instance
   */
  public static CastTypeNode copyOf(ICastTypeNode instance) {
    if (instance instanceof CastTypeNode) {
      return (CastTypeNode) instance;
    }
    return CastTypeNode.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link CastTypeNode CastTypeNode}.
   * <pre>
   * CastTypeNode.builder()
   *    .sourceType(String) // required {@link ICastTypeNode#getSourceType() sourceType}
   *    .targetType(String) // required {@link ICastTypeNode#getTargetType() targetType}
   *    .castToSemantic(boolean) // required {@link ICastTypeNode#castToSemantic() castToSemantic}
   *    .casterClasspath(String) // required {@link ICastTypeNode#getCasterClasspath() casterClasspath}
   *    .nodeType(String) // optional {@link ICastTypeNode#getNodeType() nodeType}
   *    .fieldName(String) // required {@link ICastTypeNode#getFieldName() fieldName}
   *    .build();
   * </pre>
   * @return A new CastTypeNode builder
   */
  public static CastTypeNode.Builder builder() {
    return new CastTypeNode.Builder();
  }

  /**
   * Builds instances of type {@link CastTypeNode CastTypeNode}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "ICastTypeNode", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_SOURCE_TYPE = 0x1L;
    private static final long INIT_BIT_TARGET_TYPE = 0x2L;
    private static final long INIT_BIT_CAST_TO_SEMANTIC = 0x4L;
    private static final long INIT_BIT_CASTER_CLASSPATH = 0x8L;
    private static final long INIT_BIT_FIELD_NAME = 0x10L;
    private long initBits = 0x1fL;

    private @Nullable String sourceType;
    private @Nullable String targetType;
    private boolean castToSemantic;
    private @Nullable String casterClasspath;
    private @Nullable String nodeType;
    private @Nullable String fieldName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableCastTypeNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableCastTypeNode instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.sourceTypeIsSet()) {
        sourceType(instance.getSourceType());
      }
      if (instance.targetTypeIsSet()) {
        targetType(instance.getTargetType());
      }
      if (instance.castToSemanticIsSet()) {
        castToSemantic(instance.castToSemantic());
      }
      if (instance.casterClasspathIsSet()) {
        casterClasspath(instance.getCasterClasspath());
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
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.nodes.ICastTypeNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(ICastTypeNode instance) {
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
      if (object instanceof MutableCastTypeNode) {
        from((MutableCastTypeNode) object);
        return;
      }
      if (object instanceof IFieldContainer) {
        IFieldContainer instance = (IFieldContainer) object;
        fieldName(instance.getFieldName());
      }
      if (object instanceof ICastTypeNode) {
        ICastTypeNode instance = (ICastTypeNode) object;
        targetType(instance.getTargetType());
        sourceType(instance.getSourceType());
        casterClasspath(instance.getCasterClasspath());
        castToSemantic(instance.castToSemantic());
      }
      if (object instanceof BaseNode) {
        BaseNode instance = (BaseNode) object;
        nodeType(instance.getNodeType());
      }
    }

    /**
     * Initializes the value for the {@link ICastTypeNode#getSourceType() sourceType} attribute.
     * @param sourceType The value for sourceType 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("sourceType")
    public final Builder sourceType(String sourceType) {
      this.sourceType = Objects.requireNonNull(sourceType, "sourceType");
      initBits &= ~INIT_BIT_SOURCE_TYPE;
      return this;
    }

    /**
     * Initializes the value for the {@link ICastTypeNode#getTargetType() targetType} attribute.
     * @param targetType The value for targetType 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("targetType")
    public final Builder targetType(String targetType) {
      this.targetType = Objects.requireNonNull(targetType, "targetType");
      initBits &= ~INIT_BIT_TARGET_TYPE;
      return this;
    }

    /**
     * Initializes the value for the {@link ICastTypeNode#castToSemantic() castToSemantic} attribute.
     * @param castToSemantic The value for castToSemantic 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("castToSemantic")
    public final Builder castToSemantic(boolean castToSemantic) {
      this.castToSemantic = castToSemantic;
      initBits &= ~INIT_BIT_CAST_TO_SEMANTIC;
      return this;
    }

    /**
     * Initializes the value for the {@link ICastTypeNode#getCasterClasspath() casterClasspath} attribute.
     * @param casterClasspath The value for casterClasspath 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("casterClasspath")
    public final Builder casterClasspath(String casterClasspath) {
      this.casterClasspath = Objects.requireNonNull(casterClasspath, "casterClasspath");
      initBits &= ~INIT_BIT_CASTER_CLASSPATH;
      return this;
    }

    /**
     * Initializes the value for the {@link ICastTypeNode#getNodeType() nodeType} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link ICastTypeNode#getNodeType() nodeType}.</em>
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
     * Initializes the value for the {@link ICastTypeNode#getFieldName() fieldName} attribute.
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
     * Builds a new {@link CastTypeNode CastTypeNode}.
     * @return An immutable instance of CastTypeNode
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public CastTypeNode build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new CastTypeNode(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_SOURCE_TYPE) != 0) attributes.add("sourceType");
      if ((initBits & INIT_BIT_TARGET_TYPE) != 0) attributes.add("targetType");
      if ((initBits & INIT_BIT_CAST_TO_SEMANTIC) != 0) attributes.add("castToSemantic");
      if ((initBits & INIT_BIT_CASTER_CLASSPATH) != 0) attributes.add("casterClasspath");
      if ((initBits & INIT_BIT_FIELD_NAME) != 0) attributes.add("fieldName");
      return "Cannot build CastTypeNode, some of required attributes are not set " + attributes;
    }
  }
}
