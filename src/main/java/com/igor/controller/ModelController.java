package com.igor.controller;


import com.igor.entity.Model;
import com.igor.entity.User;
import com.igor.entity.UserSessionToken;
import com.igor.service.ModelService;
import com.igor.service.UserService;
import com.igor.utils.Utils;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
public class ModelController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelService modelService;


    private ModelAndView view;
    private Model model;
    private User user;

    @RequestMapping(value = "/restricted/newModel", method = RequestMethod.GET)
    public ModelAndView newModel() {

        view = new ModelAndView();
        view.addObject("model", model = new Model());
        view.setViewName("Restricted/NewModel");

        return view;
    }


    @RequestMapping(value = "/restricted/userHome", method = RequestMethod.GET)
    public ModelAndView userHome() {

        view = new ModelAndView();

        user = getUserSession(true);

        view.addObject("modelList", user.getModelList());

        view.setViewName("Restricted/UserHome");

        return view;
    }


    @RequestMapping(value = "/restricted/newModel", method = RequestMethod.POST)
    public ModelAndView newModelUpload(@Valid @ModelAttribute("model") Model model, @RequestParam("file") MultipartFile file, Errors err, RedirectAttributes redirectAttributes) {

        view = new ModelAndView();
        view.setViewName("redirect:/restricted/newModel");

        try {

            validate(model, err);

            if (err.hasErrors())
                return new ModelAndView("Restricted/NewModel");

            user = getUserSession(true);

            if (model.getId() == null)
                modelService.saveAndUpload(model, user, file);
            else
                modelService.updateAndUpload(model, user, file);

            redirectAttributes.addFlashAttribute("message_success", "Model " + model.getTitle() + " was successfully registered");

        } catch (Exception ex) {
            Utils.showErrors(ex);
            redirectAttributes.addFlashAttribute("message_error", "Error registering model, please contact technical support");
        }


        return view;
    }


    @RequestMapping(value = "/restricted/deleteModel", method = RequestMethod.POST)
    public ModelAndView editModel(@RequestParam("modelId") Integer modelId, RedirectAttributes redirectAttributes) {

        view = new ModelAndView();
        view.setViewName("redirect:/restricted/userHome");


        try {

            user = getUserSession(true);

            Optional<Model> optionalModel = modelService.findById(modelId);

            Model modelDelete = optionalModel.get();

            if (modelDelete != null) {
                modelService.deleteModel(user.getId(), modelDelete);
            } else {
                redirectAttributes.addFlashAttribute("message_error", "This model was already deleted");
                return view;
            }

            redirectAttributes.addFlashAttribute("message_success", "Model " + modelDelete.getTitle() + " was successfully deleted");

        } catch (Exception ex) {
            Utils.showErrors(ex);
            redirectAttributes.addFlashAttribute("message_error", "Error deleting model, please contact technical support");
        }


        return view;
    }


    @RequestMapping(value = "/restricted/editModel/{modelId}", method = RequestMethod.GET)
    public ModelAndView deleteModel(@PathVariable("modelId") Integer modelId, RedirectAttributes redirectAttributes) {

        view = new ModelAndView();
        view.setViewName("Restricted/NewModel");

        try {

            Optional<Model> optionalModel = modelService.findById(modelId);

            Model modelEdit = optionalModel.get();

            if (modelEdit != null) {
                view.addObject("model", modelEdit);
            } else {
                redirectAttributes.addFlashAttribute("message_error", "This model was deleted");
                return view;
            }

        } catch (Exception ex) {
            Utils.showErrors(ex);
            redirectAttributes.addFlashAttribute("message_error", "Error editing model, please contact technical support");
        }


        return view;
    }


    public void validate(Model m, Errors err) throws IOException, MagicParseException, MagicException, MagicMatchNotFoundException {

        if (m.getDescription().length() == 0) {
            err.rejectValue("description", "description.required");
        }

        if (m.getTitle().length() == 0) {
            err.rejectValue("title", "title.required");
        }

        if (m.getFile().isEmpty() && m.getId() == null) {
            err.rejectValue("file", "file.required");

        } else {

            if (!m.getFile().isEmpty()) {
                if (!Utils.allowedFileSize(Utils.convertFile(m.getFile()))) {
                    err.rejectValue("file", "file.not.allowed.size");
                }
                if (!Utils.allowedMimeType(Utils.convertFile(m.getFile()))) {
                    err.rejectValue("file", "file.not.allowed.mime.type");
                }
            }
        }


    }


    public User getUserSession(boolean valida) {

        User user = new User();

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication instanceof UserSessionToken)
                user = (User) authentication.getPrincipal();

            if (valida)
                user = userService.loadUserByUsername(user.getUsername());

        } catch (Exception e) {
            Utils.showErrors(e);
        }

        return user;
    }


}
