package de.upb.sede.exec.aux;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IPythonClassAux IPythonClassAux} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutablePythonClassAux is not thread-safe</em>
 * @see PythonClassAux
 */
@Generated(from = "IPythonClassAux", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IPythonClassAux"})
@NotThreadSafe
public final class MutablePythonClassAux implements IPythonClassAux {

  private MutablePythonClassAux() {}

  /**
   * Construct a modifiable instance of {@code IPythonClassAux}.
   * @return A new modifiable instance
   */
  public static MutablePythonClassAux create() {
    return new MutablePythonClassAux();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutablePythonClassAux clear() {
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IPythonClassAux} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutablePythonClassAux from(IPythonClassAux instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutablePythonClassAux) {
      from((MutablePythonClassAux) instance);
      return this;
    }
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IPythonClassAux} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutablePythonClassAux from(MutablePythonClassAux instance) {
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
   * Converts to {@link PythonClassAux PythonClassAux}.
   * @return An immutable instance of PythonClassAux
   */
  public final PythonClassAux toImmutable() {
    return PythonClassAux.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutablePythonClassAux} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutablePythonClassAux)) return false;
    MutablePythonClassAux other = (MutablePythonClassAux) another;
    return equalTo(other);
  }

  @SuppressWarnings("MethodCanBeStatic")
  private boolean equalTo(MutablePythonClassAux another) {
    return true;
  }

  /**
   * Returns a constant hash code value.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    return 985069168;
  }

  /**
   * Generates a string representation of this {@code IPythonClassAux}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return "MutablePythonClassAux{}";
  }
}
