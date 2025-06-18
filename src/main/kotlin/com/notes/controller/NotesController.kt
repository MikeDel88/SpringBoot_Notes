import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class NotesController {

    @GetMapping("/notes")
    fun getNotes(): String {
        return "notes chargées";
    }
}