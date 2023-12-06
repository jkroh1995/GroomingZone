package tdd.groomingzone.domain.member.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberRolesGenerator {
    private final String adminMailAddress;

    private static final List<String> ADMIN_ROLES_STRING = List.of("ADMIN", "BARBER", "CUSTOMER");
    private static final List<String> BARBER_ROLES_STRING = List.of("BARBER", "CUSTOMER");
    private static final List<String> CUSTOMER_ROLES_STRING = List.of("CUSTOMER");

    public MemberRolesGenerator(@Value("${mail.address.admin}") String adminMailAddress){
        this.adminMailAddress = adminMailAddress;
    }

    public List<String> generateMemberRoles(String email, String role) {
        if (email.equals(adminMailAddress)) {
            return ADMIN_ROLES_STRING;
        }
        if(role.equals("바버")){
            return BARBER_ROLES_STRING;
        }
        return CUSTOMER_ROLES_STRING;
    }
}
