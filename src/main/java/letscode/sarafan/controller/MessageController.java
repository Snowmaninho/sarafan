package letscode.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import letscode.sarafan.domain.Message;
import letscode.sarafan.domain.Views;
import letscode.sarafan.repo.MessageRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController // вместо шаблонов, как в обычном Controller'е, будет возвращаться json'ы всякие
@RequestMapping("message")
public class MessageController {
    private final MessageRepo messageRepo;

    @Autowired
    public MessageController(MessageRepo messageRepo) {this.messageRepo = messageRepo;}

    @GetMapping    // т.к. основной мэппинг указан у класса, то по молчанию будет вызван метод, у которого конкретный мэппинг не указан
    @JsonView(Views.IdName.class) // будут показываться только те поля, которые помечены аннотацией с аргументом-IdName (в response'е, т.е. другие поля даже не поедут в браузер)
    public List<Message> list() {
        return messageRepo.findAll();
    }

    @GetMapping("{id}") // будет добавляться к основному мэппингу (получится "~/message/{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id") Message message /* Spring берёт параметр из пути(урла) "id" и по нему ищет сущность Message */) {
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message) {
        message.setCreationDate(LocalDateTime.now());
        return messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Message update(@PathVariable("id") Message messageFromDb,
                          @RequestBody Message message
    ) {
        BeanUtils.copyProperties(message, messageFromDb, "id"); // перекладываем значения полей из message в messageFromDb кроме id

        return messageRepo.save(messageFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageRepo.delete(message);
    }
}
