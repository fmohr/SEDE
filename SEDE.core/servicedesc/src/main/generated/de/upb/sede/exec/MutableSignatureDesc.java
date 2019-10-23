package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.CommentAware;
import de.upb.sede.exec.auxiliary.IJavaDispatchAux;
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
  private static final long OPT_BIT_IS_PURE = 0x1L;
  private static final long OPT_BIT_IS_CONTEXT_FREE = 0x2L;
  private long optBits;

  private final ArrayList<IMethodParameterDesc> inputs = new ArrayList<IMethodParameterDesc>();
  private final ArrayList<IMethodParameterDesc> outputs = new ArrayList<IMethodParameterDesc>();
  private @Nullable IJavaDispatchAux javaDispatchAux;
  private boolean isPure;
  private boolean isContextFree;
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
   * @return value of {@code javaDispatchAux} attribute, may be {@code null}
   */
  @JsonProperty("javaDispatchAux")
  @Override
  public final @Nullable IJavaDispatchAux getJavaDispatchAux() {
    return javaDispatchAux;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code isPure} attribute
   */
  @JsonProperty("isPure")
  @Override
  public final boolean isPure() {
    return isPureIsSet()
        ? isPure
        : ISignatureDesc.super.isPure();
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code isContextFree} attribute
   */
  @JsonProperty("isContextFree")
  @Override
  public final boolean isContextFree() {
    return isContextFreeIsSet()
        ? isContextFree
        : ISignatureDesc.super.isContextFree();
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
    optBits = 0;
    inputs.clear();
    outputs.clear();
    javaDispatchAux = null;
    isPure = false;
    isContextFree = false;
    comments.clear();
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
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.CommentAware} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSignatureDesc from(CommentAware instance) {
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
      @Nullable IJavaDispatchAux javaDispatchAuxValue = instance.getJavaDispatchAux();
      if (javaDispatchAuxValue != null) {
        setJavaDispatchAux(javaDispatchAuxValue);
      }
      setIsPure(instance.isPure());
      setIsContextFree(instance.isContextFree());
      addAllComments(instance.getComments());
      return;
    }
    if (object instanceof ISignatureDesc) {
      ISignatureDesc instance = (ISignatureDesc) object;
      addAllOutputs(instance.getOutputs());
      @Nullable IJavaDispatchAux javaDispatchAuxValue = instance.getJavaDispatchAux();
      if (javaDispatchAuxValue != null) {
        setJavaDispatchAux(javaDispatchAuxValue);
      }
      setIsPure(instance.isPure());
      setIsContextFree(instance.isContextFree());
      addAllInputs(instance.getInputs());
    }
    if (object instanceof CommentAware) {
      CommentAware instance = (CommentAware) object;
      addAllComments(instance.getComments());
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
   * Assigns a value to the {@link ISignatureDesc#getJavaDispatchAux() javaDispatchAux} attribute.
   * @param javaDispatchAux The value for javaDispatchAux, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSignatureDesc setJavaDispatchAux(@Nullable IJavaDispatchAux javaDispatchAux) {
    this.javaDispatchAux = javaDispatchAux;
    return this;
  }

  /**
   * Assigns a value to the {@link ISignatureDesc#isPure() isPure} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link ISignatureDesc#isPure() isPure}.</em>
   * @param isPure The value for isPure
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSignatureDesc setIsPure(boolean isPure) {
    this.isPure = isPure;
    optBits |= OPT_BIT_IS_PURE;
    return this;
  }

  /**
   * Assigns a value to the {@link ISignatureDesc#isContextFree() isContextFree} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link ISignatureDesc#isContextFree() isContextFree}.</em>
   * @param isContextFree The value for isContextFree
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSignatureDesc setIsContextFree(boolean isContextFree) {
    this.isContextFree = isContextFree;
    optBits |= OPT_BIT_IS_CONTEXT_FREE;
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
   * Returns {@code true} if the default attribute {@link ISignatureDesc#isPure() isPure} is set.
   * @return {@code true} if set
   */
  public final boolean isPureIsSet() {
    return (optBits & OPT_BIT_IS_PURE) != 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link ISignatureDesc#isContextFree() isContextFree} is set.
   * @return {@code true} if set
   */
  public final boolean isContextFreeIsSet() {
    return (optBits & OPT_BIT_IS_CONTEXT_FREE) != 0;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableSignatureDesc unsetIsPure() {
    optBits |= 0;
    isPure = false;
    return this;
  }
  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableSignatureDesc unsetIsContextFree() {
    optBits |= 0;
    isContextFree = false;
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
    boolean isPure = isPure();
    boolean isContextFree = isContextFree();
    return inputs.equals(another.inputs)
        && outputs.equals(another.outputs)
        && Objects.equals(javaDispatchAux, another.javaDispatchAux)
        && isPure == another.isPure()
        && isContextFree == another.isContextFree()
        && comments.equals(another.comments);
  }

  /**
   * Computes a hash code from attributes: {@code inputs}, {@code outputs}, {@code javaDispatchAux}, {@code isPure}, {@code isContextFree}, {@code comments}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + inputs.hashCode();
    h += (h << 5) + outputs.hashCode();
    h += (h << 5) + Objects.hashCode(javaDispatchAux);
    boolean isPure = isPure();
    h += (h << 5) + Booleans.hashCode(isPure);
    boolean isContextFree = isContextFree();
    h += (h << 5) + Booleans.hashCode(isContextFree);
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
        .add("javaDispatchAux", getJavaDispatchAux())
        .add("isPure", isPure())
        .add("isContextFree", isContextFree())
        .add("comments", getComments())
        .toString();
  }
}
