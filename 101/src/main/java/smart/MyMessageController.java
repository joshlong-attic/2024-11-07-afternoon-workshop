package smart;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
class MyMessageController {

    private final Message message;

    MyMessageController(Message message) {
        this.message = message;
    }

    @GetMapping("/message")
    String read() {
        return this.message.message();
    }
}
