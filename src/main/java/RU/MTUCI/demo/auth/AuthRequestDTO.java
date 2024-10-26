package RU.MTUCI.demo.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
class AuthRequestDTO {
  private String username;
  private String password;
}
