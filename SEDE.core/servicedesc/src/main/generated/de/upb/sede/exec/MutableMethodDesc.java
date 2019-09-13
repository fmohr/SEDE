package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.ICommented;
import de.upb.sede.IQualifiable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IMethodDesc IMethodDesc} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableMethodDesc is not thread-safe</em>
 * @see MethodDesc
 */
@Generated(from = "IMethodDesc", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IMethodDesc"})
@NotThreadSafe
public final class MutableMethodDesc implements IMethodDesc {
  private static final long INIT_BIT_QUALIFIER = 0x1L;
  private static final long OPT_BIT_IS_PURE = 0x1L;
  private long initBits = 0x1L;
  private long optBits;

  private final ArrayList<ISignatureDesc> signatures = new ArrayList<ISignatureDesc>();
  private boolean isPure;
  private String qualifier;
  private String simpleName;
  private final ArrayList<String> comments = new ArrayList<String>();

  private MutableMethodDesc() {}

  /**
   * Construct a modifiable instance of {@code IMethodDesc}.
   * @return A new modifiable instance
   */
  public static MutableMethodDesc create() {
    return new MutableMethodDesc();
  }

  /**
   * @return modifiable list {@code signatures}
   */
  @JsonProperty("signatures")
  @Override
  public final List<ISignatureDesc> getSignatures() {
    return signatures;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code isPure} attribute
   */
  @JsonProperty("isPure")
  @Override
  public final boolean isPure() {
    return isPureIsSet()
        ? isPure
        : IMethodDesc.super.isPure();
  }

  /**
   * @return value of {@code qualifier} attribute
   */
  @JsonProperty("qualifier")
  @Override
  public final String getQualifier() {
    if (!qualifierIsSet()) {
      checkRequiredAttributes();
    }
    return qualifier;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code simpleName} attribute
   */
  @JsonProperty("simpleName")
  @Override
  public final String getSimpleName() {
    return simpleNameIsSet()
        ? simpleName
        : IMethodDesc.super.getSimpleName();
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
  public MutableMethodDesc clear() {
    initBits = 0x1L;
    optBits = 0;
    signatures.clear();
    isPure = false;
    qualifier = null;
    simpleName = null;
    comments.clear();
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.ICommented} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodDesc from(ICommented instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.exec.IMethodDesc} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodDesc from(IMethodDesc instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.IQualifiable} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodDesc from(IQualifiable instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IMethodDesc} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableMethodDesc from(MutableMethodDesc instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableMethodDesc) {
      MutableMethodDesc instance = (MutableMethodDesc) object;
      addAllSignatures(instance.getSignatures());
      setIsPure(instance.isPure());
      if (instance.qualifierIsSet()) {
        setQualifier(instance.getQualifier());
      }
      setSimpleName(instance.getSimpleName());
      addAllComments(instance.getComments());
      return;
    }
    if (object instanceof ICommented) {
      ICommented instance = (ICommented) object;
      addAllComments(instance.getComments());
    }
    if (object instanceof IMethodDesc) {
      IMethodDesc instance = (IMethodDesc) object;
      setIsPure(instance.isPure());
      addAllSignatures(instance.getSignatures());
    }
    if (object instanceof IQualifiable) {
      IQualifiable instance = (IQualifiable) object;
      setSimpleName(instance.getSimpleName());
      setQualifier(instance.getQualifier());
    }
  }

  /**
   * Adds one element to {@link IMethodDesc#getSignatures() signatures} list.
   * @param element The signatures element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodDesc addSignatures(ISignatureDesc element) {
    Objects.requireNonNull(element, "signatures element");
    this.signatures.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IMethodDesc#getSignatures() signatures} list.
   * @param elements An array of signatures elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableMethodDesc addSignatures(ISignatureDesc... elements) {
    for (ISignatureDesc e : elements) {
      addSignatures(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IMethodDesc#getSignatures() signatures} list.
   * @param elements An iterable of signatures elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodDesc setSignatures(Iterable<? extends ISignatureDesc> elements) {
    this.signatures.clear();
    addAllSignatures(elements);
    return this;
  }

  /**
   * Adds elements to {@link IMethodDesc#getSignatures() signatures} list.
   * @param elements An iterable of signatures elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodDesc addAllSignatures(Iterable<? extends ISignatureDesc> elements) {
    for (ISignatureDesc e : elements) {
      addSignatures(e);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IMethodDesc#isPure() isPure} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IMethodDesc#isPure() isPure}.</em>
   * @param isPure The value for isPure
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodDesc setIsPure(boolean isPure) {
    this.isPure = isPure;
    optBits |= OPT_BIT_IS_PURE;
    return this;
  }

  /**
   * Assigns a value to the {@link IMethodDesc#getQualifier() qualifier} attribute.
   * @param qualifier The value for qualifier
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodDesc setQualifier(String qualifier) {
    this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
    initBits &= ~INIT_BIT_QUALIFIER;
    return this;
  }

  /**
   * Assigns a value to the {@link IMethodDesc#getSimpleName() simpleName} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IMethodDesc#getSimpleName() simpleName}.</em>
   * @param simpleName The value for simpleName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodDesc setSimpleName(String simpleName) {
    this.simpleName = Objects.requireNonNull(simpleName, "simpleName");
    return this;
  }

  /**
   * Adds one element to {@link IMethodDesc#getComments() comments} list.
   * @param element The comments element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodDesc addComments(String element) {
    Objects.requireNonNull(element, "comments element");
    this.comments.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IMethodDesc#getComments() comments} list.
   * @param elements An array of comments elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableMethodDesc addComments(String... elements) {
    for (String e : elements) {
      addComments(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IMethodDesc#getComments() comments} list.
   * @param elements An iterable of comments elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodDesc setComments(Iterable<String> elements) {
    this.comments.clear();
    addAllComments(elements);
    return this;
  }

  /**
   * Adds elements to {@link IMethodDesc#getComments() comments} list.
   * @param elements An iterable of comments elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableMethodDesc addAllComments(Iterable<String> elements) {
    for (String e : elements) {
      addComments(e);
    }
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IMethodDesc#getQualifier() qualifier} is set.
   * @return {@code true} if set
   */
  public final boolean qualifierIsSet() {
    return (initBits & INIT_BIT_QUALIFIER) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IMethodDesc#isPure() isPure} is set.
   * @return {@code true} if set
   */
  public final boolean isPureIsSet() {
    return (optBits & OPT_BIT_IS_PURE) != 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IMethodDesc#getSimpleName() simpleName} is set.
   * @return {@code true} if set
   */
  public final boolean simpleNameIsSet() {
    return simpleName != null;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableMethodDesc unsetQualifier() {
    initBits |= INIT_BIT_QUALIFIER;
    qualifier = null;
    return this;
  }
  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableMethodDesc unsetIsPure() {
    optBits |= 0;
    isPure = false;
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
    if (!qualifierIsSet()) attributes.add("qualifier");
    return "MethodDesc is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link MethodDesc MethodDesc}.
   * @return An immutable instance of MethodDesc
   */
  public final MethodDesc toImmutable() {
    checkRequiredAttributes();
    return MethodDesc.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableMethodDesc} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableMethodDesc)) return false;
    MutableMethodDesc other = (MutableMethodDesc) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableMethodDesc another) {
    boolean isPure = isPure();
    String simpleName = getSimpleName();
    return signatures.equals(another.signatures)
        && isPure == another.isPure()
        && qualifier.equals(another.qualifier)
        && simpleName.equals(another.getSimpleName())
        && comments.equals(another.comments);
  }

  /**
   * Computes a hash code from attributes: {@code signatures}, {@code isPure}, {@code qualifier}, {@code simpleName}, {@code comments}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + signatures.hashCode();
    boolean isPure = isPure();
    h += (h << 5) + Booleans.hashCode(isPure);
    h += (h << 5) + qualifier.hashCode();
    String simpleName = getSimpleName();
    h += (h << 5) + simpleName.hashCode();
    h += (h << 5) + comments.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IMethodDesc}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableMethodDesc")
        .add("signatures", getSignatures())
        .add("isPure", isPure())
        .add("qualifier", qualifierIsSet() ? getQualifier() : "?")
        .add("simpleName", getSimpleName())
        .add("comments", getComments())
        .toString();
  }
}
