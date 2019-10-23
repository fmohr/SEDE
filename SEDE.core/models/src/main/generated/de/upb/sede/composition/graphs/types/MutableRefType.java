package de.upb.sede.composition.graphs.types;

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
 * A modifiable implementation of the {@link IRefType IRefType} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableRefType is not thread-safe</em>
 * @see RefType
 */
@Generated(from = "IRefType", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IRefType"})
@NotThreadSafe
public final class MutableRefType implements IRefType {
  private static final long INIT_BIT_REFERENCED_TYPE = 0x1L;
  private long initBits = 0x1L;

  private TypeClass referencedType;
  private String typeClass;

  private MutableRefType() {}

  /**
   * Construct a modifiable instance of {@code IRefType}.
   * @return A new modifiable instance
   */
  public static MutableRefType create() {
    return new MutableRefType();
  }

  /**
   * @return value of {@code referencedType} attribute
   */
  @JsonProperty("referencedType")
  @Override
  public final TypeClass getReferencedType() {
    if (!referencedTypeIsSet()) {
      checkRequiredAttributes();
    }
    return referencedType;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code typeClass} attribute
   */
  @JsonProperty("typeClass")
  @Override
  public final String getTypeClass() {
    return typeClassIsSet()
        ? typeClass
        : IRefType.super.getTypeClass();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableRefType clear() {
    initBits = 0x1L;
    referencedType = null;
    typeClass = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.types.TypeClass} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableRefType from(TypeClass instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.types.IRefType} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableRefType from(IRefType instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IRefType} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableRefType from(MutableRefType instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableRefType) {
      MutableRefType instance = (MutableRefType) object;
      if (instance.referencedTypeIsSet()) {
        setReferencedType(instance.getReferencedType());
      }
      setTypeClass(instance.getTypeClass());
      return;
    }
    if (object instanceof TypeClass) {
      TypeClass instance = (TypeClass) object;
      setTypeClass(instance.getTypeClass());
    }
    if (object instanceof IRefType) {
      IRefType instance = (IRefType) object;
      setReferencedType(instance.getReferencedType());
    }
  }

  /**
   * Assigns a value to the {@link IRefType#getReferencedType() referencedType} attribute.
   * @param referencedType The value for referencedType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableRefType setReferencedType(TypeClass referencedType) {
    this.referencedType = Objects.requireNonNull(referencedType, "referencedType");
    initBits &= ~INIT_BIT_REFERENCED_TYPE;
    return this;
  }

  /**
   * Assigns a value to the {@link IRefType#getTypeClass() typeClass} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IRefType#getTypeClass() typeClass}.</em>
   * @param typeClass The value for typeClass
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableRefType setTypeClass(String typeClass) {
    this.typeClass = Objects.requireNonNull(typeClass, "typeClass");
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IRefType#getReferencedType() referencedType} is set.
   * @return {@code true} if set
   */
  public final boolean referencedTypeIsSet() {
    return (initBits & INIT_BIT_REFERENCED_TYPE) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IRefType#getTypeClass() typeClass} is set.
   * @return {@code true} if set
   */
  public final boolean typeClassIsSet() {
    return typeClass != null;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableRefType unsetReferencedType() {
    initBits |= INIT_BIT_REFERENCED_TYPE;
    referencedType = null;
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
    if (!referencedTypeIsSet()) attributes.add("referencedType");
    return "RefType is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link RefType RefType}.
   * @return An immutable instance of RefType
   */
  public final RefType toImmutable() {
    checkRequiredAttributes();
    return RefType.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableRefType} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableRefType)) return false;
    MutableRefType other = (MutableRefType) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableRefType another) {
    String typeClass = getTypeClass();
    return referencedType.equals(another.referencedType)
        && typeClass.equals(another.getTypeClass());
  }

  /**
   * Computes a hash code from attributes: {@code referencedType}, {@code typeClass}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + referencedType.hashCode();
    String typeClass = getTypeClass();
    h += (h << 5) + typeClass.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IRefType}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableRefType")
        .add("referencedType", referencedTypeIsSet() ? getReferencedType() : "?")
        .add("typeClass", getTypeClass())
        .toString();
  }
}
