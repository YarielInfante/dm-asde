package com.dm.asde.support;


import com.fasterxml.jackson.annotation.JsonRootName;
import org.atteo.evo.inflector.English;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.core.DefaultRelProvider;

/**
 * When an embedded resource is provided in a response using the {@code org.springframework.hateoas.Resources} model,
 * this provider can be configured at runtime to make any embedded values root json name be set based on the classes
 * annotated {@code JsonRootName ( " name " )}. By default Spring hateoas renders the embedded root field based on the class
 * name with first character in lowercase.
 */
public class JsonRootRelProvider implements RelProvider {

    DefaultRelProvider defaultRelProvider = new DefaultRelProvider();


    @Override
    public String getItemResourceRelFor(Class<?> type) {
        JsonRootName rootName = getRootName(type);
        return rootName != null ? rootName.value() : defaultRelProvider.getItemResourceRelFor(type);
    }

    @Override
    public String getCollectionResourceRelFor(Class<?> type) {
        JsonRootName rootName = getRootName(type);
        return rootName != null ? English.plural(rootName.value()) :
                English.plural(defaultRelProvider.getItemResourceRelFor(type));
    }

    @Override
    public boolean supports(Class<?> delimiter) {
        return defaultRelProvider.supports(delimiter);
    }

    private JsonRootName getRootName(Class<?> type) {
        JsonRootName annotation = type.getAnnotation(JsonRootName.class);;
        return annotation;
    }
}
