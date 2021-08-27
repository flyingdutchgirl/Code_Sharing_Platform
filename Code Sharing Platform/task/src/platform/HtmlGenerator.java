package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Component
@Controller
public class HtmlGenerator {

    private DatabaseHandler databaseHandler;

    @Autowired
    public HtmlGenerator(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }


    @GetMapping("/code/{UUID}")
    @ResponseBody //maybe not
    public ModelAndView getHtmlByUuid(@PathVariable String UUID) {
        ModelAndView mav = new ModelAndView();
        mav.setStatus(HttpStatus.OK);
        mav.setViewName("codesharing1");

        var optSnippet = databaseHandler.snippetByUuid(UUID);

        if (optSnippet.isEmpty() || !optSnippet.orElseThrow().isVisible()) {
            mav.setStatus(HttpStatus.NOT_FOUND);
            return mav;
        }

        var snippet = optSnippet.orElseThrow();
        databaseHandler.snippetViewed(snippet.getUuid());

        mav.addObject("DATE", snippet.getStringDate());
        mav.addObject("CODE", snippet.getCode());
        mav.addObject("VIEWS", snippet.getNumberOfViews());
        mav.addObject("TIME_RESTRICTION", snippet.timeLeft());
        mav.addObject("snippet", snippet);

        return mav;
    }


    @GetMapping("/code/new")
    public ModelAndView newCodeHtml() {
        ModelAndView mav = new ModelAndView();
        mav.setStatus(HttpStatus.OK);
        mav.setViewName("sender");

        return mav;
    }


    @GetMapping("/code/latest")
    public String htmlLatest(Model model) {
        model.addAttribute("snippets",
                databaseHandler.latestSnippets());
        return "manySnippets";
    }

}
