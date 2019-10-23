package de.upb.sede.beta;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.exec.IExecutorHandle;
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
 * Immutable implementation of {@link IExecutorRegistration}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ExecutorRegistration.builder()}.
 */
@Generated(from = "IExecutorRegistration", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ExecutorRegistration implements IExecutorRegistration {
  private final IExecutorHandle executorHandle;

  private ExecutorRegistration(IExecutorHandle executorHandle) {
    this.executorHandle = executorHandle;
  }

  /**
   * @return The value of the {@code executorHandle} attribute
   */
  @JsonProperty("executorHandle")
  @Override
  public IExecutorHandle getExecutorHandle() {
    return executorHandle;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IExecutorRegistration#getExecutorHandle() executorHandle} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for executorHandle
   * @return A modified copy of the {@code this} object
   */
  public final ExecutorRegistration withExecutorHandle(IExecutorHandle value) {
    if (this.executorHandle == value) return this;
    IExecutorHandle newValue = Objects.requireNonNull(value, "executorHandle");
    return new ExecutorRegistration(newValue);
  }

  /**
   * This instance is equal to all instances of {@code ExecutorRegistration} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ExecutorRegistration
        && equalTo((ExecutorRegistration) another);
  }

  private boolean equalTo(ExecutorRegistration another) {
    return executorHandle.equals(another.executorHandle);
  }

  /**
   * Computes a hash code from attributes: {@code executorHandle}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + executorHandle.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ExecutorRegistration} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ExecutorRegistration")
        .omitNullValues()
        .add("executorHandle", executorHandle)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IExecutorRegistration", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IExecutorRegistration {
    @Nullable IExecutorHandle executorHandle;
    @JsonProperty("executorHandle")
    public void setExecutorHandle(IExecutorHandle executorHandle) {
      this.executorHandle = executorHandle;
    }
    @Override
    public IExecutorHandle getExecutorHandle() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static ExecutorRegistration fromJson(Json json) {
    ExecutorRegistration.Builder builder = ExecutorRegistration.builder();
    if (json.executorHandle != null) {
      builder.executorHandle(json.executorHandle);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IExecutorRegistration} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ExecutorRegistration instance
   */
  public static ExecutorRegistration copyOf(IExecutorRegistration instance) {
    if (instance instanceof ExecutorRegistration) {
      return (ExecutorRegistration) instance;
    }
    return ExecutorRegistration.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ExecutorRegistration ExecutorRegistration}.
   * <pre>
   * ExecutorRegistration.builder()
   *    .executorHandle(de.upb.sede.exec.IExecutorHandle) // required {@link IExecutorRegistration#getExecutorHandle() executorHandle}
   *    .build();
   * </pre>
   * @return A new ExecutorRegistration builder
   */
  public static ExecutorRegistration.Builder builder() {
    return new ExecutorRegistration.Builder();
  }

  /**
   * Builds instances of type {@link ExecutorRegistration ExecutorRegistration}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IExecutorRegistration", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_EXECUTOR_HANDLE = 0x1L;
    private long initBits = 0x1L;

    private @Nullable IExecutorHandle executorHandle;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableExecutorRegistration} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableExecutorRegistration instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.executorHandleIsSet()) {
        executorHandle(instance.getExecutorHandle());
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IExecutorRegistration} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IExecutorRegistration instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableExecutorRegistration) {
        return from((MutableExecutorRegistration) instance);
      }
      executorHandle(instance.getExecutorHandle());
      return this;
    }

    /**
     * Initializes the value for the {@link IExecutorRegistration#getExecutorHandle() executorHandle} attribute.
     * @param executorHandle The value for executorHandle 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("executorHandle")
    public final Builder executorHandle(IExecutorHandle executorHandle) {
      this.executorHandle = Objects.requireNonNull(executorHandle, "executorHandle");
      initBits &= ~INIT_BIT_EXECUTOR_HANDLE;
      return this;
    }

    /**
     * Builds a new {@link ExecutorRegistration ExecutorRegistration}.
     * @return An immutable instance of ExecutorRegistration
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ExecutorRegistration build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ExecutorRegistration(executorHandle);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_EXECUTOR_HANDLE) != 0) attributes.add("executorHandle");
      return "Cannot build ExecutorRegistration, some of required attributes are not set " + attributes;
    }
  }
}
