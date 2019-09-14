package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.primitives.Booleans;
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
  private static final long OPT_BIT_STATIC_INVOCATION = 0x1L;
  private static final long OPT_BIT_REDIRECT_ARG = 0x2L;
  private long optBits;

  private boolean staticInvocation;
  private int redirectArg;

  private MutableJavaMethodAux() {}

  /**
   * Construct a modifiable instance of {@code IJavaMethodAux}.
   * @return A new modifiable instance
   */
  public static MutableJavaMethodAux create() {
    return new MutableJavaMethodAux();
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code staticInvocation} attribute
   */
  @JsonProperty("staticInvocation")
  @Override
  public final boolean staticInvocation() {
    return staticInvocationIsSet()
        ? staticInvocation
        : IJavaMethodAux.super.staticInvocation();
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code redirectArg} attribute
   */
  @JsonProperty("redirectArg")
  @Override
  public final int redirectArg() {
    return redirectArgIsSet()
        ? redirectArg
        : IJavaMethodAux.super.redirectArg();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableJavaMethodAux clear() {
    optBits = 0;
    staticInvocation = false;
    redirectArg = 0;
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
    setStaticInvocation(instance.staticInvocation());
    setRedirectArg(instance.redirectArg());
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
    setStaticInvocation(instance.staticInvocation());
    setRedirectArg(instance.redirectArg());
    return this;
  }

  /**
   * Assigns a value to the {@link IJavaMethodAux#staticInvocation() staticInvocation} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IJavaMethodAux#staticInvocation() staticInvocation}.</em>
   * @param staticInvocation The value for staticInvocation
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableJavaMethodAux setStaticInvocation(boolean staticInvocation) {
    this.staticInvocation = staticInvocation;
    optBits |= OPT_BIT_STATIC_INVOCATION;
    return this;
  }

  /**
   * Assigns a value to the {@link IJavaMethodAux#redirectArg() redirectArg} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IJavaMethodAux#redirectArg() redirectArg}.</em>
   * @param redirectArg The value for redirectArg
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableJavaMethodAux setRedirectArg(int redirectArg) {
    this.redirectArg = redirectArg;
    optBits |= OPT_BIT_REDIRECT_ARG;
    return this;
  }

  /**
   * Returns {@code true} if the default attribute {@link IJavaMethodAux#staticInvocation() staticInvocation} is set.
   * @return {@code true} if set
   */
  public final boolean staticInvocationIsSet() {
    return (optBits & OPT_BIT_STATIC_INVOCATION) != 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IJavaMethodAux#redirectArg() redirectArg} is set.
   * @return {@code true} if set
   */
  public final boolean redirectArgIsSet() {
    return (optBits & OPT_BIT_REDIRECT_ARG) != 0;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableJavaMethodAux unsetStaticInvocation() {
    optBits |= 0;
    staticInvocation = false;
    return this;
  }
  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableJavaMethodAux unsetRedirectArg() {
    optBits |= 0;
    redirectArg = 0;
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

  private boolean equalTo(MutableJavaMethodAux another) {
    boolean staticInvocation = staticInvocation();
    int redirectArg = redirectArg();
    return staticInvocation == another.staticInvocation()
        && redirectArg == another.redirectArg();
  }

  /**
   * Computes a hash code from attributes: {@code staticInvocation}, {@code redirectArg}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    boolean staticInvocation = staticInvocation();
    h += (h << 5) + Booleans.hashCode(staticInvocation);
    int redirectArg = redirectArg();
    h += (h << 5) + redirectArg;
    return h;
  }

  /**
   * Generates a string representation of this {@code IJavaMethodAux}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableJavaMethodAux")
        .add("staticInvocation", staticInvocation())
        .add("redirectArg", redirectArg())
        .toString();
  }
}
