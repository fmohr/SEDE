package de.upb.sede.composition;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.exec.IMethodRef;
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
 * Immutable implementation of {@link IMethodResolution}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code MethodResolution.builder()}.
 */
@Generated(from = "IMethodResolution", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class MethodResolution implements IMethodResolution {
  private final IMethodRef methodRef;
  private final ImmutableList<ITypeCoercion> paramTypeCoercions;

  private MethodResolution(
      IMethodRef methodRef,
      ImmutableList<ITypeCoercion> paramTypeCoercions) {
    this.methodRef = methodRef;
    this.paramTypeCoercions = paramTypeCoercions;
  }

  /**
   * @return The value of the {@code methodRef} attribute
   */
  @JsonProperty("methodRef")
  @Override
  public IMethodRef getMethodRef() {
    return methodRef;
  }

  /**
   * @return The value of the {@code paramTypeCoercions} attribute
   */
  @JsonProperty("paramTypeCoercions")
  @Override
  public ImmutableList<ITypeCoercion> getParamTypeCoercions() {
    return paramTypeCoercions;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IMethodResolution#getMethodRef() methodRef} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for methodRef
   * @return A modified copy of the {@code this} object
   */
  public final MethodResolution withMethodRef(IMethodRef value) {
    if (this.methodRef == value) return this;
    IMethodRef newValue = Objects.requireNonNull(value, "methodRef");
    return new MethodResolution(newValue, this.paramTypeCoercions);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IMethodResolution#getParamTypeCoercions() paramTypeCoercions}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final MethodResolution withParamTypeCoercions(ITypeCoercion... elements) {
    ImmutableList<ITypeCoercion> newValue = ImmutableList.copyOf(elements);
    return new MethodResolution(this.methodRef, newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IMethodResolution#getParamTypeCoercions() paramTypeCoercions}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of paramTypeCoercions elements to set
   * @return A modified copy of {@code this} object
   */
  public final MethodResolution withParamTypeCoercions(Iterable<? extends ITypeCoercion> elements) {
    if (this.paramTypeCoercions == elements) return this;
    ImmutableList<ITypeCoercion> newValue = ImmutableList.copyOf(elements);
    return new MethodResolution(this.methodRef, newValue);
  }

  /**
   * This instance is equal to all instances of {@code MethodResolution} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof MethodResolution
        && equalTo((MethodResolution) another);
  }

  private boolean equalTo(MethodResolution another) {
    return methodRef.equals(another.methodRef)
        && paramTypeCoercions.equals(another.paramTypeCoercions);
  }

  /**
   * Computes a hash code from attributes: {@code methodRef}, {@code paramTypeCoercions}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + methodRef.hashCode();
    h += (h << 5) + paramTypeCoercions.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code MethodResolution} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MethodResolution")
        .omitNullValues()
        .add("methodRef", methodRef)
        .add("paramTypeCoercions", paramTypeCoercions)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IMethodResolution", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IMethodResolution {
    @Nullable IMethodRef methodRef;
    @Nullable List<ITypeCoercion> paramTypeCoercions = ImmutableList.of();
    @JsonProperty("methodRef")
    public void setMethodRef(IMethodRef methodRef) {
      this.methodRef = methodRef;
    }
    @JsonProperty("paramTypeCoercions")
    public void setParamTypeCoercions(List<ITypeCoercion> paramTypeCoercions) {
      this.paramTypeCoercions = paramTypeCoercions;
    }
    @Override
    public IMethodRef getMethodRef() { throw new UnsupportedOperationException(); }
    @Override
    public List<ITypeCoercion> getParamTypeCoercions() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static MethodResolution fromJson(Json json) {
    MethodResolution.Builder builder = MethodResolution.builder();
    if (json.methodRef != null) {
      builder.methodRef(json.methodRef);
    }
    if (json.paramTypeCoercions != null) {
      builder.addAllParamTypeCoercions(json.paramTypeCoercions);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IMethodResolution} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable MethodResolution instance
   */
  public static MethodResolution copyOf(IMethodResolution instance) {
    if (instance instanceof MethodResolution) {
      return (MethodResolution) instance;
    }
    return MethodResolution.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link MethodResolution MethodResolution}.
   * <pre>
   * MethodResolution.builder()
   *    .methodRef(de.upb.sede.exec.IMethodRef) // required {@link IMethodResolution#getMethodRef() methodRef}
   *    .addParamTypeCoercions|addAllParamTypeCoercions(de.upb.sede.composition.ITypeCoercion) // {@link IMethodResolution#getParamTypeCoercions() paramTypeCoercions} elements
   *    .build();
   * </pre>
   * @return A new MethodResolution builder
   */
  public static MethodResolution.Builder builder() {
    return new MethodResolution.Builder();
  }

  /**
   * Builds instances of type {@link MethodResolution MethodResolution}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IMethodResolution", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_METHOD_REF = 0x1L;
    private long initBits = 0x1L;

    private @Nullable IMethodRef methodRef;
    private ImmutableList.Builder<ITypeCoercion> paramTypeCoercions = ImmutableList.builder();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableMethodResolution} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableMethodResolution instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.methodRefIsSet()) {
        methodRef(instance.getMethodRef());
      }
      addAllParamTypeCoercions(instance.getParamTypeCoercions());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IMethodResolution} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IMethodResolution instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableMethodResolution) {
        return from((MutableMethodResolution) instance);
      }
      methodRef(instance.getMethodRef());
      addAllParamTypeCoercions(instance.getParamTypeCoercions());
      return this;
    }

    /**
     * Initializes the value for the {@link IMethodResolution#getMethodRef() methodRef} attribute.
     * @param methodRef The value for methodRef 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("methodRef")
    public final Builder methodRef(IMethodRef methodRef) {
      this.methodRef = Objects.requireNonNull(methodRef, "methodRef");
      initBits &= ~INIT_BIT_METHOD_REF;
      return this;
    }

    /**
     * Adds one element to {@link IMethodResolution#getParamTypeCoercions() paramTypeCoercions} list.
     * @param element A paramTypeCoercions element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addParamTypeCoercions(ITypeCoercion element) {
      this.paramTypeCoercions.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IMethodResolution#getParamTypeCoercions() paramTypeCoercions} list.
     * @param elements An array of paramTypeCoercions elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addParamTypeCoercions(ITypeCoercion... elements) {
      this.paramTypeCoercions.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IMethodResolution#getParamTypeCoercions() paramTypeCoercions} list.
     * @param elements An iterable of paramTypeCoercions elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("paramTypeCoercions")
    public final Builder paramTypeCoercions(Iterable<? extends ITypeCoercion> elements) {
      this.paramTypeCoercions = ImmutableList.builder();
      return addAllParamTypeCoercions(elements);
    }

    /**
     * Adds elements to {@link IMethodResolution#getParamTypeCoercions() paramTypeCoercions} list.
     * @param elements An iterable of paramTypeCoercions elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllParamTypeCoercions(Iterable<? extends ITypeCoercion> elements) {
      this.paramTypeCoercions.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link MethodResolution MethodResolution}.
     * @return An immutable instance of MethodResolution
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public MethodResolution build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new MethodResolution(methodRef, paramTypeCoercions.build());
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_METHOD_REF) != 0) attributes.add("methodRef");
      return "Cannot build MethodResolution, some of required attributes are not set " + attributes;
    }
  }
}
