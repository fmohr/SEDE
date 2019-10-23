package de.upb.sede.requests.resolve.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IResolvePolicy IResolvePolicy} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableResolvePolicy is not thread-safe</em>
 * @see ResolvePolicy
 */
@Generated(from = "IResolvePolicy", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IResolvePolicy"})
@NotThreadSafe
public final class MutableResolvePolicy implements IResolvePolicy {
  private static final long INIT_BIT_RETURN_POLICY = 0x1L;
  private static final long INIT_BIT_SERVICE_POLICY = 0x2L;
  private static final long INIT_BIT_IS_BLOCK_EXEC_REQUESTED = 0x4L;
  private static final long INIT_BIT_IS_DOT_GRAPH_REQUESTED = 0x8L;
  private long initBits = 0xfL;

  private IResolvePolicy.ReturnPolicy returnPolicy;
  private IResolvePolicy.ReturnPolicy servicePolicy;
  private final ArrayList<String> returnFieldnames = new ArrayList<String>();
  private final ArrayList<String> persistentServices = new ArrayList<String>();
  private boolean isBlockExecRequested;
  private boolean isDotGraphRequested;

  private MutableResolvePolicy() {}

  /**
   * Construct a modifiable instance of {@code IResolvePolicy}.
   * @return A new modifiable instance
   */
  public static MutableResolvePolicy create() {
    return new MutableResolvePolicy();
  }

  /**
   * @return value of {@code returnPolicy} attribute
   */
  @JsonProperty("returnPolicy")
  @Override
  public final IResolvePolicy.ReturnPolicy getReturnPolicy() {
    if (!returnPolicyIsSet()) {
      checkRequiredAttributes();
    }
    return returnPolicy;
  }

  /**
   * @return value of {@code servicePolicy} attribute
   */
  @JsonProperty("servicePolicy")
  @Override
  public final IResolvePolicy.ReturnPolicy getServicePolicy() {
    if (!servicePolicyIsSet()) {
      checkRequiredAttributes();
    }
    return servicePolicy;
  }

  /**
   * @return modifiable list {@code returnFieldnames}
   */
  @JsonProperty("returnFieldnames")
  @Override
  public final List<String> getReturnFieldnames() {
    return returnFieldnames;
  }

  /**
   * @return modifiable list {@code persistentServices}
   */
  @JsonProperty("persistentServices")
  @Override
  public final List<String> getPersistentServices() {
    return persistentServices;
  }

  /**
   * @return value of {@code isBlockExecRequested} attribute
   */
  @JsonProperty("isBlockExecRequested")
  @Override
  public final boolean isBlockExecRequested() {
    if (!isBlockExecRequestedIsSet()) {
      checkRequiredAttributes();
    }
    return isBlockExecRequested;
  }

  /**
   * @return value of {@code isDotGraphRequested} attribute
   */
  @JsonProperty("isDotGraphRequested")
  @Override
  public final boolean isDotGraphRequested() {
    if (!isDotGraphRequestedIsSet()) {
      checkRequiredAttributes();
    }
    return isDotGraphRequested;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableResolvePolicy clear() {
    initBits = 0xfL;
    returnPolicy = null;
    servicePolicy = null;
    returnFieldnames.clear();
    persistentServices.clear();
    isBlockExecRequested = false;
    isDotGraphRequested = false;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IResolvePolicy} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableResolvePolicy from(IResolvePolicy instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableResolvePolicy) {
      from((MutableResolvePolicy) instance);
      return this;
    }
    setReturnPolicy(instance.getReturnPolicy());
    setServicePolicy(instance.getServicePolicy());
    addAllReturnFieldnames(instance.getReturnFieldnames());
    addAllPersistentServices(instance.getPersistentServices());
    setIsBlockExecRequested(instance.isBlockExecRequested());
    setIsDotGraphRequested(instance.isDotGraphRequested());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IResolvePolicy} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableResolvePolicy from(MutableResolvePolicy instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.returnPolicyIsSet()) {
      setReturnPolicy(instance.getReturnPolicy());
    }
    if (instance.servicePolicyIsSet()) {
      setServicePolicy(instance.getServicePolicy());
    }
    addAllReturnFieldnames(instance.getReturnFieldnames());
    addAllPersistentServices(instance.getPersistentServices());
    if (instance.isBlockExecRequestedIsSet()) {
      setIsBlockExecRequested(instance.isBlockExecRequested());
    }
    if (instance.isDotGraphRequestedIsSet()) {
      setIsDotGraphRequested(instance.isDotGraphRequested());
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IResolvePolicy#getReturnPolicy() returnPolicy} attribute.
   * @param returnPolicy The value for returnPolicy
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableResolvePolicy setReturnPolicy(IResolvePolicy.ReturnPolicy returnPolicy) {
    this.returnPolicy = Objects.requireNonNull(returnPolicy, "returnPolicy");
    initBits &= ~INIT_BIT_RETURN_POLICY;
    return this;
  }

  /**
   * Assigns a value to the {@link IResolvePolicy#getServicePolicy() servicePolicy} attribute.
   * @param servicePolicy The value for servicePolicy
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableResolvePolicy setServicePolicy(IResolvePolicy.ReturnPolicy servicePolicy) {
    this.servicePolicy = Objects.requireNonNull(servicePolicy, "servicePolicy");
    initBits &= ~INIT_BIT_SERVICE_POLICY;
    return this;
  }

  /**
   * Adds one element to {@link IResolvePolicy#getReturnFieldnames() returnFieldnames} list.
   * @param element The returnFieldnames element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableResolvePolicy addReturnFieldnames(String element) {
    Objects.requireNonNull(element, "returnFieldnames element");
    this.returnFieldnames.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IResolvePolicy#getReturnFieldnames() returnFieldnames} list.
   * @param elements An array of returnFieldnames elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableResolvePolicy addReturnFieldnames(String... elements) {
    for (String e : elements) {
      addReturnFieldnames(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IResolvePolicy#getReturnFieldnames() returnFieldnames} list.
   * @param elements An iterable of returnFieldnames elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableResolvePolicy setReturnFieldnames(Iterable<String> elements) {
    this.returnFieldnames.clear();
    addAllReturnFieldnames(elements);
    return this;
  }

  /**
   * Adds elements to {@link IResolvePolicy#getReturnFieldnames() returnFieldnames} list.
   * @param elements An iterable of returnFieldnames elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableResolvePolicy addAllReturnFieldnames(Iterable<String> elements) {
    for (String e : elements) {
      addReturnFieldnames(e);
    }
    return this;
  }

  /**
   * Adds one element to {@link IResolvePolicy#getPersistentServices() persistentServices} list.
   * @param element The persistentServices element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableResolvePolicy addPersistentServices(String element) {
    Objects.requireNonNull(element, "persistentServices element");
    this.persistentServices.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IResolvePolicy#getPersistentServices() persistentServices} list.
   * @param elements An array of persistentServices elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableResolvePolicy addPersistentServices(String... elements) {
    for (String e : elements) {
      addPersistentServices(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IResolvePolicy#getPersistentServices() persistentServices} list.
   * @param elements An iterable of persistentServices elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableResolvePolicy setPersistentServices(Iterable<String> elements) {
    this.persistentServices.clear();
    addAllPersistentServices(elements);
    return this;
  }

  /**
   * Adds elements to {@link IResolvePolicy#getPersistentServices() persistentServices} list.
   * @param elements An iterable of persistentServices elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableResolvePolicy addAllPersistentServices(Iterable<String> elements) {
    for (String e : elements) {
      addPersistentServices(e);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IResolvePolicy#isBlockExecRequested() isBlockExecRequested} attribute.
   * @param isBlockExecRequested The value for isBlockExecRequested
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableResolvePolicy setIsBlockExecRequested(boolean isBlockExecRequested) {
    this.isBlockExecRequested = isBlockExecRequested;
    initBits &= ~INIT_BIT_IS_BLOCK_EXEC_REQUESTED;
    return this;
  }

  /**
   * Assigns a value to the {@link IResolvePolicy#isDotGraphRequested() isDotGraphRequested} attribute.
   * @param isDotGraphRequested The value for isDotGraphRequested
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableResolvePolicy setIsDotGraphRequested(boolean isDotGraphRequested) {
    this.isDotGraphRequested = isDotGraphRequested;
    initBits &= ~INIT_BIT_IS_DOT_GRAPH_REQUESTED;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IResolvePolicy#getReturnPolicy() returnPolicy} is set.
   * @return {@code true} if set
   */
  public final boolean returnPolicyIsSet() {
    return (initBits & INIT_BIT_RETURN_POLICY) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IResolvePolicy#getServicePolicy() servicePolicy} is set.
   * @return {@code true} if set
   */
  public final boolean servicePolicyIsSet() {
    return (initBits & INIT_BIT_SERVICE_POLICY) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IResolvePolicy#isBlockExecRequested() isBlockExecRequested} is set.
   * @return {@code true} if set
   */
  public final boolean isBlockExecRequestedIsSet() {
    return (initBits & INIT_BIT_IS_BLOCK_EXEC_REQUESTED) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IResolvePolicy#isDotGraphRequested() isDotGraphRequested} is set.
   * @return {@code true} if set
   */
  public final boolean isDotGraphRequestedIsSet() {
    return (initBits & INIT_BIT_IS_DOT_GRAPH_REQUESTED) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableResolvePolicy unsetReturnPolicy() {
    initBits |= INIT_BIT_RETURN_POLICY;
    returnPolicy = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableResolvePolicy unsetServicePolicy() {
    initBits |= INIT_BIT_SERVICE_POLICY;
    servicePolicy = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableResolvePolicy unsetIsBlockExecRequested() {
    initBits |= INIT_BIT_IS_BLOCK_EXEC_REQUESTED;
    isBlockExecRequested = false;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableResolvePolicy unsetIsDotGraphRequested() {
    initBits |= INIT_BIT_IS_DOT_GRAPH_REQUESTED;
    isDotGraphRequested = false;
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
    if (!returnPolicyIsSet()) attributes.add("returnPolicy");
    if (!servicePolicyIsSet()) attributes.add("servicePolicy");
    if (!isBlockExecRequestedIsSet()) attributes.add("isBlockExecRequested");
    if (!isDotGraphRequestedIsSet()) attributes.add("isDotGraphRequested");
    return "ResolvePolicy is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link ResolvePolicy ResolvePolicy}.
   * @return An immutable instance of ResolvePolicy
   */
  public final ResolvePolicy toImmutable() {
    checkRequiredAttributes();
    return ResolvePolicy.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableResolvePolicy} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableResolvePolicy)) return false;
    MutableResolvePolicy other = (MutableResolvePolicy) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableResolvePolicy another) {
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
    int h = 5381;
    h += (h << 5) + returnPolicy.hashCode();
    h += (h << 5) + servicePolicy.hashCode();
    h += (h << 5) + returnFieldnames.hashCode();
    h += (h << 5) + persistentServices.hashCode();
    h += (h << 5) + Booleans.hashCode(isBlockExecRequested);
    h += (h << 5) + Booleans.hashCode(isDotGraphRequested);
    return h;
  }

  /**
   * Generates a string representation of this {@code IResolvePolicy}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableResolvePolicy")
        .add("returnPolicy", returnPolicyIsSet() ? getReturnPolicy() : "?")
        .add("servicePolicy", servicePolicyIsSet() ? getServicePolicy() : "?")
        .add("returnFieldnames", getReturnFieldnames())
        .add("persistentServices", getPersistentServices())
        .add("isBlockExecRequested", isBlockExecRequestedIsSet() ? isBlockExecRequested() : "?")
        .add("isDotGraphRequested", isDotGraphRequestedIsSet() ? isDotGraphRequested() : "?")
        .toString();
  }
}
