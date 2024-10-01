package edu.wgu.d387_sample_code.Localization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TZConvertController {

    @GetMapping("/presentation")
    public ResponseEntity<String> announcePresentation() {
        String announcement = "There is a presentation at: " + TZConvert.getTime();
        return new ResponseEntity<String> (announcement, HttpStatus.OK);
    }

}
