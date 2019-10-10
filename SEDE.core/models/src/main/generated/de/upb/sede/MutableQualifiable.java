package de.upb.sede;

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
 * A modifiable implementation of the {@link IQualifiable IQualifiable} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableQualifiable is not thread-safe</em>
 * @see Qualifiable
 */
@Generated(from = "IQualifiable", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IQualifiable"})
@NotThreadSafe
public final class MutableQualifiable implements IQualifiable {
  private static final long INIT_BIT_QUALIFIER = 0x1L;
  private long initBits = 0x1L;

  private String qualifier;
  private String simpleName;

  private MutableQualifiable() {}

  /**
   * Construct a modifiable instance of {@code IQualifiable}.
   * @return A new modifiable instance
   */
  public static MutableQualifiable create() {
    return new MutableQualifiable();
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
        : IQualifiable.super.getSimpleName();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableQualifiable clear() {
    initBits = 0x1L;
    qualifier = null;
    simpleName = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IQualifiable} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableQualifiable from(IQualifiable instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableQualifiable) {
      from((MutableQualifiable) instance);
      return this;
    }
    setQualifier(instance.getQualifier());
    setSimpleName(instance.getSimpleName());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IQualifiable} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableQualifiable from(MutableQualifiable instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.qualifierIsSet()) {
      setQualifier(instance.getQualifier());
    }
    setSimpleName(instance.getSimpleName());
    return this;
  }

  /**
   * Assigns a value to the {@link IQualifiable#getQualifier() qualifier} attribute.
   * @param qualifier The value for qualifier
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableQualifiable setQualifier(String qualifier) {
    this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
    initBits &= ~INIT_BIT_QUALIFIER;
    return this;
  }

  /**
   * Assigns a value to the {@link IQualifiable#getSimpleName() simpleName} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IQualifiable#getSimpleName() simpleName}.</em>
   * @param simpleName The value for simpleName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableQualifiable setSimpleName(String simpleName) {
    this.simpleName = Objects.requireNonNull(simpleName, "simpleName");
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IQualifiable#getQualifier() qualifier} is set.
   * @return {@code true} if set
   */
  public final boolean qualifierIsSet() {
    return (initBits & INIT_BIT_QUALIFIER) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IQualifiable#getSimpleName() simpleName} is set.
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
  public final MutableQualifiable unsetQualifier() {
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
    return "Qualifiable is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link Qualifiable Qualifiable}.
   * @return An immutable instance of Qualifiable
   */
  public final Qualifiable toImmutable() {
    checkRequiredAttributes();
    return Qualifiable.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableQualifiable} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableQualifiable)) return false;
    MutableQualifiable other = (MutableQualifiable) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableQualifiable another) {
    String simpleName = getSimpleName();
    return qualifier.equals(another.qualifier)
        && simpleName.equals(another.getSimpleName());
  }

  /**
   * Computes a hash code from attributes: {@code qualifier}, {@code simpleName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + qualifier.hashCode();
    String simpleName = getSimpleName();
    h += (h << 5) + simpleName.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IQualifiable}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableQualifiable")
        .add("qualifier", qualifierIsSet() ? getQualifier() : "?")
        .add("simpleName", getSimpleName())
        .toString();
  }
}
