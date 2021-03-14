package com.groupwork.Explorers.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.groupwork.Explorers.Repo.AcquisitionCostDocRepo;
import com.groupwork.Explorers.model.Docs.AcquisitionCostDoc;

@Service
public class AcquisitionCostDocStorageService {

	@Autowired
	private AcquisitionCostDocRepo acdr;
	
	public AcquisitionCostDoc saveFile(MultipartFile file) {
		String acquisitiondocname = file.getOriginalFilename();
		
		try {
			AcquisitionCostDoc acquisitioncostdoc = new AcquisitionCostDoc(acquisitiondocname, file.getContentType(), file.getBytes());
			return acdr.save(acquisitioncostdoc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Optional<AcquisitionCostDoc> getFile(Integer fileId) {
		return acdr.findById(fileId);
	}
	
	public List<AcquisitionCostDoc> getFiles() {
		return acdr.findAll();
	}
}
