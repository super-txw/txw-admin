import com.itmk.AdminApplication;
import com.itmk.system.role.entity.SysRole;
import com.itmk.system.role.service.RoleCacheService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = AdminApplication.class)
public class RedisCacheTest {
    @Autowired
    private RoleCacheService roleCacheService;

    /**
     * 查询缓存测试
     */
    @Test
    public void getRoleById(){
        Long roleId  = 22L;
        SysRole role = roleCacheService.getRoleById(roleId);
        log.info(role.toString());
    }
    @Test
    public void updateRole(){
        SysRole role = new SysRole();
        role.setId(9L);
        role.setName("超级管理员22222");
        role.setRemark("超级管理员22222");
        SysRole res = roleCacheService.updateRole(role);
    }
    @Test
    public void addRole(){
        SysRole role = new SysRole();
        role.setName("超级管理员5555");
        role.setRemark("超级管理员55555");
        SysRole res = roleCacheService.addRole(role);
    }
    @Test
    public void deleteRole(){
        Long roleId = 17L;
        int res = roleCacheService.deleteRoleBatch(roleId);
    }
}
