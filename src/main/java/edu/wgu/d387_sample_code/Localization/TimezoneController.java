package edu.wgu.d387_sample_code.Localization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import static edu.wgu.d387_sample_code.Localization.ConvertTimezone.getTime;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TimezoneController {

    @GetMapping("/presentation")
    public ResponseEntity<String> present() {
        String announcement = "There is a presentation at: " + getTime();
        return new ResponseEntity<>(announcement, HttpStatus.OK);
    }

}
