package de.upb.sede;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link ISDLAssembly}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code SDLAssembly.builder()}.
 */
@Generated(from = "ISDLAssembly", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class SDLAssembly implements ISDLAssembly {
  private final ImmutableList<IServiceCollectionDesc> collections;

  private SDLAssembly(ImmutableList<IServiceCollectionDesc> collections) {
    this.collections = collections;
  }

  /**
   * @return The value of the {@code collections} attribute
   */
  @JsonProperty("collections")
  @Override
  public ImmutableList<IServiceCollectionDesc> getCollections() {
    return collections;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ISDLAssembly#getCollections() collections}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final SDLAssembly withCollections(IServiceCollectionDesc... elements) {
    ImmutableList<IServiceCollectionDesc> newValue = ImmutableList.copyOf(elements);
    return new SDLAssembly(newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ISDLAssembly#getCollections() collections}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of collections elements to set
   * @return A modified copy of {@code this} object
   */
  public final SDLAssembly withCollections(Iterable<? extends IServiceCollectionDesc> elements) {
    if (this.collections == elements) return this;
    ImmutableList<IServiceCollectionDesc> newValue = ImmutableList.copyOf(elements);
    return new SDLAssembly(newValue);
  }

  /**
   * This instance is equal to all instances of {@code SDLAssembly} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof SDLAssembly
        && equalTo((SDLAssembly) another);
  }

  private boolean equalTo(SDLAssembly another) {
    return collections.equals(another.collections);
  }

  /**
   * Computes a hash code from attributes: {@code collections}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + collections.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code SDLAssembly} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("SDLAssembly")
        .omitNullValues()
        .add("collections", collections)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "ISDLAssembly", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements ISDLAssembly {
    @Nullable List<IServiceCollectionDesc> collections = ImmutableList.of();
    @JsonProperty("collections")
    public void setCollections(List<IServiceCollectionDesc> collections) {
      this.collections = collections;
    }
    @Override
    public List<IServiceCollectionDesc> getCollections() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static SDLAssembly fromJson(Json json) {
    SDLAssembly.Builder builder = SDLAssembly.builder();
    if (json.collections != null) {
      builder.addAllCollections(json.collections);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link ISDLAssembly} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable SDLAssembly instance
   */
  public static SDLAssembly copyOf(ISDLAssembly instance) {
    if (instance instanceof SDLAssembly) {
      return (SDLAssembly) instance;
    }
    return SDLAssembly.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link SDLAssembly SDLAssembly}.
   * <pre>
   * SDLAssembly.builder()
   *    .addCollections|addAllCollections(de.upb.sede.IServiceCollectionDesc) // {@link ISDLAssembly#getCollections() collections} elements
   *    .build();
   * </pre>
   * @return A new SDLAssembly builder
   */
  public static SDLAssembly.Builder builder() {
    return new SDLAssembly.Builder();
  }

  /**
   * Builds instances of type {@link SDLAssembly SDLAssembly}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "ISDLAssembly", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private ImmutableList.Builder<IServiceCollectionDesc> collections = ImmutableList.builder();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableSDLAssembly} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableSDLAssembly instance) {
      Objects.requireNonNull(instance, "instance");
      addAllCollections(instance.getCollections());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code ISDLAssembly} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(ISDLAssembly instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableSDLAssembly) {
        return from((MutableSDLAssembly) instance);
      }
      addAllCollections(instance.getCollections());
      return this;
    }

    /**
     * Adds one element to {@link ISDLAssembly#getCollections() collections} list.
     * @param element A collections element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addCollections(IServiceCollectionDesc element) {
      this.collections.add(element);
      return this;
    }

    /**
     * Adds elements to {@link ISDLAssembly#getCollections() collections} list.
     * @param elements An array of collections elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addCollections(IServiceCollectionDesc... elements) {
      this.collections.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link ISDLAssembly#getCollections() collections} list.
     * @param elements An iterable of collections elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("collections")
    public final Builder collections(Iterable<? extends IServiceCollectionDesc> elements) {
      this.collections = ImmutableList.builder();
      return addAllCollections(elements);
    }

    /**
     * Adds elements to {@link ISDLAssembly#getCollections() collections} list.
     * @param elements An iterable of collections elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllCollections(Iterable<? extends IServiceCollectionDesc> elements) {
      this.collections.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link SDLAssembly SDLAssembly}.
     * @return An immutable instance of SDLAssembly
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public SDLAssembly build() {
      return new SDLAssembly(collections.build());
    }
  }
}
