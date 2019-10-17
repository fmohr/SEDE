package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.ICommented;
import de.upb.sede.IQualifiable;
import de.upb.sede.exec.auxiliary.IJavaDispatchAux;
import de.upb.sede.exec.auxiliary.IPythonClassAux;
import de.upb.sede.param.IServiceParameterizationDesc;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IServiceDesc IServiceDesc} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableServiceDesc is not thread-safe</em>
 * @see ServiceDesc
 */
@Generated(from = "IServiceDesc", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IServiceDesc"})
@NotThreadSafe
public final class MutableServiceDesc implements IServiceDesc {
  private static final long INIT_BIT_QUALIFIER = 0x1L;
  private static final long OPT_BIT_IS_ABSTRACT = 0x1L;
  private long initBits = 0x1L;
  private long optBits;

  private final ArrayList<IMethodDesc> methods = new ArrayList<IMethodDesc>();
  private final ArrayList<String> interfaces = new ArrayList<String>();
  private boolean isAbstract;
  private final Map<String, String> fieldTypes = new LinkedHashMap<String, String>();
  private @Nullable IJavaDispatchAux javaDispatchAux;
  private @Nullable IPythonClassAux pythonClassAuxiliaries;
  private @Nullable IServiceParameterizationDesc serviceParameters;
  private String qualifier;
  private final ArrayList<String> metaTags = new ArrayList<String>();
  private String simpleName;
  private final ArrayList<String> comments = new ArrayList<String>();

  private MutableServiceDesc() {}

  /**
   * Construct a modifiable instance of {@code IServiceDesc}.
   * @return A new modifiable instance
   */
  public static MutableServiceDesc create() {
    return new MutableServiceDesc();
  }

  /**
   * @return modifiable list {@code methods}
   */
  @JsonProperty("methods")
  @Override
  public final List<IMethodDesc> getMethods() {
    return methods;
  }

  /**
   * @return modifiable list {@code interfaces}
   */
  @JsonProperty("interfaces")
  @Override
  public final List<String> getInterfaces() {
    return interfaces;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code isAbstract} attribute
   */
  @JsonProperty("isAbstract")
  @Override
  public final boolean isAbstract() {
    return isAbstractIsSet()
        ? isAbstract
        : IServiceDesc.super.isAbstract();
  }

  /**
   * @return value of {@code fieldTypes} attribute
   */
  @JsonProperty("fieldTypes")
  @Override
  public final Map<String, String> getFieldTypes() {
    return fieldTypes;
  }

  /**
   * @return value of {@code javaDispatchAux} attribute, may be {@code null}
   */
  @JsonProperty("javaDispatchAux")
  @Override
  public final @Nullable IJavaDispatchAux getJavaDispatchAux() {
    return javaDispatchAux;
  }

  /**
   * @return value of {@code pythonClassAuxiliaries} attribute, may be {@code null}
   */
  @JsonProperty("pythonClassAuxiliaries")
  @Override
  public final @Nullable IPythonClassAux getPythonClassAuxiliaries() {
    return pythonClassAuxiliaries;
  }

  /**
   * @return value of {@code serviceParameters} attribute, may be {@code null}
   */
  @JsonProperty("serviceParameters")
  @Override
  public final @Nullable IServiceParameterizationDesc getServiceParameters() {
    return serviceParameters;
  }

  /**
   * @return value of {@code qualifier} attribute
   */
  @JsonProperty("qualifier")
  @Override
  public final String getQualifier() {
    if (!qualifierIsSet()) {
      checkRequiredAttributes();
    }
    return qualifier;
  }

  /**
   * @return modifiable list {@code metaTags}
   */
  @JsonProperty("metaTags")
  @Override
  public final List<String> getMetaTags() {
    return metaTags;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code simpleName} attribute
   */
  @JsonProperty("simpleName")
  @Override
  public final String getSimpleName() {
    return simpleNameIsSet()
        ? simpleName
        : IServiceDesc.super.getSimpleName();
  }

  /**
   * @return modifiable list {@code comments}
   */
  @JsonProperty("comments")
  @Override
  public final List<String> getComments() {
    return comments;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc clear() {
    initBits = 0x1L;
    optBits = 0;
    methods.clear();
    interfaces.clear();
    isAbstract = false;
    fieldTypes.clear();
    javaDispatchAux = null;
    pythonClassAuxiliaries = null;
    serviceParameters = null;
    qualifier = null;
    metaTags.clear();
    simpleName = null;
    comments.clear();
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.ICommented} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc from(ICommented instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.exec.IServiceDesc} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc from(IServiceDesc instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.IQualifiable} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc from(IQualifiable instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IServiceDesc} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableServiceDesc from(MutableServiceDesc instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableServiceDesc) {
      MutableServiceDesc instance = (MutableServiceDesc) object;
      addAllMethods(instance.getMethods());
      addAllInterfaces(instance.getInterfaces());
      setIsAbstract(instance.isAbstract());
      putAllFieldTypes(instance.getFieldTypes());
      @Nullable IJavaDispatchAux javaDispatchAuxValue = instance.getJavaDispatchAux();
      if (javaDispatchAuxValue != null) {
        setJavaDispatchAux(javaDispatchAuxValue);
      }
      @Nullable IPythonClassAux pythonClassAuxiliariesValue = instance.getPythonClassAuxiliaries();
      if (pythonClassAuxiliariesValue != null) {
        setPythonClassAuxiliaries(pythonClassAuxiliariesValue);
      }
      @Nullable IServiceParameterizationDesc serviceParametersValue = instance.getServiceParameters();
      if (serviceParametersValue != null) {
        setServiceParameters(serviceParametersValue);
      }
      if (instance.qualifierIsSet()) {
        setQualifier(instance.getQualifier());
      }
      addAllMetaTags(instance.getMetaTags());
      setSimpleName(instance.getSimpleName());
      addAllComments(instance.getComments());
      return;
    }
    if (object instanceof ICommented) {
      ICommented instance = (ICommented) object;
      addAllComments(instance.getComments());
    }
    if (object instanceof IServiceDesc) {
      IServiceDesc instance = (IServiceDesc) object;
      addAllInterfaces(instance.getInterfaces());
      @Nullable IJavaDispatchAux javaDispatchAuxValue = instance.getJavaDispatchAux();
      if (javaDispatchAuxValue != null) {
        setJavaDispatchAux(javaDispatchAuxValue);
      }
      addAllMethods(instance.getMethods());
      putAllFieldTypes(instance.getFieldTypes());
      setIsAbstract(instance.isAbstract());
      @Nullable IPythonClassAux pythonClassAuxiliariesValue = instance.getPythonClassAuxiliaries();
      if (pythonClassAuxiliariesValue != null) {
        setPythonClassAuxiliaries(pythonClassAuxiliariesValue);
      }
      @Nullable IServiceParameterizationDesc serviceParametersValue = instance.getServiceParameters();
      if (serviceParametersValue != null) {
        setServiceParameters(serviceParametersValue);
      }
    }
    if (object instanceof IQualifiable) {
      IQualifiable instance = (IQualifiable) object;
      addAllMetaTags(instance.getMetaTags());
      setSimpleName(instance.getSimpleName());
      setQualifier(instance.getQualifier());
    }
  }

  /**
   * Adds one element to {@link IServiceDesc#getMethods() methods} list.
   * @param element The methods element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc addMethods(IMethodDesc element) {
    Objects.requireNonNull(element, "methods element");
    this.methods.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IServiceDesc#getMethods() methods} list.
   * @param elements An array of methods elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableServiceDesc addMethods(IMethodDesc... elements) {
    for (IMethodDesc e : elements) {
      addMethods(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IServiceDesc#getMethods() methods} list.
   * @param elements An iterable of methods elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc setMethods(Iterable<? extends IMethodDesc> elements) {
    this.methods.clear();
    addAllMethods(elements);
    return this;
  }

  /**
   * Adds elements to {@link IServiceDesc#getMethods() methods} list.
   * @param elements An iterable of methods elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc addAllMethods(Iterable<? extends IMethodDesc> elements) {
    for (IMethodDesc e : elements) {
      addMethods(e);
    }
    return this;
  }

  /**
   * Adds one element to {@link IServiceDesc#getInterfaces() interfaces} list.
   * @param element The interfaces element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc addInterfaces(String element) {
    Objects.requireNonNull(element, "interfaces element");
    this.interfaces.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IServiceDesc#getInterfaces() interfaces} list.
   * @param elements An array of interfaces elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableServiceDesc addInterfaces(String... elements) {
    for (String e : elements) {
      addInterfaces(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IServiceDesc#getInterfaces() interfaces} list.
   * @param elements An iterable of interfaces elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc setInterfaces(Iterable<String> elements) {
    this.interfaces.clear();
    addAllInterfaces(elements);
    return this;
  }

  /**
   * Adds elements to {@link IServiceDesc#getInterfaces() interfaces} list.
   * @param elements An iterable of interfaces elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc addAllInterfaces(Iterable<String> elements) {
    for (String e : elements) {
      addInterfaces(e);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IServiceDesc#isAbstract() isAbstract} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IServiceDesc#isAbstract() isAbstract}.</em>
   * @param isAbstract The value for isAbstract
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc setIsAbstract(boolean isAbstract) {
    this.isAbstract = isAbstract;
    optBits |= OPT_BIT_IS_ABSTRACT;
    return this;
  }

  /**
   * Put one entry to the {@link IServiceDesc#getFieldTypes() fieldTypes} map.
   * @param key The key in fieldTypes map
   * @param value The associated value in the fieldTypes map
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc putFieldTypes(String key, String value) {
    this.fieldTypes.put(
        Objects.requireNonNull(key, "fieldTypes key"),
        Objects.requireNonNull(value, "fieldTypes value"));
    return this;
  }

  /**
   * Sets or replaces all mappings from the specified map as entries for the {@link IServiceDesc#getFieldTypes() fieldTypes} map.
   * Nulls are not permitted as keys or values.
   * @param entries The entries that will be added to the fieldTypes map
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc setFieldTypes(Map<String, ? extends String> entries) {
    this.fieldTypes.clear();
    for (Map.Entry<String, ? extends String> e : entries.entrySet()) {
      String k = e.getKey();
      String v = e.getValue();
      this.fieldTypes.put(
          Objects.requireNonNull(k, "fieldTypes key"),
          Objects.requireNonNull(v, "fieldTypes value"));
    }
    return this;
  }

  /**
   * Put all mappings from the specified map as entries to the {@link IServiceDesc#getFieldTypes() fieldTypes} map.
   * Nulls are not permitted as keys or values.
   * @param entries to be added to fieldTypes map
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc putAllFieldTypes(Map<String, ? extends String> entries) {
    for (Map.Entry<String, ? extends String> e : entries.entrySet()) {
      String k = e.getKey();
      String v = e.getValue();
      this.fieldTypes.put(
          Objects.requireNonNull(k, "fieldTypes key"),
          Objects.requireNonNull(v, "fieldTypes value"));
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IServiceDesc#getJavaDispatchAux() javaDispatchAux} attribute.
   * @param javaDispatchAux The value for javaDispatchAux, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc setJavaDispatchAux(@Nullable IJavaDispatchAux javaDispatchAux) {
    this.javaDispatchAux = javaDispatchAux;
    return this;
  }

  /**
   * Assigns a value to the {@link IServiceDesc#getPythonClassAuxiliaries() pythonClassAuxiliaries} attribute.
   * @param pythonClassAuxiliaries The value for pythonClassAuxiliaries, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc setPythonClassAuxiliaries(@Nullable IPythonClassAux pythonClassAuxiliaries) {
    this.pythonClassAuxiliaries = pythonClassAuxiliaries;
    return this;
  }

  /**
   * Assigns a value to the {@link IServiceDesc#getServiceParameters() serviceParameters} attribute.
   * @param serviceParameters The value for serviceParameters, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc setServiceParameters(@Nullable IServiceParameterizationDesc serviceParameters) {
    this.serviceParameters = serviceParameters;
    return this;
  }

  /**
   * Assigns a value to the {@link IServiceDesc#getQualifier() qualifier} attribute.
   * @param qualifier The value for qualifier
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc setQualifier(String qualifier) {
    this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
    initBits &= ~INIT_BIT_QUALIFIER;
    return this;
  }

  /**
   * Adds one element to {@link IServiceDesc#getMetaTags() metaTags} list.
   * @param element The metaTags element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc addMetaTags(String element) {
    Objects.requireNonNull(element, "metaTags element");
    this.metaTags.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IServiceDesc#getMetaTags() metaTags} list.
   * @param elements An array of metaTags elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableServiceDesc addMetaTags(String... elements) {
    for (String e : elements) {
      addMetaTags(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IServiceDesc#getMetaTags() metaTags} list.
   * @param elements An iterable of metaTags elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc setMetaTags(Iterable<String> elements) {
    this.metaTags.clear();
    addAllMetaTags(elements);
    return this;
  }

  /**
   * Adds elements to {@link IServiceDesc#getMetaTags() metaTags} list.
   * @param elements An iterable of metaTags elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc addAllMetaTags(Iterable<String> elements) {
    for (String e : elements) {
      addMetaTags(e);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IServiceDesc#getSimpleName() simpleName} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IServiceDesc#getSimpleName() simpleName}.</em>
   * @param simpleName The value for simpleName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc setSimpleName(String simpleName) {
    this.simpleName = Objects.requireNonNull(simpleName, "simpleName");
    return this;
  }

  /**
   * Adds one element to {@link IServiceDesc#getComments() comments} list.
   * @param element The comments element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc addComments(String element) {
    Objects.requireNonNull(element, "comments element");
    this.comments.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IServiceDesc#getComments() comments} list.
   * @param elements An array of comments elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableServiceDesc addComments(String... elements) {
    for (String e : elements) {
      addComments(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IServiceDesc#getComments() comments} list.
   * @param elements An iterable of comments elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc setComments(Iterable<String> elements) {
    this.comments.clear();
    addAllComments(elements);
    return this;
  }

  /**
   * Adds elements to {@link IServiceDesc#getComments() comments} list.
   * @param elements An iterable of comments elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceDesc addAllComments(Iterable<String> elements) {
    for (String e : elements) {
      addComments(e);
    }
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IServiceDesc#getQualifier() qualifier} is set.
   * @return {@code true} if set
   */
  public final boolean qualifierIsSet() {
    return (initBits & INIT_BIT_QUALIFIER) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IServiceDesc#isAbstract() isAbstract} is set.
   * @return {@code true} if set
   */
  public final boolean isAbstractIsSet() {
    return (optBits & OPT_BIT_IS_ABSTRACT) != 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IServiceDesc#getSimpleName() simpleName} is set.
   * @return {@code true} if set
   */
  public final boolean simpleNameIsSet() {
    return simpleName != null;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableServiceDesc unsetQualifier() {
    initBits |= INIT_BIT_QUALIFIER;
    qualifier = null;
    return this;
  }
  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableServiceDesc unsetIsAbstract() {
    optBits |= 0;
    isAbstract = false;
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
    if (!qualifierIsSet()) attributes.add("qualifier");
    return "ServiceDesc is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link ServiceDesc ServiceDesc}.
   * @return An immutable instance of ServiceDesc
   */
  public final ServiceDesc toImmutable() {
    checkRequiredAttributes();
    return ServiceDesc.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableServiceDesc} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableServiceDesc)) return false;
    MutableServiceDesc other = (MutableServiceDesc) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableServiceDesc another) {
    boolean isAbstract = isAbstract();
    return methods.equals(another.methods)
        && interfaces.equals(another.interfaces)
        && isAbstract == another.isAbstract()
        && fieldTypes.equals(another.fieldTypes)
        && Objects.equals(javaDispatchAux, another.javaDispatchAux)
        && Objects.equals(pythonClassAuxiliaries, another.pythonClassAuxiliaries)
        && Objects.equals(serviceParameters, another.serviceParameters)
        && qualifier.equals(another.qualifier)
        && comments.equals(another.comments);
  }

  /**
   * Computes a hash code from attributes: {@code methods}, {@code interfaces}, {@code isAbstract}, {@code fieldTypes}, {@code javaDispatchAux}, {@code pythonClassAuxiliaries}, {@code serviceParameters}, {@code qualifier}, {@code comments}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + methods.hashCode();
    h += (h << 5) + interfaces.hashCode();
    boolean isAbstract = isAbstract();
    h += (h << 5) + Booleans.hashCode(isAbstract);
    h += (h << 5) + fieldTypes.hashCode();
    h += (h << 5) + Objects.hashCode(javaDispatchAux);
    h += (h << 5) + Objects.hashCode(pythonClassAuxiliaries);
    h += (h << 5) + Objects.hashCode(serviceParameters);
    h += (h << 5) + qualifier.hashCode();
    h += (h << 5) + comments.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IServiceDesc}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableServiceDesc")
        .add("methods", getMethods())
        .add("interfaces", getInterfaces())
        .add("isAbstract", isAbstract())
        .add("fieldTypes", getFieldTypes())
        .add("javaDispatchAux", getJavaDispatchAux())
        .add("pythonClassAuxiliaries", getPythonClassAuxiliaries())
        .add("serviceParameters", getServiceParameters())
        .add("qualifier", qualifierIsSet() ? getQualifier() : "?")
        .add("comments", getComments())
        .toString();
  }
}
