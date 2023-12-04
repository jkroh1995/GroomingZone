package tdd.groomingzone.domain.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRolesGeneratorTest {

    @Test
    @DisplayName("이메일, 선택한 역할에 따른 적절한 역할이 생성된다.")
    void testGenerateMemberRoles(){
        String adminEmail = "shworud29@gmail.com";
        MemberRolesGenerator memberRolesGenerator = new MemberRolesGenerator(adminEmail);
        String nonAdminEmail = "test@test.com";
        String barberRole = "바버";
        String customerRole = "고객";

        assertThat(memberRolesGenerator.generateMemberRoles(adminEmail, null)).contains("ADMIN", "BARBER", "CUSTOMER");

        assertThat(memberRolesGenerator.generateMemberRoles(nonAdminEmail, barberRole)).doesNotContain("ADMIN");
        assertThat(memberRolesGenerator.generateMemberRoles(nonAdminEmail, barberRole)).contains("BARBER", "CUSTOMER");

        assertThat(memberRolesGenerator.generateMemberRoles(nonAdminEmail, customerRole)).doesNotContain("ADMIN", "BARBER");
        assertThat(memberRolesGenerator.generateMemberRoles(nonAdminEmail, customerRole)).contains("CUSTOMER");
    }
}