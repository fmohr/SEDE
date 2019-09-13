package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.ICommented;
import de.upb.sede.IQualifiable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
  private final ImmutableList<String> interfaces;
  private final boolean isAbstract;
  private final ImmutableMap<String, String> fieldTypes;
  private final @Nullable IJavaClassAux javaClassAuxiliaries;
  private final @Nullable IPythonClassAux pythonClassAuxiliaries;
  private final String qualifier;
  private final String simpleName;
  private final ImmutableList<String> comments;

  private ServiceDesc(ServiceDesc.Builder builder) {
    this.methods = builder.methods.build();
    this.interfaces = builder.interfaces.build();
    this.fieldTypes = builder.fieldTypes.build();
    this.javaClassAuxiliaries = builder.javaClassAuxiliaries;
    this.pythonClassAuxiliaries = builder.pythonClassAuxiliaries;
    this.qualifier = builder.qualifier;
    this.comments = builder.comments.build();
    if (builder.isAbstractIsSet()) {
      initShim.isAbstract(builder.isAbstract);
    }
    if (builder.simpleName != null) {
      initShim.simpleName(builder.simpleName);
    }
    this.isAbstract = initShim.isAbstract();
    this.simpleName = initShim.getSimpleName();
    this.initShim = null;
  }

  private ServiceDesc(
      ImmutableList<IMethodDesc> methods,
      ImmutableList<String> interfaces,
      boolean isAbstract,
      ImmutableMap<String, String> fieldTypes,
      @Nullable IJavaClassAux javaClassAuxiliaries,
      @Nullable IPythonClassAux pythonClassAuxiliaries,
      String qualifier,
      String simpleName,
      ImmutableList<String> comments) {
    this.methods = methods;
    this.interfaces = interfaces;
    this.isAbstract = isAbstract;
    this.fieldTypes = fieldTypes;
    this.javaClassAuxiliaries = javaClassAuxiliaries;
    this.pythonClassAuxiliaries = pythonClassAuxiliaries;
    this.qualifier = qualifier;
    this.simpleName = simpleName;
    this.comments = comments;
    this.initShim = null;
  }

  private static final byte STAGE_INITIALIZING = -1;
  private static final byte STAGE_UNINITIALIZED = 0;
  private static final byte STAGE_INITIALIZED = 1;
  @SuppressWarnings("Immutable")
  private transient volatile InitShim initShim = new InitShim();

  @Generated(from = "IServiceDesc", generator = "Immutables")
  private final class InitShim {
    private byte isAbstractBuildStage = STAGE_UNINITIALIZED;
    private boolean isAbstract;

    boolean isAbstract() {
      if (isAbstractBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (isAbstractBuildStage == STAGE_UNINITIALIZED) {
        isAbstractBuildStage = STAGE_INITIALIZING;
        this.isAbstract = isAbstractInitialize();
        isAbstractBuildStage = STAGE_INITIALIZED;
      }
      return this.isAbstract;
    }

    void isAbstract(boolean isAbstract) {
      this.isAbstract = isAbstract;
      isAbstractBuildStage = STAGE_INITIALIZED;
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
      if (isAbstractBuildStage == STAGE_INITIALIZING) attributes.add("isAbstract");
      if (simpleNameBuildStage == STAGE_INITIALIZING) attributes.add("simpleName");
      return "Cannot build ServiceDesc, attribute initializers form cycle " + attributes;
    }
  }

  private boolean isAbstractInitialize() {
    return IServiceDesc.super.isAbstract();
  }

  private String getSimpleNameInitialize() {
    return IServiceDesc.super.getSimpleName();
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
   * @return The value of the {@code interfaces} attribute
   */
  @JsonProperty("interfaces")
  @Override
  public ImmutableList<String> getInterfaces() {
    return interfaces;
  }

  /**
   * @return The value of the {@code isAbstract} attribute
   */
  @JsonProperty("isAbstract")
  @Override
  public boolean isAbstract() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.isAbstract()
        : this.isAbstract;
  }

  /**
   * @return The value of the {@code fieldTypes} attribute
   */
  @JsonProperty("fieldTypes")
  @Override
  public ImmutableMap<String, String> getFieldTypes() {
    return fieldTypes;
  }

  /**
   * @return The value of the {@code javaClassAuxiliaries} attribute
   */
  @JsonProperty("javaClassAuxiliaries")
  @Override
  public @Nullable IJavaClassAux getJavaClassAuxiliaries() {
    return javaClassAuxiliaries;
  }

  /**
   * @return The value of the {@code pythonClassAuxiliaries} attribute
   */
  @JsonProperty("pythonClassAuxiliaries")
  @Override
  public @Nullable IPythonClassAux getPythonClassAuxiliaries() {
    return pythonClassAuxiliaries;
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
    InitShim shim = this.initShim;
    return shim != null
        ? shim.getSimpleName()
        : this.simpleName;
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
   * Copy the current immutable object with elements that replace the content of {@link IServiceDesc#getMethods() methods}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceDesc withMethods(IMethodDesc... elements) {
    ImmutableList<IMethodDesc> newValue = ImmutableList.copyOf(elements);
    return new ServiceDesc(
        newValue,
        this.interfaces,
        this.isAbstract,
        this.fieldTypes,
        this.javaClassAuxiliaries,
        this.pythonClassAuxiliaries,
        this.qualifier,
        this.simpleName,
        this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IServiceDesc#getMethods() methods}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of methods elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceDesc withMethods(Iterable<? extends IMethodDesc> elements) {
    if (this.methods == elements) return this;
    ImmutableList<IMethodDesc> newValue = ImmutableList.copyOf(elements);
    return new ServiceDesc(
        newValue,
        this.interfaces,
        this.isAbstract,
        this.fieldTypes,
        this.javaClassAuxiliaries,
        this.pythonClassAuxiliaries,
        this.qualifier,
        this.simpleName,
        this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IServiceDesc#getInterfaces() interfaces}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceDesc withInterfaces(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ServiceDesc(
        this.methods,
        newValue,
        this.isAbstract,
        this.fieldTypes,
        this.javaClassAuxiliaries,
        this.pythonClassAuxiliaries,
        this.qualifier,
        this.simpleName,
        this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IServiceDesc#getInterfaces() interfaces}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of interfaces elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceDesc withInterfaces(Iterable<String> elements) {
    if (this.interfaces == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ServiceDesc(
        this.methods,
        newValue,
        this.isAbstract,
        this.fieldTypes,
        this.javaClassAuxiliaries,
        this.pythonClassAuxiliaries,
        this.qualifier,
        this.simpleName,
        this.comments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IServiceDesc#isAbstract() isAbstract} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isAbstract
   * @return A modified copy of the {@code this} object
   */
  public final ServiceDesc withIsAbstract(boolean value) {
    if (this.isAbstract == value) return this;
    return new ServiceDesc(
        this.methods,
        this.interfaces,
        value,
        this.fieldTypes,
        this.javaClassAuxiliaries,
        this.pythonClassAuxiliaries,
        this.qualifier,
        this.simpleName,
        this.comments);
  }

  /**
   * Copy the current immutable object by replacing the {@link IServiceDesc#getFieldTypes() fieldTypes} map with the specified map.
   * Nulls are not permitted as keys or values.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param entries The entries to be added to the fieldTypes map
   * @return A modified copy of {@code this} object
   */
  public final ServiceDesc withFieldTypes(Map<String, ? extends String> entries) {
    if (this.fieldTypes == entries) return this;
    ImmutableMap<String, String> newValue = ImmutableMap.copyOf(entries);
    return new ServiceDesc(
        this.methods,
        this.interfaces,
        this.isAbstract,
        newValue,
        this.javaClassAuxiliaries,
        this.pythonClassAuxiliaries,
        this.qualifier,
        this.simpleName,
        this.comments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IServiceDesc#getJavaClassAuxiliaries() javaClassAuxiliaries} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for javaClassAuxiliaries (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final ServiceDesc withJavaClassAuxiliaries(@Nullable IJavaClassAux value) {
    if (this.javaClassAuxiliaries == value) return this;
    return new ServiceDesc(
        this.methods,
        this.interfaces,
        this.isAbstract,
        this.fieldTypes,
        value,
        this.pythonClassAuxiliaries,
        this.qualifier,
        this.simpleName,
        this.comments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IServiceDesc#getPythonClassAuxiliaries() pythonClassAuxiliaries} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for pythonClassAuxiliaries (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final ServiceDesc withPythonClassAuxiliaries(@Nullable IPythonClassAux value) {
    if (this.pythonClassAuxiliaries == value) return this;
    return new ServiceDesc(
        this.methods,
        this.interfaces,
        this.isAbstract,
        this.fieldTypes,
        this.javaClassAuxiliaries,
        value,
        this.qualifier,
        this.simpleName,
        this.comments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IServiceDesc#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final ServiceDesc withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new ServiceDesc(
        this.methods,
        this.interfaces,
        this.isAbstract,
        this.fieldTypes,
        this.javaClassAuxiliaries,
        this.pythonClassAuxiliaries,
        newValue,
        this.simpleName,
        this.comments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IServiceDesc#getSimpleName() simpleName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for simpleName
   * @return A modified copy of the {@code this} object
   */
  public final ServiceDesc withSimpleName(String value) {
    String newValue = Objects.requireNonNull(value, "simpleName");
    if (this.simpleName.equals(newValue)) return this;
    return new ServiceDesc(
        this.methods,
        this.interfaces,
        this.isAbstract,
        this.fieldTypes,
        this.javaClassAuxiliaries,
        this.pythonClassAuxiliaries,
        this.qualifier,
        newValue,
        this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IServiceDesc#getComments() comments}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceDesc withComments(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ServiceDesc(
        this.methods,
        this.interfaces,
        this.isAbstract,
        this.fieldTypes,
        this.javaClassAuxiliaries,
        this.pythonClassAuxiliaries,
        this.qualifier,
        this.simpleName,
        newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IServiceDesc#getComments() comments}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of comments elements to set
   * @return A modified copy of {@code this} object
   */
  public final ServiceDesc withComments(Iterable<String> elements) {
    if (this.comments == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ServiceDesc(
        this.methods,
        this.interfaces,
        this.isAbstract,
        this.fieldTypes,
        this.javaClassAuxiliaries,
        this.pythonClassAuxiliaries,
        this.qualifier,
        this.simpleName,
        newValue);
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
        && interfaces.equals(another.interfaces)
        && isAbstract == another.isAbstract
        && fieldTypes.equals(another.fieldTypes)
        && Objects.equals(javaClassAuxiliaries, another.javaClassAuxiliaries)
        && Objects.equals(pythonClassAuxiliaries, another.pythonClassAuxiliaries)
        && qualifier.equals(another.qualifier)
        && simpleName.equals(another.simpleName)
        && comments.equals(another.comments);
  }

  /**
   * Computes a hash code from attributes: {@code methods}, {@code interfaces}, {@code isAbstract}, {@code fieldTypes}, {@code javaClassAuxiliaries}, {@code pythonClassAuxiliaries}, {@code qualifier}, {@code simpleName}, {@code comments}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + methods.hashCode();
    h += (h << 5) + interfaces.hashCode();
    h += (h << 5) + Booleans.hashCode(isAbstract);
    h += (h << 5) + fieldTypes.hashCode();
    h += (h << 5) + Objects.hashCode(javaClassAuxiliaries);
    h += (h << 5) + Objects.hashCode(pythonClassAuxiliaries);
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
        .add("interfaces", interfaces)
        .add("isAbstract", isAbstract)
        .add("fieldTypes", fieldTypes)
        .add("javaClassAuxiliaries", javaClassAuxiliaries)
        .add("pythonClassAuxiliaries", pythonClassAuxiliaries)
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
    @Nullable List<String> interfaces = ImmutableList.of();
    boolean isAbstract;
    boolean isAbstractIsSet;
    @Nullable Map<String, String> fieldTypes = ImmutableMap.of();
    @Nullable IJavaClassAux javaClassAuxiliaries;
    @Nullable IPythonClassAux pythonClassAuxiliaries;
    @Nullable String qualifier;
    @Nullable String simpleName;
    @Nullable List<String> comments = ImmutableList.of();
    @JsonProperty("methods")
    public void setMethods(List<IMethodDesc> methods) {
      this.methods = methods;
    }
    @JsonProperty("interfaces")
    public void setInterfaces(List<String> interfaces) {
      this.interfaces = interfaces;
    }
    @JsonProperty("isAbstract")
    public void setIsAbstract(boolean isAbstract) {
      this.isAbstract = isAbstract;
      this.isAbstractIsSet = true;
    }
    @JsonProperty("fieldTypes")
    public void setFieldTypes(Map<String, String> fieldTypes) {
      this.fieldTypes = fieldTypes;
    }
    @JsonProperty("javaClassAuxiliaries")
    public void setJavaClassAuxiliaries(@Nullable IJavaClassAux javaClassAuxiliaries) {
      this.javaClassAuxiliaries = javaClassAuxiliaries;
    }
    @JsonProperty("pythonClassAuxiliaries")
    public void setPythonClassAuxiliaries(@Nullable IPythonClassAux pythonClassAuxiliaries) {
      this.pythonClassAuxiliaries = pythonClassAuxiliaries;
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
    public List<String> getInterfaces() { throw new UnsupportedOperationException(); }
    @Override
    public boolean isAbstract() { throw new UnsupportedOperationException(); }
    @Override
    public Map<String, String> getFieldTypes() { throw new UnsupportedOperationException(); }
    @Override
    public IJavaClassAux getJavaClassAuxiliaries() { throw new UnsupportedOperationException(); }
    @Override
    public IPythonClassAux getPythonClassAuxiliaries() { throw new UnsupportedOperationException(); }
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
    if (json.interfaces != null) {
      builder.addAllInterfaces(json.interfaces);
    }
    if (json.isAbstractIsSet) {
      builder.isAbstract(json.isAbstract);
    }
    if (json.fieldTypes != null) {
      builder.putAllFieldTypes(json.fieldTypes);
    }
    if (json.javaClassAuxiliaries != null) {
      builder.javaClassAuxiliaries(json.javaClassAuxiliaries);
    }
    if (json.pythonClassAuxiliaries != null) {
      builder.pythonClassAuxiliaries(json.pythonClassAuxiliaries);
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
  public static ServiceDesc copyOf(IServiceDesc instance) {
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
   *    .addMethods|addAllMethods(de.upb.sede.exec.IMethodDesc) // {@link IServiceDesc#getMethods() methods} elements
   *    .addInterfaces|addAllInterfaces(String) // {@link IServiceDesc#getInterfaces() interfaces} elements
   *    .isAbstract(boolean) // optional {@link IServiceDesc#isAbstract() isAbstract}
   *    .putFieldTypes|putAllFieldTypes(String => String) // {@link IServiceDesc#getFieldTypes() fieldTypes} mappings
   *    .javaClassAuxiliaries(de.upb.sede.exec.IJavaClassAux | null) // nullable {@link IServiceDesc#getJavaClassAuxiliaries() javaClassAuxiliaries}
   *    .pythonClassAuxiliaries(de.upb.sede.exec.IPythonClassAux | null) // nullable {@link IServiceDesc#getPythonClassAuxiliaries() pythonClassAuxiliaries}
   *    .qualifier(String) // required {@link IServiceDesc#getQualifier() qualifier}
   *    .simpleName(String) // optional {@link IServiceDesc#getSimpleName() simpleName}
   *    .addComments|addAllComments(String) // {@link IServiceDesc#getComments() comments} elements
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
    private static final long OPT_BIT_IS_ABSTRACT = 0x1L;
    private long initBits = 0x1L;
    private long optBits;

    private ImmutableList.Builder<IMethodDesc> methods = ImmutableList.builder();
    private ImmutableList.Builder<String> interfaces = ImmutableList.builder();
    private boolean isAbstract;
    private ImmutableMap.Builder<String, String> fieldTypes = ImmutableMap.builder();
    private @Nullable IJavaClassAux javaClassAuxiliaries;
    private @Nullable IPythonClassAux pythonClassAuxiliaries;
    private @Nullable String qualifier;
    private @Nullable String simpleName;
    private ImmutableList.Builder<String> comments = ImmutableList.builder();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableServiceDesc} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableServiceDesc instance) {
      Objects.requireNonNull(instance, "instance");
      addAllMethods(instance.getMethods());
      addAllInterfaces(instance.getInterfaces());
      isAbstract(instance.isAbstract());
      putAllFieldTypes(instance.getFieldTypes());
      @Nullable IJavaClassAux javaClassAuxiliariesValue = instance.getJavaClassAuxiliaries();
      if (javaClassAuxiliariesValue != null) {
        javaClassAuxiliaries(javaClassAuxiliariesValue);
      }
      @Nullable IPythonClassAux pythonClassAuxiliariesValue = instance.getPythonClassAuxiliaries();
      if (pythonClassAuxiliariesValue != null) {
        pythonClassAuxiliaries(pythonClassAuxiliariesValue);
      }
      if (instance.qualifierIsSet()) {
        qualifier(instance.getQualifier());
      }
      simpleName(instance.getSimpleName());
      addAllComments(instance.getComments());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.ICommented} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(ICommented instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.exec.IServiceDesc} instance.
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
      if (object instanceof MutableServiceDesc) {
        from((MutableServiceDesc) object);
        return;
      }
      if (object instanceof ICommented) {
        ICommented instance = (ICommented) object;
        addAllComments(instance.getComments());
      }
      if (object instanceof IServiceDesc) {
        IServiceDesc instance = (IServiceDesc) object;
        addAllInterfaces(instance.getInterfaces());
        isAbstract(instance.isAbstract());
        @Nullable IPythonClassAux pythonClassAuxiliariesValue = instance.getPythonClassAuxiliaries();
        if (pythonClassAuxiliariesValue != null) {
          pythonClassAuxiliaries(pythonClassAuxiliariesValue);
        }
        addAllMethods(instance.getMethods());
        putAllFieldTypes(instance.getFieldTypes());
        @Nullable IJavaClassAux javaClassAuxiliariesValue = instance.getJavaClassAuxiliaries();
        if (javaClassAuxiliariesValue != null) {
          javaClassAuxiliaries(javaClassAuxiliariesValue);
        }
      }
      if (object instanceof IQualifiable) {
        IQualifiable instance = (IQualifiable) object;
        simpleName(instance.getSimpleName());
        qualifier(instance.getQualifier());
      }
    }

    /**
     * Adds one element to {@link IServiceDesc#getMethods() methods} list.
     * @param element A methods element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMethods(IMethodDesc element) {
      this.methods.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IServiceDesc#getMethods() methods} list.
     * @param elements An array of methods elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMethods(IMethodDesc... elements) {
      this.methods.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IServiceDesc#getMethods() methods} list.
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
     * Adds elements to {@link IServiceDesc#getMethods() methods} list.
     * @param elements An iterable of methods elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllMethods(Iterable<? extends IMethodDesc> elements) {
      this.methods.addAll(elements);
      return this;
    }

    /**
     * Adds one element to {@link IServiceDesc#getInterfaces() interfaces} list.
     * @param element A interfaces element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addInterfaces(String element) {
      this.interfaces.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IServiceDesc#getInterfaces() interfaces} list.
     * @param elements An array of interfaces elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addInterfaces(String... elements) {
      this.interfaces.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IServiceDesc#getInterfaces() interfaces} list.
     * @param elements An iterable of interfaces elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("interfaces")
    public final Builder interfaces(Iterable<String> elements) {
      this.interfaces = ImmutableList.builder();
      return addAllInterfaces(elements);
    }

    /**
     * Adds elements to {@link IServiceDesc#getInterfaces() interfaces} list.
     * @param elements An iterable of interfaces elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllInterfaces(Iterable<String> elements) {
      this.interfaces.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link IServiceDesc#isAbstract() isAbstract} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IServiceDesc#isAbstract() isAbstract}.</em>
     * @param isAbstract The value for isAbstract 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("isAbstract")
    public final Builder isAbstract(boolean isAbstract) {
      this.isAbstract = isAbstract;
      optBits |= OPT_BIT_IS_ABSTRACT;
      return this;
    }

    /**
     * Put one entry to the {@link IServiceDesc#getFieldTypes() fieldTypes} map.
     * @param key The key in the fieldTypes map
     * @param value The associated value in the fieldTypes map
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder putFieldTypes(String key, String value) {
      this.fieldTypes.put(key, value);
      return this;
    }

    /**
     * Put one entry to the {@link IServiceDesc#getFieldTypes() fieldTypes} map. Nulls are not permitted
     * @param entry The key and value entry
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder putFieldTypes(Map.Entry<String, ? extends String> entry) {
      this.fieldTypes.put(entry);
      return this;
    }

    /**
     * Sets or replaces all mappings from the specified map as entries for the {@link IServiceDesc#getFieldTypes() fieldTypes} map. Nulls are not permitted
     * @param entries The entries that will be added to the fieldTypes map
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("fieldTypes")
    public final Builder fieldTypes(Map<String, ? extends String> entries) {
      this.fieldTypes = ImmutableMap.builder();
      return putAllFieldTypes(entries);
    }

    /**
     * Put all mappings from the specified map as entries to {@link IServiceDesc#getFieldTypes() fieldTypes} map. Nulls are not permitted
     * @param entries The entries that will be added to the fieldTypes map
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder putAllFieldTypes(Map<String, ? extends String> entries) {
      this.fieldTypes.putAll(entries);
      return this;
    }

    /**
     * Initializes the value for the {@link IServiceDesc#getJavaClassAuxiliaries() javaClassAuxiliaries} attribute.
     * @param javaClassAuxiliaries The value for javaClassAuxiliaries (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("javaClassAuxiliaries")
    public final Builder javaClassAuxiliaries(@Nullable IJavaClassAux javaClassAuxiliaries) {
      this.javaClassAuxiliaries = javaClassAuxiliaries;
      return this;
    }

    /**
     * Initializes the value for the {@link IServiceDesc#getPythonClassAuxiliaries() pythonClassAuxiliaries} attribute.
     * @param pythonClassAuxiliaries The value for pythonClassAuxiliaries (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("pythonClassAuxiliaries")
    public final Builder pythonClassAuxiliaries(@Nullable IPythonClassAux pythonClassAuxiliaries) {
      this.pythonClassAuxiliaries = pythonClassAuxiliaries;
      return this;
    }

    /**
     * Initializes the value for the {@link IServiceDesc#getQualifier() qualifier} attribute.
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
     * Initializes the value for the {@link IServiceDesc#getSimpleName() simpleName} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IServiceDesc#getSimpleName() simpleName}.</em>
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
     * Adds one element to {@link IServiceDesc#getComments() comments} list.
     * @param element A comments element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addComments(String element) {
      this.comments.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IServiceDesc#getComments() comments} list.
     * @param elements An array of comments elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addComments(String... elements) {
      this.comments.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IServiceDesc#getComments() comments} list.
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
     * Adds elements to {@link IServiceDesc#getComments() comments} list.
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

    private boolean isAbstractIsSet() {
      return (optBits & OPT_BIT_IS_ABSTRACT) != 0;
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      return "Cannot build ServiceDesc, some of required attributes are not set " + attributes;
    }
  }
}
