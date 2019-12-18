package de.upb.sede;

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
 * A modifiable implementation of the {@link ISDLAssembly ISDLAssembly} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableSDLAssembly is not thread-safe</em>
 * @see SDLAssembly
 */
@Generated(from = "ISDLAssembly", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "ISDLAssembly"})
@NotThreadSafe
public final class MutableSDLAssembly implements ISDLAssembly {
  private final ArrayList<IServiceCollectionDesc> collections = new ArrayList<IServiceCollectionDesc>();

  private MutableSDLAssembly() {}

  /**
   * Construct a modifiable instance of {@code ISDLAssembly}.
   * @return A new modifiable instance
   */
  public static MutableSDLAssembly create() {
    return new MutableSDLAssembly();
  }

  /**
   * @return modifiable list {@code collections}
   */
  @JsonProperty("collections")
  @Override
  public final List<IServiceCollectionDesc> getCollections() {
    return collections;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSDLAssembly clear() {
    collections.clear();
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link ISDLAssembly} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableSDLAssembly from(ISDLAssembly instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableSDLAssembly) {
      from((MutableSDLAssembly) instance);
      return this;
    }
    addAllCollections(instance.getCollections());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link ISDLAssembly} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableSDLAssembly from(MutableSDLAssembly instance) {
    Objects.requireNonNull(instance, "instance");
    addAllCollections(instance.getCollections());
    return this;
  }

  /**
   * Adds one element to {@link ISDLAssembly#getCollections() collections} list.
   * @param element The collections element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSDLAssembly addCollections(IServiceCollectionDesc element) {
    Objects.requireNonNull(element, "collections element");
    this.collections.add(element);
    return this;
  }

  /**
   * Adds elements to {@link ISDLAssembly#getCollections() collections} list.
   * @param elements An array of collections elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableSDLAssembly addCollections(IServiceCollectionDesc... elements) {
    for (IServiceCollectionDesc e : elements) {
      addCollections(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link ISDLAssembly#getCollections() collections} list.
   * @param elements An iterable of collections elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSDLAssembly setCollections(Iterable<? extends IServiceCollectionDesc> elements) {
    this.collections.clear();
    addAllCollections(elements);
    return this;
  }

  /**
   * Adds elements to {@link ISDLAssembly#getCollections() collections} list.
   * @param elements An iterable of collections elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSDLAssembly addAllCollections(Iterable<? extends IServiceCollectionDesc> elements) {
    for (IServiceCollectionDesc e : elements) {
      addCollections(e);
    }
    return this;
  }


  /**
   * Returns {@code true} if all required attributes are set, indicating that the object is initialized.
   * @return {@code true} if set
   */
  public final boolean isInitialized() {
    return true;
  }

  /**
   * Converts to {@link SDLAssembly SDLAssembly}.
   * @return An immutable instance of SDLAssembly
   */
  public final SDLAssembly toImmutable() {
    return SDLAssembly.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableSDLAssembly} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableSDLAssembly)) return false;
    MutableSDLAssembly other = (MutableSDLAssembly) another;
    return equalTo(other);
  }

  private boolean equalTo(MutableSDLAssembly another) {
    return collections.equals(another.collections);
  }

  /**
   * Computes a hash code from attributes: {@code collections}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + collections.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code ISDLAssembly}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableSDLAssembly")
        .add("collections", getCollections())
        .toString();
  }
}
