package de.upb.sede.exec.auxiliary;

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
 * A modifiable implementation of the {@link IJavaDispatchAux IJavaDispatchAux} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableJavaDispatchAux is not thread-safe</em>
 * @see JavaDispatchAux
 */
@Generated(from = "IJavaDispatchAux", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IJavaDispatchAux"})
@NotThreadSafe
public final class MutableJavaDispatchAux implements IJavaDispatchAux {
  private static final long OPT_BIT_STATIC_INVOCATION = 0x1L;
  private static final long OPT_BIT_REDIRECT_ARG = 0x2L;
  private long optBits;

  private boolean staticInvocation;
  private int redirectArg;
  private @Nullable String methodName;
  private @Nullable String className;
  private @Nullable String metaclass;

  private MutableJavaDispatchAux() {}

  /**
   * Construct a modifiable instance of {@code IJavaDispatchAux}.
   * @return A new modifiable instance
   */
  public static MutableJavaDispatchAux create() {
    return new MutableJavaDispatchAux();
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code staticInvocation} attribute
   */
  @JsonProperty("staticInvocation")
  @Override
  public final boolean staticInvocation() {
    return staticInvocationIsSet()
        ? staticInvocation
        : IJavaDispatchAux.super.staticInvocation();
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code redirectArg} attribute
   */
  @JsonProperty("redirectArg")
  @Override
  public final int redirectArg() {
    return redirectArgIsSet()
        ? redirectArg
        : IJavaDispatchAux.super.redirectArg();
  }

  /**
   * @return value of {@code methodName} attribute, may be {@code null}
   */
  @JsonProperty("methodName")
  @Override
  public final @Nullable String methodName() {
    return methodName;
  }

  /**
   * @return value of {@code className} attribute, may be {@code null}
   */
  @JsonProperty("className")
  @Override
  public final @Nullable String className() {
    return className;
  }

  /**
   * @return value of {@code metaclass} attribute, may be {@code null}
   */
  @JsonProperty("metaclass")
  @Override
  public final @Nullable String getMetaclass() {
    return metaclass;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableJavaDispatchAux clear() {
    optBits = 0;
    staticInvocation = false;
    redirectArg = 0;
    methodName = null;
    className = null;
    metaclass = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IJavaDispatchAux} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableJavaDispatchAux from(IJavaDispatchAux instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableJavaDispatchAux) {
      from((MutableJavaDispatchAux) instance);
      return this;
    }
    setStaticInvocation(instance.staticInvocation());
    setRedirectArg(instance.redirectArg());
    @Nullable String methodNameValue = instance.methodName();
    if (methodNameValue != null) {
      setMethodName(methodNameValue);
    }
    @Nullable String classNameValue = instance.className();
    if (classNameValue != null) {
      setClassName(classNameValue);
    }
    @Nullable String metaclassValue = instance.getMetaclass();
    if (metaclassValue != null) {
      setMetaclass(metaclassValue);
    }
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IJavaDispatchAux} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableJavaDispatchAux from(MutableJavaDispatchAux instance) {
    Objects.requireNonNull(instance, "instance");
    setStaticInvocation(instance.staticInvocation());
    setRedirectArg(instance.redirectArg());
    @Nullable String methodNameValue = instance.methodName();
    if (methodNameValue != null) {
      setMethodName(methodNameValue);
    }
    @Nullable String classNameValue = instance.className();
    if (classNameValue != null) {
      setClassName(classNameValue);
    }
    @Nullable String metaclassValue = instance.getMetaclass();
    if (metaclassValue != null) {
      setMetaclass(metaclassValue);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IJavaDispatchAux#staticInvocation() staticInvocation} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IJavaDispatchAux#staticInvocation() staticInvocation}.</em>
   * @param staticInvocation The value for staticInvocation
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableJavaDispatchAux setStaticInvocation(boolean staticInvocation) {
    this.staticInvocation = staticInvocation;
    optBits |= OPT_BIT_STATIC_INVOCATION;
    return this;
  }

  /**
   * Assigns a value to the {@link IJavaDispatchAux#redirectArg() redirectArg} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IJavaDispatchAux#redirectArg() redirectArg}.</em>
   * @param redirectArg The value for redirectArg
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableJavaDispatchAux setRedirectArg(int redirectArg) {
    this.redirectArg = redirectArg;
    optBits |= OPT_BIT_REDIRECT_ARG;
    return this;
  }

  /**
   * Assigns a value to the {@link IJavaDispatchAux#methodName() methodName} attribute.
   * @param methodName The value for methodName, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableJavaDispatchAux setMethodName(@Nullable String methodName) {
    this.methodName = methodName;
    return this;
  }

  /**
   * Assigns a value to the {@link IJavaDispatchAux#className() className} attribute.
   * @param className The value for className, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableJavaDispatchAux setClassName(@Nullable String className) {
    this.className = className;
    return this;
  }

  /**
   * Assigns a value to the {@link IJavaDispatchAux#getMetaclass() metaclass} attribute.
   * @param metaclass The value for metaclass, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableJavaDispatchAux setMetaclass(@Nullable String metaclass) {
    this.metaclass = metaclass;
    return this;
  }

  /**
   * Returns {@code true} if the default attribute {@link IJavaDispatchAux#staticInvocation() staticInvocation} is set.
   * @return {@code true} if set
   */
  public final boolean staticInvocationIsSet() {
    return (optBits & OPT_BIT_STATIC_INVOCATION) != 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IJavaDispatchAux#redirectArg() redirectArg} is set.
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
  public final MutableJavaDispatchAux unsetStaticInvocation() {
    optBits |= 0;
    staticInvocation = false;
    return this;
  }
  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableJavaDispatchAux unsetRedirectArg() {
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
   * Converts to {@link JavaDispatchAux JavaDispatchAux}.
   * @return An immutable instance of JavaDispatchAux
   */
  public final JavaDispatchAux toImmutable() {
    return JavaDispatchAux.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableJavaDispatchAux} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableJavaDispatchAux)) return false;
    MutableJavaDispatchAux other = (MutableJavaDispatchAux) another;
    return equalTo(other);
  }

  private boolean equalTo(MutableJavaDispatchAux another) {
    boolean staticInvocation = staticInvocation();
    int redirectArg = redirectArg();
    return staticInvocation == another.staticInvocation()
        && redirectArg == another.redirectArg()
        && Objects.equals(methodName, another.methodName)
        && Objects.equals(className, another.className)
        && Objects.equals(metaclass, another.metaclass);
  }

  /**
   * Computes a hash code from attributes: {@code staticInvocation}, {@code redirectArg}, {@code methodName}, {@code className}, {@code metaclass}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    boolean staticInvocation = staticInvocation();
    h += (h << 5) + Booleans.hashCode(staticInvocation);
    int redirectArg = redirectArg();
    h += (h << 5) + redirectArg;
    h += (h << 5) + Objects.hashCode(methodName);
    h += (h << 5) + Objects.hashCode(className);
    h += (h << 5) + Objects.hashCode(metaclass);
    return h;
  }

  /**
   * Generates a string representation of this {@code IJavaDispatchAux}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableJavaDispatchAux")
        .add("staticInvocation", staticInvocation())
        .add("redirectArg", redirectArg())
        .add("methodName", methodName())
        .add("className", className())
        .add("metaclass", getMetaclass())
        .toString();
  }
}
