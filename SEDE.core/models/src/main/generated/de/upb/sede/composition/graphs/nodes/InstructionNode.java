package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Booleans;
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
 * Immutable implementation of {@link IInstructionNode}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code InstructionNode.builder()}.
 */
@Generated(from = "IInstructionNode", generator = "Immutables")
@SuppressWarnings({"all", "deprecation"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class InstructionNode implements IInstructionNode {
  private final String fMInstruction;
  private final String fieldType;
  private final String fieldClass;
  private final String host;
  private final String context;
  private final boolean contextIsFieldFlag;
  private final String method;
  private final int outputIndex;
  private final ImmutableList<String> parameterFields;
  private final ImmutableList<String> parameterTypes;
  private final String nodeType;
  private final String fieldName;

  private InstructionNode(InstructionNode.Builder builder) {
    this.fMInstruction = builder.fMInstruction;
    this.fieldType = builder.fieldType;
    this.fieldClass = builder.fieldClass;
    this.host = builder.host;
    this.context = builder.context;
    this.contextIsFieldFlag = builder.contextIsFieldFlag;
    this.method = builder.method;
    this.parameterFields = builder.parameterFields.build();
    this.parameterTypes = builder.parameterTypes.build();
    this.fieldName = builder.fieldName;
    if (builder.outputIndexIsSet()) {
      initShim.outputIndex(builder.outputIndex);
    }
    if (builder.nodeType != null) {
      initShim.nodeType(builder.nodeType);
    }
    this.outputIndex = initShim.getOutputIndex();
    this.nodeType = initShim.getNodeType();
    this.initShim = null;
  }

  private InstructionNode(
      String fMInstruction,
      String fieldType,
      String fieldClass,
      String host,
      String context,
      boolean contextIsFieldFlag,
      String method,
      int outputIndex,
      ImmutableList<String> parameterFields,
      ImmutableList<String> parameterTypes,
      String nodeType,
      String fieldName) {
    this.fMInstruction = fMInstruction;
    this.fieldType = fieldType;
    this.fieldClass = fieldClass;
    this.host = host;
    this.context = context;
    this.contextIsFieldFlag = contextIsFieldFlag;
    this.method = method;
    this.outputIndex = outputIndex;
    this.parameterFields = parameterFields;
    this.parameterTypes = parameterTypes;
    this.nodeType = nodeType;
    this.fieldName = fieldName;
    this.initShim = null;
  }

  private static final byte STAGE_INITIALIZING = -1;
  private static final byte STAGE_UNINITIALIZED = 0;
  private static final byte STAGE_INITIALIZED = 1;
  @SuppressWarnings("Immutable")
  private transient volatile InitShim initShim = new InitShim();

  @Generated(from = "IInstructionNode", generator = "Immutables")
  private final class InitShim {
    private byte outputIndexBuildStage = STAGE_UNINITIALIZED;
    private int outputIndex;

    int getOutputIndex() {
      if (outputIndexBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (outputIndexBuildStage == STAGE_UNINITIALIZED) {
        outputIndexBuildStage = STAGE_INITIALIZING;
        this.outputIndex = getOutputIndexInitialize();
        outputIndexBuildStage = STAGE_INITIALIZED;
      }
      return this.outputIndex;
    }

    void outputIndex(int outputIndex) {
      this.outputIndex = outputIndex;
      outputIndexBuildStage = STAGE_INITIALIZED;
    }

    private byte nodeTypeBuildStage = STAGE_UNINITIALIZED;
    private String nodeType;

    String getNodeType() {
      if (nodeTypeBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (nodeTypeBuildStage == STAGE_UNINITIALIZED) {
        nodeTypeBuildStage = STAGE_INITIALIZING;
        this.nodeType = Objects.requireNonNull(getNodeTypeInitialize(), "nodeType");
        nodeTypeBuildStage = STAGE_INITIALIZED;
      }
      return this.nodeType;
    }

    void nodeType(String nodeType) {
      this.nodeType = nodeType;
      nodeTypeBuildStage = STAGE_INITIALIZED;
    }

    private String formatInitCycleMessage() {
      List<String> attributes = new ArrayList<>();
      if (outputIndexBuildStage == STAGE_INITIALIZING) attributes.add("outputIndex");
      if (nodeTypeBuildStage == STAGE_INITIALIZING) attributes.add("nodeType");
      return "Cannot build InstructionNode, attribute initializers form cycle " + attributes;
    }
  }

  private int getOutputIndexInitialize() {
    return IInstructionNode.super.getOutputIndex();
  }

  private String getNodeTypeInitialize() {
    return IInstructionNode.super.getNodeType();
  }

  /**
   * @return The value of the {@code fMInstruction} attribute
   */
  @JsonProperty("fMInstruction")
  @Override
  public String getFMInstruction() {
    return fMInstruction;
  }

  /**
   * @return The value of the {@code fieldType} attribute
   */
  @JsonProperty("fieldType")
  @Override
  public String getFieldType() {
    return fieldType;
  }

  /**
   * @return The value of the {@code fieldClass} attribute
   */
  @JsonProperty("fieldClass")
  @Override
  public String getFieldClass() {
    return fieldClass;
  }

  /**
   * @return The value of the {@code host} attribute
   */
  @JsonProperty("host")
  @Deprecated
  @Override
  public String getHost() {
    return host;
  }

  /**
   * @return The value of the {@code context} attribute
   */
  @JsonProperty("context")
  @Override
  public String getContext() {
    return context;
  }

  /**
   * @return The value of the {@code contextIsFieldFlag} attribute
   */
  @JsonProperty("contextIsFieldFlag")
  @Override
  public boolean getContextIsFieldFlag() {
    return contextIsFieldFlag;
  }

  /**
   * @return The value of the {@code method} attribute
   */
  @JsonProperty("method")
  @Override
  public String getMethod() {
    return method;
  }

  /**
   * Index of instruction output.
   * -1 means that the return value of the instruction is the output.
   * Any other value >= 0 will have the instruction return the ith parameter as output.
   */
  @JsonProperty("outputIndex")
  @Override
  public int getOutputIndex() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.getOutputIndex()
        : this.outputIndex;
  }

  /**
   * Parameters for method or constructor invocation. The order of the parameters
   * has to be kept unchanged. May either contain field-names referencing to data
   * or else constants like numbers or strings. e.g.: ["a1", "b1", "10", "\"a\""]
   * The first two are fieldnames. The third is a constant number. The fourth is a
   * constant string.
   * <p>
   * <p>
   * The list itself is read-only.
   */
  @JsonProperty("parameterFields")
  @Override
  public ImmutableList<String> getParameterFields() {
    return parameterFields;
  }

  /**
   * @return The value of the {@code parameterTypes} attribute
   */
  @JsonProperty("parameterTypes")
  @Override
  public ImmutableList<String> getParameterTypes() {
    return parameterTypes;
  }

  /**
   * @return The value of the {@code nodeType} attribute
   */
  @JsonProperty("nodeType")
  @Override
  public String getNodeType() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.getNodeType()
        : this.nodeType;
  }

  /**
   * Returns the field name that this node is referencing.
   * @return Referenced field name
   */
  @JsonProperty("fieldName")
  @Override
  public String getFieldName() {
    return fieldName;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IInstructionNode#getFMInstruction() fMInstruction} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for fMInstruction
   * @return A modified copy of the {@code this} object
   */
  public final InstructionNode withFMInstruction(String value) {
    String newValue = Objects.requireNonNull(value, "fMInstruction");
    if (this.fMInstruction.equals(newValue)) return this;
    return new InstructionNode(
        newValue,
        this.fieldType,
        this.fieldClass,
        this.host,
        this.context,
        this.contextIsFieldFlag,
        this.method,
        this.outputIndex,
        this.parameterFields,
        this.parameterTypes,
        this.nodeType,
        this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IInstructionNode#getFieldType() fieldType} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for fieldType
   * @return A modified copy of the {@code this} object
   */
  public final InstructionNode withFieldType(String value) {
    String newValue = Objects.requireNonNull(value, "fieldType");
    if (this.fieldType.equals(newValue)) return this;
    return new InstructionNode(
        this.fMInstruction,
        newValue,
        this.fieldClass,
        this.host,
        this.context,
        this.contextIsFieldFlag,
        this.method,
        this.outputIndex,
        this.parameterFields,
        this.parameterTypes,
        this.nodeType,
        this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IInstructionNode#getFieldClass() fieldClass} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for fieldClass
   * @return A modified copy of the {@code this} object
   */
  public final InstructionNode withFieldClass(String value) {
    String newValue = Objects.requireNonNull(value, "fieldClass");
    if (this.fieldClass.equals(newValue)) return this;
    return new InstructionNode(
        this.fMInstruction,
        this.fieldType,
        newValue,
        this.host,
        this.context,
        this.contextIsFieldFlag,
        this.method,
        this.outputIndex,
        this.parameterFields,
        this.parameterTypes,
        this.nodeType,
        this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IInstructionNode#getHost() host} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for host
   * @return A modified copy of the {@code this} object
   */
  @Deprecated
  public final InstructionNode withHost(String value) {
    String newValue = Objects.requireNonNull(value, "host");
    if (this.host.equals(newValue)) return this;
    return new InstructionNode(
        this.fMInstruction,
        this.fieldType,
        this.fieldClass,
        newValue,
        this.context,
        this.contextIsFieldFlag,
        this.method,
        this.outputIndex,
        this.parameterFields,
        this.parameterTypes,
        this.nodeType,
        this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IInstructionNode#getContext() context} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for context
   * @return A modified copy of the {@code this} object
   */
  public final InstructionNode withContext(String value) {
    String newValue = Objects.requireNonNull(value, "context");
    if (this.context.equals(newValue)) return this;
    return new InstructionNode(
        this.fMInstruction,
        this.fieldType,
        this.fieldClass,
        this.host,
        newValue,
        this.contextIsFieldFlag,
        this.method,
        this.outputIndex,
        this.parameterFields,
        this.parameterTypes,
        this.nodeType,
        this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IInstructionNode#getContextIsFieldFlag() contextIsFieldFlag} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for contextIsFieldFlag
   * @return A modified copy of the {@code this} object
   */
  public final InstructionNode withContextIsFieldFlag(boolean value) {
    if (this.contextIsFieldFlag == value) return this;
    return new InstructionNode(
        this.fMInstruction,
        this.fieldType,
        this.fieldClass,
        this.host,
        this.context,
        value,
        this.method,
        this.outputIndex,
        this.parameterFields,
        this.parameterTypes,
        this.nodeType,
        this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IInstructionNode#getMethod() method} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for method
   * @return A modified copy of the {@code this} object
   */
  public final InstructionNode withMethod(String value) {
    String newValue = Objects.requireNonNull(value, "method");
    if (this.method.equals(newValue)) return this;
    return new InstructionNode(
        this.fMInstruction,
        this.fieldType,
        this.fieldClass,
        this.host,
        this.context,
        this.contextIsFieldFlag,
        newValue,
        this.outputIndex,
        this.parameterFields,
        this.parameterTypes,
        this.nodeType,
        this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IInstructionNode#getOutputIndex() outputIndex} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for outputIndex
   * @return A modified copy of the {@code this} object
   */
  public final InstructionNode withOutputIndex(int value) {
    if (this.outputIndex == value) return this;
    return new InstructionNode(
        this.fMInstruction,
        this.fieldType,
        this.fieldClass,
        this.host,
        this.context,
        this.contextIsFieldFlag,
        this.method,
        value,
        this.parameterFields,
        this.parameterTypes,
        this.nodeType,
        this.fieldName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IInstructionNode#getParameterFields() parameterFields}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final InstructionNode withParameterFields(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new InstructionNode(
        this.fMInstruction,
        this.fieldType,
        this.fieldClass,
        this.host,
        this.context,
        this.contextIsFieldFlag,
        this.method,
        this.outputIndex,
        newValue,
        this.parameterTypes,
        this.nodeType,
        this.fieldName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IInstructionNode#getParameterFields() parameterFields}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of parameterFields elements to set
   * @return A modified copy of {@code this} object
   */
  public final InstructionNode withParameterFields(Iterable<String> elements) {
    if (this.parameterFields == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new InstructionNode(
        this.fMInstruction,
        this.fieldType,
        this.fieldClass,
        this.host,
        this.context,
        this.contextIsFieldFlag,
        this.method,
        this.outputIndex,
        newValue,
        this.parameterTypes,
        this.nodeType,
        this.fieldName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IInstructionNode#getParameterTypes() parameterTypes}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final InstructionNode withParameterTypes(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new InstructionNode(
        this.fMInstruction,
        this.fieldType,
        this.fieldClass,
        this.host,
        this.context,
        this.contextIsFieldFlag,
        this.method,
        this.outputIndex,
        this.parameterFields,
        newValue,
        this.nodeType,
        this.fieldName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IInstructionNode#getParameterTypes() parameterTypes}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of parameterTypes elements to set
   * @return A modified copy of {@code this} object
   */
  public final InstructionNode withParameterTypes(Iterable<String> elements) {
    if (this.parameterTypes == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new InstructionNode(
        this.fMInstruction,
        this.fieldType,
        this.fieldClass,
        this.host,
        this.context,
        this.contextIsFieldFlag,
        this.method,
        this.outputIndex,
        this.parameterFields,
        newValue,
        this.nodeType,
        this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IInstructionNode#getNodeType() nodeType} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for nodeType
   * @return A modified copy of the {@code this} object
   */
  public final InstructionNode withNodeType(String value) {
    String newValue = Objects.requireNonNull(value, "nodeType");
    if (this.nodeType.equals(newValue)) return this;
    return new InstructionNode(
        this.fMInstruction,
        this.fieldType,
        this.fieldClass,
        this.host,
        this.context,
        this.contextIsFieldFlag,
        this.method,
        this.outputIndex,
        this.parameterFields,
        this.parameterTypes,
        newValue,
        this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IInstructionNode#getFieldName() fieldName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for fieldName
   * @return A modified copy of the {@code this} object
   */
  public final InstructionNode withFieldName(String value) {
    String newValue = Objects.requireNonNull(value, "fieldName");
    if (this.fieldName.equals(newValue)) return this;
    return new InstructionNode(
        this.fMInstruction,
        this.fieldType,
        this.fieldClass,
        this.host,
        this.context,
        this.contextIsFieldFlag,
        this.method,
        this.outputIndex,
        this.parameterFields,
        this.parameterTypes,
        this.nodeType,
        newValue);
  }

  /**
   * This instance is equal to all instances of {@code InstructionNode} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof InstructionNode
        && equalTo((InstructionNode) another);
  }

  private boolean equalTo(InstructionNode another) {
    return fMInstruction.equals(another.fMInstruction)
        && fieldType.equals(another.fieldType)
        && fieldClass.equals(another.fieldClass)
        && host.equals(another.host)
        && context.equals(another.context)
        && contextIsFieldFlag == another.contextIsFieldFlag
        && method.equals(another.method)
        && outputIndex == another.outputIndex
        && parameterFields.equals(another.parameterFields)
        && parameterTypes.equals(another.parameterTypes)
        && nodeType.equals(another.nodeType)
        && fieldName.equals(another.fieldName);
  }

  /**
   * Computes a hash code from attributes: {@code fMInstruction}, {@code fieldType}, {@code fieldClass}, {@code host}, {@code context}, {@code contextIsFieldFlag}, {@code method}, {@code outputIndex}, {@code parameterFields}, {@code parameterTypes}, {@code nodeType}, {@code fieldName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + fMInstruction.hashCode();
    h += (h << 5) + fieldType.hashCode();
    h += (h << 5) + fieldClass.hashCode();
    h += (h << 5) + host.hashCode();
    h += (h << 5) + context.hashCode();
    h += (h << 5) + Booleans.hashCode(contextIsFieldFlag);
    h += (h << 5) + method.hashCode();
    h += (h << 5) + outputIndex;
    h += (h << 5) + parameterFields.hashCode();
    h += (h << 5) + parameterTypes.hashCode();
    h += (h << 5) + nodeType.hashCode();
    h += (h << 5) + fieldName.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code InstructionNode} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("InstructionNode")
        .omitNullValues()
        .add("fMInstruction", fMInstruction)
        .add("fieldType", fieldType)
        .add("fieldClass", fieldClass)
        .add("host", host)
        .add("context", context)
        .add("contextIsFieldFlag", contextIsFieldFlag)
        .add("method", method)
        .add("outputIndex", outputIndex)
        .add("parameterFields", parameterFields)
        .add("parameterTypes", parameterTypes)
        .add("nodeType", nodeType)
        .add("fieldName", fieldName)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IInstructionNode", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IInstructionNode {
    @Nullable String fMInstruction;
    @Nullable String fieldType;
    @Nullable String fieldClass;
    @Nullable String host;
    @Nullable String context;
    boolean contextIsFieldFlag;
    boolean contextIsFieldFlagIsSet;
    @Nullable String method;
    int outputIndex;
    boolean outputIndexIsSet;
    @Nullable List<String> parameterFields = ImmutableList.of();
    @Nullable List<String> parameterTypes = ImmutableList.of();
    @Nullable String nodeType;
    @Nullable String fieldName;
    @JsonProperty("fMInstruction")
    public void setFMInstruction(String fMInstruction) {
      this.fMInstruction = fMInstruction;
    }
    @JsonProperty("fieldType")
    public void setFieldType(String fieldType) {
      this.fieldType = fieldType;
    }
    @JsonProperty("fieldClass")
    public void setFieldClass(String fieldClass) {
      this.fieldClass = fieldClass;
    }
    @JsonProperty("host")
    public void setHost(String host) {
      this.host = host;
    }
    @JsonProperty("context")
    public void setContext(String context) {
      this.context = context;
    }
    @JsonProperty("contextIsFieldFlag")
    public void setContextIsFieldFlag(boolean contextIsFieldFlag) {
      this.contextIsFieldFlag = contextIsFieldFlag;
      this.contextIsFieldFlagIsSet = true;
    }
    @JsonProperty("method")
    public void setMethod(String method) {
      this.method = method;
    }
    @JsonProperty("outputIndex")
    public void setOutputIndex(int outputIndex) {
      this.outputIndex = outputIndex;
      this.outputIndexIsSet = true;
    }
    @JsonProperty("parameterFields")
    public void setParameterFields(List<String> parameterFields) {
      this.parameterFields = parameterFields;
    }
    @JsonProperty("parameterTypes")
    public void setParameterTypes(List<String> parameterTypes) {
      this.parameterTypes = parameterTypes;
    }
    @JsonProperty("nodeType")
    public void setNodeType(String nodeType) {
      this.nodeType = nodeType;
    }
    @JsonProperty("fieldName")
    public void setFieldName(String fieldName) {
      this.fieldName = fieldName;
    }
    @Override
    public String getFMInstruction() { throw new UnsupportedOperationException(); }
    @Override
    public String getFieldType() { throw new UnsupportedOperationException(); }
    @Override
    public String getFieldClass() { throw new UnsupportedOperationException(); }
    @Override
    public String getHost() { throw new UnsupportedOperationException(); }
    @Override
    public String getContext() { throw new UnsupportedOperationException(); }
    @Override
    public boolean getContextIsFieldFlag() { throw new UnsupportedOperationException(); }
    @Override
    public String getMethod() { throw new UnsupportedOperationException(); }
    @Override
    public int getOutputIndex() { throw new UnsupportedOperationException(); }
    @Override
    public List<String> getParameterFields() { throw new UnsupportedOperationException(); }
    @Override
    public List<String> getParameterTypes() { throw new UnsupportedOperationException(); }
    @Override
    public String getNodeType() { throw new UnsupportedOperationException(); }
    @Override
    public String getFieldName() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static InstructionNode fromJson(Json json) {
    InstructionNode.Builder builder = InstructionNode.builder();
    if (json.fMInstruction != null) {
      builder.fMInstruction(json.fMInstruction);
    }
    if (json.fieldType != null) {
      builder.fieldType(json.fieldType);
    }
    if (json.fieldClass != null) {
      builder.fieldClass(json.fieldClass);
    }
    if (json.host != null) {
      builder.host(json.host);
    }
    if (json.context != null) {
      builder.context(json.context);
    }
    if (json.contextIsFieldFlagIsSet) {
      builder.contextIsFieldFlag(json.contextIsFieldFlag);
    }
    if (json.method != null) {
      builder.method(json.method);
    }
    if (json.outputIndexIsSet) {
      builder.outputIndex(json.outputIndex);
    }
    if (json.parameterFields != null) {
      builder.addAllParameterFields(json.parameterFields);
    }
    if (json.parameterTypes != null) {
      builder.addAllParameterTypes(json.parameterTypes);
    }
    if (json.nodeType != null) {
      builder.nodeType(json.nodeType);
    }
    if (json.fieldName != null) {
      builder.fieldName(json.fieldName);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IInstructionNode} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable InstructionNode instance
   */
  public static InstructionNode copyOf(IInstructionNode instance) {
    if (instance instanceof InstructionNode) {
      return (InstructionNode) instance;
    }
    return InstructionNode.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link InstructionNode InstructionNode}.
   * <pre>
   * InstructionNode.builder()
   *    .fMInstruction(String) // required {@link IInstructionNode#getFMInstruction() fMInstruction}
   *    .fieldType(String) // required {@link IInstructionNode#getFieldType() fieldType}
   *    .fieldClass(String) // required {@link IInstructionNode#getFieldClass() fieldClass}
   *    .host(String) // required {@link IInstructionNode#getHost() host}
   *    .context(String) // required {@link IInstructionNode#getContext() context}
   *    .contextIsFieldFlag(boolean) // required {@link IInstructionNode#getContextIsFieldFlag() contextIsFieldFlag}
   *    .method(String) // required {@link IInstructionNode#getMethod() method}
   *    .outputIndex(int) // optional {@link IInstructionNode#getOutputIndex() outputIndex}
   *    .addParameterFields|addAllParameterFields(String) // {@link IInstructionNode#getParameterFields() parameterFields} elements
   *    .addParameterTypes|addAllParameterTypes(String) // {@link IInstructionNode#getParameterTypes() parameterTypes} elements
   *    .nodeType(String) // optional {@link IInstructionNode#getNodeType() nodeType}
   *    .fieldName(String) // required {@link IInstructionNode#getFieldName() fieldName}
   *    .build();
   * </pre>
   * @return A new InstructionNode builder
   */
  public static InstructionNode.Builder builder() {
    return new InstructionNode.Builder();
  }

  /**
   * Builds instances of type {@link InstructionNode InstructionNode}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IInstructionNode", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_F_M_INSTRUCTION = 0x1L;
    private static final long INIT_BIT_FIELD_TYPE = 0x2L;
    private static final long INIT_BIT_FIELD_CLASS = 0x4L;
    private static final long INIT_BIT_HOST = 0x8L;
    private static final long INIT_BIT_CONTEXT = 0x10L;
    private static final long INIT_BIT_CONTEXT_IS_FIELD_FLAG = 0x20L;
    private static final long INIT_BIT_METHOD = 0x40L;
    private static final long INIT_BIT_FIELD_NAME = 0x80L;
    private static final long OPT_BIT_OUTPUT_INDEX = 0x1L;
    private long initBits = 0xffL;
    private long optBits;

    private @Nullable String fMInstruction;
    private @Nullable String fieldType;
    private @Nullable String fieldClass;
    private @Nullable String host;
    private @Nullable String context;
    private boolean contextIsFieldFlag;
    private @Nullable String method;
    private int outputIndex;
    private ImmutableList.Builder<String> parameterFields = ImmutableList.builder();
    private ImmutableList.Builder<String> parameterTypes = ImmutableList.builder();
    private @Nullable String nodeType;
    private @Nullable String fieldName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableInstructionNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableInstructionNode instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.fMInstructionIsSet()) {
        fMInstruction(instance.getFMInstruction());
      }
      if (instance.fieldTypeIsSet()) {
        fieldType(instance.getFieldType());
      }
      if (instance.fieldClassIsSet()) {
        fieldClass(instance.getFieldClass());
      }
      if (instance.hostIsSet()) {
        host(instance.getHost());
      }
      if (instance.contextIsSet()) {
        context(instance.getContext());
      }
      if (instance.contextIsFieldFlagIsSet()) {
        contextIsFieldFlag(instance.getContextIsFieldFlag());
      }
      if (instance.methodIsSet()) {
        method(instance.getMethod());
      }
      outputIndex(instance.getOutputIndex());
      addAllParameterFields(instance.getParameterFields());
      addAllParameterTypes(instance.getParameterTypes());
      nodeType(instance.getNodeType());
      if (instance.fieldNameIsSet()) {
        fieldName(instance.getFieldName());
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.nodes.IFieldNameAware} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IFieldNameAware instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.nodes.IInstructionNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IInstructionNode instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.nodes.IBaseNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IBaseNode instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof MutableInstructionNode) {
        from((MutableInstructionNode) object);
        return;
      }
      if (object instanceof IFieldNameAware) {
        IFieldNameAware instance = (IFieldNameAware) object;
        fieldName(instance.getFieldName());
      }
      if (object instanceof IInstructionNode) {
        IInstructionNode instance = (IInstructionNode) object;
        addAllParameterFields(instance.getParameterFields());
        fMInstruction(instance.getFMInstruction());
        contextIsFieldFlag(instance.getContextIsFieldFlag());
        outputIndex(instance.getOutputIndex());
        method(instance.getMethod());
        addAllParameterTypes(instance.getParameterTypes());
        host(instance.getHost());
        context(instance.getContext());
        fieldType(instance.getFieldType());
        fieldClass(instance.getFieldClass());
      }
      if (object instanceof IBaseNode) {
        IBaseNode instance = (IBaseNode) object;
        nodeType(instance.getNodeType());
      }
    }

    /**
     * Initializes the value for the {@link IInstructionNode#getFMInstruction() fMInstruction} attribute.
     * @param fMInstruction The value for fMInstruction 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("fMInstruction")
    public final Builder fMInstruction(String fMInstruction) {
      this.fMInstruction = Objects.requireNonNull(fMInstruction, "fMInstruction");
      initBits &= ~INIT_BIT_F_M_INSTRUCTION;
      return this;
    }

    /**
     * Initializes the value for the {@link IInstructionNode#getFieldType() fieldType} attribute.
     * @param fieldType The value for fieldType 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("fieldType")
    public final Builder fieldType(String fieldType) {
      this.fieldType = Objects.requireNonNull(fieldType, "fieldType");
      initBits &= ~INIT_BIT_FIELD_TYPE;
      return this;
    }

    /**
     * Initializes the value for the {@link IInstructionNode#getFieldClass() fieldClass} attribute.
     * @param fieldClass The value for fieldClass 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("fieldClass")
    public final Builder fieldClass(String fieldClass) {
      this.fieldClass = Objects.requireNonNull(fieldClass, "fieldClass");
      initBits &= ~INIT_BIT_FIELD_CLASS;
      return this;
    }

    /**
     * Initializes the value for the {@link IInstructionNode#getHost() host} attribute.
     * @param host The value for host 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("host")
    @Deprecated
    public final Builder host(String host) {
      this.host = Objects.requireNonNull(host, "host");
      initBits &= ~INIT_BIT_HOST;
      return this;
    }

    /**
     * Initializes the value for the {@link IInstructionNode#getContext() context} attribute.
     * @param context The value for context 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("context")
    public final Builder context(String context) {
      this.context = Objects.requireNonNull(context, "context");
      initBits &= ~INIT_BIT_CONTEXT;
      return this;
    }

    /**
     * Initializes the value for the {@link IInstructionNode#getContextIsFieldFlag() contextIsFieldFlag} attribute.
     * @param contextIsFieldFlag The value for contextIsFieldFlag 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("contextIsFieldFlag")
    public final Builder contextIsFieldFlag(boolean contextIsFieldFlag) {
      this.contextIsFieldFlag = contextIsFieldFlag;
      initBits &= ~INIT_BIT_CONTEXT_IS_FIELD_FLAG;
      return this;
    }

    /**
     * Initializes the value for the {@link IInstructionNode#getMethod() method} attribute.
     * @param method The value for method 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("method")
    public final Builder method(String method) {
      this.method = Objects.requireNonNull(method, "method");
      initBits &= ~INIT_BIT_METHOD;
      return this;
    }

    /**
     * Initializes the value for the {@link IInstructionNode#getOutputIndex() outputIndex} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IInstructionNode#getOutputIndex() outputIndex}.</em>
     * @param outputIndex The value for outputIndex 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("outputIndex")
    public final Builder outputIndex(int outputIndex) {
      this.outputIndex = outputIndex;
      optBits |= OPT_BIT_OUTPUT_INDEX;
      return this;
    }

    /**
     * Adds one element to {@link IInstructionNode#getParameterFields() parameterFields} list.
     * @param element A parameterFields element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addParameterFields(String element) {
      this.parameterFields.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IInstructionNode#getParameterFields() parameterFields} list.
     * @param elements An array of parameterFields elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addParameterFields(String... elements) {
      this.parameterFields.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IInstructionNode#getParameterFields() parameterFields} list.
     * @param elements An iterable of parameterFields elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("parameterFields")
    public final Builder parameterFields(Iterable<String> elements) {
      this.parameterFields = ImmutableList.builder();
      return addAllParameterFields(elements);
    }

    /**
     * Adds elements to {@link IInstructionNode#getParameterFields() parameterFields} list.
     * @param elements An iterable of parameterFields elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllParameterFields(Iterable<String> elements) {
      this.parameterFields.addAll(elements);
      return this;
    }

    /**
     * Adds one element to {@link IInstructionNode#getParameterTypes() parameterTypes} list.
     * @param element A parameterTypes element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addParameterTypes(String element) {
      this.parameterTypes.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IInstructionNode#getParameterTypes() parameterTypes} list.
     * @param elements An array of parameterTypes elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addParameterTypes(String... elements) {
      this.parameterTypes.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IInstructionNode#getParameterTypes() parameterTypes} list.
     * @param elements An iterable of parameterTypes elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("parameterTypes")
    public final Builder parameterTypes(Iterable<String> elements) {
      this.parameterTypes = ImmutableList.builder();
      return addAllParameterTypes(elements);
    }

    /**
     * Adds elements to {@link IInstructionNode#getParameterTypes() parameterTypes} list.
     * @param elements An iterable of parameterTypes elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllParameterTypes(Iterable<String> elements) {
      this.parameterTypes.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link IInstructionNode#getNodeType() nodeType} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IInstructionNode#getNodeType() nodeType}.</em>
     * @param nodeType The value for nodeType 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("nodeType")
    public final Builder nodeType(String nodeType) {
      this.nodeType = Objects.requireNonNull(nodeType, "nodeType");
      return this;
    }

    /**
     * Initializes the value for the {@link IInstructionNode#getFieldName() fieldName} attribute.
     * @param fieldName The value for fieldName 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("fieldName")
    public final Builder fieldName(String fieldName) {
      this.fieldName = Objects.requireNonNull(fieldName, "fieldName");
      initBits &= ~INIT_BIT_FIELD_NAME;
      return this;
    }

    /**
     * Builds a new {@link InstructionNode InstructionNode}.
     * @return An immutable instance of InstructionNode
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public InstructionNode build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new InstructionNode(this);
    }

    private boolean outputIndexIsSet() {
      return (optBits & OPT_BIT_OUTPUT_INDEX) != 0;
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_F_M_INSTRUCTION) != 0) attributes.add("fMInstruction");
      if ((initBits & INIT_BIT_FIELD_TYPE) != 0) attributes.add("fieldType");
      if ((initBits & INIT_BIT_FIELD_CLASS) != 0) attributes.add("fieldClass");
      if ((initBits & INIT_BIT_HOST) != 0) attributes.add("host");
      if ((initBits & INIT_BIT_CONTEXT) != 0) attributes.add("context");
      if ((initBits & INIT_BIT_CONTEXT_IS_FIELD_FLAG) != 0) attributes.add("contextIsFieldFlag");
      if ((initBits & INIT_BIT_METHOD) != 0) attributes.add("method");
      if ((initBits & INIT_BIT_FIELD_NAME) != 0) attributes.add("fieldName");
      return "Cannot build InstructionNode, some of required attributes are not set " + attributes;
    }
  }
}
