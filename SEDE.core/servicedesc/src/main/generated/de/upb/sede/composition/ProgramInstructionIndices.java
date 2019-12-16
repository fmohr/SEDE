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
 * Immutable implementation of {@link IProgramInstructionIndices}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ProgramInstructionIndices.builder()}.
 */
@Generated(from = "IProgramInstructionIndices", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ProgramInstructionIndices implements IProgramInstructionIndices {
  private final ImmutableList<Long> indices;

  private ProgramInstructionIndices(ImmutableList<Long> indices) {
    this.indices = indices;
  }

  /**
   * @return The value of the {@code indices} attribute
   */
  @JsonProperty("indices")
  @Override
  public ImmutableList<Long> getIndices() {
    return indices;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IProgramInstructionIndices#getIndices() indices}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ProgramInstructionIndices withIndices(long... elements) {
    ImmutableList<Long> newValue = ImmutableList.copyOf(Longs.asList(elements));
    return new ProgramInstructionIndices(newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IProgramInstructionIndices#getIndices() indices}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of indices elements to set
   * @return A modified copy of {@code this} object
   */
  public final ProgramInstructionIndices withIndices(Iterable<Long> elements) {
    if (this.indices == elements) return this;
    ImmutableList<Long> newValue = ImmutableList.copyOf(elements);
    return new ProgramInstructionIndices(newValue);
  }

  /**
   * This instance is equal to all instances of {@code ProgramInstructionIndices} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ProgramInstructionIndices
        && equalTo((ProgramInstructionIndices) another);
  }

  private boolean equalTo(ProgramInstructionIndices another) {
    return indices.equals(another.indices);
  }

  /**
   * Computes a hash code from attributes: {@code indices}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + indices.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ProgramInstructionIndices} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ProgramInstructionIndices")
        .omitNullValues()
        .add("indices", indices)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IProgramInstructionIndices", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IProgramInstructionIndices {
    @Nullable List<Long> indices = ImmutableList.of();
    @JsonProperty("indices")
    public void setIndices(List<Long> indices) {
      this.indices = indices;
    }
    @Override
    public List<Long> getIndices() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static ProgramInstructionIndices fromJson(Json json) {
    ProgramInstructionIndices.Builder builder = ProgramInstructionIndices.builder();
    if (json.indices != null) {
      builder.addAllIndices(json.indices);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IProgramInstructionIndices} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ProgramInstructionIndices instance
   */
  public static ProgramInstructionIndices copyOf(IProgramInstructionIndices instance) {
    if (instance instanceof ProgramInstructionIndices) {
      return (ProgramInstructionIndices) instance;
    }
    return ProgramInstructionIndices.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ProgramInstructionIndices ProgramInstructionIndices}.
   * <pre>
   * ProgramInstructionIndices.builder()
   *    .addIndices|addAllIndices(long) // {@link IProgramInstructionIndices#getIndices() indices} elements
   *    .build();
   * </pre>
   * @return A new ProgramInstructionIndices builder
   */
  public static ProgramInstructionIndices.Builder builder() {
    return new ProgramInstructionIndices.Builder();
  }

  /**
   * Builds instances of type {@link ProgramInstructionIndices ProgramInstructionIndices}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IProgramInstructionIndices", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private ImmutableList.Builder<Long> indices = ImmutableList.builder();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableProgramInstructionIndices} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableProgramInstructionIndices instance) {
      Objects.requireNonNull(instance, "instance");
      addAllIndices(instance.getIndices());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IProgramInstructionIndices} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IProgramInstructionIndices instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableProgramInstructionIndices) {
        return from((MutableProgramInstructionIndices) instance);
      }
      addAllIndices(instance.getIndices());
      return this;
    }

    /**
     * Adds one element to {@link IProgramInstructionIndices#getIndices() indices} list.
     * @param element A indices element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addIndices(long element) {
      this.indices.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IProgramInstructionIndices#getIndices() indices} list.
     * @param elements An array of indices elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addIndices(long... elements) {
      this.indices.addAll(Longs.asList(elements));
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IProgramInstructionIndices#getIndices() indices} list.
     * @param elements An iterable of indices elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("indices")
    public final Builder indices(Iterable<Long> elements) {
      this.indices = ImmutableList.builder();
      return addAllIndices(elements);
    }

    /**
     * Adds elements to {@link IProgramInstructionIndices#getIndices() indices} list.
     * @param elements An iterable of indices elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllIndices(Iterable<Long> elements) {
      this.indices.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link ProgramInstructionIndices ProgramInstructionIndices}.
     * @return An immutable instance of ProgramInstructionIndices
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ProgramInstructionIndices build() {
      return new ProgramInstructionIndices(indices.build());
    }
  }
}
