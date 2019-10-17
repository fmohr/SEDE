package de.upb.sede.types.auxiliary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.exec.auxiliary.IJavaDispatchAux;
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
  private @Nullable IJavaDispatchAux dataCastHandler;
  private @Nullable String className;

  private MutableJavaTypeAux() {}

  /**
   * Construct a modifiable instance of {@code IJavaTypeAux}.
   * @return A new modifiable instance
   */
  public static MutableJavaTypeAux create() {
    return new MutableJavaTypeAux();
  }

  /**
   * @return value of {@code dataCastHandler} attribute, may be {@code null}
   */
  @JsonProperty("dataCastHandler")
  @Override
  public final @Nullable IJavaDispatchAux getDataCastHandler() {
    return dataCastHandler;
  }

  /**
   * @return value of {@code className} attribute, may be {@code null}
   */
  @JsonProperty("className")
  @Override
  public final @Nullable String getClassName() {
    return className;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableJavaTypeAux clear() {
    dataCastHandler = null;
    className = null;
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
    @Nullable IJavaDispatchAux dataCastHandlerValue = instance.getDataCastHandler();
    if (dataCastHandlerValue != null) {
      setDataCastHandler(dataCastHandlerValue);
    }
    @Nullable String classNameValue = instance.getClassName();
    if (classNameValue != null) {
      setClassName(classNameValue);
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
    @Nullable IJavaDispatchAux dataCastHandlerValue = instance.getDataCastHandler();
    if (dataCastHandlerValue != null) {
      setDataCastHandler(dataCastHandlerValue);
    }
    @Nullable String classNameValue = instance.getClassName();
    if (classNameValue != null) {
      setClassName(classNameValue);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IJavaTypeAux#getDataCastHandler() dataCastHandler} attribute.
   * @param dataCastHandler The value for dataCastHandler, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableJavaTypeAux setDataCastHandler(@Nullable IJavaDispatchAux dataCastHandler) {
    this.dataCastHandler = dataCastHandler;
    return this;
  }

  /**
   * Assigns a value to the {@link IJavaTypeAux#getClassName() className} attribute.
   * @param className The value for className, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableJavaTypeAux setClassName(@Nullable String className) {
    this.className = className;
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

  private boolean equalTo(MutableJavaTypeAux another) {
    return Objects.equals(dataCastHandler, another.dataCastHandler)
        && Objects.equals(className, another.className);
  }

  /**
   * Computes a hash code from attributes: {@code dataCastHandler}, {@code className}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Objects.hashCode(dataCastHandler);
    h += (h << 5) + Objects.hashCode(className);
    return h;
  }

  /**
   * Generates a string representation of this {@code IJavaTypeAux}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableJavaTypeAux")
        .add("dataCastHandler", getDataCastHandler())
        .add("className", getClassName())
        .toString();
  }
}
