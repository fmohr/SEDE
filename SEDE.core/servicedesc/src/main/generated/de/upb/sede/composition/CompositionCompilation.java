package de.upb.sede.composition;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Longs;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link ICompositionCompilation}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code CompositionCompilation.builder()}.
 */
@Generated(from = "ICompositionCompilation", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class CompositionCompilation implements ICompositionCompilation {
  private final ImmutableList<Long> programOrder;
  private final ImmutableList<IStaticInstAnalysis> instructionAnalysis;

  private CompositionCompilation(
      ImmutableList<Long> programOrder,
      ImmutableList<IStaticInstAnalysis> instructionAnalysis) {
    this.programOrder = programOrder;
    this.instructionAnalysis = instructionAnalysis;
  }

  /**
   * @return The value of the {@code programOrder} attribute
   */
  @JsonProperty("programOrder")
  @Override
  public ImmutableList<Long> getProgramOrder() {
    return programOrder;
  }

  /**
   * @return The value of the {@code instructionAnalysis} attribute
   */
  @JsonProperty("instructionAnalysis")
  @Override
  public ImmutableList<IStaticInstAnalysis> getInstructionAnalysis() {
    return instructionAnalysis;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ICompositionCompilation#getProgramOrder() programOrder}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final CompositionCompilation withProgramOrder(long... elements) {
    ImmutableList<Long> newValue = ImmutableList.copyOf(Longs.asList(elements));
    return new CompositionCompilation(newValue, this.instructionAnalysis);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ICompositionCompilation#getProgramOrder() programOrder}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of programOrder elements to set
   * @return A modified copy of {@code this} object
   */
  public final CompositionCompilation withProgramOrder(Iterable<Long> elements) {
    if (this.programOrder == elements) return this;
    ImmutableList<Long> newValue = ImmutableList.copyOf(elements);
    return new CompositionCompilation(newValue, this.instructionAnalysis);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ICompositionCompilation#getInstructionAnalysis() instructionAnalysis}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final CompositionCompilation withInstructionAnalysis(IStaticInstAnalysis... elements) {
    ImmutableList<IStaticInstAnalysis> newValue = ImmutableList.copyOf(elements);
    return new CompositionCompilation(this.programOrder, newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ICompositionCompilation#getInstructionAnalysis() instructionAnalysis}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of instructionAnalysis elements to set
   * @return A modified copy of {@code this} object
   */
  public final CompositionCompilation withInstructionAnalysis(Iterable<? extends IStaticInstAnalysis> elements) {
    if (this.instructionAnalysis == elements) return this;
    ImmutableList<IStaticInstAnalysis> newValue = ImmutableList.copyOf(elements);
    return new CompositionCompilation(this.programOrder, newValue);
  }

  /**
   * This instance is equal to all instances of {@code CompositionCompilation} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof CompositionCompilation
        && equalTo((CompositionCompilation) another);
  }

  private boolean equalTo(CompositionCompilation another) {
    return programOrder.equals(another.programOrder)
        && instructionAnalysis.equals(another.instructionAnalysis);
  }

  /**
   * Computes a hash code from attributes: {@code programOrder}, {@code instructionAnalysis}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + programOrder.hashCode();
    h += (h << 5) + instructionAnalysis.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code CompositionCompilation} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("CompositionCompilation")
        .omitNullValues()
        .add("programOrder", programOrder)
        .add("instructionAnalysis", instructionAnalysis)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "ICompositionCompilation", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements ICompositionCompilation {
    @Nullable List<Long> programOrder = ImmutableList.of();
    @Nullable List<IStaticInstAnalysis> instructionAnalysis = ImmutableList.of();
    @JsonProperty("programOrder")
    public void setProgramOrder(List<Long> programOrder) {
      this.programOrder = programOrder;
    }
    @JsonProperty("instructionAnalysis")
    public void setInstructionAnalysis(List<IStaticInstAnalysis> instructionAnalysis) {
      this.instructionAnalysis = instructionAnalysis;
    }
    @Override
    public List<Long> getProgramOrder() { throw new UnsupportedOperationException(); }
    @Override
    public List<IStaticInstAnalysis> getInstructionAnalysis() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static CompositionCompilation fromJson(Json json) {
    CompositionCompilation.Builder builder = CompositionCompilation.builder();
    if (json.programOrder != null) {
      builder.addAllProgramOrder(json.programOrder);
    }
    if (json.instructionAnalysis != null) {
      builder.addAllInstructionAnalysis(json.instructionAnalysis);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link ICompositionCompilation} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable CompositionCompilation instance
   */
  public static CompositionCompilation copyOf(ICompositionCompilation instance) {
    if (instance instanceof CompositionCompilation) {
      return (CompositionCompilation) instance;
    }
    return CompositionCompilation.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link CompositionCompilation CompositionCompilation}.
   * <pre>
   * CompositionCompilation.builder()
   *    .addProgramOrder|addAllProgramOrder(long) // {@link ICompositionCompilation#getProgramOrder() programOrder} elements
   *    .addInstructionAnalysis|addAllInstructionAnalysis(de.upb.sede.composition.IStaticInstAnalysis) // {@link ICompositionCompilation#getInstructionAnalysis() instructionAnalysis} elements
   *    .build();
   * </pre>
   * @return A new CompositionCompilation builder
   */
  public static CompositionCompilation.Builder builder() {
    return new CompositionCompilation.Builder();
  }

  /**
   * Builds instances of type {@link CompositionCompilation CompositionCompilation}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "ICompositionCompilation", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private ImmutableList.Builder<Long> programOrder = ImmutableList.builder();
    private ImmutableList.Builder<IStaticInstAnalysis> instructionAnalysis = ImmutableList.builder();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableCompositionCompilation} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableCompositionCompilation instance) {
      Objects.requireNonNull(instance, "instance");
      addAllProgramOrder(instance.getProgramOrder());
      addAllInstructionAnalysis(instance.getInstructionAnalysis());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code ICompositionCompilation} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(ICompositionCompilation instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableCompositionCompilation) {
        return from((MutableCompositionCompilation) instance);
      }
      addAllProgramOrder(instance.getProgramOrder());
      addAllInstructionAnalysis(instance.getInstructionAnalysis());
      return this;
    }

    /**
     * Adds one element to {@link ICompositionCompilation#getProgramOrder() programOrder} list.
     * @param element A programOrder element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addProgramOrder(long element) {
      this.programOrder.add(element);
      return this;
    }

    /**
     * Adds elements to {@link ICompositionCompilation#getProgramOrder() programOrder} list.
     * @param elements An array of programOrder elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addProgramOrder(long... elements) {
      this.programOrder.addAll(Longs.asList(elements));
      return this;
    }


    /**
     * Sets or replaces all elements for {@link ICompositionCompilation#getProgramOrder() programOrder} list.
     * @param elements An iterable of programOrder elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("programOrder")
    public final Builder programOrder(Iterable<Long> elements) {
      this.programOrder = ImmutableList.builder();
      return addAllProgramOrder(elements);
    }

    /**
     * Adds elements to {@link ICompositionCompilation#getProgramOrder() programOrder} list.
     * @param elements An iterable of programOrder elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllProgramOrder(Iterable<Long> elements) {
      this.programOrder.addAll(elements);
      return this;
    }

    /**
     * Adds one element to {@link ICompositionCompilation#getInstructionAnalysis() instructionAnalysis} list.
     * @param element A instructionAnalysis element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addInstructionAnalysis(IStaticInstAnalysis element) {
      this.instructionAnalysis.add(element);
      return this;
    }

    /**
     * Adds elements to {@link ICompositionCompilation#getInstructionAnalysis() instructionAnalysis} list.
     * @param elements An array of instructionAnalysis elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addInstructionAnalysis(IStaticInstAnalysis... elements) {
      this.instructionAnalysis.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link ICompositionCompilation#getInstructionAnalysis() instructionAnalysis} list.
     * @param elements An iterable of instructionAnalysis elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("instructionAnalysis")
    public final Builder instructionAnalysis(Iterable<? extends IStaticInstAnalysis> elements) {
      this.instructionAnalysis = ImmutableList.builder();
      return addAllInstructionAnalysis(elements);
    }

    /**
     * Adds elements to {@link ICompositionCompilation#getInstructionAnalysis() instructionAnalysis} list.
     * @param elements An iterable of instructionAnalysis elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllInstructionAnalysis(Iterable<? extends IStaticInstAnalysis> elements) {
      this.instructionAnalysis.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link CompositionCompilation CompositionCompilation}.
     * @return An immutable instance of CompositionCompilation
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public CompositionCompilation build() {
      return new CompositionCompilation(programOrder.build(), instructionAnalysis.build());
    }
  }
}
