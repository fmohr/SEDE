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
 * A modifiable implementation of the {@link IExecutorHandle IExecutorHandle} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableExecutorHandle is not thread-safe</em>
 * @see ExecutorHandle
 */
@Generated(from = "IExecutorHandle", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IExecutorHandle"})
@NotThreadSafe
public final class MutableExecutorHandle implements IExecutorHandle {
  private static final long INIT_BIT_CONTACT_INFO = 0x1L;
  private static final long INIT_BIT_CAPABILITIES = 0x2L;
  private long initBits = 0x3L;

  private IExecutorContactInfo contactInfo;
  private IExecutorCapabilities capabilities;
  private String simpleName;

  private MutableExecutorHandle() {}

  /**
   * Construct a modifiable instance of {@code IExecutorHandle}.
   * @return A new modifiable instance
   */
  public static MutableExecutorHandle create() {
    return new MutableExecutorHandle();
  }

  /**
   * @return value of {@code contactInfo} attribute
   */
  @JsonProperty("contactInfo")
  @Override
  public final IExecutorContactInfo getContactInfo() {
    if (!contactInfoIsSet()) {
      checkRequiredAttributes();
    }
    return contactInfo;
  }

  /**
   * @return value of {@code capabilities} attribute
   */
  @JsonProperty("capabilities")
  @Override
  public final IExecutorCapabilities getCapabilities() {
    if (!capabilitiesIsSet()) {
      checkRequiredAttributes();
    }
    return capabilities;
  }

  /**
   * @return newly computed, not cached value of {@code qualifier} attribute
   */
  @JsonProperty("qualifier")
  @Override
  public final String getQualifier() {
    return IExecutorHandle.super.getQualifier();
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code simpleName} attribute
   */
  @JsonProperty("simpleName")
  @Override
  public final String getSimpleName() {
    return simpleNameIsSet()
        ? simpleName
        : IExecutorHandle.super.getSimpleName();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorHandle clear() {
    initBits = 0x3L;
    contactInfo = null;
    capabilities = null;
    simpleName = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.exec.IExecutorHandle} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorHandle from(IExecutorHandle instance) {
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
  public MutableExecutorHandle from(IQualifiable instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IExecutorHandle} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableExecutorHandle from(MutableExecutorHandle instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableExecutorHandle) {
      MutableExecutorHandle instance = (MutableExecutorHandle) object;
      if (instance.contactInfoIsSet()) {
        setContactInfo(instance.getContactInfo());
      }
      if (instance.capabilitiesIsSet()) {
        setCapabilities(instance.getCapabilities());
      }
      setSimpleName(instance.getSimpleName());
      return;
    }
    if (object instanceof IExecutorHandle) {
      IExecutorHandle instance = (IExecutorHandle) object;
      setContactInfo(instance.getContactInfo());
      setCapabilities(instance.getCapabilities());
    }
    if (object instanceof IQualifiable) {
      IQualifiable instance = (IQualifiable) object;
      setSimpleName(instance.getSimpleName());
    }
  }

  /**
   * Assigns a value to the {@link IExecutorHandle#getContactInfo() contactInfo} attribute.
   * @param contactInfo The value for contactInfo
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorHandle setContactInfo(IExecutorContactInfo contactInfo) {
    this.contactInfo = Objects.requireNonNull(contactInfo, "contactInfo");
    initBits &= ~INIT_BIT_CONTACT_INFO;
    return this;
  }

  /**
   * Assigns a value to the {@link IExecutorHandle#getCapabilities() capabilities} attribute.
   * @param capabilities The value for capabilities
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorHandle setCapabilities(IExecutorCapabilities capabilities) {
    this.capabilities = Objects.requireNonNull(capabilities, "capabilities");
    initBits &= ~INIT_BIT_CAPABILITIES;
    return this;
  }

  /**
   * Assigns a value to the {@link IExecutorHandle#getSimpleName() simpleName} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IExecutorHandle#getSimpleName() simpleName}.</em>
   * @param simpleName The value for simpleName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorHandle setSimpleName(String simpleName) {
    this.simpleName = Objects.requireNonNull(simpleName, "simpleName");
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IExecutorHandle#getContactInfo() contactInfo} is set.
   * @return {@code true} if set
   */
  public final boolean contactInfoIsSet() {
    return (initBits & INIT_BIT_CONTACT_INFO) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IExecutorHandle#getCapabilities() capabilities} is set.
   * @return {@code true} if set
   */
  public final boolean capabilitiesIsSet() {
    return (initBits & INIT_BIT_CAPABILITIES) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IExecutorHandle#getSimpleName() simpleName} is set.
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
  public final MutableExecutorHandle unsetContactInfo() {
    initBits |= INIT_BIT_CONTACT_INFO;
    contactInfo = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableExecutorHandle unsetCapabilities() {
    initBits |= INIT_BIT_CAPABILITIES;
    capabilities = null;
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
    if (!contactInfoIsSet()) attributes.add("contactInfo");
    if (!capabilitiesIsSet()) attributes.add("capabilities");
    return "ExecutorHandle is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link ExecutorHandle ExecutorHandle}.
   * @return An immutable instance of ExecutorHandle
   */
  public final ExecutorHandle toImmutable() {
    checkRequiredAttributes();
    return ExecutorHandle.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableExecutorHandle} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableExecutorHandle)) return false;
    MutableExecutorHandle other = (MutableExecutorHandle) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableExecutorHandle another) {
    String qualifier = getQualifier();
    String simpleName = getSimpleName();
    return contactInfo.equals(another.contactInfo)
        && capabilities.equals(another.capabilities)
        && qualifier.equals(another.getQualifier())
        && simpleName.equals(another.getSimpleName());
  }

  /**
   * Computes a hash code from attributes: {@code contactInfo}, {@code capabilities}, {@code qualifier}, {@code simpleName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + contactInfo.hashCode();
    h += (h << 5) + capabilities.hashCode();
    String qualifier = getQualifier();
    h += (h << 5) + qualifier.hashCode();
    String simpleName = getSimpleName();
    h += (h << 5) + simpleName.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IExecutorHandle}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableExecutorHandle")
        .add("contactInfo", contactInfoIsSet() ? getContactInfo() : "?")
        .add("capabilities", capabilitiesIsSet() ? getCapabilities() : "?")
        .add("simpleName", getSimpleName())
        .toString();
  }
}
