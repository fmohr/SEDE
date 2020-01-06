package de.upb.sede.composition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.exec.IMethodRef;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IMethodResolution IMethodResolution} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableMethodResolution is not thread-safe</em>
 * @see MethodResolution
 */
@Generated(from = "IMethodResolution", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IMethodResolution"})
@NotThreadSafe
public final class MutableMethodResolution implements IMethodResolution {
  private static final long INIT_BIT_METHOD_REF = 0x1L;
  private long initBits = 0x1L;

  private IMethodRef methodRef;
  private final ArrayList<ITypeCoercion> paramTypeCoercions = new ArrayList<ITypeCoercion>();

  private MutableMethodResolution() {}

  /**
   * Construct a modifiable instance of {@code IMethodResolution}.
   * @return A new modifiable instance
   */
  public static MutableMethodResolution create() {
    return new MutableMethodResolution();
  }

  /**
   * @return value of {@code methodRef} attribute
   */
  @JsonProperty("methodRef")
  @Override
  public final IMethodRef getMethodRef() {
    if (!methodRefIsSet()) {
      checkRequiredAttributes();
    }
    return methodRef;
  }

  /**
   * @return modifiable list {@code paramTypeCoercions}
   */
  @JsonProperty("paramTypeCoercions")
  @Override
  public final List<ITypeCoercion> getParamTypeCoercions() {
    return paramTypeCoercions;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodResolution clear() {
    initBits = 0x1L;
    methodRef = null;
    paramTypeCoercions.clear();
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IMethodResolution} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableMethodResolution from(IMethodResolution instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableMethodResolution) {
      from((MutableMethodResolution) instance);
      return this;
    }
    setMethodRef(instance.getMethodRef());
    addAllParamTypeCoercions(instance.getParamTypeCoercions());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IMethodResolution} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableMethodResolution from(MutableMethodResolution instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.methodRefIsSet()) {
      setMethodRef(instance.getMethodRef());
    }
    addAllParamTypeCoercions(instance.getParamTypeCoercions());
    return this;
  }

  /**
   * Assigns a value to the {@link IMethodResolution#getMethodRef() methodRef} attribute.
   * @param methodRef The value for methodRef
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodResolution setMethodRef(IMethodRef methodRef) {
    this.methodRef = Objects.requireNonNull(methodRef, "methodRef");
    initBits &= ~INIT_BIT_METHOD_REF;
    return this;
  }

  /**
   * Adds one element to {@link IMethodResolution#getParamTypeCoercions() paramTypeCoercions} list.
   * @param element The paramTypeCoercions element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodResolution addParamTypeCoercions(ITypeCoercion element) {
    Objects.requireNonNull(element, "paramTypeCoercions element");
    this.paramTypeCoercions.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IMethodResolution#getParamTypeCoercions() paramTypeCoercions} list.
   * @param elements An array of paramTypeCoercions elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableMethodResolution addParamTypeCoercions(ITypeCoercion... elements) {
    for (ITypeCoercion e : elements) {
      addParamTypeCoercions(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IMethodResolution#getParamTypeCoercions() paramTypeCoercions} list.
   * @param elements An iterable of paramTypeCoercions elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodResolution setParamTypeCoercions(Iterable<? extends ITypeCoercion> elements) {
    this.paramTypeCoercions.clear();
    addAllParamTypeCoercions(elements);
    return this;
  }

  /**
   * Adds elements to {@link IMethodResolution#getParamTypeCoercions() paramTypeCoercions} list.
   * @param elements An iterable of paramTypeCoercions elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodResolution addAllParamTypeCoercions(Iterable<? extends ITypeCoercion> elements) {
    for (ITypeCoercion e : elements) {
      addParamTypeCoercions(e);
    }
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IMethodResolution#getMethodRef() methodRef} is set.
   * @return {@code true} if set
   */
  public final boolean methodRefIsSet() {
    return (initBits & INIT_BIT_METHOD_REF) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableMethodResolution unsetMethodRef() {
    initBits |= INIT_BIT_METHOD_REF;
    methodRef = null;
    return this;
  }

  /**
   * Returns {@code true} if all required attributes are set, indicating that the object is initialized.
   * @return {@code true} if set
   */
  public final boolean isInitialized() {
    return initBits == 0;
  }

  private void checkRequiredAttributes() {
    if (!isInitialized()) {
      throw new IllegalStateException(formatRequiredAttributesMessage());
    }
  }

  private String formatRequiredAttributesMessage() {
    List<String> attributes = new ArrayList<>();
    if (!methodRefIsSet()) attributes.add("methodRef");
    return "MethodResolution is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link MethodResolution MethodResolution}.
   * @return An immutable instance of MethodResolution
   */
  public final MethodResolution toImmutable() {
    checkRequiredAttributes();
    return MethodResolution.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableMethodResolution} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableMethodResolution)) return false;
    MutableMethodResolution other = (MutableMethodResolution) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableMethodResolution another) {
    return methodRef.equals(another.methodRef)
        && paramTypeCoercions.equals(another.paramTypeCoercions);
  }

  /**
   * Computes a hash code from attributes: {@code methodRef}, {@code paramTypeCoercions}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + methodRef.hashCode();
    h += (h << 5) + paramTypeCoercions.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IMethodResolution}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableMethodResolution")
        .add("methodRef", methodRefIsSet() ? getMethodRef() : "?")
        .add("paramTypeCoercions", getParamTypeCoercions())
        .toString();
  }
}
