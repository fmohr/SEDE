package de.upb.sede.types;

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
 * Immutable implementation of {@link IJavaTypeAux}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code JavaTypeAux.builder()}.
 */
@Generated(from = "IJavaTypeAux", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class JavaTypeAux implements IJavaTypeAux {

  private JavaTypeAux(JavaTypeAux.Builder builder) {
  }

  /**
   * This instance is equal to all instances of {@code JavaTypeAux} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof JavaTypeAux
        && equalTo((JavaTypeAux) another);
  }

  @SuppressWarnings("MethodCanBeStatic")
  private boolean equalTo(JavaTypeAux another) {
    return true;
  }

  /**
   * Returns a constant hash code value.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    return -1149880358;
  }

  /**
   * Prints the immutable value {@code JavaTypeAux}.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "JavaTypeAux{}";
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IJavaTypeAux", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IJavaTypeAux {
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static JavaTypeAux fromJson(Json json) {
    JavaTypeAux.Builder builder = JavaTypeAux.builder();
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IJavaTypeAux} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable JavaTypeAux instance
   */
  public static JavaTypeAux copyOf(IJavaTypeAux instance) {
    if (instance instanceof JavaTypeAux) {
      return (JavaTypeAux) instance;
    }
    return JavaTypeAux.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link JavaTypeAux JavaTypeAux}.
   * <pre>
   * JavaTypeAux.builder()
   *    .build();
   * </pre>
   * @return A new JavaTypeAux builder
   */
  public static JavaTypeAux.Builder builder() {
    return new JavaTypeAux.Builder();
  }

  /**
   * Builds instances of type {@link JavaTypeAux JavaTypeAux}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IJavaTypeAux", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableJavaTypeAux} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableJavaTypeAux instance) {
      Objects.requireNonNull(instance, "instance");
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IJavaTypeAux} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IJavaTypeAux instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableJavaTypeAux) {
        return from((MutableJavaTypeAux) instance);
      }
      return this;
    }

    /**
     * Builds a new {@link JavaTypeAux JavaTypeAux}.
     * @return An immutable instance of JavaTypeAux
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public JavaTypeAux build() {
      return new JavaTypeAux(this);
    }
  }
}
