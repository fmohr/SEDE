package de.upb.sede.composition;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
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
 * Immutable implementation of {@link IInstTCResult}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code InstTCResult.builder()}.
 */
@Generated(from = "IInstTCResult", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class InstTCResult implements IInstTCResult {
  private final ImmutableList<IFieldType> fieldTypes;

  private InstTCResult(ImmutableList<IFieldType> fieldTypes) {
    this.fieldTypes = fieldTypes;
  }

  /**
   * @return The value of the {@code fieldTypes} attribute
   */
  @JsonProperty("fieldTypes")
  @Override
  public ImmutableList<IFieldType> getFieldTypes() {
    return fieldTypes;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IInstTCResult#getFieldTypes() fieldTypes}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final InstTCResult withFieldTypes(IFieldType... elements) {
    ImmutableList<IFieldType> newValue = ImmutableList.copyOf(elements);
    return new InstTCResult(newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IInstTCResult#getFieldTypes() fieldTypes}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of fieldTypes elements to set
   * @return A modified copy of {@code this} object
   */
  public final InstTCResult withFieldTypes(Iterable<? extends IFieldType> elements) {
    if (this.fieldTypes == elements) return this;
    ImmutableList<IFieldType> newValue = ImmutableList.copyOf(elements);
    return new InstTCResult(newValue);
  }

  /**
   * This instance is equal to all instances of {@code InstTCResult} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof InstTCResult
        && equalTo((InstTCResult) another);
  }

  private boolean equalTo(InstTCResult another) {
    return fieldTypes.equals(another.fieldTypes);
  }

  /**
   * Computes a hash code from attributes: {@code fieldTypes}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + fieldTypes.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code InstTCResult} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("InstTCResult")
        .omitNullValues()
        .add("fieldTypes", fieldTypes)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IInstTCResult", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IInstTCResult {
    @Nullable List<IFieldType> fieldTypes = ImmutableList.of();
    @JsonProperty("fieldTypes")
    public void setFieldTypes(List<IFieldType> fieldTypes) {
      this.fieldTypes = fieldTypes;
    }
    @Override
    public List<IFieldType> getFieldTypes() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static InstTCResult fromJson(Json json) {
    InstTCResult.Builder builder = InstTCResult.builder();
    if (json.fieldTypes != null) {
      builder.addAllFieldTypes(json.fieldTypes);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IInstTCResult} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable InstTCResult instance
   */
  public static InstTCResult copyOf(IInstTCResult instance) {
    if (instance instanceof InstTCResult) {
      return (InstTCResult) instance;
    }
    return InstTCResult.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link InstTCResult InstTCResult}.
   * <pre>
   * InstTCResult.builder()
   *    .addFieldTypes|addAllFieldTypes(de.upb.sede.composition.IFieldType) // {@link IInstTCResult#getFieldTypes() fieldTypes} elements
   *    .build();
   * </pre>
   * @return A new InstTCResult builder
   */
  public static InstTCResult.Builder builder() {
    return new InstTCResult.Builder();
  }

  /**
   * Builds instances of type {@link InstTCResult InstTCResult}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IInstTCResult", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private ImmutableList.Builder<IFieldType> fieldTypes = ImmutableList.builder();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableInstTCResult} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableInstTCResult instance) {
      Objects.requireNonNull(instance, "instance");
      addAllFieldTypes(instance.getFieldTypes());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IInstTCResult} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IInstTCResult instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableInstTCResult) {
        return from((MutableInstTCResult) instance);
      }
      addAllFieldTypes(instance.getFieldTypes());
      return this;
    }

    /**
     * Adds one element to {@link IInstTCResult#getFieldTypes() fieldTypes} list.
     * @param element A fieldTypes element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addFieldTypes(IFieldType element) {
      this.fieldTypes.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IInstTCResult#getFieldTypes() fieldTypes} list.
     * @param elements An array of fieldTypes elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addFieldTypes(IFieldType... elements) {
      this.fieldTypes.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IInstTCResult#getFieldTypes() fieldTypes} list.
     * @param elements An iterable of fieldTypes elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("fieldTypes")
    public final Builder fieldTypes(Iterable<? extends IFieldType> elements) {
      this.fieldTypes = ImmutableList.builder();
      return addAllFieldTypes(elements);
    }

    /**
     * Adds elements to {@link IInstTCResult#getFieldTypes() fieldTypes} list.
     * @param elements An iterable of fieldTypes elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllFieldTypes(Iterable<? extends IFieldType> elements) {
      this.fieldTypes.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link InstTCResult InstTCResult}.
     * @return An immutable instance of InstTCResult
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public InstTCResult build() {
      return new InstTCResult(fieldTypes.build());
    }
  }
}
