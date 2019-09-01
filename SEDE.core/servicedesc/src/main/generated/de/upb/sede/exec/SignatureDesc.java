package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.IComment;
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
  private final ImmutableList<IVariableDesc> inputs;
  private final ImmutableList<IVariableDesc> outputs;
  private final ImmutableList<String> comments;

  private SignatureDesc(
      ImmutableList<IVariableDesc> inputs,
      ImmutableList<IVariableDesc> outputs,
      ImmutableList<String> comments) {
    this.inputs = inputs;
    this.outputs = outputs;
    this.comments = comments;
  }

  /**
   * @return The value of the {@code inputs} attribute
   */
  @JsonProperty("inputs")
  @Override
  public ImmutableList<IVariableDesc> getInputs() {
    return inputs;
  }

  /**
   * @return The value of the {@code outputs} attribute
   */
  @JsonProperty("outputs")
  @Override
  public ImmutableList<IVariableDesc> getOutputs() {
    return outputs;
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
  public final SignatureDesc withInputs(IVariableDesc... elements) {
    ImmutableList<IVariableDesc> newValue = ImmutableList.copyOf(elements);
    return new SignatureDesc(newValue, this.outputs, this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ISignatureDesc#getInputs() inputs}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of inputs elements to set
   * @return A modified copy of {@code this} object
   */
  public final SignatureDesc withInputs(Iterable<? extends IVariableDesc> elements) {
    if (this.inputs == elements) return this;
    ImmutableList<IVariableDesc> newValue = ImmutableList.copyOf(elements);
    return new SignatureDesc(newValue, this.outputs, this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ISignatureDesc#getOutputs() outputs}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final SignatureDesc withOutputs(IVariableDesc... elements) {
    ImmutableList<IVariableDesc> newValue = ImmutableList.copyOf(elements);
    return new SignatureDesc(this.inputs, newValue, this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ISignatureDesc#getOutputs() outputs}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of outputs elements to set
   * @return A modified copy of {@code this} object
   */
  public final SignatureDesc withOutputs(Iterable<? extends IVariableDesc> elements) {
    if (this.outputs == elements) return this;
    ImmutableList<IVariableDesc> newValue = ImmutableList.copyOf(elements);
    return new SignatureDesc(this.inputs, newValue, this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ISignatureDesc#getComments() comments}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final SignatureDesc withComments(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new SignatureDesc(this.inputs, this.outputs, newValue);
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
    return new SignatureDesc(this.inputs, this.outputs, newValue);
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
        && comments.equals(another.comments);
  }

  /**
   * Computes a hash code from attributes: {@code inputs}, {@code outputs}, {@code comments}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + inputs.hashCode();
    h += (h << 5) + outputs.hashCode();
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
    @Nullable List<IVariableDesc> inputs = ImmutableList.of();
    @Nullable List<IVariableDesc> outputs = ImmutableList.of();
    @Nullable List<String> comments = ImmutableList.of();
    @JsonProperty("inputs")
    public void setInputs(List<IVariableDesc> inputs) {
      this.inputs = inputs;
    }
    @JsonProperty("outputs")
    public void setOutputs(List<IVariableDesc> outputs) {
      this.outputs = outputs;
    }
    @JsonProperty("comments")
    public void setComments(List<String> comments) {
      this.comments = comments;
    }
    @Override
    public List<IVariableDesc> getInputs() { throw new UnsupportedOperationException(); }
    @Override
    public List<IVariableDesc> getOutputs() { throw new UnsupportedOperationException(); }
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
   *    .addInputs|addAllInputs(de.upb.sede.exec.IVariableDesc) // {@link ISignatureDesc#getInputs() inputs} elements
   *    .addOutputs|addAllOutputs(de.upb.sede.exec.IVariableDesc) // {@link ISignatureDesc#getOutputs() outputs} elements
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
    private ImmutableList.Builder<IVariableDesc> inputs = ImmutableList.builder();
    private ImmutableList.Builder<IVariableDesc> outputs = ImmutableList.builder();
    private ImmutableList.Builder<String> comments = ImmutableList.builder();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.IComment} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IComment instance) {
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
      if (object instanceof IComment) {
        IComment instance = (IComment) object;
        addAllComments(instance.getComments());
      }
      if (object instanceof ISignatureDesc) {
        ISignatureDesc instance = (ISignatureDesc) object;
        addAllOutputs(instance.getOutputs());
        addAllInputs(instance.getInputs());
      }
    }

    /**
     * Adds one element to {@link ISignatureDesc#getInputs() inputs} list.
     * @param element A inputs element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addInputs(IVariableDesc element) {
      this.inputs.add(element);
      return this;
    }

    /**
     * Adds elements to {@link ISignatureDesc#getInputs() inputs} list.
     * @param elements An array of inputs elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addInputs(IVariableDesc... elements) {
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
    public final Builder inputs(Iterable<? extends IVariableDesc> elements) {
      this.inputs = ImmutableList.builder();
      return addAllInputs(elements);
    }

    /**
     * Adds elements to {@link ISignatureDesc#getInputs() inputs} list.
     * @param elements An iterable of inputs elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllInputs(Iterable<? extends IVariableDesc> elements) {
      this.inputs.addAll(elements);
      return this;
    }

    /**
     * Adds one element to {@link ISignatureDesc#getOutputs() outputs} list.
     * @param element A outputs element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addOutputs(IVariableDesc element) {
      this.outputs.add(element);
      return this;
    }

    /**
     * Adds elements to {@link ISignatureDesc#getOutputs() outputs} list.
     * @param elements An array of outputs elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addOutputs(IVariableDesc... elements) {
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
    public final Builder outputs(Iterable<? extends IVariableDesc> elements) {
      this.outputs = ImmutableList.builder();
      return addAllOutputs(elements);
    }

    /**
     * Adds elements to {@link ISignatureDesc#getOutputs() outputs} list.
     * @param elements An iterable of outputs elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllOutputs(Iterable<? extends IVariableDesc> elements) {
      this.outputs.addAll(elements);
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
      return new SignatureDesc(inputs.build(), outputs.build(), comments.build());
    }
  }
}
