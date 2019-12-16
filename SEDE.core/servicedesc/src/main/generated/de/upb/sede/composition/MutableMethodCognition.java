package de.upb.sede.composition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.exec.IMethodDesc;
import de.upb.sede.exec.IMethodRef;
import de.upb.sede.exec.IServiceDesc;
import de.upb.sede.exec.ISignatureDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IMethodCognition IMethodCognition} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableMethodCognition is not thread-safe</em>
 * @see MethodCognition
 */
@Generated(from = "IMethodCognition", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IMethodCognition"})
@NotThreadSafe
public final class MutableMethodCognition implements IMethodCognition {
  private static final long INIT_BIT_METHOD_REF = 0x1L;
  private static final long INIT_BIT_SIGNATURE_DESC = 0x2L;
  private static final long INIT_BIT_METHOD_DESC = 0x4L;
  private static final long INIT_BIT_SERVICE_DESC = 0x8L;
  private long initBits = 0xfL;

  private IMethodRef methodRef;
  private ISignatureDesc signatureDesc;
  private IMethodDesc methodDesc;
  private IServiceDesc serviceDesc;
  private final ArrayList<ITypeCoercion> parameterTypeCoersion = new ArrayList<ITypeCoercion>();

  private MutableMethodCognition() {}

  /**
   * Construct a modifiable instance of {@code IMethodCognition}.
   * @return A new modifiable instance
   */
  public static MutableMethodCognition create() {
    return new MutableMethodCognition();
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
   * @return value of {@code signatureDesc} attribute
   */
  @JsonProperty("signatureDesc")
  @Override
  public final ISignatureDesc getSignatureDesc() {
    if (!signatureDescIsSet()) {
      checkRequiredAttributes();
    }
    return signatureDesc;
  }

  /**
   * @return value of {@code methodDesc} attribute
   */
  @JsonProperty("methodDesc")
  @Override
  public final IMethodDesc getMethodDesc() {
    if (!methodDescIsSet()) {
      checkRequiredAttributes();
    }
    return methodDesc;
  }

  /**
   * @return value of {@code serviceDesc} attribute
   */
  @JsonProperty("serviceDesc")
  @Override
  public final IServiceDesc getServiceDesc() {
    if (!serviceDescIsSet()) {
      checkRequiredAttributes();
    }
    return serviceDesc;
  }

  /**
   * @return modifiable list {@code parameterTypeCoersion}
   */
  @JsonProperty("parameterTypeCoersion")
  @Override
  public final List<ITypeCoercion> getParameterTypeCoersion() {
    return parameterTypeCoersion;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodCognition clear() {
    initBits = 0xfL;
    methodRef = null;
    signatureDesc = null;
    methodDesc = null;
    serviceDesc = null;
    parameterTypeCoersion.clear();
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IMethodCognition} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableMethodCognition from(IMethodCognition instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableMethodCognition) {
      from((MutableMethodCognition) instance);
      return this;
    }
    setMethodRef(instance.getMethodRef());
    setSignatureDesc(instance.getSignatureDesc());
    setMethodDesc(instance.getMethodDesc());
    setServiceDesc(instance.getServiceDesc());
    addAllParameterTypeCoersion(instance.getParameterTypeCoersion());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IMethodCognition} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableMethodCognition from(MutableMethodCognition instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.methodRefIsSet()) {
      setMethodRef(instance.getMethodRef());
    }
    if (instance.signatureDescIsSet()) {
      setSignatureDesc(instance.getSignatureDesc());
    }
    if (instance.methodDescIsSet()) {
      setMethodDesc(instance.getMethodDesc());
    }
    if (instance.serviceDescIsSet()) {
      setServiceDesc(instance.getServiceDesc());
    }
    addAllParameterTypeCoersion(instance.getParameterTypeCoersion());
    return this;
  }

  /**
   * Assigns a value to the {@link IMethodCognition#getMethodRef() methodRef} attribute.
   * @param methodRef The value for methodRef
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodCognition setMethodRef(IMethodRef methodRef) {
    this.methodRef = Objects.requireNonNull(methodRef, "methodRef");
    initBits &= ~INIT_BIT_METHOD_REF;
    return this;
  }

  /**
   * Assigns a value to the {@link IMethodCognition#getSignatureDesc() signatureDesc} attribute.
   * @param signatureDesc The value for signatureDesc
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodCognition setSignatureDesc(ISignatureDesc signatureDesc) {
    this.signatureDesc = Objects.requireNonNull(signatureDesc, "signatureDesc");
    initBits &= ~INIT_BIT_SIGNATURE_DESC;
    return this;
  }

  /**
   * Assigns a value to the {@link IMethodCognition#getMethodDesc() methodDesc} attribute.
   * @param methodDesc The value for methodDesc
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodCognition setMethodDesc(IMethodDesc methodDesc) {
    this.methodDesc = Objects.requireNonNull(methodDesc, "methodDesc");
    initBits &= ~INIT_BIT_METHOD_DESC;
    return this;
  }

  /**
   * Assigns a value to the {@link IMethodCognition#getServiceDesc() serviceDesc} attribute.
   * @param serviceDesc The value for serviceDesc
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodCognition setServiceDesc(IServiceDesc serviceDesc) {
    this.serviceDesc = Objects.requireNonNull(serviceDesc, "serviceDesc");
    initBits &= ~INIT_BIT_SERVICE_DESC;
    return this;
  }

  /**
   * Adds one element to {@link IMethodCognition#getParameterTypeCoersion() parameterTypeCoersion} list.
   * @param element The parameterTypeCoersion element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodCognition addParameterTypeCoersion(ITypeCoercion element) {
    Objects.requireNonNull(element, "parameterTypeCoersion element");
    this.parameterTypeCoersion.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IMethodCognition#getParameterTypeCoersion() parameterTypeCoersion} list.
   * @param elements An array of parameterTypeCoersion elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableMethodCognition addParameterTypeCoersion(ITypeCoercion... elements) {
    for (ITypeCoercion e : elements) {
      addParameterTypeCoersion(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IMethodCognition#getParameterTypeCoersion() parameterTypeCoersion} list.
   * @param elements An iterable of parameterTypeCoersion elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodCognition setParameterTypeCoersion(Iterable<? extends ITypeCoercion> elements) {
    this.parameterTypeCoersion.clear();
    addAllParameterTypeCoersion(elements);
    return this;
  }

  /**
   * Adds elements to {@link IMethodCognition#getParameterTypeCoersion() parameterTypeCoersion} list.
   * @param elements An iterable of parameterTypeCoersion elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodCognition addAllParameterTypeCoersion(Iterable<? extends ITypeCoercion> elements) {
    for (ITypeCoercion e : elements) {
      addParameterTypeCoersion(e);
    }
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IMethodCognition#getMethodRef() methodRef} is set.
   * @return {@code true} if set
   */
  public final boolean methodRefIsSet() {
    return (initBits & INIT_BIT_METHOD_REF) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IMethodCognition#getSignatureDesc() signatureDesc} is set.
   * @return {@code true} if set
   */
  public final boolean signatureDescIsSet() {
    return (initBits & INIT_BIT_SIGNATURE_DESC) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IMethodCognition#getMethodDesc() methodDesc} is set.
   * @return {@code true} if set
   */
  public final boolean methodDescIsSet() {
    return (initBits & INIT_BIT_METHOD_DESC) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IMethodCognition#getServiceDesc() serviceDesc} is set.
   * @return {@code true} if set
   */
  public final boolean serviceDescIsSet() {
    return (initBits & INIT_BIT_SERVICE_DESC) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableMethodCognition unsetMethodRef() {
    initBits |= INIT_BIT_METHOD_REF;
    methodRef = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableMethodCognition unsetSignatureDesc() {
    initBits |= INIT_BIT_SIGNATURE_DESC;
    signatureDesc = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableMethodCognition unsetMethodDesc() {
    initBits |= INIT_BIT_METHOD_DESC;
    methodDesc = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableMethodCognition unsetServiceDesc() {
    initBits |= INIT_BIT_SERVICE_DESC;
    serviceDesc = null;
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
    if (!signatureDescIsSet()) attributes.add("signatureDesc");
    if (!methodDescIsSet()) attributes.add("methodDesc");
    if (!serviceDescIsSet()) attributes.add("serviceDesc");
    return "MethodCognition is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link MethodCognition MethodCognition}.
   * @return An immutable instance of MethodCognition
   */
  public final MethodCognition toImmutable() {
    checkRequiredAttributes();
    return MethodCognition.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableMethodCognition} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableMethodCognition)) return false;
    MutableMethodCognition other = (MutableMethodCognition) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableMethodCognition another) {
    return methodRef.equals(another.methodRef)
        && signatureDesc.equals(another.signatureDesc)
        && methodDesc.equals(another.methodDesc)
        && serviceDesc.equals(another.serviceDesc)
        && parameterTypeCoersion.equals(another.parameterTypeCoersion);
  }

  /**
   * Computes a hash code from attributes: {@code methodRef}, {@code signatureDesc}, {@code methodDesc}, {@code serviceDesc}, {@code parameterTypeCoersion}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + methodRef.hashCode();
    h += (h << 5) + signatureDesc.hashCode();
    h += (h << 5) + methodDesc.hashCode();
    h += (h << 5) + serviceDesc.hashCode();
    h += (h << 5) + parameterTypeCoersion.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IMethodCognition}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableMethodCognition")
        .add("methodRef", methodRefIsSet() ? getMethodRef() : "?")
        .add("signatureDesc", signatureDescIsSet() ? getSignatureDesc() : "?")
        .add("methodDesc", methodDescIsSet() ? getMethodDesc() : "?")
        .add("serviceDesc", serviceDescIsSet() ? getServiceDesc() : "?")
        .add("parameterTypeCoersion", getParameterTypeCoersion())
        .toString();
  }
}
