package de.upb.sede.composition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IIndexedInstruction IIndexedInstruction} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableIndexedInstruction is not thread-safe</em>
 * @see IndexedInstruction
 */
@Generated(from = "IIndexedInstruction", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IIndexedInstruction"})
@NotThreadSafe
public final class MutableIndexedInstruction implements IIndexedInstruction {
  private static final long INIT_BIT_INSTRUCTION = 0x1L;
  private static final long INIT_BIT_INDEX = 0x2L;
  private long initBits = 0x3L;

  private IInstructionNode instruction;
  private Long index;

  private MutableIndexedInstruction() {}

  /**
   * Construct a modifiable instance of {@code IIndexedInstruction}.
   * @return A new modifiable instance
   */
  public static MutableIndexedInstruction create() {
    return new MutableIndexedInstruction();
  }

  /**
   * @return value of {@code instruction} attribute
   */
  @JsonProperty("instruction")
  @Override
  public final IInstructionNode getInstruction() {
    if (!instructionIsSet()) {
      checkRequiredAttributes();
    }
    return instruction;
  }

  /**
   * @return value of {@code index} attribute
   */
  @JsonProperty("index")
  @Override
  public final Long getIndex() {
    if (!indexIsSet()) {
      checkRequiredAttributes();
    }
    return index;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableIndexedInstruction clear() {
    initBits = 0x3L;
    instruction = null;
    index = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IIndexedInstruction} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableIndexedInstruction from(IIndexedInstruction instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableIndexedInstruction) {
      from((MutableIndexedInstruction) instance);
      return this;
    }
    setInstruction(instance.getInstruction());
    setIndex(instance.getIndex());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IIndexedInstruction} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableIndexedInstruction from(MutableIndexedInstruction instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.instructionIsSet()) {
      setInstruction(instance.getInstruction());
    }
    if (instance.indexIsSet()) {
      setIndex(instance.getIndex());
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IIndexedInstruction#getInstruction() instruction} attribute.
   * @param instruction The value for instruction
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableIndexedInstruction setInstruction(IInstructionNode instruction) {
    this.instruction = Objects.requireNonNull(instruction, "instruction");
    initBits &= ~INIT_BIT_INSTRUCTION;
    return this;
  }

  /**
   * Assigns a value to the {@link IIndexedInstruction#getIndex() index} attribute.
   * @param index The value for index
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableIndexedInstruction setIndex(Long index) {
    this.index = Objects.requireNonNull(index, "index");
    initBits &= ~INIT_BIT_INDEX;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IIndexedInstruction#getInstruction() instruction} is set.
   * @return {@code true} if set
   */
  public final boolean instructionIsSet() {
    return (initBits & INIT_BIT_INSTRUCTION) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IIndexedInstruction#getIndex() index} is set.
   * @return {@code true} if set
   */
  public final boolean indexIsSet() {
    return (initBits & INIT_BIT_INDEX) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableIndexedInstruction unsetInstruction() {
    initBits |= INIT_BIT_INSTRUCTION;
    instruction = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableIndexedInstruction unsetIndex() {
    initBits |= INIT_BIT_INDEX;
    index = null;
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
    if (!indexIsSet()) attributes.add("index");
    return "IndexedInstruction is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link IndexedInstruction IndexedInstruction}.
   * @return An immutable instance of IndexedInstruction
   */
  public final IndexedInstruction toImmutable() {
    checkRequiredAttributes();
    return IndexedInstruction.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableIndexedInstruction} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableIndexedInstruction)) return false;
    MutableIndexedInstruction other = (MutableIndexedInstruction) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableIndexedInstruction another) {
    return instruction.equals(another.instruction)
        && index.equals(another.index);
  }

  /**
   * Computes a hash code from attributes: {@code instruction}, {@code index}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + instruction.hashCode();
    h += (h << 5) + index.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IIndexedInstruction}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableIndexedInstruction")
        .add("instruction", instructionIsSet() ? getInstruction() : "?")
        .add("index", indexIsSet() ? getIndex() : "?")
        .toString();
  }
}
