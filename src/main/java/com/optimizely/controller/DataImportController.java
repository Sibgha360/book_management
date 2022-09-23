package com.optimizely.controller;

import com.optimizely.helper.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.optimizely.service.CSVService;
import com.optimizely.helper.CSVHelper;
import com.optimizely.helper.message.ResponseMessage;

@Controller
@RequestMapping("/api/csv")
public class DataImportController {

    @Autowired
    CSVService fileService;

    @PostMapping("/upload_data")
    public ResponseEntity<ResponseMessage> uploadFileBooks(@RequestParam("booksfile") MultipartFile booksfile, @RequestParam("authorFiles") MultipartFile authorFiles, @RequestParam("megazinesfile") MultipartFile megazinesfile) throws Exception {
        validateFileFormat(booksfile);
        validateFileFormat(authorFiles);
        validateFileFormat(megazinesfile);

        fileService.saveAuthors(authorFiles);
        fileService.saveBooks(booksfile);
        fileService.saveMegazine(megazinesfile);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Uploaded the file successfully: " + booksfile.getOriginalFilename()));
    }


    private void validateFileFormat(MultipartFile file) throws BadRequestException {
        if (!CSVHelper.hasCSVFormat(file)) {
            throw new BadRequestException("Please upload a csv file!");
        }
    }
}
