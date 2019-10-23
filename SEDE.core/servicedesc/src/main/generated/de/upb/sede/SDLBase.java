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
 * Immutable implementation of {@link ISDLBase}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code SDLBase.builder()}.
 */
@Generated(from = "ISDLBase", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class SDLBase implements ISDLBase {
  private final ImmutableList<IServiceCollectionDesc> collections;

  private SDLBase(ImmutableList<IServiceCollectionDesc> collections) {
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
   * Copy the current immutable object with elements that replace the content of {@link ISDLBase#getCollections() collections}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final SDLBase withCollections(IServiceCollectionDesc... elements) {
    ImmutableList<IServiceCollectionDesc> newValue = ImmutableList.copyOf(elements);
    return new SDLBase(newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ISDLBase#getCollections() collections}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of collections elements to set
   * @return A modified copy of {@code this} object
   */
  public final SDLBase withCollections(Iterable<? extends IServiceCollectionDesc> elements) {
    if (this.collections == elements) return this;
    ImmutableList<IServiceCollectionDesc> newValue = ImmutableList.copyOf(elements);
    return new SDLBase(newValue);
  }

  /**
   * This instance is equal to all instances of {@code SDLBase} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof SDLBase
        && equalTo((SDLBase) another);
  }

  private boolean equalTo(SDLBase another) {
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
   * Prints the immutable value {@code SDLBase} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("SDLBase")
        .omitNullValues()
        .add("collections", collections)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "ISDLBase", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements ISDLBase {
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
  static SDLBase fromJson(Json json) {
    SDLBase.Builder builder = SDLBase.builder();
    if (json.collections != null) {
      builder.addAllCollections(json.collections);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link ISDLBase} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable SDLBase instance
   */
  public static SDLBase copyOf(ISDLBase instance) {
    if (instance instanceof SDLBase) {
      return (SDLBase) instance;
    }
    return SDLBase.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link SDLBase SDLBase}.
   * <pre>
   * SDLBase.builder()
   *    .addCollections|addAllCollections(de.upb.sede.IServiceCollectionDesc) // {@link ISDLBase#getCollections() collections} elements
   *    .build();
   * </pre>
   * @return A new SDLBase builder
   */
  public static SDLBase.Builder builder() {
    return new SDLBase.Builder();
  }

  /**
   * Builds instances of type {@link SDLBase SDLBase}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "ISDLBase", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private ImmutableList.Builder<IServiceCollectionDesc> collections = ImmutableList.builder();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableSDLBase} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableSDLBase instance) {
      Objects.requireNonNull(instance, "instance");
      addAllCollections(instance.getCollections());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code ISDLBase} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(ISDLBase instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableSDLBase) {
        return from((MutableSDLBase) instance);
      }
      addAllCollections(instance.getCollections());
      return this;
    }

    /**
     * Adds one element to {@link ISDLBase#getCollections() collections} list.
     * @param element A collections element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addCollections(IServiceCollectionDesc element) {
      this.collections.add(element);
      return this;
    }

    /**
     * Adds elements to {@link ISDLBase#getCollections() collections} list.
     * @param elements An array of collections elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addCollections(IServiceCollectionDesc... elements) {
      this.collections.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link ISDLBase#getCollections() collections} list.
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
     * Adds elements to {@link ISDLBase#getCollections() collections} list.
     * @param elements An iterable of collections elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllCollections(Iterable<? extends IServiceCollectionDesc> elements) {
      this.collections.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link SDLBase SDLBase}.
     * @return An immutable instance of SDLBase
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public SDLBase build() {
      return new SDLBase(collections.build());
    }
  }
}
