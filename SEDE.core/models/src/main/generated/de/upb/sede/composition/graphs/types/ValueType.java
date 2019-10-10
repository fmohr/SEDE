package de.upb.sede.composition.graphs.types;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.IQualifiable;
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
 * Immutable implementation of {@link IValueType}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ValueType.builder()}.
 */
@Generated(from = "IValueType", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ValueType implements IValueType {
  private final String typeClass;
  private final String qualifier;
  private final String simpleName;

  private ValueType(ValueType.Builder builder) {
    this.qualifier = builder.qualifier;
    if (builder.typeClass != null) {
      initShim.typeClass(builder.typeClass);
    }
    if (builder.simpleName != null) {
      initShim.simpleName(builder.simpleName);
    }
    this.typeClass = initShim.getTypeClass();
    this.simpleName = initShim.getSimpleName();
    this.initShim = null;
  }

  private ValueType(String typeClass, String qualifier, String simpleName) {
    this.typeClass = typeClass;
    this.qualifier = qualifier;
    this.simpleName = simpleName;
    this.initShim = null;
  }

  private static final byte STAGE_INITIALIZING = -1;
  private static final byte STAGE_UNINITIALIZED = 0;
  private static final byte STAGE_INITIALIZED = 1;
  @SuppressWarnings("Immutable")
  private transient volatile InitShim initShim = new InitShim();

  @Generated(from = "IValueType", generator = "Immutables")
  private final class InitShim {
    private byte typeClassBuildStage = STAGE_UNINITIALIZED;
    private String typeClass;

    String getTypeClass() {
      if (typeClassBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (typeClassBuildStage == STAGE_UNINITIALIZED) {
        typeClassBuildStage = STAGE_INITIALIZING;
        this.typeClass = Objects.requireNonNull(getTypeClassInitialize(), "typeClass");
        typeClassBuildStage = STAGE_INITIALIZED;
      }
      return this.typeClass;
    }

    void typeClass(String typeClass) {
      this.typeClass = typeClass;
      typeClassBuildStage = STAGE_INITIALIZED;
    }

    private byte simpleNameBuildStage = STAGE_UNINITIALIZED;
    private String simpleName;

    String getSimpleName() {
      if (simpleNameBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (simpleNameBuildStage == STAGE_UNINITIALIZED) {
        simpleNameBuildStage = STAGE_INITIALIZING;
        this.simpleName = Objects.requireNonNull(getSimpleNameInitialize(), "simpleName");
        simpleNameBuildStage = STAGE_INITIALIZED;
      }
      return this.simpleName;
    }

    void simpleName(String simpleName) {
      this.simpleName = simpleName;
      simpleNameBuildStage = STAGE_INITIALIZED;
    }

    private String formatInitCycleMessage() {
      List<String> attributes = new ArrayList<>();
      if (typeClassBuildStage == STAGE_INITIALIZING) attributes.add("typeClass");
      if (simpleNameBuildStage == STAGE_INITIALIZING) attributes.add("simpleName");
      return "Cannot build ValueType, attribute initializers form cycle " + attributes;
    }
  }

  private String getTypeClassInitialize() {
    return IValueType.super.getTypeClass();
  }

  private String getSimpleNameInitialize() {
    return IValueType.super.getSimpleName();
  }

  /**
   * @return The value of the {@code typeClass} attribute
   */
  @JsonProperty("typeClass")
  @Override
  public String getTypeClass() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.getTypeClass()
        : this.typeClass;
  }

  /**
   * @return The value of the {@code qualifier} attribute
   */
  @JsonProperty("qualifier")
  @Override
  public String getQualifier() {
    return qualifier;
  }

  /**
   * @return The value of the {@code simpleName} attribute
   */
  @JsonProperty("simpleName")
  @Override
  public String getSimpleName() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.getSimpleName()
        : this.simpleName;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IValueType#getTypeClass() typeClass} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for typeClass
   * @return A modified copy of the {@code this} object
   */
  public final ValueType withTypeClass(String value) {
    String newValue = Objects.requireNonNull(value, "typeClass");
    if (this.typeClass.equals(newValue)) return this;
    return new ValueType(newValue, this.qualifier, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IValueType#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final ValueType withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new ValueType(this.typeClass, newValue, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IValueType#getSimpleName() simpleName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for simpleName
   * @return A modified copy of the {@code this} object
   */
  public final ValueType withSimpleName(String value) {
    String newValue = Objects.requireNonNull(value, "simpleName");
    if (this.simpleName.equals(newValue)) return this;
    return new ValueType(this.typeClass, this.qualifier, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ValueType} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ValueType
        && equalTo((ValueType) another);
  }

  private boolean equalTo(ValueType another) {
    return typeClass.equals(another.typeClass)
        && qualifier.equals(another.qualifier);
  }

  /**
   * Computes a hash code from attributes: {@code typeClass}, {@code qualifier}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + typeClass.hashCode();
    h += (h << 5) + qualifier.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ValueType} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ValueType")
        .omitNullValues()
        .add("typeClass", typeClass)
        .add("qualifier", qualifier)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IValueType", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IValueType {
    @Nullable String typeClass;
    @Nullable String qualifier;
    @Nullable String simpleName;
    @JsonProperty("typeClass")
    public void setTypeClass(String typeClass) {
      this.typeClass = typeClass;
    }
    @JsonProperty("qualifier")
    public void setQualifier(String qualifier) {
      this.qualifier = qualifier;
    }
    @JsonProperty("simpleName")
    public void setSimpleName(String simpleName) {
      this.simpleName = simpleName;
    }
    @Override
    public String getTypeClass() { throw new UnsupportedOperationException(); }
    @Override
    public String getQualifier() { throw new UnsupportedOperationException(); }
    @Override
    public String getSimpleName() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static ValueType fromJson(Json json) {
    ValueType.Builder builder = ValueType.builder();
    if (json.typeClass != null) {
      builder.typeClass(json.typeClass);
    }
    if (json.qualifier != null) {
      builder.qualifier(json.qualifier);
    }
    if (json.simpleName != null) {
      builder.simpleName(json.simpleName);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IValueType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ValueType instance
   */
  public static ValueType copyOf(IValueType instance) {
    if (instance instanceof ValueType) {
      return (ValueType) instance;
    }
    return ValueType.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ValueType ValueType}.
   * <pre>
   * ValueType.builder()
   *    .typeClass(String) // optional {@link IValueType#getTypeClass() typeClass}
   *    .qualifier(String) // required {@link IValueType#getQualifier() qualifier}
   *    .simpleName(String) // optional {@link IValueType#getSimpleName() simpleName}
   *    .build();
   * </pre>
   * @return A new ValueType builder
   */
  public static ValueType.Builder builder() {
    return new ValueType.Builder();
  }

  /**
   * Builds instances of type {@link ValueType ValueType}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IValueType", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_QUALIFIER = 0x1L;
    private long initBits = 0x1L;

    private @Nullable String typeClass;
    private @Nullable String qualifier;
    private @Nullable String simpleName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableValueType} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableValueType instance) {
      Objects.requireNonNull(instance, "instance");
      typeClass(instance.getTypeClass());
      if (instance.qualifierIsSet()) {
        qualifier(instance.getQualifier());
      }
      simpleName(instance.getSimpleName());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.types.TypeClass} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(TypeClass instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.IQualifiable} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IQualifiable instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.types.IValueType} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IValueType instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof MutableValueType) {
        from((MutableValueType) object);
        return;
      }
      if (object instanceof TypeClass) {
        TypeClass instance = (TypeClass) object;
        typeClass(instance.getTypeClass());
      }
      if (object instanceof IQualifiable) {
        IQualifiable instance = (IQualifiable) object;
        simpleName(instance.getSimpleName());
        qualifier(instance.getQualifier());
      }
    }

    /**
     * Initializes the value for the {@link IValueType#getTypeClass() typeClass} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IValueType#getTypeClass() typeClass}.</em>
     * @param typeClass The value for typeClass 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("typeClass")
    public final Builder typeClass(String typeClass) {
      this.typeClass = Objects.requireNonNull(typeClass, "typeClass");
      return this;
    }

    /**
     * Initializes the value for the {@link IValueType#getQualifier() qualifier} attribute.
     * @param qualifier The value for qualifier 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("qualifier")
    public final Builder qualifier(String qualifier) {
      this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
      initBits &= ~INIT_BIT_QUALIFIER;
      return this;
    }

    /**
     * Initializes the value for the {@link IValueType#getSimpleName() simpleName} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IValueType#getSimpleName() simpleName}.</em>
     * @param simpleName The value for simpleName 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("simpleName")
    public final Builder simpleName(String simpleName) {
      this.simpleName = Objects.requireNonNull(simpleName, "simpleName");
      return this;
    }

    /**
     * Builds a new {@link ValueType ValueType}.
     * @return An immutable instance of ValueType
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ValueType build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ValueType(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      return "Cannot build ValueType, some of required attributes are not set " + attributes;
    }
  }
}
