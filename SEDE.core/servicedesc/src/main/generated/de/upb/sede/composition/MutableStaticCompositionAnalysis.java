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
 * A modifiable implementation of the {@link IStaticCompositionAnalysis IStaticCompositionAnalysis} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableStaticCompositionAnalysis is not thread-safe</em>
 * @see StaticCompositionAnalysis
 */
@Generated(from = "IStaticCompositionAnalysis", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IStaticCompositionAnalysis"})
@NotThreadSafe
public final class MutableStaticCompositionAnalysis
    implements IStaticCompositionAnalysis {
  private final ArrayList<Long> programOrder = new ArrayList<Long>();
  private final ArrayList<IStaticInstAnalysis> instructionAnalysis = new ArrayList<IStaticInstAnalysis>();

  private MutableStaticCompositionAnalysis() {}

  /**
   * Construct a modifiable instance of {@code IStaticCompositionAnalysis}.
   * @return A new modifiable instance
   */
  public static MutableStaticCompositionAnalysis create() {
    return new MutableStaticCompositionAnalysis();
  }

  /**
   * @return modifiable list {@code programOrder}
   */
  @JsonProperty("programOrder")
  @Override
  public final List<Long> getProgramOrder() {
    return programOrder;
  }

  /**
   * @return modifiable list {@code instructionAnalysis}
   */
  @JsonProperty("instructionAnalysis")
  @Override
  public final List<IStaticInstAnalysis> getInstructionAnalysis() {
    return instructionAnalysis;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableStaticCompositionAnalysis clear() {
    programOrder.clear();
    instructionAnalysis.clear();
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IStaticCompositionAnalysis} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableStaticCompositionAnalysis from(IStaticCompositionAnalysis instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableStaticCompositionAnalysis) {
      from((MutableStaticCompositionAnalysis) instance);
      return this;
    }
    addAllProgramOrder(instance.getProgramOrder());
    addAllInstructionAnalysis(instance.getInstructionAnalysis());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IStaticCompositionAnalysis} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableStaticCompositionAnalysis from(MutableStaticCompositionAnalysis instance) {
    Objects.requireNonNull(instance, "instance");
    addAllProgramOrder(instance.getProgramOrder());
    addAllInstructionAnalysis(instance.getInstructionAnalysis());
    return this;
  }

  /**
   * Adds one element to {@link IStaticCompositionAnalysis#getProgramOrder() programOrder} list.
   * @param element The programOrder element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableStaticCompositionAnalysis addProgramOrder(long element) {
    this.programOrder.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IStaticCompositionAnalysis#getProgramOrder() programOrder} list.
   * @param elements An array of programOrder elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableStaticCompositionAnalysis addProgramOrder(long... elements) {
    for (long e : elements) {
      addProgramOrder(Objects.requireNonNull(e, "programOrder element"));
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IStaticCompositionAnalysis#getProgramOrder() programOrder} list.
   * @param elements An iterable of programOrder elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableStaticCompositionAnalysis setProgramOrder(Iterable<Long> elements) {
    this.programOrder.clear();
    addAllProgramOrder(elements);
    return this;
  }

  /**
   * Adds elements to {@link IStaticCompositionAnalysis#getProgramOrder() programOrder} list.
   * @param elements An iterable of programOrder elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableStaticCompositionAnalysis addAllProgramOrder(Iterable<Long> elements) {
    for (long e : elements) {
      addProgramOrder(e);
    }
    return this;
  }

  /**
   * Adds one element to {@link IStaticCompositionAnalysis#getInstructionAnalysis() instructionAnalysis} list.
   * @param element The instructionAnalysis element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableStaticCompositionAnalysis addInstructionAnalysis(IStaticInstAnalysis element) {
    Objects.requireNonNull(element, "instructionAnalysis element");
    this.instructionAnalysis.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IStaticCompositionAnalysis#getInstructionAnalysis() instructionAnalysis} list.
   * @param elements An array of instructionAnalysis elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableStaticCompositionAnalysis addInstructionAnalysis(IStaticInstAnalysis... elements) {
    for (IStaticInstAnalysis e : elements) {
      addInstructionAnalysis(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IStaticCompositionAnalysis#getInstructionAnalysis() instructionAnalysis} list.
   * @param elements An iterable of instructionAnalysis elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableStaticCompositionAnalysis setInstructionAnalysis(Iterable<? extends IStaticInstAnalysis> elements) {
    this.instructionAnalysis.clear();
    addAllInstructionAnalysis(elements);
    return this;
  }

  /**
   * Adds elements to {@link IStaticCompositionAnalysis#getInstructionAnalysis() instructionAnalysis} list.
   * @param elements An iterable of instructionAnalysis elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableStaticCompositionAnalysis addAllInstructionAnalysis(Iterable<? extends IStaticInstAnalysis> elements) {
    for (IStaticInstAnalysis e : elements) {
      addInstructionAnalysis(e);
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
   * Converts to {@link StaticCompositionAnalysis StaticCompositionAnalysis}.
   * @return An immutable instance of StaticCompositionAnalysis
   */
  public final StaticCompositionAnalysis toImmutable() {
    return StaticCompositionAnalysis.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableStaticCompositionAnalysis} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableStaticCompositionAnalysis)) return false;
    MutableStaticCompositionAnalysis other = (MutableStaticCompositionAnalysis) another;
    return equalTo(other);
  }

  private boolean equalTo(MutableStaticCompositionAnalysis another) {
    return programOrder.equals(another.programOrder)
        && instructionAnalysis.equals(another.instructionAnalysis);
  }

  /**
   * Computes a hash code from attributes: {@code programOrder}, {@code instructionAnalysis}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + programOrder.hashCode();
    h += (h << 5) + instructionAnalysis.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IStaticCompositionAnalysis}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableStaticCompositionAnalysis")
        .add("programOrder", getProgramOrder())
        .add("instructionAnalysis", getInstructionAnalysis())
        .toString();
  }
}
