package de.upb.sede.exec;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IJavaClassAux IJavaClassAux} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableJavaClassAux is not thread-safe</em>
 * @see JavaClassAux
 */
@Generated(from = "IJavaClassAux", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IJavaClassAux"})
@NotThreadSafe
public final class MutableJavaClassAux implements IJavaClassAux {

  private MutableJavaClassAux() {}

  /**
   * Construct a modifiable instance of {@code IJavaClassAux}.
   * @return A new modifiable instance
   */
  public static MutableJavaClassAux create() {
    return new MutableJavaClassAux();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableJavaClassAux clear() {
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IJavaClassAux} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableJavaClassAux from(IJavaClassAux instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableJavaClassAux) {
      from((MutableJavaClassAux) instance);
      return this;
    }
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IJavaClassAux} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableJavaClassAux from(MutableJavaClassAux instance) {
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
   * Converts to {@link JavaClassAux JavaClassAux}.
   * @return An immutable instance of JavaClassAux
   */
  public final JavaClassAux toImmutable() {
    return JavaClassAux.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableJavaClassAux} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableJavaClassAux)) return false;
    MutableJavaClassAux other = (MutableJavaClassAux) another;
    return equalTo(other);
  }

  @SuppressWarnings("MethodCanBeStatic")
  private boolean equalTo(MutableJavaClassAux another) {
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
   * Generates a string representation of this {@code IJavaClassAux}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return "MutableJavaClassAux{}";
  }
}