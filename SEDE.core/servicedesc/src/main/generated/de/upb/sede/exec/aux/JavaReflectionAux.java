package de.upb.sede.exec.aux;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link IJavaReflectionAux}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code JavaReflectionAux.builder()}.
 */
@Generated(from = "IJavaReflectionAux", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class JavaReflectionAux implements IJavaReflectionAux {
  private final boolean staticInvocation;
  private final int redirectArg;
  private final @Nullable String methodName;
  private final @Nullable String className;

  private JavaReflectionAux(JavaReflectionAux.Builder builder) {
    this.methodName = builder.methodName;
    this.className = builder.className;
    if (builder.staticInvocationIsSet()) {
      initShim.staticInvocation(builder.staticInvocation);
    }
    if (builder.redirectArgIsSet()) {
      initShim.redirectArg(builder.redirectArg);
    }
    this.staticInvocation = initShim.staticInvocation();
    this.redirectArg = initShim.redirectArg();
    this.initShim = null;
  }

  private JavaReflectionAux(
      boolean staticInvocation,
      int redirectArg,
      @Nullable String methodName,
      @Nullable String className) {
    this.staticInvocation = staticInvocation;
    this.redirectArg = redirectArg;
    this.methodName = methodName;
    this.className = className;
    this.initShim = null;
  }

  private static final byte STAGE_INITIALIZING = -1;
  private static final byte STAGE_UNINITIALIZED = 0;
  private static final byte STAGE_INITIALIZED = 1;
  @SuppressWarnings("Immutable")
  private transient volatile InitShim initShim = new InitShim();

  @Generated(from = "IJavaReflectionAux", generator = "Immutables")
  private final class InitShim {
    private byte staticInvocationBuildStage = STAGE_UNINITIALIZED;
    private boolean staticInvocation;

    boolean staticInvocation() {
      if (staticInvocationBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (staticInvocationBuildStage == STAGE_UNINITIALIZED) {
        staticInvocationBuildStage = STAGE_INITIALIZING;
        this.staticInvocation = staticInvocationInitialize();
        staticInvocationBuildStage = STAGE_INITIALIZED;
      }
      return this.staticInvocation;
    }

    void staticInvocation(boolean staticInvocation) {
      this.staticInvocation = staticInvocation;
      staticInvocationBuildStage = STAGE_INITIALIZED;
    }

    private byte redirectArgBuildStage = STAGE_UNINITIALIZED;
    private int redirectArg;

    int redirectArg() {
      if (redirectArgBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (redirectArgBuildStage == STAGE_UNINITIALIZED) {
        redirectArgBuildStage = STAGE_INITIALIZING;
        this.redirectArg = redirectArgInitialize();
        redirectArgBuildStage = STAGE_INITIALIZED;
      }
      return this.redirectArg;
    }

    void redirectArg(int redirectArg) {
      this.redirectArg = redirectArg;
      redirectArgBuildStage = STAGE_INITIALIZED;
    }

    private String formatInitCycleMessage() {
      List<String> attributes = new ArrayList<>();
      if (staticInvocationBuildStage == STAGE_INITIALIZING) attributes.add("staticInvocation");
      if (redirectArgBuildStage == STAGE_INITIALIZING) attributes.add("redirectArg");
      return "Cannot build JavaReflectionAux, attribute initializers form cycle " + attributes;
    }
  }

  private boolean staticInvocationInitialize() {
    return IJavaReflectionAux.super.staticInvocation();
  }

  private int redirectArgInitialize() {
    return IJavaReflectionAux.super.redirectArg();
  }

  /**
   * @return The value of the {@code staticInvocation} attribute
   */
  @JsonProperty("staticInvocation")
  @Override
  public boolean staticInvocation() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.staticInvocation()
        : this.staticInvocation;
  }

  /**
   * @return The value of the {@code redirectArg} attribute
   */
  @JsonProperty("redirectArg")
  @Override
  public int redirectArg() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.redirectArg()
        : this.redirectArg;
  }

  /**
   * @return The value of the {@code methodName} attribute
   */
  @JsonProperty("methodName")
  @Override
  public @Nullable String methodName() {
    return methodName;
  }

  /**
   * @return The value of the {@code className} attribute
   */
  @JsonProperty("className")
  @Override
  public @Nullable String className() {
    return className;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IJavaReflectionAux#staticInvocation() staticInvocation} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for staticInvocation
   * @return A modified copy of the {@code this} object
   */
  public final JavaReflectionAux withStaticInvocation(boolean value) {
    if (this.staticInvocation == value) return this;
    return new JavaReflectionAux(value, this.redirectArg, this.methodName, this.className);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IJavaReflectionAux#redirectArg() redirectArg} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for redirectArg
   * @return A modified copy of the {@code this} object
   */
  public final JavaReflectionAux withRedirectArg(int value) {
    if (this.redirectArg == value) return this;
    return new JavaReflectionAux(this.staticInvocation, value, this.methodName, this.className);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IJavaReflectionAux#methodName() methodName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for methodName (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final JavaReflectionAux withMethodName(@Nullable String value) {
    if (Objects.equals(this.methodName, value)) return this;
    return new JavaReflectionAux(this.staticInvocation, this.redirectArg, value, this.className);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IJavaReflectionAux#className() className} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for className (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final JavaReflectionAux withClassName(@Nullable String value) {
    if (Objects.equals(this.className, value)) return this;
    return new JavaReflectionAux(this.staticInvocation, this.redirectArg, this.methodName, value);
  }

  /**
   * This instance is equal to all instances of {@code JavaReflectionAux} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof JavaReflectionAux
        && equalTo((JavaReflectionAux) another);
  }

  private boolean equalTo(JavaReflectionAux another) {
    return staticInvocation == another.staticInvocation
        && redirectArg == another.redirectArg
        && Objects.equals(methodName, another.methodName)
        && Objects.equals(className, another.className);
  }

  /**
   * Computes a hash code from attributes: {@code staticInvocation}, {@code redirectArg}, {@code methodName}, {@code className}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Booleans.hashCode(staticInvocation);
    h += (h << 5) + redirectArg;
    h += (h << 5) + Objects.hashCode(methodName);
    h += (h << 5) + Objects.hashCode(className);
    return h;
  }

  /**
   * Prints the immutable value {@code JavaReflectionAux} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("JavaReflectionAux")
        .omitNullValues()
        .add("staticInvocation", staticInvocation)
        .add("redirectArg", redirectArg)
        .add("methodName", methodName)
        .add("className", className)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IJavaReflectionAux", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IJavaReflectionAux {
    boolean staticInvocation;
    boolean staticInvocationIsSet;
    int redirectArg;
    boolean redirectArgIsSet;
    @Nullable String methodName;
    @Nullable String className;
    @JsonProperty("staticInvocation")
    public void setStaticInvocation(boolean staticInvocation) {
      this.staticInvocation = staticInvocation;
      this.staticInvocationIsSet = true;
    }
    @JsonProperty("redirectArg")
    public void setRedirectArg(int redirectArg) {
      this.redirectArg = redirectArg;
      this.redirectArgIsSet = true;
    }
    @JsonProperty("methodName")
    public void setMethodName(@Nullable String methodName) {
      this.methodName = methodName;
    }
    @JsonProperty("className")
    public void setClassName(@Nullable String className) {
      this.className = className;
    }
    @Override
    public boolean staticInvocation() { throw new UnsupportedOperationException(); }
    @Override
    public int redirectArg() { throw new UnsupportedOperationException(); }
    @Override
    public String methodName() { throw new UnsupportedOperationException(); }
    @Override
    public String className() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static JavaReflectionAux fromJson(Json json) {
    JavaReflectionAux.Builder builder = JavaReflectionAux.builder();
    if (json.staticInvocationIsSet) {
      builder.staticInvocation(json.staticInvocation);
    }
    if (json.redirectArgIsSet) {
      builder.redirectArg(json.redirectArg);
    }
    if (json.methodName != null) {
      builder.methodName(json.methodName);
    }
    if (json.className != null) {
      builder.className(json.className);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IJavaReflectionAux} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable JavaReflectionAux instance
   */
  public static JavaReflectionAux copyOf(IJavaReflectionAux instance) {
    if (instance instanceof JavaReflectionAux) {
      return (JavaReflectionAux) instance;
    }
    return JavaReflectionAux.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link JavaReflectionAux JavaReflectionAux}.
   * <pre>
   * JavaReflectionAux.builder()
   *    .staticInvocation(boolean) // optional {@link IJavaReflectionAux#staticInvocation() staticInvocation}
   *    .redirectArg(int) // optional {@link IJavaReflectionAux#redirectArg() redirectArg}
   *    .methodName(String | null) // nullable {@link IJavaReflectionAux#methodName() methodName}
   *    .className(String | null) // nullable {@link IJavaReflectionAux#className() className}
   *    .build();
   * </pre>
   * @return A new JavaReflectionAux builder
   */
  public static JavaReflectionAux.Builder builder() {
    return new JavaReflectionAux.Builder();
  }

  /**
   * Builds instances of type {@link JavaReflectionAux JavaReflectionAux}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IJavaReflectionAux", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long OPT_BIT_STATIC_INVOCATION = 0x1L;
    private static final long OPT_BIT_REDIRECT_ARG = 0x2L;
    private long optBits;

    private boolean staticInvocation;
    private int redirectArg;
    private @Nullable String methodName;
    private @Nullable String className;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableJavaReflectionAux} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableJavaReflectionAux instance) {
      Objects.requireNonNull(instance, "instance");
      staticInvocation(instance.staticInvocation());
      redirectArg(instance.redirectArg());
      @Nullable String methodNameValue = instance.methodName();
      if (methodNameValue != null) {
        methodName(methodNameValue);
      }
      @Nullable String classNameValue = instance.className();
      if (classNameValue != null) {
        className(classNameValue);
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IJavaReflectionAux} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IJavaReflectionAux instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableJavaReflectionAux) {
        return from((MutableJavaReflectionAux) instance);
      }
      staticInvocation(instance.staticInvocation());
      redirectArg(instance.redirectArg());
      @Nullable String methodNameValue = instance.methodName();
      if (methodNameValue != null) {
        methodName(methodNameValue);
      }
      @Nullable String classNameValue = instance.className();
      if (classNameValue != null) {
        className(classNameValue);
      }
      return this;
    }

    /**
     * Initializes the value for the {@link IJavaReflectionAux#staticInvocation() staticInvocation} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IJavaReflectionAux#staticInvocation() staticInvocation}.</em>
     * @param staticInvocation The value for staticInvocation 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("staticInvocation")
    public final Builder staticInvocation(boolean staticInvocation) {
      this.staticInvocation = staticInvocation;
      optBits |= OPT_BIT_STATIC_INVOCATION;
      return this;
    }

    /**
     * Initializes the value for the {@link IJavaReflectionAux#redirectArg() redirectArg} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IJavaReflectionAux#redirectArg() redirectArg}.</em>
     * @param redirectArg The value for redirectArg 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("redirectArg")
    public final Builder redirectArg(int redirectArg) {
      this.redirectArg = redirectArg;
      optBits |= OPT_BIT_REDIRECT_ARG;
      return this;
    }

    /**
     * Initializes the value for the {@link IJavaReflectionAux#methodName() methodName} attribute.
     * @param methodName The value for methodName (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("methodName")
    public final Builder methodName(@Nullable String methodName) {
      this.methodName = methodName;
      return this;
    }

    /**
     * Initializes the value for the {@link IJavaReflectionAux#className() className} attribute.
     * @param className The value for className (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("className")
    public final Builder className(@Nullable String className) {
      this.className = className;
      return this;
    }

    /**
     * Builds a new {@link JavaReflectionAux JavaReflectionAux}.
     * @return An immutable instance of JavaReflectionAux
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public JavaReflectionAux build() {
      return new JavaReflectionAux(this);
    }

    private boolean staticInvocationIsSet() {
      return (optBits & OPT_BIT_STATIC_INVOCATION) != 0;
    }

    private boolean redirectArgIsSet() {
      return (optBits & OPT_BIT_REDIRECT_ARG) != 0;
    }
  }
}
