package de.upb.sede.exec;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IJavaParameterizationAux IJavaParameterizationAux} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableJavaParameterizationAux is not thread-safe</em>
 * @see JavaParameterizationAux
 */
@Generated(from = "IJavaParameterizationAux", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IJavaParameterizationAux"})
@NotThreadSafe
public final class MutableJavaParameterizationAux implements IJavaParameterizationAux {

  private MutableJavaParameterizationAux() {}

  /**
   * Construct a modifiable instance of {@code IJavaParameterizationAux}.
   * @return A new modifiable instance
   */
  public static MutableJavaParameterizationAux create() {
    return new MutableJavaParameterizationAux();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableJavaParameterizationAux clear() {
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IJavaParameterizationAux} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableJavaParameterizationAux from(IJavaParameterizationAux instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableJavaParameterizationAux) {
      from((MutableJavaParameterizationAux) instance);
      return this;
    }
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IJavaParameterizationAux} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableJavaParameterizationAux from(MutableJavaParameterizationAux instance) {
    Objects.requireNonNull(instance, "instance");
    return this;
  }


  /**
   * Returns {@code true} if all required attributes are set, indicating that the object is initialized.
   * @return {@code true} if set
   */
  public final boolean isInitialized() {
    return true;
  }

  /**
   * Converts to {@link JavaParameterizationAux JavaParameterizationAux}.
   * @return An immutable instance of JavaParameterizationAux
   */
  public final JavaParameterizationAux toImmutable() {
    return JavaParameterizationAux.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableJavaParameterizationAux} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableJavaParameterizationAux)) return false;
    MutableJavaParameterizationAux other = (MutableJavaParameterizationAux) another;
    return equalTo(other);
  }

  @SuppressWarnings("MethodCanBeStatic")
  private boolean equalTo(MutableJavaParameterizationAux another) {
    return true;
  }

  /**
   * Returns a constant hash code value.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    return 469549267;
  }

  /**
   * Generates a string representation of this {@code IJavaParameterizationAux}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return "MutableJavaParameterizationAux{}";
  }
}
