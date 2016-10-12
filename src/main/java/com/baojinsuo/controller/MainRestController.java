package com.baojinsuo.controller;

/**
 * Created by bresai on 2016/10/2.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;

/**
 * Controller with REST API. Access to login is generally permitted,
 * stuff in /secure/ sub-context is protected by config. Some security
 * annotations are thrown in just to make a point.
 */
@RestController
public class MainRestController {

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        System.out.println(" *** MainRestController.init with: " + applicationContext);
    }

    @RequestMapping("/test")
    public String test() {
        System.out.println(" *** MainRestController.test");
        // Spring Security dependency is unwanted in controller, typically some @Component (UserContext) hides it.
        // Not that we don't use Spring Security annotations anyway...
        return "SecurityContext: " + SecurityContextHolder.getContext();
    }

    // standard JSR 250 annotation
    @RolesAllowed("ADMIN")
    @RequestMapping("/admin")
    public String admin() {
        System.out.println(" *** MainRestController.admin");
        return "Cool, you're admin!";
    }

    @RequestMapping("/secure/service1")
    public String service1() {
        System.out.println(" *** MainRestController.service1");
        return "Any authorized user should have access.";
    }



    // Spring annotation virtually equivalent with @RolesAllowed - except for...
    // WARNING: @Secured by default works only with roles starting with ROLE_ prefix, see this for more:
    // http://bluefoot.info/howtos/spring-security-adding-a-custom-role-prefix/
    // I don't want to mess with RoleVoters - that's why ADMIN does NOT have access to this page
    @Secured({"ROLE_SPECIAL", "ADMIN"})
    @RequestMapping("/secure/special")
    public String special() {
        System.out.println(" *** MainRestController.special");
        return "ROLE_SPECIAL users should have access.";
    }

//    // Spring annotation that speaks SpEL!
//    @PreAuthorize("hasRole('ADMIN')")
//    @RequestMapping("/secure/allusers")
//    public Map<String, UserDetails> allUsers() {
//        System.out.println(" *** MainRestController.allUsers");
//        return tokenManager.getValidUsers();
//    }
}