package de.upb.sede.param.auxiliary;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.exec.auxiliary.IJavaDispatchAux;
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
 * Immutable implementation of {@link IJavaParameterizationAux}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code JavaParameterizationAux.builder()}.
 */
@Generated(from = "IJavaParameterizationAux", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class JavaParameterizationAux implements IJavaParameterizationAux {
  private final @Nullable IJavaDispatchAux parameterHandler;
  private final boolean autoScanEachParam;
  private final boolean bundleInMap;
  private final boolean bundleInArray;
  private final boolean bundleInList;
  private final boolean precedeParamsWithNames;
  private final @Nullable ImmutableList<String> paramOrder;

  private JavaParameterizationAux(JavaParameterizationAux.Builder builder) {
    this.parameterHandler = builder.parameterHandler;
    this.paramOrder = builder.paramOrder == null ? null : builder.paramOrder.build();
    if (builder.autoScanEachParamIsSet()) {
      initShim.autoScanEachParam(builder.autoScanEachParam);
    }
    if (builder.bundleInMapIsSet()) {
      initShim.bundleInMap(builder.bundleInMap);
    }
    if (builder.bundleInArrayIsSet()) {
      initShim.bundleInArray(builder.bundleInArray);
    }
    if (builder.bundleInListIsSet()) {
      initShim.bundleInList(builder.bundleInList);
    }
    if (builder.precedeParamsWithNamesIsSet()) {
      initShim.precedeParamsWithNames(builder.precedeParamsWithNames);
    }
    this.autoScanEachParam = initShim.getAutoScanEachParam();
    this.bundleInMap = initShim.getBundleInMap();
    this.bundleInArray = initShim.getBundleInArray();
    this.bundleInList = initShim.getBundleInList();
    this.precedeParamsWithNames = initShim.getPrecedeParamsWithNames();
    this.initShim = null;
  }

  private JavaParameterizationAux(
      @Nullable IJavaDispatchAux parameterHandler,
      boolean autoScanEachParam,
      boolean bundleInMap,
      boolean bundleInArray,
      boolean bundleInList,
      boolean precedeParamsWithNames,
      @Nullable ImmutableList<String> paramOrder) {
    this.parameterHandler = parameterHandler;
    this.autoScanEachParam = autoScanEachParam;
    this.bundleInMap = bundleInMap;
    this.bundleInArray = bundleInArray;
    this.bundleInList = bundleInList;
    this.precedeParamsWithNames = precedeParamsWithNames;
    this.paramOrder = paramOrder;
    this.initShim = null;
  }

  private static final byte STAGE_INITIALIZING = -1;
  private static final byte STAGE_UNINITIALIZED = 0;
  private static final byte STAGE_INITIALIZED = 1;
  @SuppressWarnings("Immutable")
  private transient volatile InitShim initShim = new InitShim();

  @Generated(from = "IJavaParameterizationAux", generator = "Immutables")
  private final class InitShim {
    private byte autoScanEachParamBuildStage = STAGE_UNINITIALIZED;
    private boolean autoScanEachParam;

    boolean getAutoScanEachParam() {
      if (autoScanEachParamBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (autoScanEachParamBuildStage == STAGE_UNINITIALIZED) {
        autoScanEachParamBuildStage = STAGE_INITIALIZING;
        this.autoScanEachParam = getAutoScanEachParamInitialize();
        autoScanEachParamBuildStage = STAGE_INITIALIZED;
      }
      return this.autoScanEachParam;
    }

    void autoScanEachParam(boolean autoScanEachParam) {
      this.autoScanEachParam = autoScanEachParam;
      autoScanEachParamBuildStage = STAGE_INITIALIZED;
    }

    private byte bundleInMapBuildStage = STAGE_UNINITIALIZED;
    private boolean bundleInMap;

    boolean getBundleInMap() {
      if (bundleInMapBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (bundleInMapBuildStage == STAGE_UNINITIALIZED) {
        bundleInMapBuildStage = STAGE_INITIALIZING;
        this.bundleInMap = getBundleInMapInitialize();
        bundleInMapBuildStage = STAGE_INITIALIZED;
      }
      return this.bundleInMap;
    }

    void bundleInMap(boolean bundleInMap) {
      this.bundleInMap = bundleInMap;
      bundleInMapBuildStage = STAGE_INITIALIZED;
    }

    private byte bundleInArrayBuildStage = STAGE_UNINITIALIZED;
    private boolean bundleInArray;

    boolean getBundleInArray() {
      if (bundleInArrayBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (bundleInArrayBuildStage == STAGE_UNINITIALIZED) {
        bundleInArrayBuildStage = STAGE_INITIALIZING;
        this.bundleInArray = getBundleInArrayInitialize();
        bundleInArrayBuildStage = STAGE_INITIALIZED;
      }
      return this.bundleInArray;
    }

    void bundleInArray(boolean bundleInArray) {
      this.bundleInArray = bundleInArray;
      bundleInArrayBuildStage = STAGE_INITIALIZED;
    }

    private byte bundleInListBuildStage = STAGE_UNINITIALIZED;
    private boolean bundleInList;

    boolean getBundleInList() {
      if (bundleInListBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (bundleInListBuildStage == STAGE_UNINITIALIZED) {
        bundleInListBuildStage = STAGE_INITIALIZING;
        this.bundleInList = getBundleInListInitialize();
        bundleInListBuildStage = STAGE_INITIALIZED;
      }
      return this.bundleInList;
    }

    void bundleInList(boolean bundleInList) {
      this.bundleInList = bundleInList;
      bundleInListBuildStage = STAGE_INITIALIZED;
    }

    private byte precedeParamsWithNamesBuildStage = STAGE_UNINITIALIZED;
    private boolean precedeParamsWithNames;

    boolean getPrecedeParamsWithNames() {
      if (precedeParamsWithNamesBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (precedeParamsWithNamesBuildStage == STAGE_UNINITIALIZED) {
        precedeParamsWithNamesBuildStage = STAGE_INITIALIZING;
        this.precedeParamsWithNames = getPrecedeParamsWithNamesInitialize();
        precedeParamsWithNamesBuildStage = STAGE_INITIALIZED;
      }
      return this.precedeParamsWithNames;
    }

    void precedeParamsWithNames(boolean precedeParamsWithNames) {
      this.precedeParamsWithNames = precedeParamsWithNames;
      precedeParamsWithNamesBuildStage = STAGE_INITIALIZED;
    }

    private String formatInitCycleMessage() {
      List<String> attributes = new ArrayList<>();
      if (autoScanEachParamBuildStage == STAGE_INITIALIZING) attributes.add("autoScanEachParam");
      if (bundleInMapBuildStage == STAGE_INITIALIZING) attributes.add("bundleInMap");
      if (bundleInArrayBuildStage == STAGE_INITIALIZING) attributes.add("bundleInArray");
      if (bundleInListBuildStage == STAGE_INITIALIZING) attributes.add("bundleInList");
      if (precedeParamsWithNamesBuildStage == STAGE_INITIALIZING) attributes.add("precedeParamsWithNames");
      return "Cannot build JavaParameterizationAux, attribute initializers form cycle " + attributes;
    }
  }

  private boolean getAutoScanEachParamInitialize() {
    return IJavaParameterizationAux.super.getAutoScanEachParam();
  }

  private boolean getBundleInMapInitialize() {
    return IJavaParameterizationAux.super.getBundleInMap();
  }

  private boolean getBundleInArrayInitialize() {
    return IJavaParameterizationAux.super.getBundleInArray();
  }

  private boolean getBundleInListInitialize() {
    return IJavaParameterizationAux.super.getBundleInList();
  }

  private boolean getPrecedeParamsWithNamesInitialize() {
    return IJavaParameterizationAux.super.getPrecedeParamsWithNames();
  }

  /**
   * @return The value of the {@code parameterHandler} attribute
   */
  @JsonProperty("parameterHandler")
  @Override
  public @Nullable IJavaDispatchAux getParameterHandler() {
    return parameterHandler;
  }

  /**
   * @return The value of the {@code autoScanEachParam} attribute
   */
  @JsonProperty("autoScanEachParam")
  @Override
  public boolean getAutoScanEachParam() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.getAutoScanEachParam()
        : this.autoScanEachParam;
  }

  /**
   * @return The value of the {@code bundleInMap} attribute
   */
  @JsonProperty("bundleInMap")
  @Override
  public boolean getBundleInMap() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.getBundleInMap()
        : this.bundleInMap;
  }

  /**
   * @return The value of the {@code bundleInArray} attribute
   */
  @JsonProperty("bundleInArray")
  @Override
  public boolean getBundleInArray() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.getBundleInArray()
        : this.bundleInArray;
  }

  /**
   * @return The value of the {@code bundleInList} attribute
   */
  @JsonProperty("bundleInList")
  @Override
  public boolean getBundleInList() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.getBundleInList()
        : this.bundleInList;
  }

  /**
   * @return The value of the {@code precedeParamsWithNames} attribute
   */
  @JsonProperty("precedeParamsWithNames")
  @Override
  public boolean getPrecedeParamsWithNames() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.getPrecedeParamsWithNames()
        : this.precedeParamsWithNames;
  }

  /**
   * @return The value of the {@code paramOrder} attribute
   */
  @JsonProperty("paramOrder")
  @Override
  public @Nullable ImmutableList<String> getParamOrder() {
    return paramOrder;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IJavaParameterizationAux#getParameterHandler() parameterHandler} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for parameterHandler (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final JavaParameterizationAux withParameterHandler(@Nullable IJavaDispatchAux value) {
    if (this.parameterHandler == value) return this;
    return new JavaParameterizationAux(
        value,
        this.autoScanEachParam,
        this.bundleInMap,
        this.bundleInArray,
        this.bundleInList,
        this.precedeParamsWithNames,
        this.paramOrder);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IJavaParameterizationAux#getAutoScanEachParam() autoScanEachParam} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for autoScanEachParam
   * @return A modified copy of the {@code this} object
   */
  public final JavaParameterizationAux withAutoScanEachParam(boolean value) {
    if (this.autoScanEachParam == value) return this;
    return new JavaParameterizationAux(
        this.parameterHandler,
        value,
        this.bundleInMap,
        this.bundleInArray,
        this.bundleInList,
        this.precedeParamsWithNames,
        this.paramOrder);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IJavaParameterizationAux#getBundleInMap() bundleInMap} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for bundleInMap
   * @return A modified copy of the {@code this} object
   */
  public final JavaParameterizationAux withBundleInMap(boolean value) {
    if (this.bundleInMap == value) return this;
    return new JavaParameterizationAux(
        this.parameterHandler,
        this.autoScanEachParam,
        value,
        this.bundleInArray,
        this.bundleInList,
        this.precedeParamsWithNames,
        this.paramOrder);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IJavaParameterizationAux#getBundleInArray() bundleInArray} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for bundleInArray
   * @return A modified copy of the {@code this} object
   */
  public final JavaParameterizationAux withBundleInArray(boolean value) {
    if (this.bundleInArray == value) return this;
    return new JavaParameterizationAux(
        this.parameterHandler,
        this.autoScanEachParam,
        this.bundleInMap,
        value,
        this.bundleInList,
        this.precedeParamsWithNames,
        this.paramOrder);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IJavaParameterizationAux#getBundleInList() bundleInList} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for bundleInList
   * @return A modified copy of the {@code this} object
   */
  public final JavaParameterizationAux withBundleInList(boolean value) {
    if (this.bundleInList == value) return this;
    return new JavaParameterizationAux(
        this.parameterHandler,
        this.autoScanEachParam,
        this.bundleInMap,
        this.bundleInArray,
        value,
        this.precedeParamsWithNames,
        this.paramOrder);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IJavaParameterizationAux#getPrecedeParamsWithNames() precedeParamsWithNames} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for precedeParamsWithNames
   * @return A modified copy of the {@code this} object
   */
  public final JavaParameterizationAux withPrecedeParamsWithNames(boolean value) {
    if (this.precedeParamsWithNames == value) return this;
    return new JavaParameterizationAux(
        this.parameterHandler,
        this.autoScanEachParam,
        this.bundleInMap,
        this.bundleInArray,
        this.bundleInList,
        value,
        this.paramOrder);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IJavaParameterizationAux#getParamOrder() paramOrder}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final JavaParameterizationAux withParamOrder(@Nullable String... elements) {
    if (elements == null) {
      return new JavaParameterizationAux(
          this.parameterHandler,
          this.autoScanEachParam,
          this.bundleInMap,
          this.bundleInArray,
          this.bundleInList,
          this.precedeParamsWithNames,
          null);
    }
    @Nullable ImmutableList<String> newValue = elements == null ? null : ImmutableList.copyOf(elements);
    return new JavaParameterizationAux(
        this.parameterHandler,
        this.autoScanEachParam,
        this.bundleInMap,
        this.bundleInArray,
        this.bundleInList,
        this.precedeParamsWithNames,
        newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IJavaParameterizationAux#getParamOrder() paramOrder}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of paramOrder elements to set
   * @return A modified copy of {@code this} object
   */
  public final JavaParameterizationAux withParamOrder(@Nullable Iterable<String> elements) {
    if (this.paramOrder == elements) return this;
    @Nullable ImmutableList<String> newValue = elements == null ? null : ImmutableList.copyOf(elements);
    return new JavaParameterizationAux(
        this.parameterHandler,
        this.autoScanEachParam,
        this.bundleInMap,
        this.bundleInArray,
        this.bundleInList,
        this.precedeParamsWithNames,
        newValue);
  }

  /**
   * This instance is equal to all instances of {@code JavaParameterizationAux} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof JavaParameterizationAux
        && equalTo((JavaParameterizationAux) another);
  }

  private boolean equalTo(JavaParameterizationAux another) {
    return Objects.equals(parameterHandler, another.parameterHandler)
        && autoScanEachParam == another.autoScanEachParam
        && bundleInMap == another.bundleInMap
        && bundleInArray == another.bundleInArray
        && bundleInList == another.bundleInList
        && precedeParamsWithNames == another.precedeParamsWithNames
        && Objects.equals(paramOrder, another.paramOrder);
  }

  /**
   * Computes a hash code from attributes: {@code parameterHandler}, {@code autoScanEachParam}, {@code bundleInMap}, {@code bundleInArray}, {@code bundleInList}, {@code precedeParamsWithNames}, {@code paramOrder}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Objects.hashCode(parameterHandler);
    h += (h << 5) + Booleans.hashCode(autoScanEachParam);
    h += (h << 5) + Booleans.hashCode(bundleInMap);
    h += (h << 5) + Booleans.hashCode(bundleInArray);
    h += (h << 5) + Booleans.hashCode(bundleInList);
    h += (h << 5) + Booleans.hashCode(precedeParamsWithNames);
    h += (h << 5) + Objects.hashCode(paramOrder);
    return h;
  }

  /**
   * Prints the immutable value {@code JavaParameterizationAux} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("JavaParameterizationAux")
        .omitNullValues()
        .add("parameterHandler", parameterHandler)
        .add("autoScanEachParam", autoScanEachParam)
        .add("bundleInMap", bundleInMap)
        .add("bundleInArray", bundleInArray)
        .add("bundleInList", bundleInList)
        .add("precedeParamsWithNames", precedeParamsWithNames)
        .add("paramOrder", paramOrder)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IJavaParameterizationAux", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IJavaParameterizationAux {
    @Nullable IJavaDispatchAux parameterHandler;
    boolean autoScanEachParam;
    boolean autoScanEachParamIsSet;
    boolean bundleInMap;
    boolean bundleInMapIsSet;
    boolean bundleInArray;
    boolean bundleInArrayIsSet;
    boolean bundleInList;
    boolean bundleInListIsSet;
    boolean precedeParamsWithNames;
    boolean precedeParamsWithNamesIsSet;
    @Nullable List<String> paramOrder = null;
    @JsonProperty("parameterHandler")
    public void setParameterHandler(@Nullable IJavaDispatchAux parameterHandler) {
      this.parameterHandler = parameterHandler;
    }
    @JsonProperty("autoScanEachParam")
    public void setAutoScanEachParam(boolean autoScanEachParam) {
      this.autoScanEachParam = autoScanEachParam;
      this.autoScanEachParamIsSet = true;
    }
    @JsonProperty("bundleInMap")
    public void setBundleInMap(boolean bundleInMap) {
      this.bundleInMap = bundleInMap;
      this.bundleInMapIsSet = true;
    }
    @JsonProperty("bundleInArray")
    public void setBundleInArray(boolean bundleInArray) {
      this.bundleInArray = bundleInArray;
      this.bundleInArrayIsSet = true;
    }
    @JsonProperty("bundleInList")
    public void setBundleInList(boolean bundleInList) {
      this.bundleInList = bundleInList;
      this.bundleInListIsSet = true;
    }
    @JsonProperty("precedeParamsWithNames")
    public void setPrecedeParamsWithNames(boolean precedeParamsWithNames) {
      this.precedeParamsWithNames = precedeParamsWithNames;
      this.precedeParamsWithNamesIsSet = true;
    }
    @JsonProperty("paramOrder")
    public void setParamOrder(@Nullable List<String> paramOrder) {
      this.paramOrder = paramOrder;
    }
    @Override
    public IJavaDispatchAux getParameterHandler() { throw new UnsupportedOperationException(); }
    @Override
    public boolean getAutoScanEachParam() { throw new UnsupportedOperationException(); }
    @Override
    public boolean getBundleInMap() { throw new UnsupportedOperationException(); }
    @Override
    public boolean getBundleInArray() { throw new UnsupportedOperationException(); }
    @Override
    public boolean getBundleInList() { throw new UnsupportedOperationException(); }
    @Override
    public boolean getPrecedeParamsWithNames() { throw new UnsupportedOperationException(); }
    @Override
    public List<String> getParamOrder() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static JavaParameterizationAux fromJson(Json json) {
    JavaParameterizationAux.Builder builder = JavaParameterizationAux.builder();
    if (json.parameterHandler != null) {
      builder.parameterHandler(json.parameterHandler);
    }
    if (json.autoScanEachParamIsSet) {
      builder.autoScanEachParam(json.autoScanEachParam);
    }
    if (json.bundleInMapIsSet) {
      builder.bundleInMap(json.bundleInMap);
    }
    if (json.bundleInArrayIsSet) {
      builder.bundleInArray(json.bundleInArray);
    }
    if (json.bundleInListIsSet) {
      builder.bundleInList(json.bundleInList);
    }
    if (json.precedeParamsWithNamesIsSet) {
      builder.precedeParamsWithNames(json.precedeParamsWithNames);
    }
    if (json.paramOrder != null) {
      builder.addAllParamOrder(json.paramOrder);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IJavaParameterizationAux} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable JavaParameterizationAux instance
   */
  public static JavaParameterizationAux copyOf(IJavaParameterizationAux instance) {
    if (instance instanceof JavaParameterizationAux) {
      return (JavaParameterizationAux) instance;
    }
    return JavaParameterizationAux.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link JavaParameterizationAux JavaParameterizationAux}.
   * <pre>
   * JavaParameterizationAux.builder()
   *    .parameterHandler(de.upb.sede.exec.auxiliary.IJavaDispatchAux | null) // nullable {@link IJavaParameterizationAux#getParameterHandler() parameterHandler}
   *    .autoScanEachParam(boolean) // optional {@link IJavaParameterizationAux#getAutoScanEachParam() autoScanEachParam}
   *    .bundleInMap(boolean) // optional {@link IJavaParameterizationAux#getBundleInMap() bundleInMap}
   *    .bundleInArray(boolean) // optional {@link IJavaParameterizationAux#getBundleInArray() bundleInArray}
   *    .bundleInList(boolean) // optional {@link IJavaParameterizationAux#getBundleInList() bundleInList}
   *    .precedeParamsWithNames(boolean) // optional {@link IJavaParameterizationAux#getPrecedeParamsWithNames() precedeParamsWithNames}
   *    .paramOrder(List&lt;String&gt; | null) // nullable {@link IJavaParameterizationAux#getParamOrder() paramOrder}
   *    .build();
   * </pre>
   * @return A new JavaParameterizationAux builder
   */
  public static JavaParameterizationAux.Builder builder() {
    return new JavaParameterizationAux.Builder();
  }

  /**
   * Builds instances of type {@link JavaParameterizationAux JavaParameterizationAux}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IJavaParameterizationAux", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long OPT_BIT_AUTO_SCAN_EACH_PARAM = 0x1L;
    private static final long OPT_BIT_BUNDLE_IN_MAP = 0x2L;
    private static final long OPT_BIT_BUNDLE_IN_ARRAY = 0x4L;
    private static final long OPT_BIT_BUNDLE_IN_LIST = 0x8L;
    private static final long OPT_BIT_PRECEDE_PARAMS_WITH_NAMES = 0x10L;
    private long optBits;

    private @Nullable IJavaDispatchAux parameterHandler;
    private boolean autoScanEachParam;
    private boolean bundleInMap;
    private boolean bundleInArray;
    private boolean bundleInList;
    private boolean precedeParamsWithNames;
    private ImmutableList.Builder<String> paramOrder = null;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableJavaParameterizationAux} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableJavaParameterizationAux instance) {
      Objects.requireNonNull(instance, "instance");
      @Nullable IJavaDispatchAux parameterHandlerValue = instance.getParameterHandler();
      if (parameterHandlerValue != null) {
        parameterHandler(parameterHandlerValue);
      }
      autoScanEachParam(instance.getAutoScanEachParam());
      bundleInMap(instance.getBundleInMap());
      bundleInArray(instance.getBundleInArray());
      bundleInList(instance.getBundleInList());
      precedeParamsWithNames(instance.getPrecedeParamsWithNames());
      @Nullable List<String> paramOrderValue = instance.getParamOrder();
      if (paramOrderValue != null) {
        addAllParamOrder(paramOrderValue);
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IJavaParameterizationAux} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IJavaParameterizationAux instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableJavaParameterizationAux) {
        return from((MutableJavaParameterizationAux) instance);
      }
      @Nullable IJavaDispatchAux parameterHandlerValue = instance.getParameterHandler();
      if (parameterHandlerValue != null) {
        parameterHandler(parameterHandlerValue);
      }
      autoScanEachParam(instance.getAutoScanEachParam());
      bundleInMap(instance.getBundleInMap());
      bundleInArray(instance.getBundleInArray());
      bundleInList(instance.getBundleInList());
      precedeParamsWithNames(instance.getPrecedeParamsWithNames());
      @Nullable List<String> paramOrderValue = instance.getParamOrder();
      if (paramOrderValue != null) {
        addAllParamOrder(paramOrderValue);
      }
      return this;
    }

    /**
     * Initializes the value for the {@link IJavaParameterizationAux#getParameterHandler() parameterHandler} attribute.
     * @param parameterHandler The value for parameterHandler (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("parameterHandler")
    public final Builder parameterHandler(@Nullable IJavaDispatchAux parameterHandler) {
      this.parameterHandler = parameterHandler;
      return this;
    }

    /**
     * Initializes the value for the {@link IJavaParameterizationAux#getAutoScanEachParam() autoScanEachParam} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IJavaParameterizationAux#getAutoScanEachParam() autoScanEachParam}.</em>
     * @param autoScanEachParam The value for autoScanEachParam 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("autoScanEachParam")
    public final Builder autoScanEachParam(boolean autoScanEachParam) {
      this.autoScanEachParam = autoScanEachParam;
      optBits |= OPT_BIT_AUTO_SCAN_EACH_PARAM;
      return this;
    }

    /**
     * Initializes the value for the {@link IJavaParameterizationAux#getBundleInMap() bundleInMap} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IJavaParameterizationAux#getBundleInMap() bundleInMap}.</em>
     * @param bundleInMap The value for bundleInMap 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("bundleInMap")
    public final Builder bundleInMap(boolean bundleInMap) {
      this.bundleInMap = bundleInMap;
      optBits |= OPT_BIT_BUNDLE_IN_MAP;
      return this;
    }

    /**
     * Initializes the value for the {@link IJavaParameterizationAux#getBundleInArray() bundleInArray} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IJavaParameterizationAux#getBundleInArray() bundleInArray}.</em>
     * @param bundleInArray The value for bundleInArray 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("bundleInArray")
    public final Builder bundleInArray(boolean bundleInArray) {
      this.bundleInArray = bundleInArray;
      optBits |= OPT_BIT_BUNDLE_IN_ARRAY;
      return this;
    }

    /**
     * Initializes the value for the {@link IJavaParameterizationAux#getBundleInList() bundleInList} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IJavaParameterizationAux#getBundleInList() bundleInList}.</em>
     * @param bundleInList The value for bundleInList 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("bundleInList")
    public final Builder bundleInList(boolean bundleInList) {
      this.bundleInList = bundleInList;
      optBits |= OPT_BIT_BUNDLE_IN_LIST;
      return this;
    }

    /**
     * Initializes the value for the {@link IJavaParameterizationAux#getPrecedeParamsWithNames() precedeParamsWithNames} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IJavaParameterizationAux#getPrecedeParamsWithNames() precedeParamsWithNames}.</em>
     * @param precedeParamsWithNames The value for precedeParamsWithNames 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("precedeParamsWithNames")
    public final Builder precedeParamsWithNames(boolean precedeParamsWithNames) {
      this.precedeParamsWithNames = precedeParamsWithNames;
      optBits |= OPT_BIT_PRECEDE_PARAMS_WITH_NAMES;
      return this;
    }

    /**
     * Adds one element to {@link IJavaParameterizationAux#getParamOrder() paramOrder} list.
     * @param element A paramOrder element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addParamOrder(String element) {
      if (this.paramOrder == null) {
        this.paramOrder = ImmutableList.builder();
      }
      this.paramOrder.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IJavaParameterizationAux#getParamOrder() paramOrder} list.
     * @param elements An array of paramOrder elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addParamOrder(String... elements) {
      if (this.paramOrder == null) {
        this.paramOrder = ImmutableList.builder();
      }
      this.paramOrder.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IJavaParameterizationAux#getParamOrder() paramOrder} list.
     * @param elements An iterable of paramOrder elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("paramOrder")
    public final Builder paramOrder(@Nullable Iterable<String> elements) {
      if (elements == null) {
        this.paramOrder = null;
        return this;
      }
      this.paramOrder = ImmutableList.builder();
      return addAllParamOrder(elements);
    }

    /**
     * Adds elements to {@link IJavaParameterizationAux#getParamOrder() paramOrder} list.
     * @param elements An iterable of paramOrder elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllParamOrder(Iterable<String> elements) {
      Objects.requireNonNull(elements, "paramOrder element");
      if (this.paramOrder == null) {
        this.paramOrder = ImmutableList.builder();
      }
      this.paramOrder.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link JavaParameterizationAux JavaParameterizationAux}.
     * @return An immutable instance of JavaParameterizationAux
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public JavaParameterizationAux build() {
      return new JavaParameterizationAux(this);
    }

    private boolean autoScanEachParamIsSet() {
      return (optBits & OPT_BIT_AUTO_SCAN_EACH_PARAM) != 0;
    }

    private boolean bundleInMapIsSet() {
      return (optBits & OPT_BIT_BUNDLE_IN_MAP) != 0;
    }

    private boolean bundleInArrayIsSet() {
      return (optBits & OPT_BIT_BUNDLE_IN_ARRAY) != 0;
    }

    private boolean bundleInListIsSet() {
      return (optBits & OPT_BIT_BUNDLE_IN_LIST) != 0;
    }

    private boolean precedeParamsWithNamesIsSet() {
      return (optBits & OPT_BIT_PRECEDE_PARAMS_WITH_NAMES) != 0;
    }
  }
}
