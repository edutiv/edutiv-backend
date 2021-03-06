package com.projectlms.projectlms.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.projectlms.projectlms.domain.common.BaseEntityWithDeletedAt;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Table(name = "M_USER")
@SQLDelete(sql = "UPDATE M_USER SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
public class User extends BaseEntityWithDeletedAt{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "username", nullable =  false)
    @Email(message = "Email should be valid")
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    private String username;

    // @Column(name = "username", nullable = false)
    // @Email(message = "EMAIL IS NOT VALID")
    // private String username;

    @Column(name = "password", nullable =  false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private Category category;

    @Builder.Default
    @Column(name = "profile_image")
    private String profileImage = "https://www.kindpng.com/picc/m/24-248253_user-profile-default-image-png-clipart-png-download.png";

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    @JsonBackReference
    //@JsonManagedReference
    private List<EnrolledCourse> enrolledCourses;

    // @JsonIgnore
    // @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    // private List<Course> courses;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    @JsonBackReference
    //@JsonManagedReference
    private List<RequestForm> requestForms;

    // @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    // //@JsonManagedReference
    // @JsonBackReference
    // private List<Review> reviews;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    //@JsonIgnore
    private Set<Role> roles;

    // @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // @JoinTable(
    //         name = "user_roles",
    //         joinColumns = @JoinColumn(name = "user_id"),
    //         inverseJoinColumns = @JoinColumn(name = "role_id")
    // )
    // @JsonIgnore
    // private Set<Role> roles;

    // public User(String username, String email, String password) {
    //     this.username = username;
    //     this.email = email;
    //     this.password = password;
    // }

}
