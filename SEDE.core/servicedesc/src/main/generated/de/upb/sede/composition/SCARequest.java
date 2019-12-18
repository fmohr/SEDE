package de.upb.sede.composition;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
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
 * Static Code Analysis Request
 */
@Generated(from = "ISCARequest", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class SCARequest implements ICCRequest {
  private final String composition;
  private final ImmutableList<IFieldType> initialContext;

  private SCARequest(
      String composition,
      ImmutableList<IFieldType> initialContext) {
    this.composition = composition;
    this.initialContext = initialContext;
  }

  /**
   * @return The value of the {@code composition} attribute
   */
  @JsonProperty("composition")
  @Override
  public String getComposition() {
    return composition;
  }

  /**
   * @return The value of the {@code initialContext} attribute
   */
  @JsonProperty("initialContext")
  @Override
  public ImmutableList<IFieldType> getInitialContext() {
    return initialContext;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ICCRequest#getComposition() composition} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for composition
   * @return A modified copy of the {@code this} object
   */
  public final SCARequest withComposition(String value) {
    String newValue = Objects.requireNonNull(value, "composition");
    if (this.composition.equals(newValue)) return this;
    return new SCARequest(newValue, this.initialContext);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ICCRequest#getInitialContext() initialContext}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final SCARequest withInitialContext(IFieldType... elements) {
    ImmutableList<IFieldType> newValue = ImmutableList.copyOf(elements);
    return new SCARequest(this.composition, newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ICCRequest#getInitialContext() initialContext}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of initialContext elements to set
   * @return A modified copy of {@code this} object
   */
  public final SCARequest withInitialContext(Iterable<? extends IFieldType> elements) {
    if (this.initialContext == elements) return this;
    ImmutableList<IFieldType> newValue = ImmutableList.copyOf(elements);
    return new SCARequest(this.composition, newValue);
  }

  /**
   * This instance is equal to all instances of {@code SCARequest} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof SCARequest
        && equalTo((SCARequest) another);
  }

  private boolean equalTo(SCARequest another) {
    return composition.equals(another.composition)
        && initialContext.equals(another.initialContext);
  }

  /**
   * Computes a hash code from attributes: {@code composition}, {@code initialContext}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + composition.hashCode();
    h += (h << 5) + initialContext.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code SCARequest} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("SCARequest")
        .omitNullValues()
        .add("composition", composition)
        .add("initialContext", initialContext)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "ISCARequest", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements ICCRequest {
    @Nullable String composition;
    @Nullable List<IFieldType> initialContext = ImmutableList.of();
    @JsonProperty("composition")
    public void setComposition(String composition) {
      this.composition = composition;
    }
    @JsonProperty("initialContext")
    public void setInitialContext(List<IFieldType> initialContext) {
      this.initialContext = initialContext;
    }
    @Override
    public String getComposition() { throw new UnsupportedOperationException(); }
    @Override
    public List<IFieldType> getInitialContext() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static SCARequest fromJson(Json json) {
    SCARequest.Builder builder = SCARequest.builder();
    if (json.composition != null) {
      builder.composition(json.composition);
    }
    if (json.initialContext != null) {
      builder.addAllInitialContext(json.initialContext);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link ICCRequest} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable SCARequest instance
   */
  public static SCARequest copyOf(ICCRequest instance) {
    if (instance instanceof SCARequest) {
      return (SCARequest) instance;
    }
    return SCARequest.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link SCARequest SCARequest}.
   * <pre>
   * SCARequest.builder()
   *    .composition(String) // required {@link ICCRequest#getComposition() composition}
   *    .addInitialContext|addAllInitialContext(de.upb.sede.composition.IFieldType) // {@link ICCRequest#getInitialContext() initialContext} elements
   *    .build();
   * </pre>
   * @return A new SCARequest builder
   */
  public static SCARequest.Builder builder() {
    return new SCARequest.Builder();
  }

  /**
   * Builds instances of type {@link SCARequest SCARequest}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "ISCARequest", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_COMPOSITION = 0x1L;
    private long initBits = 0x1L;

    private @Nullable String composition;
    private ImmutableList.Builder<IFieldType> initialContext = ImmutableList.builder();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableSCARequest} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue
    public final Builder from(MutableSCARequest instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.compositionIsSet()) {
        composition(instance.getComposition());
      }
      addAllInitialContext(instance.getInitialContext());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code ISCARequest} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue
    public final Builder from(ICCRequest instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableSCARequest) {
        return from((MutableSCARequest) instance);
      }
      composition(instance.getComposition());
      addAllInitialContext(instance.getInitialContext());
      return this;
    }

    /**
     * Initializes the value for the {@link ICCRequest#getComposition() composition} attribute.
     * @param composition The value for composition
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue
    @JsonProperty("composition")
    public final Builder composition(String composition) {
      this.composition = Objects.requireNonNull(composition, "composition");
      initBits &= ~INIT_BIT_COMPOSITION;
      return this;
    }

    /**
     * Adds one element to {@link ICCRequest#getInitialContext() initialContext} list.
     * @param element A initialContext element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue
    public final Builder addInitialContext(IFieldType element) {
      this.initialContext.add(element);
      return this;
    }

    /**
     * Adds elements to {@link ICCRequest#getInitialContext() initialContext} list.
     * @param elements An array of initialContext elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue
    public final Builder addInitialContext(IFieldType... elements) {
      this.initialContext.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link ICCRequest#getInitialContext() initialContext} list.
     * @param elements An iterable of initialContext elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue
    @JsonProperty("initialContext")
    public final Builder initialContext(Iterable<? extends IFieldType> elements) {
      this.initialContext = ImmutableList.builder();
      return addAllInitialContext(elements);
    }

    /**
     * Adds elements to {@link ICCRequest#getInitialContext() initialContext} list.
     * @param elements An iterable of initialContext elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue
    public final Builder addAllInitialContext(Iterable<? extends IFieldType> elements) {
      this.initialContext.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link SCARequest SCARequest}.
     * @return An immutable instance of SCARequest
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public SCARequest build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new SCARequest(composition, initialContext.build());
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_COMPOSITION) != 0) attributes.add("composition");
      return "Cannot build SCARequest, some of required attributes are not set " + attributes;
    }
  }
}
