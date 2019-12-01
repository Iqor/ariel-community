package com.igor.service;

import com.igor.amazon.DeleteObject;
import com.igor.amazon.UploadObject;
import com.igor.entity.Model;
import com.igor.entity.User;
import com.igor.repository.ModelRepository;
import com.igor.utils.Patterns;
import com.igor.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ModelService {

    @Autowired
    private ModelRepository modelRepository;

    public List<Model> findAll() {
        return modelRepository.findAll();
    }

    public Optional<Model> findById(Integer id) {
        return modelRepository.findById(id);
    }

    public List<Model> findBySearch(String searchParameter) {
        return modelRepository.findModelsByDescriptionContainingOrTitleContainingIgnoreCase(searchParameter, searchParameter);
    }

    @Transactional
    public void deleteModel(Integer userId, Model model) throws IOException {
        DeleteObject.delete(model, userId);
        modelRepository.deleteModel(model.getId());
    }


    public void saveAndUpload(Model model, User user, MultipartFile file) throws IOException {

        File convertedFile = Utils.convertFile(file);

        model.setModelDate(new Date());
        model.setUser(user);
        model.setFileHash(Utils.generateFileHash());

        model.setFilePath(System.getenv("IMAGES_PATH_AMAZON") + user.getId() + "/" + model.getFileHash());

        UploadObject.upload(model, user, convertedFile);


        modelRepository.save(model);

    }

    public void updateAndUpload(Model model, User user, MultipartFile file) throws IOException {

        Optional<Model> optionalModel = modelRepository.findById(model.getId());
        Model newModel = optionalModel.get();

        newModel.setDescription(model.getDescription());
        newModel.setTitle(model.getTitle());

        if (!file.isEmpty()) {
            File convertedFile = Utils.convertFile(file);
            newModel.setFileHash(Utils.generateFileHash());

            newModel.setFilePath(System.getenv("IMAGES_PATH_AMAZON") + user.getId() + "/" + newModel.getFileHash());

            UploadObject.upload(newModel, user, convertedFile);
        }

        modelRepository.save(newModel);
    }


}
