package de.upb.sede.composition;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.exec.IMethodDesc;
import de.upb.sede.exec.IMethodRef;
import de.upb.sede.exec.IServiceDesc;
import de.upb.sede.exec.ISignatureDesc;
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
 * Immutable implementation of {@link IMethodCognition}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code MethodCognition.builder()}.
 */
@Generated(from = "IMethodCognition", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class MethodCognition implements IMethodCognition {
  private final IMethodRef methodRef;
  private final ISignatureDesc signatureDesc;
  private final IMethodDesc methodDesc;
  private final IServiceDesc serviceDesc;
  private final ImmutableList<ITypeCoercion> parameterTypeCoersion;

  private MethodCognition(
      IMethodRef methodRef,
      ISignatureDesc signatureDesc,
      IMethodDesc methodDesc,
      IServiceDesc serviceDesc,
      ImmutableList<ITypeCoercion> parameterTypeCoersion) {
    this.methodRef = methodRef;
    this.signatureDesc = signatureDesc;
    this.methodDesc = methodDesc;
    this.serviceDesc = serviceDesc;
    this.parameterTypeCoersion = parameterTypeCoersion;
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
   * @return The value of the {@code signatureDesc} attribute
   */
  @JsonProperty("signatureDesc")
  @Override
  public ISignatureDesc getSignatureDesc() {
    return signatureDesc;
  }

  /**
   * @return The value of the {@code methodDesc} attribute
   */
  @JsonProperty("methodDesc")
  @Override
  public IMethodDesc getMethodDesc() {
    return methodDesc;
  }

  /**
   * @return The value of the {@code serviceDesc} attribute
   */
  @JsonProperty("serviceDesc")
  @Override
  public IServiceDesc getServiceDesc() {
    return serviceDesc;
  }

  /**
   * @return The value of the {@code parameterTypeCoersion} attribute
   */
  @JsonProperty("parameterTypeCoersion")
  @Override
  public ImmutableList<ITypeCoercion> getParameterTypeCoersion() {
    return parameterTypeCoersion;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IMethodCognition#getMethodRef() methodRef} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for methodRef
   * @return A modified copy of the {@code this} object
   */
  public final MethodCognition withMethodRef(IMethodRef value) {
    if (this.methodRef == value) return this;
    IMethodRef newValue = Objects.requireNonNull(value, "methodRef");
    return new MethodCognition(newValue, this.signatureDesc, this.methodDesc, this.serviceDesc, this.parameterTypeCoersion);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IMethodCognition#getSignatureDesc() signatureDesc} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for signatureDesc
   * @return A modified copy of the {@code this} object
   */
  public final MethodCognition withSignatureDesc(ISignatureDesc value) {
    if (this.signatureDesc == value) return this;
    ISignatureDesc newValue = Objects.requireNonNull(value, "signatureDesc");
    return new MethodCognition(this.methodRef, newValue, this.methodDesc, this.serviceDesc, this.parameterTypeCoersion);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IMethodCognition#getMethodDesc() methodDesc} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for methodDesc
   * @return A modified copy of the {@code this} object
   */
  public final MethodCognition withMethodDesc(IMethodDesc value) {
    if (this.methodDesc == value) return this;
    IMethodDesc newValue = Objects.requireNonNull(value, "methodDesc");
    return new MethodCognition(this.methodRef, this.signatureDesc, newValue, this.serviceDesc, this.parameterTypeCoersion);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IMethodCognition#getServiceDesc() serviceDesc} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for serviceDesc
   * @return A modified copy of the {@code this} object
   */
  public final MethodCognition withServiceDesc(IServiceDesc value) {
    if (this.serviceDesc == value) return this;
    IServiceDesc newValue = Objects.requireNonNull(value, "serviceDesc");
    return new MethodCognition(this.methodRef, this.signatureDesc, this.methodDesc, newValue, this.parameterTypeCoersion);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IMethodCognition#getParameterTypeCoersion() parameterTypeCoersion}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final MethodCognition withParameterTypeCoersion(ITypeCoercion... elements) {
    ImmutableList<ITypeCoercion> newValue = ImmutableList.copyOf(elements);
    return new MethodCognition(this.methodRef, this.signatureDesc, this.methodDesc, this.serviceDesc, newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IMethodCognition#getParameterTypeCoersion() parameterTypeCoersion}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of parameterTypeCoersion elements to set
   * @return A modified copy of {@code this} object
   */
  public final MethodCognition withParameterTypeCoersion(Iterable<? extends ITypeCoercion> elements) {
    if (this.parameterTypeCoersion == elements) return this;
    ImmutableList<ITypeCoercion> newValue = ImmutableList.copyOf(elements);
    return new MethodCognition(this.methodRef, this.signatureDesc, this.methodDesc, this.serviceDesc, newValue);
  }

  /**
   * This instance is equal to all instances of {@code MethodCognition} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof MethodCognition
        && equalTo((MethodCognition) another);
  }

  private boolean equalTo(MethodCognition another) {
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
    @Var int h = 5381;
    h += (h << 5) + methodRef.hashCode();
    h += (h << 5) + signatureDesc.hashCode();
    h += (h << 5) + methodDesc.hashCode();
    h += (h << 5) + serviceDesc.hashCode();
    h += (h << 5) + parameterTypeCoersion.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code MethodCognition} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MethodCognition")
        .omitNullValues()
        .add("methodRef", methodRef)
        .add("signatureDesc", signatureDesc)
        .add("methodDesc", methodDesc)
        .add("serviceDesc", serviceDesc)
        .add("parameterTypeCoersion", parameterTypeCoersion)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IMethodCognition", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IMethodCognition {
    @Nullable IMethodRef methodRef;
    @Nullable ISignatureDesc signatureDesc;
    @Nullable IMethodDesc methodDesc;
    @Nullable IServiceDesc serviceDesc;
    @Nullable List<ITypeCoercion> parameterTypeCoersion = ImmutableList.of();
    @JsonProperty("methodRef")
    public void setMethodRef(IMethodRef methodRef) {
      this.methodRef = methodRef;
    }
    @JsonProperty("signatureDesc")
    public void setSignatureDesc(ISignatureDesc signatureDesc) {
      this.signatureDesc = signatureDesc;
    }
    @JsonProperty("methodDesc")
    public void setMethodDesc(IMethodDesc methodDesc) {
      this.methodDesc = methodDesc;
    }
    @JsonProperty("serviceDesc")
    public void setServiceDesc(IServiceDesc serviceDesc) {
      this.serviceDesc = serviceDesc;
    }
    @JsonProperty("parameterTypeCoersion")
    public void setParameterTypeCoersion(List<ITypeCoercion> parameterTypeCoersion) {
      this.parameterTypeCoersion = parameterTypeCoersion;
    }
    @Override
    public IMethodRef getMethodRef() { throw new UnsupportedOperationException(); }
    @Override
    public ISignatureDesc getSignatureDesc() { throw new UnsupportedOperationException(); }
    @Override
    public IMethodDesc getMethodDesc() { throw new UnsupportedOperationException(); }
    @Override
    public IServiceDesc getServiceDesc() { throw new UnsupportedOperationException(); }
    @Override
    public List<ITypeCoercion> getParameterTypeCoersion() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static MethodCognition fromJson(Json json) {
    MethodCognition.Builder builder = MethodCognition.builder();
    if (json.methodRef != null) {
      builder.methodRef(json.methodRef);
    }
    if (json.signatureDesc != null) {
      builder.signatureDesc(json.signatureDesc);
    }
    if (json.methodDesc != null) {
      builder.methodDesc(json.methodDesc);
    }
    if (json.serviceDesc != null) {
      builder.serviceDesc(json.serviceDesc);
    }
    if (json.parameterTypeCoersion != null) {
      builder.addAllParameterTypeCoersion(json.parameterTypeCoersion);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IMethodCognition} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable MethodCognition instance
   */
  public static MethodCognition copyOf(IMethodCognition instance) {
    if (instance instanceof MethodCognition) {
      return (MethodCognition) instance;
    }
    return MethodCognition.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link MethodCognition MethodCognition}.
   * <pre>
   * MethodCognition.builder()
   *    .methodRef(de.upb.sede.exec.IMethodRef) // required {@link IMethodCognition#getMethodRef() methodRef}
   *    .signatureDesc(de.upb.sede.exec.ISignatureDesc) // required {@link IMethodCognition#getSignatureDesc() signatureDesc}
   *    .methodDesc(de.upb.sede.exec.IMethodDesc) // required {@link IMethodCognition#getMethodDesc() methodDesc}
   *    .serviceDesc(de.upb.sede.exec.IServiceDesc) // required {@link IMethodCognition#getServiceDesc() serviceDesc}
   *    .addParameterTypeCoersion|addAllParameterTypeCoersion(de.upb.sede.composition.ITypeCoercion) // {@link IMethodCognition#getParameterTypeCoersion() parameterTypeCoersion} elements
   *    .build();
   * </pre>
   * @return A new MethodCognition builder
   */
  public static MethodCognition.Builder builder() {
    return new MethodCognition.Builder();
  }

  /**
   * Builds instances of type {@link MethodCognition MethodCognition}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IMethodCognition", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_METHOD_REF = 0x1L;
    private static final long INIT_BIT_SIGNATURE_DESC = 0x2L;
    private static final long INIT_BIT_METHOD_DESC = 0x4L;
    private static final long INIT_BIT_SERVICE_DESC = 0x8L;
    private long initBits = 0xfL;

    private @Nullable IMethodRef methodRef;
    private @Nullable ISignatureDesc signatureDesc;
    private @Nullable IMethodDesc methodDesc;
    private @Nullable IServiceDesc serviceDesc;
    private ImmutableList.Builder<ITypeCoercion> parameterTypeCoersion = ImmutableList.builder();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableMethodCognition} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableMethodCognition instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.methodRefIsSet()) {
        methodRef(instance.getMethodRef());
      }
      if (instance.signatureDescIsSet()) {
        signatureDesc(instance.getSignatureDesc());
      }
      if (instance.methodDescIsSet()) {
        methodDesc(instance.getMethodDesc());
      }
      if (instance.serviceDescIsSet()) {
        serviceDesc(instance.getServiceDesc());
      }
      addAllParameterTypeCoersion(instance.getParameterTypeCoersion());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IMethodCognition} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IMethodCognition instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableMethodCognition) {
        return from((MutableMethodCognition) instance);
      }
      methodRef(instance.getMethodRef());
      signatureDesc(instance.getSignatureDesc());
      methodDesc(instance.getMethodDesc());
      serviceDesc(instance.getServiceDesc());
      addAllParameterTypeCoersion(instance.getParameterTypeCoersion());
      return this;
    }

    /**
     * Initializes the value for the {@link IMethodCognition#getMethodRef() methodRef} attribute.
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
     * Initializes the value for the {@link IMethodCognition#getSignatureDesc() signatureDesc} attribute.
     * @param signatureDesc The value for signatureDesc 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("signatureDesc")
    public final Builder signatureDesc(ISignatureDesc signatureDesc) {
      this.signatureDesc = Objects.requireNonNull(signatureDesc, "signatureDesc");
      initBits &= ~INIT_BIT_SIGNATURE_DESC;
      return this;
    }

    /**
     * Initializes the value for the {@link IMethodCognition#getMethodDesc() methodDesc} attribute.
     * @param methodDesc The value for methodDesc 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("methodDesc")
    public final Builder methodDesc(IMethodDesc methodDesc) {
      this.methodDesc = Objects.requireNonNull(methodDesc, "methodDesc");
      initBits &= ~INIT_BIT_METHOD_DESC;
      return this;
    }

    /**
     * Initializes the value for the {@link IMethodCognition#getServiceDesc() serviceDesc} attribute.
     * @param serviceDesc The value for serviceDesc 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("serviceDesc")
    public final Builder serviceDesc(IServiceDesc serviceDesc) {
      this.serviceDesc = Objects.requireNonNull(serviceDesc, "serviceDesc");
      initBits &= ~INIT_BIT_SERVICE_DESC;
      return this;
    }

    /**
     * Adds one element to {@link IMethodCognition#getParameterTypeCoersion() parameterTypeCoersion} list.
     * @param element A parameterTypeCoersion element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addParameterTypeCoersion(ITypeCoercion element) {
      this.parameterTypeCoersion.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IMethodCognition#getParameterTypeCoersion() parameterTypeCoersion} list.
     * @param elements An array of parameterTypeCoersion elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addParameterTypeCoersion(ITypeCoercion... elements) {
      this.parameterTypeCoersion.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IMethodCognition#getParameterTypeCoersion() parameterTypeCoersion} list.
     * @param elements An iterable of parameterTypeCoersion elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("parameterTypeCoersion")
    public final Builder parameterTypeCoersion(Iterable<? extends ITypeCoercion> elements) {
      this.parameterTypeCoersion = ImmutableList.builder();
      return addAllParameterTypeCoersion(elements);
    }

    /**
     * Adds elements to {@link IMethodCognition#getParameterTypeCoersion() parameterTypeCoersion} list.
     * @param elements An iterable of parameterTypeCoersion elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllParameterTypeCoersion(Iterable<? extends ITypeCoercion> elements) {
      this.parameterTypeCoersion.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link MethodCognition MethodCognition}.
     * @return An immutable instance of MethodCognition
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public MethodCognition build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new MethodCognition(methodRef, signatureDesc, methodDesc, serviceDesc, parameterTypeCoersion.build());
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_METHOD_REF) != 0) attributes.add("methodRef");
      if ((initBits & INIT_BIT_SIGNATURE_DESC) != 0) attributes.add("signatureDesc");
      if ((initBits & INIT_BIT_METHOD_DESC) != 0) attributes.add("methodDesc");
      if ((initBits & INIT_BIT_SERVICE_DESC) != 0) attributes.add("serviceDesc");
      return "Cannot build MethodCognition, some of required attributes are not set " + attributes;
    }
  }
}
