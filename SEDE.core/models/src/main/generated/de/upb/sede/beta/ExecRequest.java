package de.upb.sede.beta;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.IQualifiable;
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
 * Immutable implementation of {@link IExecRequest}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ExecRequest.builder()}.
 */
@Generated(from = "IExecRequest", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ExecRequest implements IExecRequest {
  private final String compositionGraph;
  private final String qualifier;
  private final ImmutableList<String> metaTags;
  private final String simpleName;

  private ExecRequest(ExecRequest.Builder builder) {
    this.compositionGraph = builder.compositionGraph;
    this.qualifier = builder.qualifier;
    this.metaTags = builder.metaTags.build();
    this.simpleName = builder.simpleName != null
        ? builder.simpleName
        : Objects.requireNonNull(IExecRequest.super.getSimpleName(), "simpleName");
  }

  private ExecRequest(
      String compositionGraph,
      String qualifier,
      ImmutableList<String> metaTags,
      String simpleName) {
    this.compositionGraph = compositionGraph;
    this.qualifier = qualifier;
    this.metaTags = metaTags;
    this.simpleName = simpleName;
  }

  /**
   * @return The value of the {@code compositionGraph} attribute
   */
  @JsonProperty("compositionGraph")
  @Override
  public String getCompositionGraph() {
    return compositionGraph;
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
   * Copy the current immutable object by setting a value for the {@link IExecRequest#getCompositionGraph() compositionGraph} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for compositionGraph
   * @return A modified copy of the {@code this} object
   */
  public final ExecRequest withCompositionGraph(String value) {
    String newValue = Objects.requireNonNull(value, "compositionGraph");
    if (this.compositionGraph.equals(newValue)) return this;
    return new ExecRequest(newValue, this.qualifier, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IExecRequest#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final ExecRequest withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new ExecRequest(this.compositionGraph, newValue, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IExecRequest#getMetaTags() metaTags}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ExecRequest withMetaTags(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ExecRequest(this.compositionGraph, this.qualifier, newValue, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IExecRequest#getMetaTags() metaTags}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of metaTags elements to set
   * @return A modified copy of {@code this} object
   */
  public final ExecRequest withMetaTags(Iterable<String> elements) {
    if (this.metaTags == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ExecRequest(this.compositionGraph, this.qualifier, newValue, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IExecRequest#getSimpleName() simpleName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for simpleName
   * @return A modified copy of the {@code this} object
   */
  public final ExecRequest withSimpleName(String value) {
    String newValue = Objects.requireNonNull(value, "simpleName");
    if (this.simpleName.equals(newValue)) return this;
    return new ExecRequest(this.compositionGraph, this.qualifier, this.metaTags, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ExecRequest} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ExecRequest
        && equalTo((ExecRequest) another);
  }

  private boolean equalTo(ExecRequest another) {
    return compositionGraph.equals(another.compositionGraph)
        && qualifier.equals(another.qualifier);
  }

  /**
   * Computes a hash code from attributes: {@code compositionGraph}, {@code qualifier}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + compositionGraph.hashCode();
    h += (h << 5) + qualifier.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ExecRequest} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ExecRequest")
        .omitNullValues()
        .add("compositionGraph", compositionGraph)
        .add("qualifier", qualifier)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IExecRequest", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IExecRequest {
    @Nullable String compositionGraph;
    @Nullable String qualifier;
    @Nullable List<String> metaTags = ImmutableList.of();
    @Nullable String simpleName;
    @JsonProperty("compositionGraph")
    public void setCompositionGraph(String compositionGraph) {
      this.compositionGraph = compositionGraph;
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
    @Override
    public String getCompositionGraph() { throw new UnsupportedOperationException(); }
    @Override
    public String getQualifier() { throw new UnsupportedOperationException(); }
    @Override
    public List<String> getMetaTags() { throw new UnsupportedOperationException(); }
    @Override
    public String getSimpleName() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static ExecRequest fromJson(Json json) {
    ExecRequest.Builder builder = ExecRequest.builder();
    if (json.compositionGraph != null) {
      builder.compositionGraph(json.compositionGraph);
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
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IExecRequest} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ExecRequest instance
   */
  public static ExecRequest copyOf(IExecRequest instance) {
    if (instance instanceof ExecRequest) {
      return (ExecRequest) instance;
    }
    return ExecRequest.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ExecRequest ExecRequest}.
   * <pre>
   * ExecRequest.builder()
   *    .compositionGraph(String) // required {@link IExecRequest#getCompositionGraph() compositionGraph}
   *    .qualifier(String) // required {@link IExecRequest#getQualifier() qualifier}
   *    .addMetaTags|addAllMetaTags(String) // {@link IExecRequest#getMetaTags() metaTags} elements
   *    .simpleName(String) // optional {@link IExecRequest#getSimpleName() simpleName}
   *    .build();
   * </pre>
   * @return A new ExecRequest builder
   */
  public static ExecRequest.Builder builder() {
    return new ExecRequest.Builder();
  }

  /**
   * Builds instances of type {@link ExecRequest ExecRequest}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IExecRequest", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_COMPOSITION_GRAPH = 0x1L;
    private static final long INIT_BIT_QUALIFIER = 0x2L;
    private long initBits = 0x3L;

    private @Nullable String compositionGraph;
    private @Nullable String qualifier;
    private ImmutableList.Builder<String> metaTags = ImmutableList.builder();
    private @Nullable String simpleName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableExecRequest} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableExecRequest instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.compositionGraphIsSet()) {
        compositionGraph(instance.getCompositionGraph());
      }
      if (instance.qualifierIsSet()) {
        qualifier(instance.getQualifier());
      }
      addAllMetaTags(instance.getMetaTags());
      simpleName(instance.getSimpleName());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.beta.IExecRequest} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IExecRequest instance) {
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

    private void from(Object object) {
      if (object instanceof MutableExecRequest) {
        from((MutableExecRequest) object);
        return;
      }
      if (object instanceof IExecRequest) {
        IExecRequest instance = (IExecRequest) object;
        compositionGraph(instance.getCompositionGraph());
      }
      if (object instanceof IQualifiable) {
        IQualifiable instance = (IQualifiable) object;
        addAllMetaTags(instance.getMetaTags());
        simpleName(instance.getSimpleName());
        qualifier(instance.getQualifier());
      }
    }

    /**
     * Initializes the value for the {@link IExecRequest#getCompositionGraph() compositionGraph} attribute.
     * @param compositionGraph The value for compositionGraph 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("compositionGraph")
    public final Builder compositionGraph(String compositionGraph) {
      this.compositionGraph = Objects.requireNonNull(compositionGraph, "compositionGraph");
      initBits &= ~INIT_BIT_COMPOSITION_GRAPH;
      return this;
    }

    /**
     * Initializes the value for the {@link IExecRequest#getQualifier() qualifier} attribute.
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
     * Adds one element to {@link IExecRequest#getMetaTags() metaTags} list.
     * @param element A metaTags element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMetaTags(String element) {
      this.metaTags.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IExecRequest#getMetaTags() metaTags} list.
     * @param elements An array of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMetaTags(String... elements) {
      this.metaTags.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IExecRequest#getMetaTags() metaTags} list.
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
     * Adds elements to {@link IExecRequest#getMetaTags() metaTags} list.
     * @param elements An iterable of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllMetaTags(Iterable<String> elements) {
      this.metaTags.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link IExecRequest#getSimpleName() simpleName} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IExecRequest#getSimpleName() simpleName}.</em>
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
     * Builds a new {@link ExecRequest ExecRequest}.
     * @return An immutable instance of ExecRequest
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ExecRequest build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ExecRequest(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_COMPOSITION_GRAPH) != 0) attributes.add("compositionGraph");
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      return "Cannot build ExecRequest, some of required attributes are not set " + attributes;
    }
  }
}
