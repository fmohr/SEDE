package de.upb.sede.composition;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
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
 * Immutable implementation of {@link IStaticInstAnalysis}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code StaticInstAnalysis.builder()}.
 */
@Generated(from = "IStaticInstAnalysis", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class StaticInstAnalysis implements IStaticInstAnalysis {
  private final IIndexedInstruction instruction;
  private final ImmutableList<ITypeJournalPageModel> typeContext;
  private final IMethodCognition methodCognition;
  private final ImmutableList<IFieldAccess> fieldAccesses;

  private StaticInstAnalysis(
      IIndexedInstruction instruction,
      ImmutableList<ITypeJournalPageModel> typeContext,
      IMethodCognition methodCognition,
      ImmutableList<IFieldAccess> fieldAccesses) {
    this.instruction = instruction;
    this.typeContext = typeContext;
    this.methodCognition = methodCognition;
    this.fieldAccesses = fieldAccesses;
  }

  /**
   * @return The value of the {@code instruction} attribute
   */
  @JsonProperty("instruction")
  @Override
  public IIndexedInstruction getInstruction() {
    return instruction;
  }

  /**
   * @return The value of the {@code typeContext} attribute
   */
  @JsonProperty("typeContext")
  @Override
  public ImmutableList<ITypeJournalPageModel> getTypeContext() {
    return typeContext;
  }

  /**
   * @return The value of the {@code methodCognition} attribute
   */
  @JsonProperty("methodCognition")
  @Override
  public IMethodCognition getMethodCognition() {
    return methodCognition;
  }

  /**
   * @return The value of the {@code fieldAccesses} attribute
   */
  @JsonProperty("fieldAccesses")
  @Override
  public ImmutableList<IFieldAccess> getFieldAccesses() {
    return fieldAccesses;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IStaticInstAnalysis#getInstruction() instruction} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for instruction
   * @return A modified copy of the {@code this} object
   */
  public final StaticInstAnalysis withInstruction(IIndexedInstruction value) {
    if (this.instruction == value) return this;
    IIndexedInstruction newValue = Objects.requireNonNull(value, "instruction");
    return new StaticInstAnalysis(newValue, this.typeContext, this.methodCognition, this.fieldAccesses);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IStaticInstAnalysis#getTypeContext() typeContext}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final StaticInstAnalysis withTypeContext(ITypeJournalPageModel... elements) {
    ImmutableList<ITypeJournalPageModel> newValue = ImmutableList.copyOf(elements);
    return new StaticInstAnalysis(this.instruction, newValue, this.methodCognition, this.fieldAccesses);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IStaticInstAnalysis#getTypeContext() typeContext}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of typeContext elements to set
   * @return A modified copy of {@code this} object
   */
  public final StaticInstAnalysis withTypeContext(Iterable<? extends ITypeJournalPageModel> elements) {
    if (this.typeContext == elements) return this;
    ImmutableList<ITypeJournalPageModel> newValue = ImmutableList.copyOf(elements);
    return new StaticInstAnalysis(this.instruction, newValue, this.methodCognition, this.fieldAccesses);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IStaticInstAnalysis#getMethodCognition() methodCognition} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for methodCognition
   * @return A modified copy of the {@code this} object
   */
  public final StaticInstAnalysis withMethodCognition(IMethodCognition value) {
    if (this.methodCognition == value) return this;
    IMethodCognition newValue = Objects.requireNonNull(value, "methodCognition");
    return new StaticInstAnalysis(this.instruction, this.typeContext, newValue, this.fieldAccesses);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IStaticInstAnalysis#getFieldAccesses() fieldAccesses}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final StaticInstAnalysis withFieldAccesses(IFieldAccess... elements) {
    ImmutableList<IFieldAccess> newValue = ImmutableList.copyOf(elements);
    return new StaticInstAnalysis(this.instruction, this.typeContext, this.methodCognition, newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IStaticInstAnalysis#getFieldAccesses() fieldAccesses}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of fieldAccesses elements to set
   * @return A modified copy of {@code this} object
   */
  public final StaticInstAnalysis withFieldAccesses(Iterable<? extends IFieldAccess> elements) {
    if (this.fieldAccesses == elements) return this;
    ImmutableList<IFieldAccess> newValue = ImmutableList.copyOf(elements);
    return new StaticInstAnalysis(this.instruction, this.typeContext, this.methodCognition, newValue);
  }

  /**
   * This instance is equal to all instances of {@code StaticInstAnalysis} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof StaticInstAnalysis
        && equalTo((StaticInstAnalysis) another);
  }

  private boolean equalTo(StaticInstAnalysis another) {
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
    @Var int h = 5381;
    h += (h << 5) + instruction.hashCode();
    h += (h << 5) + typeContext.hashCode();
    h += (h << 5) + methodCognition.hashCode();
    h += (h << 5) + fieldAccesses.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code StaticInstAnalysis} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("StaticInstAnalysis")
        .omitNullValues()
        .add("instruction", instruction)
        .add("typeContext", typeContext)
        .add("methodCognition", methodCognition)
        .add("fieldAccesses", fieldAccesses)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IStaticInstAnalysis", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IStaticInstAnalysis {
    @Nullable IIndexedInstruction instruction;
    @Nullable List<ITypeJournalPageModel> typeContext = ImmutableList.of();
    @Nullable IMethodCognition methodCognition;
    @Nullable List<IFieldAccess> fieldAccesses = ImmutableList.of();
    @JsonProperty("instruction")
    public void setInstruction(IIndexedInstruction instruction) {
      this.instruction = instruction;
    }
    @JsonProperty("typeContext")
    public void setTypeContext(List<ITypeJournalPageModel> typeContext) {
      this.typeContext = typeContext;
    }
    @JsonProperty("methodCognition")
    public void setMethodCognition(IMethodCognition methodCognition) {
      this.methodCognition = methodCognition;
    }
    @JsonProperty("fieldAccesses")
    public void setFieldAccesses(List<IFieldAccess> fieldAccesses) {
      this.fieldAccesses = fieldAccesses;
    }
    @Override
    public IIndexedInstruction getInstruction() { throw new UnsupportedOperationException(); }
    @Override
    public List<ITypeJournalPageModel> getTypeContext() { throw new UnsupportedOperationException(); }
    @Override
    public IMethodCognition getMethodCognition() { throw new UnsupportedOperationException(); }
    @Override
    public List<IFieldAccess> getFieldAccesses() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static StaticInstAnalysis fromJson(Json json) {
    StaticInstAnalysis.Builder builder = StaticInstAnalysis.builder();
    if (json.instruction != null) {
      builder.instruction(json.instruction);
    }
    if (json.typeContext != null) {
      builder.addAllTypeContext(json.typeContext);
    }
    if (json.methodCognition != null) {
      builder.methodCognition(json.methodCognition);
    }
    if (json.fieldAccesses != null) {
      builder.addAllFieldAccesses(json.fieldAccesses);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IStaticInstAnalysis} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable StaticInstAnalysis instance
   */
  public static StaticInstAnalysis copyOf(IStaticInstAnalysis instance) {
    if (instance instanceof StaticInstAnalysis) {
      return (StaticInstAnalysis) instance;
    }
    return StaticInstAnalysis.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link StaticInstAnalysis StaticInstAnalysis}.
   * <pre>
   * StaticInstAnalysis.builder()
   *    .instruction(de.upb.sede.composition.IIndexedInstruction) // required {@link IStaticInstAnalysis#getInstruction() instruction}
   *    .addTypeContext|addAllTypeContext(de.upb.sede.composition.ITypeJournalPageModel) // {@link IStaticInstAnalysis#getTypeContext() typeContext} elements
   *    .methodCognition(de.upb.sede.composition.IMethodCognition) // required {@link IStaticInstAnalysis#getMethodCognition() methodCognition}
   *    .addFieldAccesses|addAllFieldAccesses(de.upb.sede.composition.IFieldAccess) // {@link IStaticInstAnalysis#getFieldAccesses() fieldAccesses} elements
   *    .build();
   * </pre>
   * @return A new StaticInstAnalysis builder
   */
  public static StaticInstAnalysis.Builder builder() {
    return new StaticInstAnalysis.Builder();
  }

  /**
   * Builds instances of type {@link StaticInstAnalysis StaticInstAnalysis}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IStaticInstAnalysis", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_INSTRUCTION = 0x1L;
    private static final long INIT_BIT_METHOD_COGNITION = 0x2L;
    private long initBits = 0x3L;

    private @Nullable IIndexedInstruction instruction;
    private ImmutableList.Builder<ITypeJournalPageModel> typeContext = ImmutableList.builder();
    private @Nullable IMethodCognition methodCognition;
    private ImmutableList.Builder<IFieldAccess> fieldAccesses = ImmutableList.builder();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableStaticInstAnalysis} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableStaticInstAnalysis instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.instructionIsSet()) {
        instruction(instance.getInstruction());
      }
      addAllTypeContext(instance.getTypeContext());
      if (instance.methodCognitionIsSet()) {
        methodCognition(instance.getMethodCognition());
      }
      addAllFieldAccesses(instance.getFieldAccesses());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IStaticInstAnalysis} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IStaticInstAnalysis instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableStaticInstAnalysis) {
        return from((MutableStaticInstAnalysis) instance);
      }
      instruction(instance.getInstruction());
      addAllTypeContext(instance.getTypeContext());
      methodCognition(instance.getMethodCognition());
      addAllFieldAccesses(instance.getFieldAccesses());
      return this;
    }

    /**
     * Initializes the value for the {@link IStaticInstAnalysis#getInstruction() instruction} attribute.
     * @param instruction The value for instruction 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("instruction")
    public final Builder instruction(IIndexedInstruction instruction) {
      this.instruction = Objects.requireNonNull(instruction, "instruction");
      initBits &= ~INIT_BIT_INSTRUCTION;
      return this;
    }

    /**
     * Adds one element to {@link IStaticInstAnalysis#getTypeContext() typeContext} list.
     * @param element A typeContext element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addTypeContext(ITypeJournalPageModel element) {
      this.typeContext.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IStaticInstAnalysis#getTypeContext() typeContext} list.
     * @param elements An array of typeContext elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addTypeContext(ITypeJournalPageModel... elements) {
      this.typeContext.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IStaticInstAnalysis#getTypeContext() typeContext} list.
     * @param elements An iterable of typeContext elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("typeContext")
    public final Builder typeContext(Iterable<? extends ITypeJournalPageModel> elements) {
      this.typeContext = ImmutableList.builder();
      return addAllTypeContext(elements);
    }

    /**
     * Adds elements to {@link IStaticInstAnalysis#getTypeContext() typeContext} list.
     * @param elements An iterable of typeContext elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllTypeContext(Iterable<? extends ITypeJournalPageModel> elements) {
      this.typeContext.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link IStaticInstAnalysis#getMethodCognition() methodCognition} attribute.
     * @param methodCognition The value for methodCognition 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("methodCognition")
    public final Builder methodCognition(IMethodCognition methodCognition) {
      this.methodCognition = Objects.requireNonNull(methodCognition, "methodCognition");
      initBits &= ~INIT_BIT_METHOD_COGNITION;
      return this;
    }

    /**
     * Adds one element to {@link IStaticInstAnalysis#getFieldAccesses() fieldAccesses} list.
     * @param element A fieldAccesses element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addFieldAccesses(IFieldAccess element) {
      this.fieldAccesses.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IStaticInstAnalysis#getFieldAccesses() fieldAccesses} list.
     * @param elements An array of fieldAccesses elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addFieldAccesses(IFieldAccess... elements) {
      this.fieldAccesses.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IStaticInstAnalysis#getFieldAccesses() fieldAccesses} list.
     * @param elements An iterable of fieldAccesses elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("fieldAccesses")
    public final Builder fieldAccesses(Iterable<? extends IFieldAccess> elements) {
      this.fieldAccesses = ImmutableList.builder();
      return addAllFieldAccesses(elements);
    }

    /**
     * Adds elements to {@link IStaticInstAnalysis#getFieldAccesses() fieldAccesses} list.
     * @param elements An iterable of fieldAccesses elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllFieldAccesses(Iterable<? extends IFieldAccess> elements) {
      this.fieldAccesses.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link StaticInstAnalysis StaticInstAnalysis}.
     * @return An immutable instance of StaticInstAnalysis
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public StaticInstAnalysis build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new StaticInstAnalysis(instruction, typeContext.build(), methodCognition, fieldAccesses.build());
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_INSTRUCTION) != 0) attributes.add("instruction");
      if ((initBits & INIT_BIT_METHOD_COGNITION) != 0) attributes.add("methodCognition");
      return "Cannot build StaticInstAnalysis, some of required attributes are not set " + attributes;
    }
  }
}
