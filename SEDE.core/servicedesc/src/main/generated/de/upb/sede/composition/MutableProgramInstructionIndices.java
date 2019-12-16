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
 * A modifiable implementation of the {@link IProgramInstructionIndices IProgramInstructionIndices} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableProgramInstructionIndices is not thread-safe</em>
 * @see ProgramInstructionIndices
 */
@Generated(from = "IProgramInstructionIndices", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IProgramInstructionIndices"})
@NotThreadSafe
public final class MutableProgramInstructionIndices
    implements IProgramInstructionIndices {
  private final ArrayList<Long> indices = new ArrayList<Long>();

  private MutableProgramInstructionIndices() {}

  /**
   * Construct a modifiable instance of {@code IProgramInstructionIndices}.
   * @return A new modifiable instance
   */
  public static MutableProgramInstructionIndices create() {
    return new MutableProgramInstructionIndices();
  }

  /**
   * @return modifiable list {@code indices}
   */
  @JsonProperty("indices")
  @Override
  public final List<Long> getIndices() {
    return indices;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableProgramInstructionIndices clear() {
    indices.clear();
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IProgramInstructionIndices} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableProgramInstructionIndices from(IProgramInstructionIndices instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableProgramInstructionIndices) {
      from((MutableProgramInstructionIndices) instance);
      return this;
    }
    addAllIndices(instance.getIndices());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IProgramInstructionIndices} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableProgramInstructionIndices from(MutableProgramInstructionIndices instance) {
    Objects.requireNonNull(instance, "instance");
    addAllIndices(instance.getIndices());
    return this;
  }

  /**
   * Adds one element to {@link IProgramInstructionIndices#getIndices() indices} list.
   * @param element The indices element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableProgramInstructionIndices addIndices(long element) {
    this.indices.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IProgramInstructionIndices#getIndices() indices} list.
   * @param elements An array of indices elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableProgramInstructionIndices addIndices(long... elements) {
    for (long e : elements) {
      addIndices(Objects.requireNonNull(e, "indices element"));
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IProgramInstructionIndices#getIndices() indices} list.
   * @param elements An iterable of indices elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableProgramInstructionIndices setIndices(Iterable<Long> elements) {
    this.indices.clear();
    addAllIndices(elements);
    return this;
  }

  /**
   * Adds elements to {@link IProgramInstructionIndices#getIndices() indices} list.
   * @param elements An iterable of indices elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableProgramInstructionIndices addAllIndices(Iterable<Long> elements) {
    for (long e : elements) {
      addIndices(e);
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
   * Converts to {@link ProgramInstructionIndices ProgramInstructionIndices}.
   * @return An immutable instance of ProgramInstructionIndices
   */
  public final ProgramInstructionIndices toImmutable() {
    return ProgramInstructionIndices.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableProgramInstructionIndices} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableProgramInstructionIndices)) return false;
    MutableProgramInstructionIndices other = (MutableProgramInstructionIndices) another;
    return equalTo(other);
  }

  private boolean equalTo(MutableProgramInstructionIndices another) {
    return indices.equals(another.indices);
  }

  /**
   * Computes a hash code from attributes: {@code indices}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + indices.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IProgramInstructionIndices}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableProgramInstructionIndices")
        .add("indices", getIndices())
        .toString();
  }
}
