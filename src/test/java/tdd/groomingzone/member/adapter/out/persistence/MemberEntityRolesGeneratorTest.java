package tdd.groomingzone.member.adapter.out.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberEntityRolesGeneratorTest {

    @Test
    @DisplayName("이메일, 선택한 역할에 따른 적절한 역할이 생성된다.")
    void testGenerateMemberRoles() {
        String adminEmail = "shworud29@gmail.com";
        MemberEntityRolesGenerator memberEntityRolesGenerator = new MemberEntityRolesGenerator(adminEmail);
        String nonAdminEmail = "test@test.com";
        String barberRole = "바버";
        String customerRole = "고객";

        assertThat(memberEntityRolesGenerator.generateMemberRoles(adminEmail, null)).contains("ADMIN", "BARBER", "CUSTOMER");

        assertThat(memberEntityRolesGenerator.generateMemberRoles(nonAdminEmail, barberRole)).doesNotContain("ADMIN");
        assertThat(memberEntityRolesGenerator.generateMemberRoles(nonAdminEmail, barberRole)).contains("BARBER", "CUSTOMER");

        assertThat(memberEntityRolesGenerator.generateMemberRoles(nonAdminEmail, customerRole)).doesNotContain("ADMIN", "BARBER");
        assertThat(memberEntityRolesGenerator.generateMemberRoles(nonAdminEmail, customerRole)).contains("CUSTOMER");
    }
}