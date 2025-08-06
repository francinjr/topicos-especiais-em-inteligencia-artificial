package com.francinjr.rentalbusiness.components.users;

import com.francinjr.rentalbusiness.commons.infrastructure.web.response.ApiSuccessResponse;
import com.francinjr.rentalbusiness.commons.infrastructure.web.response.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários e autenticação")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Autenticar usuário", description = "Realiza o login do usuário e gera um token de autenticação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Credenciais inválidas.")
    })
    @PostMapping("/auth/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response) {
        User user = UserMapper.loginDtoToUser(loginDto);
        userService.login(user, response);
        return ResponseEntity.ok().build();
    }

    /*@Operation(summary = "Validar operações de usuário", description = "Verifica a identidade do usuário autenticado para realizar determinada ação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Validação realizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Credenciais inválidas.")
    })
    @PostMapping("/validate")
    public ResponseEntity<Void> validateUserOperation(@RequestBody @Valid LoginDto loginDto) {
        User user = UserMapper.loginDtoToUser(loginDto);
        userService.validateUserOperation(user);
        return ResponseEntity.ok().build();
    }*/

    /*@Operation(summary = "Solicitar redefinição de senha", description = "Envia um e-mail com um token para redefinição de senha.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "E-mail enviado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    })
    @PostMapping("/request-password-reset")
    public ResponseEntity<Void> solicitarAlteracaoSenha(@RequestParam String email) {
        userService.requestChangePassword(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    /*@Operation(summary = "Verificar token de redefinição de senha", description = "Valida se um token de redefinição de senha é válido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token válido."),
            @ApiResponse(responseCode = "400", description = "Token inválido ou expirado.")
    })
    @PostMapping("/verify-token")
    public ResponseEntity<ApiSuccessResponse<Void>> verificarCodigo(@RequestParam String token, HttpServletRequest request) {
        userService.verifyToken(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    /*@Operation(summary = "Alterar senha do usuário", description = "Permite a alteração de senha do usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao alterar a senha.")
    })
    @PutMapping("/change-password")
    public ResponseEntity<Void> alterarSenha(@RequestParam String email, @RequestParam String novaSenha) {
        userService.changePassword(email, novaSenha);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Alterar senha do usuário", description = "Permite a alteração de senha do usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao alterar a senha.")
    })
    @PutMapping("/change-passwordwt")
    public ResponseEntity<Void> alterarSenhaSemToken(@RequestParam String email, @RequestParam String novaSenha) {
        userService.changePasswordWithoutToken(email, novaSenha);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    @Operation(summary = "Buscar usuário por e-mail", description = "Obtém as informações de um usuário pelo seu e-mail.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    })
    @GetMapping
    public ResponseEntity<ApiSuccessResponse<BuscarUserDto>> fetchUserByEmail(
            @RequestParam String email, HttpServletRequest request) {

        User foundedUser = userService.fetchByEmail(email);
        BuscarUserDto userDto = UserMapper.userToBuscarUserDto(foundedUser);

        ApiSuccessResponse<BuscarUserDto> response = ResponseUtil.success(userDto,
                "Usuário buscado com sucesso.", request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
