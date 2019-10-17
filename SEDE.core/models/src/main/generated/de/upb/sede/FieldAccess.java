package de.upb.sede;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link IFieldAccess}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code FieldAccess.builder()}.
 */
@Generated(from = "IFieldAccess", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class FieldAccess implements IFieldAccess {
  private final boolean isDereference;
  private final @Nullable IFieldAccess member;
  private final String fieldName;

  private FieldAccess(FieldAccess.Builder builder) {
    this.member = builder.member;
    this.fieldName = builder.fieldName;
    this.isDereference = builder.isDereferenceIsSet()
        ? builder.isDereference
        : IFieldAccess.super.isDereference();
  }

  private FieldAccess(
      boolean isDereference,
      @Nullable IFieldAccess member,
      String fieldName) {
    this.isDereference = isDereference;
    this.member = member;
    this.fieldName = fieldName;
  }

  /**
   * @return The value of the {@code isDereference} attribute
   */
  @JsonProperty("isDereference")
  @Override
  public boolean isDereference() {
    return isDereference;
  }

  /**
   * @return The value of the {@code member} attribute
   */
  @JsonProperty("member")
  @Override
  public @Nullable IFieldAccess getMember() {
    return member;
  }

  /**
   * Returns the field name that is being refered at.
   * @return Referenced field name
   */
  @JsonProperty("fieldName")
  @Override
  public String getFieldName() {
    return fieldName;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IFieldAccess#isDereference() isDereference} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isDereference
   * @return A modified copy of the {@code this} object
   */
  public final FieldAccess withIsDereference(boolean value) {
    if (this.isDereference == value) return this;
    return new FieldAccess(value, this.member, this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IFieldAccess#getMember() member} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for member (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final FieldAccess withMember(@Nullable IFieldAccess value) {
    if (this.member == value) return this;
    return new FieldAccess(this.isDereference, value, this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IFieldAccess#getFieldName() fieldName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for fieldName
   * @return A modified copy of the {@code this} object
   */
  public final FieldAccess withFieldName(String value) {
    String newValue = Objects.requireNonNull(value, "fieldName");
    if (this.fieldName.equals(newValue)) return this;
    return new FieldAccess(this.isDereference, this.member, newValue);
  }

  /**
   * This instance is equal to all instances of {@code FieldAccess} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof FieldAccess
        && equalTo((FieldAccess) another);
  }

  private boolean equalTo(FieldAccess another) {
    return isDereference == another.isDereference
        && Objects.equals(member, another.member)
        && fieldName.equals(another.fieldName);
  }

  /**
   * Computes a hash code from attributes: {@code isDereference}, {@code member}, {@code fieldName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Booleans.hashCode(isDereference);
    h += (h << 5) + Objects.hashCode(member);
    h += (h << 5) + fieldName.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code FieldAccess} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("FieldAccess")
        .omitNullValues()
        .add("isDereference", isDereference)
        .add("member", member)
        .add("fieldName", fieldName)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IFieldAccess", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IFieldAccess {
    boolean isDereference;
    boolean isDereferenceIsSet;
    @Nullable IFieldAccess member;
    @Nullable String fieldName;
    @JsonProperty("isDereference")
    public void setIsDereference(boolean isDereference) {
      this.isDereference = isDereference;
      this.isDereferenceIsSet = true;
    }
    @JsonProperty("member")
    public void setMember(@Nullable IFieldAccess member) {
      this.member = member;
    }
    @JsonProperty("fieldName")
    public void setFieldName(String fieldName) {
      this.fieldName = fieldName;
    }
    @Override
    public boolean isDereference() { throw new UnsupportedOperationException(); }
    @Override
    public IFieldAccess getMember() { throw new UnsupportedOperationException(); }
    @Override
    public String getFieldName() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static FieldAccess fromJson(Json json) {
    FieldAccess.Builder builder = FieldAccess.builder();
    if (json.isDereferenceIsSet) {
      builder.isDereference(json.isDereference);
    }
    if (json.member != null) {
      builder.member(json.member);
    }
    if (json.fieldName != null) {
      builder.fieldName(json.fieldName);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IFieldAccess} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable FieldAccess instance
   */
  public static FieldAccess copyOf(IFieldAccess instance) {
    if (instance instanceof FieldAccess) {
      return (FieldAccess) instance;
    }
    return FieldAccess.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link FieldAccess FieldAccess}.
   * <pre>
   * FieldAccess.builder()
   *    .isDereference(boolean) // optional {@link IFieldAccess#isDereference() isDereference}
   *    .member(de.upb.sede.IFieldAccess | null) // nullable {@link IFieldAccess#getMember() member}
   *    .fieldName(String) // required {@link IFieldAccess#getFieldName() fieldName}
   *    .build();
   * </pre>
   * @return A new FieldAccess builder
   */
  public static FieldAccess.Builder builder() {
    return new FieldAccess.Builder();
  }

  /**
   * Builds instances of type {@link FieldAccess FieldAccess}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IFieldAccess", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_FIELD_NAME = 0x1L;
    private static final long OPT_BIT_IS_DEREFERENCE = 0x1L;
    private long initBits = 0x1L;
    private long optBits;

    private boolean isDereference;
    private @Nullable IFieldAccess member;
    private @Nullable String fieldName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableFieldAccess} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableFieldAccess instance) {
      Objects.requireNonNull(instance, "instance");
      isDereference(instance.isDereference());
      @Nullable IFieldAccess memberValue = instance.getMember();
      if (memberValue != null) {
        member(memberValue);
      }
      if (instance.fieldNameIsSet()) {
        fieldName(instance.getFieldName());
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.IFieldContainer} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IFieldContainer instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.IFieldAccess} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IFieldAccess instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof MutableFieldAccess) {
        from((MutableFieldAccess) object);
        return;
      }
      if (object instanceof IFieldContainer) {
        IFieldContainer instance = (IFieldContainer) object;
        fieldName(instance.getFieldName());
      }
      if (object instanceof IFieldAccess) {
        IFieldAccess instance = (IFieldAccess) object;
        @Nullable IFieldAccess memberValue = instance.getMember();
        if (memberValue != null) {
          member(memberValue);
        }
        isDereference(instance.isDereference());
      }
    }

    /**
     * Initializes the value for the {@link IFieldAccess#isDereference() isDereference} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IFieldAccess#isDereference() isDereference}.</em>
     * @param isDereference The value for isDereference 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("isDereference")
    public final Builder isDereference(boolean isDereference) {
      this.isDereference = isDereference;
      optBits |= OPT_BIT_IS_DEREFERENCE;
      return this;
    }

    /**
     * Initializes the value for the {@link IFieldAccess#getMember() member} attribute.
     * @param member The value for member (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("member")
    public final Builder member(@Nullable IFieldAccess member) {
      this.member = member;
      return this;
    }

    /**
     * Initializes the value for the {@link IFieldAccess#getFieldName() fieldName} attribute.
     * @param fieldName The value for fieldName 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("fieldName")
    public final Builder fieldName(String fieldName) {
      this.fieldName = Objects.requireNonNull(fieldName, "fieldName");
      initBits &= ~INIT_BIT_FIELD_NAME;
      return this;
    }

    /**
     * Builds a new {@link FieldAccess FieldAccess}.
     * @return An immutable instance of FieldAccess
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public FieldAccess build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new FieldAccess(this);
    }

    private boolean isDereferenceIsSet() {
      return (optBits & OPT_BIT_IS_DEREFERENCE) != 0;
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_FIELD_NAME) != 0) attributes.add("fieldName");
      return "Cannot build FieldAccess, some of required attributes are not set " + attributes;
    }
  }
}
