package de.upb.sede.types.aux;

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
 * Immutable implementation of {@link IPythonTypeAux}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code PythonTypeAux.builder()}.
 */
@Generated(from = "IPythonTypeAux", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class PythonTypeAux implements IPythonTypeAux {

  private PythonTypeAux(PythonTypeAux.Builder builder) {
  }

  /**
   * This instance is equal to all instances of {@code PythonTypeAux} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof PythonTypeAux
        && equalTo((PythonTypeAux) another);
  }

  @SuppressWarnings("MethodCanBeStatic")
  private boolean equalTo(PythonTypeAux another) {
    return true;
  }

  /**
   * Returns a constant hash code value.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    return 1504174474;
  }

  /**
   * Prints the immutable value {@code PythonTypeAux}.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "PythonTypeAux{}";
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IPythonTypeAux", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IPythonTypeAux {
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static PythonTypeAux fromJson(Json json) {
    PythonTypeAux.Builder builder = PythonTypeAux.builder();
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IPythonTypeAux} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable PythonTypeAux instance
   */
  public static PythonTypeAux copyOf(IPythonTypeAux instance) {
    if (instance instanceof PythonTypeAux) {
      return (PythonTypeAux) instance;
    }
    return PythonTypeAux.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link PythonTypeAux PythonTypeAux}.
   * <pre>
   * PythonTypeAux.builder()
   *    .build();
   * </pre>
   * @return A new PythonTypeAux builder
   */
  public static PythonTypeAux.Builder builder() {
    return new PythonTypeAux.Builder();
  }

  /**
   * Builds instances of type {@link PythonTypeAux PythonTypeAux}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IPythonTypeAux", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutablePythonTypeAux} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutablePythonTypeAux instance) {
      Objects.requireNonNull(instance, "instance");
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IPythonTypeAux} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IPythonTypeAux instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutablePythonTypeAux) {
        return from((MutablePythonTypeAux) instance);
      }
      return this;
    }

    /**
     * Builds a new {@link PythonTypeAux PythonTypeAux}.
     * @return An immutable instance of PythonTypeAux
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public PythonTypeAux build() {
      return new PythonTypeAux(this);
    }
  }
}
