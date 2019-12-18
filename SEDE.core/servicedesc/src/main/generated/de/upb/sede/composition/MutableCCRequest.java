package de.upb.sede.composition;

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
 * A modifiable implementation of the {@link ICCRequest ICCRequest} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableCCRequest is not thread-safe</em>
 * @see CCRequest
 */
@Generated(from = "ICCRequest", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "ICCRequest"})
@NotThreadSafe
public final class MutableCCRequest implements ICCRequest {
  private static final long INIT_BIT_COMPOSITION = 0x1L;
  private long initBits = 0x1L;

  private String composition;
  private final ArrayList<IFieldType> initialContext = new ArrayList<IFieldType>();

  private MutableCCRequest() {}

  /**
   * Construct a modifiable instance of {@code ICCRequest}.
   * @return A new modifiable instance
   */
  public static MutableCCRequest create() {
    return new MutableCCRequest();
  }

  /**
   * @return value of {@code composition} attribute
   */
  @JsonProperty("composition")
  @Override
  public final String getComposition() {
    if (!compositionIsSet()) {
      checkRequiredAttributes();
    }
    return composition;
  }

  /**
   * @return modifiable list {@code initialContext}
   */
  @JsonProperty("initialContext")
  @Override
  public final List<IFieldType> getInitialContext() {
    return initialContext;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCCRequest clear() {
    initBits = 0x1L;
    composition = null;
    initialContext.clear();
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link ICCRequest} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableCCRequest from(ICCRequest instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableCCRequest) {
      from((MutableCCRequest) instance);
      return this;
    }
    setComposition(instance.getComposition());
    addAllInitialContext(instance.getInitialContext());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link ICCRequest} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableCCRequest from(MutableCCRequest instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.compositionIsSet()) {
      setComposition(instance.getComposition());
    }
    addAllInitialContext(instance.getInitialContext());
    return this;
  }

  /**
   * Assigns a value to the {@link ICCRequest#getComposition() composition} attribute.
   * @param composition The value for composition
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCCRequest setComposition(String composition) {
    this.composition = Objects.requireNonNull(composition, "composition");
    initBits &= ~INIT_BIT_COMPOSITION;
    return this;
  }

  /**
   * Adds one element to {@link ICCRequest#getInitialContext() initialContext} list.
   * @param element The initialContext element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCCRequest addInitialContext(IFieldType element) {
    Objects.requireNonNull(element, "initialContext element");
    this.initialContext.add(element);
    return this;
  }

  /**
   * Adds elements to {@link ICCRequest#getInitialContext() initialContext} list.
   * @param elements An array of initialContext elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableCCRequest addInitialContext(IFieldType... elements) {
    for (IFieldType e : elements) {
      addInitialContext(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link ICCRequest#getInitialContext() initialContext} list.
   * @param elements An iterable of initialContext elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCCRequest setInitialContext(Iterable<? extends IFieldType> elements) {
    this.initialContext.clear();
    addAllInitialContext(elements);
    return this;
  }

  /**
   * Adds elements to {@link ICCRequest#getInitialContext() initialContext} list.
   * @param elements An iterable of initialContext elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCCRequest addAllInitialContext(Iterable<? extends IFieldType> elements) {
    for (IFieldType e : elements) {
      addInitialContext(e);
    }
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link ICCRequest#getComposition() composition} is set.
   * @return {@code true} if set
   */
  public final boolean compositionIsSet() {
    return (initBits & INIT_BIT_COMPOSITION) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableCCRequest unsetComposition() {
    initBits |= INIT_BIT_COMPOSITION;
    composition = null;
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
    if (!compositionIsSet()) attributes.add("composition");
    return "CCRequest is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link CCRequest CCRequest}.
   * @return An immutable instance of CCRequest
   */
  public final CCRequest toImmutable() {
    checkRequiredAttributes();
    return CCRequest.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableCCRequest} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableCCRequest)) return false;
    MutableCCRequest other = (MutableCCRequest) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableCCRequest another) {
    return composition.equals(another.composition)
        && initialContext.equals(another.initialContext);
  }

  /**
   * Computes a hash code from attributes: {@code composition}, {@code initialContext}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + composition.hashCode();
    h += (h << 5) + initialContext.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code ICCRequest}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableCCRequest")
        .add("composition", compositionIsSet() ? getComposition() : "?")
        .add("initialContext", getInitialContext())
        .toString();
  }
}
