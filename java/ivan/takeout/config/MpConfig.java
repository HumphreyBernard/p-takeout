package ivan.takeout.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置mybatis plus的配置类
 *
 * @author Maximilian_Li
 */
@Configuration
public class MpConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor in = new MybatisPlusInterceptor();

        // 添加分页插件
        in.addInnerInterceptor(new PaginationInnerInterceptor());

        return in;
    }
}
