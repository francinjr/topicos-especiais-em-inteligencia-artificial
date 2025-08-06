package com.francinjr.rentalbusiness.components.users;


import java.security.SecureRandom;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    //private EmailService emailService;
    //private PasswordResetTokenService passwordResetTokenService;
    AuthenticationManager authenticationManager;
    TokenService tokenService;
    //RabbitMQEmailProducer rabbitMQEmailProducer;

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User fetchByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {

            throw new BadCredentialsException("O usuário informado está incorreto ou não existe.");
        }
        return user;
    }

    @Override
    public void login(User user, HttpServletResponse response) {
        fetchByEmail(user.getEmail());
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            var auth = authenticationManager.authenticate(usernamePassword);
            user = (User) auth.getPrincipal();
            String token = tokenService.generateToken(user);

            response.setHeader("Authorization", token);

            logger.info("Usuário {} se autenticou com sucesso.", user.getId());

        } catch (BadCredentialsException ex) {
            throw new UsernameOrPasswordInvalidException("Usuário e/ou senha incorretos.");
        } catch (InternalAuthenticationServiceException ex) {

            logger.error("Usuário {} falhou ao tentar se autenticar.", user.getId());

            throw new InternalAuthenticationServiceException("Erro interno de autenticação", ex);
        }
    }

    /*@Transactional
    @Override
    public User save(User user) {
        String password = generateRandomPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setIsPasswordChanged(false);
        //emailService.sendPassword(user.getEmail(), password);
        return userRepository.save(user);
    }*/

	/*private List<ValidationField> validateUniqueFields(User user) throws UniqueFieldValuesAlreadyExistsException {

		if (user.getId() == null) {
			user.setId(0L);
		}

		List<ValidationField> existingFieldValues = new ArrayList<>();
		// Optional<User> searchedUserByEmail =
		// userRepository.findByEmail(user.getEmail());
		// User searchedUserByEmail = userRepository.findUserByEmail(user.getEmail());
		User searchedUserByEmail = userRepository.findByEmail(user.getEmail());

		if (searchedUserByEmail != null && !user.getId().equals(searchedUserByEmail.getId())) {
			existingFieldValues.add(
					new ValidationField("email", "Já existe um usuário cadastrado com o email: " + user.getEmail()));
		}

		return existingFieldValues;
	}*/

    /*@Override
    public void requestChangePassword(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new ResourceNotFoundException("Não foi possível enviar o e-mail, pois o email informado não existe. Verifique se o email está correto.");
        }
        PasswordResetToken passwordResetToken = passwordResetTokenService.createToken(email);
        //emailService.sendToken(passwordResetToken.getEmail(), passwordResetToken.getToken());
    }

    @Override
    public void verifyToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token não encontrado"));
        if (passwordResetTokenService.isTokenExpired(passwordResetToken.getToken())) {
            throw new InvalidTokenException("Token expirou.");
        }
        passwordResetToken.setValidated(true);
        passwordResetTokenService.saveToken(passwordResetToken);
    }

    @Override
    public void changePassword(String email, String novaSenha) {

        PasswordResetToken prt = passwordResetTokenService.findByEmail(email);

        if (prt == null || !prt.isValidated()) {
            throw new ResourceNotFoundException("Token não existe ou não foi validado.");
        }

        passwordResetTokenService.deleteToken(prt.getToken());

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new ResourceNotFoundException("Não foi possível alterar senha. Usuário com e-mail informado não existe.");
        }

        String senhaCodificada = passwordEncoder.encode(novaSenha);
        user.setPassword(senhaCodificada);

        if (user.getIsPasswordChanged() == false) {
            user.setIsPasswordChanged(true);
        }

        User savedUser = userRepository.save(user);

        logger.info("Usuário {} alterou sua senha com sucesso.", savedUser.getId());
    }

    @Override
    public void changePasswordWithoutToken(String email, String novaSenha) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new ResourceNotFoundException("Não foi possível alterar senha. Usuário com e-mail informado não existe.");
        }

        String senhaCodificada = passwordEncoder.encode(novaSenha);
        user.setPassword(senhaCodificada);

        if (user.getIsPasswordChanged() == false) {
            user.setIsPasswordChanged(true);
        }

        User savedUser = userRepository.save(user);

        logger.info("Usuário {} alterou sua senha com sucesso.", savedUser.getId());
    }

    @Override
    public void validateUserOperation(User user) {
        try {
            fetchByEmail(user.getEmail());

            var usernamePassword = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            authenticationManager.authenticate(usernamePassword);

            logger.info("Usuário {} autenticado para realizar a operação.", user.getId());

        } catch (BadCredentialsException ex) {
            throw new UsernameOrPasswordInvalidException("Usuário e/ou senha incorretos.");
        } catch (InternalAuthenticationServiceException ex) {
            logger.error("Falha ao tentar autenticar o usuário com email {}", user.getEmail());
            throw new InternalAuthenticationServiceException("Erro interno de autenticação", ex);
        }
    }


    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[7];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes).substring(0, 7);
    }*/
}
