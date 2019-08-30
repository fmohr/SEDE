package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.upb.sede.IQualifiable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link IMethodDesc}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code MethodDesc.builder()}.
 */
@Generated(from = "IMethodDesc", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
public final class MethodDesc implements IMethodDesc {
  private final List<IVariableDesc> inputs;
  private final List<IVariableDesc> outputs;
  private final String qualifier;

  private MethodDesc(
      List<IVariableDesc> inputs,
      List<IVariableDesc> outputs,
      String qualifier) {
    this.inputs = inputs;
    this.outputs = outputs;
    this.qualifier = qualifier;
  }

  /**
   * @return The value of the {@code inputs} attribute
   */
  @JsonProperty("inputs")
  @Override
  public List<IVariableDesc> getInputs() {
    return inputs;
  }

  /**
   * @return The value of the {@code outputs} attribute
   */
  @JsonProperty("outputs")
  @Override
  public List<IVariableDesc> getOutputs() {
    return outputs;
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
   * Copy the current immutable object with elements that replace the content of {@link IMethodDesc#getInputs() inputs}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final MethodDesc withInputs(IVariableDesc... elements) {
    List<IVariableDesc> newValue = createUnmodifiableList(false, createSafeList(Arrays.asList(elements), true, false));
    return new MethodDesc(newValue, this.outputs, this.qualifier);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IMethodDesc#getInputs() inputs}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of inputs elements to set
   * @return A modified copy of {@code this} object
   */
  public final MethodDesc withInputs(Iterable<? extends IVariableDesc> elements) {
    if (this.inputs == elements) return this;
    List<IVariableDesc> newValue = createUnmodifiableList(false, createSafeList(elements, true, false));
    return new MethodDesc(newValue, this.outputs, this.qualifier);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IMethodDesc#getOutputs() outputs}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final MethodDesc withOutputs(IVariableDesc... elements) {
    List<IVariableDesc> newValue = createUnmodifiableList(false, createSafeList(Arrays.asList(elements), true, false));
    return new MethodDesc(this.inputs, newValue, this.qualifier);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IMethodDesc#getOutputs() outputs}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of outputs elements to set
   * @return A modified copy of {@code this} object
   */
  public final MethodDesc withOutputs(Iterable<? extends IVariableDesc> elements) {
    if (this.outputs == elements) return this;
    List<IVariableDesc> newValue = createUnmodifiableList(false, createSafeList(elements, true, false));
    return new MethodDesc(this.inputs, newValue, this.qualifier);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IMethodDesc#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final MethodDesc withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new MethodDesc(this.inputs, this.outputs, newValue);
  }

  /**
   * This instance is equal to all instances of {@code MethodDesc} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof MethodDesc
        && equalTo((MethodDesc) another);
  }

  private boolean equalTo(MethodDesc another) {
    return inputs.equals(another.inputs)
        && outputs.equals(another.outputs)
        && qualifier.equals(another.qualifier);
  }

  /**
   * Computes a hash code from attributes: {@code inputs}, {@code outputs}, {@code qualifier}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + inputs.hashCode();
    h += (h << 5) + outputs.hashCode();
    h += (h << 5) + qualifier.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code MethodDesc} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "MethodDesc{"
        + "inputs=" + inputs
        + ", outputs=" + outputs
        + ", qualifier=" + qualifier
        + "}";
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IMethodDesc", generator = "Immutables")
  @Deprecated
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IMethodDesc {
    List<IVariableDesc> inputs = Collections.emptyList();
    List<IVariableDesc> outputs = Collections.emptyList();
    String qualifier;
    @JsonProperty("inputs")
    public void setInputs(List<IVariableDesc> inputs) {
      this.inputs = inputs;
    }
    @JsonProperty("outputs")
    public void setOutputs(List<IVariableDesc> outputs) {
      this.outputs = outputs;
    }
    @JsonProperty("qualifier")
    public void setQualifier(String qualifier) {
      this.qualifier = qualifier;
    }
    @Override
    public List<IVariableDesc> getInputs() { throw new UnsupportedOperationException(); }
    @Override
    public List<IVariableDesc> getOutputs() { throw new UnsupportedOperationException(); }
    @Override
    public String getQualifier() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static MethodDesc fromJson(Json json) {
    MethodDesc.Builder builder = MethodDesc.builder();
    if (json.inputs != null) {
      builder.addAllInputs(json.inputs);
    }
    if (json.outputs != null) {
      builder.addAllOutputs(json.outputs);
    }
    if (json.qualifier != null) {
      builder.qualifier(json.qualifier);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IMethodDesc} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable MethodDesc instance
   */
  public static MethodDesc copyOf(IMethodDesc instance) {
    if (instance instanceof MethodDesc) {
      return (MethodDesc) instance;
    }
    return MethodDesc.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link MethodDesc MethodDesc}.
   * <pre>
   * MethodDesc.builder()
   *    .addInputs|addAllInputs(de.upb.sede.exec.IVariableDesc) // {@link IMethodDesc#getInputs() inputs} elements
   *    .addOutputs|addAllOutputs(de.upb.sede.exec.IVariableDesc) // {@link IMethodDesc#getOutputs() outputs} elements
   *    .qualifier(String) // required {@link IMethodDesc#getQualifier() qualifier}
   *    .build();
   * </pre>
   * @return A new MethodDesc builder
   */
  public static MethodDesc.Builder builder() {
    return new MethodDesc.Builder();
  }

  /**
   * Builds instances of type {@link MethodDesc MethodDesc}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IMethodDesc", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_QUALIFIER = 0x1L;
    private long initBits = 0x1L;

    private List<IVariableDesc> inputs = new ArrayList<IVariableDesc>();
    private List<IVariableDesc> outputs = new ArrayList<IVariableDesc>();
    private String qualifier;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.exec.IMethodDesc} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(IMethodDesc instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.IQualifiable} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(IQualifiable instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof IMethodDesc) {
        IMethodDesc instance = (IMethodDesc) object;
        addAllOutputs(instance.getOutputs());
        addAllInputs(instance.getInputs());
      }
      if (object instanceof IQualifiable) {
        IQualifiable instance = (IQualifiable) object;
        qualifier(instance.getQualifier());
      }
    }

    /**
     * Adds one element to {@link IMethodDesc#getInputs() inputs} list.
     * @param element A inputs element
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addInputs(IVariableDesc element) {
      this.inputs.add(Objects.requireNonNull(element, "inputs element"));
      return this;
    }

    /**
     * Adds elements to {@link IMethodDesc#getInputs() inputs} list.
     * @param elements An array of inputs elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addInputs(IVariableDesc... elements) {
      for (IVariableDesc element : elements) {
        this.inputs.add(Objects.requireNonNull(element, "inputs element"));
      }
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IMethodDesc#getInputs() inputs} list.
     * @param elements An iterable of inputs elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder inputs(Iterable<? extends IVariableDesc> elements) {
      this.inputs.clear();
      return addAllInputs(elements);
    }

    /**
     * Adds elements to {@link IMethodDesc#getInputs() inputs} list.
     * @param elements An iterable of inputs elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addAllInputs(Iterable<? extends IVariableDesc> elements) {
      for (IVariableDesc element : elements) {
        this.inputs.add(Objects.requireNonNull(element, "inputs element"));
      }
      return this;
    }

    /**
     * Adds one element to {@link IMethodDesc#getOutputs() outputs} list.
     * @param element A outputs element
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addOutputs(IVariableDesc element) {
      this.outputs.add(Objects.requireNonNull(element, "outputs element"));
      return this;
    }

    /**
     * Adds elements to {@link IMethodDesc#getOutputs() outputs} list.
     * @param elements An array of outputs elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addOutputs(IVariableDesc... elements) {
      for (IVariableDesc element : elements) {
        this.outputs.add(Objects.requireNonNull(element, "outputs element"));
      }
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IMethodDesc#getOutputs() outputs} list.
     * @param elements An iterable of outputs elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder outputs(Iterable<? extends IVariableDesc> elements) {
      this.outputs.clear();
      return addAllOutputs(elements);
    }

    /**
     * Adds elements to {@link IMethodDesc#getOutputs() outputs} list.
     * @param elements An iterable of outputs elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addAllOutputs(Iterable<? extends IVariableDesc> elements) {
      for (IVariableDesc element : elements) {
        this.outputs.add(Objects.requireNonNull(element, "outputs element"));
      }
      return this;
    }

    /**
     * Initializes the value for the {@link IMethodDesc#getQualifier() qualifier} attribute.
     * @param qualifier The value for qualifier 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder qualifier(String qualifier) {
      this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
      initBits &= ~INIT_BIT_QUALIFIER;
      return this;
    }

    /**
     * Builds a new {@link MethodDesc MethodDesc}.
     * @return An immutable instance of MethodDesc
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public MethodDesc build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new MethodDesc(createUnmodifiableList(true, inputs), createUnmodifiableList(true, outputs), qualifier);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      return "Cannot build MethodDesc, some of required attributes are not set " + attributes;
    }
  }

  private static <T> List<T> createSafeList(Iterable<? extends T> iterable, boolean checkNulls, boolean skipNulls) {
    ArrayList<T> list;
    if (iterable instanceof Collection<?>) {
      int size = ((Collection<?>) iterable).size();
      if (size == 0) return Collections.emptyList();
      list = new ArrayList<>();
    } else {
      list = new ArrayList<>();
    }
    for (T element : iterable) {
      if (skipNulls && element == null) continue;
      if (checkNulls) Objects.requireNonNull(element, "element");
      list.add(element);
    }
    return list;
  }

  private static <T> List<T> createUnmodifiableList(boolean clone, List<T> list) {
    switch(list.size()) {
    case 0: return Collections.emptyList();
    case 1: return Collections.singletonList(list.get(0));
    default:
      if (clone) {
        return Collections.unmodifiableList(new ArrayList<>(list));
      } else {
        if (list instanceof ArrayList<?>) {
          ((ArrayList<?>) list).trimToSize();
        }
        return Collections.unmodifiableList(list);
      }
    }
  }
}
