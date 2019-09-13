package de.upb.sede.types;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IJavaTypeAux IJavaTypeAux} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableJavaTypeAux is not thread-safe</em>
 * @see JavaTypeAux
 */
@Generated(from = "IJavaTypeAux", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IJavaTypeAux"})
@NotThreadSafe
public final class MutableJavaTypeAux implements IJavaTypeAux {

  private MutableJavaTypeAux() {}

  /**
   * Construct a modifiable instance of {@code IJavaTypeAux}.
   * @return A new modifiable instance
   */
  public static MutableJavaTypeAux create() {
    return new MutableJavaTypeAux();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableJavaTypeAux clear() {
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IJavaTypeAux} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableJavaTypeAux from(IJavaTypeAux instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableJavaTypeAux) {
      from((MutableJavaTypeAux) instance);
      return this;
    }
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IJavaTypeAux} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableJavaTypeAux from(MutableJavaTypeAux instance) {
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
   * Converts to {@link JavaTypeAux JavaTypeAux}.
   * @return An immutable instance of JavaTypeAux
   */
  public final JavaTypeAux toImmutable() {
    return JavaTypeAux.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableJavaTypeAux} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableJavaTypeAux)) return false;
    MutableJavaTypeAux other = (MutableJavaTypeAux) another;
    return equalTo(other);
  }

  @SuppressWarnings("MethodCanBeStatic")
  private boolean equalTo(MutableJavaTypeAux another) {
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
   * Generates a string representation of this {@code IJavaTypeAux}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return "MutableJavaTypeAux{}";
  }
}
