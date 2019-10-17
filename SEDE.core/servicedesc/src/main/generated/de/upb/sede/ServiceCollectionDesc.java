package de.upb.sede;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.exec.IServiceDesc;
import de.upb.sede.types.IDataTypeDesc;
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
 * Immutable implementation of {@link IServiceCollectionDesc}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ServiceCollectionDesc.builder()}.
 */
@Generated(from = "IServiceCollectionDesc", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ServiceCollectionDesc implements IServiceCollectionDesc {
  private final ImmutableList<IServiceDesc> services;
  private final ImmutableList<IDataTypeDesc> dataTypes;
  private final ImmutableList<String> comments;
  private final String qualifier;
  private final ImmutableList<String> metaTags;
  private final String simpleName;

  private ServiceCollectionDesc(ServiceCollectionDesc.Builder builder) {
    this.services = builder.services.build();
    this.dataTypes = builder.dataTypes.build();
    this.comments = builder.comments.build();
    this.qualifier = builder.qualifier;
    this.metaTags = builder.metaTags.build();
    this.simpleName = builder.simpleName != null
        ? builder.simpleName
        : Objects.requireNonNull(IServiceCollectionDesc.super.getSimpleName(), "simpleName");
  }

  private ServiceCollectionDesc(
      ImmutableList<IServiceDesc> services,
      ImmutableList<IDataTypeDesc> dataTypes,
      ImmutableList<String> comments,
      String qualifier,
      ImmutableList<String> metaTags,
      String simpleName) {
    this.services = services;
    this.dataTypes = dataTypes;
    this.comments = comments;
    this.qualifier = qualifier;
    this.metaTags = metaTags;
    this.simpleName = simpleName;
  }

  /**
   * @return The value of the {@code services} attribute
   */
  @JsonProperty("services")
  @Override
  public ImmutableList<IServiceDesc> getServices() {
    return services;
  }

  /**
   * @return The value of the {@code dataTypes} attribute
   */
  @JsonProperty("dataTypes")
  @Override
  public ImmutableList<IDataTypeDesc> getDataTypes() {
    return dataTypes;
  }

  /**
   * @return The value of the {@code comments} attribute
   */
  @JsonProperty("comments")
  @Override
  public ImmutableList<String> getComments() {
    return comments;
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
    return simpleName;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IServiceCollectionDesc#getServices() services}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceCollectionDesc withServices(IServiceDesc... elements) {
    ImmutableList<IServiceDesc> newValue = ImmutableList.copyOf(elements);
    return new ServiceCollectionDesc(newValue, this.dataTypes, this.comments, this.qualifier, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IServiceCollectionDesc#getServices() services}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of services elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceCollectionDesc withServices(Iterable<? extends IServiceDesc> elements) {
    if (this.services == elements) return this;
    ImmutableList<IServiceDesc> newValue = ImmutableList.copyOf(elements);
    return new ServiceCollectionDesc(newValue, this.dataTypes, this.comments, this.qualifier, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IServiceCollectionDesc#getDataTypes() dataTypes}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceCollectionDesc withDataTypes(IDataTypeDesc... elements) {
    ImmutableList<IDataTypeDesc> newValue = ImmutableList.copyOf(elements);
    return new ServiceCollectionDesc(this.services, newValue, this.comments, this.qualifier, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IServiceCollectionDesc#getDataTypes() dataTypes}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of dataTypes elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceCollectionDesc withDataTypes(Iterable<? extends IDataTypeDesc> elements) {
    if (this.dataTypes == elements) return this;
    ImmutableList<IDataTypeDesc> newValue = ImmutableList.copyOf(elements);
    return new ServiceCollectionDesc(this.services, newValue, this.comments, this.qualifier, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IServiceCollectionDesc#getComments() comments}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceCollectionDesc withComments(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ServiceCollectionDesc(this.services, this.dataTypes, newValue, this.qualifier, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IServiceCollectionDesc#getComments() comments}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of comments elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceCollectionDesc withComments(Iterable<String> elements) {
    if (this.comments == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ServiceCollectionDesc(this.services, this.dataTypes, newValue, this.qualifier, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IServiceCollectionDesc#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final ServiceCollectionDesc withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new ServiceCollectionDesc(this.services, this.dataTypes, this.comments, newValue, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IServiceCollectionDesc#getMetaTags() metaTags}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceCollectionDesc withMetaTags(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ServiceCollectionDesc(this.services, this.dataTypes, this.comments, this.qualifier, newValue, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IServiceCollectionDesc#getMetaTags() metaTags}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of metaTags elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceCollectionDesc withMetaTags(Iterable<String> elements) {
    if (this.metaTags == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ServiceCollectionDesc(this.services, this.dataTypes, this.comments, this.qualifier, newValue, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IServiceCollectionDesc#getSimpleName() simpleName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for simpleName
   * @return A modified copy of the {@code this} object
   */
  public final ServiceCollectionDesc withSimpleName(String value) {
    String newValue = Objects.requireNonNull(value, "simpleName");
    if (this.simpleName.equals(newValue)) return this;
    return new ServiceCollectionDesc(this.services, this.dataTypes, this.comments, this.qualifier, this.metaTags, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ServiceCollectionDesc} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ServiceCollectionDesc
        && equalTo((ServiceCollectionDesc) another);
  }

  private boolean equalTo(ServiceCollectionDesc another) {
    return services.equals(another.services)
        && dataTypes.equals(another.dataTypes)
        && comments.equals(another.comments)
        && qualifier.equals(another.qualifier);
  }

  /**
   * Computes a hash code from attributes: {@code services}, {@code dataTypes}, {@code comments}, {@code qualifier}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + services.hashCode();
    h += (h << 5) + dataTypes.hashCode();
    h += (h << 5) + comments.hashCode();
    h += (h << 5) + qualifier.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ServiceCollectionDesc} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ServiceCollectionDesc")
        .omitNullValues()
        .add("services", services)
        .add("dataTypes", dataTypes)
        .add("comments", comments)
        .add("qualifier", qualifier)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IServiceCollectionDesc", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IServiceCollectionDesc {
    @Nullable List<IServiceDesc> services = ImmutableList.of();
    @Nullable List<IDataTypeDesc> dataTypes = ImmutableList.of();
    @Nullable List<String> comments = ImmutableList.of();
    @Nullable String qualifier;
    @Nullable List<String> metaTags = ImmutableList.of();
    @Nullable String simpleName;
    @JsonProperty("services")
    public void setServices(List<IServiceDesc> services) {
      this.services = services;
    }
    @JsonProperty("dataTypes")
    public void setDataTypes(List<IDataTypeDesc> dataTypes) {
      this.dataTypes = dataTypes;
    }
    @JsonProperty("comments")
    public void setComments(List<String> comments) {
      this.comments = comments;
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
    public List<IServiceDesc> getServices() { throw new UnsupportedOperationException(); }
    @Override
    public List<IDataTypeDesc> getDataTypes() { throw new UnsupportedOperationException(); }
    @Override
    public List<String> getComments() { throw new UnsupportedOperationException(); }
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
  static ServiceCollectionDesc fromJson(Json json) {
    ServiceCollectionDesc.Builder builder = ServiceCollectionDesc.builder();
    if (json.services != null) {
      builder.addAllServices(json.services);
    }
    if (json.dataTypes != null) {
      builder.addAllDataTypes(json.dataTypes);
    }
    if (json.comments != null) {
      builder.addAllComments(json.comments);
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
   * Creates an immutable copy of a {@link IServiceCollectionDesc} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ServiceCollectionDesc instance
   */
  public static ServiceCollectionDesc copyOf(IServiceCollectionDesc instance) {
    if (instance instanceof ServiceCollectionDesc) {
      return (ServiceCollectionDesc) instance;
    }
    return ServiceCollectionDesc.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ServiceCollectionDesc ServiceCollectionDesc}.
   * <pre>
   * ServiceCollectionDesc.builder()
   *    .addServices|addAllServices(de.upb.sede.exec.IServiceDesc) // {@link IServiceCollectionDesc#getServices() services} elements
   *    .addDataTypes|addAllDataTypes(de.upb.sede.types.IDataTypeDesc) // {@link IServiceCollectionDesc#getDataTypes() dataTypes} elements
   *    .addComments|addAllComments(String) // {@link IServiceCollectionDesc#getComments() comments} elements
   *    .qualifier(String) // required {@link IServiceCollectionDesc#getQualifier() qualifier}
   *    .addMetaTags|addAllMetaTags(String) // {@link IServiceCollectionDesc#getMetaTags() metaTags} elements
   *    .simpleName(String) // optional {@link IServiceCollectionDesc#getSimpleName() simpleName}
   *    .build();
   * </pre>
   * @return A new ServiceCollectionDesc builder
   */
  public static ServiceCollectionDesc.Builder builder() {
    return new ServiceCollectionDesc.Builder();
  }

  /**
   * Builds instances of type {@link ServiceCollectionDesc ServiceCollectionDesc}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IServiceCollectionDesc", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_QUALIFIER = 0x1L;
    private long initBits = 0x1L;

    private ImmutableList.Builder<IServiceDesc> services = ImmutableList.builder();
    private ImmutableList.Builder<IDataTypeDesc> dataTypes = ImmutableList.builder();
    private ImmutableList.Builder<String> comments = ImmutableList.builder();
    private @Nullable String qualifier;
    private ImmutableList.Builder<String> metaTags = ImmutableList.builder();
    private @Nullable String simpleName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableServiceCollectionDesc} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableServiceCollectionDesc instance) {
      Objects.requireNonNull(instance, "instance");
      addAllServices(instance.getServices());
      addAllDataTypes(instance.getDataTypes());
      addAllComments(instance.getComments());
      if (instance.qualifierIsSet()) {
        qualifier(instance.getQualifier());
      }
      addAllMetaTags(instance.getMetaTags());
      simpleName(instance.getSimpleName());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.IServiceCollectionDesc} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IServiceCollectionDesc instance) {
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
     * Fill a builder with attribute values from the provided {@code de.upb.sede.CommentAware} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(CommentAware instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof MutableServiceCollectionDesc) {
        from((MutableServiceCollectionDesc) object);
        return;
      }
      if (object instanceof IServiceCollectionDesc) {
        IServiceCollectionDesc instance = (IServiceCollectionDesc) object;
        addAllDataTypes(instance.getDataTypes());
        addAllServices(instance.getServices());
      }
      if (object instanceof IQualifiable) {
        IQualifiable instance = (IQualifiable) object;
        addAllMetaTags(instance.getMetaTags());
        simpleName(instance.getSimpleName());
        qualifier(instance.getQualifier());
      }
      if (object instanceof CommentAware) {
        CommentAware instance = (CommentAware) object;
        addAllComments(instance.getComments());
      }
    }

    /**
     * Adds one element to {@link IServiceCollectionDesc#getServices() services} list.
     * @param element A services element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addServices(IServiceDesc element) {
      this.services.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IServiceCollectionDesc#getServices() services} list.
     * @param elements An array of services elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addServices(IServiceDesc... elements) {
      this.services.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IServiceCollectionDesc#getServices() services} list.
     * @param elements An iterable of services elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("services")
    public final Builder services(Iterable<? extends IServiceDesc> elements) {
      this.services = ImmutableList.builder();
      return addAllServices(elements);
    }

    /**
     * Adds elements to {@link IServiceCollectionDesc#getServices() services} list.
     * @param elements An iterable of services elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllServices(Iterable<? extends IServiceDesc> elements) {
      this.services.addAll(elements);
      return this;
    }

    /**
     * Adds one element to {@link IServiceCollectionDesc#getDataTypes() dataTypes} list.
     * @param element A dataTypes element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addDataTypes(IDataTypeDesc element) {
      this.dataTypes.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IServiceCollectionDesc#getDataTypes() dataTypes} list.
     * @param elements An array of dataTypes elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addDataTypes(IDataTypeDesc... elements) {
      this.dataTypes.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IServiceCollectionDesc#getDataTypes() dataTypes} list.
     * @param elements An iterable of dataTypes elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("dataTypes")
    public final Builder dataTypes(Iterable<? extends IDataTypeDesc> elements) {
      this.dataTypes = ImmutableList.builder();
      return addAllDataTypes(elements);
    }

    /**
     * Adds elements to {@link IServiceCollectionDesc#getDataTypes() dataTypes} list.
     * @param elements An iterable of dataTypes elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllDataTypes(Iterable<? extends IDataTypeDesc> elements) {
      this.dataTypes.addAll(elements);
      return this;
    }

    /**
     * Adds one element to {@link IServiceCollectionDesc#getComments() comments} list.
     * @param element A comments element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addComments(String element) {
      this.comments.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IServiceCollectionDesc#getComments() comments} list.
     * @param elements An array of comments elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addComments(String... elements) {
      this.comments.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IServiceCollectionDesc#getComments() comments} list.
     * @param elements An iterable of comments elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("comments")
    public final Builder comments(Iterable<String> elements) {
      this.comments = ImmutableList.builder();
      return addAllComments(elements);
    }

    /**
     * Adds elements to {@link IServiceCollectionDesc#getComments() comments} list.
     * @param elements An iterable of comments elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllComments(Iterable<String> elements) {
      this.comments.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link IServiceCollectionDesc#getQualifier() qualifier} attribute.
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
     * Adds one element to {@link IServiceCollectionDesc#getMetaTags() metaTags} list.
     * @param element A metaTags element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMetaTags(String element) {
      this.metaTags.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IServiceCollectionDesc#getMetaTags() metaTags} list.
     * @param elements An array of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMetaTags(String... elements) {
      this.metaTags.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IServiceCollectionDesc#getMetaTags() metaTags} list.
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
     * Adds elements to {@link IServiceCollectionDesc#getMetaTags() metaTags} list.
     * @param elements An iterable of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllMetaTags(Iterable<String> elements) {
      this.metaTags.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link IServiceCollectionDesc#getSimpleName() simpleName} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IServiceCollectionDesc#getSimpleName() simpleName}.</em>
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
     * Builds a new {@link ServiceCollectionDesc ServiceCollectionDesc}.
     * @return An immutable instance of ServiceCollectionDesc
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ServiceCollectionDesc build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ServiceCollectionDesc(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      return "Cannot build ServiceCollectionDesc, some of required attributes are not set " + attributes;
    }
  }
}
