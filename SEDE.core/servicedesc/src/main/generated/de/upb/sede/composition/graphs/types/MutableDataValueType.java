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
 * A modifiable implementation of the {@link IDataValueType IDataValueType} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableDataValueType is not thread-safe</em>
 * @see DataValueType
 */
@Generated(from = "IDataValueType", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IDataValueType"})
@NotThreadSafe
public final class MutableDataValueType implements IDataValueType {
  private static final long INIT_BIT_QUALIFIER = 0x1L;
  private long initBits = 0x1L;

  private String qualifier;

  private MutableDataValueType() {}

  /**
   * Construct a modifiable instance of {@code IDataValueType}.
   * @return A new modifiable instance
   */
  public static MutableDataValueType create() {
    return new MutableDataValueType();
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
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataValueType clear() {
    initBits = 0x1L;
    qualifier = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.IQualifiable} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataValueType from(IQualifiable instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.types.IDataValueType} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataValueType from(IDataValueType instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IDataValueType} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableDataValueType from(MutableDataValueType instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableDataValueType) {
      MutableDataValueType instance = (MutableDataValueType) object;
      if (instance.qualifierIsSet()) {
        setQualifier(instance.getQualifier());
      }
      return;
    }
    if (object instanceof IQualifiable) {
      IQualifiable instance = (IQualifiable) object;
      setQualifier(instance.getQualifier());
    }
  }

  /**
   * Assigns a value to the {@link IDataValueType#getQualifier() qualifier} attribute.
   * @param qualifier The value for qualifier
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataValueType setQualifier(String qualifier) {
    this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
    initBits &= ~INIT_BIT_QUALIFIER;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IDataValueType#getQualifier() qualifier} is set.
   * @return {@code true} if set
   */
  public final boolean qualifierIsSet() {
    return (initBits & INIT_BIT_QUALIFIER) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableDataValueType unsetQualifier() {
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
    return "DataValueType is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link DataValueType DataValueType}.
   * @return An immutable instance of DataValueType
   */
  public final DataValueType toImmutable() {
    checkRequiredAttributes();
    return DataValueType.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableDataValueType} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableDataValueType)) return false;
    MutableDataValueType other = (MutableDataValueType) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableDataValueType another) {
    return qualifier.equals(another.qualifier);
  }

  /**
   * Computes a hash code from attributes: {@code qualifier}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + qualifier.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IDataValueType}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableDataValueType")
        .add("qualifier", qualifierIsSet() ? getQualifier() : "?")
        .toString();
  }
}
