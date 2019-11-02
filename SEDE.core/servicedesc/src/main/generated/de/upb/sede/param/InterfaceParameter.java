package de.upb.sede.param;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Booleans;
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
 * Immutable implementation of {@link IInterfaceParameter}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code InterfaceParameter.builder()}.
 */
@Generated(from = "IInterfaceParameter", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class InterfaceParameter implements IInterfaceParameter {
  private final String interfaceQualifier;
  private final String paramType;
  private final boolean isOptional;
  private final String qualifier;
  private final ImmutableList<String> metaTags;
  private final String simpleName;

  private InterfaceParameter(InterfaceParameter.Builder builder) {
    this.interfaceQualifier = builder.interfaceQualifier;
    this.qualifier = builder.qualifier;
    this.metaTags = builder.metaTags.build();
    if (builder.paramType != null) {
      initShim.paramType(builder.paramType);
    }
    if (builder.isOptionalIsSet()) {
      initShim.isOptional(builder.isOptional);
    }
    if (builder.simpleName != null) {
      initShim.simpleName(builder.simpleName);
    }
    this.paramType = initShim.getParamType();
    this.isOptional = initShim.isOptional();
    this.simpleName = initShim.getSimpleName();
    this.initShim = null;
  }

  private InterfaceParameter(
      String interfaceQualifier,
      String paramType,
      boolean isOptional,
      String qualifier,
      ImmutableList<String> metaTags,
      String simpleName) {
    this.interfaceQualifier = interfaceQualifier;
    this.paramType = paramType;
    this.isOptional = isOptional;
    this.qualifier = qualifier;
    this.metaTags = metaTags;
    this.simpleName = simpleName;
    this.initShim = null;
  }

  private static final byte STAGE_INITIALIZING = -1;
  private static final byte STAGE_UNINITIALIZED = 0;
  private static final byte STAGE_INITIALIZED = 1;
  @SuppressWarnings("Immutable")
  private transient volatile InitShim initShim = new InitShim();

  @Generated(from = "IInterfaceParameter", generator = "Immutables")
  private final class InitShim {
    private byte paramTypeBuildStage = STAGE_UNINITIALIZED;
    private String paramType;

    String getParamType() {
      if (paramTypeBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (paramTypeBuildStage == STAGE_UNINITIALIZED) {
        paramTypeBuildStage = STAGE_INITIALIZING;
        this.paramType = Objects.requireNonNull(getParamTypeInitialize(), "paramType");
        paramTypeBuildStage = STAGE_INITIALIZED;
      }
      return this.paramType;
    }

    void paramType(String paramType) {
      this.paramType = paramType;
      paramTypeBuildStage = STAGE_INITIALIZED;
    }

    private byte isOptionalBuildStage = STAGE_UNINITIALIZED;
    private boolean isOptional;

    boolean isOptional() {
      if (isOptionalBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (isOptionalBuildStage == STAGE_UNINITIALIZED) {
        isOptionalBuildStage = STAGE_INITIALIZING;
        this.isOptional = isOptionalInitialize();
        isOptionalBuildStage = STAGE_INITIALIZED;
      }
      return this.isOptional;
    }

    void isOptional(boolean isOptional) {
      this.isOptional = isOptional;
      isOptionalBuildStage = STAGE_INITIALIZED;
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
      if (paramTypeBuildStage == STAGE_INITIALIZING) attributes.add("paramType");
      if (isOptionalBuildStage == STAGE_INITIALIZING) attributes.add("isOptional");
      if (simpleNameBuildStage == STAGE_INITIALIZING) attributes.add("simpleName");
      return "Cannot build InterfaceParameter, attribute initializers form cycle " + attributes;
    }
  }

  private String getParamTypeInitialize() {
    return IInterfaceParameter.super.getParamType();
  }

  private boolean isOptionalInitialize() {
    return IInterfaceParameter.super.isOptional();
  }

  private String getSimpleNameInitialize() {
    return IInterfaceParameter.super.getSimpleName();
  }

  /**
   * @return The value of the {@code interfaceQualifier} attribute
   */
  @JsonProperty("interfaceQualifier")
  @Override
  public String getInterfaceQualifier() {
    return interfaceQualifier;
  }

  /**
   * @return The value of the {@code paramType} attribute
   */
  @JsonProperty("paramType")
  @Override
  public String getParamType() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.getParamType()
        : this.paramType;
  }

  /**
   * @return The value of the {@code isOptional} attribute
   */
  @JsonProperty("isOptional")
  @Override
  public boolean isOptional() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.isOptional()
        : this.isOptional;
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
   * @return The value of the {@code metaTags} attribute
   */
  @JsonProperty("metaTags")
  @Override
  public ImmutableList<String> getMetaTags() {
    return metaTags;
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
   * Copy the current immutable object by setting a value for the {@link IInterfaceParameter#getInterfaceQualifier() interfaceQualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for interfaceQualifier
   * @return A modified copy of the {@code this} object
   */
  public final InterfaceParameter withInterfaceQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "interfaceQualifier");
    if (this.interfaceQualifier.equals(newValue)) return this;
    return new InterfaceParameter(newValue, this.paramType, this.isOptional, this.qualifier, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IInterfaceParameter#getParamType() paramType} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for paramType
   * @return A modified copy of the {@code this} object
   */
  public final InterfaceParameter withParamType(String value) {
    String newValue = Objects.requireNonNull(value, "paramType");
    if (this.paramType.equals(newValue)) return this;
    return new InterfaceParameter(
        this.interfaceQualifier,
        newValue,
        this.isOptional,
        this.qualifier,
        this.metaTags,
        this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IInterfaceParameter#isOptional() isOptional} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isOptional
   * @return A modified copy of the {@code this} object
   */
  public final InterfaceParameter withIsOptional(boolean value) {
    if (this.isOptional == value) return this;
    return new InterfaceParameter(this.interfaceQualifier, this.paramType, value, this.qualifier, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IInterfaceParameter#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final InterfaceParameter withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new InterfaceParameter(
        this.interfaceQualifier,
        this.paramType,
        this.isOptional,
        newValue,
        this.metaTags,
        this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IInterfaceParameter#getMetaTags() metaTags}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final InterfaceParameter withMetaTags(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new InterfaceParameter(
        this.interfaceQualifier,
        this.paramType,
        this.isOptional,
        this.qualifier,
        newValue,
        this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IInterfaceParameter#getMetaTags() metaTags}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of metaTags elements to set
   * @return A modified copy of {@code this} object
   */
  public final InterfaceParameter withMetaTags(Iterable<String> elements) {
    if (this.metaTags == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new InterfaceParameter(
        this.interfaceQualifier,
        this.paramType,
        this.isOptional,
        this.qualifier,
        newValue,
        this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IInterfaceParameter#getSimpleName() simpleName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for simpleName
   * @return A modified copy of the {@code this} object
   */
  public final InterfaceParameter withSimpleName(String value) {
    String newValue = Objects.requireNonNull(value, "simpleName");
    if (this.simpleName.equals(newValue)) return this;
    return new InterfaceParameter(
        this.interfaceQualifier,
        this.paramType,
        this.isOptional,
        this.qualifier,
        this.metaTags,
        newValue);
  }

  /**
   * This instance is equal to all instances of {@code InterfaceParameter} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof InterfaceParameter
        && equalTo((InterfaceParameter) another);
  }

  private boolean equalTo(InterfaceParameter another) {
    return interfaceQualifier.equals(another.interfaceQualifier)
        && paramType.equals(another.paramType)
        && isOptional == another.isOptional
        && qualifier.equals(another.qualifier);
  }

  /**
   * Computes a hash code from attributes: {@code interfaceQualifier}, {@code paramType}, {@code isOptional}, {@code qualifier}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + interfaceQualifier.hashCode();
    h += (h << 5) + paramType.hashCode();
    h += (h << 5) + Booleans.hashCode(isOptional);
    h += (h << 5) + qualifier.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code InterfaceParameter} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("InterfaceParameter")
        .omitNullValues()
        .add("interfaceQualifier", interfaceQualifier)
        .add("paramType", paramType)
        .add("isOptional", isOptional)
        .add("qualifier", qualifier)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IInterfaceParameter", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IInterfaceParameter {
    @Nullable String interfaceQualifier;
    @Nullable String paramType;
    boolean isOptional;
    boolean isOptionalIsSet;
    @Nullable String qualifier;
    @Nullable List<String> metaTags = ImmutableList.of();
    @Nullable String simpleName;
    @JsonProperty("interfaceQualifier")
    public void setInterfaceQualifier(String interfaceQualifier) {
      this.interfaceQualifier = interfaceQualifier;
    }
    @JsonProperty("paramType")
    public void setParamType(String paramType) {
      this.paramType = paramType;
    }
    @JsonProperty("isOptional")
    public void setIsOptional(boolean isOptional) {
      this.isOptional = isOptional;
      this.isOptionalIsSet = true;
    }
    @JsonProperty("qualifier")
    public void setQualifier(String qualifier) {
      this.qualifier = qualifier;
    }
    @JsonProperty("metaTags")
    public void setMetaTags(List<String> metaTags) {
      this.metaTags = metaTags;
    }
    @JsonProperty("simpleName")
    public void setSimpleName(String simpleName) {
      this.simpleName = simpleName;
    }
    @Override
    public String getInterfaceQualifier() { throw new UnsupportedOperationException(); }
    @Override
    public String getParamType() { throw new UnsupportedOperationException(); }
    @Override
    public boolean isOptional() { throw new UnsupportedOperationException(); }
    @Override
    public String getQualifier() { throw new UnsupportedOperationException(); }
    @Override
    public List<String> getMetaTags() { throw new UnsupportedOperationException(); }
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
  static InterfaceParameter fromJson(Json json) {
    InterfaceParameter.Builder builder = InterfaceParameter.builder();
    if (json.interfaceQualifier != null) {
      builder.interfaceQualifier(json.interfaceQualifier);
    }
    if (json.paramType != null) {
      builder.paramType(json.paramType);
    }
    if (json.isOptionalIsSet) {
      builder.isOptional(json.isOptional);
    }
    if (json.qualifier != null) {
      builder.qualifier(json.qualifier);
    }
    if (json.metaTags != null) {
      builder.addAllMetaTags(json.metaTags);
    }
    if (json.simpleName != null) {
      builder.simpleName(json.simpleName);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IInterfaceParameter} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable InterfaceParameter instance
   */
  public static InterfaceParameter copyOf(IInterfaceParameter instance) {
    if (instance instanceof InterfaceParameter) {
      return (InterfaceParameter) instance;
    }
    return InterfaceParameter.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link InterfaceParameter InterfaceParameter}.
   * <pre>
   * InterfaceParameter.builder()
   *    .interfaceQualifier(String) // required {@link IInterfaceParameter#getInterfaceQualifier() interfaceQualifier}
   *    .paramType(String) // optional {@link IInterfaceParameter#getParamType() paramType}
   *    .isOptional(boolean) // optional {@link IInterfaceParameter#isOptional() isOptional}
   *    .qualifier(String) // required {@link IInterfaceParameter#getQualifier() qualifier}
   *    .addMetaTags|addAllMetaTags(String) // {@link IInterfaceParameter#getMetaTags() metaTags} elements
   *    .simpleName(String) // optional {@link IInterfaceParameter#getSimpleName() simpleName}
   *    .build();
   * </pre>
   * @return A new InterfaceParameter builder
   */
  public static InterfaceParameter.Builder builder() {
    return new InterfaceParameter.Builder();
  }

  /**
   * Builds instances of type {@link InterfaceParameter InterfaceParameter}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IInterfaceParameter", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_INTERFACE_QUALIFIER = 0x1L;
    private static final long INIT_BIT_QUALIFIER = 0x2L;
    private static final long OPT_BIT_IS_OPTIONAL = 0x1L;
    private long initBits = 0x3L;
    private long optBits;

    private @Nullable String interfaceQualifier;
    private @Nullable String paramType;
    private boolean isOptional;
    private @Nullable String qualifier;
    private ImmutableList.Builder<String> metaTags = ImmutableList.builder();
    private @Nullable String simpleName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableInterfaceParameter} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableInterfaceParameter instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.interfaceQualifierIsSet()) {
        interfaceQualifier(instance.getInterfaceQualifier());
      }
      paramType(instance.getParamType());
      isOptional(instance.isOptional());
      if (instance.qualifierIsSet()) {
        qualifier(instance.getQualifier());
      }
      addAllMetaTags(instance.getMetaTags());
      simpleName(instance.getSimpleName());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.param.IInterfaceParameter} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IInterfaceParameter instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.param.IParameter} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IParameter instance) {
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

    private void from(Object object) {
      if (object instanceof MutableInterfaceParameter) {
        from((MutableInterfaceParameter) object);
        return;
      }
      if (object instanceof IInterfaceParameter) {
        IInterfaceParameter instance = (IInterfaceParameter) object;
        interfaceQualifier(instance.getInterfaceQualifier());
      }
      if (object instanceof IParameter) {
        IParameter instance = (IParameter) object;
        paramType(instance.getParamType());
        isOptional(instance.isOptional());
      }
      if (object instanceof IQualifiable) {
        IQualifiable instance = (IQualifiable) object;
        addAllMetaTags(instance.getMetaTags());
        simpleName(instance.getSimpleName());
        qualifier(instance.getQualifier());
      }
    }

    /**
     * Initializes the value for the {@link IInterfaceParameter#getInterfaceQualifier() interfaceQualifier} attribute.
     * @param interfaceQualifier The value for interfaceQualifier 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("interfaceQualifier")
    public final Builder interfaceQualifier(String interfaceQualifier) {
      this.interfaceQualifier = Objects.requireNonNull(interfaceQualifier, "interfaceQualifier");
      initBits &= ~INIT_BIT_INTERFACE_QUALIFIER;
      return this;
    }

    /**
     * Initializes the value for the {@link IInterfaceParameter#getParamType() paramType} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IInterfaceParameter#getParamType() paramType}.</em>
     * @param paramType The value for paramType 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("paramType")
    public final Builder paramType(String paramType) {
      this.paramType = Objects.requireNonNull(paramType, "paramType");
      return this;
    }

    /**
     * Initializes the value for the {@link IInterfaceParameter#isOptional() isOptional} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IInterfaceParameter#isOptional() isOptional}.</em>
     * @param isOptional The value for isOptional 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("isOptional")
    public final Builder isOptional(boolean isOptional) {
      this.isOptional = isOptional;
      optBits |= OPT_BIT_IS_OPTIONAL;
      return this;
    }

    /**
     * Initializes the value for the {@link IInterfaceParameter#getQualifier() qualifier} attribute.
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
     * Adds one element to {@link IInterfaceParameter#getMetaTags() metaTags} list.
     * @param element A metaTags element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMetaTags(String element) {
      this.metaTags.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IInterfaceParameter#getMetaTags() metaTags} list.
     * @param elements An array of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMetaTags(String... elements) {
      this.metaTags.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IInterfaceParameter#getMetaTags() metaTags} list.
     * @param elements An iterable of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("metaTags")
    public final Builder metaTags(Iterable<String> elements) {
      this.metaTags = ImmutableList.builder();
      return addAllMetaTags(elements);
    }

    /**
     * Adds elements to {@link IInterfaceParameter#getMetaTags() metaTags} list.
     * @param elements An iterable of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllMetaTags(Iterable<String> elements) {
      this.metaTags.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link IInterfaceParameter#getSimpleName() simpleName} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IInterfaceParameter#getSimpleName() simpleName}.</em>
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
     * Builds a new {@link InterfaceParameter InterfaceParameter}.
     * @return An immutable instance of InterfaceParameter
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public InterfaceParameter build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new InterfaceParameter(this);
    }

    private boolean isOptionalIsSet() {
      return (optBits & OPT_BIT_IS_OPTIONAL) != 0;
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_INTERFACE_QUALIFIER) != 0) attributes.add("interfaceQualifier");
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      return "Cannot build InterfaceParameter, some of required attributes are not set " + attributes;
    }
  }
}
