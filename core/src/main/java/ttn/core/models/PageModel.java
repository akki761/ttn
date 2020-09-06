package ttn.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import javax.inject.Named;

@Model(adaptables = Resource.class)
public class PageModel {

    @Inject @Named("jcr:title")
    private String title;

    @Inject @Named("cq:lastModified")
    private String modified;

    @Inject @Named("cq:lastModifiedBy")
    private String modifiedBy;

    public String getModified() {
        return modified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public String getTitle() {
        return title;
    }
}
