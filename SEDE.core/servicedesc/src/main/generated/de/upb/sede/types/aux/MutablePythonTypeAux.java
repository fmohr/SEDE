package de.upb.sede.types.aux;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IPythonTypeAux IPythonTypeAux} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutablePythonTypeAux is not thread-safe</em>
 * @see PythonTypeAux
 */
@Generated(from = "IPythonTypeAux", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IPythonTypeAux"})
@NotThreadSafe
public final class MutablePythonTypeAux implements IPythonTypeAux {

  private MutablePythonTypeAux() {}

  /**
   * Construct a modifiable instance of {@code IPythonTypeAux}.
   * @return A new modifiable instance
   */
  public static MutablePythonTypeAux create() {
    return new MutablePythonTypeAux();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutablePythonTypeAux clear() {
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IPythonTypeAux} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutablePythonTypeAux from(IPythonTypeAux instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutablePythonTypeAux) {
      from((MutablePythonTypeAux) instance);
      return this;
    }
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IPythonTypeAux} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutablePythonTypeAux from(MutablePythonTypeAux instance) {
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
   * Converts to {@link PythonTypeAux PythonTypeAux}.
   * @return An immutable instance of PythonTypeAux
   */
  public final PythonTypeAux toImmutable() {
    return PythonTypeAux.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutablePythonTypeAux} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutablePythonTypeAux)) return false;
    MutablePythonTypeAux other = (MutablePythonTypeAux) another;
    return equalTo(other);
  }

  @SuppressWarnings("MethodCanBeStatic")
  private boolean equalTo(MutablePythonTypeAux another) {
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
   * Generates a string representation of this {@code IPythonTypeAux}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return "MutablePythonTypeAux{}";
  }
}
