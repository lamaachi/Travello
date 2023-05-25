package com.example.travel_agency_pfe.Services;

import com.example.travel_agency_pfe.Models.Image;
import com.example.travel_agency_pfe.Repositories.IImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.travel_agency_pfe.Configurations.ImageUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private IImageRepository imageDataRepository;

    public void uploadImage(MultipartFile file) throws IOException {
        imageDataRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes())).build());
    }

    @Transactional
    public Image getInfoByImageByName(String name) {
        Image dbImage = imageDataRepository.findByName(name);

        return Image.builder()
                .name(dbImage.getName())
                .type(dbImage.getType())
                .imageData(ImageUtil.decompressImage(dbImage.getImageData())).build();
    }

    @Transactional
    public byte[] getImage(String name) {
        Image dbImage = imageDataRepository.findByName(name);
        byte[] image = ImageUtil.decompressImage(dbImage.getImageData());
        return image;
    }

}
