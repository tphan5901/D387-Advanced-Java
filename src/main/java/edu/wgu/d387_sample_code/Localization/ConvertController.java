package edu.wgu.d387_sample_code.Localization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ConvertController {

    @GetMapping("/presentation")
    public ResponseEntity<String> announcePresentation() {
        Calendar TZConvert = null;
        String announcement = "ATTENTION: There is a presentation beginning at: " + TZConvert.getTime();
        return new ResponseEntity<String> (announcement, HttpStatus.OK);
    }
}
