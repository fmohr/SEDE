package de.upb.sede.param;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
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
 * Immutable implementation of {@link IParameterDependencyDesc}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ParameterDependencyDesc.builder()}.
 */
@Generated(from = "IParameterDependencyDesc", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ParameterDependencyDesc implements IParameterDependencyDesc {
  private final String premise;
  private final String conclusion;

  private ParameterDependencyDesc(String premise, String conclusion) {
    this.premise = premise;
    this.conclusion = conclusion;
  }

  /**
   * @return The value of the {@code premise} attribute
   */
  @JsonProperty("premise")
  @Override
  public String getPremise() {
    return premise;
  }

  /**
   * @return The value of the {@code conclusion} attribute
   */
  @JsonProperty("conclusion")
  @Override
  public String getConclusion() {
    return conclusion;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IParameterDependencyDesc#getPremise() premise} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for premise
   * @return A modified copy of the {@code this} object
   */
  public final ParameterDependencyDesc withPremise(String value) {
    String newValue = Objects.requireNonNull(value, "premise");
    if (this.premise.equals(newValue)) return this;
    return new ParameterDependencyDesc(newValue, this.conclusion);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IParameterDependencyDesc#getConclusion() conclusion} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for conclusion
   * @return A modified copy of the {@code this} object
   */
  public final ParameterDependencyDesc withConclusion(String value) {
    String newValue = Objects.requireNonNull(value, "conclusion");
    if (this.conclusion.equals(newValue)) return this;
    return new ParameterDependencyDesc(this.premise, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ParameterDependencyDesc} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ParameterDependencyDesc
        && equalTo((ParameterDependencyDesc) another);
  }

  private boolean equalTo(ParameterDependencyDesc another) {
    return premise.equals(another.premise)
        && conclusion.equals(another.conclusion);
  }

  /**
   * Computes a hash code from attributes: {@code premise}, {@code conclusion}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + premise.hashCode();
    h += (h << 5) + conclusion.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ParameterDependencyDesc} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ParameterDependencyDesc")
        .omitNullValues()
        .add("premise", premise)
        .add("conclusion", conclusion)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IParameterDependencyDesc", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IParameterDependencyDesc {
    @Nullable String premise;
    @Nullable String conclusion;
    @JsonProperty("premise")
    public void setPremise(String premise) {
      this.premise = premise;
    }
    @JsonProperty("conclusion")
    public void setConclusion(String conclusion) {
      this.conclusion = conclusion;
    }
    @Override
    public String getPremise() { throw new UnsupportedOperationException(); }
    @Override
    public String getConclusion() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static ParameterDependencyDesc fromJson(Json json) {
    ParameterDependencyDesc.Builder builder = ParameterDependencyDesc.builder();
    if (json.premise != null) {
      builder.premise(json.premise);
    }
    if (json.conclusion != null) {
      builder.conclusion(json.conclusion);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IParameterDependencyDesc} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ParameterDependencyDesc instance
   */
  public static ParameterDependencyDesc copyOf(IParameterDependencyDesc instance) {
    if (instance instanceof ParameterDependencyDesc) {
      return (ParameterDependencyDesc) instance;
    }
    return ParameterDependencyDesc.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ParameterDependencyDesc ParameterDependencyDesc}.
   * <pre>
   * ParameterDependencyDesc.builder()
   *    .premise(String) // required {@link IParameterDependencyDesc#getPremise() premise}
   *    .conclusion(String) // required {@link IParameterDependencyDesc#getConclusion() conclusion}
   *    .build();
   * </pre>
   * @return A new ParameterDependencyDesc builder
   */
  public static ParameterDependencyDesc.Builder builder() {
    return new ParameterDependencyDesc.Builder();
  }

  /**
   * Builds instances of type {@link ParameterDependencyDesc ParameterDependencyDesc}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IParameterDependencyDesc", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_PREMISE = 0x1L;
    private static final long INIT_BIT_CONCLUSION = 0x2L;
    private long initBits = 0x3L;

    private @Nullable String premise;
    private @Nullable String conclusion;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableParameterDependencyDesc} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableParameterDependencyDesc instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.premiseIsSet()) {
        premise(instance.getPremise());
      }
      if (instance.conclusionIsSet()) {
        conclusion(instance.getConclusion());
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IParameterDependencyDesc} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IParameterDependencyDesc instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableParameterDependencyDesc) {
        return from((MutableParameterDependencyDesc) instance);
      }
      premise(instance.getPremise());
      conclusion(instance.getConclusion());
      return this;
    }

    /**
     * Initializes the value for the {@link IParameterDependencyDesc#getPremise() premise} attribute.
     * @param premise The value for premise 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("premise")
    public final Builder premise(String premise) {
      this.premise = Objects.requireNonNull(premise, "premise");
      initBits &= ~INIT_BIT_PREMISE;
      return this;
    }

    /**
     * Initializes the value for the {@link IParameterDependencyDesc#getConclusion() conclusion} attribute.
     * @param conclusion The value for conclusion 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("conclusion")
    public final Builder conclusion(String conclusion) {
      this.conclusion = Objects.requireNonNull(conclusion, "conclusion");
      initBits &= ~INIT_BIT_CONCLUSION;
      return this;
    }

    /**
     * Builds a new {@link ParameterDependencyDesc ParameterDependencyDesc}.
     * @return An immutable instance of ParameterDependencyDesc
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ParameterDependencyDesc build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ParameterDependencyDesc(premise, conclusion);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_PREMISE) != 0) attributes.add("premise");
      if ((initBits & INIT_BIT_CONCLUSION) != 0) attributes.add("conclusion");
      return "Cannot build ParameterDependencyDesc, some of required attributes are not set " + attributes;
    }
  }
}
