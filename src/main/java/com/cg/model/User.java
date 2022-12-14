package com.cg.model;

import com.cg.model.dto.UserDTO;
import com.mysql.cj.protocol.ColumnDefinition;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
@Accessors(chain = true)
public class User extends BaseEntities{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, updatable = false)
    private String password;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne
    @JoinColumn(name = "location_region_id", nullable = false)
    private LocationRegion locationRegion;

    @Column(columnDefinition = " boolean default false")
    private boolean deleted;

    public UserDTO toUserDTO(){
        return new UserDTO()
                .setId(id.toString())
                .setFullName(fullName)
                .setUsername(username)
                .setPassword(password)
                .setPhone(phone)
                .setRole(role.toRoleDTO())
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                .setDeleted(deleted);
    }
}
