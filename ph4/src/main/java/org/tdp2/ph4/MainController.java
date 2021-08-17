package org.tdp2.ph4;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller // This means that this class is a Controller
@RequestMapping(path="/ph4") 
public class MainController {
  @Autowired // This means to get the bean called valuesMPURepository
         // Which is auto-generated by Spring, we will use it to handle the data
  private ValuesMPURepository valuesMPURepository;

  @Autowired
    RestService restService;
    



  @PostMapping(path="/add") // Map ONLY POST Requests
  public @ResponseBody String addNewValuesMPU (@RequestBody ValuesMPU valuesMPU) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
  

    valuesMPURepository.save(valuesMPU);
    return "Saved";
  }

  @GetMapping(path="/deleteAll") 
  public  String deleteAllValuesMPU () {
   
    valuesMPURepository.deleteAll();
    return "indexA";
  }

  @GetMapping(path="/all")
  public @ResponseBody Iterable<ValuesMPU> getAllValuesMPU() {
    // This returns a JSON or XML with the valuesMPU
    return valuesMPURepository.findAll();
  }


  @GetMapping("/excel")
public ResponseEntity<StreamingResponseBody> excel() {
  Workbook workBook = new XSSFWorkbook();
  Sheet sheet = workBook.createSheet("ValoresMPU");
  sheet.setColumnWidth(0, 2560);
  sheet.setColumnWidth(1, 2560);
  Row row = sheet.createRow(0);
  row.createCell(0).setCellValue("Roll");
  row.createCell(1).setCellValue("Pitch");
  Iterator<ValuesMPU> it= valuesMPURepository.findAll().iterator();
        int i=1;
        while (it.hasNext()){
           row = sheet.createRow(i);
            ValuesMPU v=it.next();
            row.createCell(0).setCellValue(v.getrotX());
            row.createCell(1).setCellValue(v.getrotY());
            i=i+1;

        }
  
  
  


  return ResponseEntity
    .ok()
    .contentType(MediaType.APPLICATION_OCTET_STREAM)
    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"ValoresMPU.xlsx\"")
    .body(workBook::write);
}








  @PostMapping(
    path = "/processing",
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE

)
  public @ResponseBody String processingGetValues (ValueDTO valueDTO, Model model) {
    // @ResponseBody means the returned String is the response, not a view name
    String resp="Roll: "+valueDTO.getValueRotX()+" Pitch: "+valueDTO.getValueRotY();
    System.out.println(resp);
    ValuesMPU valuesMPU=new ValuesMPU();
    valuesMPU.setRotX(valueDTO.valueRotX);
    valuesMPU.setRotY(valueDTO.valueRotY);
    valuesMPURepository.save(valuesMPU);



    return resp;
  }



  @GetMapping(path="/valuesMPU")
  public String indexPage(Model model) {
    // This returns a JSON or XML with the valuesMPU
    
  
    
    List<ValuesMPU> listValuesMPU= valuesMPURepository.findAll();
    model.addAttribute("listValuesMPU", listValuesMPU);  
    

    return "index";
  }



  
  


}