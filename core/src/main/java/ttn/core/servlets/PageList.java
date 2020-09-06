package ttn.core.servlets;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import ttn.core.models.PageModel;
import ttn.core.pojo.Result;

import javax.jcr.Session;
import javax.servlet.Servlet;
import java.io.IOException;
import java.util.*;

@Component(service = Servlet.class, property = {
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/find/pagelist",
        "sling.servlet.extensions=" + "json"
})
@ServiceDescription("PageList servlet")
public class PageList extends SlingSafeMethodsServlet {

    @Reference
    private QueryBuilder queryBuilder;

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException {
        String path = request.getParameter("path");
        String limit = request.getParameter("limit");
        String offset = request.getParameter("offset");
        Map<String, String> queryMap = createQueryMap(path, limit, offset);
        SearchResult result = queryBuilder.createQuery(PredicateGroup.create(queryMap),
                request.getResourceResolver().adaptTo(Session.class)).getResult();
        Iterator<Resource> resourceIterator = result.getResources();
        List<PageModel> list = new ArrayList<>();
        while(resourceIterator.hasNext()){
            list.add(resourceIterator.next().adaptTo(PageModel.class));
        }
        Result result1 = new Result();
        result1.setPageModelList(list);
        result1.setTotalResult(result.getTotalMatches());
        Gson gson = new GsonBuilder().serializeNulls().create();
        gson.toJson(result1, response.getWriter());
    }

    private Map<String, String> createQueryMap(final String path, final String limit, final String offset){
        Map<String,String> map = new HashMap<>();
        map.put("path", path);
        map.put("type", "cq:PageContent");
        map.put("p.offset", offset);
        map.put("p.limit", limit);
        return map;
    }
}