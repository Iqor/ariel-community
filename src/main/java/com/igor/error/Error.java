package com.igor.error;

import com.igor.utils.Patterns;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class Error implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {

        ModelAndView view = new ModelAndView("Error/Error");
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            view.addObject("errorStatus", statusCode);

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return view;
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return view;
            }
        }

        return view;
    }

    @Override
    public String getErrorPath() {
        return Patterns.ERROR_PATH;
    }


}