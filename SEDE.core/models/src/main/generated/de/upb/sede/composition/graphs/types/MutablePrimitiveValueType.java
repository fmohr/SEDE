package de.upb.sede.composition.graphs.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IPrimitiveValueType IPrimitiveValueType} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutablePrimitiveValueType is not thread-safe</em>
 * @see PrimitiveValueType
 */
@Generated(from = "IPrimitiveValueType", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IPrimitiveValueType"})
@NotThreadSafe
public final class MutablePrimitiveValueType
    implements IPrimitiveValueType {
  private String typeClass;

  private MutablePrimitiveValueType() {}

  /**
   * Construct a modifiable instance of {@code IPrimitiveValueType}.
   * @return A new modifiable instance
   */
  public static MutablePrimitiveValueType create() {
    return new MutablePrimitiveValueType();
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code typeClass} attribute
   */
  @JsonProperty("typeClass")
  @Override
  public final String getTypeClass() {
    return typeClassIsSet()
        ? typeClass
        : IPrimitiveValueType.super.getTypeClass();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutablePrimitiveValueType clear() {
    typeClass = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.types.TypeClass} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutablePrimitiveValueType from(TypeClass instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.types.IPrimitiveValueType} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutablePrimitiveValueType from(IPrimitiveValueType instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IPrimitiveValueType} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutablePrimitiveValueType from(MutablePrimitiveValueType instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutablePrimitiveValueType) {
      MutablePrimitiveValueType instance = (MutablePrimitiveValueType) object;
      setTypeClass(instance.getTypeClass());
      return;
    }
    if (object instanceof TypeClass) {
      TypeClass instance = (TypeClass) object;
      setTypeClass(instance.getTypeClass());
    }
  }

  /**
   * Assigns a value to the {@link IPrimitiveValueType#getTypeClass() typeClass} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IPrimitiveValueType#getTypeClass() typeClass}.</em>
   * @param typeClass The value for typeClass
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutablePrimitiveValueType setTypeClass(String typeClass) {
    this.typeClass = Objects.requireNonNull(typeClass, "typeClass");
    return this;
  }

  /**
   * Returns {@code true} if the default attribute {@link IPrimitiveValueType#getTypeClass() typeClass} is set.
   * @return {@code true} if set
   */
  public final boolean typeClassIsSet() {
    return typeClass != null;
  }


  /**
   * Returns {@code true} if all required attributes are set, indicating that the object is initialized.
   * @return {@code true} if set
   */
  public final boolean isInitialized() {
    return true;
  }

  /**
   * Converts to {@link PrimitiveValueType PrimitiveValueType}.
   * @return An immutable instance of PrimitiveValueType
   */
  public final PrimitiveValueType toImmutable() {
    return PrimitiveValueType.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutablePrimitiveValueType} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutablePrimitiveValueType)) return false;
    MutablePrimitiveValueType other = (MutablePrimitiveValueType) another;
    return equalTo(other);
  }

  private boolean equalTo(MutablePrimitiveValueType another) {
    String typeClass = getTypeClass();
    return typeClass.equals(another.getTypeClass());
  }

  /**
   * Computes a hash code from attributes: {@code typeClass}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    String typeClass = getTypeClass();
    h += (h << 5) + typeClass.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IPrimitiveValueType}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutablePrimitiveValueType")
        .add("typeClass", getTypeClass())
        .toString();
  }
}
