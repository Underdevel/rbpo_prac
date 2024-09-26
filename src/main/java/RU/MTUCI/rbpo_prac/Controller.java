package RU.MTUCI.rbpo_prac;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class Controller {

    //("/") + ("/hello")
    @GetMapping("/hello")
    public String getHello(@RequestParam String str) {
        return str;
    }

    @PostMapping
    public mainClass setValue(@RequestBody mainClass demo) {
        demo.setNumber(demo.getNumber() + 5);
        return demo;
    }

}