package com.eden.orchid.impl.compilers.pebble;

import com.eden.orchid.api.OrchidContext;
import com.eden.orchid.api.compilers.TemplateFunction;
import com.eden.orchid.api.theme.pages.OrchidPage;
import com.google.inject.Provider;
import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.extension.escaper.SafeString;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public final class PebbleWrapperTemplateFunction implements Function {

    private final Provider<OrchidContext> contextProvider;
    private final String name;
    private final List<String> params;
    private final Class<? extends TemplateFunction> functionClass;

    public PebbleWrapperTemplateFunction(Provider<OrchidContext> contextProvider, String name, List<String> params, Class<? extends TemplateFunction> functionClass) {
        this.contextProvider = contextProvider;
        this.name = name;
        this.params = params;
        this.functionClass = functionClass;
    }

    @Override
    public List<String> getArgumentNames() {
        return params;
    }

    @Override
    public Object execute(
            Map<String, Object> args,
            PebbleTemplate self,
            EvaluationContext context,
            int lineNumber) {
        TemplateFunction freshFunction = contextProvider.get().getInjector().getInstance(functionClass);

        Object pageVar = context.getVariable("page");
        if(pageVar instanceof OrchidPage) {
            freshFunction.setPage((OrchidPage) pageVar);
        }

        freshFunction.extractOptions(contextProvider.get(), args);
        Object output = freshFunction.apply();

        if(freshFunction.isSafe()) {
            return new SafeString(output.toString());
        }
        else {
            return output;
        }
    }
}
