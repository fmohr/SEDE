package de.upb.sede;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IFieldAccess IFieldAccess} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableFieldAccess is not thread-safe</em>
 * @see FieldAccess
 */
@Generated(from = "IFieldAccess", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IFieldAccess"})
@NotThreadSafe
public final class MutableFieldAccess implements IFieldAccess {
  private static final long INIT_BIT_FIELD_NAME = 0x1L;
  private static final long OPT_BIT_IS_DEREFERENCE = 0x1L;
  private long initBits = 0x1L;
  private long optBits;

  private boolean isDereference;
  private @Nullable IFieldAccess member;
  private String fieldName;

  private MutableFieldAccess() {}

  /**
   * Construct a modifiable instance of {@code IFieldAccess}.
   * @return A new modifiable instance
   */
  public static MutableFieldAccess create() {
    return new MutableFieldAccess();
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code isDereference} attribute
   */
  @JsonProperty("isDereference")
  @Override
  public final boolean isDereference() {
    return isDereferenceIsSet()
        ? isDereference
        : IFieldAccess.super.isDereference();
  }

  /**
   * @return value of {@code member} attribute, may be {@code null}
   */
  @JsonProperty("member")
  @Override
  public final @Nullable IFieldAccess getMember() {
    return member;
  }

  /**
   * @return value of {@code fieldName} attribute
   */
  @JsonProperty("fieldName")
  @Override
  public final String getFieldName() {
    if (!fieldNameIsSet()) {
      checkRequiredAttributes();
    }
    return fieldName;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFieldAccess clear() {
    initBits = 0x1L;
    optBits = 0;
    isDereference = false;
    member = null;
    fieldName = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.IFieldContainer} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFieldAccess from(IFieldContainer instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.IFieldAccess} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFieldAccess from(IFieldAccess instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IFieldAccess} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableFieldAccess from(MutableFieldAccess instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableFieldAccess) {
      MutableFieldAccess instance = (MutableFieldAccess) object;
      setIsDereference(instance.isDereference());
      @Nullable IFieldAccess memberValue = instance.getMember();
      if (memberValue != null) {
        setMember(memberValue);
      }
      if (instance.fieldNameIsSet()) {
        setFieldName(instance.getFieldName());
      }
      return;
    }
    if (object instanceof IFieldContainer) {
      IFieldContainer instance = (IFieldContainer) object;
      setFieldName(instance.getFieldName());
    }
    if (object instanceof IFieldAccess) {
      IFieldAccess instance = (IFieldAccess) object;
      @Nullable IFieldAccess memberValue = instance.getMember();
      if (memberValue != null) {
        setMember(memberValue);
      }
      setIsDereference(instance.isDereference());
    }
  }

  /**
   * Assigns a value to the {@link IFieldAccess#isDereference() isDereference} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IFieldAccess#isDereference() isDereference}.</em>
   * @param isDereference The value for isDereference
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFieldAccess setIsDereference(boolean isDereference) {
    this.isDereference = isDereference;
    optBits |= OPT_BIT_IS_DEREFERENCE;
    return this;
  }

  /**
   * Assigns a value to the {@link IFieldAccess#getMember() member} attribute.
   * @param member The value for member, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFieldAccess setMember(@Nullable IFieldAccess member) {
    this.member = member;
    return this;
  }

  /**
   * Assigns a value to the {@link IFieldAccess#getFieldName() fieldName} attribute.
   * @param fieldName The value for fieldName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableFieldAccess setFieldName(String fieldName) {
    this.fieldName = Objects.requireNonNull(fieldName, "fieldName");
    initBits &= ~INIT_BIT_FIELD_NAME;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IFieldAccess#getFieldName() fieldName} is set.
   * @return {@code true} if set
   */
  public final boolean fieldNameIsSet() {
    return (initBits & INIT_BIT_FIELD_NAME) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IFieldAccess#isDereference() isDereference} is set.
   * @return {@code true} if set
   */
  public final boolean isDereferenceIsSet() {
    return (optBits & OPT_BIT_IS_DEREFERENCE) != 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableFieldAccess unsetFieldName() {
    initBits |= INIT_BIT_FIELD_NAME;
    fieldName = null;
    return this;
  }
  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableFieldAccess unsetIsDereference() {
    optBits |= 0;
    isDereference = false;
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
    if (!fieldNameIsSet()) attributes.add("fieldName");
    return "FieldAccess is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link FieldAccess FieldAccess}.
   * @return An immutable instance of FieldAccess
   */
  public final FieldAccess toImmutable() {
    checkRequiredAttributes();
    return FieldAccess.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableFieldAccess} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableFieldAccess)) return false;
    MutableFieldAccess other = (MutableFieldAccess) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableFieldAccess another) {
    boolean isDereference = isDereference();
    return isDereference == another.isDereference()
        && Objects.equals(member, another.member)
        && fieldName.equals(another.fieldName);
  }

  /**
   * Computes a hash code from attributes: {@code isDereference}, {@code member}, {@code fieldName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    boolean isDereference = isDereference();
    h += (h << 5) + Booleans.hashCode(isDereference);
    h += (h << 5) + Objects.hashCode(member);
    h += (h << 5) + fieldName.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IFieldAccess}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableFieldAccess")
        .add("isDereference", isDereference())
        .add("member", getMember())
        .add("fieldName", fieldNameIsSet() ? getFieldName() : "?")
        .toString();
  }
}
