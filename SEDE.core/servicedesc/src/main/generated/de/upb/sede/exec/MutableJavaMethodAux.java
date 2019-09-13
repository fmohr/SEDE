package de.upb.sede.exec;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IJavaMethodAux IJavaMethodAux} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableJavaMethodAux is not thread-safe</em>
 * @see JavaMethodAux
 */
@Generated(from = "IJavaMethodAux", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IJavaMethodAux"})
@NotThreadSafe
public final class MutableJavaMethodAux implements IJavaMethodAux {

  private MutableJavaMethodAux() {}

  /**
   * Construct a modifiable instance of {@code IJavaMethodAux}.
   * @return A new modifiable instance
   */
  public static MutableJavaMethodAux create() {
    return new MutableJavaMethodAux();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableJavaMethodAux clear() {
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IJavaMethodAux} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableJavaMethodAux from(IJavaMethodAux instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableJavaMethodAux) {
      from((MutableJavaMethodAux) instance);
      return this;
    }
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IJavaMethodAux} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableJavaMethodAux from(MutableJavaMethodAux instance) {
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
   * Converts to {@link JavaMethodAux JavaMethodAux}.
   * @return An immutable instance of JavaMethodAux
   */
  public final JavaMethodAux toImmutable() {
    return JavaMethodAux.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableJavaMethodAux} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableJavaMethodAux)) return false;
    MutableJavaMethodAux other = (MutableJavaMethodAux) another;
    return equalTo(other);
  }

  @SuppressWarnings("MethodCanBeStatic")
  private boolean equalTo(MutableJavaMethodAux another) {
    return true;
  }

  /**
   * Returns a constant hash code value.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    return 1222259853;
  }

  /**
   * Generates a string representation of this {@code IJavaMethodAux}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return "MutableJavaMethodAux{}";
  }
}
