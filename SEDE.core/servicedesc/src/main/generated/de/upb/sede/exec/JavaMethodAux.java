package de.upb.sede.exec;

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
 * Immutable implementation of {@link IJavaMethodAux}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code JavaMethodAux.builder()}.
 */
@Generated(from = "IJavaMethodAux", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class JavaMethodAux implements IJavaMethodAux {
  private final boolean staticInvocation;
  private final int redirectArg;

  private JavaMethodAux(JavaMethodAux.Builder builder) {
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

  private JavaMethodAux(boolean staticInvocation, int redirectArg) {
    this.staticInvocation = staticInvocation;
    this.redirectArg = redirectArg;
    this.initShim = null;
  }

  private static final byte STAGE_INITIALIZING = -1;
  private static final byte STAGE_UNINITIALIZED = 0;
  private static final byte STAGE_INITIALIZED = 1;
  @SuppressWarnings("Immutable")
  private transient volatile InitShim initShim = new InitShim();

  @Generated(from = "IJavaMethodAux", generator = "Immutables")
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
      return "Cannot build JavaMethodAux, attribute initializers form cycle " + attributes;
    }
  }

  private boolean staticInvocationInitialize() {
    return IJavaMethodAux.super.staticInvocation();
  }

  private int redirectArgInitialize() {
    return IJavaMethodAux.super.redirectArg();
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
   * Copy the current immutable object by setting a value for the {@link IJavaMethodAux#staticInvocation() staticInvocation} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for staticInvocation
   * @return A modified copy of the {@code this} object
   */
  public final JavaMethodAux withStaticInvocation(boolean value) {
    if (this.staticInvocation == value) return this;
    return new JavaMethodAux(value, this.redirectArg);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IJavaMethodAux#redirectArg() redirectArg} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for redirectArg
   * @return A modified copy of the {@code this} object
   */
  public final JavaMethodAux withRedirectArg(int value) {
    if (this.redirectArg == value) return this;
    return new JavaMethodAux(this.staticInvocation, value);
  }

  /**
   * This instance is equal to all instances of {@code JavaMethodAux} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof JavaMethodAux
        && equalTo((JavaMethodAux) another);
  }

  private boolean equalTo(JavaMethodAux another) {
    return staticInvocation == another.staticInvocation
        && redirectArg == another.redirectArg;
  }

  /**
   * Computes a hash code from attributes: {@code staticInvocation}, {@code redirectArg}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Booleans.hashCode(staticInvocation);
    h += (h << 5) + redirectArg;
    return h;
  }

  /**
   * Prints the immutable value {@code JavaMethodAux} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("JavaMethodAux")
        .omitNullValues()
        .add("staticInvocation", staticInvocation)
        .add("redirectArg", redirectArg)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IJavaMethodAux", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IJavaMethodAux {
    boolean staticInvocation;
    boolean staticInvocationIsSet;
    int redirectArg;
    boolean redirectArgIsSet;
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
    @Override
    public boolean staticInvocation() { throw new UnsupportedOperationException(); }
    @Override
    public int redirectArg() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static JavaMethodAux fromJson(Json json) {
    JavaMethodAux.Builder builder = JavaMethodAux.builder();
    if (json.staticInvocationIsSet) {
      builder.staticInvocation(json.staticInvocation);
    }
    if (json.redirectArgIsSet) {
      builder.redirectArg(json.redirectArg);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IJavaMethodAux} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable JavaMethodAux instance
   */
  public static JavaMethodAux copyOf(IJavaMethodAux instance) {
    if (instance instanceof JavaMethodAux) {
      return (JavaMethodAux) instance;
    }
    return JavaMethodAux.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link JavaMethodAux JavaMethodAux}.
   * <pre>
   * JavaMethodAux.builder()
   *    .staticInvocation(boolean) // optional {@link IJavaMethodAux#staticInvocation() staticInvocation}
   *    .redirectArg(int) // optional {@link IJavaMethodAux#redirectArg() redirectArg}
   *    .build();
   * </pre>
   * @return A new JavaMethodAux builder
   */
  public static JavaMethodAux.Builder builder() {
    return new JavaMethodAux.Builder();
  }

  /**
   * Builds instances of type {@link JavaMethodAux JavaMethodAux}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IJavaMethodAux", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long OPT_BIT_STATIC_INVOCATION = 0x1L;
    private static final long OPT_BIT_REDIRECT_ARG = 0x2L;
    private long optBits;

    private boolean staticInvocation;
    private int redirectArg;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableJavaMethodAux} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableJavaMethodAux instance) {
      Objects.requireNonNull(instance, "instance");
      staticInvocation(instance.staticInvocation());
      redirectArg(instance.redirectArg());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IJavaMethodAux} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IJavaMethodAux instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableJavaMethodAux) {
        return from((MutableJavaMethodAux) instance);
      }
      staticInvocation(instance.staticInvocation());
      redirectArg(instance.redirectArg());
      return this;
    }

    /**
     * Initializes the value for the {@link IJavaMethodAux#staticInvocation() staticInvocation} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IJavaMethodAux#staticInvocation() staticInvocation}.</em>
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
     * Initializes the value for the {@link IJavaMethodAux#redirectArg() redirectArg} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IJavaMethodAux#redirectArg() redirectArg}.</em>
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
     * Builds a new {@link JavaMethodAux JavaMethodAux}.
     * @return An immutable instance of JavaMethodAux
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public JavaMethodAux build() {
      return new JavaMethodAux(this);
    }

    private boolean staticInvocationIsSet() {
      return (optBits & OPT_BIT_STATIC_INVOCATION) != 0;
    }

    private boolean redirectArgIsSet() {
      return (optBits & OPT_BIT_REDIRECT_ARG) != 0;
    }
  }
}
