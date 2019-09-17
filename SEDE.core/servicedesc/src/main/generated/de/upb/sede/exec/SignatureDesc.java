package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.ICommented;
import de.upb.sede.exec.aux.IJavaReflectionAux;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link ISignatureDesc}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code SignatureDesc.builder()}.
 */
@Generated(from = "ISignatureDesc", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class SignatureDesc implements ISignatureDesc {
  private final ImmutableList<IMethodParameterDesc> inputs;
  private final ImmutableList<IMethodParameterDesc> outputs;
  private final @Nullable IJavaReflectionAux javaAux;
  private final boolean isPure;
  private final ImmutableList<String> comments;

  private SignatureDesc(SignatureDesc.Builder builder) {
    this.inputs = builder.inputs.build();
    this.outputs = builder.outputs.build();
    this.javaAux = builder.javaAux;
    this.comments = builder.comments.build();
    this.isPure = builder.isPureIsSet()
        ? builder.isPure
        : ISignatureDesc.super.isPure();
  }

  private SignatureDesc(
      ImmutableList<IMethodParameterDesc> inputs,
      ImmutableList<IMethodParameterDesc> outputs,
      @Nullable IJavaReflectionAux javaAux,
      boolean isPure,
      ImmutableList<String> comments) {
    this.inputs = inputs;
    this.outputs = outputs;
    this.javaAux = javaAux;
    this.isPure = isPure;
    this.comments = comments;
  }

  /**
   * @return The value of the {@code inputs} attribute
   */
  @JsonProperty("inputs")
  @Override
  public ImmutableList<IMethodParameterDesc> getInputs() {
    return inputs;
  }

  /**
   * @return The value of the {@code outputs} attribute
   */
  @JsonProperty("outputs")
  @Override
  public ImmutableList<IMethodParameterDesc> getOutputs() {
    return outputs;
  }

  /**
   * @return The value of the {@code javaAux} attribute
   */
  @JsonProperty("javaAux")
  @Override
  public @Nullable IJavaReflectionAux getJavaAux() {
    return javaAux;
  }

  /**
   * @return The value of the {@code isPure} attribute
   */
  @JsonProperty("isPure")
  @Override
  public boolean isPure() {
    return isPure;
  }

  /**
   * @return The value of the {@code comments} attribute
   */
  @JsonProperty("comments")
  @Override
  public ImmutableList<String> getComments() {
    return comments;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ISignatureDesc#getInputs() inputs}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final SignatureDesc withInputs(IMethodParameterDesc... elements) {
    ImmutableList<IMethodParameterDesc> newValue = ImmutableList.copyOf(elements);
    return new SignatureDesc(newValue, this.outputs, this.javaAux, this.isPure, this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ISignatureDesc#getInputs() inputs}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of inputs elements to set
   * @return A modified copy of {@code this} object
   */
  public final SignatureDesc withInputs(Iterable<? extends IMethodParameterDesc> elements) {
    if (this.inputs == elements) return this;
    ImmutableList<IMethodParameterDesc> newValue = ImmutableList.copyOf(elements);
    return new SignatureDesc(newValue, this.outputs, this.javaAux, this.isPure, this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ISignatureDesc#getOutputs() outputs}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final SignatureDesc withOutputs(IMethodParameterDesc... elements) {
    ImmutableList<IMethodParameterDesc> newValue = ImmutableList.copyOf(elements);
    return new SignatureDesc(this.inputs, newValue, this.javaAux, this.isPure, this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ISignatureDesc#getOutputs() outputs}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of outputs elements to set
   * @return A modified copy of {@code this} object
   */
  public final SignatureDesc withOutputs(Iterable<? extends IMethodParameterDesc> elements) {
    if (this.outputs == elements) return this;
    ImmutableList<IMethodParameterDesc> newValue = ImmutableList.copyOf(elements);
    return new SignatureDesc(this.inputs, newValue, this.javaAux, this.isPure, this.comments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ISignatureDesc#getJavaAux() javaAux} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for javaAux (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final SignatureDesc withJavaAux(@Nullable IJavaReflectionAux value) {
    if (this.javaAux == value) return this;
    return new SignatureDesc(this.inputs, this.outputs, value, this.isPure, this.comments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ISignatureDesc#isPure() isPure} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isPure
   * @return A modified copy of the {@code this} object
   */
  public final SignatureDesc withIsPure(boolean value) {
    if (this.isPure == value) return this;
    return new SignatureDesc(this.inputs, this.outputs, this.javaAux, value, this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ISignatureDesc#getComments() comments}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final SignatureDesc withComments(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new SignatureDesc(this.inputs, this.outputs, this.javaAux, this.isPure, newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ISignatureDesc#getComments() comments}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of comments elements to set
   * @return A modified copy of {@code this} object
   */
  public final SignatureDesc withComments(Iterable<String> elements) {
    if (this.comments == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new SignatureDesc(this.inputs, this.outputs, this.javaAux, this.isPure, newValue);
  }

  /**
   * This instance is equal to all instances of {@code SignatureDesc} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof SignatureDesc
        && equalTo((SignatureDesc) another);
  }

  private boolean equalTo(SignatureDesc another) {
    return inputs.equals(another.inputs)
        && outputs.equals(another.outputs)
        && Objects.equals(javaAux, another.javaAux)
        && isPure == another.isPure
        && comments.equals(another.comments);
  }

  /**
   * Computes a hash code from attributes: {@code inputs}, {@code outputs}, {@code javaAux}, {@code isPure}, {@code comments}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + inputs.hashCode();
    h += (h << 5) + outputs.hashCode();
    h += (h << 5) + Objects.hashCode(javaAux);
    h += (h << 5) + Booleans.hashCode(isPure);
    h += (h << 5) + comments.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code SignatureDesc} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("SignatureDesc")
        .omitNullValues()
        .add("inputs", inputs)
        .add("outputs", outputs)
        .add("javaAux", javaAux)
        .add("isPure", isPure)
        .add("comments", comments)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "ISignatureDesc", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements ISignatureDesc {
    @Nullable List<IMethodParameterDesc> inputs = ImmutableList.of();
    @Nullable List<IMethodParameterDesc> outputs = ImmutableList.of();
    @Nullable IJavaReflectionAux javaAux;
    boolean isPure;
    boolean isPureIsSet;
    @Nullable List<String> comments = ImmutableList.of();
    @JsonProperty("inputs")
    public void setInputs(List<IMethodParameterDesc> inputs) {
      this.inputs = inputs;
    }
    @JsonProperty("outputs")
    public void setOutputs(List<IMethodParameterDesc> outputs) {
      this.outputs = outputs;
    }
    @JsonProperty("javaAux")
    public void setJavaAux(@Nullable IJavaReflectionAux javaAux) {
      this.javaAux = javaAux;
    }
    @JsonProperty("isPure")
    public void setIsPure(boolean isPure) {
      this.isPure = isPure;
      this.isPureIsSet = true;
    }
    @JsonProperty("comments")
    public void setComments(List<String> comments) {
      this.comments = comments;
    }
    @Override
    public List<IMethodParameterDesc> getInputs() { throw new UnsupportedOperationException(); }
    @Override
    public List<IMethodParameterDesc> getOutputs() { throw new UnsupportedOperationException(); }
    @Override
    public IJavaReflectionAux getJavaAux() { throw new UnsupportedOperationException(); }
    @Override
    public boolean isPure() { throw new UnsupportedOperationException(); }
    @Override
    public List<String> getComments() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static SignatureDesc fromJson(Json json) {
    SignatureDesc.Builder builder = SignatureDesc.builder();
    if (json.inputs != null) {
      builder.addAllInputs(json.inputs);
    }
    if (json.outputs != null) {
      builder.addAllOutputs(json.outputs);
    }
    if (json.javaAux != null) {
      builder.javaAux(json.javaAux);
    }
    if (json.isPureIsSet) {
      builder.isPure(json.isPure);
    }
    if (json.comments != null) {
      builder.addAllComments(json.comments);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link ISignatureDesc} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable SignatureDesc instance
   */
  public static SignatureDesc copyOf(ISignatureDesc instance) {
    if (instance instanceof SignatureDesc) {
      return (SignatureDesc) instance;
    }
    return SignatureDesc.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link SignatureDesc SignatureDesc}.
   * <pre>
   * SignatureDesc.builder()
   *    .addInputs|addAllInputs(de.upb.sede.exec.IMethodParameterDesc) // {@link ISignatureDesc#getInputs() inputs} elements
   *    .addOutputs|addAllOutputs(de.upb.sede.exec.IMethodParameterDesc) // {@link ISignatureDesc#getOutputs() outputs} elements
   *    .javaAux(de.upb.sede.exec.aux.IJavaReflectionAux | null) // nullable {@link ISignatureDesc#getJavaAux() javaAux}
   *    .isPure(boolean) // optional {@link ISignatureDesc#isPure() isPure}
   *    .addComments|addAllComments(String) // {@link ISignatureDesc#getComments() comments} elements
   *    .build();
   * </pre>
   * @return A new SignatureDesc builder
   */
  public static SignatureDesc.Builder builder() {
    return new SignatureDesc.Builder();
  }

  /**
   * Builds instances of type {@link SignatureDesc SignatureDesc}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "ISignatureDesc", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long OPT_BIT_IS_PURE = 0x1L;
    private long optBits;

    private ImmutableList.Builder<IMethodParameterDesc> inputs = ImmutableList.builder();
    private ImmutableList.Builder<IMethodParameterDesc> outputs = ImmutableList.builder();
    private @Nullable IJavaReflectionAux javaAux;
    private boolean isPure;
    private ImmutableList.Builder<String> comments = ImmutableList.builder();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableSignatureDesc} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableSignatureDesc instance) {
      Objects.requireNonNull(instance, "instance");
      addAllInputs(instance.getInputs());
      addAllOutputs(instance.getOutputs());
      @Nullable IJavaReflectionAux javaAuxValue = instance.getJavaAux();
      if (javaAuxValue != null) {
        javaAux(javaAuxValue);
      }
      isPure(instance.isPure());
      addAllComments(instance.getComments());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.ICommented} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(ICommented instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.exec.ISignatureDesc} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(ISignatureDesc instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof MutableSignatureDesc) {
        from((MutableSignatureDesc) object);
        return;
      }
      if (object instanceof ICommented) {
        ICommented instance = (ICommented) object;
        addAllComments(instance.getComments());
      }
      if (object instanceof ISignatureDesc) {
        ISignatureDesc instance = (ISignatureDesc) object;
        addAllOutputs(instance.getOutputs());
        @Nullable IJavaReflectionAux javaAuxValue = instance.getJavaAux();
        if (javaAuxValue != null) {
          javaAux(javaAuxValue);
        }
        isPure(instance.isPure());
        addAllInputs(instance.getInputs());
      }
    }

    /**
     * Adds one element to {@link ISignatureDesc#getInputs() inputs} list.
     * @param element A inputs element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addInputs(IMethodParameterDesc element) {
      this.inputs.add(element);
      return this;
    }

    /**
     * Adds elements to {@link ISignatureDesc#getInputs() inputs} list.
     * @param elements An array of inputs elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addInputs(IMethodParameterDesc... elements) {
      this.inputs.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link ISignatureDesc#getInputs() inputs} list.
     * @param elements An iterable of inputs elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("inputs")
    public final Builder inputs(Iterable<? extends IMethodParameterDesc> elements) {
      this.inputs = ImmutableList.builder();
      return addAllInputs(elements);
    }

    /**
     * Adds elements to {@link ISignatureDesc#getInputs() inputs} list.
     * @param elements An iterable of inputs elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllInputs(Iterable<? extends IMethodParameterDesc> elements) {
      this.inputs.addAll(elements);
      return this;
    }

    /**
     * Adds one element to {@link ISignatureDesc#getOutputs() outputs} list.
     * @param element A outputs element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addOutputs(IMethodParameterDesc element) {
      this.outputs.add(element);
      return this;
    }

    /**
     * Adds elements to {@link ISignatureDesc#getOutputs() outputs} list.
     * @param elements An array of outputs elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addOutputs(IMethodParameterDesc... elements) {
      this.outputs.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link ISignatureDesc#getOutputs() outputs} list.
     * @param elements An iterable of outputs elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("outputs")
    public final Builder outputs(Iterable<? extends IMethodParameterDesc> elements) {
      this.outputs = ImmutableList.builder();
      return addAllOutputs(elements);
    }

    /**
     * Adds elements to {@link ISignatureDesc#getOutputs() outputs} list.
     * @param elements An iterable of outputs elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllOutputs(Iterable<? extends IMethodParameterDesc> elements) {
      this.outputs.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link ISignatureDesc#getJavaAux() javaAux} attribute.
     * @param javaAux The value for javaAux (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("javaAux")
    public final Builder javaAux(@Nullable IJavaReflectionAux javaAux) {
      this.javaAux = javaAux;
      return this;
    }

    /**
     * Initializes the value for the {@link ISignatureDesc#isPure() isPure} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link ISignatureDesc#isPure() isPure}.</em>
     * @param isPure The value for isPure 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("isPure")
    public final Builder isPure(boolean isPure) {
      this.isPure = isPure;
      optBits |= OPT_BIT_IS_PURE;
      return this;
    }

    /**
     * Adds one element to {@link ISignatureDesc#getComments() comments} list.
     * @param element A comments element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addComments(String element) {
      this.comments.add(element);
      return this;
    }

    /**
     * Adds elements to {@link ISignatureDesc#getComments() comments} list.
     * @param elements An array of comments elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addComments(String... elements) {
      this.comments.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link ISignatureDesc#getComments() comments} list.
     * @param elements An iterable of comments elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("comments")
    public final Builder comments(Iterable<String> elements) {
      this.comments = ImmutableList.builder();
      return addAllComments(elements);
    }

    /**
     * Adds elements to {@link ISignatureDesc#getComments() comments} list.
     * @param elements An iterable of comments elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllComments(Iterable<String> elements) {
      this.comments.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link SignatureDesc SignatureDesc}.
     * @return An immutable instance of SignatureDesc
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public SignatureDesc build() {
      return new SignatureDesc(this);
    }

    private boolean isPureIsSet() {
      return (optBits & OPT_BIT_IS_PURE) != 0;
    }
  }
}
