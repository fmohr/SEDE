package de.upb.sede.exec.auxiliary;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link IPythonMethodAux}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code PythonMethodAux.builder()}.
 */
@Generated(from = "IPythonMethodAux", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class PythonMethodAux implements IPythonMethodAux {

  private PythonMethodAux(PythonMethodAux.Builder builder) {
  }

  /**
   * This instance is equal to all instances of {@code PythonMethodAux} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof PythonMethodAux
        && equalTo((PythonMethodAux) another);
  }

  @SuppressWarnings("MethodCanBeStatic")
  private boolean equalTo(PythonMethodAux another) {
    return true;
  }

  /**
   * Returns a constant hash code value.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    return -552995717;
  }

  /**
   * Prints the immutable value {@code PythonMethodAux}.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "PythonMethodAux{}";
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IPythonMethodAux", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IPythonMethodAux {
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static PythonMethodAux fromJson(Json json) {
    PythonMethodAux.Builder builder = PythonMethodAux.builder();
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IPythonMethodAux} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable PythonMethodAux instance
   */
  public static PythonMethodAux copyOf(IPythonMethodAux instance) {
    if (instance instanceof PythonMethodAux) {
      return (PythonMethodAux) instance;
    }
    return PythonMethodAux.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link PythonMethodAux PythonMethodAux}.
   * <pre>
   * PythonMethodAux.builder()
   *    .build();
   * </pre>
   * @return A new PythonMethodAux builder
   */
  public static PythonMethodAux.Builder builder() {
    return new PythonMethodAux.Builder();
  }

  /**
   * Builds instances of type {@link PythonMethodAux PythonMethodAux}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IPythonMethodAux", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutablePythonMethodAux} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutablePythonMethodAux instance) {
      Objects.requireNonNull(instance, "instance");
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IPythonMethodAux} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IPythonMethodAux instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutablePythonMethodAux) {
        return from((MutablePythonMethodAux) instance);
      }
      return this;
    }

    /**
     * Builds a new {@link PythonMethodAux PythonMethodAux}.
     * @return An immutable instance of PythonMethodAux
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public PythonMethodAux build() {
      return new PythonMethodAux(this);
    }
  }
}
