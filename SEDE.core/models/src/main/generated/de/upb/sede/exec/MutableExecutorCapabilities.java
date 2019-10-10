package de.upb.sede.exec;

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
 * A modifiable implementation of the {@link IExecutorCapabilities IExecutorCapabilities} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableExecutorCapabilities is not thread-safe</em>
 * @see ExecutorCapabilities
 */
@Generated(from = "IExecutorCapabilities", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IExecutorCapabilities"})
@NotThreadSafe
public final class MutableExecutorCapabilities implements IExecutorCapabilities {
  private final ArrayList<String> features = new ArrayList<String>();
  private final ArrayList<String> services = new ArrayList<String>();

  private MutableExecutorCapabilities() {}

  /**
   * Construct a modifiable instance of {@code IExecutorCapabilities}.
   * @return A new modifiable instance
   */
  public static MutableExecutorCapabilities create() {
    return new MutableExecutorCapabilities();
  }

  /**
   * @return modifiable list {@code features}
   */
  @JsonProperty("features")
  @Override
  public final List<String> getFeatures() {
    return features;
  }

  /**
   * @return modifiable list {@code services}
   */
  @JsonProperty("services")
  @Override
  public final List<String> getServices() {
    return services;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorCapabilities clear() {
    features.clear();
    services.clear();
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IExecutorCapabilities} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableExecutorCapabilities from(IExecutorCapabilities instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableExecutorCapabilities) {
      from((MutableExecutorCapabilities) instance);
      return this;
    }
    addAllFeatures(instance.getFeatures());
    addAllServices(instance.getServices());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IExecutorCapabilities} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableExecutorCapabilities from(MutableExecutorCapabilities instance) {
    Objects.requireNonNull(instance, "instance");
    addAllFeatures(instance.getFeatures());
    addAllServices(instance.getServices());
    return this;
  }

  /**
   * Adds one element to {@link IExecutorCapabilities#getFeatures() features} list.
   * @param element The features element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorCapabilities addFeatures(String element) {
    Objects.requireNonNull(element, "features element");
    this.features.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IExecutorCapabilities#getFeatures() features} list.
   * @param elements An array of features elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableExecutorCapabilities addFeatures(String... elements) {
    for (String e : elements) {
      addFeatures(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IExecutorCapabilities#getFeatures() features} list.
   * @param elements An iterable of features elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorCapabilities setFeatures(Iterable<String> elements) {
    this.features.clear();
    addAllFeatures(elements);
    return this;
  }

  /**
   * Adds elements to {@link IExecutorCapabilities#getFeatures() features} list.
   * @param elements An iterable of features elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorCapabilities addAllFeatures(Iterable<String> elements) {
    for (String e : elements) {
      addFeatures(e);
    }
    return this;
  }

  /**
   * Adds one element to {@link IExecutorCapabilities#getServices() services} list.
   * @param element The services element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorCapabilities addServices(String element) {
    Objects.requireNonNull(element, "services element");
    this.services.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IExecutorCapabilities#getServices() services} list.
   * @param elements An array of services elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableExecutorCapabilities addServices(String... elements) {
    for (String e : elements) {
      addServices(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IExecutorCapabilities#getServices() services} list.
   * @param elements An iterable of services elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorCapabilities setServices(Iterable<String> elements) {
    this.services.clear();
    addAllServices(elements);
    return this;
  }

  /**
   * Adds elements to {@link IExecutorCapabilities#getServices() services} list.
   * @param elements An iterable of services elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorCapabilities addAllServices(Iterable<String> elements) {
    for (String e : elements) {
      addServices(e);
    }
    return this;
  }


  /**
   * Returns {@code true} if all required attributes are set, indicating that the object is initialized.
   * @return {@code true} if set
   */
  public final boolean isInitialized() {
    return true;
  }

  /**
   * Converts to {@link ExecutorCapabilities ExecutorCapabilities}.
   * @return An immutable instance of ExecutorCapabilities
   */
  public final ExecutorCapabilities toImmutable() {
    return ExecutorCapabilities.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableExecutorCapabilities} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableExecutorCapabilities)) return false;
    MutableExecutorCapabilities other = (MutableExecutorCapabilities) another;
    return equalTo(other);
  }

  private boolean equalTo(MutableExecutorCapabilities another) {
    return features.equals(another.features)
        && services.equals(another.services);
  }

  /**
   * Computes a hash code from attributes: {@code features}, {@code services}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + features.hashCode();
    h += (h << 5) + services.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IExecutorCapabilities}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableExecutorCapabilities")
        .add("features", getFeatures())
        .add("services", getServices())
        .toString();
  }
}
