package de.upb.sede.exec;

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
 * A modifiable implementation of the {@link IExecutorContactInfo IExecutorContactInfo} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableExecutorContactInfo is not thread-safe</em>
 * @see ExecutorContactInfo
 */
@Generated(from = "IExecutorContactInfo", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IExecutorContactInfo"})
@NotThreadSafe
public final class MutableExecutorContactInfo implements IExecutorContactInfo {
  private static final long INIT_BIT_QUALIFIER = 0x1L;
  private long initBits = 0x1L;

  private @Nullable String hostAddress;
  private String qualifier;
  private String simpleName;

  private MutableExecutorContactInfo() {}

  /**
   * Construct a modifiable instance of {@code IExecutorContactInfo}.
   * @return A new modifiable instance
   */
  public static MutableExecutorContactInfo create() {
    return new MutableExecutorContactInfo();
  }

  /**
   * @return value of {@code hostAddress} attribute, may be {@code null}
   */
  @JsonProperty("hostAddress")
  @Override
  public final @Nullable String getHostAddress() {
    return hostAddress;
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
   * @return assigned or, otherwise, newly computed, not cached value of {@code simpleName} attribute
   */
  @JsonProperty("simpleName")
  @Override
  public final String getSimpleName() {
    return simpleNameIsSet()
        ? simpleName
        : IExecutorContactInfo.super.getSimpleName();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorContactInfo clear() {
    initBits = 0x1L;
    hostAddress = null;
    qualifier = null;
    simpleName = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.exec.IExecutorContactInfo} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorContactInfo from(IExecutorContactInfo instance) {
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
  public MutableExecutorContactInfo from(IQualifiable instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IExecutorContactInfo} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableExecutorContactInfo from(MutableExecutorContactInfo instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableExecutorContactInfo) {
      MutableExecutorContactInfo instance = (MutableExecutorContactInfo) object;
      @Nullable String hostAddressValue = instance.getHostAddress();
      if (hostAddressValue != null) {
        setHostAddress(hostAddressValue);
      }
      if (instance.qualifierIsSet()) {
        setQualifier(instance.getQualifier());
      }
      setSimpleName(instance.getSimpleName());
      return;
    }
    if (object instanceof IExecutorContactInfo) {
      IExecutorContactInfo instance = (IExecutorContactInfo) object;
      @Nullable String hostAddressValue = instance.getHostAddress();
      if (hostAddressValue != null) {
        setHostAddress(hostAddressValue);
      }
    }
    if (object instanceof IQualifiable) {
      IQualifiable instance = (IQualifiable) object;
      setSimpleName(instance.getSimpleName());
      setQualifier(instance.getQualifier());
    }
  }

  /**
   * Assigns a value to the {@link IExecutorContactInfo#getHostAddress() hostAddress} attribute.
   * @param hostAddress The value for hostAddress, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorContactInfo setHostAddress(@Nullable String hostAddress) {
    this.hostAddress = hostAddress;
    return this;
  }

  /**
   * Assigns a value to the {@link IExecutorContactInfo#getQualifier() qualifier} attribute.
   * @param qualifier The value for qualifier
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorContactInfo setQualifier(String qualifier) {
    this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
    initBits &= ~INIT_BIT_QUALIFIER;
    return this;
  }

  /**
   * Assigns a value to the {@link IExecutorContactInfo#getSimpleName() simpleName} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IExecutorContactInfo#getSimpleName() simpleName}.</em>
   * @param simpleName The value for simpleName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorContactInfo setSimpleName(String simpleName) {
    this.simpleName = Objects.requireNonNull(simpleName, "simpleName");
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IExecutorContactInfo#getQualifier() qualifier} is set.
   * @return {@code true} if set
   */
  public final boolean qualifierIsSet() {
    return (initBits & INIT_BIT_QUALIFIER) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IExecutorContactInfo#getSimpleName() simpleName} is set.
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
  public final MutableExecutorContactInfo unsetQualifier() {
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
    if (!qualifierIsSet()) attributes.add("qualifier");
    return "ExecutorContactInfo is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link ExecutorContactInfo ExecutorContactInfo}.
   * @return An immutable instance of ExecutorContactInfo
   */
  public final ExecutorContactInfo toImmutable() {
    checkRequiredAttributes();
    return ExecutorContactInfo.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableExecutorContactInfo} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableExecutorContactInfo)) return false;
    MutableExecutorContactInfo other = (MutableExecutorContactInfo) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableExecutorContactInfo another) {
    String simpleName = getSimpleName();
    return Objects.equals(hostAddress, another.hostAddress)
        && qualifier.equals(another.qualifier)
        && simpleName.equals(another.getSimpleName());
  }

  /**
   * Computes a hash code from attributes: {@code hostAddress}, {@code qualifier}, {@code simpleName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Objects.hashCode(hostAddress);
    h += (h << 5) + qualifier.hashCode();
    String simpleName = getSimpleName();
    h += (h << 5) + simpleName.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IExecutorContactInfo}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableExecutorContactInfo")
        .add("hostAddress", getHostAddress())
        .add("qualifier", qualifierIsSet() ? getQualifier() : "?")
        .add("simpleName", getSimpleName())
        .toString();
  }
}
