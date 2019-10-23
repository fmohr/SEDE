package de.upb.sede.composition.graphs.types;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
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
 * Immutable implementation of {@link IServiceInstanceType}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ServiceInstanceType.builder()}.
 */
@Generated(from = "IServiceInstanceType", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ServiceInstanceType implements IServiceInstanceType {
  private final String typeClass;
  private final String qualifier;
  private final ImmutableList<String> metaTags;
  private final String simpleName;

  private ServiceInstanceType(ServiceInstanceType.Builder builder) {
    this.qualifier = builder.qualifier;
    this.metaTags = builder.metaTags.build();
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

  private ServiceInstanceType(
      String typeClass,
      String qualifier,
      ImmutableList<String> metaTags,
      String simpleName) {
    this.typeClass = typeClass;
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

  @Generated(from = "IServiceInstanceType", generator = "Immutables")
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
      return "Cannot build ServiceInstanceType, attribute initializers form cycle " + attributes;
    }
  }

  private String getTypeClassInitialize() {
    return IServiceInstanceType.super.getTypeClass();
  }

  private String getSimpleNameInitialize() {
    return IServiceInstanceType.super.getSimpleName();
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
   * Copy the current immutable object by setting a value for the {@link IServiceInstanceType#getTypeClass() typeClass} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for typeClass
   * @return A modified copy of the {@code this} object
   */
  public final ServiceInstanceType withTypeClass(String value) {
    String newValue = Objects.requireNonNull(value, "typeClass");
    if (this.typeClass.equals(newValue)) return this;
    return new ServiceInstanceType(newValue, this.qualifier, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IServiceInstanceType#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final ServiceInstanceType withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new ServiceInstanceType(this.typeClass, newValue, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IServiceInstanceType#getMetaTags() metaTags}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceInstanceType withMetaTags(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ServiceInstanceType(this.typeClass, this.qualifier, newValue, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IServiceInstanceType#getMetaTags() metaTags}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of metaTags elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceInstanceType withMetaTags(Iterable<String> elements) {
    if (this.metaTags == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ServiceInstanceType(this.typeClass, this.qualifier, newValue, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IServiceInstanceType#getSimpleName() simpleName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for simpleName
   * @return A modified copy of the {@code this} object
   */
  public final ServiceInstanceType withSimpleName(String value) {
    String newValue = Objects.requireNonNull(value, "simpleName");
    if (this.simpleName.equals(newValue)) return this;
    return new ServiceInstanceType(this.typeClass, this.qualifier, this.metaTags, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ServiceInstanceType} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ServiceInstanceType
        && equalTo((ServiceInstanceType) another);
  }

  private boolean equalTo(ServiceInstanceType another) {
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
   * Prints the immutable value {@code ServiceInstanceType} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ServiceInstanceType")
        .omitNullValues()
        .add("typeClass", typeClass)
        .add("qualifier", qualifier)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IServiceInstanceType", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IServiceInstanceType {
    @Nullable String typeClass;
    @Nullable String qualifier;
    @Nullable List<String> metaTags = ImmutableList.of();
    @Nullable String simpleName;
    @JsonProperty("typeClass")
    public void setTypeClass(String typeClass) {
      this.typeClass = typeClass;
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
    public String getTypeClass() { throw new UnsupportedOperationException(); }
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
  static ServiceInstanceType fromJson(Json json) {
    ServiceInstanceType.Builder builder = ServiceInstanceType.builder();
    if (json.typeClass != null) {
      builder.typeClass(json.typeClass);
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
   * Creates an immutable copy of a {@link IServiceInstanceType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ServiceInstanceType instance
   */
  public static ServiceInstanceType copyOf(IServiceInstanceType instance) {
    if (instance instanceof ServiceInstanceType) {
      return (ServiceInstanceType) instance;
    }
    return ServiceInstanceType.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ServiceInstanceType ServiceInstanceType}.
   * <pre>
   * ServiceInstanceType.builder()
   *    .typeClass(String) // optional {@link IServiceInstanceType#getTypeClass() typeClass}
   *    .qualifier(String) // required {@link IServiceInstanceType#getQualifier() qualifier}
   *    .addMetaTags|addAllMetaTags(String) // {@link IServiceInstanceType#getMetaTags() metaTags} elements
   *    .simpleName(String) // optional {@link IServiceInstanceType#getSimpleName() simpleName}
   *    .build();
   * </pre>
   * @return A new ServiceInstanceType builder
   */
  public static ServiceInstanceType.Builder builder() {
    return new ServiceInstanceType.Builder();
  }

  /**
   * Builds instances of type {@link ServiceInstanceType ServiceInstanceType}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IServiceInstanceType", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_QUALIFIER = 0x1L;
    private long initBits = 0x1L;

    private @Nullable String typeClass;
    private @Nullable String qualifier;
    private ImmutableList.Builder<String> metaTags = ImmutableList.builder();
    private @Nullable String simpleName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableServiceInstanceType} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableServiceInstanceType instance) {
      Objects.requireNonNull(instance, "instance");
      typeClass(instance.getTypeClass());
      if (instance.qualifierIsSet()) {
        qualifier(instance.getQualifier());
      }
      addAllMetaTags(instance.getMetaTags());
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
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.types.IServiceInstanceType} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IServiceInstanceType instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof MutableServiceInstanceType) {
        from((MutableServiceInstanceType) object);
        return;
      }
      if (object instanceof TypeClass) {
        TypeClass instance = (TypeClass) object;
        typeClass(instance.getTypeClass());
      }
      if (object instanceof IQualifiable) {
        IQualifiable instance = (IQualifiable) object;
        addAllMetaTags(instance.getMetaTags());
        simpleName(instance.getSimpleName());
        qualifier(instance.getQualifier());
      }
    }

    /**
     * Initializes the value for the {@link IServiceInstanceType#getTypeClass() typeClass} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IServiceInstanceType#getTypeClass() typeClass}.</em>
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
     * Initializes the value for the {@link IServiceInstanceType#getQualifier() qualifier} attribute.
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
     * Adds one element to {@link IServiceInstanceType#getMetaTags() metaTags} list.
     * @param element A metaTags element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMetaTags(String element) {
      this.metaTags.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IServiceInstanceType#getMetaTags() metaTags} list.
     * @param elements An array of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMetaTags(String... elements) {
      this.metaTags.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IServiceInstanceType#getMetaTags() metaTags} list.
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
     * Adds elements to {@link IServiceInstanceType#getMetaTags() metaTags} list.
     * @param elements An iterable of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllMetaTags(Iterable<String> elements) {
      this.metaTags.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link IServiceInstanceType#getSimpleName() simpleName} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IServiceInstanceType#getSimpleName() simpleName}.</em>
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
     * Builds a new {@link ServiceInstanceType ServiceInstanceType}.
     * @return An immutable instance of ServiceInstanceType
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ServiceInstanceType build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ServiceInstanceType(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      return "Cannot build ServiceInstanceType, some of required attributes are not set " + attributes;
    }
  }
}
