package com.igor.configuration;


import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingStrategy;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;


@Configuration
@ComponentScan(basePackages = {"com.igor"})
public class WebConfig extends WebMvcConfigurationSupport {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/signIn").setViewName("Login/SignIn");
        registry.addViewController("/about").setViewName("About/About");
        registry.addViewController("/education").setViewName("Education/Education");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/libs/**")
                .addResourceLocations("classpath:/static/libs/");

        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");

        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("classpath:/static/fonts/");

//        registry.addResourceHandler("/models/**")
//                .addResourceLocations(Patterns.IMAGES_PATH_LOCAL);

//        registry.addResourceHandler("/models/**")
//                .addResourceLocations(Patterns.IMAGES_PATH_AMAZON);


    }


    @Bean
    public ViewResolver mvcViewResolver() {

        final ThymeleafViewResolver resolver = new ThymeleafViewResolver();

        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        resolver.clearCache();

        resolver.setCache(false);
        resolver.setCacheUnresolved(false);

        return resolver;

    }


    private ISpringTemplateEngine templateEngine() {

        final SpringTemplateEngine engine = new SpringTemplateEngine();
        final LayoutDialect layoutDialect = new LayoutDialect(new GroupingStrategy());

        engine.setEnableSpringELCompiler(true);
        engine.addTemplateResolver(templateResolver());

        engine.addDialect(layoutDialect);
        engine.addDialect(new SpringSecurityDialect());

        engine.clearTemplateCache();

        return engine;
    }

    private ITemplateResolver templateResolver() {

        final SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();

        resolver.setPrefix("/templates/");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setSuffix(".html");
        resolver.setCacheable(false);

        return resolver;
    }


    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/resources/i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(1);
        return messageSource;
    }


}
