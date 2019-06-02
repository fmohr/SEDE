/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.upb.sede.edd.process;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

public class DefaultProcessHandleBuilder extends AbstractProcessHandleBuilder {
    private final List<Object> arguments = new ArrayList<Object>();

    public DefaultProcessHandleBuilder(Executor executor) {
        super(executor);
    }

    @Override
    public List<String> getAllArguments() {
        return arguments.stream().map(Object::toString).collect(Collectors.toList());
    }

    public DefaultProcessHandleBuilder args(Object... args) {
        if (args == null) {
            throw new IllegalArgumentException("args == null!");
        }
        this.arguments.addAll(Arrays.asList(args));
        return this;
    }

    public DefaultProcessHandleBuilder args(Iterable<?> args) {
        for(Object o : args){
            arguments.add(o);
        }
        return this;
    }

    public DefaultProcessHandleBuilder setArgs(List<String> arguments) {
        this.arguments.clear();
        this.arguments.addAll(arguments);
        return this;
    }

    public DefaultProcessHandleBuilder setArgs(Iterable<?> arguments) {
        this.arguments.clear();
        for(Object o : arguments){
            this.arguments.add(o);
        }
        return this;
    }

    public List<String> getArgs() {
        List<String> args = new ArrayList<String>();
        for (Object argument : arguments) {
            args.add(argument.toString());
        }
        return args;
    }

    @Override
    public DefaultProcessHandleBuilder setIgnoreExitValue(boolean ignoreExitValue) {
        super.setIgnoreExitValue(ignoreExitValue);
        return this;
    }


    @Override
    public DefaultProcessHandleBuilder setDisplayName(String displayName) {
        super.setDisplayName(displayName);
        return this;
    }

    public DefaultProcessHandleBuilder redirectErrorStream() {
        super.redirectErrorStream();
        return this;
    }

    public DefaultProcessHandleBuilder setStandardOutput(OutputStream outputStream) {
        super.setStandardOutput(outputStream);
        return this;
    }

    public DefaultProcessHandleBuilder setStandardInput(InputStream inputStream) {
        super.setStandardInput(inputStream);
        return this;
    }


    public DefaultProcessHandleBuilder setTimeout(int timeoutMillis) {
        super.setTimeout(timeoutMillis);
        return this;
    }

    public DefaultProcessHandleBuilder setDaemon(boolean daemon) {
        super.daemon = daemon;
        return this;
    }
}
