// package com.projectlms.projectlms.service;

// import java.util.Collection;
// import java.util.List;
// import java.util.Objects;
// import java.util.stream.Collectors;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// import com.projectlms.projectlms.domain.dao.User;

// public class CustomUserDetails implements UserDetails {
//     private Long id;
//     private String username;
//     private String password;
//     private Collection<? extends GrantedAuthority> authorities;
//     // private String firstname;
//     // private String lastname;
//     //private String specialization;
//     // public String getFirstname() {
//     //     return firstname;
//     // }
//     // public String getLastname() {
//     //     return lastname;
//     // }
//     // public String getSpecialization() {
//     //     return specialization;
//     // }
    
//     public Long getId() {
//         return id;
//     }

//     @Override
//     public String getPassword() {
//         return password;
//     }

//     @Override
//     public String getUsername() {
//         return username;
//     }

//     public CustomUserDetails(
//             Long id, String username, String password,
//             Collection<? extends GrantedAuthority> authorities
//     ) {
//         this.id = id;
//         this.username = username;
//         this.password = password;
//         this.authorities = authorities;
//     }

//     public static CustomUserDetails build(User user) {
//         List<GrantedAuthority> authorities = user.getRoles().stream()
//                 .map(role -> new SimpleGrantedAuthority(role.getName().name()))
//                 .collect(Collectors.toList());

//         return new CustomUserDetails(
//                 user.getId(),
//                 user.getUsername(),
//                 user.getPassword(),
//                 authorities);
//     }

//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() {
//         return authorities;
//     }
    
//     @Override
//     public boolean isAccountNonExpired() {
//         return true;
//     }

//     @Override
//     public boolean isAccountNonLocked() {
//         return true;
//     }

//     @Override
//     public boolean isCredentialsNonExpired() {
//         return true;
//     }

//     @Override
//     public boolean isEnabled() {
//         return true;
//     }

//     @Override
//     public boolean equals(Object o) {
//         if (this == o)
//             return true;
//         if (o == null || getClass() != o.getClass())
//             return false;
//         CustomUserDetails user = (CustomUserDetails) o;
//         return Objects.equals(id, user.id);
//     }
// }