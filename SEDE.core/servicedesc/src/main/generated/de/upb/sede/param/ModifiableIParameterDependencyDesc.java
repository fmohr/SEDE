package de.upb.sede.param;

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
 * A modifiable implementation of the {@link IParameterDependencyDesc IParameterDependencyDesc} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>ModifiableIParameterDependencyDesc is not thread-safe</em>
 * @see ImmutableIParameterDependencyDesc
 */
@Generated(from = "IParameterDependencyDesc", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IParameterDependencyDesc"})
@NotThreadSafe
public final class ModifiableIParameterDependencyDesc implements IParameterDependencyDesc {
  private static final long INIT_BIT_PREMISE = 0x1L;
  private static final long INIT_BIT_CONCLUSION = 0x2L;
  private long initBits = 0x3L;

  private String premise;
  private String conclusion;

  private ModifiableIParameterDependencyDesc() {}

  /**
   * Construct a modifiable instance of {@code IParameterDependencyDesc}.
   * @return A new modifiable instance
   */
  public static ModifiableIParameterDependencyDesc create() {
    return new ModifiableIParameterDependencyDesc();
  }

  /**
   * @return value of {@code premise} attribute
   */
  @JsonProperty("premise")
  @Override
  public final String getPremise() {
    if (!premiseIsSet()) {
      checkRequiredAttributes();
    }
    return premise;
  }

  /**
   * @return value of {@code conclusion} attribute
   */
  @JsonProperty("conclusion")
  @Override
  public final String getConclusion() {
    if (!conclusionIsSet()) {
      checkRequiredAttributes();
    }
    return conclusion;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableIParameterDependencyDesc clear() {
    initBits = 0x3L;
    premise = null;
    conclusion = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IParameterDependencyDesc} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public ModifiableIParameterDependencyDesc from(IParameterDependencyDesc instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof ModifiableIParameterDependencyDesc) {
      from((ModifiableIParameterDependencyDesc) instance);
      return this;
    }
    setPremise(instance.getPremise());
    setConclusion(instance.getConclusion());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IParameterDependencyDesc} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public ModifiableIParameterDependencyDesc from(ModifiableIParameterDependencyDesc instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.premiseIsSet()) {
      setPremise(instance.getPremise());
    }
    if (instance.conclusionIsSet()) {
      setConclusion(instance.getConclusion());
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IParameterDependencyDesc#getPremise() premise} attribute.
   * @param premise The value for premise
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableIParameterDependencyDesc setPremise(String premise) {
    this.premise = Objects.requireNonNull(premise, "premise");
    initBits &= ~INIT_BIT_PREMISE;
    return this;
  }

  /**
   * Assigns a value to the {@link IParameterDependencyDesc#getConclusion() conclusion} attribute.
   * @param conclusion The value for conclusion
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableIParameterDependencyDesc setConclusion(String conclusion) {
    this.conclusion = Objects.requireNonNull(conclusion, "conclusion");
    initBits &= ~INIT_BIT_CONCLUSION;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IParameterDependencyDesc#getPremise() premise} is set.
   * @return {@code true} if set
   */
  public final boolean premiseIsSet() {
    return (initBits & INIT_BIT_PREMISE) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IParameterDependencyDesc#getConclusion() conclusion} is set.
   * @return {@code true} if set
   */
  public final boolean conclusionIsSet() {
    return (initBits & INIT_BIT_CONCLUSION) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableIParameterDependencyDesc unsetPremise() {
    initBits |= INIT_BIT_PREMISE;
    premise = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableIParameterDependencyDesc unsetConclusion() {
    initBits |= INIT_BIT_CONCLUSION;
    conclusion = null;
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
    if (!premiseIsSet()) attributes.add("premise");
    if (!conclusionIsSet()) attributes.add("conclusion");
    return "IParameterDependencyDesc is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link ImmutableIParameterDependencyDesc ImmutableIParameterDependencyDesc}.
   * @return An immutable instance of IParameterDependencyDesc
   */
  public final ImmutableIParameterDependencyDesc toImmutable() {
    checkRequiredAttributes();
    return ImmutableIParameterDependencyDesc.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code ModifiableIParameterDependencyDesc} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof ModifiableIParameterDependencyDesc)) return false;
    ModifiableIParameterDependencyDesc other = (ModifiableIParameterDependencyDesc) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(ModifiableIParameterDependencyDesc another) {
    return premise.equals(another.premise)
        && conclusion.equals(another.conclusion);
  }

  /**
   * Computes a hash code from attributes: {@code premise}, {@code conclusion}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + premise.hashCode();
    h += (h << 5) + conclusion.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IParameterDependencyDesc}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ModifiableIParameterDependencyDesc")
        .add("premise", premiseIsSet() ? getPremise() : "?")
        .add("conclusion", conclusionIsSet() ? getConclusion() : "?")
        .toString();
  }
}
