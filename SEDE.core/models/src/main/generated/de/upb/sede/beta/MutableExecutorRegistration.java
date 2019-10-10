package de.upb.sede.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.exec.IExecutorHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IExecutorRegistration IExecutorRegistration} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableExecutorRegistration is not thread-safe</em>
 * @see ExecutorRegistration
 */
@Generated(from = "IExecutorRegistration", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IExecutorRegistration"})
@NotThreadSafe
public final class MutableExecutorRegistration implements IExecutorRegistration {
  private static final long INIT_BIT_EXECUTOR_HANDLE = 0x1L;
  private long initBits = 0x1L;

  private IExecutorHandle executorHandle;

  private MutableExecutorRegistration() {}

  /**
   * Construct a modifiable instance of {@code IExecutorRegistration}.
   * @return A new modifiable instance
   */
  public static MutableExecutorRegistration create() {
    return new MutableExecutorRegistration();
  }

  /**
   * @return value of {@code executorHandle} attribute
   */
  @JsonProperty("executorHandle")
  @Override
  public final IExecutorHandle getExecutorHandle() {
    if (!executorHandleIsSet()) {
      checkRequiredAttributes();
    }
    return executorHandle;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorRegistration clear() {
    initBits = 0x1L;
    executorHandle = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IExecutorRegistration} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableExecutorRegistration from(IExecutorRegistration instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableExecutorRegistration) {
      from((MutableExecutorRegistration) instance);
      return this;
    }
    setExecutorHandle(instance.getExecutorHandle());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IExecutorRegistration} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableExecutorRegistration from(MutableExecutorRegistration instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.executorHandleIsSet()) {
      setExecutorHandle(instance.getExecutorHandle());
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IExecutorRegistration#getExecutorHandle() executorHandle} attribute.
   * @param executorHandle The value for executorHandle
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableExecutorRegistration setExecutorHandle(IExecutorHandle executorHandle) {
    this.executorHandle = Objects.requireNonNull(executorHandle, "executorHandle");
    initBits &= ~INIT_BIT_EXECUTOR_HANDLE;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IExecutorRegistration#getExecutorHandle() executorHandle} is set.
   * @return {@code true} if set
   */
  public final boolean executorHandleIsSet() {
    return (initBits & INIT_BIT_EXECUTOR_HANDLE) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableExecutorRegistration unsetExecutorHandle() {
    initBits |= INIT_BIT_EXECUTOR_HANDLE;
    executorHandle = null;
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
    if (!executorHandleIsSet()) attributes.add("executorHandle");
    return "ExecutorRegistration is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link ExecutorRegistration ExecutorRegistration}.
   * @return An immutable instance of ExecutorRegistration
   */
  public final ExecutorRegistration toImmutable() {
    checkRequiredAttributes();
    return ExecutorRegistration.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableExecutorRegistration} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableExecutorRegistration)) return false;
    MutableExecutorRegistration other = (MutableExecutorRegistration) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableExecutorRegistration another) {
    return executorHandle.equals(another.executorHandle);
  }

  /**
   * Computes a hash code from attributes: {@code executorHandle}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + executorHandle.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IExecutorRegistration}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableExecutorRegistration")
        .add("executorHandle", executorHandleIsSet() ? getExecutorHandle() : "?")
        .toString();
  }
}
