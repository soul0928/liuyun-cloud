package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.apache.ibatis.annotations.Mapper;

/**
 * ${table.comment!} Mapper 接口
 *
 * @author ${author}
 * @since ${.now}
 */
@Mapper
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}