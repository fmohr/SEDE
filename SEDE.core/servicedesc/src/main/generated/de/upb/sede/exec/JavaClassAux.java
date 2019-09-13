package de.upb.sede.exec;

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
 * Immutable implementation of {@link IJavaClassAux}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code JavaClassAux.builder()}.
 */
@Generated(from = "IJavaClassAux", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class JavaClassAux implements IJavaClassAux {

  private JavaClassAux(JavaClassAux.Builder builder) {
  }

  /**
   * This instance is equal to all instances of {@code JavaClassAux} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof JavaClassAux
        && equalTo((JavaClassAux) another);
  }

  @SuppressWarnings("MethodCanBeStatic")
  private boolean equalTo(JavaClassAux another) {
    return true;
  }

  /**
   * Returns a constant hash code value.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    return -1318807904;
  }

  /**
   * Prints the immutable value {@code JavaClassAux}.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "JavaClassAux{}";
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IJavaClassAux", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IJavaClassAux {
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static JavaClassAux fromJson(Json json) {
    JavaClassAux.Builder builder = JavaClassAux.builder();
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IJavaClassAux} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable JavaClassAux instance
   */
  public static JavaClassAux copyOf(IJavaClassAux instance) {
    if (instance instanceof JavaClassAux) {
      return (JavaClassAux) instance;
    }
    return JavaClassAux.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link JavaClassAux JavaClassAux}.
   * <pre>
   * JavaClassAux.builder()
   *    .build();
   * </pre>
   * @return A new JavaClassAux builder
   */
  public static JavaClassAux.Builder builder() {
    return new JavaClassAux.Builder();
  }

  /**
   * Builds instances of type {@link JavaClassAux JavaClassAux}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IJavaClassAux", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableJavaClassAux} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableJavaClassAux instance) {
      Objects.requireNonNull(instance, "instance");
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IJavaClassAux} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IJavaClassAux instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableJavaClassAux) {
        return from((MutableJavaClassAux) instance);
      }
      return this;
    }

    /**
     * Builds a new {@link JavaClassAux JavaClassAux}.
     * @return An immutable instance of JavaClassAux
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public JavaClassAux build() {
      return new JavaClassAux(this);
    }
  }
}
