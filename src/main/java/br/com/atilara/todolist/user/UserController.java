package br.com.atilara.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired // Injeção de dependência, spring vai gerenciar a instância
    private IUserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }

        var hashedPassword = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(hashedPassword);

        return ResponseEntity.ok(this.userRepository.save(userModel));
    }

}
