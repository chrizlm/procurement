package com.groupwork.Explorers.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.groupwork.Explorers.model.Docs.AcquisitionCostDoc;
import com.groupwork.Explorers.service.AcquisitionCostDocStorageService;

@Controller
public class AcquisitionCostDocController {

	@Autowired
	private AcquisitionCostDocStorageService acdss;
	
	@GetMapping("/acquisitionCostDocFile")
	public void get() {
		acdss.getFiles();
	}
	
	@PostMapping("/uploadAcquisitionCostDocFile")
	public String uploadMultipleFiles(MultipartFile[] files) {
		for(MultipartFile file : files) {
			acdss.saveFile(file);
		}
		return "redirect:/";
	}
	
	@GetMapping("/downloadAcquisitionCostDocFile/{fileId}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId){
		AcquisitionCostDoc acquisitioncostdoc = acdss.getFile(fileId).get();
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(acquisitioncostdoc.getAcquisitionCostDocType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + acquisitioncostdoc.getAcquisitionCostDocName() + "\"")
				.body(new ByteArrayResource(acquisitioncostdoc.getAcquisitionCostDocData()));
	}
}
