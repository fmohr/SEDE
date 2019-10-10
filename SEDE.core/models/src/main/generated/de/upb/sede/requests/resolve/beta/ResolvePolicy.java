package de.upb.sede.requests.resolve.beta;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Booleans;
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
 * Immutable implementation of {@link IResolvePolicy}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ResolvePolicy.builder()}.
 */
@Generated(from = "IResolvePolicy", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ResolvePolicy implements IResolvePolicy {
  private final IResolvePolicy.ReturnPolicy returnPolicy;
  private final IResolvePolicy.ReturnPolicy servicePolicy;
  private final ImmutableList<String> returnFieldnames;
  private final ImmutableList<String> persistentServices;
  private final boolean isBlockExecRequested;
  private final boolean isDotGraphRequested;

  private ResolvePolicy(
      IResolvePolicy.ReturnPolicy returnPolicy,
      IResolvePolicy.ReturnPolicy servicePolicy,
      ImmutableList<String> returnFieldnames,
      ImmutableList<String> persistentServices,
      boolean isBlockExecRequested,
      boolean isDotGraphRequested) {
    this.returnPolicy = returnPolicy;
    this.servicePolicy = servicePolicy;
    this.returnFieldnames = returnFieldnames;
    this.persistentServices = persistentServices;
    this.isBlockExecRequested = isBlockExecRequested;
    this.isDotGraphRequested = isDotGraphRequested;
  }

  /**
   * @return The value of the {@code returnPolicy} attribute
   */
  @JsonProperty("returnPolicy")
  @Override
  public IResolvePolicy.ReturnPolicy getReturnPolicy() {
    return returnPolicy;
  }

  /**
   * @return The value of the {@code servicePolicy} attribute
   */
  @JsonProperty("servicePolicy")
  @Override
  public IResolvePolicy.ReturnPolicy getServicePolicy() {
    return servicePolicy;
  }

  /**
   * @return The value of the {@code returnFieldnames} attribute
   */
  @JsonProperty("returnFieldnames")
  @Override
  public ImmutableList<String> getReturnFieldnames() {
    return returnFieldnames;
  }

  /**
   * @return The value of the {@code persistentServices} attribute
   */
  @JsonProperty("persistentServices")
  @Override
  public ImmutableList<String> getPersistentServices() {
    return persistentServices;
  }

  /**
   * @return The value of the {@code isBlockExecRequested} attribute
   */
  @JsonProperty("isBlockExecRequested")
  @Override
  public boolean isBlockExecRequested() {
    return isBlockExecRequested;
  }

  /**
   * @return The value of the {@code isDotGraphRequested} attribute
   */
  @JsonProperty("isDotGraphRequested")
  @Override
  public boolean isDotGraphRequested() {
    return isDotGraphRequested;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IResolvePolicy#getReturnPolicy() returnPolicy} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for returnPolicy
   * @return A modified copy of the {@code this} object
   */
  public final ResolvePolicy withReturnPolicy(IResolvePolicy.ReturnPolicy value) {
    if (this.returnPolicy == value) return this;
    IResolvePolicy.ReturnPolicy newValue = Objects.requireNonNull(value, "returnPolicy");
    if (this.returnPolicy.equals(newValue)) return this;
    return new ResolvePolicy(
        newValue,
        this.servicePolicy,
        this.returnFieldnames,
        this.persistentServices,
        this.isBlockExecRequested,
        this.isDotGraphRequested);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IResolvePolicy#getServicePolicy() servicePolicy} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for servicePolicy
   * @return A modified copy of the {@code this} object
   */
  public final ResolvePolicy withServicePolicy(IResolvePolicy.ReturnPolicy value) {
    if (this.servicePolicy == value) return this;
    IResolvePolicy.ReturnPolicy newValue = Objects.requireNonNull(value, "servicePolicy");
    if (this.servicePolicy.equals(newValue)) return this;
    return new ResolvePolicy(
        this.returnPolicy,
        newValue,
        this.returnFieldnames,
        this.persistentServices,
        this.isBlockExecRequested,
        this.isDotGraphRequested);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IResolvePolicy#getReturnFieldnames() returnFieldnames}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ResolvePolicy withReturnFieldnames(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ResolvePolicy(
        this.returnPolicy,
        this.servicePolicy,
        newValue,
        this.persistentServices,
        this.isBlockExecRequested,
        this.isDotGraphRequested);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IResolvePolicy#getReturnFieldnames() returnFieldnames}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of returnFieldnames elements to set
   * @return A modified copy of {@code this} object
   */
  public final ResolvePolicy withReturnFieldnames(Iterable<String> elements) {
    if (this.returnFieldnames == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ResolvePolicy(
        this.returnPolicy,
        this.servicePolicy,
        newValue,
        this.persistentServices,
        this.isBlockExecRequested,
        this.isDotGraphRequested);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IResolvePolicy#getPersistentServices() persistentServices}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ResolvePolicy withPersistentServices(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ResolvePolicy(
        this.returnPolicy,
        this.servicePolicy,
        this.returnFieldnames,
        newValue,
        this.isBlockExecRequested,
        this.isDotGraphRequested);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IResolvePolicy#getPersistentServices() persistentServices}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of persistentServices elements to set
   * @return A modified copy of {@code this} object
   */
  public final ResolvePolicy withPersistentServices(Iterable<String> elements) {
    if (this.persistentServices == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ResolvePolicy(
        this.returnPolicy,
        this.servicePolicy,
        this.returnFieldnames,
        newValue,
        this.isBlockExecRequested,
        this.isDotGraphRequested);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IResolvePolicy#isBlockExecRequested() isBlockExecRequested} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isBlockExecRequested
   * @return A modified copy of the {@code this} object
   */
  public final ResolvePolicy withIsBlockExecRequested(boolean value) {
    if (this.isBlockExecRequested == value) return this;
    return new ResolvePolicy(
        this.returnPolicy,
        this.servicePolicy,
        this.returnFieldnames,
        this.persistentServices,
        value,
        this.isDotGraphRequested);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IResolvePolicy#isDotGraphRequested() isDotGraphRequested} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isDotGraphRequested
   * @return A modified copy of the {@code this} object
   */
  public final ResolvePolicy withIsDotGraphRequested(boolean value) {
    if (this.isDotGraphRequested == value) return this;
    return new ResolvePolicy(
        this.returnPolicy,
        this.servicePolicy,
        this.returnFieldnames,
        this.persistentServices,
        this.isBlockExecRequested,
        value);
  }

  /**
   * This instance is equal to all instances of {@code ResolvePolicy} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ResolvePolicy
        && equalTo((ResolvePolicy) another);
  }

  private boolean equalTo(ResolvePolicy another) {
    return returnPolicy.equals(another.returnPolicy)
        && servicePolicy.equals(another.servicePolicy)
        && returnFieldnames.equals(another.returnFieldnames)
        && persistentServices.equals(another.persistentServices)
        && isBlockExecRequested == another.isBlockExecRequested
        && isDotGraphRequested == another.isDotGraphRequested;
  }

  /**
   * Computes a hash code from attributes: {@code returnPolicy}, {@code servicePolicy}, {@code returnFieldnames}, {@code persistentServices}, {@code isBlockExecRequested}, {@code isDotGraphRequested}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + returnPolicy.hashCode();
    h += (h << 5) + servicePolicy.hashCode();
    h += (h << 5) + returnFieldnames.hashCode();
    h += (h << 5) + persistentServices.hashCode();
    h += (h << 5) + Booleans.hashCode(isBlockExecRequested);
    h += (h << 5) + Booleans.hashCode(isDotGraphRequested);
    return h;
  }

  /**
   * Prints the immutable value {@code ResolvePolicy} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ResolvePolicy")
        .omitNullValues()
        .add("returnPolicy", returnPolicy)
        .add("servicePolicy", servicePolicy)
        .add("returnFieldnames", returnFieldnames)
        .add("persistentServices", persistentServices)
        .add("isBlockExecRequested", isBlockExecRequested)
        .add("isDotGraphRequested", isDotGraphRequested)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IResolvePolicy", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IResolvePolicy {
    @Nullable IResolvePolicy.ReturnPolicy returnPolicy;
    @Nullable IResolvePolicy.ReturnPolicy servicePolicy;
    @Nullable List<String> returnFieldnames = ImmutableList.of();
    @Nullable List<String> persistentServices = ImmutableList.of();
    boolean isBlockExecRequested;
    boolean isBlockExecRequestedIsSet;
    boolean isDotGraphRequested;
    boolean isDotGraphRequestedIsSet;
    @JsonProperty("returnPolicy")
    public void setReturnPolicy(IResolvePolicy.ReturnPolicy returnPolicy) {
      this.returnPolicy = returnPolicy;
    }
    @JsonProperty("servicePolicy")
    public void setServicePolicy(IResolvePolicy.ReturnPolicy servicePolicy) {
      this.servicePolicy = servicePolicy;
    }
    @JsonProperty("returnFieldnames")
    public void setReturnFieldnames(List<String> returnFieldnames) {
      this.returnFieldnames = returnFieldnames;
    }
    @JsonProperty("persistentServices")
    public void setPersistentServices(List<String> persistentServices) {
      this.persistentServices = persistentServices;
    }
    @JsonProperty("isBlockExecRequested")
    public void setIsBlockExecRequested(boolean isBlockExecRequested) {
      this.isBlockExecRequested = isBlockExecRequested;
      this.isBlockExecRequestedIsSet = true;
    }
    @JsonProperty("isDotGraphRequested")
    public void setIsDotGraphRequested(boolean isDotGraphRequested) {
      this.isDotGraphRequested = isDotGraphRequested;
      this.isDotGraphRequestedIsSet = true;
    }
    @Override
    public IResolvePolicy.ReturnPolicy getReturnPolicy() { throw new UnsupportedOperationException(); }
    @Override
    public IResolvePolicy.ReturnPolicy getServicePolicy() { throw new UnsupportedOperationException(); }
    @Override
    public List<String> getReturnFieldnames() { throw new UnsupportedOperationException(); }
    @Override
    public List<String> getPersistentServices() { throw new UnsupportedOperationException(); }
    @Override
    public boolean isBlockExecRequested() { throw new UnsupportedOperationException(); }
    @Override
    public boolean isDotGraphRequested() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static ResolvePolicy fromJson(Json json) {
    ResolvePolicy.Builder builder = ResolvePolicy.builder();
    if (json.returnPolicy != null) {
      builder.returnPolicy(json.returnPolicy);
    }
    if (json.servicePolicy != null) {
      builder.servicePolicy(json.servicePolicy);
    }
    if (json.returnFieldnames != null) {
      builder.addAllReturnFieldnames(json.returnFieldnames);
    }
    if (json.persistentServices != null) {
      builder.addAllPersistentServices(json.persistentServices);
    }
    if (json.isBlockExecRequestedIsSet) {
      builder.isBlockExecRequested(json.isBlockExecRequested);
    }
    if (json.isDotGraphRequestedIsSet) {
      builder.isDotGraphRequested(json.isDotGraphRequested);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IResolvePolicy} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ResolvePolicy instance
   */
  public static ResolvePolicy copyOf(IResolvePolicy instance) {
    if (instance instanceof ResolvePolicy) {
      return (ResolvePolicy) instance;
    }
    return ResolvePolicy.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ResolvePolicy ResolvePolicy}.
   * <pre>
   * ResolvePolicy.builder()
   *    .returnPolicy(de.upb.sede.requests.resolve.beta.IResolvePolicy.ReturnPolicy) // required {@link IResolvePolicy#getReturnPolicy() returnPolicy}
   *    .servicePolicy(de.upb.sede.requests.resolve.beta.IResolvePolicy.ReturnPolicy) // required {@link IResolvePolicy#getServicePolicy() servicePolicy}
   *    .addReturnFieldnames|addAllReturnFieldnames(String) // {@link IResolvePolicy#getReturnFieldnames() returnFieldnames} elements
   *    .addPersistentServices|addAllPersistentServices(String) // {@link IResolvePolicy#getPersistentServices() persistentServices} elements
   *    .isBlockExecRequested(boolean) // required {@link IResolvePolicy#isBlockExecRequested() isBlockExecRequested}
   *    .isDotGraphRequested(boolean) // required {@link IResolvePolicy#isDotGraphRequested() isDotGraphRequested}
   *    .build();
   * </pre>
   * @return A new ResolvePolicy builder
   */
  public static ResolvePolicy.Builder builder() {
    return new ResolvePolicy.Builder();
  }

  /**
   * Builds instances of type {@link ResolvePolicy ResolvePolicy}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IResolvePolicy", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_RETURN_POLICY = 0x1L;
    private static final long INIT_BIT_SERVICE_POLICY = 0x2L;
    private static final long INIT_BIT_IS_BLOCK_EXEC_REQUESTED = 0x4L;
    private static final long INIT_BIT_IS_DOT_GRAPH_REQUESTED = 0x8L;
    private long initBits = 0xfL;

    private @Nullable IResolvePolicy.ReturnPolicy returnPolicy;
    private @Nullable IResolvePolicy.ReturnPolicy servicePolicy;
    private ImmutableList.Builder<String> returnFieldnames = ImmutableList.builder();
    private ImmutableList.Builder<String> persistentServices = ImmutableList.builder();
    private boolean isBlockExecRequested;
    private boolean isDotGraphRequested;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableResolvePolicy} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableResolvePolicy instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.returnPolicyIsSet()) {
        returnPolicy(instance.getReturnPolicy());
      }
      if (instance.servicePolicyIsSet()) {
        servicePolicy(instance.getServicePolicy());
      }
      addAllReturnFieldnames(instance.getReturnFieldnames());
      addAllPersistentServices(instance.getPersistentServices());
      if (instance.isBlockExecRequestedIsSet()) {
        isBlockExecRequested(instance.isBlockExecRequested());
      }
      if (instance.isDotGraphRequestedIsSet()) {
        isDotGraphRequested(instance.isDotGraphRequested());
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IResolvePolicy} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IResolvePolicy instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableResolvePolicy) {
        return from((MutableResolvePolicy) instance);
      }
      returnPolicy(instance.getReturnPolicy());
      servicePolicy(instance.getServicePolicy());
      addAllReturnFieldnames(instance.getReturnFieldnames());
      addAllPersistentServices(instance.getPersistentServices());
      isBlockExecRequested(instance.isBlockExecRequested());
      isDotGraphRequested(instance.isDotGraphRequested());
      return this;
    }

    /**
     * Initializes the value for the {@link IResolvePolicy#getReturnPolicy() returnPolicy} attribute.
     * @param returnPolicy The value for returnPolicy 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("returnPolicy")
    public final Builder returnPolicy(IResolvePolicy.ReturnPolicy returnPolicy) {
      this.returnPolicy = Objects.requireNonNull(returnPolicy, "returnPolicy");
      initBits &= ~INIT_BIT_RETURN_POLICY;
      return this;
    }

    /**
     * Initializes the value for the {@link IResolvePolicy#getServicePolicy() servicePolicy} attribute.
     * @param servicePolicy The value for servicePolicy 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("servicePolicy")
    public final Builder servicePolicy(IResolvePolicy.ReturnPolicy servicePolicy) {
      this.servicePolicy = Objects.requireNonNull(servicePolicy, "servicePolicy");
      initBits &= ~INIT_BIT_SERVICE_POLICY;
      return this;
    }

    /**
     * Adds one element to {@link IResolvePolicy#getReturnFieldnames() returnFieldnames} list.
     * @param element A returnFieldnames element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addReturnFieldnames(String element) {
      this.returnFieldnames.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IResolvePolicy#getReturnFieldnames() returnFieldnames} list.
     * @param elements An array of returnFieldnames elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addReturnFieldnames(String... elements) {
      this.returnFieldnames.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IResolvePolicy#getReturnFieldnames() returnFieldnames} list.
     * @param elements An iterable of returnFieldnames elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("returnFieldnames")
    public final Builder returnFieldnames(Iterable<String> elements) {
      this.returnFieldnames = ImmutableList.builder();
      return addAllReturnFieldnames(elements);
    }

    /**
     * Adds elements to {@link IResolvePolicy#getReturnFieldnames() returnFieldnames} list.
     * @param elements An iterable of returnFieldnames elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllReturnFieldnames(Iterable<String> elements) {
      this.returnFieldnames.addAll(elements);
      return this;
    }

    /**
     * Adds one element to {@link IResolvePolicy#getPersistentServices() persistentServices} list.
     * @param element A persistentServices element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addPersistentServices(String element) {
      this.persistentServices.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IResolvePolicy#getPersistentServices() persistentServices} list.
     * @param elements An array of persistentServices elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addPersistentServices(String... elements) {
      this.persistentServices.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IResolvePolicy#getPersistentServices() persistentServices} list.
     * @param elements An iterable of persistentServices elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("persistentServices")
    public final Builder persistentServices(Iterable<String> elements) {
      this.persistentServices = ImmutableList.builder();
      return addAllPersistentServices(elements);
    }

    /**
     * Adds elements to {@link IResolvePolicy#getPersistentServices() persistentServices} list.
     * @param elements An iterable of persistentServices elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllPersistentServices(Iterable<String> elements) {
      this.persistentServices.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link IResolvePolicy#isBlockExecRequested() isBlockExecRequested} attribute.
     * @param isBlockExecRequested The value for isBlockExecRequested 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("isBlockExecRequested")
    public final Builder isBlockExecRequested(boolean isBlockExecRequested) {
      this.isBlockExecRequested = isBlockExecRequested;
      initBits &= ~INIT_BIT_IS_BLOCK_EXEC_REQUESTED;
      return this;
    }

    /**
     * Initializes the value for the {@link IResolvePolicy#isDotGraphRequested() isDotGraphRequested} attribute.
     * @param isDotGraphRequested The value for isDotGraphRequested 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("isDotGraphRequested")
    public final Builder isDotGraphRequested(boolean isDotGraphRequested) {
      this.isDotGraphRequested = isDotGraphRequested;
      initBits &= ~INIT_BIT_IS_DOT_GRAPH_REQUESTED;
      return this;
    }

    /**
     * Builds a new {@link ResolvePolicy ResolvePolicy}.
     * @return An immutable instance of ResolvePolicy
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ResolvePolicy build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ResolvePolicy(
          returnPolicy,
          servicePolicy,
          returnFieldnames.build(),
          persistentServices.build(),
          isBlockExecRequested,
          isDotGraphRequested);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_RETURN_POLICY) != 0) attributes.add("returnPolicy");
      if ((initBits & INIT_BIT_SERVICE_POLICY) != 0) attributes.add("servicePolicy");
      if ((initBits & INIT_BIT_IS_BLOCK_EXEC_REQUESTED) != 0) attributes.add("isBlockExecRequested");
      if ((initBits & INIT_BIT_IS_DOT_GRAPH_REQUESTED) != 0) attributes.add("isDotGraphRequested");
      return "Cannot build ResolvePolicy, some of required attributes are not set " + attributes;
    }
  }
}
