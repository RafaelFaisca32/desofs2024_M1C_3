package com.mycompany.myapp.config;

import java.time.Duration;

import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.manager.Manager;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import com.mycompany.myapp.domain.serviceRequest.ServiceStatus;
import com.mycompany.myapp.domain.transport.Transport;
import com.mycompany.myapp.domain.truck.Truck;
import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.domain.user.Authority;
import com.mycompany.myapp.infrastructure.repository.jpa.UserRepository;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries())
            )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build()
        );
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, Authority.class.getName());
            createCache(cm, Driver.class.getName());
            createCache(cm, Manager.class.getName());
            createCache(cm, Customer.class.getName());
            createCache(cm, Customer.class.getName() + ".locations");
            createCache(cm, ApplicationUser.class.getName());
            createCache(cm, Truck.class.getName());
            createCache(cm, Location.class.getName());
            createCache(cm, ServiceRequest.class.getName());
            createCache(cm, ServiceRequest.class.getName() + ".serviceStatuses");
            createCache(cm, ServiceStatus.class.getName());
            createCache(cm, Transport.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
