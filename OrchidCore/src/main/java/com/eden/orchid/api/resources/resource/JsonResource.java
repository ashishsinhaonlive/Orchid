package com.eden.orchid.api.resources.resource;

import com.eden.common.json.JSONElement;
import com.eden.orchid.api.theme.pages.OrchidReference;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A Resource type that provides a JSON data as content to a template. When used with renderTemplate() or renderString(),
 * this resource will supply additional data to the template renderer. When used with renderRaw(), the raw JSON-encoded
 * data will be written directly instead.
 */
public final class JsonResource extends OrchidResource {

    public JsonResource(JSONElement element, OrchidReference reference) {
        super(reference);
        this.embeddedData = null;
        if(element != null) {
            if(element.getElement() instanceof JSONObject) {
                this.rawContent = ((JSONObject) element.getElement()).toString(2);
                this.content = ((JSONObject) element.getElement()).toString(2);
            }
            else if(element.getElement() instanceof JSONArray) {
                this.rawContent = ((JSONArray) element.getElement()).toString(2);
                this.content = ((JSONArray) element.getElement()).toString(2);
            }
            else{
                this.rawContent = element.toString();
                this.content = element.toString();
            }
        }
        else {
            this.rawContent = "";
            this.content = "";
        }
    }
}
