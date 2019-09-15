package de.upb.sede.exec.aux;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IPythonMethodAux IPythonMethodAux} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutablePythonMethodAux is not thread-safe</em>
 * @see PythonMethodAux
 */
@Generated(from = "IPythonMethodAux", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IPythonMethodAux"})
@NotThreadSafe
public final class MutablePythonMethodAux implements IPythonMethodAux {

  private MutablePythonMethodAux() {}

  /**
   * Construct a modifiable instance of {@code IPythonMethodAux}.
   * @return A new modifiable instance
   */
  public static MutablePythonMethodAux create() {
    return new MutablePythonMethodAux();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutablePythonMethodAux clear() {
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IPythonMethodAux} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutablePythonMethodAux from(IPythonMethodAux instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutablePythonMethodAux) {
      from((MutablePythonMethodAux) instance);
      return this;
    }
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IPythonMethodAux} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutablePythonMethodAux from(MutablePythonMethodAux instance) {
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
   * Converts to {@link PythonMethodAux PythonMethodAux}.
   * @return An immutable instance of PythonMethodAux
   */
  public final PythonMethodAux toImmutable() {
    return PythonMethodAux.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutablePythonMethodAux} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutablePythonMethodAux)) return false;
    MutablePythonMethodAux other = (MutablePythonMethodAux) another;
    return equalTo(other);
  }

  @SuppressWarnings("MethodCanBeStatic")
  private boolean equalTo(MutablePythonMethodAux another) {
    return true;
  }

  /**
   * Returns a constant hash code value.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    return -371994947;
  }

  /**
   * Generates a string representation of this {@code IPythonMethodAux}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return "MutablePythonMethodAux{}";
  }
}
