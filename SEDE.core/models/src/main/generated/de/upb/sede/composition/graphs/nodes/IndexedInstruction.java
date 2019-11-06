package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
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
 * Immutable implementation of {@link IIndexedInstruction}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code IndexedInstruction.builder()}.
 */
@Generated(from = "IIndexedInstruction", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class IndexedInstruction implements IIndexedInstruction {
  private final IInstructionNode instruction;
  private final Long index;

  private IndexedInstruction(IInstructionNode instruction, Long index) {
    this.instruction = instruction;
    this.index = index;
  }

  /**
   * @return The value of the {@code instruction} attribute
   */
  @JsonProperty("instruction")
  @Override
  public IInstructionNode getInstruction() {
    return instruction;
  }

  /**
   * @return The value of the {@code index} attribute
   */
  @JsonProperty("index")
  @Override
  public Long getIndex() {
    return index;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IIndexedInstruction#getInstruction() instruction} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for instruction
   * @return A modified copy of the {@code this} object
   */
  public final IndexedInstruction withInstruction(IInstructionNode value) {
    if (this.instruction == value) return this;
    IInstructionNode newValue = Objects.requireNonNull(value, "instruction");
    return new IndexedInstruction(newValue, this.index);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IIndexedInstruction#getIndex() index} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for index
   * @return A modified copy of the {@code this} object
   */
  public final IndexedInstruction withIndex(Long value) {
    Long newValue = Objects.requireNonNull(value, "index");
    if (this.index.equals(newValue)) return this;
    return new IndexedInstruction(this.instruction, newValue);
  }

  /**
   * This instance is equal to all instances of {@code IndexedInstruction} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof IndexedInstruction
        && equalTo((IndexedInstruction) another);
  }

  private boolean equalTo(IndexedInstruction another) {
    return instruction.equals(another.instruction)
        && index.equals(another.index);
  }

  /**
   * Computes a hash code from attributes: {@code instruction}, {@code index}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + instruction.hashCode();
    h += (h << 5) + index.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code IndexedInstruction} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("IndexedInstruction")
        .omitNullValues()
        .add("instruction", instruction)
        .add("index", index)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IIndexedInstruction", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IIndexedInstruction {
    @Nullable IInstructionNode instruction;
    @Nullable Long index;
    @JsonProperty("instruction")
    public void setInstruction(IInstructionNode instruction) {
      this.instruction = instruction;
    }
    @JsonProperty("index")
    public void setIndex(Long index) {
      this.index = index;
    }
    @Override
    public IInstructionNode getInstruction() { throw new UnsupportedOperationException(); }
    @Override
    public Long getIndex() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static IndexedInstruction fromJson(Json json) {
    IndexedInstruction.Builder builder = IndexedInstruction.builder();
    if (json.instruction != null) {
      builder.instruction(json.instruction);
    }
    if (json.index != null) {
      builder.index(json.index);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IIndexedInstruction} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable IndexedInstruction instance
   */
  public static IndexedInstruction copyOf(IIndexedInstruction instance) {
    if (instance instanceof IndexedInstruction) {
      return (IndexedInstruction) instance;
    }
    return IndexedInstruction.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link IndexedInstruction IndexedInstruction}.
   * <pre>
   * IndexedInstruction.builder()
   *    .instruction(de.upb.sede.composition.graphs.nodes.IInstructionNode) // required {@link IIndexedInstruction#getInstruction() instruction}
   *    .index(Long) // required {@link IIndexedInstruction#getIndex() index}
   *    .build();
   * </pre>
   * @return A new IndexedInstruction builder
   */
  public static IndexedInstruction.Builder builder() {
    return new IndexedInstruction.Builder();
  }

  /**
   * Builds instances of type {@link IndexedInstruction IndexedInstruction}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IIndexedInstruction", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_INSTRUCTION = 0x1L;
    private static final long INIT_BIT_INDEX = 0x2L;
    private long initBits = 0x3L;

    private @Nullable IInstructionNode instruction;
    private @Nullable Long index;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableIndexedInstruction} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableIndexedInstruction instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.instructionIsSet()) {
        instruction(instance.getInstruction());
      }
      if (instance.indexIsSet()) {
        index(instance.getIndex());
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IIndexedInstruction} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IIndexedInstruction instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableIndexedInstruction) {
        return from((MutableIndexedInstruction) instance);
      }
      instruction(instance.getInstruction());
      index(instance.getIndex());
      return this;
    }

    /**
     * Initializes the value for the {@link IIndexedInstruction#getInstruction() instruction} attribute.
     * @param instruction The value for instruction 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("instruction")
    public final Builder instruction(IInstructionNode instruction) {
      this.instruction = Objects.requireNonNull(instruction, "instruction");
      initBits &= ~INIT_BIT_INSTRUCTION;
      return this;
    }

    /**
     * Initializes the value for the {@link IIndexedInstruction#getIndex() index} attribute.
     * @param index The value for index 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("index")
    public final Builder index(Long index) {
      this.index = Objects.requireNonNull(index, "index");
      initBits &= ~INIT_BIT_INDEX;
      return this;
    }

    /**
     * Builds a new {@link IndexedInstruction IndexedInstruction}.
     * @return An immutable instance of IndexedInstruction
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public IndexedInstruction build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new IndexedInstruction(instruction, index);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_INSTRUCTION) != 0) attributes.add("instruction");
      if ((initBits & INIT_BIT_INDEX) != 0) attributes.add("index");
      return "Cannot build IndexedInstruction, some of required attributes are not set " + attributes;
    }
  }
}
