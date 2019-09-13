package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IServiceParameterDesc IServiceParameterDesc} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableServiceParameterDesc is not thread-safe</em>
 * @see ServiceParameterDesc
 */
@Generated(from = "IServiceParameterDesc", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IServiceParameterDesc"})
@NotThreadSafe
public final class MutableServiceParameterDesc implements IServiceParameterDesc {
  private @Nullable IJavaParameterizationAux javaParameterizationAuxiliaries;

  private MutableServiceParameterDesc() {}

  /**
   * Construct a modifiable instance of {@code IServiceParameterDesc}.
   * @return A new modifiable instance
   */
  public static MutableServiceParameterDesc create() {
    return new MutableServiceParameterDesc();
  }

  /**
   * @return value of {@code javaParameterizationAuxiliaries} attribute, may be {@code null}
   */
  @JsonProperty("javaParameterizationAuxiliaries")
  @Override
  public final @Nullable IJavaParameterizationAux getJavaParameterizationAuxiliaries() {
    return javaParameterizationAuxiliaries;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceParameterDesc clear() {
    javaParameterizationAuxiliaries = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IServiceParameterDesc} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableServiceParameterDesc from(IServiceParameterDesc instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableServiceParameterDesc) {
      from((MutableServiceParameterDesc) instance);
      return this;
    }
    @Nullable IJavaParameterizationAux javaParameterizationAuxiliariesValue = instance.getJavaParameterizationAuxiliaries();
    if (javaParameterizationAuxiliariesValue != null) {
      setJavaParameterizationAuxiliaries(javaParameterizationAuxiliariesValue);
    }
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IServiceParameterDesc} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableServiceParameterDesc from(MutableServiceParameterDesc instance) {
    Objects.requireNonNull(instance, "instance");
    @Nullable IJavaParameterizationAux javaParameterizationAuxiliariesValue = instance.getJavaParameterizationAuxiliaries();
    if (javaParameterizationAuxiliariesValue != null) {
      setJavaParameterizationAuxiliaries(javaParameterizationAuxiliariesValue);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IServiceParameterDesc#getJavaParameterizationAuxiliaries() javaParameterizationAuxiliaries} attribute.
   * @param javaParameterizationAuxiliaries The value for javaParameterizationAuxiliaries, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceParameterDesc setJavaParameterizationAuxiliaries(@Nullable IJavaParameterizationAux javaParameterizationAuxiliaries) {
    this.javaParameterizationAuxiliaries = javaParameterizationAuxiliaries;
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
   * Converts to {@link ServiceParameterDesc ServiceParameterDesc}.
   * @return An immutable instance of ServiceParameterDesc
   */
  public final ServiceParameterDesc toImmutable() {
    return ServiceParameterDesc.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableServiceParameterDesc} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableServiceParameterDesc)) return false;
    MutableServiceParameterDesc other = (MutableServiceParameterDesc) another;
    return equalTo(other);
  }

  private boolean equalTo(MutableServiceParameterDesc another) {
    return Objects.equals(javaParameterizationAuxiliaries, another.javaParameterizationAuxiliaries);
  }

  /**
   * Computes a hash code from attributes: {@code javaParameterizationAuxiliaries}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Objects.hashCode(javaParameterizationAuxiliaries);
    return h;
  }

  /**
   * Generates a string representation of this {@code IServiceParameterDesc}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableServiceParameterDesc")
        .add("javaParameterizationAuxiliaries", getJavaParameterizationAuxiliaries())
        .toString();
  }
}
