package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.exec.IExecutorContactInfo;
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
 * Immutable implementation of {@link IInterruptExecNode}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code InterruptExecNode.builder()}.
 */
@Generated(from = "IInterruptExecNode", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class InterruptExecNode implements IInterruptExecNode {
  private final IExecutorContactInfo contactInfo;

  private InterruptExecNode(IExecutorContactInfo contactInfo) {
    this.contactInfo = contactInfo;
  }

  /**
   * @return The value of the {@code contactInfo} attribute
   */
  @JsonProperty("contactInfo")
  @Override
  public IExecutorContactInfo getContactInfo() {
    return contactInfo;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IInterruptExecNode#getContactInfo() contactInfo} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for contactInfo
   * @return A modified copy of the {@code this} object
   */
  public final InterruptExecNode withContactInfo(IExecutorContactInfo value) {
    if (this.contactInfo == value) return this;
    IExecutorContactInfo newValue = Objects.requireNonNull(value, "contactInfo");
    return new InterruptExecNode(newValue);
  }

  /**
   * This instance is equal to all instances of {@code InterruptExecNode} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof InterruptExecNode
        && equalTo((InterruptExecNode) another);
  }

  private boolean equalTo(InterruptExecNode another) {
    return contactInfo.equals(another.contactInfo);
  }

  /**
   * Computes a hash code from attributes: {@code contactInfo}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + contactInfo.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code InterruptExecNode} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("InterruptExecNode")
        .omitNullValues()
        .add("contactInfo", contactInfo)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IInterruptExecNode", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IInterruptExecNode {
    @Nullable IExecutorContactInfo contactInfo;
    @JsonProperty("contactInfo")
    public void setContactInfo(IExecutorContactInfo contactInfo) {
      this.contactInfo = contactInfo;
    }
    @Override
    public IExecutorContactInfo getContactInfo() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static InterruptExecNode fromJson(Json json) {
    InterruptExecNode.Builder builder = InterruptExecNode.builder();
    if (json.contactInfo != null) {
      builder.contactInfo(json.contactInfo);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IInterruptExecNode} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable InterruptExecNode instance
   */
  public static InterruptExecNode copyOf(IInterruptExecNode instance) {
    if (instance instanceof InterruptExecNode) {
      return (InterruptExecNode) instance;
    }
    return InterruptExecNode.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link InterruptExecNode InterruptExecNode}.
   * <pre>
   * InterruptExecNode.builder()
   *    .contactInfo(de.upb.sede.exec.IExecutorContactInfo) // required {@link IInterruptExecNode#getContactInfo() contactInfo}
   *    .build();
   * </pre>
   * @return A new InterruptExecNode builder
   */
  public static InterruptExecNode.Builder builder() {
    return new InterruptExecNode.Builder();
  }

  /**
   * Builds instances of type {@link InterruptExecNode InterruptExecNode}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IInterruptExecNode", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_CONTACT_INFO = 0x1L;
    private long initBits = 0x1L;

    private @Nullable IExecutorContactInfo contactInfo;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableInterruptExecNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableInterruptExecNode instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.contactInfoIsSet()) {
        contactInfo(instance.getContactInfo());
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IInterruptExecNode} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IInterruptExecNode instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableInterruptExecNode) {
        return from((MutableInterruptExecNode) instance);
      }
      contactInfo(instance.getContactInfo());
      return this;
    }

    /**
     * Initializes the value for the {@link IInterruptExecNode#getContactInfo() contactInfo} attribute.
     * @param contactInfo The value for contactInfo 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("contactInfo")
    public final Builder contactInfo(IExecutorContactInfo contactInfo) {
      this.contactInfo = Objects.requireNonNull(contactInfo, "contactInfo");
      initBits &= ~INIT_BIT_CONTACT_INFO;
      return this;
    }

    /**
     * Builds a new {@link InterruptExecNode InterruptExecNode}.
     * @return An immutable instance of InterruptExecNode
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public InterruptExecNode build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new InterruptExecNode(contactInfo);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_CONTACT_INFO) != 0) attributes.add("contactInfo");
      return "Cannot build InterruptExecNode, some of required attributes are not set " + attributes;
    }
  }
}
