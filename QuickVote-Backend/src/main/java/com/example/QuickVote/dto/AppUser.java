//package com.example.QuickVote.dto;

//public class AppUser {
//}
package com.example.QuickVote.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// ✅ Only used for JWT payload
@Getter
@Setter
public class AppUser {
    private String email;
    private String role; // can be "USER", "ADMIN", or "SUPERADMIN"

    public AppUser() {
    }

    public AppUser(String email,String role) {
        this.role = role;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

