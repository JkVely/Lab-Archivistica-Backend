package co.edu.udistrital.labarchivistica.security;

import co.edu.udistrital.labarchivistica.model.UserEntity;
import co.edu.udistrital.labarchivistica.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de {@link UserDetailsService} que carga usuarios desde la base de datos
 * usando el email como username. Solo permite el acceso a cuentas activas.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Carga el usuario por email.
     *
     * @param email dirección de correo del usuario
     * @return {@link UserDetails} con rol como autoridad Spring Security
     * @throws UsernameNotFoundException si el usuario no existe o está inactivo
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado con email: " + email));

        if (!user.isActive()) {
            throw new UsernameNotFoundException("La cuenta está desactivada: " + email);
        }

        // El prefijo ROLE_ es requerido por Spring Security cuando se usa hasRole()
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(!user.isActive())
                .credentialsExpired(false)
                .disabled(!user.isActive())
                .build();
    }
}
