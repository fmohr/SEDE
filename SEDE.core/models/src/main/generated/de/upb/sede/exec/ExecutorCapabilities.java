package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link IExecutorCapabilities}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ExecutorCapabilities.builder()}.
 */
@Generated(from = "IExecutorCapabilities", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ExecutorCapabilities implements IExecutorCapabilities {
  private final ImmutableList<String> features;
  private final ImmutableList<String> services;

  private ExecutorCapabilities(
      ImmutableList<String> features,
      ImmutableList<String> services) {
    this.features = features;
    this.services = services;
  }

  /**
   * @return The value of the {@code features} attribute
   */
  @JsonProperty("features")
  @Override
  public ImmutableList<String> getFeatures() {
    return features;
  }

  /**
   * @return The value of the {@code services} attribute
   */
  @JsonProperty("services")
  @Override
  public ImmutableList<String> getServices() {
    return services;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IExecutorCapabilities#getFeatures() features}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ExecutorCapabilities withFeatures(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ExecutorCapabilities(newValue, this.services);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IExecutorCapabilities#getFeatures() features}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of features elements to set
   * @return A modified copy of {@code this} object
   */
  public final ExecutorCapabilities withFeatures(Iterable<String> elements) {
    if (this.features == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ExecutorCapabilities(newValue, this.services);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IExecutorCapabilities#getServices() services}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ExecutorCapabilities withServices(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ExecutorCapabilities(this.features, newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IExecutorCapabilities#getServices() services}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of services elements to set
   * @return A modified copy of {@code this} object
   */
  public final ExecutorCapabilities withServices(Iterable<String> elements) {
    if (this.services == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ExecutorCapabilities(this.features, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ExecutorCapabilities} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ExecutorCapabilities
        && equalTo((ExecutorCapabilities) another);
  }

  private boolean equalTo(ExecutorCapabilities another) {
    return features.equals(another.features)
        && services.equals(another.services);
  }

  /**
   * Computes a hash code from attributes: {@code features}, {@code services}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + features.hashCode();
    h += (h << 5) + services.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ExecutorCapabilities} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ExecutorCapabilities")
        .omitNullValues()
        .add("features", features)
        .add("services", services)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IExecutorCapabilities", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IExecutorCapabilities {
    @Nullable List<String> features = ImmutableList.of();
    @Nullable List<String> services = ImmutableList.of();
    @JsonProperty("features")
    public void setFeatures(List<String> features) {
      this.features = features;
    }
    @JsonProperty("services")
    public void setServices(List<String> services) {
      this.services = services;
    }
    @Override
    public List<String> getFeatures() { throw new UnsupportedOperationException(); }
    @Override
    public List<String> getServices() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static ExecutorCapabilities fromJson(Json json) {
    ExecutorCapabilities.Builder builder = ExecutorCapabilities.builder();
    if (json.features != null) {
      builder.addAllFeatures(json.features);
    }
    if (json.services != null) {
      builder.addAllServices(json.services);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IExecutorCapabilities} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ExecutorCapabilities instance
   */
  public static ExecutorCapabilities copyOf(IExecutorCapabilities instance) {
    if (instance instanceof ExecutorCapabilities) {
      return (ExecutorCapabilities) instance;
    }
    return ExecutorCapabilities.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ExecutorCapabilities ExecutorCapabilities}.
   * <pre>
   * ExecutorCapabilities.builder()
   *    .addFeatures|addAllFeatures(String) // {@link IExecutorCapabilities#getFeatures() features} elements
   *    .addServices|addAllServices(String) // {@link IExecutorCapabilities#getServices() services} elements
   *    .build();
   * </pre>
   * @return A new ExecutorCapabilities builder
   */
  public static ExecutorCapabilities.Builder builder() {
    return new ExecutorCapabilities.Builder();
  }

  /**
   * Builds instances of type {@link ExecutorCapabilities ExecutorCapabilities}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IExecutorCapabilities", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private ImmutableList.Builder<String> features = ImmutableList.builder();
    private ImmutableList.Builder<String> services = ImmutableList.builder();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableExecutorCapabilities} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableExecutorCapabilities instance) {
      Objects.requireNonNull(instance, "instance");
      addAllFeatures(instance.getFeatures());
      addAllServices(instance.getServices());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IExecutorCapabilities} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IExecutorCapabilities instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableExecutorCapabilities) {
        return from((MutableExecutorCapabilities) instance);
      }
      addAllFeatures(instance.getFeatures());
      addAllServices(instance.getServices());
      return this;
    }

    /**
     * Adds one element to {@link IExecutorCapabilities#getFeatures() features} list.
     * @param element A features element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addFeatures(String element) {
      this.features.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IExecutorCapabilities#getFeatures() features} list.
     * @param elements An array of features elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addFeatures(String... elements) {
      this.features.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IExecutorCapabilities#getFeatures() features} list.
     * @param elements An iterable of features elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("features")
    public final Builder features(Iterable<String> elements) {
      this.features = ImmutableList.builder();
      return addAllFeatures(elements);
    }

    /**
     * Adds elements to {@link IExecutorCapabilities#getFeatures() features} list.
     * @param elements An iterable of features elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllFeatures(Iterable<String> elements) {
      this.features.addAll(elements);
      return this;
    }

    /**
     * Adds one element to {@link IExecutorCapabilities#getServices() services} list.
     * @param element A services element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addServices(String element) {
      this.services.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IExecutorCapabilities#getServices() services} list.
     * @param elements An array of services elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addServices(String... elements) {
      this.services.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IExecutorCapabilities#getServices() services} list.
     * @param elements An iterable of services elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("services")
    public final Builder services(Iterable<String> elements) {
      this.services = ImmutableList.builder();
      return addAllServices(elements);
    }

    /**
     * Adds elements to {@link IExecutorCapabilities#getServices() services} list.
     * @param elements An iterable of services elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllServices(Iterable<String> elements) {
      this.services.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link ExecutorCapabilities ExecutorCapabilities}.
     * @return An immutable instance of ExecutorCapabilities
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ExecutorCapabilities build() {
      return new ExecutorCapabilities(features.build(), services.build());
    }
  }
}
