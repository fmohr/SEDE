package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.IComment;
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
 * Immutable implementation of {@link IServiceDesc}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ServiceDesc.builder()}.
 */
@Generated(from = "IServiceDesc", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ServiceDesc implements IServiceDesc {
  private final ImmutableList<IMethodDesc> methods;
  private final String qualifier;
  private final String simpleName;
  private final ImmutableList<String> comments;

  private ServiceDesc(ServiceDesc.Builder builder) {
    this.methods = builder.methods.build();
    this.qualifier = builder.qualifier;
    this.comments = builder.comments.build();
    this.simpleName = builder.simpleName != null
        ? builder.simpleName
        : Objects.requireNonNull(IServiceDesc.super.getSimpleName(), "simpleName");
  }

  private ServiceDesc(
      ImmutableList<IMethodDesc> methods,
      String qualifier,
      String simpleName,
      ImmutableList<String> comments) {
    this.methods = methods;
    this.qualifier = qualifier;
    this.simpleName = simpleName;
    this.comments = comments;
  }

  /**
   * @return The value of the {@code methods} attribute
   */
  @JsonProperty("methods")
  @Override
  public ImmutableList<IMethodDesc> getMethods() {
    return methods;
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
    return simpleName;
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
   * Copy the current immutable object with elements that replace the content of {@link ServiceDesc#getMethods() methods}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceDesc withMethods(IMethodDesc... elements) {
    ImmutableList<IMethodDesc> newValue = ImmutableList.copyOf(elements);
    return new ServiceDesc(newValue, this.qualifier, this.simpleName, this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ServiceDesc#getMethods() methods}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of methods elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceDesc withMethods(Iterable<? extends IMethodDesc> elements) {
    if (this.methods == elements) return this;
    ImmutableList<IMethodDesc> newValue = ImmutableList.copyOf(elements);
    return new ServiceDesc(newValue, this.qualifier, this.simpleName, this.comments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ServiceDesc#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final ServiceDesc withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new ServiceDesc(this.methods, newValue, this.simpleName, this.comments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ServiceDesc#getSimpleName() simpleName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for simpleName
   * @return A modified copy of the {@code this} object
   */
  public final ServiceDesc withSimpleName(String value) {
    String newValue = Objects.requireNonNull(value, "simpleName");
    if (this.simpleName.equals(newValue)) return this;
    return new ServiceDesc(this.methods, this.qualifier, newValue, this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ServiceDesc#getComments() comments}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceDesc withComments(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ServiceDesc(this.methods, this.qualifier, this.simpleName, newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ServiceDesc#getComments() comments}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of comments elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceDesc withComments(Iterable<String> elements) {
    if (this.comments == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ServiceDesc(this.methods, this.qualifier, this.simpleName, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ServiceDesc} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ServiceDesc
        && equalTo((ServiceDesc) another);
  }

  private boolean equalTo(ServiceDesc another) {
    return methods.equals(another.methods)
        && qualifier.equals(another.qualifier)
        && simpleName.equals(another.simpleName)
        && comments.equals(another.comments);
  }

  /**
   * Computes a hash code from attributes: {@code methods}, {@code qualifier}, {@code simpleName}, {@code comments}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + methods.hashCode();
    h += (h << 5) + qualifier.hashCode();
    h += (h << 5) + simpleName.hashCode();
    h += (h << 5) + comments.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ServiceDesc} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ServiceDesc")
        .omitNullValues()
        .add("methods", methods)
        .add("qualifier", qualifier)
        .add("simpleName", simpleName)
        .add("comments", comments)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IServiceDesc", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IServiceDesc {
    @Nullable List<IMethodDesc> methods = ImmutableList.of();
    @Nullable String qualifier;
    @Nullable String simpleName;
    @Nullable List<String> comments = ImmutableList.of();
    @JsonProperty("methods")
    public void setMethods(List<IMethodDesc> methods) {
      this.methods = methods;
    }
    @JsonProperty("qualifier")
    public void setQualifier(String qualifier) {
      this.qualifier = qualifier;
    }
    @JsonProperty("simpleName")
    public void setSimpleName(String simpleName) {
      this.simpleName = simpleName;
    }
    @JsonProperty("comments")
    public void setComments(List<String> comments) {
      this.comments = comments;
    }
    @Override
    public List<IMethodDesc> getMethods() { throw new UnsupportedOperationException(); }
    @Override
    public String getQualifier() { throw new UnsupportedOperationException(); }
    @Override
    public String getSimpleName() { throw new UnsupportedOperationException(); }
    @Override
    public List<String> getComments() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static ServiceDesc fromJson(Json json) {
    ServiceDesc.Builder builder = ServiceDesc.builder();
    if (json.methods != null) {
      builder.addAllMethods(json.methods);
    }
    if (json.qualifier != null) {
      builder.qualifier(json.qualifier);
    }
    if (json.simpleName != null) {
      builder.simpleName(json.simpleName);
    }
    if (json.comments != null) {
      builder.addAllComments(json.comments);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IServiceDesc} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ServiceDesc instance
   */
  static ServiceDesc copyOf(IServiceDesc instance) {
    if (instance instanceof ServiceDesc) {
      return (ServiceDesc) instance;
    }
    return ServiceDesc.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ServiceDesc ServiceDesc}.
   * <pre>
   * ServiceDesc.builder()
   *    .addMethods|addAllMethods(de.upb.sede.exec.IMethodDesc) // {@link ServiceDesc#getMethods() methods} elements
   *    .qualifier(String) // required {@link ServiceDesc#getQualifier() qualifier}
   *    .simpleName(String) // optional {@link ServiceDesc#getSimpleName() simpleName}
   *    .addComments|addAllComments(String) // {@link ServiceDesc#getComments() comments} elements
   *    .build();
   * </pre>
   * @return A new ServiceDesc builder
   */
  public static ServiceDesc.Builder builder() {
    return new ServiceDesc.Builder();
  }

  /**
   * Builds instances of type {@link ServiceDesc ServiceDesc}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IServiceDesc", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_QUALIFIER = 0x1L;
    private long initBits = 0x1L;

    private ImmutableList.Builder<IMethodDesc> methods = ImmutableList.builder();
    private @Nullable String qualifier;
    private @Nullable String simpleName;
    private ImmutableList.Builder<String> comments = ImmutableList.builder();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code ServiceDesc} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(ServiceDesc instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Copy abstract value type {@code IServiceDesc} instance into builder.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IServiceDesc instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.IComment} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IComment instance) {
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
      if (object instanceof IServiceDesc) {
        IServiceDesc instance = (IServiceDesc) object;
        addAllMethods(instance.getMethods());
      }
      if (object instanceof IComment) {
        IComment instance = (IComment) object;
        addAllComments(instance.getComments());
      }
      if (object instanceof IQualifiable) {
        IQualifiable instance = (IQualifiable) object;
        simpleName(instance.getSimpleName());
        qualifier(instance.getQualifier());
      }
    }

    /**
     * Adds one element to {@link ServiceDesc#getMethods() methods} list.
     * @param element A methods element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMethods(IMethodDesc element) {
      this.methods.add(element);
      return this;
    }

    /**
     * Adds elements to {@link ServiceDesc#getMethods() methods} list.
     * @param elements An array of methods elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMethods(IMethodDesc... elements) {
      this.methods.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link ServiceDesc#getMethods() methods} list.
     * @param elements An iterable of methods elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("methods")
    public final Builder methods(Iterable<? extends IMethodDesc> elements) {
      this.methods = ImmutableList.builder();
      return addAllMethods(elements);
    }

    /**
     * Adds elements to {@link ServiceDesc#getMethods() methods} list.
     * @param elements An iterable of methods elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllMethods(Iterable<? extends IMethodDesc> elements) {
      this.methods.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link ServiceDesc#getQualifier() qualifier} attribute.
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
     * Initializes the value for the {@link ServiceDesc#getSimpleName() simpleName} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link ServiceDesc#getSimpleName() simpleName}.</em>
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
     * Adds one element to {@link ServiceDesc#getComments() comments} list.
     * @param element A comments element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addComments(String element) {
      this.comments.add(element);
      return this;
    }

    /**
     * Adds elements to {@link ServiceDesc#getComments() comments} list.
     * @param elements An array of comments elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addComments(String... elements) {
      this.comments.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link ServiceDesc#getComments() comments} list.
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
     * Adds elements to {@link ServiceDesc#getComments() comments} list.
     * @param elements An iterable of comments elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllComments(Iterable<String> elements) {
      this.comments.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link ServiceDesc ServiceDesc}.
     * @return An immutable instance of ServiceDesc
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ServiceDesc build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ServiceDesc(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      return "Cannot build ServiceDesc, some of required attributes are not set " + attributes;
    }
  }
}
