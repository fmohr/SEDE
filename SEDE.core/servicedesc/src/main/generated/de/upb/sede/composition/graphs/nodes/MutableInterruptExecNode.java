package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.exec.IExecutorContactInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IInterruptExecNode IInterruptExecNode} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableInterruptExecNode is not thread-safe</em>
 * @see InterruptExecNode
 */
@Generated(from = "IInterruptExecNode", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IInterruptExecNode"})
@NotThreadSafe
public final class MutableInterruptExecNode implements IInterruptExecNode {
  private static final long INIT_BIT_CONTACT_INFO = 0x1L;
  private long initBits = 0x1L;

  private IExecutorContactInfo contactInfo;

  private MutableInterruptExecNode() {}

  /**
   * Construct a modifiable instance of {@code IInterruptExecNode}.
   * @return A new modifiable instance
   */
  public static MutableInterruptExecNode create() {
    return new MutableInterruptExecNode();
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
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInterruptExecNode clear() {
    initBits = 0x1L;
    contactInfo = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IInterruptExecNode} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableInterruptExecNode from(IInterruptExecNode instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableInterruptExecNode) {
      from((MutableInterruptExecNode) instance);
      return this;
    }
    setContactInfo(instance.getContactInfo());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IInterruptExecNode} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableInterruptExecNode from(MutableInterruptExecNode instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.contactInfoIsSet()) {
      setContactInfo(instance.getContactInfo());
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IInterruptExecNode#getContactInfo() contactInfo} attribute.
   * @param contactInfo The value for contactInfo
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInterruptExecNode setContactInfo(IExecutorContactInfo contactInfo) {
    this.contactInfo = Objects.requireNonNull(contactInfo, "contactInfo");
    initBits &= ~INIT_BIT_CONTACT_INFO;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IInterruptExecNode#getContactInfo() contactInfo} is set.
   * @return {@code true} if set
   */
  public final boolean contactInfoIsSet() {
    return (initBits & INIT_BIT_CONTACT_INFO) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableInterruptExecNode unsetContactInfo() {
    initBits |= INIT_BIT_CONTACT_INFO;
    contactInfo = null;
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
    return "InterruptExecNode is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link InterruptExecNode InterruptExecNode}.
   * @return An immutable instance of InterruptExecNode
   */
  public final InterruptExecNode toImmutable() {
    checkRequiredAttributes();
    return InterruptExecNode.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableInterruptExecNode} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableInterruptExecNode)) return false;
    MutableInterruptExecNode other = (MutableInterruptExecNode) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableInterruptExecNode another) {
    return contactInfo.equals(another.contactInfo);
  }

  /**
   * Computes a hash code from attributes: {@code contactInfo}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + contactInfo.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IInterruptExecNode}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableInterruptExecNode")
        .add("contactInfo", contactInfoIsSet() ? getContactInfo() : "?")
        .toString();
  }
}
