package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.ICommented;
import de.upb.sede.exec.aux.IJavaReflectionAux;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link ISignatureDesc ISignatureDesc} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableSignatureDesc is not thread-safe</em>
 * @see SignatureDesc
 */
@Generated(from = "ISignatureDesc", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "ISignatureDesc"})
@NotThreadSafe
public final class MutableSignatureDesc implements ISignatureDesc {
  private final ArrayList<IMethodParameterDesc> inputs = new ArrayList<IMethodParameterDesc>();
  private final ArrayList<IMethodParameterDesc> outputs = new ArrayList<IMethodParameterDesc>();
  private @Nullable IJavaReflectionAux javaAux;
  private final ArrayList<String> comments = new ArrayList<String>();

  private MutableSignatureDesc() {}

  /**
   * Construct a modifiable instance of {@code ISignatureDesc}.
   * @return A new modifiable instance
   */
  public static MutableSignatureDesc create() {
    return new MutableSignatureDesc();
  }

  /**
   * @return modifiable list {@code inputs}
   */
  @JsonProperty("inputs")
  @Override
  public final List<IMethodParameterDesc> getInputs() {
    return inputs;
  }

  /**
   * @return modifiable list {@code outputs}
   */
  @JsonProperty("outputs")
  @Override
  public final List<IMethodParameterDesc> getOutputs() {
    return outputs;
  }

  /**
   * @return value of {@code javaAux} attribute, may be {@code null}
   */
  @JsonProperty("javaAux")
  @Override
  public final @Nullable IJavaReflectionAux getJavaAux() {
    return javaAux;
  }

  /**
   * @return modifiable list {@code comments}
   */
  @JsonProperty("comments")
  @Override
  public final List<String> getComments() {
    return comments;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSignatureDesc clear() {
    inputs.clear();
    outputs.clear();
    javaAux = null;
    comments.clear();
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.ICommented} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSignatureDesc from(ICommented instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.exec.ISignatureDesc} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSignatureDesc from(ISignatureDesc instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link ISignatureDesc} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableSignatureDesc from(MutableSignatureDesc instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableSignatureDesc) {
      MutableSignatureDesc instance = (MutableSignatureDesc) object;
      addAllInputs(instance.getInputs());
      addAllOutputs(instance.getOutputs());
      @Nullable IJavaReflectionAux javaAuxValue = instance.getJavaAux();
      if (javaAuxValue != null) {
        setJavaAux(javaAuxValue);
      }
      addAllComments(instance.getComments());
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
        setJavaAux(javaAuxValue);
      }
      addAllInputs(instance.getInputs());
    }
  }

  /**
   * Adds one element to {@link ISignatureDesc#getInputs() inputs} list.
   * @param element The inputs element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSignatureDesc addInputs(IMethodParameterDesc element) {
    Objects.requireNonNull(element, "inputs element");
    this.inputs.add(element);
    return this;
  }

  /**
   * Adds elements to {@link ISignatureDesc#getInputs() inputs} list.
   * @param elements An array of inputs elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableSignatureDesc addInputs(IMethodParameterDesc... elements) {
    for (IMethodParameterDesc e : elements) {
      addInputs(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link ISignatureDesc#getInputs() inputs} list.
   * @param elements An iterable of inputs elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSignatureDesc setInputs(Iterable<? extends IMethodParameterDesc> elements) {
    this.inputs.clear();
    addAllInputs(elements);
    return this;
  }

  /**
   * Adds elements to {@link ISignatureDesc#getInputs() inputs} list.
   * @param elements An iterable of inputs elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSignatureDesc addAllInputs(Iterable<? extends IMethodParameterDesc> elements) {
    for (IMethodParameterDesc e : elements) {
      addInputs(e);
    }
    return this;
  }

  /**
   * Adds one element to {@link ISignatureDesc#getOutputs() outputs} list.
   * @param element The outputs element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSignatureDesc addOutputs(IMethodParameterDesc element) {
    Objects.requireNonNull(element, "outputs element");
    this.outputs.add(element);
    return this;
  }

  /**
   * Adds elements to {@link ISignatureDesc#getOutputs() outputs} list.
   * @param elements An array of outputs elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableSignatureDesc addOutputs(IMethodParameterDesc... elements) {
    for (IMethodParameterDesc e : elements) {
      addOutputs(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link ISignatureDesc#getOutputs() outputs} list.
   * @param elements An iterable of outputs elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSignatureDesc setOutputs(Iterable<? extends IMethodParameterDesc> elements) {
    this.outputs.clear();
    addAllOutputs(elements);
    return this;
  }

  /**
   * Adds elements to {@link ISignatureDesc#getOutputs() outputs} list.
   * @param elements An iterable of outputs elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSignatureDesc addAllOutputs(Iterable<? extends IMethodParameterDesc> elements) {
    for (IMethodParameterDesc e : elements) {
      addOutputs(e);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link ISignatureDesc#getJavaAux() javaAux} attribute.
   * @param javaAux The value for javaAux, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSignatureDesc setJavaAux(@Nullable IJavaReflectionAux javaAux) {
    this.javaAux = javaAux;
    return this;
  }

  /**
   * Adds one element to {@link ISignatureDesc#getComments() comments} list.
   * @param element The comments element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSignatureDesc addComments(String element) {
    Objects.requireNonNull(element, "comments element");
    this.comments.add(element);
    return this;
  }

  /**
   * Adds elements to {@link ISignatureDesc#getComments() comments} list.
   * @param elements An array of comments elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableSignatureDesc addComments(String... elements) {
    for (String e : elements) {
      addComments(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link ISignatureDesc#getComments() comments} list.
   * @param elements An iterable of comments elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSignatureDesc setComments(Iterable<String> elements) {
    this.comments.clear();
    addAllComments(elements);
    return this;
  }

  /**
   * Adds elements to {@link ISignatureDesc#getComments() comments} list.
   * @param elements An iterable of comments elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSignatureDesc addAllComments(Iterable<String> elements) {
    for (String e : elements) {
      addComments(e);
    }
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
   * Converts to {@link SignatureDesc SignatureDesc}.
   * @return An immutable instance of SignatureDesc
   */
  public final SignatureDesc toImmutable() {
    return SignatureDesc.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableSignatureDesc} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableSignatureDesc)) return false;
    MutableSignatureDesc other = (MutableSignatureDesc) another;
    return equalTo(other);
  }

  private boolean equalTo(MutableSignatureDesc another) {
    return inputs.equals(another.inputs)
        && outputs.equals(another.outputs)
        && Objects.equals(javaAux, another.javaAux)
        && comments.equals(another.comments);
  }

  /**
   * Computes a hash code from attributes: {@code inputs}, {@code outputs}, {@code javaAux}, {@code comments}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + inputs.hashCode();
    h += (h << 5) + outputs.hashCode();
    h += (h << 5) + Objects.hashCode(javaAux);
    h += (h << 5) + comments.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code ISignatureDesc}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableSignatureDesc")
        .add("inputs", getInputs())
        .add("outputs", getOutputs())
        .add("javaAux", getJavaAux())
        .add("comments", getComments())
        .toString();
  }
}
