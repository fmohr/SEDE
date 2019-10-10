package de.upb.sede.composition.graphs.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.IQualifiable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IValueType IValueType} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableValueType is not thread-safe</em>
 * @see ValueType
 */
@Generated(from = "IValueType", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IValueType"})
@NotThreadSafe
public final class MutableValueType implements IValueType {
  private static final long INIT_BIT_QUALIFIER = 0x1L;
  private long initBits = 0x1L;

  private String typeClass;
  private String qualifier;
  private String simpleName;

  private MutableValueType() {}

  /**
   * Construct a modifiable instance of {@code IValueType}.
   * @return A new modifiable instance
   */
  public static MutableValueType create() {
    return new MutableValueType();
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code typeClass} attribute
   */
  @JsonProperty("typeClass")
  @Override
  public final String getTypeClass() {
    return typeClassIsSet()
        ? typeClass
        : IValueType.super.getTypeClass();
  }

  /**
   * @return value of {@code qualifier} attribute
   */
  @JsonProperty("qualifier")
  @Override
  public final String getQualifier() {
    if (!qualifierIsSet()) {
      checkRequiredAttributes();
    }
    return qualifier;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code simpleName} attribute
   */
  @JsonProperty("simpleName")
  @Override
  public final String getSimpleName() {
    return simpleNameIsSet()
        ? simpleName
        : IValueType.super.getSimpleName();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableValueType clear() {
    initBits = 0x1L;
    typeClass = null;
    qualifier = null;
    simpleName = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.types.TypeClass} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableValueType from(TypeClass instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.IQualifiable} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableValueType from(IQualifiable instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.types.IValueType} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableValueType from(IValueType instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IValueType} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableValueType from(MutableValueType instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableValueType) {
      MutableValueType instance = (MutableValueType) object;
      setTypeClass(instance.getTypeClass());
      if (instance.qualifierIsSet()) {
        setQualifier(instance.getQualifier());
      }
      setSimpleName(instance.getSimpleName());
      return;
    }
    if (object instanceof TypeClass) {
      TypeClass instance = (TypeClass) object;
      setTypeClass(instance.getTypeClass());
    }
    if (object instanceof IQualifiable) {
      IQualifiable instance = (IQualifiable) object;
      setSimpleName(instance.getSimpleName());
      setQualifier(instance.getQualifier());
    }
  }

  /**
   * Assigns a value to the {@link IValueType#getTypeClass() typeClass} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IValueType#getTypeClass() typeClass}.</em>
   * @param typeClass The value for typeClass
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableValueType setTypeClass(String typeClass) {
    this.typeClass = Objects.requireNonNull(typeClass, "typeClass");
    return this;
  }

  /**
   * Assigns a value to the {@link IValueType#getQualifier() qualifier} attribute.
   * @param qualifier The value for qualifier
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableValueType setQualifier(String qualifier) {
    this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
    initBits &= ~INIT_BIT_QUALIFIER;
    return this;
  }

  /**
   * Assigns a value to the {@link IValueType#getSimpleName() simpleName} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IValueType#getSimpleName() simpleName}.</em>
   * @param simpleName The value for simpleName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableValueType setSimpleName(String simpleName) {
    this.simpleName = Objects.requireNonNull(simpleName, "simpleName");
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IValueType#getQualifier() qualifier} is set.
   * @return {@code true} if set
   */
  public final boolean qualifierIsSet() {
    return (initBits & INIT_BIT_QUALIFIER) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IValueType#getTypeClass() typeClass} is set.
   * @return {@code true} if set
   */
  public final boolean typeClassIsSet() {
    return typeClass != null;
  }

  /**
   * Returns {@code true} if the default attribute {@link IValueType#getSimpleName() simpleName} is set.
   * @return {@code true} if set
   */
  public final boolean simpleNameIsSet() {
    return simpleName != null;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableValueType unsetQualifier() {
    initBits |= INIT_BIT_QUALIFIER;
    qualifier = null;
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
    if (!qualifierIsSet()) attributes.add("qualifier");
    return "ValueType is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link ValueType ValueType}.
   * @return An immutable instance of ValueType
   */
  public final ValueType toImmutable() {
    checkRequiredAttributes();
    return ValueType.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableValueType} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableValueType)) return false;
    MutableValueType other = (MutableValueType) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableValueType another) {
    String typeClass = getTypeClass();
    return typeClass.equals(another.getTypeClass())
        && qualifier.equals(another.qualifier);
  }

  /**
   * Computes a hash code from attributes: {@code typeClass}, {@code qualifier}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    String typeClass = getTypeClass();
    h += (h << 5) + typeClass.hashCode();
    h += (h << 5) + qualifier.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IValueType}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableValueType")
        .add("typeClass", getTypeClass())
        .add("qualifier", qualifierIsSet() ? getQualifier() : "?")
        .toString();
  }
}
