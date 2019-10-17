package de.upb.sede.types;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.CommentAware;
import de.upb.sede.IQualifiable;
import de.upb.sede.exec.auxiliary.IJavaDispatchAux;
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
 * Immutable implementation of {@link IDataTypeDesc}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code DataTypeDesc.builder()}.
 */
@Generated(from = "IDataTypeDesc", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class DataTypeDesc implements IDataTypeDesc {
  private final String semanticType;
  private final @Nullable IJavaDispatchAux javaAux;
  private final String qualifier;
  private final ImmutableList<String> metaTags;
  private final String simpleName;
  private final ImmutableList<String> comments;

  private DataTypeDesc(DataTypeDesc.Builder builder) {
    this.semanticType = builder.semanticType;
    this.javaAux = builder.javaAux;
    this.qualifier = builder.qualifier;
    this.metaTags = builder.metaTags.build();
    this.comments = builder.comments.build();
    this.simpleName = builder.simpleName != null
        ? builder.simpleName
        : Objects.requireNonNull(IDataTypeDesc.super.getSimpleName(), "simpleName");
  }

  private DataTypeDesc(
      String semanticType,
      @Nullable IJavaDispatchAux javaAux,
      String qualifier,
      ImmutableList<String> metaTags,
      String simpleName,
      ImmutableList<String> comments) {
    this.semanticType = semanticType;
    this.javaAux = javaAux;
    this.qualifier = qualifier;
    this.metaTags = metaTags;
    this.simpleName = simpleName;
    this.comments = comments;
  }

  /**
   * @return The value of the {@code semanticType} attribute
   */
  @JsonProperty("semanticType")
  @Override
  public String getSemanticType() {
    return semanticType;
  }

  /**
   * @return The value of the {@code javaAux} attribute
   */
  @JsonProperty("javaAux")
  @Override
  public @Nullable IJavaDispatchAux getJavaAux() {
    return javaAux;
  }

  /**
   * @return The value of the {@code qualifier} attribute
   */
  @JsonProperty("qualifier")
  @Override
  public String getQualifier() {
    return qualifier;
  }

  /**
   * @return The value of the {@code metaTags} attribute
   */
  @JsonProperty("metaTags")
  @Override
  public ImmutableList<String> getMetaTags() {
    return metaTags;
  }

  /**
   * @return The value of the {@code simpleName} attribute
   */
  @JsonProperty("simpleName")
  @Override
  public String getSimpleName() {
    return simpleName;
  }

  /**
   * @return The value of the {@code comments} attribute
   */
  @JsonProperty("comments")
  @Override
  public ImmutableList<String> getComments() {
    return comments;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IDataTypeDesc#getSemanticType() semanticType} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for semanticType
   * @return A modified copy of the {@code this} object
   */
  public final DataTypeDesc withSemanticType(String value) {
    String newValue = Objects.requireNonNull(value, "semanticType");
    if (this.semanticType.equals(newValue)) return this;
    return new DataTypeDesc(newValue, this.javaAux, this.qualifier, this.metaTags, this.simpleName, this.comments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IDataTypeDesc#getJavaAux() javaAux} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for javaAux (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final DataTypeDesc withJavaAux(@Nullable IJavaDispatchAux value) {
    if (this.javaAux == value) return this;
    return new DataTypeDesc(this.semanticType, value, this.qualifier, this.metaTags, this.simpleName, this.comments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IDataTypeDesc#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final DataTypeDesc withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new DataTypeDesc(this.semanticType, this.javaAux, newValue, this.metaTags, this.simpleName, this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IDataTypeDesc#getMetaTags() metaTags}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final DataTypeDesc withMetaTags(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new DataTypeDesc(this.semanticType, this.javaAux, this.qualifier, newValue, this.simpleName, this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IDataTypeDesc#getMetaTags() metaTags}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of metaTags elements to set
   * @return A modified copy of {@code this} object
   */
  public final DataTypeDesc withMetaTags(Iterable<String> elements) {
    if (this.metaTags == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new DataTypeDesc(this.semanticType, this.javaAux, this.qualifier, newValue, this.simpleName, this.comments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IDataTypeDesc#getSimpleName() simpleName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for simpleName
   * @return A modified copy of the {@code this} object
   */
  public final DataTypeDesc withSimpleName(String value) {
    String newValue = Objects.requireNonNull(value, "simpleName");
    if (this.simpleName.equals(newValue)) return this;
    return new DataTypeDesc(this.semanticType, this.javaAux, this.qualifier, this.metaTags, newValue, this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IDataTypeDesc#getComments() comments}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final DataTypeDesc withComments(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new DataTypeDesc(this.semanticType, this.javaAux, this.qualifier, this.metaTags, this.simpleName, newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IDataTypeDesc#getComments() comments}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of comments elements to set
   * @return A modified copy of {@code this} object
   */
  public final DataTypeDesc withComments(Iterable<String> elements) {
    if (this.comments == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new DataTypeDesc(this.semanticType, this.javaAux, this.qualifier, this.metaTags, this.simpleName, newValue);
  }

  /**
   * This instance is equal to all instances of {@code DataTypeDesc} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof DataTypeDesc
        && equalTo((DataTypeDesc) another);
  }

  private boolean equalTo(DataTypeDesc another) {
    return semanticType.equals(another.semanticType)
        && Objects.equals(javaAux, another.javaAux)
        && qualifier.equals(another.qualifier)
        && comments.equals(another.comments);
  }

  /**
   * Computes a hash code from attributes: {@code semanticType}, {@code javaAux}, {@code qualifier}, {@code comments}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + semanticType.hashCode();
    h += (h << 5) + Objects.hashCode(javaAux);
    h += (h << 5) + qualifier.hashCode();
    h += (h << 5) + comments.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code DataTypeDesc} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("DataTypeDesc")
        .omitNullValues()
        .add("semanticType", semanticType)
        .add("javaAux", javaAux)
        .add("qualifier", qualifier)
        .add("comments", comments)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IDataTypeDesc", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IDataTypeDesc {
    @Nullable String semanticType;
    @Nullable IJavaDispatchAux javaAux;
    @Nullable String qualifier;
    @Nullable List<String> metaTags = ImmutableList.of();
    @Nullable String simpleName;
    @Nullable List<String> comments = ImmutableList.of();
    @JsonProperty("semanticType")
    public void setSemanticType(String semanticType) {
      this.semanticType = semanticType;
    }
    @JsonProperty("javaAux")
    public void setJavaAux(@Nullable IJavaDispatchAux javaAux) {
      this.javaAux = javaAux;
    }
    @JsonProperty("qualifier")
    public void setQualifier(String qualifier) {
      this.qualifier = qualifier;
    }
    @JsonProperty("metaTags")
    public void setMetaTags(List<String> metaTags) {
      this.metaTags = metaTags;
    }
    @JsonProperty("simpleName")
    public void setSimpleName(String simpleName) {
      this.simpleName = simpleName;
    }
    @JsonProperty("comments")
    public void setComments(List<String> comments) {
      this.comments = comments;
    }
    @Override
    public String getSemanticType() { throw new UnsupportedOperationException(); }
    @Override
    public IJavaDispatchAux getJavaAux() { throw new UnsupportedOperationException(); }
    @Override
    public String getQualifier() { throw new UnsupportedOperationException(); }
    @Override
    public List<String> getMetaTags() { throw new UnsupportedOperationException(); }
    @Override
    public String getSimpleName() { throw new UnsupportedOperationException(); }
    @Override
    public List<String> getComments() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static DataTypeDesc fromJson(Json json) {
    DataTypeDesc.Builder builder = DataTypeDesc.builder();
    if (json.semanticType != null) {
      builder.semanticType(json.semanticType);
    }
    if (json.javaAux != null) {
      builder.javaAux(json.javaAux);
    }
    if (json.qualifier != null) {
      builder.qualifier(json.qualifier);
    }
    if (json.metaTags != null) {
      builder.addAllMetaTags(json.metaTags);
    }
    if (json.simpleName != null) {
      builder.simpleName(json.simpleName);
    }
    if (json.comments != null) {
      builder.addAllComments(json.comments);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IDataTypeDesc} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable DataTypeDesc instance
   */
  public static DataTypeDesc copyOf(IDataTypeDesc instance) {
    if (instance instanceof DataTypeDesc) {
      return (DataTypeDesc) instance;
    }
    return DataTypeDesc.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link DataTypeDesc DataTypeDesc}.
   * <pre>
   * DataTypeDesc.builder()
   *    .semanticType(String) // required {@link IDataTypeDesc#getSemanticType() semanticType}
   *    .javaAux(de.upb.sede.exec.auxiliary.IJavaDispatchAux | null) // nullable {@link IDataTypeDesc#getJavaAux() javaAux}
   *    .qualifier(String) // required {@link IDataTypeDesc#getQualifier() qualifier}
   *    .addMetaTags|addAllMetaTags(String) // {@link IDataTypeDesc#getMetaTags() metaTags} elements
   *    .simpleName(String) // optional {@link IDataTypeDesc#getSimpleName() simpleName}
   *    .addComments|addAllComments(String) // {@link IDataTypeDesc#getComments() comments} elements
   *    .build();
   * </pre>
   * @return A new DataTypeDesc builder
   */
  public static DataTypeDesc.Builder builder() {
    return new DataTypeDesc.Builder();
  }

  /**
   * Builds instances of type {@link DataTypeDesc DataTypeDesc}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IDataTypeDesc", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_SEMANTIC_TYPE = 0x1L;
    private static final long INIT_BIT_QUALIFIER = 0x2L;
    private long initBits = 0x3L;

    private @Nullable String semanticType;
    private @Nullable IJavaDispatchAux javaAux;
    private @Nullable String qualifier;
    private ImmutableList.Builder<String> metaTags = ImmutableList.builder();
    private @Nullable String simpleName;
    private ImmutableList.Builder<String> comments = ImmutableList.builder();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableDataTypeDesc} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableDataTypeDesc instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.semanticTypeIsSet()) {
        semanticType(instance.getSemanticType());
      }
      @Nullable IJavaDispatchAux javaAuxValue = instance.getJavaAux();
      if (javaAuxValue != null) {
        javaAux(javaAuxValue);
      }
      if (instance.qualifierIsSet()) {
        qualifier(instance.getQualifier());
      }
      addAllMetaTags(instance.getMetaTags());
      simpleName(instance.getSimpleName());
      addAllComments(instance.getComments());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.types.IDataTypeDesc} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IDataTypeDesc instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.IQualifiable} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IQualifiable instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.CommentAware} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(CommentAware instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof MutableDataTypeDesc) {
        from((MutableDataTypeDesc) object);
        return;
      }
      if (object instanceof IDataTypeDesc) {
        IDataTypeDesc instance = (IDataTypeDesc) object;
        @Nullable IJavaDispatchAux javaAuxValue = instance.getJavaAux();
        if (javaAuxValue != null) {
          javaAux(javaAuxValue);
        }
        semanticType(instance.getSemanticType());
      }
      if (object instanceof IQualifiable) {
        IQualifiable instance = (IQualifiable) object;
        addAllMetaTags(instance.getMetaTags());
        simpleName(instance.getSimpleName());
        qualifier(instance.getQualifier());
      }
      if (object instanceof CommentAware) {
        CommentAware instance = (CommentAware) object;
        addAllComments(instance.getComments());
      }
    }

    /**
     * Initializes the value for the {@link IDataTypeDesc#getSemanticType() semanticType} attribute.
     * @param semanticType The value for semanticType 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("semanticType")
    public final Builder semanticType(String semanticType) {
      this.semanticType = Objects.requireNonNull(semanticType, "semanticType");
      initBits &= ~INIT_BIT_SEMANTIC_TYPE;
      return this;
    }

    /**
     * Initializes the value for the {@link IDataTypeDesc#getJavaAux() javaAux} attribute.
     * @param javaAux The value for javaAux (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("javaAux")
    public final Builder javaAux(@Nullable IJavaDispatchAux javaAux) {
      this.javaAux = javaAux;
      return this;
    }

    /**
     * Initializes the value for the {@link IDataTypeDesc#getQualifier() qualifier} attribute.
     * @param qualifier The value for qualifier 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("qualifier")
    public final Builder qualifier(String qualifier) {
      this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
      initBits &= ~INIT_BIT_QUALIFIER;
      return this;
    }

    /**
     * Adds one element to {@link IDataTypeDesc#getMetaTags() metaTags} list.
     * @param element A metaTags element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMetaTags(String element) {
      this.metaTags.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IDataTypeDesc#getMetaTags() metaTags} list.
     * @param elements An array of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMetaTags(String... elements) {
      this.metaTags.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IDataTypeDesc#getMetaTags() metaTags} list.
     * @param elements An iterable of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("metaTags")
    public final Builder metaTags(Iterable<String> elements) {
      this.metaTags = ImmutableList.builder();
      return addAllMetaTags(elements);
    }

    /**
     * Adds elements to {@link IDataTypeDesc#getMetaTags() metaTags} list.
     * @param elements An iterable of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllMetaTags(Iterable<String> elements) {
      this.metaTags.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link IDataTypeDesc#getSimpleName() simpleName} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IDataTypeDesc#getSimpleName() simpleName}.</em>
     * @param simpleName The value for simpleName 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("simpleName")
    public final Builder simpleName(String simpleName) {
      this.simpleName = Objects.requireNonNull(simpleName, "simpleName");
      return this;
    }

    /**
     * Adds one element to {@link IDataTypeDesc#getComments() comments} list.
     * @param element A comments element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addComments(String element) {
      this.comments.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IDataTypeDesc#getComments() comments} list.
     * @param elements An array of comments elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addComments(String... elements) {
      this.comments.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IDataTypeDesc#getComments() comments} list.
     * @param elements An iterable of comments elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("comments")
    public final Builder comments(Iterable<String> elements) {
      this.comments = ImmutableList.builder();
      return addAllComments(elements);
    }

    /**
     * Adds elements to {@link IDataTypeDesc#getComments() comments} list.
     * @param elements An iterable of comments elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllComments(Iterable<String> elements) {
      this.comments.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link DataTypeDesc DataTypeDesc}.
     * @return An immutable instance of DataTypeDesc
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public DataTypeDesc build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new DataTypeDesc(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_SEMANTIC_TYPE) != 0) attributes.add("semanticType");
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      return "Cannot build DataTypeDesc, some of required attributes are not set " + attributes;
    }
  }
}
