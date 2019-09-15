package de.upb.sede.exec.aux;

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
 * Immutable implementation of {@link IJavaParameterizationAux}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code JavaParameterizationAux.builder()}.
 */
@Generated(from = "IJavaParameterizationAux", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class JavaParameterizationAux implements IJavaParameterizationAux {

  private JavaParameterizationAux(JavaParameterizationAux.Builder builder) {
  }

  /**
   * This instance is equal to all instances of {@code JavaParameterizationAux} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof JavaParameterizationAux
        && equalTo((JavaParameterizationAux) another);
  }

  @SuppressWarnings("MethodCanBeStatic")
  private boolean equalTo(JavaParameterizationAux another) {
    return true;
  }

  /**
   * Returns a constant hash code value.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    return 2085995965;
  }

  /**
   * Prints the immutable value {@code JavaParameterizationAux}.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "JavaParameterizationAux{}";
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IJavaParameterizationAux", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IJavaParameterizationAux {
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static JavaParameterizationAux fromJson(Json json) {
    JavaParameterizationAux.Builder builder = JavaParameterizationAux.builder();
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IJavaParameterizationAux} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable JavaParameterizationAux instance
   */
  public static JavaParameterizationAux copyOf(IJavaParameterizationAux instance) {
    if (instance instanceof JavaParameterizationAux) {
      return (JavaParameterizationAux) instance;
    }
    return JavaParameterizationAux.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link JavaParameterizationAux JavaParameterizationAux}.
   * <pre>
   * JavaParameterizationAux.builder()
   *    .build();
   * </pre>
   * @return A new JavaParameterizationAux builder
   */
  public static JavaParameterizationAux.Builder builder() {
    return new JavaParameterizationAux.Builder();
  }

  /**
   * Builds instances of type {@link JavaParameterizationAux JavaParameterizationAux}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IJavaParameterizationAux", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableJavaParameterizationAux} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableJavaParameterizationAux instance) {
      Objects.requireNonNull(instance, "instance");
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IJavaParameterizationAux} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IJavaParameterizationAux instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableJavaParameterizationAux) {
        return from((MutableJavaParameterizationAux) instance);
      }
      return this;
    }

    /**
     * Builds a new {@link JavaParameterizationAux JavaParameterizationAux}.
     * @return An immutable instance of JavaParameterizationAux
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public JavaParameterizationAux build() {
      return new JavaParameterizationAux(this);
    }
  }
}
