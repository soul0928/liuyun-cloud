package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import ${cfg.basepackage}.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * ${table.comment!} 服务实现类
 *
 * @author ${author}
 * @since ${.now}
 */
@Service
public class ${table.serviceImplName}
extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {


}
