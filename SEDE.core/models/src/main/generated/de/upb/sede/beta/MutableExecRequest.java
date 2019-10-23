package de.upb.sede.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.IQualifiable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IExecRequest IExecRequest} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableExecRequest is not thread-safe</em>
 * @see ExecRequest
 */
@Generated(from = "IExecRequest", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IExecRequest"})
@NotThreadSafe
public final class MutableExecRequest implements IExecRequest {
  private static final long INIT_BIT_COMPOSITION_GRAPH = 0x1L;
  private static final long INIT_BIT_QUALIFIER = 0x2L;
  private long initBits = 0x3L;

  private String compositionGraph;
  private String qualifier;
  private final ArrayList<String> metaTags = new ArrayList<String>();
  private String simpleName;

  private MutableExecRequest() {}

  /**
   * Construct a modifiable instance of {@code IExecRequest}.
   * @return A new modifiable instance
   */
  public static MutableExecRequest create() {
    return new MutableExecRequest();
  }

  /**
   * @return value of {@code compositionGraph} attribute
   */
  @JsonProperty("compositionGraph")
  @Override
  public final String getCompositionGraph() {
    if (!compositionGraphIsSet()) {
      checkRequiredAttributes();
    }
    return compositionGraph;
  }

  /**
   * @return value of {@code qualifier} attribute
   */
  @JsonProperty("qualifier")
  @Override
  public final String getQualifier() {
    if (!qualifierIsSet()) {
      checkRequiredAttributes();
    }
    return qualifier;
  }

  /**
   * @return modifiable list {@code metaTags}
   */
  @JsonProperty("metaTags")
  @Override
  public final List<String> getMetaTags() {
    return metaTags;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code simpleName} attribute
   */
  @JsonProperty("simpleName")
  @Override
  public final String getSimpleName() {
    return simpleNameIsSet()
        ? simpleName
        : IExecRequest.super.getSimpleName();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecRequest clear() {
    initBits = 0x3L;
    compositionGraph = null;
    qualifier = null;
    metaTags.clear();
    simpleName = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.beta.IExecRequest} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecRequest from(IExecRequest instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.IQualifiable} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecRequest from(IQualifiable instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IExecRequest} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableExecRequest from(MutableExecRequest instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableExecRequest) {
      MutableExecRequest instance = (MutableExecRequest) object;
      if (instance.compositionGraphIsSet()) {
        setCompositionGraph(instance.getCompositionGraph());
      }
      if (instance.qualifierIsSet()) {
        setQualifier(instance.getQualifier());
      }
      addAllMetaTags(instance.getMetaTags());
      setSimpleName(instance.getSimpleName());
      return;
    }
    if (object instanceof IExecRequest) {
      IExecRequest instance = (IExecRequest) object;
      setCompositionGraph(instance.getCompositionGraph());
    }
    if (object instanceof IQualifiable) {
      IQualifiable instance = (IQualifiable) object;
      addAllMetaTags(instance.getMetaTags());
      setSimpleName(instance.getSimpleName());
      setQualifier(instance.getQualifier());
    }
  }

  /**
   * Assigns a value to the {@link IExecRequest#getCompositionGraph() compositionGraph} attribute.
   * @param compositionGraph The value for compositionGraph
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecRequest setCompositionGraph(String compositionGraph) {
    this.compositionGraph = Objects.requireNonNull(compositionGraph, "compositionGraph");
    initBits &= ~INIT_BIT_COMPOSITION_GRAPH;
    return this;
  }

  /**
   * Assigns a value to the {@link IExecRequest#getQualifier() qualifier} attribute.
   * @param qualifier The value for qualifier
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecRequest setQualifier(String qualifier) {
    this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
    initBits &= ~INIT_BIT_QUALIFIER;
    return this;
  }

  /**
   * Adds one element to {@link IExecRequest#getMetaTags() metaTags} list.
   * @param element The metaTags element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecRequest addMetaTags(String element) {
    Objects.requireNonNull(element, "metaTags element");
    this.metaTags.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IExecRequest#getMetaTags() metaTags} list.
   * @param elements An array of metaTags elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableExecRequest addMetaTags(String... elements) {
    for (String e : elements) {
      addMetaTags(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IExecRequest#getMetaTags() metaTags} list.
   * @param elements An iterable of metaTags elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecRequest setMetaTags(Iterable<String> elements) {
    this.metaTags.clear();
    addAllMetaTags(elements);
    return this;
  }

  /**
   * Adds elements to {@link IExecRequest#getMetaTags() metaTags} list.
   * @param elements An iterable of metaTags elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecRequest addAllMetaTags(Iterable<String> elements) {
    for (String e : elements) {
      addMetaTags(e);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IExecRequest#getSimpleName() simpleName} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IExecRequest#getSimpleName() simpleName}.</em>
   * @param simpleName The value for simpleName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecRequest setSimpleName(String simpleName) {
    this.simpleName = Objects.requireNonNull(simpleName, "simpleName");
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IExecRequest#getCompositionGraph() compositionGraph} is set.
   * @return {@code true} if set
   */
  public final boolean compositionGraphIsSet() {
    return (initBits & INIT_BIT_COMPOSITION_GRAPH) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IExecRequest#getQualifier() qualifier} is set.
   * @return {@code true} if set
   */
  public final boolean qualifierIsSet() {
    return (initBits & INIT_BIT_QUALIFIER) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IExecRequest#getSimpleName() simpleName} is set.
   * @return {@code true} if set
   */
  public final boolean simpleNameIsSet() {
    return simpleName != null;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableExecRequest unsetCompositionGraph() {
    initBits |= INIT_BIT_COMPOSITION_GRAPH;
    compositionGraph = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableExecRequest unsetQualifier() {
    initBits |= INIT_BIT_QUALIFIER;
    qualifier = null;
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
    if (!compositionGraphIsSet()) attributes.add("compositionGraph");
    if (!qualifierIsSet()) attributes.add("qualifier");
    return "ExecRequest is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link ExecRequest ExecRequest}.
   * @return An immutable instance of ExecRequest
   */
  public final ExecRequest toImmutable() {
    checkRequiredAttributes();
    return ExecRequest.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableExecRequest} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableExecRequest)) return false;
    MutableExecRequest other = (MutableExecRequest) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableExecRequest another) {
    return compositionGraph.equals(another.compositionGraph)
        && qualifier.equals(another.qualifier);
  }

  /**
   * Computes a hash code from attributes: {@code compositionGraph}, {@code qualifier}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + compositionGraph.hashCode();
    h += (h << 5) + qualifier.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IExecRequest}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableExecRequest")
        .add("compositionGraph", compositionGraphIsSet() ? getCompositionGraph() : "?")
        .add("qualifier", qualifierIsSet() ? getQualifier() : "?")
        .toString();
  }
}
