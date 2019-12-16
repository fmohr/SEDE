package de.upb.sede.composition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IStaticInstAnalysis IStaticInstAnalysis} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableStaticInstAnalysis is not thread-safe</em>
 * @see StaticInstAnalysis
 */
@Generated(from = "IStaticInstAnalysis", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IStaticInstAnalysis"})
@NotThreadSafe
public final class MutableStaticInstAnalysis implements IStaticInstAnalysis {
  private static final long INIT_BIT_INSTRUCTION = 0x1L;
  private static final long INIT_BIT_METHOD_COGNITION = 0x2L;
  private long initBits = 0x3L;

  private IIndexedInstruction instruction;
  private final ArrayList<ITypeJournalPageModel> typeContext = new ArrayList<ITypeJournalPageModel>();
  private IMethodCognition methodCognition;
  private final ArrayList<IFieldAccess> fieldAccesses = new ArrayList<IFieldAccess>();

  private MutableStaticInstAnalysis() {}

  /**
   * Construct a modifiable instance of {@code IStaticInstAnalysis}.
   * @return A new modifiable instance
   */
  public static MutableStaticInstAnalysis create() {
    return new MutableStaticInstAnalysis();
  }

  /**
   * @return value of {@code instruction} attribute
   */
  @JsonProperty("instruction")
  @Override
  public final IIndexedInstruction getInstruction() {
    if (!instructionIsSet()) {
      checkRequiredAttributes();
    }
    return instruction;
  }

  /**
   * @return modifiable list {@code typeContext}
   */
  @JsonProperty("typeContext")
  @Override
  public final List<ITypeJournalPageModel> getTypeContext() {
    return typeContext;
  }

  /**
   * @return value of {@code methodCognition} attribute
   */
  @JsonProperty("methodCognition")
  @Override
  public final IMethodCognition getMethodCognition() {
    if (!methodCognitionIsSet()) {
      checkRequiredAttributes();
    }
    return methodCognition;
  }

  /**
   * @return modifiable list {@code fieldAccesses}
   */
  @JsonProperty("fieldAccesses")
  @Override
  public final List<IFieldAccess> getFieldAccesses() {
    return fieldAccesses;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableStaticInstAnalysis clear() {
    initBits = 0x3L;
    instruction = null;
    typeContext.clear();
    methodCognition = null;
    fieldAccesses.clear();
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IStaticInstAnalysis} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableStaticInstAnalysis from(IStaticInstAnalysis instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableStaticInstAnalysis) {
      from((MutableStaticInstAnalysis) instance);
      return this;
    }
    setInstruction(instance.getInstruction());
    addAllTypeContext(instance.getTypeContext());
    setMethodCognition(instance.getMethodCognition());
    addAllFieldAccesses(instance.getFieldAccesses());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IStaticInstAnalysis} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableStaticInstAnalysis from(MutableStaticInstAnalysis instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.instructionIsSet()) {
      setInstruction(instance.getInstruction());
    }
    addAllTypeContext(instance.getTypeContext());
    if (instance.methodCognitionIsSet()) {
      setMethodCognition(instance.getMethodCognition());
    }
    addAllFieldAccesses(instance.getFieldAccesses());
    return this;
  }

  /**
   * Assigns a value to the {@link IStaticInstAnalysis#getInstruction() instruction} attribute.
   * @param instruction The value for instruction
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableStaticInstAnalysis setInstruction(IIndexedInstruction instruction) {
    this.instruction = Objects.requireNonNull(instruction, "instruction");
    initBits &= ~INIT_BIT_INSTRUCTION;
    return this;
  }

  /**
   * Adds one element to {@link IStaticInstAnalysis#getTypeContext() typeContext} list.
   * @param element The typeContext element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableStaticInstAnalysis addTypeContext(ITypeJournalPageModel element) {
    Objects.requireNonNull(element, "typeContext element");
    this.typeContext.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IStaticInstAnalysis#getTypeContext() typeContext} list.
   * @param elements An array of typeContext elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableStaticInstAnalysis addTypeContext(ITypeJournalPageModel... elements) {
    for (ITypeJournalPageModel e : elements) {
      addTypeContext(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IStaticInstAnalysis#getTypeContext() typeContext} list.
   * @param elements An iterable of typeContext elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableStaticInstAnalysis setTypeContext(Iterable<? extends ITypeJournalPageModel> elements) {
    this.typeContext.clear();
    addAllTypeContext(elements);
    return this;
  }

  /**
   * Adds elements to {@link IStaticInstAnalysis#getTypeContext() typeContext} list.
   * @param elements An iterable of typeContext elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableStaticInstAnalysis addAllTypeContext(Iterable<? extends ITypeJournalPageModel> elements) {
    for (ITypeJournalPageModel e : elements) {
      addTypeContext(e);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IStaticInstAnalysis#getMethodCognition() methodCognition} attribute.
   * @param methodCognition The value for methodCognition
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableStaticInstAnalysis setMethodCognition(IMethodCognition methodCognition) {
    this.methodCognition = Objects.requireNonNull(methodCognition, "methodCognition");
    initBits &= ~INIT_BIT_METHOD_COGNITION;
    return this;
  }

  /**
   * Adds one element to {@link IStaticInstAnalysis#getFieldAccesses() fieldAccesses} list.
   * @param element The fieldAccesses element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableStaticInstAnalysis addFieldAccesses(IFieldAccess element) {
    Objects.requireNonNull(element, "fieldAccesses element");
    this.fieldAccesses.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IStaticInstAnalysis#getFieldAccesses() fieldAccesses} list.
   * @param elements An array of fieldAccesses elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableStaticInstAnalysis addFieldAccesses(IFieldAccess... elements) {
    for (IFieldAccess e : elements) {
      addFieldAccesses(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IStaticInstAnalysis#getFieldAccesses() fieldAccesses} list.
   * @param elements An iterable of fieldAccesses elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableStaticInstAnalysis setFieldAccesses(Iterable<? extends IFieldAccess> elements) {
    this.fieldAccesses.clear();
    addAllFieldAccesses(elements);
    return this;
  }

  /**
   * Adds elements to {@link IStaticInstAnalysis#getFieldAccesses() fieldAccesses} list.
   * @param elements An iterable of fieldAccesses elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableStaticInstAnalysis addAllFieldAccesses(Iterable<? extends IFieldAccess> elements) {
    for (IFieldAccess e : elements) {
      addFieldAccesses(e);
    }
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IStaticInstAnalysis#getInstruction() instruction} is set.
   * @return {@code true} if set
   */
  public final boolean instructionIsSet() {
    return (initBits & INIT_BIT_INSTRUCTION) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IStaticInstAnalysis#getMethodCognition() methodCognition} is set.
   * @return {@code true} if set
   */
  public final boolean methodCognitionIsSet() {
    return (initBits & INIT_BIT_METHOD_COGNITION) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableStaticInstAnalysis unsetInstruction() {
    initBits |= INIT_BIT_INSTRUCTION;
    instruction = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableStaticInstAnalysis unsetMethodCognition() {
    initBits |= INIT_BIT_METHOD_COGNITION;
    methodCognition = null;
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
    if (!instructionIsSet()) attributes.add("instruction");
    if (!methodCognitionIsSet()) attributes.add("methodCognition");
    return "StaticInstAnalysis is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link StaticInstAnalysis StaticInstAnalysis}.
   * @return An immutable instance of StaticInstAnalysis
   */
  public final StaticInstAnalysis toImmutable() {
    checkRequiredAttributes();
    return StaticInstAnalysis.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableStaticInstAnalysis} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableStaticInstAnalysis)) return false;
    MutableStaticInstAnalysis other = (MutableStaticInstAnalysis) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableStaticInstAnalysis another) {
    return instruction.equals(another.instruction)
        && typeContext.equals(another.typeContext)
        && methodCognition.equals(another.methodCognition)
        && fieldAccesses.equals(another.fieldAccesses);
  }

  /**
   * Computes a hash code from attributes: {@code instruction}, {@code typeContext}, {@code methodCognition}, {@code fieldAccesses}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + instruction.hashCode();
    h += (h << 5) + typeContext.hashCode();
    h += (h << 5) + methodCognition.hashCode();
    h += (h << 5) + fieldAccesses.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IStaticInstAnalysis}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableStaticInstAnalysis")
        .add("instruction", instructionIsSet() ? getInstruction() : "?")
        .add("typeContext", getTypeContext())
        .add("methodCognition", methodCognitionIsSet() ? getMethodCognition() : "?")
        .add("fieldAccesses", getFieldAccesses())
        .toString();
  }
}
