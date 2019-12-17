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
 * A modifiable implementation of the {@link IInstTCResult IInstTCResult} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableInstTCResult is not thread-safe</em>
 * @see InstTCResult
 */
@Generated(from = "IInstTCResult", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IInstTCResult"})
@NotThreadSafe
public final class MutableInstTCResult implements IInstTCResult {
  private final ArrayList<IFieldType> fieldTypes = new ArrayList<IFieldType>();

  private MutableInstTCResult() {}

  /**
   * Construct a modifiable instance of {@code IInstTCResult}.
   * @return A new modifiable instance
   */
  public static MutableInstTCResult create() {
    return new MutableInstTCResult();
  }

  /**
   * @return modifiable list {@code fieldTypes}
   */
  @JsonProperty("fieldTypes")
  @Override
  public final List<IFieldType> getFieldTypes() {
    return fieldTypes;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstTCResult clear() {
    fieldTypes.clear();
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IInstTCResult} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableInstTCResult from(IInstTCResult instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableInstTCResult) {
      from((MutableInstTCResult) instance);
      return this;
    }
    addAllFieldTypes(instance.getFieldTypes());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IInstTCResult} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableInstTCResult from(MutableInstTCResult instance) {
    Objects.requireNonNull(instance, "instance");
    addAllFieldTypes(instance.getFieldTypes());
    return this;
  }

  /**
   * Adds one element to {@link IInstTCResult#getFieldTypes() fieldTypes} list.
   * @param element The fieldTypes element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstTCResult addFieldTypes(IFieldType element) {
    Objects.requireNonNull(element, "fieldTypes element");
    this.fieldTypes.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IInstTCResult#getFieldTypes() fieldTypes} list.
   * @param elements An array of fieldTypes elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableInstTCResult addFieldTypes(IFieldType... elements) {
    for (IFieldType e : elements) {
      addFieldTypes(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IInstTCResult#getFieldTypes() fieldTypes} list.
   * @param elements An iterable of fieldTypes elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstTCResult setFieldTypes(Iterable<? extends IFieldType> elements) {
    this.fieldTypes.clear();
    addAllFieldTypes(elements);
    return this;
  }

  /**
   * Adds elements to {@link IInstTCResult#getFieldTypes() fieldTypes} list.
   * @param elements An iterable of fieldTypes elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstTCResult addAllFieldTypes(Iterable<? extends IFieldType> elements) {
    for (IFieldType e : elements) {
      addFieldTypes(e);
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
   * Converts to {@link InstTCResult InstTCResult}.
   * @return An immutable instance of InstTCResult
   */
  public final InstTCResult toImmutable() {
    return InstTCResult.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableInstTCResult} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableInstTCResult)) return false;
    MutableInstTCResult other = (MutableInstTCResult) another;
    return equalTo(other);
  }

  private boolean equalTo(MutableInstTCResult another) {
    return fieldTypes.equals(another.fieldTypes);
  }

  /**
   * Computes a hash code from attributes: {@code fieldTypes}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + fieldTypes.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IInstTCResult}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableInstTCResult")
        .add("fieldTypes", getFieldTypes())
        .toString();
  }
}
