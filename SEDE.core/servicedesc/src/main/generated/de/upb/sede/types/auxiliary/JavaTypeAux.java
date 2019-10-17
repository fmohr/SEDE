package de.upb.sede.types.auxiliary;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.exec.auxiliary.IJavaDispatchAux;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link IJavaTypeAux}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code JavaTypeAux.builder()}.
 */
@Generated(from = "IJavaTypeAux", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class JavaTypeAux implements IJavaTypeAux {
  private final @Nullable IJavaDispatchAux dataCastHandler;
  private final @Nullable String className;

  private JavaTypeAux(
      @Nullable IJavaDispatchAux dataCastHandler,
      @Nullable String className) {
    this.dataCastHandler = dataCastHandler;
    this.className = className;
  }

  /**
   * @return The value of the {@code dataCastHandler} attribute
   */
  @JsonProperty("dataCastHandler")
  @Override
  public @Nullable IJavaDispatchAux getDataCastHandler() {
    return dataCastHandler;
  }

  /**
   * @return The value of the {@code className} attribute
   */
  @JsonProperty("className")
  @Override
  public @Nullable String getClassName() {
    return className;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IJavaTypeAux#getDataCastHandler() dataCastHandler} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for dataCastHandler (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final JavaTypeAux withDataCastHandler(@Nullable IJavaDispatchAux value) {
    if (this.dataCastHandler == value) return this;
    return new JavaTypeAux(value, this.className);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IJavaTypeAux#getClassName() className} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for className (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final JavaTypeAux withClassName(@Nullable String value) {
    if (Objects.equals(this.className, value)) return this;
    return new JavaTypeAux(this.dataCastHandler, value);
  }

  /**
   * This instance is equal to all instances of {@code JavaTypeAux} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof JavaTypeAux
        && equalTo((JavaTypeAux) another);
  }

  private boolean equalTo(JavaTypeAux another) {
    return Objects.equals(dataCastHandler, another.dataCastHandler)
        && Objects.equals(className, another.className);
  }

  /**
   * Computes a hash code from attributes: {@code dataCastHandler}, {@code className}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Objects.hashCode(dataCastHandler);
    h += (h << 5) + Objects.hashCode(className);
    return h;
  }

  /**
   * Prints the immutable value {@code JavaTypeAux} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("JavaTypeAux")
        .omitNullValues()
        .add("dataCastHandler", dataCastHandler)
        .add("className", className)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IJavaTypeAux", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IJavaTypeAux {
    @Nullable IJavaDispatchAux dataCastHandler;
    @Nullable String className;
    @JsonProperty("dataCastHandler")
    public void setDataCastHandler(@Nullable IJavaDispatchAux dataCastHandler) {
      this.dataCastHandler = dataCastHandler;
    }
    @JsonProperty("className")
    public void setClassName(@Nullable String className) {
      this.className = className;
    }
    @Override
    public IJavaDispatchAux getDataCastHandler() { throw new UnsupportedOperationException(); }
    @Override
    public String getClassName() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static JavaTypeAux fromJson(Json json) {
    JavaTypeAux.Builder builder = JavaTypeAux.builder();
    if (json.dataCastHandler != null) {
      builder.dataCastHandler(json.dataCastHandler);
    }
    if (json.className != null) {
      builder.className(json.className);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IJavaTypeAux} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable JavaTypeAux instance
   */
  public static JavaTypeAux copyOf(IJavaTypeAux instance) {
    if (instance instanceof JavaTypeAux) {
      return (JavaTypeAux) instance;
    }
    return JavaTypeAux.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link JavaTypeAux JavaTypeAux}.
   * <pre>
   * JavaTypeAux.builder()
   *    .dataCastHandler(de.upb.sede.exec.auxiliary.IJavaDispatchAux | null) // nullable {@link IJavaTypeAux#getDataCastHandler() dataCastHandler}
   *    .className(String | null) // nullable {@link IJavaTypeAux#getClassName() className}
   *    .build();
   * </pre>
   * @return A new JavaTypeAux builder
   */
  public static JavaTypeAux.Builder builder() {
    return new JavaTypeAux.Builder();
  }

  /**
   * Builds instances of type {@link JavaTypeAux JavaTypeAux}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IJavaTypeAux", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private @Nullable IJavaDispatchAux dataCastHandler;
    private @Nullable String className;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableJavaTypeAux} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableJavaTypeAux instance) {
      Objects.requireNonNull(instance, "instance");
      @Nullable IJavaDispatchAux dataCastHandlerValue = instance.getDataCastHandler();
      if (dataCastHandlerValue != null) {
        dataCastHandler(dataCastHandlerValue);
      }
      @Nullable String classNameValue = instance.getClassName();
      if (classNameValue != null) {
        className(classNameValue);
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IJavaTypeAux} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IJavaTypeAux instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableJavaTypeAux) {
        return from((MutableJavaTypeAux) instance);
      }
      @Nullable IJavaDispatchAux dataCastHandlerValue = instance.getDataCastHandler();
      if (dataCastHandlerValue != null) {
        dataCastHandler(dataCastHandlerValue);
      }
      @Nullable String classNameValue = instance.getClassName();
      if (classNameValue != null) {
        className(classNameValue);
      }
      return this;
    }

    /**
     * Initializes the value for the {@link IJavaTypeAux#getDataCastHandler() dataCastHandler} attribute.
     * @param dataCastHandler The value for dataCastHandler (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("dataCastHandler")
    public final Builder dataCastHandler(@Nullable IJavaDispatchAux dataCastHandler) {
      this.dataCastHandler = dataCastHandler;
      return this;
    }

    /**
     * Initializes the value for the {@link IJavaTypeAux#getClassName() className} attribute.
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
     * Builds a new {@link JavaTypeAux JavaTypeAux}.
     * @return An immutable instance of JavaTypeAux
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public JavaTypeAux build() {
      return new JavaTypeAux(dataCastHandler, className);
    }
  }
}
