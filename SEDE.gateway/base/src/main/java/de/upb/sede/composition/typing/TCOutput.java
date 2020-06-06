package de.upb.sede.composition.typing;

import de.upb.sede.IServiceRef;
import de.upb.sede.composition.FieldType;
import de.upb.sede.composition.IFieldType;
import de.upb.sede.composition.ITypeCoercion;
import de.upb.sede.composition.types.TypeClass;
import de.upb.sede.exec.*;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TCOutput {

    private ContextInfo context;

    private MethodInfo method;

    private FieldTC fieldTC;

    TCOutput() {

    }

    TCOutput(TCOutput lastOutput) {
        if(lastOutput != null)
            fieldTC = new FieldTC(lastOutput.getFieldTC());
    }

    public ContextInfo getContext() {
        if(context == null)
            context = new ContextInfo();
        return context;
    }

    public MethodInfo getMethodInfo() {
        if(method == null)
            method = new MethodInfo();
        return method;
    }

    public FieldTC getFieldTC() {
        if(fieldTC == null)
            fieldTC = new FieldTC(null);
        return fieldTC;
    }

    public static class ContextInfo {
        private boolean isStatic;

        private String serviceQualifier;

        private IServiceRef serviceRef;

        private IServiceDesc serviceDesc;

        public boolean isStatic() {
            return isStatic;
        }

        public void setStatic(boolean aStatic) {
            isStatic = aStatic;
        }

        public String getServiceQualifier() {
            return serviceQualifier;
        }

        public void setServiceQualifier(String serviceQualifier) {
            this.serviceQualifier = serviceQualifier;
        }

        public IServiceRef getServiceRef() {
            return serviceRef;
        }

        public void setServiceRef(IServiceRef serviceRef) {
            this.serviceRef = serviceRef;
        }

        public IServiceDesc getServiceDesc() {
            return serviceDesc;
        }

        public void setServiceDesc(IServiceDesc serviceDesc) {
            this.serviceDesc = serviceDesc;
        }
    }

    public static class MethodInfo {

        private String methodQualifier;

        private IMethodRef methodRef;

        private IMethodDesc methodDesc;

        private List<ITypeCoercion> parameterTypeCoercions;

        public String getMethodQualifier() {
            return methodQualifier;
        }

        void setMethodQualifier(String methodQualifier) {
            this.methodQualifier = methodQualifier;
        }

        public IMethodRef getMethodRef() {
            return methodRef;
        }

        void setMethodRef(IMethodRef methodRef) {
            this.methodRef = methodRef;
        }

        public IMethodDesc getMethodDesc() {
            return methodDesc;
        }

        public void setMethodDesc(IMethodDesc methodDesc) {
            this.methodDesc = methodDesc;
        }

        public List<ITypeCoercion> getParameterTypeCoercions() {
            return parameterTypeCoercions;
        }

        public void setParameterTypeCoercions(List<ITypeCoercion> parameterTypeCoercions) {
            this.parameterTypeCoercions = parameterTypeCoercions;
        }
    }

    public static class FieldTC {

        private Map<String, TypeClass> typeMap = new HashMap<>();

        FieldTC(FieldTC prevTC) {
            if(prevTC != null) {
                typeMap = new HashMap<>(prevTC.typeMap);
            }
        }

        public void setInitialContext(List<IFieldType> initialContext) {
            initialContext.forEach(
                journalPage -> typeMap.put(journalPage.getFieldname(), journalPage.getType())
            );
        }

        @Nullable
        public TypeClass getFieldType(String fieldname) {
            return typeMap.getOrDefault(fieldname, null);
        }

        public void setFieldType(String fieldname, TypeClass valueType) {
            typeMap.put(fieldname, valueType);
        }

        private static IFieldType toFieldType(Map.Entry<String, TypeClass> entry) {
            return FieldType.builder()
                .fieldname(entry.getKey())
                .type(entry.getValue())
                .build();
        }

        public void extractInto(List<IFieldType> context) {
            typeMap.entrySet().stream()
                .map(FieldTC::toFieldType)
                .forEach(context::add);
        }

        public void copyInto(TypeJournalPage otherPage) {
            typeMap.forEach(otherPage::setFieldType);
        }

    }

}
