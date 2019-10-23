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
 * Immutable implementation of {@link IPythonClassAux}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code PythonClassAux.builder()}.
 */
@Generated(from = "IPythonClassAux", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class PythonClassAux implements IPythonClassAux {

  private PythonClassAux(PythonClassAux.Builder builder) {
  }

  /**
   * This instance is equal to all instances of {@code PythonClassAux} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof PythonClassAux
        && equalTo((PythonClassAux) another);
  }

  @SuppressWarnings("MethodCanBeStatic")
  private boolean equalTo(PythonClassAux another) {
    return true;
  }

  /**
   * Returns a constant hash code value.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    return 1533419762;
  }

  /**
   * Prints the immutable value {@code PythonClassAux}.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "PythonClassAux{}";
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IPythonClassAux", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IPythonClassAux {
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static PythonClassAux fromJson(Json json) {
    PythonClassAux.Builder builder = PythonClassAux.builder();
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IPythonClassAux} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable PythonClassAux instance
   */
  public static PythonClassAux copyOf(IPythonClassAux instance) {
    if (instance instanceof PythonClassAux) {
      return (PythonClassAux) instance;
    }
    return PythonClassAux.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link PythonClassAux PythonClassAux}.
   * <pre>
   * PythonClassAux.builder()
   *    .build();
   * </pre>
   * @return A new PythonClassAux builder
   */
  public static PythonClassAux.Builder builder() {
    return new PythonClassAux.Builder();
  }

  /**
   * Builds instances of type {@link PythonClassAux PythonClassAux}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IPythonClassAux", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutablePythonClassAux} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutablePythonClassAux instance) {
      Objects.requireNonNull(instance, "instance");
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IPythonClassAux} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IPythonClassAux instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutablePythonClassAux) {
        return from((MutablePythonClassAux) instance);
      }
      return this;
    }

    /**
     * Builds a new {@link PythonClassAux PythonClassAux}.
     * @return An immutable instance of PythonClassAux
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public PythonClassAux build() {
      return new PythonClassAux(this);
    }
  }
}
